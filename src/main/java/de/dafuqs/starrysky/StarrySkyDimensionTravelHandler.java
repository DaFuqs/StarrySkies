package de.dafuqs.starrysky;

import de.dafuqs.starrysky.dimension.StarrySkyDimension;
import de.dafuqs.starrysky.mixin.EntityAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.PortalUtil;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.dimension.AreaHelper;
import net.minecraft.world.dimension.DimensionType;

import java.util.Optional;

import static org.apache.logging.log4j.Level.ERROR;

public class StarrySkyDimensionTravelHandler {

    public static final BlockPos END_SPAWN_BLOCK_POS = new BlockPos(10, 65, 0);
    public static final BlockPos OVERWORLD_SPAWN_BLOCK_POS = new BlockPos(16, 85, 16);

    public static ServerWorld modifyNetherPortalDestination(Entity thisEntity, ServerWorld serverWorld) {
        if(thisEntity.getEntityWorld().getRegistryKey().equals(StarrySkyDimension.STARRY_SKY_WORLD_KEY)) {
            return StarrySkyCommon.starryWorldNether;
        } else if(thisEntity.getEntityWorld().getRegistryKey().equals(StarrySkyDimension.STARRY_SKY_NETHER_WORLD_KEY)) {
            return StarrySkyCommon.starryWorld;
        }
        return serverWorld;
    }

    public static boolean handleEndPortalCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (world instanceof ServerWorld
                && !entity.hasVehicle()
                && !entity.hasPassengers()
                && entity.canUsePortals()
                && VoxelShapes.matchesAnywhere(VoxelShapes.cuboid(entity.getBoundingBox().offset((-pos.getX()), (-pos.getY()), (-pos.getZ()))), state.getOutlineShape(world, pos), BooleanBiFunction.AND)) {

            RegistryKey<World> sourceRegistryKey = world.getRegistryKey();
            RegistryKey<World> destinationRegistryKey;
            if (StarrySkyDimension.STARRY_SKY_END_WORLD_KEY.equals(sourceRegistryKey)) {
                destinationRegistryKey = StarrySkyDimension.STARRY_SKY_WORLD_KEY;
            } else if (StarrySkyDimension.STARRY_SKY_WORLD_KEY.equals(sourceRegistryKey)) {
                destinationRegistryKey = StarrySkyDimension.STARRY_SKY_END_WORLD_KEY;
            } else {
                // vanilla dimension travel.
                // handle the vanilla way
                return false;
            }

            ServerWorld destinationServerWorld = ((ServerWorld)world).getServer().getWorld(destinationRegistryKey);
            if (destinationRegistryKey == null) {
                // world not loaded.
                return false;
            }

            entity.moveToWorld(destinationServerWorld);
            return true;
        } else {
            // collision box doesn't collide enough
            // since that's what vanilla would check, too we can
            // cancel it to save on calculation time and prevent errors
            return true;
        }
    }

    // Handler for Entity.getTeleportTarget
    // returning null means letting vanilla handling it the default way
    public static TeleportTarget handleGetTeleportTarget(Entity thisEntity, ServerWorld destination) {
        boolean isTravellingToStarryEnd = thisEntity.world.getRegistryKey() == StarrySkyDimension.STARRY_SKY_WORLD_KEY && destination.getRegistryKey() == StarrySkyDimension.STARRY_SKY_END_WORLD_KEY;
        boolean isTravellingBackFromStarryEnd = thisEntity.world.getRegistryKey() == StarrySkyDimension.STARRY_SKY_END_WORLD_KEY && destination.getRegistryKey() == StarrySkyDimension.STARRY_SKY_WORLD_KEY;
        if (!isTravellingBackFromStarryEnd && !isTravellingToStarryEnd) {
            boolean isTravellingToStarryNether = thisEntity.world.getRegistryKey() == StarrySkyDimension.STARRY_SKY_WORLD_KEY && destination.getRegistryKey() == StarrySkyDimension.STARRY_SKY_NETHER_WORLD_KEY;
            boolean isTravellingBackFromStarryNether = thisEntity.world.getRegistryKey() == StarrySkyDimension.STARRY_SKY_NETHER_WORLD_KEY && destination.getRegistryKey() == StarrySkyDimension.STARRY_SKY_WORLD_KEY;

            if (!isTravellingToStarryNether && !isTravellingBackFromStarryNether) {
                // HANDLE VANILLA DIMENSIONS NORMALLY
                return null;
            } else {
                WorldBorder worldBorder = destination.getWorldBorder();
                double d = Math.max(-2.9999872E7D, worldBorder.getBoundWest() + 16.0D);
                double e = Math.max(-2.9999872E7D, worldBorder.getBoundNorth() + 16.0D);
                double f = Math.min(2.9999872E7D, worldBorder.getBoundEast() - 16.0D);
                double g = Math.min(2.9999872E7D, worldBorder.getBoundSouth() - 16.0D);

                // h = dimensionScale modifier. 0.125 for overworld => nether
                double h = DimensionType.getCoordinateScaleFactor(thisEntity.world.getDimension(), destination.getDimension());
                BlockPos blockPos3 = new BlockPos(MathHelper.clamp(thisEntity.getX() * h, d, f), thisEntity.getY(), MathHelper.clamp(thisEntity.getZ() * h, e, g));

                // no teleport / vanilla-checks.
                // Non-Player entities won't create new nether portals
                // and none do already exist
                Optional<TeleportTarget> a = destination.getPortalForcer().getPortalRect(blockPos3, isTravellingToStarryNether).map((arg) -> {
                    BlockPos lastNetherPortalPosition = ((EntityAccessor) thisEntity).getLastNetherPortalPosition();
                    BlockState blockState = thisEntity.world.getBlockState(lastNetherPortalPosition);

                    Direction.Axis axis2;
                    Vec3d vec3d2;
                    if (blockState.contains(Properties.HORIZONTAL_AXIS)) {
                        axis2 = blockState.get(Properties.HORIZONTAL_AXIS);
                        PortalUtil.Rectangle rectangle = PortalUtil.getLargestRectangle(lastNetherPortalPosition, axis2, 21, Direction.Axis.Y, 21, (blockPos) -> {
                            return thisEntity.getEntityWorld().getBlockState(blockPos) == blockState;
                        });
                        vec3d2 = AreaHelper.entityPosInPortal(rectangle, axis2, thisEntity.getPos(), thisEntity.getDimensions(thisEntity.getPose()));
                    } else {
                        axis2 = Direction.Axis.X;
                        vec3d2 = new Vec3d(0.5D, 0.0D, 0.0D);
                    }

                    return AreaHelper.getNetherTeleportTarget(destination, arg, axis2, vec3d2, thisEntity.getDimensions(thisEntity.getPose()), thisEntity.getVelocity(), thisEntity.getYaw(), thisEntity.getPitch());
                });

                if(a.isPresent()) {
                    // existing portal found => teleport
                    return a.get();
                // no portal exists => generate one, if player
                } else if(thisEntity instanceof ServerPlayerEntity) {
                    Direction.Axis axis = thisEntity.world.getBlockState(((EntityAccessor) thisEntity).getLastNetherPortalPosition()).getOrEmpty(NetherPortalBlock.AXIS).orElse(Direction.Axis.X);
                    Optional<PortalUtil.Rectangle> optional2 = destination.getPortalForcer().createPortal(blockPos3, axis);
                    if (optional2.isEmpty()) {
                        StarrySkyCommon.log(ERROR, "Unable to create a portal, likely target out of world border");
                    } else {
                        BlockPos targetPos = optional2.get().lowerLeft;
                        return new TeleportTarget(new Vec3d(targetPos.getX() + 0.5D, targetPos.getY(), targetPos.getZ() + 0.5D), thisEntity.getVelocity(), thisEntity.getYaw(), thisEntity.getPitch());
                    }
                } else {
                    // no portal exists => no teleport
                    // that's not nice here, but eh
                    return new TeleportTarget(null, null, 0, 0);
                }
            }
        } else {
            // OVERWORLD <=> END
            BlockPos blockPos2;
            if (isTravellingToStarryEnd) {
                blockPos2 = END_SPAWN_BLOCK_POS;
            } else {
                // vanilla code: (but only the overworld has a spawn point so the vanilla way won't work and we need a hard coded point)
                //blockPos2 = destination.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, destination.getSpawnPos());
                blockPos2 = OVERWORLD_SPAWN_BLOCK_POS;
            }
            return new TeleportTarget(new Vec3d((double) blockPos2.getX() + 0.5D, blockPos2.getY(), (double) blockPos2.getZ() + 0.5D), thisEntity.getVelocity(), thisEntity.getYaw(), thisEntity.getPitch());
        }
        // vanilla dimensions => let vanilla handle it
        return null;
    }

}
