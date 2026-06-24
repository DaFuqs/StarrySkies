package de.dafuqs.starryskies.mixin;

import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.registries.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.portal.*;
import net.minecraft.world.phys.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(EndPortalBlock.class)
public abstract class EndPortalBlockMixin {
	
	@Inject(at = @At("HEAD"), method = "getPortalDestination(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/portal/TeleportTransition;", cancellable = true)
	void starryskies$createTeleportTarget(ServerLevel world, Entity entity, BlockPos pos, CallbackInfoReturnable<TeleportTransition> cir) {
		if (StarrySkies.CONFIG.enableEndPortalsToStarryEnd) {
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
					Vec3 targetVec = Vec3.atBottomCenterOf(targetPos);
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

						targetVec = Vec3.atBottomCenterOf(entity.adjustSpawnLocation(serverWorld, targetPos));
					}
					
					cir.setReturnValue(new TeleportTransition(serverWorld, targetVec, entity.getDeltaMovement(), entityYaw, entity.getXRot(), TeleportTransition.PLAY_PORTAL_SOUND.then(TeleportTransition.PLACE_PORTAL_TICKET)));
				}
			}
		}
	}

}