package de.dafuqs.starrysky.generation.layer;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.generation.StarrySkyBiomeProvider;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.layer.type.CrossSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;


public enum StarrySkyBiomeScalePillarLayer implements CrossSamplingLayer {

    INSTANCE;

    // the biome files in data/worldgen/biome that should be available in that biome layer
    private static final Identifier STARRY_SKY_BIOME = new Identifier(StarrySkyCommon.MOD_ID, "starry_sky_biome");
    //private static final Identifier STARRY_SKY_BIOME_2 = new Identifier(StarrySkyCommon.MOD_ID, "starry_sky_biome2");

    public int sample(LayerRandomnessSource context, int n, int e, int s, int w, int center) {
        int rawId = StarrySkyBiomeProvider.layersBiomeRegistry.getRawId(StarrySkyBiomeProvider.layersBiomeRegistry.get(STARRY_SKY_BIOME));
        if(center != rawId){
            boolean borderingHivePillar = false;

            if((n == rawId ||
                e == rawId) ||
                    (w == rawId ||
                    s == rawId)) {
                borderingHivePillar = true;
            }

            if (borderingHivePillar) {
                return rawId;
            }
        }
        return center;
    }
}