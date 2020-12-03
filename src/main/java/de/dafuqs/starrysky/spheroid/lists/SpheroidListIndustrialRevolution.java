package de.dafuqs.starrysky.spheroid.lists;

import de.dafuqs.starrysky.dimension.SpheroidLoader;
import de.dafuqs.starrysky.StarrySkyCommon;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static de.dafuqs.starrysky.dimension.SpheroidLoader.SpheroidDimensionType.OVERWORLD;

public class SpheroidListIndustrialRevolution extends SpheroidList {

    private static final String MOD_ID = "indrev";


    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateIndustrialRevolutionSpheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        StarrySkyCommon.LOGGER.info("Loading Industrial Revolution integration...");

        BlockState industrialrevolution_nikolite = Registry.BLOCK.get(new Identifier(MOD_ID,"nikolite_ore")).getDefaultState();
        BlockState industrialrevolution_copper = Registry.BLOCK.get(new Identifier(MOD_ID,"tin_ore")).getDefaultState();
        BlockState industrialrevolution_tin = Registry.BLOCK.get(new Identifier(MOD_ID,"copper_ore")).getDefaultState();

        spheroidLoader.registerDynamicOre(OVERWORLD, "nikolite", industrialrevolution_nikolite);
        spheroidLoader.registerDynamicOre(OVERWORLD, "copper", industrialrevolution_copper);
        spheroidLoader.registerDynamicOre(OVERWORLD, "tin", industrialrevolution_tin);
    }

}
