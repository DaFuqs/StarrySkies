package de.dafuqs.starrysky.spheroidlists;

import de.dafuqs.starrysky.SpheroidLoader;
import de.dafuqs.starrysky.StarrySkyCommon;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SpheroidListIndustrialRevolution extends SpheroidList {

    private static final String MOD_ID = "indrev";


    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateIndustrialRevolutionSpheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        BlockState industrialrevolution_nikolite = Registry.BLOCK.get(new Identifier(MOD_ID,"nikolite_ore")).getDefaultState();
        BlockState industrialrevolution_copper = Registry.BLOCK.get(new Identifier(MOD_ID,"tin_ore")).getDefaultState();
        BlockState industrialrevolution_tin = Registry.BLOCK.get(new Identifier(MOD_ID,"copper_ore")).getDefaultState();

        spheroidLoader.registerDynamicOre("nikolite", industrialrevolution_nikolite);
        spheroidLoader.registerDynamicOre("copper", industrialrevolution_copper);
        spheroidLoader.registerDynamicOre("tin", industrialrevolution_tin);
    }

}
