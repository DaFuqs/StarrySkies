package de.dafuqs.starrysky.dimension.sky;

import de.dafuqs.starrysky.StarrySkyCommon;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.LinkedHashMap;

import static org.apache.logging.log4j.Level.WARN;

/**
 * Renderer for the custom skybox
 */
public class StarrySkyBox {

    public static final Identifier UP = new Identifier("skybox", "up");
    public static final Identifier DOWN = new Identifier("skybox", "down");
    public static final Identifier WEST = new Identifier("skybox", "west");
    public static final Identifier EAST = new Identifier("skybox", "east");
    public static final Identifier NORTH = new Identifier("skybox", "north");
    public static final Identifier SOUTH = new Identifier("skybox", "south");

    public LinkedHashMap<Identifier, Identifier> textures = new LinkedHashMap<>();

    long lastTime = 0;

    public StarrySkyBox(String up, String down, String west, String east, String north, String south) {
        this.textures.put(UP, new Identifier(StarrySkyCommon.MOD_ID, up));
        this.textures.put(DOWN, new Identifier(StarrySkyCommon.MOD_ID, down));
        this.textures.put(WEST, new Identifier(StarrySkyCommon.MOD_ID, west));
        this.textures.put(EAST, new Identifier(StarrySkyCommon.MOD_ID, east));
        this.textures.put(NORTH, new Identifier(StarrySkyCommon.MOD_ID, north));
        this.textures.put(SOUTH, new Identifier(StarrySkyCommon.MOD_ID, south));
    }

    public void render(MatrixStack matrices, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        TextureManager textureManager = client.getTextureManager();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        World world = client.world;

        if (world == null) {
            return;
        }

        GameOptions options = MinecraftClient.getInstance().options;
        float distance = 16F * (float) Option.RENDER_DISTANCE.get(options) - 8F;

        //float rotation = (world.getTimeOfDay() / 12000f) * 360; // don't rotate at all
        int color = (int) Math.abs(((Math.abs(world.getTime()-6000)-12000)/47)); // 47 = 12000 (half day)  /255 (max hue)
        int rawLight = (int) ((world.getTimeOfDay() / 12000) % 15); // a day is 24000; max lightlevel = 15
        int vertexLight = 0x00f000f0 >> 2 | rawLight >> 3 | rawLight;

        for (int i = 0; i < 6; ++i) {
            matrices.push();
            switch (i) {
                case 0: {
                    textureManager.bindTexture(this.textures.get(DOWN));
                    break;
                }
                case 1: {
                    textureManager.bindTexture(this.textures.get(WEST));
                    matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90.0F));
                    break;
                }
                case 2: {
                    textureManager.bindTexture(this.textures.get(EAST));
                    matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-90.0F));
                    break;
                }
                case 3: {
                    textureManager.bindTexture(this.textures.get(UP));
                    matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(180.0F));
                    break;
                }
                case 4: {
                    textureManager.bindTexture(this.textures.get(NORTH));
                    matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
                    break;
                }
                case 5: {
                    textureManager.bindTexture(this.textures.get(SOUTH));
                    matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(-90.0F));
                    break;
                }
            }

            if(lastTime != world.getTime()) {
                StarrySkyCommon.LOGGER.log(WARN, "Time: " + world.getTime() + " TimeOfDay: " + world.getTimeOfDay() + " Color: " + color + " Vertex: " + vertexLight);
                lastTime = world.getTime();
            }

            buffer.begin(7, VertexFormats.POSITION_COLOR_TEXTURE_LIGHT);
            buffer.vertex(matrices.peek().getModel(), -distance, -distance, -distance).color(color, color, color, color).texture(0.0F, 0.0F).light(vertexLight).next();
            buffer.vertex(matrices.peek().getModel(), -distance, -distance, distance).color(color, color, color, color).texture(0.0F, 1.0F).light(vertexLight).next();
            buffer.vertex(matrices.peek().getModel(), distance, -distance, distance).color(color, color, color, color).texture(1.0F, 1.0F).light(vertexLight).next();
            buffer.vertex(matrices.peek().getModel(), distance, -distance, -distance).color(color, color, color, color).texture(1.0F, 0.0F).light(vertexLight).next();

            tessellator.draw();
            matrices.pop();
        }

    }

}