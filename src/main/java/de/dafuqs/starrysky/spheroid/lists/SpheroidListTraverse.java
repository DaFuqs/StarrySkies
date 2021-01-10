package de.dafuqs.starrysky.spheroid.lists;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.dimension.SpheroidDistributionType;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import de.dafuqs.starrysky.spheroid.types.ShellSpheroidType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;

import static de.dafuqs.starrysky.dimension.SpheroidLoader.SpheroidDimensionType.OVERWORLD;

public class SpheroidListTraverse extends SpheroidList {

    private static final String MOD_ID = "traverse";

    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateTraverseSpheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        StarrySkyCommon.LOGGER.info("[StarrySky] Loading Traverse integration...");

        BlockState traverse_fir_leaves = getDefaultBlockState(MOD_ID, "fir_leaves").with(LeavesBlock.DISTANCE, 1);
        BlockState traverse_fir_log = getDefaultBlockState(MOD_ID, "fir_log");
        BlockState traverse_orange_autumnal_leaves = getDefaultBlockState(MOD_ID, "orange_autumnal_leaves").with(LeavesBlock.DISTANCE, 1); // oak logs
        BlockState traverse_brown_autumnal_leaves = getDefaultBlockState(MOD_ID, "brown_autumnal_leaves").with(LeavesBlock.DISTANCE, 1);  // oak logs
        BlockState traverse_red_autumnal_leaves = getDefaultBlockState(MOD_ID, "red_autumnal_leaves").with(LeavesBlock.DISTANCE, 1);    // dark oak logs
        BlockState traverse_yellow_autumnal_leaves = getDefaultBlockState(MOD_ID, "yellow_autumnal_leaves").with(LeavesBlock.DISTANCE, 1); // birch logs

        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 1.0F, new ShellSpheroidType(null, 7, 14, traverse_fir_log, traverse_fir_leaves, 2, 4));
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 0.7F, new ShellSpheroidType(null, 6, 12, Blocks.OAK_LOG.getDefaultState(), traverse_orange_autumnal_leaves, 2, 3));
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 0.7F, new ShellSpheroidType(null, 6, 12, Blocks.OAK_LOG.getDefaultState(), traverse_brown_autumnal_leaves, 2, 3));
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 0.7F, new ShellSpheroidType(null, 6, 12, Blocks.DARK_OAK_LOG.getDefaultState(), traverse_red_autumnal_leaves, 2, 3));
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 0.7F, new ShellSpheroidType(null, 6, 12, Blocks.BIRCH_LOG.getDefaultState(), traverse_yellow_autumnal_leaves, 2, 3));

    }
}
