package de.dafuqs.starrysky.spheroidlists;

import de.dafuqs.starrysky.dimension.SpheroidDistributionType;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.spheroidtypes.CoreSpheroidType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

public class SpheroidListUnearthed extends SpheroidList {

    private static final String MOD_ID = "unearthed";

    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateUnearthedSpheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {

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
            BlockState stoneBlockState = Registry.BLOCK.get(new Identifier(MOD_ID, stone)).getDefaultState();

            // Probabilities are 1/10th of the vanilla counterparts
            spheroidLoader.registerSpheroidType(SpheroidDistributionType.ORE, 0.9F,  new CoreSpheroidType(SpheroidAdvancementIdentifier.coal, 5, 15, Registry.BLOCK.get(new Identifier(MOD_ID,stone + "_coal_ore")).getDefaultState(), stoneBlockState, 3, 7));
            spheroidLoader.registerSpheroidType(SpheroidDistributionType.ORE, 0.5F,  new CoreSpheroidType(SpheroidAdvancementIdentifier.iron, 5, 12, Registry.BLOCK.get(new Identifier(MOD_ID,stone + "_iron_ire")).getDefaultState(), stoneBlockState, 3, 5));
            spheroidLoader.registerSpheroidType(SpheroidDistributionType.ORE, 0.1F,  new CoreSpheroidType(SpheroidAdvancementIdentifier.gold, 5, 10, Registry.BLOCK.get(new Identifier(MOD_ID,stone + "_gold_ore")).getDefaultState(), stoneBlockState, 2, 4));
            spheroidLoader.registerSpheroidType(SpheroidDistributionType.ORE, 0.08F, new CoreSpheroidType(SpheroidAdvancementIdentifier.lapis, 5, 8, Registry.BLOCK.get(new Identifier(MOD_ID,stone + "_lapis_ore")).getDefaultState(), stoneBlockState, 2, 4));
            spheroidLoader.registerSpheroidType(SpheroidDistributionType.ORE, 0.4F,  new CoreSpheroidType(SpheroidAdvancementIdentifier.redstone, 5, 15, Registry.BLOCK.get(new Identifier(MOD_ID,stone + "_redstone_ore")).getDefaultState(), stoneBlockState, 4, 8));
            spheroidLoader.registerSpheroidType(SpheroidDistributionType.ORE, 0.05F, new CoreSpheroidType(SpheroidAdvancementIdentifier.diamond, 3, 7, Registry.BLOCK.get(new Identifier(MOD_ID,stone + "_diamond_ore")).getDefaultState(), stoneBlockState, 1, 3));
            spheroidLoader.registerSpheroidType(SpheroidDistributionType.ORE, 0.01F, new CoreSpheroidType(SpheroidAdvancementIdentifier.emerald, 5, 6, Registry.BLOCK.get(new Identifier(MOD_ID,stone + "_emerald_ore")).getDefaultState(), stoneBlockState, 1, 3));

        }

    }

}
