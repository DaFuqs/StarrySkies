package de.dafuqs.starryskies.spheroids.lists;

import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.data_loaders.SpheroidTemplateLoader;
import net.fabricmc.loader.api.FabricLoader;

import static org.apache.logging.log4j.Level.INFO;

public class SpheroidListTraverse extends SpheroidList {
	
	private static final String MOD_ID = "traverse";
	
	public static boolean shouldGenerate() {
		return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkies.CONFIG.generateTraverseSpheroids;
	}
	
	public static void setup(SpheroidTemplateLoader spheroidLoader) {
		StarrySkies.log(INFO, "Loading Traverse integration...");

        /*BlockState traverse_fir_leaves = getDefaultBlockState(MOD_ID, "fir_leaves").with(LeavesBlock.DISTANCE, 1);
        BlockState traverse_fir_log = getDefaultBlockState(MOD_ID, "fir_log");
        BlockState traverse_orange_autumnal_leaves = getDefaultBlockState(MOD_ID, "orange_autumnal_leaves").with(LeavesBlock.DISTANCE, 1); // oak logs
        BlockState traverse_brown_autumnal_leaves = getDefaultBlockState(MOD_ID, "brown_autumnal_leaves").with(LeavesBlock.DISTANCE, 1);  // oak logs
        BlockState traverse_red_autumnal_leaves = getDefaultBlockState(MOD_ID, "red_autumnal_leaves").with(LeavesBlock.DISTANCE, 1);    // dark oak logs
        BlockState traverse_yellow_autumnal_leaves = getDefaultBlockState(MOD_ID, "yellow_autumnal_leaves").with(LeavesBlock.DISTANCE, 1); // birch logs

        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 1.0F, new ShellSpheroidType(null, 7, 14, traverse_fir_log, traverse_fir_leaves, 2, 4));
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 0.7F, new ShellSpheroidType(null, 6, 12, Blocks.OAK_LOG.getDefaultState(), traverse_orange_autumnal_leaves, 2, 3));
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 0.7F, new ShellSpheroidType(null, 6, 12, Blocks.OAK_LOG.getDefaultState(), traverse_brown_autumnal_leaves, 2, 3));
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 0.7F, new ShellSpheroidType(null, 6, 12, Blocks.DARK_OAK_LOG.getDefaultState(), traverse_red_autumnal_leaves, 2, 3));
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 0.7F, new ShellSpheroidType(null, 6, 12, Blocks.BIRCH_LOG.getDefaultState(), traverse_yellow_autumnal_leaves, 2, 3));*/
	}
}
