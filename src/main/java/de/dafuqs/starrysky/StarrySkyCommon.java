package de.dafuqs.starrysky;

import de.dafuqs.starrysky.advancements.ProximityAchivementCheckEvent;
import de.dafuqs.starrysky.commands.StarrySkyCommands;
import de.dafuqs.starrysky.config.StarrySkyConfig;
import de.dafuqs.starrysky.dimension.StarrySkyDimension;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.DefaultBiomeCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StarrySkyCommon implements ModInitializer {

    public static final String MOD_ID = "starry_sky";
    public static final String STARRY_SKY_DIMENSION_ID = "starry_sky";
    public static final Identifier MOD_DIMENSION_ID = new Identifier(StarrySkyCommon.MOD_ID, StarrySkyCommon.STARRY_SKY_DIMENSION_ID);

    public static StarrySkyConfig STARRY_SKY_CONFIG;
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static ServerWorld starryWorld;

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        LOGGER.info("Hello Fabric world! This will be fun. Or not. We'll see.");

        //Set up config
        LOGGER.info("Loading config file...");
        AutoConfig.register(StarrySkyConfig.class, JanksonConfigSerializer::new);
        STARRY_SKY_CONFIG = AutoConfig.getConfigHolder(StarrySkyConfig.class).getConfig();
        LOGGER.info("Finished loading config file.");

        // Register all the stuff
        StarrySkyDimension.setupDimension();
        StarrySkyDimension.setupPortal();
        StarrySkyCommands.initialize();

        // triggers everytime a world is loaded
        // so for overworld, nether, ... (they all share the same seed)
        ServerWorldEvents.LOAD.register((server, world) -> {
            if(world.getRegistryKey().equals(StarrySkyDimension.STARRY_SKY_WORLD_KEY)) {
                StarrySkyCommon.starryWorld = world;

                // make sure the spawn is safe => wood planet
                world.setSpawnPos(new BlockPos(16, 90, 16), 0);
            }
        });

        ServerTickEvents.END_SERVER_TICK.register(new ProximityAchivementCheckEvent());

    }

    public static void reserveBiomeIDs() {
        //Reserve the biome IDs for the json version to replace
        Registry.register(BuiltinRegistries.BIOME, new Identifier(StarrySkyCommon.MOD_ID, "starry_sky_biome"), DefaultBiomeCreator.createTheVoid());
    }

}
