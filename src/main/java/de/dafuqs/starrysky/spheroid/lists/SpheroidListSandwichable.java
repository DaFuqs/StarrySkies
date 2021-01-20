package de.dafuqs.starrysky.spheroid.lists;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import de.dafuqs.starrysky.dimension.decorators.GroundDecorator;
import de.dafuqs.starrysky.dimension.decorators.PlantDecorator;
import net.fabricmc.loader.api.FabricLoader;

import static org.apache.logging.log4j.Level.INFO;

public class SpheroidListSandwichable extends SpheroidList {

    private static final String MOD_ID = "sandwichable";

    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateSandwichableSpheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        StarrySkyCommon.log(INFO, "Loading Sandwichable integration...");

        SpheroidDecorator SANDWICHABLE_SALTY_SAND_DECORATOR = new GroundDecorator(getDefaultBlockState(MOD_ID, "salty_sand"), 0.08F);
        SpheroidDecorator SANDWICHABLE_SHRUB_DECORATOR = new PlantDecorator(getDefaultBlockState(MOD_ID, "shrub"), 0.04F);

        SpheroidListVanilla.SAND.addDecorator(SANDWICHABLE_SALTY_SAND_DECORATOR, 0.2F);
        SpheroidListVanilla.GRASS.addDecorator(SANDWICHABLE_SHRUB_DECORATOR, 0.3F);
    }

}
