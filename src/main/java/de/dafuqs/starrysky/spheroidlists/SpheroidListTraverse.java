package de.dafuqs.starrysky.spheroidlists;

import de.dafuqs.starrysky.SpheroidLoader;
import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.spheroidtypes.ShellSpheroidType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SpheroidListTraverse extends SpheroidList {

    private static final String MOD_ID = "taverse";

    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateTraverseSpheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        BlockState traverse_fir_leaves             = Registry.BLOCK.get(new Identifier(MOD_ID,"fir_leaves")).getDefaultState();
        BlockState traverse_fir_log                = Registry.BLOCK.get(new Identifier(MOD_ID,"fir_log")).getDefaultState();
        BlockState traverse_orange_autumnal_leaves = Registry.BLOCK.get(new Identifier(MOD_ID,"orange_autumnal_leaves")).getDefaultState(); // oak logs
        BlockState traverse_brown_autumnal_leaves  = Registry.BLOCK.get(new Identifier(MOD_ID,"brown_autumnal_leaves")).getDefaultState();  // oak logs
        BlockState traverse_red_autumnal_leaves    = Registry.BLOCK.get(new Identifier(MOD_ID,"red_autumnal_leaves")).getDefaultState();    // dark oak logs
        BlockState traverse_yellow_autumnal_leaves = Registry.BLOCK.get(new Identifier(MOD_ID,"yellow_autumnal_leaves")).getDefaultState(); // birch logs

        spheroidLoader.registerSpheroidType(SpheroidDistributionType.WOOD, 11.0F, new ShellSpheroidType(null, traverse_fir_log, traverse_fir_leaves,7, 14, 2, 4));
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.WOOD, 10.7F, new ShellSpheroidType(null, Blocks.OAK_LOG.getDefaultState(), traverse_orange_autumnal_leaves,6, 12, 2, 3));
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.WOOD, 10.7F, new ShellSpheroidType(null, Blocks.OAK_LOG.getDefaultState(), traverse_brown_autumnal_leaves,6, 12, 2, 3));
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.WOOD, 10.7F, new ShellSpheroidType(null, Blocks.DARK_OAK_LOG.getDefaultState(), traverse_red_autumnal_leaves,6, 12, 2, 3));
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.WOOD, 10.7F, new ShellSpheroidType(null, Blocks.BIRCH_LOG.getDefaultState(), traverse_yellow_autumnal_leaves,6, 12, 2, 3));
    }

}
