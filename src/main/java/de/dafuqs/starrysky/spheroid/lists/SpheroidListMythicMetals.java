package de.dafuqs.starrysky.spheroid.lists;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static de.dafuqs.starrysky.dimension.SpheroidLoader.SpheroidDimensionType.OVERWORLD;

public class SpheroidListMythicMetals extends SpheroidList {

    private static final String MOD_ID = "mythicmetals";

    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateMythicMetalsSpheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        StarrySkyCommon.LOGGER.info("Loading Mythic Metals integration...");
		
		BlockState mythicmetals_adamantite_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"adamantite_ore")).getDefaultState();
		BlockState mythicmetals_aetherium_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"aetherium_ore")).getDefaultState();
		BlockState mythicmetals_aquarium_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"aquarium_ore")).getDefaultState();
		BlockState mythicmetals_banglum_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"banglum_ore")).getDefaultState();
		BlockState mythicmetals_carmot_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"carmot_ore")).getDefaultState();
		BlockState mythicmetals_copper_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"copper_ore")).getDefaultState();
		BlockState mythicmetals_kyber_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"kyber_ore")).getDefaultState();
		BlockState mythicmetals_lutetium_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"lutetium_ore")).getDefaultState();
		BlockState mythicmetals_manganese_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"manganese_ore")).getDefaultState();
		BlockState mythicmetals_mythril_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"mythril_ore")).getDefaultState();
		BlockState mythicmetals_orichalcum_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"orichalcum_ore")).getDefaultState();
		BlockState mythicmetals_osmium_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"osmium_ore")).getDefaultState();
		BlockState mythicmetals_platinum_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"platinum_ore")).getDefaultState();
		BlockState mythicmetals_prometheum_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"prometheum_ore")).getDefaultState();
		BlockState mythicmetals_quadrillum_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"quadrillum_ore")).getDefaultState();
		BlockState mythicmetals_runite_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"runite_ore")).getDefaultState();
		BlockState mythicmetals_silver_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"silver_ore")).getDefaultState();
		BlockState mythicmetals_starrite_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"starrite_ore")).getDefaultState();
		BlockState mythicmetals_tantalite_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"tantalite_ore")).getDefaultState();
		BlockState mythicmetals_tin_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"tin_ore")).getDefaultState();
		BlockState mythicmetals_unobtainium_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"unobtainium_ore")).getDefaultState();
		BlockState mythicmetals_vermiculite_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"vermiculite_ore")).getDefaultState();
		BlockState mythicmetals_zinc_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"zinc_ore")).getDefaultState();
		// BlockState mythicmetals_stormyx_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"stormyx_ore")).getDefaultState();
		// BlockState mythicmetals_truesilver_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"truesilver_ore")).getDefaultState();
		// BlockState mythicmetals_ur_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"ur_ore")).getDefaultState();
		// BlockState mythicmetals_midas_gold_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"midas_gold_ore")).getDefaultState();

		// OVERWORLD
		spheroidLoader.registerDynamicOre(OVERWORLD, "adamantite", mythicmetals_adamantite_ore);
		spheroidLoader.registerDynamicOre(OVERWORLD, "aetherium", mythicmetals_aetherium_ore);
		spheroidLoader.registerDynamicOre(OVERWORLD, "aquarium", mythicmetals_aquarium_ore); // BiomeKeys.COLD_OCEAN, BiomeKeys.DEEP_COLD_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN, BiomeKeys.DEEP_OCEAN, BiomeKeys.FROZEN_OCEAN,
		spheroidLoader.registerDynamicOre(OVERWORLD, "banglum", mythicmetals_banglum_ore);
		spheroidLoader.registerDynamicOre(OVERWORLD, "carmot", mythicmetals_carmot_ore);
		spheroidLoader.registerDynamicOre(OVERWORLD, "copper", mythicmetals_copper_ore);
		spheroidLoader.registerDynamicOre(OVERWORLD, "kyber", mythicmetals_kyber_ore);
		spheroidLoader.registerDynamicOre(OVERWORLD, "lutetium", mythicmetals_lutetium_ore);
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
		// spheroidLoader.registerDynamicOre("midas_gold", mythicmetals_midas_gold_ore);
		// spheroidLoader.registerDynamicOre("stormyx", mythicmetals_stormyx_ore);
		// spheroidLoader.registerDynamicOre("truesilver", mythicmetals_truesilver_ore);
		// spheroidLoader.registerDynamicOre("ur", mythicmetals_ur_ore);
	}
}