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
import net.fabricmc.fabric.api.event.lifecycle.v1.*;
import net.fabricmc.fabric.api.resource.v1.*;
import net.minecraft.commands.synchronization.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.server.packs.*;
import net.minecraft.world.level.chunk.*;
import org.slf4j.*;

import java.util.*;

public class StarrySkies implements ModInitializer {
	
	public static final String MOD_ID = "starry_skies";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static StarrySkyConfig CONFIG;

	public static Identifier id(String name) {
		return Identifier.fromNamespaceAndPath(MOD_ID, name);
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

		ResourceLoader.get(PackType.SERVER_DATA).registerReloadListener(  UniqueBlockGroupDataLoader.ID,   UniqueBlockGroupDataLoader.INSTANCE);
		ResourceLoader.get(PackType.SERVER_DATA).registerReloadListener(WeightedBlockGroupDataLoader.ID, WeightedBlockGroupDataLoader.INSTANCE);
		
		// Register all the stuff
		Registry.register(BuiltInRegistries.CHUNK_GENERATOR, StarrySkies.id("starry_skies"), StarrySkyChunkGenerator.CODEC);
		
		StarryRegistries.register();
		StarryStateProviders.register();
		Spheres.initialize();
		StarryBlocks.register();
		StarryFeatures.initialize();
		SphereDecorators.initialize();
		StarryAdvancementCriteria.register();
		StarryPoiTypes.register();

		ArgumentTypeInfos.register(BuiltInRegistries.COMMAND_ARGUMENT_TYPE, "starry_skies_configured_sphere", ConfiguredSphereArgumentType.class, SingletonArgumentInfo.contextAware(ConfiguredSphereArgumentType::configuredSphere));
		CommandRegistrationCallback.EVENT.register((commandDispatcher, commandRegistryAccess, registrationEnvironment) -> {
			ClosestSphereCommand.register(commandDispatcher, commandRegistryAccess);
			GenerateSphereCommand.register(commandDispatcher, commandRegistryAccess);
			SystemStatisticsCommand.register(commandDispatcher, commandRegistryAccess);
		});
		ServerTickEvents.END_SERVER_TICK.register(new ProximityAdvancementCheckEvent());

		// Build a final map of sphere generation data for each chunk generator
		ServerLifecycleEvents.SERVER_STARTING.register(server -> {
			Registry<GenerationGroup> generationGroupRegistry = server.registryAccess().lookupOrThrow(StarryRegistryKeys.GENERATION_GROUP);
			Registry<SystemGenerator> systemGeneratorRegistry = server.registryAccess().lookupOrThrow(StarryRegistryKeys.SYSTEM_GENERATOR);
			Registry<ConfiguredSphere<?, ?>> configuredSphereRegistry = server.registryAccess().lookupOrThrow(StarryRegistryKeys.CONFIGURED_SPHERE);

			for (GenerationGroup generationGroup : generationGroupRegistry) {
				// cursed generator group id lookup. Using getEntries() does return random order, making worldgen undeterministic :C
				Identifier generationGroupId = generationGroupRegistry.getResourceKey(generationGroup).orElseThrow().identifier();
				Identifier systemGeneratorId = generationGroup.systemGeneratorId();

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

		LOGGER.info("Finished loading.");
	}

}
