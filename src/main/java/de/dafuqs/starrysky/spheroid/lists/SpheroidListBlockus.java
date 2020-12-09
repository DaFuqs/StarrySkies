package de.dafuqs.starrysky.spheroid.lists;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.dimension.SpheroidDistributionType;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import de.dafuqs.starrysky.spheroid.types.RainbowSpheroidType;
import de.dafuqs.starrysky.spheroid.types.ShellSpheroidType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

import static de.dafuqs.starrysky.dimension.SpheroidLoader.SpheroidDimensionType.OVERWORLD;

public class SpheroidListBlockus extends SpheroidList {

    private static final String MOD_ID = "blockus";


    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateBlockusSpheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        StarrySkyCommon.LOGGER.info("Loading Blockus integration...");

        BlockState blockus_limestone = Registry.BLOCK.get(new Identifier(MOD_ID,"limestone")).getDefaultState();
        BlockState blockus_marble = Registry.BLOCK.get(new Identifier(MOD_ID,"marble")).getDefaultState();
        BlockState blockus_bluestone = Registry.BLOCK.get(new Identifier(MOD_ID,"bluestone")).getDefaultState();
        BlockState blockus_white_oak_leaves = Registry.BLOCK.get(new Identifier(MOD_ID,"white_oak_leaves")).getDefaultState();
        BlockState blockus_white_oak_log = Registry.BLOCK.get(new Identifier(MOD_ID,"white_oak_log")).getDefaultState();

        ArrayList<BlockState> rainbowBlocks = new ArrayList<BlockState>() {{
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"black_beveled_glass")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"blue_beveled_glass")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"brown_beveled_glass")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"cyan_beveled_glass")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"gray_beveled_glass")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"green_beveled_glass")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"light_blue_beveled_glass")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"light_gray_beveled_glass")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"lime_beveled_glass")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"magenta_beveled_glass")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"orange_beveled_glass")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"pink_beveled_glass")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"purple_beveled_glass")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"red_beveled_glass")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"white_beveled_glass")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"yellow_beveled_glass")).getDefaultState());
        }};

        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.3F, new RainbowSpheroidType(null, 7, 12, rainbowBlocks));
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.3F, new ShellSpheroidType(null, 7, 14, blockus_white_oak_log, blockus_white_oak_leaves.with(Properties.DISTANCE_1_7, 1),2, 4));

        // Add blocks to default lists
        SpheroidList.MAP_STONES.put(blockus_limestone, 0.5F);
        SpheroidList.MAP_STONES.put(blockus_marble, 0.5F);
        SpheroidList.MAP_STONES.put(blockus_bluestone, 0.5F);
    }

}
