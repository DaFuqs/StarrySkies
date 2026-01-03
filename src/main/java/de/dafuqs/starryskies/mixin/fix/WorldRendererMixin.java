package de.dafuqs.starryskies.mixin.fix;

import com.mojang.blaze3d.buffers.*;
import de.dafuqs.starryskies.client.*;
import de.dafuqs.starryskies.registries.*;
import net.fabricmc.api.*;
import net.minecraft.client.*;
import net.minecraft.client.render.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Environment(EnvType.CLIENT)
@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
	
	@Shadow
	@Final
	private DefaultFramebufferSet framebufferSet;
	
	@Inject(at = @At(value = "HEAD"), method = "renderSky", order = 999)
	private void renderSky(FrameGraphBuilder frameGraphBuilder, Camera camera, GpuBufferSlice fogBuffer, CallbackInfo ci) {
		Entity cameraEntity = MinecraftClient.getInstance().getCameraEntity();
		if (cameraEntity.getEntityWorld().getRegistryKey() == StarryDimensionKeys.OVERWORLD_KEY) {
			StarrySkiesClient.skyBox.render((WorldRenderer) (Object) this, camera, frameGraphBuilder, framebufferSet, fogBuffer);
		}
	}
	
}
