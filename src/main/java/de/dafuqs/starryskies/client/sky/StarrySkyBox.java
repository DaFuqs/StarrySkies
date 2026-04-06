package de.dafuqs.starryskies.client.sky;

import com.mojang.blaze3d.buffers.*;
import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import com.mojang.blaze3d.framegraph.FramePass;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.*;
import com.mojang.blaze3d.vertex.*;
import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.client.fix.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelTargetBundle;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.*;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FogType;
import org.joml.*;

import java.util.*;

@Environment(EnvType.CLIENT)
public class StarrySkyBox implements DimensionRenderingRegistry.SkyRenderer {

	private GpuBuffer skyVertexBuffer;
	private RenderSystem.AutoStorageIndexBuffer indexBuffer;

	public final ResourceLocation UP;
	public final ResourceLocation DOWN;
	public final ResourceLocation WEST;
	public final ResourceLocation EAST;
	public final ResourceLocation NORTH;
	public final ResourceLocation SOUTH;

	public StarrySkyBox(String up, String down, String west, String east, String north, String south) {
		UP = StarrySkies.id(up);
		DOWN = StarrySkies.id(down);
		WEST = StarrySkies.id(west);
		EAST = StarrySkies.id(east);
		NORTH = StarrySkies.id(north);
		SOUTH = StarrySkies.id(south);
	}

	@Override
	public void render(WorldRenderContext context) {
		FogType cameraSubmersionType = context.camera().getFluidInCamera();
		if (cameraSubmersionType == FogType.POWDER_SNOW || cameraSubmersionType == FogType.LAVA || hasBlindnessOrDarkness(context.camera())) {
			return;
		}
		if (skyVertexBuffer == null) {
			// that needs to be initialized very, very late, and there is no real hook for it afaik,
			// so a null check will do
			skyVertexBuffer = uploadStarrySky();
			indexBuffer = RenderSystem.getSequentialBuffer(VertexFormat.Mode.QUADS);
		}
		// Create a proper frame pass so the starry sky would render
		// Mimics vanilla logic
		RenderSkyArgumentCapture capture = context.worldRenderer();
		FrameGraphBuilder frameGraphBuilder = capture.starrySkies$frameGraphBuilder();
		LevelTargetBundle framebufferSet = capture.starrySkies$framebufferSet();
		GpuBufferSlice fog = capture.starrySkies$fog();
		
		FramePass framePass = frameGraphBuilder.addPass("sky");
		// NOTE: framebufferSet.mainFramebuffer is a Handle over MinecraftClient.getInstance().getFramebuffer()
		// NOTE: This transfer is needed in order for skybox rendering to work because of some
		// Mojang voodoo rendering magic which is too complicated for me to figure out
		framebufferSet.main = framePass.readsAndWrites(framebufferSet.main);
		// Nothing renders outside of a FramePass renderer, therefore run the rendering logic inside one
		framePass.executes(() -> {
			RenderSystem.setShaderFog(fog);
			renderStarrySky(context);
		});
	}

	private boolean hasBlindnessOrDarkness(Camera camera) {
		Entity focusedEntity = camera.getEntity();
		return focusedEntity instanceof LivingEntity livingEntity
				&& (livingEntity.hasEffect(MobEffects.BLINDNESS) || livingEntity.hasEffect(MobEffects.DARKNESS));
	}
	
	// See WorldRenderer.renderSky() + CubeMapRenderer for inspiration
	private void renderStarrySky(WorldRenderContext context) {
		Minecraft client = Minecraft.getInstance();
		TextureManager textureManager = client.getTextureManager();
		AbstractTexture[] skyTextures = {
				textureManager.getTexture(NORTH),
				textureManager.getTexture(SOUTH),
				textureManager.getTexture(EAST),
				textureManager.getTexture(WEST),
				textureManager.getTexture(UP),
				textureManager.getTexture(DOWN),
		};
		for (var skyTexture : skyTextures) skyTexture.setFilter(false, false);
		
		Camera camera = context.camera();
		ClientLevel world = context.world();
		float tickProgress = context.tickCounter().getGameTimeDeltaPartialTick(false);
		int color = world.getSkyColor(camera.getPosition(), tickProgress);
		Vector4f colorVec = new Vector4f(
				ARGB.redFloat(color),
				ARGB.greenFloat(color),
				ARGB.blueFloat(color),
				ARGB.alphaFloat(color)
		);

		// All defaults except for colorModulator (== colorVec)
		GpuBufferSlice colorTransform = RenderSystem.getDynamicUniforms()
				.writeTransform(RenderSystem.getModelViewMatrix(), colorVec, new Vector3f(), new Matrix4f(), 0.0F);
		
		// The number 36 comes from VertexFormat.DrawMode.QUADS.getIndexCount(24)
		// the formula of which is vertexCount / 4 * 6, i.e. 6 indices per 4 vertices (1 quad)
		GpuBuffer idxBuf = indexBuffer.getBuffer(36);
		RenderTarget framebuffer = client.getMainRenderTarget();
		
		try (RenderPass renderPass = RenderSystem.getDevice()
				.createCommandEncoder()
				.createRenderPass(() -> "Starry Skies skybox", framebuffer.getColorTextureView(), OptionalInt.empty(),
						framebuffer.useDepth ? framebuffer.getDepthTextureView() : null, OptionalDouble.empty())) {
			// NOTE: using POSITION_TEX_COLOR_END_SKY because it uses BlendFunction.TRANSLUCENT
			// The skybox texture looks washed out on POSITION_TEX_COLOR_CELESTIAL due to its BlendFunction.OVERLAY
			renderPass.setPipeline(RenderPipelines.END_SKY);
			RenderSystem.bindDefaultUniforms(renderPass);
			renderPass.setUniform("DynamicTransforms", colorTransform);
			renderPass.setIndexBuffer(idxBuf, indexBuffer.type());
			renderPass.setVertexBuffer(0, this.skyVertexBuffer);
			// draw each quad (side) with a different texture
			// 6 indices per quad, as per VertexFormat.DrawMode.QUADS.getIndexCount(4)
			for (int i = 0; i < 6; ++i) {
				renderPass.bindSampler("Sampler0", skyTextures[i].getTextureView());
				renderPass.drawIndexed(0, 6 * i, 6, 1);
			}
		}
	}
	
	// Write skybox vertices into skyVertexBuffer with the specified vertex color
	private GpuBuffer uploadStarrySky() {
		// NOTE: method_72201 creates a BufferAllocator that has both size and capacity set to the argument
		// doing this as default constructor has an "unlimited" max capacity, which will not catch overallocation
		try (ByteBufferBuilder bufferAllocator = ByteBufferBuilder.exactlySized(24 * DefaultVertexFormat.POSITION_TEX_COLOR.getVertexSize())) {
			BufferBuilder bufferBuilder = new BufferBuilder(bufferAllocator, VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
			final int color = CommonColors.WHITE;
			// NOTE: UV coords are left-to-right, up-to-down
			// all of these sides follow the sequence of:
			// bottom left  (0,1)
			// bottom right (1,1)
			// top right    (1,0)
			// top left     (0,0)
			
			// Couldn't figure out correct matrix rotations for each side's vertices, so I just typed the coords out manually
			
			// NORTH
			bufferBuilder.addVertex(-100.0f, -100.0f, -100.0f).setUv(0.0F, 1.0F).setColor(color);
			bufferBuilder.addVertex(100.0f, -100.0f, -100.0f).setUv(1.0F, 1.0F).setColor(color);
			bufferBuilder.addVertex(100.0f, 100.0f, -100.0f).setUv(1.0F, 0.0F).setColor(color);
			bufferBuilder.addVertex(-100.0f, 100.0f, -100.0f).setUv(0.0F, 0.0F).setColor(color);
			
			// SOUTH
			bufferBuilder.addVertex(100.0f, -100.0f, 100.0f).setUv(0.0F, 1.0F).setColor(color);
			bufferBuilder.addVertex(-100.0f, -100.0f, 100.0f).setUv(1.0F, 1.0F).setColor(color);
			bufferBuilder.addVertex(-100.0f, 100.0f, 100.0f).setUv(1.0F, 0.0F).setColor(color);
			bufferBuilder.addVertex(100.0f, 100.0f, 100.0f).setUv(0.0F, 0.0F).setColor(color);
			
			// EAST
			bufferBuilder.addVertex(100.0f, -100.0f, -100.0f).setUv(0.0F, 1.0F).setColor(color);
			bufferBuilder.addVertex(100.0f, -100.0f, 100.0f).setUv(1.0F, 1.0F).setColor(color);
			bufferBuilder.addVertex(100.0f, 100.0f, 100.0f).setUv(1.0F, 0.0F).setColor(color);
			bufferBuilder.addVertex(100.0f, 100.0f, -100.0f).setUv(0.0F, 0.0F).setColor(color);
			
			// WEST
			bufferBuilder.addVertex(-100.0f, -100.0f, 100.0f).setUv(0.0F, 1.0F).setColor(color);
			bufferBuilder.addVertex(-100.0f, -100.0f, -100.0f).setUv(1.0F, 1.0F).setColor(color);
			bufferBuilder.addVertex(-100.0f, 100.0f, -100.0f).setUv(1.0F, 0.0F).setColor(color);
			bufferBuilder.addVertex(-100.0f, 100.0f, 100.0f).setUv(0.0F, 0.0F).setColor(color);
			
			// UP
			bufferBuilder.addVertex(-100.0f, 100.0f, -100.0f).setUv(0.0F, 1.0F).setColor(color);
			bufferBuilder.addVertex(100.0f, 100.0f, -100.0f).setUv(1.0F, 1.0F).setColor(color);
			bufferBuilder.addVertex(100.0f, 100.0f, 100.0f).setUv(1.0F, 0.0F).setColor(color);
			bufferBuilder.addVertex(-100.0f, 100.0f, 100.0f).setUv(0.0F, 0.0F).setColor(color);
			
			// DOWN
			bufferBuilder.addVertex(-100.0f, -100.0f, 100.0f).setUv(0.0F, 1.0F).setColor(color);
			bufferBuilder.addVertex(100.0f, -100.0f, 100.0f).setUv(1.0F, 1.0F).setColor(color);
			bufferBuilder.addVertex(100.0f, -100.0f, -100.0f).setUv(1.0F, 0.0F).setColor(color);
			bufferBuilder.addVertex(-100.0f, -100.0f, -100.0f).setUv(0.0F, 0.0F).setColor(color);
			
			try (MeshData builtBuffer = bufferBuilder.buildOrThrow()) {
				return RenderSystem.getDevice()
						.createBuffer(() -> "StarrySkies sky vertex buffer", GpuBuffer.USAGE_VERTEX, builtBuffer.vertexBuffer());
			}
		}
	}

}