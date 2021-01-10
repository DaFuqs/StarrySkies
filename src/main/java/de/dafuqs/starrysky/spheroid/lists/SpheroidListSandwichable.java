package de.dafuqs.starrysky.spheroid.lists;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import de.dafuqs.starrysky.dimension.decorators.GroundDecorator;
import de.dafuqs.starrysky.dimension.decorators.PlantDecorator;
import net.fabricmc.loader.api.FabricLoader;

public class SpheroidListSandwichable extends SpheroidList {

    private static final String MOD_ID = "sandwichable";

    private static final SpheroidDecorator SANDWICHABLE_SALTY_SAND_DECORATOR = new GroundDecorator(getDefaultBlockState(MOD_ID,"salty_sand"), 0.08F);
    private static final SpheroidDecorator SANDWICHABLE_SHRUB_DECORATOR = new PlantDecorator(getDefaultBlockState(MOD_ID,"shrub"), 0.04F);

    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateSandwichableSpheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        StarrySkyCommon.LOGGER.info("[StarrySky] Loading Sandwichable integration...");

        SpheroidListVanilla.SAND.addDecorator(SANDWICHABLE_SALTY_SAND_DECORATOR, 0.2F);
        SpheroidListVanilla.GRASS.addDecorator(SANDWICHABLE_SHRUB_DECORATOR, 0.3F);
    }

}
