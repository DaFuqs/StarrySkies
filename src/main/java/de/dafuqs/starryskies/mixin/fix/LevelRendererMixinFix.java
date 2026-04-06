package de.dafuqs.starryskies.mixin.fix;

import com.mojang.blaze3d.buffers.*;
import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import de.dafuqs.starryskies.client.fix.*;
import net.fabricmc.api.*;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LevelTargetBundle;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

// Captures objects needed for skybox rendering.
@Environment(EnvType.CLIENT)
@Mixin(LevelRenderer.class)
public class LevelRendererMixinFix implements RenderSkyArgumentCapture {
	@Shadow
	@Final
	private LevelTargetBundle targets;
	
	@Unique
    FrameGraphBuilder frameGraphBuilder;
	@Unique
	GpuBufferSlice fog;
	
	
	@Inject(at = @At(value = "HEAD"), method = "addSkyPass", order = 999 /* apply just before Fabric API */)
	private void renderSky(FrameGraphBuilder frameGraphBuilder, Camera camera, float tickProgress, GpuBufferSlice fog, CallbackInfo ci) {
		this.frameGraphBuilder = frameGraphBuilder;
		this.fog = fog;
	}
	
	@Override
	public LevelTargetBundle starrySkies$framebufferSet() {
		return targets;
	}
	
	@Override
	public FrameGraphBuilder starrySkies$frameGraphBuilder() {
		return frameGraphBuilder;
	}
	
	@Override
	public GpuBufferSlice starrySkies$fog() {
		return fog;
	}
	
}
