package de.dafuqs.starrysky.SpheroidLists;

import de.dafuqs.starrysky.spheroidtypes.*;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;

public class SpheroidListAppliedEnergistics2 extends SpheroidList {

    private static final BlockState appliedenergistics_quartz         = Registry.BLOCK.get(new Identifier("appliedenergistics2","quartz_ore")).getDefaultState();
    private static final BlockState appliedenergistics_charged_quartz = Registry.BLOCK.get(new Identifier("appliedenergistics2","charged_quartz_ore")).getDefaultState();
    private static final BlockState appliedenergistics_sky_stone      = Registry.BLOCK.get(new Identifier("appliedenergistics2","sky_stone_block")).getDefaultState();

    public static final CoreSpheroidType APPLIEDENERGISTRICS_QUARTZ = new CoreSpheroidType(null, appliedenergistics_quartz, MAP_STONES, 5, 7, 5, 8);
    public static final DoubleCoreSpheroidType APPLIEDENERGISTRICS_CHARGED_QUARTZ = new DoubleCoreSpheroidType(null, appliedenergistics_quartz, appliedenergistics_charged_quartz, MAP_STONES, 5, 8, 1, 2, 3, 5);
    // TODO: The asteroid is missing the pattern chest
    public static final ModularSpheroidType APPLIEDENERGISTRICS_ASTEROID = new ModularSpheroidType(null, appliedenergistics_sky_stone, 8, 15);



    public static boolean isModPresent() {
        return FabricLoader.getInstance().isModLoaded("appliedenergistics2");
    }

    public static LinkedHashMap<SpheroidType, Float> getSpheroidTypesWithProbabilities() {
        LinkedHashMap<SpheroidType, Float> spheroidTypes = new LinkedHashMap<>();
        spheroidTypes.put(APPLIEDENERGISTRICS_QUARTZ, 0.5F);
        spheroidTypes.put(APPLIEDENERGISTRICS_CHARGED_QUARTZ, 0.3F);
        spheroidTypes.put(APPLIEDENERGISTRICS_ASTEROID, 0.15F);
        return spheroidTypes;
    }

}
