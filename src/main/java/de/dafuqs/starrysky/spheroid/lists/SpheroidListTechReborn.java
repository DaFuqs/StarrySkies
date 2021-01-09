package de.dafuqs.starrysky.spheroid.lists;

import de.dafuqs.starrysky.dimension.SpheroidDistributionType;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.spheroid.types.ShellSpheroidType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static de.dafuqs.starrysky.dimension.SpheroidLoader.SpheroidDimensionType.*;

public class SpheroidListTechReborn extends SpheroidList {

    private static final String MOD_ID = "techreborn";
    
    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateTechRebornSpheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        StarrySkyCommon.LOGGER.info("[StarrySky] Loading Tech Reborn integration...");

        // Rubber
        BlockState techreborn_rubber_log = Registry.BLOCK.get(new Identifier(MOD_ID,"rubber_log")).getDefaultState();
        BlockState techreborn_rubber_leaves = Registry.BLOCK.get(new Identifier(MOD_ID,"rubber_leaves")).getDefaultState();

        // Overworld
        BlockState techreborn_bauxite_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"bauxite_ore")).getDefaultState();
        BlockState techreborn_copper_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"copper_ore")).getDefaultState();
        BlockState techreborn_galena_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"galena_ore")).getDefaultState();
        BlockState techreborn_iridium_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"iridium_ore")).getDefaultState();
        BlockState techreborn_lead_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"lead_ore")).getDefaultState();
        BlockState techreborn_ruby_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"ruby_ore")).getDefaultState();
        BlockState techreborn_sapphire_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"sapphire_ore")).getDefaultState();
        BlockState techreborn_silver_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"silver_ore")).getDefaultState();
        BlockState techreborn_tin_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"tin_ore")).getDefaultState();

        // Nether
        BlockState techreborn_cinnabar_ore = Registry.BLOCK.get(new Identifier("techreborn","cinnabar_ore")).getDefaultState();
        BlockState techreborn_pyrite_ore = Registry.BLOCK.get(new Identifier("techreborn","pyrite_ore")).getDefaultState();
        BlockState techreborn_sphalerite_ore = Registry.BLOCK.get(new Identifier("techreborn","sphalerite_ore")).getDefaultState();

        // End
        BlockState techreborn_peridot_ore = Registry.BLOCK.get(new Identifier("techreborn","peridot_ore")).getDefaultState();
        BlockState techreborn_sheldonite_ore = Registry.BLOCK.get(new Identifier("techreborn","sheldonite_ore")).getDefaultState();
        BlockState techreborn_sodalite_ore = Registry.BLOCK.get(new Identifier("techreborn","sodalite_ore")).getDefaultState();
        BlockState techreborn_tungsten_ore = Registry.BLOCK.get(new Identifier("techreborn","tungsten_ore")).getDefaultState();

        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 2.5F, new ShellSpheroidType(null, 8, 14, techreborn_rubber_log, techreborn_rubber_leaves.with(Properties.DISTANCE_1_7, 1), 2, 4));

        spheroidLoader.registerDynamicOre(OVERWORLD, "bauxite", techreborn_bauxite_ore);
        spheroidLoader.registerDynamicOre(OVERWORLD, "copper", techreborn_copper_ore);
        spheroidLoader.registerDynamicOre(OVERWORLD, "galena", techreborn_galena_ore);
        spheroidLoader.registerDynamicOre(OVERWORLD, "iridium", techreborn_iridium_ore);
        spheroidLoader.registerDynamicOre(OVERWORLD, "lead", techreborn_lead_ore);
        spheroidLoader.registerDynamicOre(OVERWORLD, "ruby", techreborn_ruby_ore);
        spheroidLoader.registerDynamicOre(OVERWORLD, "sapphire", techreborn_sapphire_ore);
        spheroidLoader.registerDynamicOre(OVERWORLD, "silver", techreborn_silver_ore);
        spheroidLoader.registerDynamicOre(OVERWORLD, "tin", techreborn_tin_ore);

        spheroidLoader.registerDynamicOre(NETHER, "cinnabar", techreborn_cinnabar_ore);
        spheroidLoader.registerDynamicOre(NETHER, "pyrite", techreborn_pyrite_ore);
        spheroidLoader.registerDynamicOre(NETHER, "sphalerite", techreborn_sphalerite_ore);

        spheroidLoader.registerDynamicOre(END, "peridot", techreborn_peridot_ore);
        spheroidLoader.registerDynamicOre(END, "sheldonite", techreborn_sheldonite_ore);
        spheroidLoader.registerDynamicOre(END, "sodalite", techreborn_sodalite_ore);
        spheroidLoader.registerDynamicOre(END, "tungsten", techreborn_tungsten_ore);
    }

}
