package de.dafuqs.starryskies.mixin.fix;

import com.mojang.blaze3d.buffers.*;
import de.dafuqs.starryskies.client.fix.*;
import net.fabricmc.api.*;
import net.minecraft.client.render.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

// Captures objects needed for skybox rendering.
@Environment(EnvType.CLIENT)
@Mixin(WorldRenderer.class)
public class WorldRendererMixinFix implements RenderSkyArgumentCapture {
	@Shadow
	@Final
	private DefaultFramebufferSet framebufferSet;
	
	@Unique
	FrameGraphBuilder frameGraphBuilder;
	@Unique
	GpuBufferSlice fog;
	
	
	@Inject(at = @At(value = "HEAD"), method = "renderSky", order = 999 /* apply just before Fabric API */)
	private void renderSky(FrameGraphBuilder frameGraphBuilder, Camera camera, float tickProgress, GpuBufferSlice fog, CallbackInfo ci) {
		this.frameGraphBuilder = frameGraphBuilder;
		this.fog = fog;
	}
	
	@Override
	public DefaultFramebufferSet starrySkies$framebufferSet() {
		return framebufferSet;
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
