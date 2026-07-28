package de.dafuqs.starryskies.client.sky;

import com.mojang.blaze3d.*;
import com.mojang.blaze3d.buffers.*;
import com.mojang.blaze3d.pipeline.*;
import com.mojang.blaze3d.systems.*;
import com.mojang.blaze3d.vertex.*;
import de.dafuqs.starryskies.client.*;
import net.fabricmc.api.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;

import java.util.*;

@Environment(EnvType.CLIENT)
public class StarrySkyBox implements AutoCloseable {

	private final GpuBuffer skyVertexBuffer;
	private final RenderTarget skyTarget;
	private final RenderSystem.AutoStorageIndexBuffer indices = RenderSystem.getSequentialBuffer(PrimitiveTopology.QUADS);

	public final AbstractTexture[] TEXTURES;

	public static final int INDICES_PER_FACE = PrimitiveTopology.QUADS.indexCount(4);
	public static final int INDEXBUFFER_SIZE = INDICES_PER_FACE * 6;

	public StarrySkyBox(final TextureManager textureManager, final RenderTarget renderTarget) {
		TEXTURES = new AbstractTexture[] {
				textureManager.getTexture(StarrySkyBoxTextures.INSTANCE.NORTH),
				textureManager.getTexture(StarrySkyBoxTextures.INSTANCE.SOUTH),
				textureManager.getTexture(StarrySkyBoxTextures.INSTANCE.EAST ),
				textureManager.getTexture(StarrySkyBoxTextures.INSTANCE.WEST ),
				textureManager.getTexture(StarrySkyBoxTextures.INSTANCE.UP   ),
				textureManager.getTexture(StarrySkyBoxTextures.INSTANCE.DOWN )
        };
        skyVertexBuffer = uploadStarrySky();
		skyTarget = renderTarget;
	}

	public void close() {
		skyVertexBuffer.close();
	}
	
	// See SkyRenderer.renderEndSky() + CubeMap for inspiration
	public void renderStarrySky(int skyColor) {
		// All defaults except for colorModulator (== sky color)
		GpuBufferSlice colorTransform = RenderSystem.getDynamicUniforms()
				.writeTransform(RenderSystem.getModelViewMatrixCopy(), ARGB.vector4fFromARGB32(skyColor));
		GpuBuffer idxBuf = indices.getBuffer(INDEXBUFFER_SIZE);
		
		try (RenderPass renderPass = RenderSystem.getDevice()
				.createCommandEncoder()
				.createRenderPass(() -> "Starry Skies skybox", skyTarget.getColorTextureView(), Optional.empty(),
						skyTarget.useDepth ? skyTarget.getDepthTextureView() : null, OptionalDouble.empty())) {
			renderPass.setPipeline(RenderPipelines.END_SKY);
			RenderSystem.bindDefaultUniforms(renderPass);
			renderPass.setUniform("DynamicTransforms", colorTransform);
			renderPass.setIndexBuffer(idxBuf, indices.type());
			renderPass.setVertexBuffer(0, this.skyVertexBuffer.slice());
			// draw each quad (side) with a different texture
			// 6 indices per quad, per PrimitiveTopology.QUADS.indexCount(4)
			for (int i = 0; i < 6; ++i) {
				renderPass.bindTexture("Sampler0", TEXTURES[i].getTextureView(), TEXTURES[i].getSampler());
				renderPass.drawIndexed(INDICES_PER_FACE, 1, INDICES_PER_FACE * i, 0, 0);
			}
		}
	}
	
	// Write skybox vertices into skyVertexBuffer with the specified vertex color
	private GpuBuffer uploadStarrySky() {
		try (ByteBufferBuilder bufferAllocator = ByteBufferBuilder.exactlySized(24 * DefaultVertexFormat.POSITION_TEX_COLOR.getVertexSize())) {
			BufferBuilder bufferBuilder = new BufferBuilder(bufferAllocator, PrimitiveTopology.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
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