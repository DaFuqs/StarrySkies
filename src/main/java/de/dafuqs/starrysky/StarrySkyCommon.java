package de.dafuqs.starrysky;

import de.dafuqs.starrysky.advancements.ProximityAdvancementCheckEvent;
import de.dafuqs.starrysky.commands.StarrySkyCommands;
import de.dafuqs.starrysky.configs.StarrySkyConfig;
import de.dafuqs.starrysky.dimension.DecoratorFeatures;
import de.dafuqs.starrysky.dimension.StarrySkyDimension;
import de.dafuqs.starrysky.mixin.EntityAccessor;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import me.shedaniel.cloth.api.common.events.v1.PlayerChangeWorldCallback;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.block.BlockState;
import net.minecraft.class_5459;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.dimension.AreaHelper;
import net.minecraft.world.dimension.DimensionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class StarrySkyCommon implements ModInitializer {

    public static final String MOD_ID = "starry_sky";

    public static StarrySkyConfig STARRY_SKY_CONFIG;
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static ServerWorld starryWorld;
    public static ServerWorld starryWorldNether;
    public static ServerWorld starryWorldEnd;

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.

        //Set up config
        LOGGER.info("[StarrySky] Starting up...");
        AutoConfig.register(StarrySkyConfig.class, JanksonConfigSerializer::new);
        STARRY_SKY_CONFIG = AutoConfig.getConfigHolder(StarrySkyConfig.class).getConfig();

        // Register all the stuff
        StarrySkyDimension.setupDimension();
        StarrySkyDimension.setupPortals();
        StarrySkyCommands.initialize();
        DecoratorFeatures.initialize();

        // triggers everytime a world is loaded
        // so for overworld, nether, ... (they all share the same seed)
        ServerWorldEvents.LOAD.register((server, world) -> {
            if(world.getRegistryKey().equals(StarrySkyDimension.STARRY_SKY_WORLD_KEY)) {
                StarrySkyCommon.starryWorld = world;
            } else if(world.getRegistryKey().equals(StarrySkyDimension.STARRY_SKY_NETHER_WORLD_KEY)) {
                StarrySkyCommon.starryWorldNether = world;
            } else if(world.getRegistryKey().equals(StarrySkyDimension.STARRY_SKY_END_WORLD_KEY)) {
                StarrySkyCommon.starryWorldEnd = world;
            }
        });

        ServerTickEvents.END_SERVER_TICK.register(new ProximityAdvancementCheckEvent());

        PlayerChangeWorldCallback.EVENT.register(new PlayerChangeWorldCallback() {
            @Override
            public void onChangeWorld(ServerPlayerEntity serverPlayerEntity, RegistryKey<World> registryKey, RegistryKey<World> registryKey1) {

            }
        });

        LOGGER.info("[StarrySky] Finished loading.");
    }

    public static boolean inStarryWorld(ServerPlayerEntity serverPlayerEntity) {
        return serverPlayerEntity.getEntityWorld().equals(StarrySkyCommon.starryWorld) || serverPlayerEntity.getEntityWorld().equals(starryWorldNether) || serverPlayerEntity.getEntityWorld().equals(starryWorldEnd);
    }


    ///////////////////////////

    private static final BlockPos END_SPAWN_BLOCK_POS = new BlockPos(10, 64, 0);
    private static final BlockPos OVERWORLD_SPAWN_BLOCK_POS = new BlockPos(16, 80, 16);

    public static TeleportTarget handleGetTeleportTarget(Entity entity, ServerWorld destination) {
        //return new TeleportTarget(new Vec3d(0.5D, 100, 0.5D), new Vec3d(0, 0, 0), 0, 0);

        Entity thisEntity = (Entity) (Object) entity;

        boolean bl = thisEntity.world.getRegistryKey() == StarrySkyDimension.STARRY_SKY_END_WORLD_KEY && destination.getRegistryKey() == StarrySkyDimension.STARRY_SKY_WORLD_KEY;
        boolean bl2 = destination.getRegistryKey() == StarrySkyDimension.STARRY_SKY_END_WORLD_KEY;
        if (!bl && !bl2) {
            boolean bl3 = destination.getRegistryKey() == StarrySkyDimension.STARRY_SKY_NETHER_WORLD_KEY;
            if (thisEntity.world.getRegistryKey() != StarrySkyDimension.STARRY_SKY_NETHER_WORLD_KEY && !bl3) {
                return null;
            } else {
                WorldBorder worldBorder = destination.getWorldBorder();
                double d = Math.max(-2.9999872E7D, worldBorder.getBoundWest() + 16.0D);
                double e = Math.max(-2.9999872E7D, worldBorder.getBoundNorth() + 16.0D);
                double f = Math.min(2.9999872E7D, worldBorder.getBoundEast() - 16.0D);
                double g = Math.min(2.9999872E7D, worldBorder.getBoundSouth() - 16.0D);
                double h = DimensionType.method_31109(thisEntity.world.getDimension(), destination.getDimension());
                BlockPos blockPos3 = new BlockPos(MathHelper.clamp(thisEntity.getX() * h, d, f), thisEntity.getY(), MathHelper.clamp(thisEntity.getZ() * h, e, g));

                return doMethod_30330(destination, blockPos3, bl3).map((arg) -> {

                    BlockPos lastNetherPortalPosition = ((EntityAccessor) entity).getLastNetherPortalPosition();

                    BlockState blockState = thisEntity.world.getBlockState(lastNetherPortalPosition);
                    Direction.Axis axis2;
                    Vec3d vec3d2;
                    if (blockState.contains(Properties.HORIZONTAL_AXIS)) {
                        axis2 = blockState.get(Properties.HORIZONTAL_AXIS);
                        class_5459.class_5460 lv = class_5459.method_30574(lastNetherPortalPosition, axis2, 21, Direction.Axis.Y, 21, (blockPos) -> {
                            return thisEntity.world.getBlockState(blockPos) == blockState;
                        });
                        vec3d2 = doMethod_30633(thisEntity, axis2, lv);
                    } else {
                        axis2 = Direction.Axis.X;
                        vec3d2 = new Vec3d(0.5D, 0.0D, 0.0D);
                    }

                    return AreaHelper.method_30484(destination, arg, axis2, vec3d2, thisEntity.getDimensions(thisEntity.getPose()), thisEntity.getVelocity(), thisEntity.yaw, thisEntity.pitch);
                }).orElse(null);
            }
        } else {
            BlockPos blockPos2;
            if (bl2) {
                blockPos2 = END_SPAWN_BLOCK_POS;
            } else {
                blockPos2 = OVERWORLD_SPAWN_BLOCK_POS;
                //blockPos2 = destination.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, destination.getSpawnPos());
            }

            return new TeleportTarget(new Vec3d((double) blockPos2.getX() + 0.5D, blockPos2.getY(), (double) blockPos2.getZ() + 0.5D), thisEntity.getVelocity(), thisEntity.yaw, thisEntity.pitch);
        }
    }

    public static ServerWorld doinjected(Entity thisEntity, ServerWorld x) {
        if(thisEntity.getEntityWorld().getRegistryKey().equals(StarrySkyDimension.STARRY_SKY_WORLD_KEY)) {
            return StarrySkyCommon.starryWorldNether;
        } else if(thisEntity.getEntityWorld().getRegistryKey().equals(StarrySkyDimension.STARRY_SKY_NETHER_WORLD_KEY)) {
            return StarrySkyCommon.starryWorld;
        }

        return x;
    }


    private static Vec3d doMethod_30633(Entity thisEntity, Direction.Axis axis, class_5459.class_5460 arg) {
        return AreaHelper.method_30494(arg, axis, thisEntity.getPos(), thisEntity.getDimensions(thisEntity.getPose()));
    }

    private static Optional<class_5459.class_5460> doMethod_30330(ServerWorld serverWorld, BlockPos blockPos, boolean bl) {
        return serverWorld.getPortalForcer().method_30483(blockPos, bl);
    }

}
