package de.dafuqs.starrysky.spheroid.lists;

import de.dafuqs.starrysky.dimension.SpheroidDistributionType;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.spheroid.types.CoreSpheroidType;
import de.dafuqs.starrysky.spheroid.types.DoubleCoreSpheroidType;
import de.dafuqs.starrysky.spheroid.types.ModularSpheroidType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static de.dafuqs.starrysky.dimension.SpheroidLoader.SpheroidDimensionType.OVERWORLD;

public class SpheroidListAppliedEnergistics2 extends SpheroidList {

    private static final String MOD_ID = "appliedenergistics2";

    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateAppliedEnergistics2Spheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        StarrySkyCommon.LOGGER.info("Loading Applied Energistics 2 integration...");

        BlockState appliedenergistics_quartz          = Registry.BLOCK.get(new Identifier(MOD_ID,"quartz_ore")).getDefaultState();
        BlockState appliedenergistics_charged_quartz  = Registry.BLOCK.get(new Identifier(MOD_ID,"charged_quartz_ore")).getDefaultState();
        BlockState appliedenergistics_sky_stone       = Registry.BLOCK.get(new Identifier(MOD_ID,"sky_stone_block")).getDefaultState();

        Identifier  appliedenergistics_sky_stone_chest_loot_table = new Identifier(StarrySkyCommon.MOD_ID, "appliedenergistics2_meteor_chest");

        CoreSpheroidType       APPLIEDENERGISTRICS_QUARTZ         = new CoreSpheroidType(null, 5, 7, appliedenergistics_quartz, MAP_STONES, 5, 8);
        DoubleCoreSpheroidType APPLIEDENERGISTRICS_CHARGED_QUARTZ = new DoubleCoreSpheroidType(null, 5, 8, appliedenergistics_quartz, appliedenergistics_charged_quartz, MAP_STONES, 1, 2, 3, 5);
        ModularSpheroidType    APPLIEDENERGISTRICS_ASTEROID       = new ModularSpheroidType(null, 8, 14, appliedenergistics_sky_stone).addCenterChestWithLoot(appliedenergistics_sky_stone_chest_loot_table);

        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 0.8F, APPLIEDENERGISTRICS_QUARTZ);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 0.3F, APPLIEDENERGISTRICS_CHARGED_QUARTZ);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ESSENTIAL, 0.5F, APPLIEDENERGISTRICS_ASTEROID);
    }

}
