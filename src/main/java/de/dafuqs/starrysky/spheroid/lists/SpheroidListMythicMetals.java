package de.dafuqs.starrysky.spheroid.lists;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;

import static de.dafuqs.starrysky.dimension.SpheroidDimensionType.NETHER;
import static de.dafuqs.starrysky.dimension.SpheroidDimensionType.OVERWORLD;
import static org.apache.logging.log4j.Level.INFO;

public class SpheroidListMythicMetals extends SpheroidList {

    private static final String MOD_ID = "mythicmetals";

    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateMythicMetalsSpheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
		StarrySkyCommon.log(INFO, "Loading Mythic Metals integration...");
		
		BlockState mythicmetals_adamantite_ore = getDefaultBlockState(MOD_ID,"adamantite_ore");
		BlockState mythicmetals_aetherium_ore = getDefaultBlockState(MOD_ID,"aetherium_ore");
		BlockState mythicmetals_aquarium_ore = getDefaultBlockState(MOD_ID,"aquarium_ore");
		BlockState mythicmetals_banglum_ore = getDefaultBlockState(MOD_ID,"banglum_ore");
		BlockState mythicmetals_carmot_ore = getDefaultBlockState(MOD_ID,"carmot_ore");
		BlockState mythicmetals_kyber_ore = getDefaultBlockState(MOD_ID,"kyber_ore");
		BlockState mythicmetals_manganese_ore = getDefaultBlockState(MOD_ID,"manganese_ore");
		BlockState mythicmetals_mythril_ore = getDefaultBlockState(MOD_ID,"mythril_ore");
		BlockState mythicmetals_orichalcum_ore = getDefaultBlockState(MOD_ID,"orichalcum_ore");
		BlockState mythicmetals_osmium_ore = getDefaultBlockState(MOD_ID,"osmium_ore");
		BlockState mythicmetals_platinum_ore = getDefaultBlockState(MOD_ID,"platinum_ore");
		BlockState mythicmetals_prometheum_ore = getDefaultBlockState(MOD_ID,"prometheum_ore");
		BlockState mythicmetals_quadrillum_ore = getDefaultBlockState(MOD_ID,"quadrillum_ore");
		BlockState mythicmetals_runite_ore = getDefaultBlockState(MOD_ID,"runite_ore");
		BlockState mythicmetals_silver_ore = getDefaultBlockState(MOD_ID,"silver_ore");
		BlockState mythicmetals_starrite_ore = getDefaultBlockState(MOD_ID,"starrite_ore");
		BlockState mythicmetals_tantalite_ore = getDefaultBlockState(MOD_ID,"tantalite_ore");
		BlockState mythicmetals_tin_ore = getDefaultBlockState(MOD_ID,"tin_ore");
		BlockState mythicmetals_unobtainium_ore = getDefaultBlockState(MOD_ID,"unobtainium_ore");
		BlockState mythicmetals_vermiculite_ore = getDefaultBlockState(MOD_ID,"vermiculite_ore");
		BlockState mythicmetals_zinc_ore = getDefaultBlockState(MOD_ID,"zinc_ore");

		// Nether
		BlockState mythicmetals_stormyx_ore = getDefaultBlockState(MOD_ID,"stormyx_ore");
		BlockState mythicmetals_truesilver_ore = getDefaultBlockState(MOD_ID,"truesilver_ore");
		BlockState mythicmetals_ur_ore = getDefaultBlockState(MOD_ID,"ur_ore");
		BlockState mythicmetals_midas_gold_ore = getDefaultBlockState(MOD_ID,"midas_gold_ore");

		// OVERWORLD
		spheroidLoader.registerDynamicOre(OVERWORLD, "adamantite", mythicmetals_adamantite_ore);
		spheroidLoader.registerDynamicOre(OVERWORLD, "aetherium", mythicmetals_aetherium_ore);
		spheroidLoader.registerDynamicOre(OVERWORLD, "aquarium", mythicmetals_aquarium_ore); // BiomeKeys.COLD_OCEAN, BiomeKeys.DEEP_COLD_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN, BiomeKeys.DEEP_OCEAN, BiomeKeys.FROZEN_OCEAN,
		spheroidLoader.registerDynamicOre(OVERWORLD, "banglum", mythicmetals_banglum_ore);
		spheroidLoader.registerDynamicOre(OVERWORLD, "carmot", mythicmetals_carmot_ore);
		spheroidLoader.registerDynamicOre(OVERWORLD, "kyber", mythicmetals_kyber_ore);
		spheroidLoader.registerDynamicOre(OVERWORLD, "manganese", mythicmetals_manganese_ore);
		spheroidLoader.registerDynamicOre(OVERWORLD, "mythril", mythicmetals_mythril_ore);
		spheroidLoader.registerDynamicOre(OVERWORLD, "orichalcum", mythicmetals_orichalcum_ore);
		spheroidLoader.registerDynamicOre(OVERWORLD, "osmium", mythicmetals_osmium_ore);
		spheroidLoader.registerDynamicOre(OVERWORLD, "platinum", mythicmetals_platinum_ore);
		spheroidLoader.registerDynamicOre(OVERWORLD, "prometheum", mythicmetals_prometheum_ore); // BiomeKeys.BAMBOO_JUNGLE, BiomeKeys.BAMBOO_JUNGLE_HILLS, BiomeKeys.JUNGLE, BiomeKeys.JUNGLE_EDGE, BiomeKeys.JUNGLE_HILLS, BiomeKeys.MODIFIED_JUNGLE, BiomeKeys.MODIFIED_JUNGLE_EDGE
		spheroidLoader.registerDynamicOre(OVERWORLD, "quadrillum", mythicmetals_quadrillum_ore);
		spheroidLoader.registerDynamicOre(OVERWORLD, "runite", mythicmetals_runite_ore);
		spheroidLoader.registerDynamicOre(OVERWORLD, "silver", mythicmetals_silver_ore);
		spheroidLoader.registerDynamicOre(OVERWORLD, "starrite", mythicmetals_starrite_ore); // BiomeKeys.GRAVELLY_MOUNTAINS, BiomeKeys.MODIFIED_GRAVELLY_MOUNTAINS, BiomeKeys.MOUNTAINS, BiomeKeys.MOUNTAIN_EDGE, BiomeKeys.SHATTERED_SAVANNA, BiomeKeys.SHATTERED_SAVANNA_PLATEAU, BiomeKeys.SNOWY_MOUNTAINS, BiomeKeys.SNOWY_TAIGA_MOUNTAINS, BiomeKeys.TAIGA_MOUNTAINS

		spheroidLoader.registerDynamicOre(OVERWORLD, "tantalite", mythicmetals_tantalite_ore);
		spheroidLoader.registerDynamicOre(OVERWORLD, "tin", mythicmetals_tin_ore);
		spheroidLoader.registerDynamicOre(OVERWORLD, "unobtainium", mythicmetals_unobtainium_ore);
		spheroidLoader.registerDynamicOre(OVERWORLD, "vermiculite", mythicmetals_vermiculite_ore);
		spheroidLoader.registerDynamicOre(OVERWORLD, "zinc", mythicmetals_zinc_ore);
		
		// NETHER
		spheroidLoader.registerDynamicOre(NETHER,"midas_gold", mythicmetals_midas_gold_ore);
		spheroidLoader.registerDynamicOre(NETHER,"stormyx", mythicmetals_stormyx_ore);
		spheroidLoader.registerDynamicOre(NETHER,"truesilver", mythicmetals_truesilver_ore);
		spheroidLoader.registerDynamicOre(NETHER,"ur", mythicmetals_ur_ore);
	}
}