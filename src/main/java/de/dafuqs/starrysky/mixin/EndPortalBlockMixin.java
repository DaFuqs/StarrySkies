package de.dafuqs.starrysky.mixin;

import de.dafuqs.starrysky.dimension.StarrySkyPortalHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.EndPortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(EndPortalBlock.class)
public abstract class EndPortalBlockMixin {

    @Inject(at = @At("HEAD"), method = "onEntityCollision", cancellable = true)
    void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo callbackInfo) {
        boolean handled = StarrySkyPortalHandler.handleEndPortalCollision(state, world, pos, entity);
        if(handled) {
            callbackInfo.cancel();
        }
    }

}