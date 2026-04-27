package de.dafuqs.starryskies.mixin;

import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.registries.StarryDimensionKeys;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.portal.TeleportTransition;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(NetherPortalBlock.class)
public abstract class NetherPortalBlockMixin {
	
	@Shadow
	@Nullable
	protected abstract TeleportTransition getExitPortal(ServerLevel world, Entity entity, BlockPos pos, BlockPos scaledPos, boolean inNether, WorldBorder worldBorder);
	
	@Inject(at = @At("HEAD"), method = "getPortalDestination(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/portal/TeleportTransition;", cancellable = true)
	void starryskies$createTeleportTarget(ServerLevel world, Entity entity, BlockPos pos, CallbackInfoReturnable<TeleportTransition> cir) {
		if (StarrySkies.CONFIG.enableNetherPortalsToStarryNether.get()) {
			ResourceKey<Level> sourceWorldKey = world.dimension();
			boolean sourceIsStarryNether = sourceWorldKey == StarryDimensionKeys.NETHER_KEY;
			boolean sourceIsStarryOverworld = sourceWorldKey == StarryDimensionKeys.OVERWORLD_KEY;
			
			if (sourceIsStarryNether || sourceIsStarryOverworld) {
				ResourceKey<Level> targetWorldKey = sourceIsStarryNether ? StarryDimensionKeys.OVERWORLD_KEY : StarryDimensionKeys.NETHER_KEY;
				ServerLevel targetWorld = world.getServer().getLevel(targetWorldKey);
				if (targetWorld != null) {
					boolean targetIsStarryNether = targetWorld.dimension() == StarryDimensionKeys.NETHER_KEY;
					WorldBorder worldBorder = targetWorld.getWorldBorder();
					double d = DimensionType.getTeleportationScale(world.dimensionType(), targetWorld.dimensionType());
					BlockPos blockPos = worldBorder.clampToBounds(entity.getX() * d, entity.getY(), entity.getZ() * d);
					cir.setReturnValue(this.getExitPortal(targetWorld, entity, pos, blockPos, targetIsStarryNether, worldBorder));
				}
			}
		}
	}
	
}