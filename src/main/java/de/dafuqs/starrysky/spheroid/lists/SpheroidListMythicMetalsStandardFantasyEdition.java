package de.dafuqs.starrysky.spheroid.lists;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.dimension.SpheroidDistributionType;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import de.dafuqs.starrysky.spheroid.types.CoreSpheroidType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;

import static de.dafuqs.starrysky.dimension.SpheroidLoader.SpheroidDimensionType.NETHER;
import static de.dafuqs.starrysky.dimension.SpheroidLoader.SpheroidDimensionType.OVERWORLD;

public class SpheroidListMythicMetalsStandardFantasyEdition extends SpheroidList {

    private static final String MOD_ID = "mythicmetals";

    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && !StarrySkyCommon.STARRY_SKY_CONFIG.generateMythicMetalsSpheroids && StarrySkyCommon.STARRY_SKY_CONFIG.generateMythicMetalsSpheroidsStandardFantasyEdition;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        StarrySkyCommon.LOGGER.info("[StarrySky] Loading Mythic Metals Standard Fantasy Edition integration...");

        BlockState mythril_ore = getDefaultBlockState(MOD_ID,"mythril_ore");
        BlockState zinc_ore = getDefaultBlockState(MOD_ID,"zinc_ore");
        BlockState carmot_ore = getDefaultBlockState(MOD_ID,"carmot_ore");

        // Overworld
        CoreSpheroidType ZINC_SPHEROID_TYPE = new CoreSpheroidType(null, 8, 11, zinc_ore, SpheroidList.MAP_STONES, 5, 6);
        CoreSpheroidType MYTHRIL_SPHEROID_TYPE = new CoreSpheroidType(null, 4, 6, mythril_ore, SpheroidList.MAP_DUNGEON_STONES, 1, 2);

        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 1.5F, ZINC_SPHEROID_TYPE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 0.2F, MYTHRIL_SPHEROID_TYPE);

        // Nether
        CoreSpheroidType CARMOT_SPHEROID_TYPE = new CoreSpheroidType(null, 5, 7, carmot_ore, SpheroidList.MAP_NETHER_STONES, 2, 4);
        spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.ORE, 0.9F, CARMOT_SPHEROID_TYPE);
    }
}