package de.dafuqs.starryskies.dimension.sky;

import com.mojang.blaze3d.systems.RenderSystem;
import de.dafuqs.starryskies.StarrySkies;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

import java.util.LinkedHashMap;

/**
 * Renderer for the custom skybox
 */
@Environment(EnvType.CLIENT)
public class StarrySkyBox {
	
	public static final Identifier UP = new Identifier("skybox", "up");
	public static final Identifier DOWN = new Identifier("skybox", "down");
	public static final Identifier WEST = new Identifier("skybox", "west");
	public static final Identifier EAST = new Identifier("skybox", "east");
	public static final Identifier NORTH = new Identifier("skybox", "north");
	public static final Identifier SOUTH = new Identifier("skybox", "south");
	
	public LinkedHashMap<Identifier, Identifier> textures = new LinkedHashMap<>();
	
	public StarrySkyBox(String up, String down, String west, String east, String north, String south) {
		this.textures.put(UP, new Identifier(StarrySkies.MOD_ID, up));
		this.textures.put(DOWN, new Identifier(StarrySkies.MOD_ID, down));
		this.textures.put(WEST, new Identifier(StarrySkies.MOD_ID, west));
		this.textures.put(EAST, new Identifier(StarrySkies.MOD_ID, east));
		this.textures.put(NORTH, new Identifier(StarrySkies.MOD_ID, north));
		this.textures.put(SOUTH, new Identifier(StarrySkies.MOD_ID, south));
	}
	
	public void render(MatrixStack matrices, float tickDelta) {
		MinecraftClient client = MinecraftClient.getInstance();
		World world = client.world;
		
		if (world == null) {
			return;
		}
		
		GameOptions options = MinecraftClient.getInstance().options;
		float distance = 16F * (float) options.getViewDistance().getValue() - 8F;
		int color = (int) Math.abs(((Math.abs((world.getTimeOfDay() - 6000) % 24000) - 12000) / 47)); // 47 = 12000 (half day)  /255 (max hue)
		int rawLight = (int) ((world.getTimeOfDay() / 12000) % 15); // a day is 24000; max light level = 15
		int vertexLight = 0x00f000f0 >> 2 | rawLight >> 3 | rawLight;
		
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.depthMask(false);
		RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
		
		for (int i = 0; i < 6; ++i) {
			matrices.push();
			if (i == 0) {
				RenderSystem.setShaderTexture(0, this.textures.get(DOWN));
			}
			if (i == 1) {
				RenderSystem.setShaderTexture(0, this.textures.get(WEST));
				matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0F));
			}
			if (i == 2) {
				RenderSystem.setShaderTexture(0, this.textures.get(EAST));
				matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-90.0F));
			}
			if (i == 3) {
				RenderSystem.setShaderTexture(0, this.textures.get(UP));
				matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180.0F));
			}
			if (i == 4) {
				RenderSystem.setShaderTexture(0, this.textures.get(NORTH));
				matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
			}
			if (i == 5) {
				RenderSystem.setShaderTexture(0, this.textures.get(SOUTH));
				matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(-90.0F));
			}
			
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder buffer = tessellator.getBuffer();
			
			Matrix4f matrix4f = matrices.peek().getPositionMatrix();
			buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
			buffer.vertex(matrix4f, -distance, -distance, -distance).texture(0.0F, 0.0F).color(color, color, color, color).light(vertexLight).next();
			buffer.vertex(matrix4f, -distance, -distance, distance).texture(0.0F, 1.0F).color(color, color, color, color).light(vertexLight).next();
			buffer.vertex(matrix4f, distance, -distance, distance).texture(1.0F, 1.0F).color(color, color, color, color).light(vertexLight).next();
			buffer.vertex(matrix4f, distance, -distance, -distance).texture(1.0F, 0.0F).color(color, color, color, color).light(vertexLight).next();
			tessellator.draw();
			matrices.pop();
		}
		
		RenderSystem.depthMask(true);
		RenderSystem.enableTexture();
		RenderSystem.disableBlend();
		
	}
	
}