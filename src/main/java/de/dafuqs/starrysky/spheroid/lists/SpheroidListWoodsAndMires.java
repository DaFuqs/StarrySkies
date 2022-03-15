package de.dafuqs.starrysky.spheroid.lists;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.dimension.SpheroidDistributionType;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import de.dafuqs.starrysky.spheroid.types.ShellSpheroidType;
import de.dafuqs.starrysky.spheroid.types.SpheroidType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;

import static de.dafuqs.starrysky.dimension.SpheroidDimensionType.OVERWORLD;
import static org.apache.logging.log4j.Level.INFO;

public class SpheroidListWoodsAndMires extends SpheroidList {

    private static final String MOD_ID = "woods_and_mires";


    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateWoodsAndMiresSpheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        StarrySkyCommon.log(INFO, "Loading Woods And Mires integration...");

        BlockState pine_log = getDefaultBlockState(MOD_ID,"pine_log");
        BlockState pine_leaves = getDefaultBlockState(MOD_ID,"pine_leaves");
        BlockState fireweed = getDefaultBlockState(MOD_ID,"fireweed");
        BlockState tansy = getDefaultBlockState(MOD_ID,"tansy");

        SpheroidType PINE_WOOD = new ShellSpheroidType(null, 7, 12, pine_log, pine_leaves, 2, 4);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 0.7F, PINE_WOOD);

        LIST_TALL_FLOWERS.add(fireweed);
        LIST_FLOWERS.add(tansy);
    }

}
