package de.dafuqs.starryskies.mixin;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import de.dafuqs.starryskies.client.StarrySkiesClient;
import de.dafuqs.starryskies.client.sky.StarrySkyBox;
import de.dafuqs.starryskies.registries.StarryDimensionKeys;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LevelTargetBundle;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jspecify.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @Shadow
    private @Nullable ClientLevel level;

    @Shadow
    @Final
    private LevelTargetBundle targets;

    @Shadow
    @Final
    private Minecraft minecraft;

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
