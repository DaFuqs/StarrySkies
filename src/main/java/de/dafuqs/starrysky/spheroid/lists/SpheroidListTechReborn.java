package de.dafuqs.starrysky.spheroid.lists;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.dimension.SpheroidDistributionType;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import de.dafuqs.starrysky.spheroid.types.ShellSpheroidType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;

import static de.dafuqs.starrysky.dimension.SpheroidDimensionType.*;
import static org.apache.logging.log4j.Level.INFO;

public class SpheroidListTechReborn extends SpheroidList {

    private static final String MOD_ID = "techreborn";
    
    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateTechRebornSpheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        StarrySkyCommon.log(INFO, "Loading Tech Reborn integration...");

        // Rubber
        BlockState techreborn_rubber_log = getDefaultBlockState(MOD_ID,"rubber_log");
        BlockState techreborn_rubber_leaves = getDefaultBlockState(MOD_ID,"rubber_leaves");

        // Overworld
        BlockState techreborn_bauxite_ore = getDefaultBlockState(MOD_ID,"bauxite_ore");
        BlockState techreborn_galena_ore = getDefaultBlockState(MOD_ID,"galena_ore");
        BlockState techreborn_iridium_ore = getDefaultBlockState(MOD_ID,"iridium_ore");
        BlockState techreborn_lead_ore = getDefaultBlockState(MOD_ID,"lead_ore");
        BlockState techreborn_ruby_ore = getDefaultBlockState(MOD_ID,"ruby_ore");
        BlockState techreborn_sapphire_ore = getDefaultBlockState(MOD_ID,"sapphire_ore");
        BlockState techreborn_silver_ore = getDefaultBlockState(MOD_ID,"silver_ore");
        BlockState techreborn_tin_ore = getDefaultBlockState(MOD_ID,"tin_ore");

        // Nether
        BlockState techreborn_cinnabar_ore = getDefaultBlockState("techreborn","cinnabar_ore");
        BlockState techreborn_pyrite_ore = getDefaultBlockState("techreborn","pyrite_ore");
        BlockState techreborn_sphalerite_ore = getDefaultBlockState("techreborn","sphalerite_ore");

        // End
        BlockState techreborn_peridot_ore = getDefaultBlockState("techreborn","peridot_ore");
        BlockState techreborn_sheldonite_ore = getDefaultBlockState("techreborn","sheldonite_ore");
        BlockState techreborn_sodalite_ore = getDefaultBlockState("techreborn","sodalite_ore");
        BlockState techreborn_tungsten_ore = getDefaultBlockState("techreborn","tungsten_ore");

        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 2.5F, new ShellSpheroidType(null, 8, 14, techreborn_rubber_log, techreborn_rubber_leaves.with(Properties.DISTANCE_1_7, 1), 2, 4));

        spheroidLoader.registerDynamicOre(OVERWORLD, "bauxite", techreborn_bauxite_ore);
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
