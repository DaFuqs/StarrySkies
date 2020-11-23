package de.dafuqs.starrysky.spheroidlists;

import de.dafuqs.starrysky.SpheroidLoader;
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
            spheroidLoader.registerSpheroidType(SpheroidDistributionType.ORE, 0.9F,  new CoreSpheroidType(SpheroidAdvancementIdentifier.coal, Registry.BLOCK.get(new Identifier(MOD_ID,stone + "_coal_ore")).getDefaultState(), stoneBlockState, 5, 15, 3, 7));
            spheroidLoader.registerSpheroidType(SpheroidDistributionType.ORE, 0.5F,  new CoreSpheroidType(SpheroidAdvancementIdentifier.iron, Registry.BLOCK.get(new Identifier(MOD_ID,stone + "_iron_ire")).getDefaultState(), stoneBlockState, 5, 12, 3, 5));
            spheroidLoader.registerSpheroidType(SpheroidDistributionType.ORE, 0.1F,  new CoreSpheroidType(SpheroidAdvancementIdentifier.gold, Registry.BLOCK.get(new Identifier(MOD_ID,stone + "_gold_ore")).getDefaultState(), stoneBlockState, 5, 10, 2, 4));
            spheroidLoader.registerSpheroidType(SpheroidDistributionType.ORE, 0.08F, new CoreSpheroidType(SpheroidAdvancementIdentifier.lapis, Registry.BLOCK.get(new Identifier(MOD_ID,stone + "_lapis_ore")).getDefaultState(), stoneBlockState, 5, 8, 2, 4));
            spheroidLoader.registerSpheroidType(SpheroidDistributionType.ORE, 0.4F,  new CoreSpheroidType(SpheroidAdvancementIdentifier.redstone, Registry.BLOCK.get(new Identifier(MOD_ID,stone + "_redstone_ore")).getDefaultState(), stoneBlockState, 5, 15, 4, 8));
            spheroidLoader.registerSpheroidType(SpheroidDistributionType.ORE, 0.05F, new CoreSpheroidType(SpheroidAdvancementIdentifier.diamond, Registry.BLOCK.get(new Identifier(MOD_ID,stone + "_diamond_ore")).getDefaultState(), stoneBlockState, 3, 7, 1, 3));
            spheroidLoader.registerSpheroidType(SpheroidDistributionType.ORE, 0.01F, new CoreSpheroidType(SpheroidAdvancementIdentifier.emerald, Registry.BLOCK.get(new Identifier(MOD_ID,stone + "_emerald_ore")).getDefaultState(), stoneBlockState, 5, 6, 1, 3));

        }

    }

}
