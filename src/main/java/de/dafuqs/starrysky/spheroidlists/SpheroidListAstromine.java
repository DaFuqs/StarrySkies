package de.dafuqs.starrysky.spheroidlists;

import de.dafuqs.starrysky.spheroidtypes.CoreSpheroidType;
import de.dafuqs.starrysky.spheroidtypes.LiquidSpheroidType;
import de.dafuqs.starrysky.spheroidtypes.SpheroidType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;

public class SpheroidListAstromine extends SpheroidList {

    private static final String MOD_ID = "astromine";

    private static final BlockState astromine_crude_oil         = Registry.BLOCK.get(new Identifier(MOD_ID,"crude_oil")).getDefaultState();
    private static final BlockState astromine_copper_ore        = Registry.BLOCK.get(new Identifier(MOD_ID,"copper_ore")).getDefaultState();
    private static final BlockState astromine_tin_ore           = Registry.BLOCK.get(new Identifier(MOD_ID,"tin_ore")).getDefaultState();
    private static final BlockState astromine_silver_ore        = Registry.BLOCK.get(new Identifier(MOD_ID,"silver_ore")).getDefaultState();
    private static final BlockState astromine_lead_ore          = Registry.BLOCK.get(new Identifier(MOD_ID,"lead_ore")).getDefaultState();
    private static final BlockState astromine_meteor_metite_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"meteor_metite_ore")).getDefaultState();
    private static final BlockState astromine_meteor_stone      = Registry.BLOCK.get(new Identifier(MOD_ID,"meteor_stone")).getDefaultState();

    public static final CoreSpheroidType ASTROMINE_METEORITE         = new CoreSpheroidType(null, astromine_meteor_metite_ore, astromine_meteor_stone, 8, 13, 5, 8);
    public static final CoreSpheroidType ASTROMINE_CRUDE_OIL_STONE   = new CoreSpheroidType(null, astromine_crude_oil, astromine_meteor_stone, 8, 15, 5, 8);
    public static final LiquidSpheroidType ASTROMINE_CRUDE_OIL_GLASS = new LiquidSpheroidType(null, astromine_crude_oil, MAP_STONES, 7, 12, 2, 3, 50, 90, 20);

    public static boolean isModPresent() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID);
    }

    public static LinkedHashMap<String, BlockState> getDictionaryEntries() {
        LinkedHashMap<String, BlockState> dictionary = new LinkedHashMap<>();
        dictionary.put("lead", astromine_lead_ore);
        dictionary.put("silver", astromine_silver_ore);
        dictionary.put("tin", astromine_tin_ore);
        dictionary.put("copper", astromine_copper_ore);
        return dictionary;
    }

    public static LinkedHashMap<SpheroidType, Float> getSpheroidTypesWithProbabilities() {
        LinkedHashMap<SpheroidType, Float> spheroidTypes = new LinkedHashMap<>();
        spheroidTypes.put(ASTROMINE_METEORITE, 0.5F);
        spheroidTypes.put(ASTROMINE_CRUDE_OIL_STONE, 0.3F);
        spheroidTypes.put(ASTROMINE_CRUDE_OIL_GLASS, 0.15F);
        return spheroidTypes;
    }

}
