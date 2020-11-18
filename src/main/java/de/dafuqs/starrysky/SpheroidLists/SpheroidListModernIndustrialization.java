package de.dafuqs.starrysky.SpheroidLists;

import de.dafuqs.starrysky.spheroidtypes.SpheroidType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;

public class SpheroidListModernIndustrialization extends SpheroidList {

    private static final BlockState modern_industrialization_copper_ore = Registry.BLOCK.get(new Identifier("modern_industrialization","copper_ore")).getDefaultState();
    private static final BlockState modern_industrialization_tin_ore = Registry.BLOCK.get(new Identifier("modern_industrialization","tin_ore")).getDefaultState();
    private static final BlockState modern_industrialization_lead_ore = Registry.BLOCK.get(new Identifier("modern_industrialization","lead_ore")).getDefaultState();
    private static final BlockState modern_industrialization_lignite_coal_ore = Registry.BLOCK.get(new Identifier("modern_industrialization","lignite_coal_ore")).getDefaultState();
    private static final BlockState modern_industrialization_nickel_ore = Registry.BLOCK.get(new Identifier("modern_industrialization","nickel_ore")).getDefaultState();
    private static final BlockState modern_industrialization_salt_ore = Registry.BLOCK.get(new Identifier("modern_industrialization","salt_ore")).getDefaultState();
    private static final BlockState modern_industrialization_silver_ore = Registry.BLOCK.get(new Identifier("modern_industrialization","silver_ore")).getDefaultState();
    private static final BlockState modern_industrialization_antimony_ore = Registry.BLOCK.get(new Identifier("modern_industrialization","antimony_ore")).getDefaultState();
    private static final BlockState modern_industrialization_bauxite_ore = Registry.BLOCK.get(new Identifier("modern_industrialization","bauxite_ore")).getDefaultState();

    public static boolean isModPresent() {
        return FabricLoader.getInstance().isModLoaded("modern_industrialization");
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
