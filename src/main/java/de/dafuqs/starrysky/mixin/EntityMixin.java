package de.dafuqs.starrysky.mixin;

import de.dafuqs.starrysky.StarrySkyCommon;
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
        TeleportTarget newTeleportTarget = handleGetTeleportTarget((Entity) (Object) this, destination);
        if (newTeleportTarget != null) {
            callbackInfo.setReturnValue(newTeleportTarget);
        }
    }

    /**
     * Returns the new position for the entity on dimension change
     *
     * @param entity      The teleporting entity
     * @param destination The destination dimension
     * @return The position in the new dimension
     */
    private static TeleportTarget handleGetTeleportTarget(Entity entity, ServerWorld destination) {
        //return new TeleportTarget(new Vec3d(0.5D, 100, 0.5D), new Vec3d(0, 0, 0), 0, 0);
        Entity thisEntity = (Entity) (Object) entity;
        return StarrySkyCommon.handleGetTeleportTarget(thisEntity, destination);
    }


    @ModifyVariable(method = "tickNetherPortal", at = @At("STORE"), ordinal = 1)
    private ServerWorld injected(ServerWorld x) {
        return StarrySkyCommon.doinjected((Entity) (Object) this, x);
    }


}