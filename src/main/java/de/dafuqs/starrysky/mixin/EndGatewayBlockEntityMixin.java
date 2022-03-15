package de.dafuqs.starrysky.mixin;

import de.dafuqs.starrysky.dimension.StarrySkyDimension;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.EndGatewayFeatureConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndGatewayBlockEntity.class)
public abstract class EndGatewayBlockEntityMixin {
	
	@Inject(at = @At("HEAD"), method = "tryTeleportingEntity(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/Entity;Lnet/minecraft/block/entity/EndGatewayBlockEntity;)V")
	private static void tryTeleportingEntity(World world, BlockPos pos, BlockState state, Entity entity, EndGatewayBlockEntity blockEntity, CallbackInfo ci) {
		BlockPos blockPos;
		if (world instanceof ServerWorld serverWorld){
			if (((EndGatewayBlockEntityAccessor) blockEntity).getExitPortalPos() == null && world.getRegistryKey() == StarrySkyDimension.END_KEY) {
				blockPos = EndGatewayBlockEntityAccessor.invokeSetupExitPortalLocation(serverWorld, pos);
				blockPos = blockPos.up(10);
				EndGatewayBlockEntityAccessor.invokeCreatePortal(serverWorld, blockPos, EndGatewayFeatureConfig.createConfig(pos, false));
				((EndGatewayBlockEntityAccessor) blockEntity).setExitPortalPos(blockPos);
			}
		}
	}
	
}
