package de.dafuqs.starryskies;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.advancements.*;
import de.dafuqs.starryskies.commands.*;
import de.dafuqs.starryskies.configs.*;
import de.dafuqs.starryskies.data_loaders.*;
import de.dafuqs.starryskies.portal.*;
import de.dafuqs.starryskies.registries.*;
import de.dafuqs.starryskies.state_providers.*;
import de.dafuqs.starryskies.worldgen.*;
import de.dafuqs.starryskies.worldgen.dimension.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.commands.synchronization.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.server.*;
import net.minecraft.server.level.*;
import net.minecraft.server.players.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.chunk.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.*;
import net.neoforged.fml.common.*;
import net.neoforged.fml.config.*;
import net.neoforged.neoforge.client.gui.*;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.event.*;
import net.neoforged.neoforge.event.level.*;
import net.neoforged.neoforge.event.server.*;
import net.neoforged.neoforge.event.tick.*;
import net.neoforged.neoforge.registries.*;
import org.slf4j.*;

import java.util.*;
import java.util.function.*;

@Mod(value = StarrySkies.MOD_ID)
@EventBusSubscriber(modid = StarrySkies.MOD_ID)
public class StarrySkies {
	
	public static final String MOD_ID = "starry_skies";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Identifier id(String name) {
		return Identifier.fromNamespaceAndPath(MOD_ID, name);
	}

	public static boolean isStarryWorld(ServerLevel world) {
		ChunkGenerator chunkGenerator = world.getChunkSource().getGenerator();
		return chunkGenerator instanceof StarrySkyChunkGenerator;
	}

	private static final DeferredRegister<ArgumentTypeInfo<?, ?>> COMMAND_ARGUMENT_REGISTRAR = DeferredRegister.create(Registries.COMMAND_ARGUMENT_TYPE, StarrySkies.MOD_ID);
	private static final DeferredRegister<MapCodec<? extends ChunkGenerator>> CHUNK_GENERATOR_REGISTRAR = DeferredRegister.create(Registries.CHUNK_GENERATOR, StarrySkies.MOD_ID);

	public StarrySkies(IEventBus modBus, ModContainer modContainer) {
		LOGGER.info("Starting up...");
		modContainer.registerConfig(ModConfig.Type.COMMON, StarrySkyConfig.CONFIG_SPEC);
		modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);

		NeoForge.EVENT_BUS.addListener((Consumer<AddServerReloadListenersEvent>) event -> {
			event.addListener(UniqueBlockGroupDataLoader.ID, UniqueBlockGroupDataLoader.INSTANCE);
			event.addListener(WeightedBlockGroupDataLoader.ID, WeightedBlockGroupDataLoader.INSTANCE);
		});
		
		// Register all the stuff
		CHUNK_GENERATOR_REGISTRAR.register("starry_skies", () -> StarrySkyChunkGenerator.CODEC);
		CHUNK_GENERATOR_REGISTRAR.register(modBus);

		SingletonArgumentInfo<ConfiguredSphereArgumentType> singleton = SingletonArgumentInfo.contextAware(ConfiguredSphereArgumentType::configuredSphere);
		ArgumentTypeInfos.registerByClass(ConfiguredSphereArgumentType.class, singleton);
		COMMAND_ARGUMENT_REGISTRAR.register("starry_skies_configured_sphere", () -> singleton);
		COMMAND_ARGUMENT_REGISTRAR.register(modBus);

		modBus.addListener(StarryRegistries::registerRegistries);
		modBus.addListener(StarryRegistries::registerDynamicRegistries);
		StarryStateProviders.register(modBus);
		Spheres.register(modBus);
		StarryBlocks.register(modBus);
		StarryFeatures.register(modBus);
		SphereDecorators.register(modBus);
		StarryAdvancementCriteria.register(modBus);
		
		LOGGER.info("Finished loading.");
	}

	private final static int ADVANCEMENT_CHECK_TICKS = 100;
	private static int tickCounter;

	@SubscribeEvent
	public static void serverTick(ServerTickEvent.Post event) {
		tickCounter++;
		if (tickCounter % ADVANCEMENT_CHECK_TICKS == 0) {
			tickCounter = 0;
			PlayerList playerList = event.getServer().getPlayerList();
			// StarrySkies.LOGGER.debug("Advancement check start. Players: {}", playerList.getPlayerCount());
			for (ServerPlayer serverPlayerEntity : playerList.getPlayers()) {
				// StarrySkies.LOGGER.debug("Checking player {}", serverPlayerEntity.getName());
				if (StarrySkies.isStarryWorld(serverPlayerEntity.level())) {
					// StarrySkies.LOGGER.debug("In starry world");
					Optional<Support.SphereDistance> distance = Support.getClosestSphere(serverPlayerEntity.level(), serverPlayerEntity.blockPosition());
					if (distance.isPresent() && (Math.sqrt(distance.get().squaredDistance)) < distance.get().sphere.getRadius() + 2) {
						PlacedSphere<?> sphere = distance.get().sphere;
						StarryAdvancementCriteria.SPHERE_DISCOVERED.get().trigger(serverPlayerEntity, sphere);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void explosion(ExplosionEvent.Detonate event) {
		if (StarrySkyConfig.CONFIG.enableStarryPortal.get() && event.getExplosion().canTriggerBlocks()) { // like wind charges
			Level level = event.getLevel();
			BlockPos pos = BlockPos.containing(event.getExplosion().center());
			if (inPortalDimension(level)) {
				Optional<StarryPortalShape> optionalShape = StarryPortalShape.findEmptyPortalShape(level, pos, Direction.Axis.X);
				if (optionalShape.isPresent()) {
					optionalShape.get().createPortalBlocks(level);
				}
			}
		}
	}

	private static boolean inPortalDimension(Level level) {
		return level.dimension() == Level.OVERWORLD || level.dimension() == StarryDimensionKeys.OVERWORLD_KEY;
	}

	@SubscribeEvent
	public static void register(ServerStartingEvent event) {
		StarryPoiTypes.bootstrap(BuiltInRegistries.POINT_OF_INTEREST_TYPE);

		// Build a final map of sphere generation data for each chunk generator
		MinecraftServer server = event.getServer();
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
	}

	@SubscribeEvent
	public static void register(RegisterCommandsEvent event) {
		ClosestSphereCommand.register(event.getDispatcher(), event.getBuildContext());
		GenerateSphereCommand.register(event.getDispatcher(), event.getBuildContext());
		SystemStatisticsCommand.register(event.getDispatcher(), event.getBuildContext());
	}

}
