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

    // up, down, west, east, north, south
    // keep both instances loaded => not swappable
    private final StarrySkyBox starrySkyBox = new StarrySkyBox("textures/skybox/light.png", "textures/skybox/darker.png", "textures/skybox/west.png", "textures/skybox/east.png", "textures/skybox/north.png", "textures/skybox/south.png");
    private final StarrySkyBox starrySkyBoxRainbow = new StarrySkyBox("textures/skybox/rainbow_up.png", "textures/skybox/rainbow_down.png", "textures/skybox/rainbow_west.png", "textures/skybox/rainbow_east.png", "textures/skybox/rainbow_north.png", "textures/skybox/rainbow_south.png");

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(at = @At("HEAD"), method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;F)V", cancellable = true)
    void renderSky(MatrixStack matrices, float tickDelta, CallbackInfo callbackInformation) {

        if (this.client.world.getRegistryKey().equals(StarrySkyCommon.starryWorld.getRegistryKey())) {

            if(StarrySkyCommon.STARRY_SKY_CONFIG == null || !StarrySkyCommon.STARRY_SKY_CONFIG.rainbowSkybox) {
                starrySkyBox.render(matrices, tickDelta);
            } else {
                starrySkyBoxRainbow.render(matrices, tickDelta);
            }
            callbackInformation.cancel();
        }
    }
}