package de.dafuqs.starrysky.spheroidlists;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;

public class SpheroidListIndustrialRevolution extends SpheroidList {

    private static final String MOD_ID = "indrev";

    private static final BlockState industrialrevolution_nikolite = Registry.BLOCK.get(new Identifier(MOD_ID,"nikolite_ore")).getDefaultState();
    private static final BlockState industrialrevolution_copper = Registry.BLOCK.get(new Identifier(MOD_ID,"tin_ore")).getDefaultState();
    private static final BlockState industrialrevolution_tin = Registry.BLOCK.get(new Identifier(MOD_ID,"copper_ore")).getDefaultState();

    public static boolean isModPresent() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID);
    }

    public static LinkedHashMap<String, BlockState> getDictionaryEntries() {
        LinkedHashMap<String, BlockState> dictionary = new LinkedHashMap<>();
        dictionary.put("nikolite", industrialrevolution_nikolite);
        dictionary.put("copper", industrialrevolution_copper);
        dictionary.put("tin", industrialrevolution_tin);
        return dictionary;
    }

}
