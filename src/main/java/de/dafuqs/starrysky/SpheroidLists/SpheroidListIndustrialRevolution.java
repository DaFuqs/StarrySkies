package de.dafuqs.starrysky.SpheroidLists;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;

public class SpheroidListIndustrialRevolution extends SpheroidList {

    private static final BlockState industrialrevolution_nikolite = Registry.BLOCK.get(new Identifier("indrev","nikolite_ore")).getDefaultState();

    public static boolean isModPresent() {
        return FabricLoader.getInstance().isModLoaded("indrev");
    }

    public static LinkedHashMap<String, BlockState> getDictionaryEntries() {
        LinkedHashMap<String, BlockState> dictionary = new LinkedHashMap<>();
        dictionary.put("nikolite", industrialrevolution_nikolite);
        return dictionary;
    }

}
