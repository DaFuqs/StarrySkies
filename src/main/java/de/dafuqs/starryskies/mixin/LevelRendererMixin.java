package de.dafuqs.starryskies.mixin;

import com.mojang.blaze3d.buffers.*;
import com.mojang.blaze3d.framegraph.*;
import de.dafuqs.starryskies.client.*;
import de.dafuqs.starryskies.client.sky.*;
import de.dafuqs.starryskies.registries.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.state.level.*;
import net.minecraft.server.packs.resources.*;
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

    @Inject(method = "onResourceManagerReload", at = @At("RETURN"))
    private void reloadStarrySky(ResourceManager resourceManager, CallbackInfo ci) {
        if (StarrySkiesClient.SKYBOX != null) StarrySkiesClient.SKYBOX.close();

        StarrySkiesClient.SKYBOX = new StarrySkyBox(this.minecraft.getTextureManager());
    }

    @Inject(method = "addSkyPass", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/state/level/LevelRenderState;skyRenderState:Lnet/minecraft/client/renderer/state/level/SkyRenderState;", opcode = Opcodes.GETFIELD), cancellable = true)
    private void addStarrySky(FrameGraphBuilder frame, CameraRenderState cameraState, GpuBufferSlice skyFog, CallbackInfo ci) {
        if (StarrySkiesClient.SKYBOX == null || level == null || !StarryDimensionKeys.OVERWORLD_KEY.equals(level.dimension())) return;
        ci.cancel();

        var pass = frame.addPass("starry_skies:sky");
        this.targets.main = pass.readsAndWrites(this.targets.main);
        pass.executes(() -> StarrySkiesClient.SKYBOX.renderStarrySky(this.levelRenderState.skyRenderState.skyColor));
    }

}
