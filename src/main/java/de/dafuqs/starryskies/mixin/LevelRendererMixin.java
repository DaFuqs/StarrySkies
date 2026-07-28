package de.dafuqs.starryskies.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.buffers.*;
import com.mojang.blaze3d.framegraph.*;
import com.mojang.blaze3d.pipeline.RenderTarget;
import de.dafuqs.starryskies.client.*;
import de.dafuqs.starryskies.client.sky.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.state.level.*;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.sprite.AtlasManager;
import org.objectweb.asm.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    @Shadow
    @Final
    private LevelTargetBundle targets;

    @Shadow
    @Final
    private LevelRenderState levelRenderState;

    @WrapOperation(method = "addSkyPass", at = @At(value = "NEW", target = "net/minecraft/client/renderer/SkyRenderer"))
    private SkyRenderer reloadStarrySky(TextureManager textureManager, AtlasManager atlasManager, RenderTarget renderTarget, Operation<SkyRenderer> original) {
        if (StarrySkiesClient.SKYBOX != null) StarrySkiesClient.SKYBOX.close();
        StarrySkiesClient.SKYBOX = new StarrySkyBox(textureManager, renderTarget);
        return original.call(textureManager, atlasManager, renderTarget);
    }

    @Inject(method = "addSkyPass", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/state/level/LevelRenderState;skyRenderState:Lnet/minecraft/client/renderer/state/level/SkyRenderState;", opcode = Opcodes.GETFIELD), cancellable = true)
    private void addStarrySky(FrameGraphBuilder frame, CameraRenderState cameraState, GpuBufferSlice skyFog, CallbackInfo ci) {
        if (Boolean.FALSE.equals(this.levelRenderState.getData(StarrySkiesClient.USE_SKYBOX))) return;
        ci.cancel();

        var pass = frame.addPass("starry_skies:sky");
        this.targets.main = pass.readsAndWrites(this.targets.main);
        pass.executes(() -> StarrySkiesClient.SKYBOX.renderStarrySky(this.levelRenderState.skyRenderState.skyColor));
    }

}
