package de.dafuqs.starrysky.dimension;

import de.dafuqs.starrysky.mixin.ServerPlayerEntityAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;

public class StarrySkyPortalHandler {

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

                if(entity instanceof ServerPlayerEntity) {
                    ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) entity;
                    entity.detach();
                    serverPlayerEntity.getServerWorld().removePlayer(serverPlayerEntity);
                    if (!serverPlayerEntity.notInAnyWorld) {
                        serverPlayerEntity.notInAnyWorld = true;

                        boolean seenCredits = ((ServerPlayerEntityAccessor) serverPlayerEntity).getSeenCredits();

                        if(seenCredits) {
                            serverPlayerEntity.networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.GAME_WON, 0.0F));
                        } else {
                            serverPlayerEntity.networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.GAME_WON, 1.0F));
                            ((ServerPlayerEntityAccessor) serverPlayerEntity).setSeenCredits(true);
                        }
                    }
                }
            } else if (StarrySkyDimension.STARRY_SKY_WORLD_KEY.equals(sourceRegistryKey)) {
                destinationRegistryKey = StarrySkyDimension.STARRY_SKY_END_WORLD_KEY;
            } else {
                return false;
            }

            ServerWorld destinationServerWorld = ((ServerWorld)world).getServer().getWorld(destinationRegistryKey);
            if (destinationRegistryKey == null) {
                return false;
            }

            entity.moveToWorld(destinationServerWorld);
            return true;
        } else {
            return false;
        }
    }

}
