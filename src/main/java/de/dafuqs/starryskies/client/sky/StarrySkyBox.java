package de.dafuqs.starryskies.client.sky;

import com.mojang.blaze3d.buffers.*;
import com.mojang.blaze3d.systems.*;
import com.mojang.blaze3d.vertex.*;
import de.dafuqs.starryskies.*;
import net.fabricmc.api.*;
import net.minecraft.block.enums.*;
import net.minecraft.client.*;
import net.minecraft.client.gl.*;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.*;
import net.minecraft.client.util.*;
import net.minecraft.client.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import org.joml.*;

import java.util.*;

@Environment(EnvType.CLIENT)
public class StarrySkyBox {

	private GpuBuffer skyVertexBuffer;
	private RenderSystem.ShapeIndexBuffer indexBuffer;

	public final Identifier UP;
	public final Identifier DOWN;
	public final Identifier WEST;
	public final Identifier EAST;
	public final Identifier NORTH;
	public final Identifier SOUTH;

	public StarrySkyBox(String up, String down, String west, String east, String north, String south) {
		UP = StarrySkies.id(up);
		DOWN = StarrySkies.id(down);
		WEST = StarrySkies.id(west);
		EAST = StarrySkies.id(east);
		NORTH = StarrySkies.id(north);
		SOUTH = StarrySkies.id(south);
	}
	
	public void render(WorldRenderer worldRenderer, Camera camera, FrameGraphBuilder frameGraphBuilder, DefaultFramebufferSet framebufferSet, GpuBufferSlice fog) {
		CameraSubmersionType cameraSubmersionType = camera.getSubmersionType();
		if (cameraSubmersionType == CameraSubmersionType.POWDER_SNOW || cameraSubmersionType == CameraSubmersionType.LAVA || hasBlindnessOrDarkness(camera)) {
			return;
		}
		if (skyVertexBuffer == null) {
			// that needs to be initialized very, very late, and there is no real hook for it afaik,
			// so a null check will do
			skyVertexBuffer = uploadStarrySky();
			indexBuffer = RenderSystem.getSequentialBuffer(VertexFormat.DrawMode.QUADS);
		}
		// Create a proper frame pass so the starry sky would render
		// Mimics vanilla logic
		
		FramePass framePass = frameGraphBuilder.createPass("sky");
		// NOTE: framebufferSet.mainFramebuffer is a Handle over MinecraftClient.getInstance().getFramebuffer()
		// NOTE: This transfer is needed in order for skybox rendering to work because of some
		// Mojang voodoo rendering magic which is too complicated for me to figure out
		framebufferSet.mainFramebuffer = framePass.transfer(framebufferSet.mainFramebuffer);
		// Nothing renders outside of a FramePass renderer, therefore run the rendering logic inside one
		framePass.setRenderer(() -> {
			RenderSystem.setShaderFog(fog);
			renderStarrySky(context);
		});
	}

	private boolean hasBlindnessOrDarkness(Camera camera) {
		Entity focusedEntity = camera.getFocusedEntity();
		return focusedEntity instanceof LivingEntity livingEntity
				&& (livingEntity.hasStatusEffect(StatusEffects.BLINDNESS) || livingEntity.hasStatusEffect(StatusEffects.DARKNESS));
	}
	
	// See WorldRenderer.renderSky() + CubeMapRenderer for inspiration
	private void renderStarrySky(WorldRenderer worldRenderer, Camera camera) {
		MinecraftClient client = MinecraftClient.getInstance();
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
		
		ClientWorld world = context.world();
		float tickProgress = context.tickCounter().getTickProgress(false);
		int color = world.getSkyColor(camera.getPos(), tickProgress);
		Vector4f colorVec = new Vector4f(
				ColorHelper.getRedFloat(color),
				ColorHelper.getGreenFloat(color),
				ColorHelper.getBlueFloat(color),
				ColorHelper.getAlphaFloat(color)
		);

		// All defaults except for colorModulator (== colorVec)
		GpuBufferSlice colorTransform = RenderSystem.getDynamicUniforms()
				.write(RenderSystem.getModelViewMatrix(), colorVec, new Vector3f(), new Matrix4f(), 0.0F);
		
		// The number 36 comes from VertexFormat.DrawMode.QUADS.getIndexCount(24)
		// the formula of which is vertexCount / 4 * 6, i.e. 6 indices per 4 vertices (1 quad)
		GpuBuffer idxBuf = indexBuffer.getIndexBuffer(36);
		Framebuffer framebuffer = client.getFramebuffer();
		
		try (RenderPass renderPass = RenderSystem.getDevice()
				.createCommandEncoder()
				.createRenderPass(() -> "Starry Skies skybox", framebuffer.getColorAttachmentView(), OptionalInt.empty(),
						framebuffer.useDepthAttachment ? framebuffer.getDepthAttachmentView() : null, OptionalDouble.empty())) {
			// NOTE: using POSITION_TEX_COLOR_END_SKY because it uses BlendFunction.TRANSLUCENT
			// The skybox texture looks washed out on POSITION_TEX_COLOR_CELESTIAL due to its BlendFunction.OVERLAY
			renderPass.setPipeline(RenderPipelines.POSITION_TEX_COLOR_END_SKY);
			RenderSystem.bindDefaultUniforms(renderPass);
			renderPass.setUniform("DynamicTransforms", colorTransform);
			renderPass.setIndexBuffer(idxBuf, indexBuffer.getIndexType());
			renderPass.setVertexBuffer(0, this.skyVertexBuffer);
			// draw each quad (side) with a different texture
			// 6 indices per quad, as per VertexFormat.DrawMode.QUADS.getIndexCount(4)
			for (int i = 0; i < 6; ++i) {
				renderPass.bindSampler("Sampler0", skyTextures[i].getGlTextureView());
				renderPass.drawIndexed(0, 6 * i, 6, 1);
			}
		}
	}
	
	// Write skybox vertices into skyVertexBuffer with the specified vertex color
	private GpuBuffer uploadStarrySky() {
		// NOTE: method_72201 creates a BufferAllocator that has both size and capacity set to the argument
		// doing this as default constructor has an "unlimited" max capacity, which will not catch overallocation
		try (BufferAllocator bufferAllocator = BufferAllocator.method_72201(24 * VertexFormats.POSITION_TEXTURE_COLOR.getVertexSize())) {
			BufferBuilder bufferBuilder = new BufferBuilder(bufferAllocator, VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
			final int color = Colors.WHITE;
			// NOTE: UV coords are left-to-right, up-to-down
			// all of these sides follow the sequence of:
			// bottom left  (0,1)
			// bottom right (1,1)
			// top right    (1,0)
			// top left     (0,0)
			
			// Couldn't figure out correct matrix rotations for each side's vertices, so I just typed the coords out manually
			
			// NORTH
			bufferBuilder.vertex(-100.0f, -100.0f, -100.0f).texture(0.0F, 1.0F).color(color);
			bufferBuilder.vertex(100.0f, -100.0f, -100.0f).texture(1.0F, 1.0F).color(color);
			bufferBuilder.vertex(100.0f, 100.0f, -100.0f).texture(1.0F, 0.0F).color(color);
			bufferBuilder.vertex(-100.0f, 100.0f, -100.0f).texture(0.0F, 0.0F).color(color);
			
			// SOUTH
			bufferBuilder.vertex(100.0f, -100.0f, 100.0f).texture(0.0F, 1.0F).color(color);
			bufferBuilder.vertex(-100.0f, -100.0f, 100.0f).texture(1.0F, 1.0F).color(color);
			bufferBuilder.vertex(-100.0f, 100.0f, 100.0f).texture(1.0F, 0.0F).color(color);
			bufferBuilder.vertex(100.0f, 100.0f, 100.0f).texture(0.0F, 0.0F).color(color);
			
			// EAST
			bufferBuilder.vertex(100.0f, -100.0f, -100.0f).texture(0.0F, 1.0F).color(color);
			bufferBuilder.vertex(100.0f, -100.0f, 100.0f).texture(1.0F, 1.0F).color(color);
			bufferBuilder.vertex(100.0f, 100.0f, 100.0f).texture(1.0F, 0.0F).color(color);
			bufferBuilder.vertex(100.0f, 100.0f, -100.0f).texture(0.0F, 0.0F).color(color);
			
			// WEST
			bufferBuilder.vertex(-100.0f, -100.0f, 100.0f).texture(0.0F, 1.0F).color(color);
			bufferBuilder.vertex(-100.0f, -100.0f, -100.0f).texture(1.0F, 1.0F).color(color);
			bufferBuilder.vertex(-100.0f, 100.0f, -100.0f).texture(1.0F, 0.0F).color(color);
			bufferBuilder.vertex(-100.0f, 100.0f, 100.0f).texture(0.0F, 0.0F).color(color);
			
			// UP
			bufferBuilder.vertex(-100.0f, 100.0f, -100.0f).texture(0.0F, 1.0F).color(color);
			bufferBuilder.vertex(100.0f, 100.0f, -100.0f).texture(1.0F, 1.0F).color(color);
			bufferBuilder.vertex(100.0f, 100.0f, 100.0f).texture(1.0F, 0.0F).color(color);
			bufferBuilder.vertex(-100.0f, 100.0f, 100.0f).texture(0.0F, 0.0F).color(color);
			
			// DOWN
			bufferBuilder.vertex(-100.0f, -100.0f, 100.0f).texture(0.0F, 1.0F).color(color);
			bufferBuilder.vertex(100.0f, -100.0f, 100.0f).texture(1.0F, 1.0F).color(color);
			bufferBuilder.vertex(100.0f, -100.0f, -100.0f).texture(1.0F, 0.0F).color(color);
			bufferBuilder.vertex(-100.0f, -100.0f, -100.0f).texture(0.0F, 0.0F).color(color);
			
			try (BuiltBuffer builtBuffer = bufferBuilder.end()) {
				return RenderSystem.getDevice()
						.createBuffer(() -> "StarrySkies sky vertex buffer", GpuBuffer.USAGE_VERTEX, builtBuffer.getBuffer());
			}
		}
	}

}