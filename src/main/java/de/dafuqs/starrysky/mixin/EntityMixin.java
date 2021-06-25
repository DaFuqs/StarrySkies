package de.dafuqs.starrysky.mixin;

import de.dafuqs.starrysky.StarrySkyDimensionTravelHandler;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.TeleportTarget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(at = @At("HEAD"), method = "getTeleportTarget", cancellable = true)
    void getTeleportTarget(ServerWorld destination, CallbackInfoReturnable<TeleportTarget> callbackInfo) {
        Entity thisEntity = (Entity) (Object) this;
        TeleportTarget newTeleportTarget = StarrySkyDimensionTravelHandler.handleGetTeleportTarget(thisEntity, destination);
        if (newTeleportTarget != null) {
            if(newTeleportTarget.position == null) {
                // starry dimensions, but no teleport target found
                // cancel vanilla, but without destination
                callbackInfo.setReturnValue(null);
            } else {
                callbackInfo.setReturnValue(newTeleportTarget);
            }
        }
    }

    @ModifyVariable(method = "tickNetherPortal", at = @At("STORE"), ordinal = 1)
    private ServerWorld injected(ServerWorld x) {
        return StarrySkyDimensionTravelHandler.modifyNetherPortalDestination((Entity) (Object) this, x);
    }

}