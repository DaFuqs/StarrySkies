package de.dafuqs.starryskies;

import de.dafuqs.starryskies.advancements.ProximityAdvancementCheckEvent;
import de.dafuqs.starryskies.advancements.StarryAdvancementCriteria;
import de.dafuqs.starryskies.commands.ClosestSpheroidCommand;
import de.dafuqs.starryskies.configs.StarrySkyConfig;
import de.dafuqs.starryskies.data_loaders.*;
import de.dafuqs.starryskies.dimension.SpheroidDimensionType;
import de.dafuqs.starryskies.dimension.StarrySkyBiomes;
import de.dafuqs.starryskies.dimension.StarrySkyChunkGenerator;
import de.dafuqs.starryskies.dimension.StarrySkyDimension;
import de.dafuqs.starryskies.spheroids.DecoratorFeatures;
import de.dafuqs.starryskies.spheroids.SpheroidDecorators;
import de.dafuqs.starryskies.spheroids.SpheroidTypes;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.apache.logging.log4j.Level.INFO;

public class StarrySkies implements ModInitializer {
	
	public static final String MOD_ID = "starry_skies";
	
	public static StarrySkyConfig CONFIG;
	private static final Logger LOGGER = LogManager.getLogger();
	
	public static ServerWorld starryWorld;
	public static ServerWorld starryWorldNether;
	public static ServerWorld starryWorldEnd;
	
	@Override
	public void onInitialize() {

		//Set up config
		log(INFO, "Starting up...");
		AutoConfig.register(StarrySkyConfig.class, JanksonConfigSerializer::new);
		CONFIG = AutoConfig.getConfigHolder(StarrySkyConfig.class).getConfig();
		
		// Register all the stuff
		StarrySkies.log(INFO, "Registering all the worldgen stuff...");
		Registry.register(Registry.CHUNK_GENERATOR, new Identifier(MOD_ID, "starry_skies_chunk_generator"), StarrySkyChunkGenerator.CODEC);
		StarrySkyBiomes.initialize();
		StarrySkyDimension.setupPortals();
		DecoratorFeatures.initialize();
		StarryAdvancementCriteria.register();
		
		SpheroidTypes.initialize();
		SpheroidDecorators.initialize();
		
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(UniqueBlockGroupsLoader.INSTANCE);
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(WeightedBlockGroupsLoader.INSTANCE);
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(SpheroidDistributionLoader.INSTANCE);
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(SpheroidTemplateLoader.INSTANCE);
		
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			ClosestSpheroidCommand.register(dispatcher);
		});
		
		// triggers everytime a world is loaded
		// so for overworld, nether, ... (they all share the same seed)
		ServerWorldEvents.LOAD.register((server, world) -> {
			if (world.getRegistryKey().equals(StarrySkyDimension.OVERWORLD_KEY)) {
				StarrySkies.starryWorld = world;
			} else if (world.getRegistryKey().equals(StarrySkyDimension.NETHER_KEY)) {
				StarrySkies.starryWorldNether = world;
			} else if (world.getRegistryKey().equals(StarrySkyDimension.END_KEY)) {
				StarrySkies.starryWorldEnd = world;
			}
		});
		
		ServerTickEvents.END_SERVER_TICK.register(new ProximityAdvancementCheckEvent());
		
		log(INFO, "Finished loading.");
	}
	
	public static Identifier locate(String name) {
		return new Identifier(MOD_ID, name);
	}
	
	public static void log(Level logLevel, String message) {
		LOGGER.log(logLevel, "[StarrySkies] " + message);
	}
	
	public static boolean inStarryWorld(ServerPlayerEntity serverPlayerEntity) {
		RegistryKey<World> worldRegistryKey = serverPlayerEntity.getEntityWorld().getRegistryKey();
		return isStarryWorld(worldRegistryKey);
	}
	
	
	public static ServerWorld getStarryWorld(SpheroidDimensionType dimensionType) {
		switch (dimensionType) {
			case OVERWORLD -> {
				return starryWorld;
			}
			case NETHER -> {
				return starryWorldNether;
			}
			default -> {
				return starryWorldEnd;
			}
		}
	}
	
	public static boolean isStarryWorld(RegistryKey<World> worldRegistryKey) {
		if (StarrySkies.starryWorld == null || StarrySkies.starryWorldNether == null || StarrySkies.starryWorldEnd == null) {
			log(Level.ERROR, "The Starry Dimensions could not be loaded. If this is your first launch this is probably related to a known vanilla bug where custom dimensions are not loaded when first generating the world. Restarting / quitting and reloading will fix this issue.");
			return false;
		} else {
			return worldRegistryKey.equals(StarrySkies.starryWorld.getRegistryKey())
					|| worldRegistryKey.equals(starryWorldNether.getRegistryKey())
					|| worldRegistryKey.equals(starryWorldEnd.getRegistryKey());
			
		}
	}
	
}
