package de.dafuqs.starrysky.dimension.biome;

import de.dafuqs.starrysky.StarrySkyCommon;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.layer.type.InitLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;


public enum StarrySkyBiomeLayer implements InitLayer {

    INSTANCE;

    // the biome files in data/worldgen/biome that should be available in that biome layer
    private static final Identifier STARRY_SKY_BIOME = new Identifier(StarrySkyCommon.MOD_ID, "starry_sky_biome");
    //private static final Identifier STARRY_SKY_BIOME_2 = new Identifier(StarrySkyCommon.MOD_ID, "starry_sky_biome2");

    //returns the biome for given coord
    public int sample(LayerRandomnessSource noise, int x, int z) {
        return StarrySkyBiomeProvider.layersBiomeRegistry.getRawId(StarrySkyBiomeProvider.layersBiomeRegistry.get(STARRY_SKY_BIOME));
    }

    public static void setSeed(long seed) { }
}