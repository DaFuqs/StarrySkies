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

        ArrayList<BlockState> rainbowBeveled = new ArrayList<BlockState>() {{
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

        ArrayList<BlockState> rainbowAsphalt = new ArrayList<BlockState>() {{
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"black_asphalt")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"blue_asphalt")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"brown_asphalt")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"cyan_asphalt")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"gray_asphalt")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"green_asphalt")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"light_blue_asphalt")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"light_gray_asphalt")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"lime_asphalt")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"magenta_asphalt")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"orange_asphalt")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"pink_asphalt")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"purple_asphalt")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"red_asphalt")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"white_asphalt")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"yellow_asphalt")).getDefaultState());
        }};

        ArrayList<BlockState> rainbowFuturneo = new ArrayList<BlockState>() {{
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"black_futurneo_block")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"blue_futurneo_block")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"brown_futurneo_block")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"cyan_futurneo_block")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"gray_futurneo_block")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"green_futurneo_block")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"light_blue_futurneo_block")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"light_gray_futurneo_block")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"lime_futurneo_block")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"magenta_futurneo_block")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"orange_futurneo_block")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"pink_futurneo_block")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"purple_futurneo_block")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"red_futurneo_block")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"white_futurneo_block")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID,"yellow_futurneo_block")).getDefaultState());
        }};

        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.1F, new RainbowSpheroidType(null, 7, 12, rainbowFuturneo));
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.1F, new RainbowSpheroidType(null, 7, 12, rainbowBeveled));
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.1F, new RainbowSpheroidType(null, 7, 12, rainbowAsphalt));
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.3F, new ShellSpheroidType(null, 7, 14, blockus_white_oak_log, blockus_white_oak_leaves.with(Properties.DISTANCE_1_7, 1),2, 4));

        // Add blocks to default lists
        SpheroidList.MAP_STONES.put(blockus_limestone, 0.5F);
        SpheroidList.MAP_STONES.put(blockus_marble, 0.5F);
        SpheroidList.MAP_STONES.put(blockus_bluestone, 0.5F);
    }

}
