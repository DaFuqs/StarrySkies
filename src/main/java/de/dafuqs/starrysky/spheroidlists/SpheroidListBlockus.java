package de.dafuqs.starrysky.spheroidlists;

import de.dafuqs.starrysky.SpheroidLoader;
import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.spheroidtypes.ShellSpheroidType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SpheroidListBlockus extends SpheroidList {

    private static final String MOD_ID = "blockus";


    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateBlockusSpheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        BlockState blockus_limestone = Registry.BLOCK.get(new Identifier(MOD_ID,"limestone")).getDefaultState();
        BlockState blockus_marble = Registry.BLOCK.get(new Identifier(MOD_ID,"marble")).getDefaultState();
        BlockState blockus_bluestone = Registry.BLOCK.get(new Identifier(MOD_ID,"bluestone")).getDefaultState();
        BlockState blockus_white_oak_leaves = Registry.BLOCK.get(new Identifier(MOD_ID,"white_oak_leaves")).getDefaultState();
        BlockState blockus_white_oak_log = Registry.BLOCK.get(new Identifier(MOD_ID,"white_oak_log")).getDefaultState();

        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 0.3F, new ShellSpheroidType(null, blockus_white_oak_log, blockus_white_oak_leaves.with(Properties.DISTANCE_1_7, 1),7, 14, 2, 4));

        // Add blocks to default lists
        SpheroidList.MAP_STONES.put(blockus_limestone, 0.5F);
        SpheroidList.MAP_STONES.put(blockus_marble, 0.5F);
        SpheroidList.MAP_STONES.put(blockus_bluestone, 0.5F);
    }

}
