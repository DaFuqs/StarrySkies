package de.dafuqs.starrysky;

import de.dafuqs.starrysky.SpheroidData.SpheroidAdvancementGroup;
import de.dafuqs.starrysky.SpheroidData.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.SpheroidData.SpheroidAdvancementIdentifierGroups;
import de.dafuqs.starrysky.SpheroidLists.SpheroidLoader;
import de.dafuqs.starrysky.commands.StarrySkyCommands;
import de.dafuqs.starrysky.configs.StarrySkyConfig;
import de.dafuqs.starrysky.dimension.StarrySkyDimension;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.server.network.ServerPlayerEntity;
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
    public static SpheroidLoader spheroidLoader;

    // Advancements
    private int tickCounter;
    private final int advancementsEveryXTicks = 100;
    private SpheroidAdvancementIdentifierGroups spheroidAdvancementIdentifierGroups;

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

        spheroidAdvancementIdentifierGroups = new SpheroidAdvancementIdentifierGroups();
        spheroidLoader = new SpheroidLoader();



        // triggers everytime a world is loaded
        // so for overworld, nether, ... (they all share the same seed)
        ServerWorldEvents.LOAD.register((server, world) -> {
            if(world.getRegistryKey().equals(StarrySkyDimension.STARRY_SKY_WORLD_KEY)) {
                StarrySkyCommon.starryWorld = world;

                // make sure the spawn is safe => wood planet
                world.setSpawnPos(new BlockPos(16, 90, 16), 0);
            }
        });

        ServerTickEvents.START_WORLD_TICK.register((server) -> {
            tickCounter++;
            if(tickCounter % advancementsEveryXTicks == 0) {
                tickCounter = 0;
                for (ServerPlayerEntity serverPlayerEntity : server.getPlayers()) {
                    if(serverPlayerEntity.getServerWorld().equals(starryWorld)) {
                        Support.SpheroidDistance spheroidDistance = Support.getClosestSpheroidToPlayer(serverPlayerEntity);
                        if(spheroidDistance.spheroid != null && (Math.sqrt(spheroidDistance.distance)) < spheroidDistance.spheroid.getRadius()) {
                            SpheroidAdvancementIdentifier spheroidAdvancementIdentifier = spheroidDistance.spheroid.getSpheroidType().getSpheroidTypeIdentifier();

                            if(spheroidAdvancementIdentifier != null) {
                                SpheroidAdvancementGroup spheroidAdvancementGroup = spheroidAdvancementIdentifierGroups.spheroidAdvancementIdentifierGroups.get(spheroidAdvancementIdentifier);

                                String groupAdvancementString = "sphere_group_" + spheroidAdvancementGroup.name().toLowerCase();
                                String identifierAdvancementString = "sphere_" + spheroidAdvancementIdentifier.name().toLowerCase();

                                ServerAdvancementLoader sal = server.getServer().getAdvancementLoader();
                                PlayerAdvancementTracker tracker = serverPlayerEntity.getAdvancementTracker();

                                // grant group advancement
                                Identifier advancementIdentifier = new Identifier(StarrySkyCommon.MOD_ID, groupAdvancementString);
                                Advancement advancement = sal.get(advancementIdentifier);
                                if (advancement != null) {
                                    tracker.grantCriterion(advancement, "seen");
                                }

                                // grant identifier advancement
                                advancementIdentifier = new Identifier(StarrySkyCommon.MOD_ID, identifierAdvancementString);
                                advancement = sal.get(advancementIdentifier);
                                if (advancement != null) {
                                    tracker.grantCriterion(advancement, "seen");
                                }
                            }
                        }
                    }
                }
            }

        });
    }

    public static void reserveBiomeIDs() {
        //Reserve the biome IDs for the json version to replace
        Registry.register(BuiltinRegistries.BIOME, new Identifier(StarrySkyCommon.MOD_ID, "starry_sky_biome"), DefaultBiomeCreator.createTheVoid());
    }

}
