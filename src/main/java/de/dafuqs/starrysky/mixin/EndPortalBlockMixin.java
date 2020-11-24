/*package de.dafuqs.starrysky.mixin;

import de.dafuqs.starrysky.StarrySkyCommon;
import net.minecraft.block.BlockState;
import net.minecraft.block.EndPortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;*/

/**
 * In the end:
 * return to Starry Sky instead of Overworld
 */
/*@Mixin(EndPortalBlock.class)
public abstract class EndPortalBlockMixin {

    @Inject(method = "onEntityCollision", at = @At("HEAD"), cancellable = true)
    void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo info) {
        if (world instanceof ServerWorld
                && !entity.hasVehicle()
                && !entity.hasPassengers()
                && entity.canUsePortals()
                && VoxelShapes.matchesAnywhere(VoxelShapes.cuboid(entity.getBoundingBox().offset((-pos.getX()), (-pos.getY()), (-pos.getZ()))), state.getOutlineShape(world, pos), BooleanBiFunction.AND)) {

            RegistryKey<World> currentWorldRegistryKey = world.getRegistryKey();
            RegistryKey<World> targetWorldRegistryKey = null;
            if(currentWorldRegistryKey == World.END) {
                targetWorldRegistryKey = StarrySkyCommon.starryWorld.getRegistryKey();
            }

            if(targetWorldRegistryKey != null) {
                ServerWorld serverWorld = ((ServerWorld)world).getServer().getWorld(targetWorldRegistryKey);
                if(serverWorld != null) {
                    entity.moveToWorld(serverWorld);
                    info.cancel();
                }
            }
        }

    }

}*/