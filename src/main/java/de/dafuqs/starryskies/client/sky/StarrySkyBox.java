package de.dafuqs.starryskies.client.sky;

import com.mojang.blaze3d.buffers.*;
import com.mojang.blaze3d.systems.*;
import com.mojang.blaze3d.textures.*;
import com.mojang.blaze3d.vertex.*;
import de.dafuqs.starryskies.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.minecraft.block.enums.*;
import net.minecraft.client.*;
import net.minecraft.client.gl.*;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.*;
import net.minecraft.client.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.*;
import net.minecraft.util.*;
import org.joml.*;

import java.util.*;

// TODO: the rainbow skybox is currently nonfunctional
@Environment(EnvType.CLIENT)
public class StarrySkyBox implements DimensionRenderingRegistry.SkyRenderer {
	
	private GpuBuffer skyVertexBuffer;
	
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

	@Override
	public void render(WorldRenderContext context) {
		CameraSubmersionType cameraSubmersionType = context.camera().getSubmersionType();
		if (cameraSubmersionType == CameraSubmersionType.POWDER_SNOW || cameraSubmersionType == CameraSubmersionType.LAVA || hasBlindnessOrDarkness(context.camera())) {
			return;
		}
		if (skyVertexBuffer == null) {
			// that needs to be initialized very, very late, and there is no real hook for it afaik,
			// so a null check will do
			skyVertexBuffer = createStarrySky();
		}
		renderStarrySky();
	}
	
	private boolean hasBlindnessOrDarkness(Camera camera) {
		Entity var3 = camera.getFocusedEntity();
		if (!(var3 instanceof LivingEntity livingEntity)) {
			return false;
		} else {
			return livingEntity.hasStatusEffect(StatusEffects.BLINDNESS) || livingEntity.hasStatusEffect(StatusEffects.DARKNESS);
		}
	}
	
	private static GpuBuffer createStarrySky() {
		BufferAllocator bufferAllocator = new BufferAllocator(24 * VertexFormats.POSITION_TEXTURE_COLOR.getVertexSize());
		
		GpuBuffer gpuBuffer;
		BufferBuilder bufferBuilder = new BufferBuilder(bufferAllocator, VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
		
		for (int i = 0; i < 6; ++i) {
			Matrix4f matrix4f = new Matrix4f();
			switch (i) {
				case 1 -> matrix4f.rotationX(1.5707964F);
				case 2 -> matrix4f.rotationX(-1.5707964F);
				case 3 -> matrix4f.rotationX(3.1415927F);
				case 4 -> matrix4f.rotationZ(1.5707964F);
				case 5 -> matrix4f.rotationZ(-1.5707964F);
			}
			
			bufferBuilder.vertex(matrix4f, -100.0F, -100.0F, -100.0F).texture(0.0F, 0.0F).color(-14145496);
			bufferBuilder.vertex(matrix4f, -100.0F, -100.0F, 100.0F).texture(0.0F, 16.0F).color(-14145496);
			bufferBuilder.vertex(matrix4f, 100.0F, -100.0F, 100.0F).texture(16.0F, 16.0F).color(-14145496);
			bufferBuilder.vertex(matrix4f, 100.0F, -100.0F, -100.0F).texture(16.0F, 0.0F).color(-14145496);
		}
		
		BuiltBuffer builtBuffer = bufferBuilder.end();
		gpuBuffer = RenderSystem.getDevice().createBuffer(() -> "StarrySkies sky vertex buffer", BufferType.VERTICES, BufferUsage.STATIC_WRITE, builtBuffer.getBuffer());
		builtBuffer.close();
		
		bufferAllocator.close();
		return gpuBuffer;
	}
	
	// See WorldRenderer.renderEndSky() for inspiration
	private void renderStarrySky() {
		TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();
		AbstractTexture abstractTexture = textureManager.getTexture(DOWN);
		abstractTexture.setFilter(TriState.FALSE, false);
		RenderSystem.ShapeIndexBuffer shapeIndexBuffer = RenderSystem.getSequentialBuffer(VertexFormat.DrawMode.QUADS);
		GpuBuffer gpuBuffer = shapeIndexBuffer.getIndexBuffer(36);
		GpuTexture gpuTexture = MinecraftClient.getInstance().getFramebuffer().getColorAttachment();
		GpuTexture gpuTexture2 = MinecraftClient.getInstance().getFramebuffer().getDepthAttachment();
		RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(gpuTexture, OptionalInt.empty(), gpuTexture2, OptionalDouble.empty());
		
		renderPass.setPipeline(RenderPipelines.POSITION_TEX_COLOR_END_SKY);
		renderPass.bindSampler("Sampler0", abstractTexture.getGlTexture());
		renderPass.setVertexBuffer(0, this.skyVertexBuffer);
		renderPass.setIndexBuffer(gpuBuffer, shapeIndexBuffer.getIndexType());
		renderPass.drawIndexed(0, 36);
		renderPass.close();
	}
	
	
}