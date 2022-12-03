package de.dafuqs.starryskies.mixin.client;

import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.dimension.StarrySkyDimension;
import de.dafuqs.starryskies.dimension.sky.StarrySkyBox;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
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
	private static final StarrySkyBox starrySkyBox = new StarrySkyBox("textures/skybox/light.png", "textures/skybox/darker.png", "textures/skybox/west.png", "textures/skybox/east.png", "textures/skybox/north.png", "textures/skybox/south.png");
	private static final StarrySkyBox starrySkyBoxRainbow = new StarrySkyBox("textures/skybox/rainbow_up.png", "textures/skybox/rainbow_down.png", "textures/skybox/rainbow_west.png", "textures/skybox/rainbow_east.png", "textures/skybox/rainbow_north.png", "textures/skybox/rainbow_south.png");
	
	@Inject(at = @At("HEAD"), method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", cancellable = true)
	void renderSky(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, Camera camera, boolean bl, Runnable runnable, CallbackInfo ci) {
		MinecraftClient client = MinecraftClient.getInstance();
		
		if (client.world != null && client.world.getRegistryKey().equals(StarrySkyDimension.OVERWORLD_KEY)) {
			if (StarrySkies.CONFIG == null || !StarrySkies.CONFIG.rainbowSkybox) {
				starrySkyBox.render(matrices, client.getTickDelta());
			} else {
				starrySkyBoxRainbow.render(matrices, client.getTickDelta());
			}
			ci.cancel();
		}
	}
}