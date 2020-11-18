package de.dafuqs.starrysky.SpheroidLists;

import de.dafuqs.starrysky.spheroidtypes.SpheroidType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;

public class SpheroidListModernIndustrialization extends SpheroidList {

    private static final String MOD_ID = "modern_industrialization";

    private static final BlockState modern_industrialization_copper_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"copper_ore")).getDefaultState();
    private static final BlockState modern_industrialization_tin_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"tin_ore")).getDefaultState();
    private static final BlockState modern_industrialization_lead_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"lead_ore")).getDefaultState();
    private static final BlockState modern_industrialization_lignite_coal_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"lignite_coal_ore")).getDefaultState();
    private static final BlockState modern_industrialization_nickel_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"nickel_ore")).getDefaultState();
    private static final BlockState modern_industrialization_salt_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"salt_ore")).getDefaultState();
    private static final BlockState modern_industrialization_silver_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"silver_ore")).getDefaultState();
    private static final BlockState modern_industrialization_antimony_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"antimony_ore")).getDefaultState();
    private static final BlockState modern_industrialization_bauxite_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"bauxite_ore")).getDefaultState();

    public static boolean isModPresent() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID);
    }

    public static LinkedHashMap<String, BlockState> getDictionaryEntries() {
        LinkedHashMap<String, BlockState> dictionary = new LinkedHashMap<>();
        dictionary.put("copper", modern_industrialization_copper_ore);
        dictionary.put("tin", modern_industrialization_tin_ore);
        dictionary.put("lignite_coal", modern_industrialization_lignite_coal_ore);
        dictionary.put("nickel", modern_industrialization_nickel_ore);
        dictionary.put("salt", modern_industrialization_salt_ore);
        dictionary.put("silver", modern_industrialization_silver_ore);
        dictionary.put("lead", modern_industrialization_lead_ore);
        dictionary.put("antimony", modern_industrialization_antimony_ore);
        dictionary.put("bauxite", modern_industrialization_bauxite_ore);
        return dictionary;
    }

}
