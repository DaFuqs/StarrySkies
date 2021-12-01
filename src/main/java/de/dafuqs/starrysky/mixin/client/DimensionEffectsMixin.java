package de.dafuqs.starrysky.mixin.client;

import de.dafuqs.starrysky.callbacks.SkyPropertiesCallback;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Custom sky properties
 * used for increasing cloud height
 */
@Environment(EnvType.CLIENT)
@Mixin(DimensionEffects.class)
public abstract class DimensionEffectsMixin {

    @Shadow
    @Final
    private static Object2ObjectMap<Identifier, DimensionEffects> BY_IDENTIFIER;

    @Inject(method = "<init>(FZLnet/minecraft/client/render/DimensionEffects$SkyType;ZZ)V", at = @At("RETURN"))
    private static void starrySky_init(CallbackInfo info) {
        SkyPropertiesCallback.EVENT.invoker().handle(BY_IDENTIFIER);
    }

}