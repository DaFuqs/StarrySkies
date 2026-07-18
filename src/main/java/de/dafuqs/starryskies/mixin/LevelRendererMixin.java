package de.dafuqs.starryskies.mixin;

import com.mojang.blaze3d.buffers.*;
import com.mojang.blaze3d.framegraph.*;
import de.dafuqs.starryskies.client.sky.*;
import de.dafuqs.starryskies.registries.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.state.level.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.model.sprite.*;
import net.minecraft.world.level.material.*;
import org.jspecify.annotations.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    @Shadow
    @Final
    private LevelRenderState levelRenderState;

    @Shadow
    private @Nullable SkyRenderer skyRenderer;

    @Shadow
    @Final
    private TextureManager textureManager;

    @Shadow
    @Final
    private AtlasManager atlasManager;

    @Shadow
    @Final
    private GameRenderer gameRenderer;

    @Inject(method = "addSkyPass(Lcom/mojang/blaze3d/framegraph/FrameGraphBuilder;Lnet/minecraft/client/renderer/state/level/CameraRenderState;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;)V", at = @At(value = "HEAD"), cancellable = true)
    private void addStarrySky(FrameGraphBuilder frame, CameraRenderState cameraState, GpuBufferSlice skyFog, CallbackInfo ci) {
        if (this.levelRenderState.shouldResetSkyRenderer || Minecraft.getInstance().level != null && StarryDimensionKeys.OVERWORLD_KEY.equals(Minecraft.getInstance().level.dimension())) {
            FogType fogType = cameraState.fogType;
            if (fogType != FogType.POWDER_SNOW && fogType != FogType.LAVA && !cameraState.entityRenderState.doesMobEffectBlockSky) {
                if (this.levelRenderState.shouldResetSkyRenderer || this.skyRenderer == null) {
                    if (this.skyRenderer != null) {
                        this.skyRenderer.close();
                    }

                    this.skyRenderer = new StarrySkyBox(this.textureManager, this.atlasManager, this.gameRenderer.mainRenderTarget());
                    ci.cancel();
                }
            }
        }

    }

}
