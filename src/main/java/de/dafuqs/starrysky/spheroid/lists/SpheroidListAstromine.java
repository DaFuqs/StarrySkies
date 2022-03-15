package de.dafuqs.starrysky.spheroid.lists;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.dimension.SpheroidDistributionType;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import de.dafuqs.starrysky.spheroid.types.CoreSpheroidType;
import de.dafuqs.starrysky.spheroid.types.LiquidSpheroidType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;

import static de.dafuqs.starrysky.dimension.SpheroidDimensionType.OVERWORLD;
import static org.apache.logging.log4j.Level.INFO;

public class SpheroidListAstromine extends SpheroidList {

    private static final String MOD_ID = "astromine";

    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateAstromineSpheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        StarrySkyCommon.log(INFO, "Loading Astromine integration...");

        BlockState astromine_crude_oil         = getDefaultBlockState(MOD_ID,"crude_oil");
        BlockState astromine_copper_ore        = getDefaultBlockState(MOD_ID,"copper_ore");
        BlockState astromine_tin_ore           = getDefaultBlockState(MOD_ID,"tin_ore");
        BlockState astromine_silver_ore        = getDefaultBlockState(MOD_ID,"silver_ore");
        BlockState astromine_lead_ore          = getDefaultBlockState(MOD_ID,"lead_ore");
        BlockState astromine_meteor_metite_ore = getDefaultBlockState(MOD_ID,"meteor_metite_ore");
        BlockState astromine_meteor_stone      = getDefaultBlockState(MOD_ID,"meteor_stone");

        CoreSpheroidType ASTROMINE_METEORITE         = new CoreSpheroidType(null, 8, 13, astromine_meteor_metite_ore, astromine_meteor_stone, 5, 8);
        LiquidSpheroidType ASTROMINE_CRUDE_OIL_STONE = new LiquidSpheroidType(null, 8, 15, astromine_crude_oil, MAP_STONES, 5, 8, 50, 100, 30);
        LiquidSpheroidType ASTROMINE_CRUDE_OIL_GLASS = new LiquidSpheroidType(null, 7, 12, astromine_crude_oil, MAP_GLASS, 2, 3, 50, 80, 50);

        spheroidLoader.registerDynamicOre(OVERWORLD, "lead", astromine_lead_ore);
        spheroidLoader.registerDynamicOre(OVERWORLD, "silver", astromine_silver_ore);
        spheroidLoader.registerDynamicOre(OVERWORLD, "tin", astromine_tin_ore);
        spheroidLoader.registerDynamicOre(OVERWORLD, "copper", astromine_copper_ore);

        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 0.5F, ASTROMINE_METEORITE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.FLUID, 1.0F, ASTROMINE_CRUDE_OIL_STONE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.FLUID, 1.0F, ASTROMINE_CRUDE_OIL_GLASS);
    }

}
