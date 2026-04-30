package de.dafuqs.starryskies.mixin;

import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.configs.StarrySkyConfig;
import de.dafuqs.starryskies.registries.StarryDimensionKeys;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EndPortalBlock;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndPortalBlock.class)
public abstract class EndPortalBlockMixin {
	
	@Inject(at = @At("HEAD"), method = "getPortalDestination(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/portal/TeleportTransition;", cancellable = true)
	void starryskies$createTeleportTarget(ServerLevel world, Entity entity, BlockPos pos, CallbackInfoReturnable<TeleportTransition> cir) {
        if (StarrySkyConfig.CONFIG.enableEndPortalsToStarryEnd.get()) {
			boolean sourceIsStarryEnd = world.dimension() == StarryDimensionKeys.END_KEY;
			boolean sourceIsStarryOverworld = world.dimension() == StarryDimensionKeys.OVERWORLD_KEY;
			
			if (sourceIsStarryEnd || sourceIsStarryOverworld) {
				// show the credits
				// taken from EndPortalBlock.onEntityCollision()
				if (!world.isClientSide() && sourceIsStarryEnd && entity instanceof ServerPlayer serverPlayerEntity) {
					if (!serverPlayerEntity.seenCredits) {
						serverPlayerEntity.showEndCredits();
						cir.cancel();
					}
				}
				
				ResourceKey<Level> targetWorldKey = sourceIsStarryEnd ? StarryDimensionKeys.OVERWORLD_KEY : StarryDimensionKeys.END_KEY;
				ServerLevel serverWorld = world.getServer().getLevel(targetWorldKey);
				if (serverWorld == null) {
					cir.cancel();
				} else {
					BlockPos targetPos = sourceIsStarryOverworld ? StarryDimensionKeys.STARRY_END_SPAWN_BLOCK_POS : StarryDimensionKeys.STARRY_OVERWORLD_SPAWN_BLOCK_POS;
					Vec3 targetVec = targetPos.getBottomCenter();
					float entityYaw = entity.getYRot();
					if (sourceIsStarryOverworld) {
						entityYaw = Direction.WEST.toYRot();
						if (entity instanceof ServerPlayer) {
							targetVec = targetVec.subtract(0.0, 1.0, 0.0);
						}
					} else {
						if (entity instanceof ServerPlayer serverPlayerEntity) {
							cir.setReturnValue(serverPlayerEntity.findRespawnPositionAndUseSpawnBlock(false, TeleportTransition.DO_NOTHING));
						}
						
						targetVec = entity.adjustSpawnLocation(serverWorld, targetPos).getBottomCenter();
					}
					
					cir.setReturnValue(new TeleportTransition(serverWorld, targetVec, entity.getDeltaMovement(), entityYaw, entity.getXRot(), TeleportTransition.PLAY_PORTAL_SOUND.then(TeleportTransition.PLACE_PORTAL_TICKET)));
				}
			}
		}
	}

}