package de.dafuqs.starrysky.spheroidlists;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.spheroidtypes.CoreSpheroidType;
import de.dafuqs.starrysky.spheroidtypes.DoubleCoreSpheroidType;
import de.dafuqs.starrysky.spheroidtypes.ModularSpheroidType;
import de.dafuqs.starrysky.spheroidtypes.SpheroidType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;

public class SpheroidListAppliedEnergistics2 extends SpheroidList {

    private static final String MOD_ID = "appliedenergistics2";

    private static final BlockState appliedenergistics_quartz          = Registry.BLOCK.get(new Identifier(MOD_ID,"quartz_ore")).getDefaultState();
    private static final BlockState appliedenergistics_charged_quartz  = Registry.BLOCK.get(new Identifier(MOD_ID,"charged_quartz_ore")).getDefaultState();
    private static final BlockState appliedenergistics_sky_stone       = Registry.BLOCK.get(new Identifier(MOD_ID,"sky_stone_block")).getDefaultState();
    // private static final BlockState appliedenergistics_sky_stone_chest = Registry.BLOCK.get(new Identifier(MOD_ID, "sky_stone_chest")).getDefaultState(); // TODO: no good way to get the BlockEntity?

    private static final Identifier  appliedenergistics_sky_stone_chest_loot_table = new Identifier(StarrySkyCommon.MOD_ID, "appliedenergistics2_meteor_chest");

    public static final CoreSpheroidType       APPLIEDENERGISTRICS_QUARTZ         = new CoreSpheroidType(null, appliedenergistics_quartz, MAP_STONES, 5, 7, 5, 8);
    public static final DoubleCoreSpheroidType APPLIEDENERGISTRICS_CHARGED_QUARTZ = new DoubleCoreSpheroidType(null, appliedenergistics_quartz, appliedenergistics_charged_quartz, MAP_STONES, 5, 8, 1, 2, 3, 5);
    public static final ModularSpheroidType    APPLIEDENERGISTRICS_ASTEROID       = new ModularSpheroidType(null, appliedenergistics_sky_stone, 8, 14).addCenterChestWithLoot(appliedenergistics_sky_stone_chest_loot_table);

    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID);
    }

    public static LinkedHashMap<SpheroidType, Float> getSpheroidTypesWithProbabilities() {
        LinkedHashMap<SpheroidType, Float> spheroidTypes = new LinkedHashMap<>();
        spheroidTypes.put(APPLIEDENERGISTRICS_QUARTZ, 0.5F);
        spheroidTypes.put(APPLIEDENERGISTRICS_CHARGED_QUARTZ, 0.3F);
        spheroidTypes.put(APPLIEDENERGISTRICS_ASTEROID, 0.15F);
        return spheroidTypes;
    }

}
