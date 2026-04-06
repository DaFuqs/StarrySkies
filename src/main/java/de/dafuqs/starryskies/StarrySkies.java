package de.dafuqs.starryskies;

import de.dafuqs.starryskies.advancements.*;
import de.dafuqs.starryskies.commands.*;
import de.dafuqs.starryskies.configs.*;
import de.dafuqs.starryskies.data_loaders.*;
import de.dafuqs.starryskies.registries.*;
import de.dafuqs.starryskies.state_providers.*;
import de.dafuqs.starryskies.worldgen.*;
import de.dafuqs.starryskies.worldgen.dimension.*;
import it.unimi.dsi.fastutil.objects.*;
import me.shedaniel.autoconfig.*;
import me.shedaniel.autoconfig.serializer.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.command.v2.*;
import net.fabricmc.fabric.api.entity.event.v1.*;
import net.fabricmc.fabric.api.event.lifecycle.v1.*;
import net.fabricmc.fabric.api.resource.*;
import net.kyrptonaught.customportalapi.*;
import net.kyrptonaught.customportalapi.util.*;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.ChunkGenerator;
import org.slf4j.*;

import java.util.*;

public class StarrySkies implements ModInitializer {
	
	public static final String MOD_ID = "starry_skies";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static StarrySkyConfig CONFIG;
	
	public static ResourceLocation id(String name) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
	}
	
	public static String idPlain(String name) {
		return id(name).toString();
	}
	
	public static boolean isStarryWorld(ServerLevel world) {
		ChunkGenerator chunkGenerator = world.getChunkSource().getGenerator();
		return chunkGenerator instanceof StarrySkyChunkGenerator;
	}
	
	@Override
	public void onInitialize() {
		//Set up config
		LOGGER.info("Starting up...");
		AutoConfig.register(StarrySkyConfig.class, JanksonConfigSerializer::new);
		CONFIG = AutoConfig.getConfigHolder(StarrySkyConfig.class).getConfig();
		
		ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(UniqueBlockGroupDataLoader.INSTANCE);
		ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(WeightedBlockGroupDataLoader.INSTANCE);
		
		// Register all the stuff
		Registry.register(BuiltInRegistries.CHUNK_GENERATOR, StarrySkies.id("starry_skies"), StarrySkyChunkGenerator.CODEC);
		
		StarryRegistries.register();
		StarryStateProviders.register();
		Spheres.initialize();
		StarryFeatures.initialize();
		SphereDecorators.initialize();
		StarryAdvancementCriteria.register();
		
		ArgumentTypeInfos.register(BuiltInRegistries.COMMAND_ARGUMENT_TYPE, "starry_skies_configured_sphere", ConfiguredSphereArgumentType.class, SingletonArgumentInfo.contextAware(ConfiguredSphereArgumentType::configuredSphere));
		CommandRegistrationCallback.EVENT.register((commandDispatcher, commandRegistryAccess, registrationEnvironment) -> {
			ClosestSphereCommand.register(commandDispatcher, commandRegistryAccess);
			GenerateSphereCommand.register(commandDispatcher, commandRegistryAccess);
		});
		ServerTickEvents.END_SERVER_TICK.register(new ProximityAdvancementCheckEvent());
		
		// Build a final map of sphere generation data for each chunk generator
		ServerLifecycleEvents.SERVER_STARTING.register(server -> {
			Registry<GenerationGroup> generationGroupRegistry = server.registryAccess().lookupOrThrow(StarryRegistryKeys.GENERATION_GROUP);
			Registry<SystemGenerator> systemGeneratorRegistry = server.registryAccess().lookupOrThrow(StarryRegistryKeys.SYSTEM_GENERATOR);
			Registry<ConfiguredSphere<?, ?>> configuredSphereRegistry = server.registryAccess().lookupOrThrow(StarryRegistryKeys.CONFIGURED_SPHERE);
			
			for (GenerationGroup generationGroup : generationGroupRegistry) {
				// cursed generator group id lookup. Using getEntries() does return random order, making worldgen undeterministic :C
				ResourceLocation generationGroupId = generationGroupRegistry.getResourceKey(generationGroup).orElseThrow().location();
				ResourceLocation systemGeneratorId = generationGroup.systemGeneratorId();
				
				SystemGenerator systemGenerator = systemGeneratorRegistry.getValue(systemGeneratorId);
				if (systemGenerator == null) {
					LOGGER.error("System generator with id {} referenced in starry skies generation group {} was not found", generationGroup.systemGeneratorId(), generationGroupId);
					continue;
				}
				
				Map<ConfiguredSphere<?, ?>, Float> weightedSpheres = new Object2ObjectArrayMap<>();
				for (ConfiguredSphere<?, ?> sphere : configuredSphereRegistry) {
					SphereConfig.Generation sphereGenerationGroup = sphere.getGenerationGroup();
					if (sphereGenerationGroup != null && sphereGenerationGroup.group().equals(generationGroupId)) {
						weightedSpheres.put(sphere, sphereGenerationGroup.weight());
					}
				}
				
				if (!weightedSpheres.isEmpty()) {
					systemGenerator.addGenerationGroup(weightedSpheres, generationGroup.weight());
				}
			}
		});
		
		/*
			Workaround for https://bugs.mojang.com/browse/MC-188578:
			Sleeping in a bed in a custom dimension doesn't set time to day
			Weather and time of day is also only tracked in the overworld
		 */
		EntitySleepEvents.STOP_SLEEPING.register((entity, sleepingPos) -> {
			if (entity instanceof ServerPlayer serverPlayerEntity) {
				ServerLevel world = serverPlayerEntity.level();
				if (isStarryWorld(world) && serverPlayerEntity.isSleepingLongEnough()) {
					long nextDay = world.getDayTime() + 24000L;
					long mod = nextDay - nextDay % 24000L;
					world.getServer().overworld().setDayTime(mod);
					
					if (world.getGameRules().getBoolean(GameRules.RULE_WEATHER_CYCLE) && world.isRaining()) {
						world.getServer().overworld().resetWeatherCycle();
					}
				}
			}
		});
		
		
		if (CONFIG.registerStarryPortal) {
			setupPortals();
		}
		
		LOGGER.info("Finished loading.");
	}
	
	public static void setupPortals() {
		StarrySkies.LOGGER.info("Setting up Portal to Starry Skies...");
		
		ResourceLocation portalFrameBlockIdentifier = ResourceLocation.tryParse(StarrySkies.CONFIG.starrySkyPortalFrameBlock.toLowerCase());
		Block portalFrameBlock = BuiltInRegistries.BLOCK.getValue(portalFrameBlockIdentifier);
		
		PortalLink portalLink = new PortalLink(portalFrameBlockIdentifier, StarryDimensionKeys.STARRY_SKIES_DIMENSION_ID, StarrySkies.CONFIG.starrySkyPortalColor);
		CustomPortalApiRegistry.addPortal(portalFrameBlock, portalLink);
	}
	
}
