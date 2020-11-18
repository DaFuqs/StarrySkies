package de.dafuqs.starrysky.SpheroidLists;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;

public class SpheroidListTechReborn extends SpheroidList {

    private static final String MOD_ID = "techreborn";

    // Overworld
    private static final BlockState techreborn_bauxite_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"bauxite_ore")).getDefaultState();
    private static final BlockState techreborn_copper_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"copper_ore")).getDefaultState();
    private static final BlockState techreborn_galena_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"galena_ore")).getDefaultState();
    private static final BlockState techreborn_iridium_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"iridium_ore")).getDefaultState();
    private static final BlockState techreborn_lead_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"lead_ore")).getDefaultState();
    private static final BlockState techreborn_ruby_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"ruby_ore")).getDefaultState();
    private static final BlockState techreborn_sapphire_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"sapphire_ore")).getDefaultState();
    private static final BlockState techreborn_silver_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"silver_ore")).getDefaultState();
    private static final BlockState techreborn_tin_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"tin_ore")).getDefaultState();

    // Nether
    //private static final BlockState techreborn_cinnabar_ore = Registry.BLOCK.get(new Identifier("techreborn","cinnabar_ore")).getDefaultState();
    //private static final BlockState techreborn_pyrite_ore = Registry.BLOCK.get(new Identifier("techreborn","pyrite_ore")).getDefaultState();
    //private static final BlockState techreborn_sphalerite_ore = Registry.BLOCK.get(new Identifier("techreborn","sphalerite_ore")).getDefaultState();

    // End
    //private static final BlockState techreborn_peridot_ore = Registry.BLOCK.get(new Identifier("techreborn","peridot_ore")).getDefaultState();
    //private static final BlockState techreborn_sheldonite_ore = Registry.BLOCK.get(new Identifier("techreborn","sheldonite_ore")).getDefaultState();
    //private static final BlockState techreborn_sodalite_ore = Registry.BLOCK.get(new Identifier("techreborn","sodalite_ore")).getDefaultState();
    //private static final BlockState techreborn_tungsten_ore = Registry.BLOCK.get(new Identifier("techreborn","tungsten_ore")).getDefaultState();

    public static boolean isModPresent() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID);
    }

    public static LinkedHashMap<String, BlockState> getDictionaryEntries() {
        LinkedHashMap<String, BlockState> dictionary = new LinkedHashMap<>();
        dictionary.put("bauxite", techreborn_bauxite_ore);
        dictionary.put("copper", techreborn_copper_ore);
        dictionary.put("galena", techreborn_galena_ore);
        dictionary.put("iridium", techreborn_iridium_ore);
        dictionary.put("lead", techreborn_lead_ore);
        dictionary.put("ruby", techreborn_ruby_ore);
        dictionary.put("sapphire", techreborn_sapphire_ore);
        dictionary.put("silver", techreborn_silver_ore);
        dictionary.put("tin", techreborn_tin_ore);
        return dictionary;
    }

}
