package de.dafuqs.starrysky.spheroid.lists;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.dimension.SpheroidDistributionType;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import de.dafuqs.starrysky.spheroid.types.RainbowSpheroidType;
import de.dafuqs.starrysky.spheroid.types.ShellSpheroidType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;

import java.util.ArrayList;

import static de.dafuqs.starrysky.dimension.SpheroidLoader.SpheroidDimensionType.OVERWORLD;
import static org.apache.logging.log4j.Level.INFO;

public class SpheroidListEcotones extends SpheroidList {

    private static final String MOD_ID = "ecotones";


    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateEcotonesSpheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        StarrySkyCommon.log(INFO, "Loading Ecotones integration...");

        BlockState blockus_limestone = getDefaultBlockState(MOD_ID,"limestone");
        BlockState blockus_marble = getDefaultBlockState(MOD_ID,"marble");
        BlockState blockus_bluestone = getDefaultBlockState(MOD_ID,"bluestone");
        BlockState blockus_white_oak_leaves = getDefaultBlockState(MOD_ID,"white_oak_leaves");


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
