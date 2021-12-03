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
	public void setTeleportCooldown(int teleportCooldown);
	
	@Accessor("exitPortalPos")
	public BlockPos getExitPortalPos();
	
	@Accessor("exitPortalPos")
	public void setExitPortalPos(BlockPos blockPos);
	
	@Invoker("setupExitPortalLocation")
	public static BlockPos invokeSetupExitPortalLocation(ServerWorld world, BlockPos pos) {
		return null;
	}
	
	@Invoker("createPortal")
	public static void invokeCreatePortal(ServerWorld world, BlockPos pos, EndGatewayFeatureConfig config) {

	}
	
}
