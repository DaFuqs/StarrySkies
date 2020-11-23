package de.dafuqs.starrysky.spheroidlists;

import de.dafuqs.starrysky.SpheroidLoader;
import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.spheroidtypes.ShellSpheroidType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SpheroidListSakuraRosea extends SpheroidList {

    private static final String MOD_ID = "sakurarosea";

    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateSakuraRoseaSpheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        BlockState sakurarosea_sakura_log          = Registry.BLOCK.get(new Identifier(MOD_ID,"sakura_log")).getDefaultState();
        BlockState sakurarosea_sakura_leaves       = Registry.BLOCK.get(new Identifier(MOD_ID,"sakura_leaves")).getDefaultState();
        BlockState sakurarosea_alt_sakura_leaves   = Registry.BLOCK.get(new Identifier(MOD_ID,"alt_sakura_leaves")).getDefaultState();
        BlockState sakurarosea_white_sakura_leaves = Registry.BLOCK.get(new Identifier(MOD_ID,"white_sakura_leaves")).getDefaultState();

        spheroidLoader.registerSpheroidType(SpheroidDistributionType.WOOD, 0.7F, new ShellSpheroidType(null, sakurarosea_sakura_log, sakurarosea_sakura_leaves.with(Properties.DISTANCE_1_7, 1),7, 14, 2, 4));
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.WOOD, 0.7F, new ShellSpheroidType(null, sakurarosea_sakura_log, sakurarosea_alt_sakura_leaves.with(Properties.DISTANCE_1_7, 1),7, 14, 2, 4));
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.WOOD, 0.7F, new ShellSpheroidType(null, sakurarosea_sakura_log, sakurarosea_white_sakura_leaves.with(Properties.DISTANCE_1_7, 1),7, 14, 2, 4));
    }

}
