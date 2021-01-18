package de.dafuqs.starrysky.spheroid.lists;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;

import static de.dafuqs.starrysky.dimension.SpheroidLoader.SpheroidDimensionType.NETHER;
import static de.dafuqs.starrysky.dimension.SpheroidLoader.SpheroidDimensionType.OVERWORLD;

public class SpheroidListMythicMetalsStandardFantasyEdition extends SpheroidList {

    private static final String MOD_ID = "mythicmetals";

    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateMythicMetalsSpheroidsStandardFantasyEdition;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        StarrySkyCommon.LOGGER.info("[StarrySky] Loading Mythic Metals Standard Fantasy Edition integration...");
		
        // Overworld ores
        BlockState mythicmetals_mythril_ore = getDefaultBlockState(MOD_ID,"mythril_ore");
        BlockState mythicmetals_zinc_ore = getDefaultBlockState(MOD_ID,"zinc_ore");
        spheroidLoader.registerDynamicOre(OVERWORLD, "mythril", mythicmetals_mythril_ore);
        spheroidLoader.registerDynamicOre(OVERWORLD, "zinc", mythicmetals_zinc_ore);

        // Nether ores
        BlockState mythicmetals_carmot_ore = getDefaultBlockState(MOD_ID,"carmot_ore");
        spheroidLoader.registerDynamicOre(NETHER, "carmot", mythicmetals_carmot_ore);
    }
}
