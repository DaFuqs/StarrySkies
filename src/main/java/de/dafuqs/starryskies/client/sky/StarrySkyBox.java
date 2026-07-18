package de.dafuqs.starryskies.client.sky;

import com.mojang.blaze3d.*;
import com.mojang.blaze3d.buffers.*;
import com.mojang.blaze3d.pipeline.*;
import com.mojang.blaze3d.systems.*;
import com.mojang.blaze3d.textures.*;
import com.mojang.blaze3d.vertex.*;
import de.dafuqs.starryskies.client.*;
import net.fabricmc.api.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.model.sprite.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;

import java.util.*;

// Replaces vanilla SkyRenderer in Starry Skies Overworld
@Environment(EnvType.CLIENT)
public class StarrySkyBox extends SkyRenderer implements AutoCloseable {

	private final GpuBuffer skyVertexBuffer;
	private final RenderTarget renderTarget;

	public final AbstractTexture[] TEXTURES;

	public StarrySkyBox(final TextureManager textureManager, final AtlasManager atlasManager, final RenderTarget renderTarget) {
		super(textureManager, atlasManager, renderTarget);

		TEXTURES = new AbstractTexture[]{
				textureManager.getTexture(StarrySkyBoxTextures.INSTANCE.NORTH),
				textureManager.getTexture(StarrySkyBoxTextures.INSTANCE.SOUTH),
				textureManager.getTexture(StarrySkyBoxTextures.INSTANCE.EAST),
				textureManager.getTexture(StarrySkyBoxTextures.INSTANCE.WEST),
				textureManager.getTexture(StarrySkyBoxTextures.INSTANCE.UP),
				textureManager.getTexture(StarrySkyBoxTextures.INSTANCE.DOWN)
        };
		this.renderTarget = renderTarget;
        skyVertexBuffer = uploadStarrySky();
	}

	public void close() {
		skyVertexBuffer.close();
	}
	
	// See SkyRenderer.renderEndSky() + CubeMap for inspiration
	@Override
	public void renderSkyDisc(final int skyColor) {
		// The number 36 comes from VertexFormat.Mode.QUADS.getIndexCount(24)
		// the formula of which is vertexCount / 4 * 6, i.e. 6 indices per 4 vertices (1 quad)
		RenderSystem.AutoStorageIndexBuffer autoIndices = RenderSystem.getSequentialBuffer(PrimitiveTopology.QUADS);
		GpuBuffer indexBuffer = autoIndices.getBuffer(36);
		GpuTextureView colorTexture = this.renderTarget.getColorTextureView();
		GpuTextureView depthTexture = this.renderTarget.getDepthTextureView();
		GpuBufferSlice dynamicTransforms = RenderSystem.getDynamicUniforms().writeTransform(RenderSystem.getModelViewMatrixCopy(), ARGB.vector4fFromARGB32(skyColor));

		try (RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(() -> "Starry sky", colorTexture, Optional.empty(), depthTexture, OptionalDouble.empty())) {
			renderPass.setPipeline(RenderPipelines.END_SKY);
			RenderSystem.bindDefaultUniforms(renderPass);
			renderPass.setUniform("DynamicTransforms", dynamicTransforms);

			renderPass.setIndexBuffer(indexBuffer, autoIndices.type());
			renderPass.setVertexBuffer(0, this.skyVertexBuffer.slice());
			// draw each quad (side) with a different texture
			// 6 indices per quad, as per VertexFormat.Mode.QUADS.getIndexCount(4)
			for (int i = 0; i < 6; ++i) {
				renderPass.bindTexture("Sampler0", TEXTURES[i].getTextureView(), TEXTURES[i].getSampler());
				renderPass.drawIndexed(36, 1, 0, 0, 0);
			}
		}
	}

	@Override
	public void renderSunMoonAndStars(final PoseStack poseStack, final float sunAngle, final float moonAngle, final float starAngle, final MoonPhase moonPhase, final float rainBrightness, final float starBrightness) {

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