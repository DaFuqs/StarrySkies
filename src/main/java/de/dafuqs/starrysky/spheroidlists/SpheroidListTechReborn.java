package de.dafuqs.starrysky.spheroidlists;

import de.dafuqs.starrysky.spheroidtypes.ShellSpheroidType;
import de.dafuqs.starrysky.spheroidtypes.SpheroidType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;

public class SpheroidListTechReborn extends SpheroidList {

    private static final String MOD_ID = "techreborn";

    // Rubber
    private static final BlockState techreborn_rubber_log = Registry.BLOCK.get(new Identifier(MOD_ID,"rubber_log")).getDefaultState();
    private static final BlockState techreborn_rubber_leaves = Registry.BLOCK.get(new Identifier(MOD_ID,"rubber_leaves")).getDefaultState();

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

    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID);
    }

    public static LinkedHashMap<SpheroidType, Float> getSpheroidTypesWithProbabilities() {
        LinkedHashMap<SpheroidType, Float> spheroidTypes = new LinkedHashMap<>();

        // only initialize rubber leaves block with tags when tech reborn is present (else: crash)
        spheroidTypes.put(new ShellSpheroidType(null, techreborn_rubber_log, techreborn_rubber_leaves.with(Properties.DISTANCE_1_7, 1),5, 20, 2, 4), 0.5F);

        return spheroidTypes;
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
