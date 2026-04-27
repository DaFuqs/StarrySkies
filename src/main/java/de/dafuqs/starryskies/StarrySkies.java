package de.dafuqs.starryskies;

import de.dafuqs.starryskies.advancements.ProximityAdvancementCheckEvent;
import de.dafuqs.starryskies.advancements.StarryAdvancementCriteria;
import de.dafuqs.starryskies.commands.ClosestSphereCommand;
import de.dafuqs.starryskies.commands.ConfiguredSphereArgumentType;
import de.dafuqs.starryskies.commands.GenerateSphereCommand;
import de.dafuqs.starryskies.configs.StarrySkyConfig;
import de.dafuqs.starryskies.data_loaders.UniqueBlockGroupDataLoader;
import de.dafuqs.starryskies.data_loaders.WeightedBlockGroupDataLoader;
import de.dafuqs.starryskies.registries.StarryDimensionKeys;
import de.dafuqs.starryskies.registries.StarryRegistries;
import de.dafuqs.starryskies.registries.StarryRegistryKeys;
import de.dafuqs.starryskies.state_providers.StarryStateProviders;
import de.dafuqs.starryskies.worldgen.*;
import de.dafuqs.starryskies.worldgen.dimension.StarrySkyChunkGenerator;
import de.dafuqs.starryskies.worldgen.dimension.SystemGenerator;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Mod(value = StarrySkies.MOD_ID)
public class StarrySkies {
	
	public static final String MOD_ID = "starry_skies";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static StarrySkyConfig CONFIG;
	
	public static Identifier id(String name) {
		return Identifier.fromNamespaceAndPath(MOD_ID, name);
	}
	
	public static String idPlain(String name) {
		return id(name).toString();
	}
	
	public static boolean isStarryWorld(ServerLevel world) {
		ChunkGenerator chunkGenerator = world.getChunkSource().getGenerator();
		return chunkGenerator instanceof StarrySkyChunkGenerator;
	}

	public StarrySkies(IEventBus modBus, ModContainer modContainer) {
		LOGGER.info("Starting up...");
		modContainer.registerConfig(ModConfig.Type.COMMON, StarrySkyConfig.CONFIG_SPEC);
		modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);

		ResourceLoader.get(PackType.SERVER_DATA).registerReloadListener(  UniqueBlockGroupDataLoader.ID,   UniqueBlockGroupDataLoader.INSTANCE);
		ResourceLoader.get(PackType.SERVER_DATA).registerReloadListener(WeightedBlockGroupDataLoader.ID, WeightedBlockGroupDataLoader.INSTANCE);
		
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


		if (CONFIG.registerStarryPortal.get()) {
			setupPortals();
		}
		
		LOGGER.info("Finished loading.");
	}
	
	public static void setupPortals() {
		StarrySkies.LOGGER.info("Setting up Portal to Starry Skies...");

		Identifier portalFrameBlockIdentifier = Identifier.tryParse(StarrySkies.CONFIG.starrySkiesPortalFrameBlock.get().toLowerCase());
		Block portalFrameBlock = BuiltInRegistries.BLOCK.getValue(portalFrameBlockIdentifier);

		PortalLink portalLink = new PortalLink(portalFrameBlockIdentifier, StarryDimensionKeys.STARRY_SKIES_DIMENSION_ID, StarrySkies.CONFIG.starrySkiesPortalColor);
		CustomPortalApiRegistry.addPortal(portalFrameBlock, portalLink);
	}
	
}
