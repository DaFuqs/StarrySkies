package de.dafuqs.starrysky.spheroid.lists;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;

import static de.dafuqs.starrysky.dimension.SpheroidLoader.SpheroidDimensionType.OVERWORLD;
import static org.apache.logging.log4j.Level.INFO;

public class SpheroidListIndustrialRevolution extends SpheroidList {

    private static final String MOD_ID = "indrev";


    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateIndustrialRevolutionSpheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        StarrySkyCommon.log(INFO, "Loading Industrial Revolution integration...");

        BlockState industrialrevolution_nikolite = getDefaultBlockState(MOD_ID,"nikolite_ore");
        BlockState industrialrevolution_copper = getDefaultBlockState(MOD_ID,"tin_ore");
        BlockState industrialrevolution_tin = getDefaultBlockState(MOD_ID,"copper_ore");

        spheroidLoader.registerDynamicOre(OVERWORLD, "nikolite", industrialrevolution_nikolite);
        spheroidLoader.registerDynamicOre(OVERWORLD, "copper", industrialrevolution_copper);
        spheroidLoader.registerDynamicOre(OVERWORLD, "tin", industrialrevolution_tin);
    }

}
