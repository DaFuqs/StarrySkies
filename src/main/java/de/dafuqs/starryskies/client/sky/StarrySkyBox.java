package de.dafuqs.starryskies.client.sky;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import de.dafuqs.starryskies.client.StarrySkyBoxTextures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ARGB;
import net.minecraft.util.CommonColors;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.OptionalDouble;
import java.util.OptionalInt;

public class StarrySkyBox implements AutoCloseable {

	private final GpuBuffer skyVertexBuffer;
	private final RenderSystem.AutoStorageIndexBuffer indices;

	public final AbstractTexture[] TEXTURES;

	public StarrySkyBox(final TextureManager textureManager) {
		TEXTURES = new AbstractTexture[] {
				textureManager.getTexture(StarrySkyBoxTextures.INSTANCE.NORTH),
				textureManager.getTexture(StarrySkyBoxTextures.INSTANCE.SOUTH),
				textureManager.getTexture(StarrySkyBoxTextures.INSTANCE.EAST ),
				textureManager.getTexture(StarrySkyBoxTextures.INSTANCE.WEST ),
				textureManager.getTexture(StarrySkyBoxTextures.INSTANCE.UP   ),
				textureManager.getTexture(StarrySkyBoxTextures.INSTANCE.DOWN )
        };
        skyVertexBuffer = uploadStarrySky();
		indices = RenderSystem.getSequentialBuffer(VertexFormat.Mode.QUADS);
	}

	public void close() {
		skyVertexBuffer.close();
	}
	
	// See SkyRenderer.renderEndSky() + CubeMap for inspiration
	public void renderStarrySky(int skyColor) {
		// All defaults except for colorModulator (== sky color)
		GpuBufferSlice colorTransform = RenderSystem.getDynamicUniforms()
				.writeTransform(RenderSystem.getModelViewMatrix(), ARGB.vector4fFromARGB32(skyColor), new Vector3f(), new Matrix4f());

		// The number 36 comes from VertexFormat.Mode.QUADS.getIndexCount(24)
		// the formula of which is vertexCount / 4 * 6, i.e. 6 indices per 4 vertices (1 quad)
		GpuBuffer idxBuf = indices.getBuffer(36);
		RenderTarget framebuffer = Minecraft.getInstance().getMainRenderTarget();
		
		try (RenderPass renderPass = RenderSystem.getDevice()
				.createCommandEncoder()
				.createRenderPass(() -> "Starry Skies skybox", framebuffer.getColorTextureView(), OptionalInt.empty(),
						framebuffer.useDepth ? framebuffer.getDepthTextureView() : null, OptionalDouble.empty())) {
			renderPass.setPipeline(RenderPipelines.END_SKY);
			RenderSystem.bindDefaultUniforms(renderPass);
			renderPass.setUniform("DynamicTransforms", colorTransform);
			renderPass.setIndexBuffer(idxBuf, indices.type());
			renderPass.setVertexBuffer(0, this.skyVertexBuffer);
			// draw each quad (side) with a different texture
			// 6 indices per quad, as per VertexFormat.Mode.QUADS.getIndexCount(4)
			for (int i = 0; i < 6; ++i) {
				renderPass.bindTexture("Sampler0", TEXTURES[i].getTextureView(), TEXTURES[i].getSampler());
				renderPass.drawIndexed(0, 6 * i, 6, 1);
			}
		}
	}
	
	// Write skybox vertices into skyVertexBuffer with the specified vertex color
	private GpuBuffer uploadStarrySky() {
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