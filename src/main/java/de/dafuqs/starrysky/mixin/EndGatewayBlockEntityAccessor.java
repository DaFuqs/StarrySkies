package de.dafuqs.starrysky.mixin;

import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.EndGatewayFeatureConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EndGatewayBlockEntity.class)
public interface EndGatewayBlockEntityAccessor {
	
	@Accessor("teleportCooldown")
	void setTeleportCooldown(int teleportCooldown);
	
	@Accessor("exitPortalPos")
	BlockPos getExitPortalPos();
	
	@Accessor("exitPortalPos")
	void setExitPortalPos(BlockPos blockPos);
	
	@Invoker("setupExitPortalLocation")
	static BlockPos invokeSetupExitPortalLocation(ServerWorld world, BlockPos pos) {
		return null;
	}
	
	@Invoker("createPortal")
	static void invokeCreatePortal(ServerWorld world, BlockPos pos, EndGatewayFeatureConfig config) {

	}
	
}
