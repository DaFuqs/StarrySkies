package de.dafuqs.starrysky.spheroid.lists;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.dimension.SpheroidDistributionType;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import de.dafuqs.starrysky.spheroid.types.CoreSpheroidType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;

import java.util.ArrayList;

import static de.dafuqs.starrysky.dimension.SpheroidLoader.SpheroidDimensionType.OVERWORLD;
import static org.apache.logging.log4j.Level.INFO;

public class SpheroidListUnearthed extends SpheroidList {

    private static final String MOD_ID = "unearthed";

    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateUnearthedSpheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        StarrySkyCommon.log(INFO, "Loading Unearthed integration...");

        ArrayList<String> stones = new ArrayList<String>() {{
            add("gray_basalt");
            add("gabbro");
            add("pumice");
            add("kimberlite");
            add("rhyolite");
            add("slate");
            add("marble");
            add("quartzite");
            add("phyllite");
            add("limestone");
            add("lignite");
            add("siltstone");
            add("mudstone");
            add("conglomerate");
        }};

        for(String stone : stones) {
            BlockState stoneBlockState = getDefaultBlockState(MOD_ID, stone);

            // Probabilities are 1/10th of the vanilla counterparts
            spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 0.9F,  new CoreSpheroidType(SpheroidAdvancementIdentifier.coal, 5, 15, getDefaultBlockState(MOD_ID,stone + "_coal_ore"), stoneBlockState, 4, 8)); // works?
            spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 0.5F,  new CoreSpheroidType(SpheroidAdvancementIdentifier.iron, 5, 12, getDefaultBlockState(MOD_ID,stone + "_iron_ore"), stoneBlockState, 3, 5));
            spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 0.1F,  new CoreSpheroidType(SpheroidAdvancementIdentifier.gold, 5, 10, getDefaultBlockState(MOD_ID,stone + "_gold_ore"), stoneBlockState, 2, 4));
            spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 0.08F, new CoreSpheroidType(SpheroidAdvancementIdentifier.lapis, 5, 8, getDefaultBlockState(MOD_ID,stone + "_lapis_ore"), stoneBlockState, 2, 4));
            spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 0.4F,  new CoreSpheroidType(SpheroidAdvancementIdentifier.redstone, 5, 15, getDefaultBlockState(MOD_ID,stone + "_redstone_ore"), stoneBlockState, 4, 8)); // broken?
            spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 0.05F, new CoreSpheroidType(SpheroidAdvancementIdentifier.diamond, 3, 7, getDefaultBlockState(MOD_ID,stone + "_diamond_ore"), stoneBlockState, 1, 3));
            spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 0.01F, new CoreSpheroidType(SpheroidAdvancementIdentifier.emerald, 5, 6, getDefaultBlockState(MOD_ID,stone + "_emerald_ore"), stoneBlockState, 1, 3));

        }

    }

}
