package de.dafuqs.starryskies.mixin.fix;

import de.dafuqs.starryskies.client.fix.RenderSkyArgumentCapture;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Captures objects needed for skybox rendering.
@Environment(EnvType.CLIENT)
@Mixin(WorldRenderer.class)
public class WorldRendererMixinFix implements RenderSkyArgumentCapture {
	@Shadow
	@Final
	private DefaultFramebufferSet framebufferSet;

	@Unique FrameGraphBuilder frameGraphBuilder;
	@Unique Fog fog;


	@Inject(at = @At(value = "HEAD"), method = "renderSky", order = 999 /* apply just before Fabric API */)
	private void renderSky(FrameGraphBuilder frameGraphBuilder, Camera camera, float tickProgress, Fog fog, CallbackInfo info) {
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
	public Fog starrySkies$fog() {
		return fog;
	}
}
