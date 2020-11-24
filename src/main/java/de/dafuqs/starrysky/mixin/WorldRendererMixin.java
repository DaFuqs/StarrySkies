package de.dafuqs.starrysky.mixin;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.dimension.sky.StarrySkyBox;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Rendering the custom sky box
 */
@Mixin(WorldRenderer.class)
@Environment(EnvType.CLIENT)
public abstract class WorldRendererMixin {

    private final String skyBoxTextureAllSides = "textures/skybox/all_sides.png";
    private final StarrySkyBox starrySkyBox = new StarrySkyBox(skyBoxTextureAllSides, skyBoxTextureAllSides, skyBoxTextureAllSides, skyBoxTextureAllSides, skyBoxTextureAllSides, skyBoxTextureAllSides);

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(at = @At("HEAD"), method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;F)V", cancellable = true)
    void renderStarrySky(MatrixStack matrices, float tickDelta, CallbackInfo callbackInformation) {
        if (this.client.world.getRegistryKey().equals(StarrySkyCommon.starryWorld.getRegistryKey())) {
            starrySkyBox.render(matrices, tickDelta);
            callbackInformation.cancel();
        }
    }
}