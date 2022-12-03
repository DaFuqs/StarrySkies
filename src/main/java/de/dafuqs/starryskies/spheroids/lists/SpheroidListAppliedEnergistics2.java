package de.dafuqs.starryskies.spheroids.lists;

import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.data_loaders.SpheroidTemplateLoader;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

import static org.apache.logging.log4j.Level.INFO;

public class SpheroidListAppliedEnergistics2 extends SpheroidList {
	
	private static final String MOD_ID = "appliedenergistics2";
	private static final Identifier APPLIED_ENERGISTICS_METEOR_CHEST_LOOT_TABLE = new Identifier("starry_skies", "appliedenergistics2_meteor_chest");
	
	public static boolean shouldGenerate() {
		return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkies.CONFIG.generateAppliedEnergistics2Spheroids;
	}
	
	public static void setup(SpheroidTemplateLoader spheroidLoader) {
		StarrySkies.log(INFO, "Loading Applied Energistics 2 integration...");

        /*BlockState appliedenergistics_quartz          = getDefaultBlockState(MOD_ID, "quartz_ore");
        BlockState appliedenergistics_charged_quartz  = getDefaultBlockState(MOD_ID,"charged_quartz_ore");
        BlockState appliedenergistics_sky_stone       = getDefaultBlockState(MOD_ID,"sky_stone_block");

        Identifier  appliedenergistics_sky_stone_chest_loot_table = new Identifier(StarrySky.MOD_ID, "appliedenergistics2_meteor_chest");

        CoreSpheroidType       APPLIEDENERGISTRICS_QUARTZ         = new CoreSpheroidType(null, 5, 7, appliedenergistics_quartz, MAP_STONES, 5, 8);
        DoubleCoreSpheroidType APPLIEDENERGISTRICS_CHARGED_QUARTZ = new DoubleCoreSpheroidType(null, 5, 8, appliedenergistics_charged_quartz, appliedenergistics_quartz, MAP_STONES, 1, 2, 3, 5);
        ModularSpheroidType    APPLIEDENERGISTRICS_ASTEROID       = new ModularSpheroidType(null, 8, 14, appliedenergistics_sky_stone)
                .addCenterChestWithLoot(appliedenergistics_sky_stone_chest_loot_table);

        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 1.0F, APPLIEDENERGISTRICS_QUARTZ);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 0.4F, APPLIEDENERGISTRICS_CHARGED_QUARTZ);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ESSENTIAL, 0.9F, APPLIEDENERGISTRICS_ASTEROID);

        // Only fill AE2 loot table with items when AE2 is loaded.
        // Otherwise the items don't even exist and vanilla throws
        // an error even though the loot table is not used
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (APPLIED_ENERGISTICS_METEOR_CHEST_LOOT_TABLE.equals(id)) {
                StarrySky.log(DEBUG, "Creating AE2 loot table...");
    
                Item CALCULATION_PRESS = Registry.ITEM.get(new Identifier(MOD_ID, "calculation_processor_press"));
                Item ENGINEERING_PRESS = Registry.ITEM.get(new Identifier(MOD_ID, "engineering_processor_press"));
                Item LOGIC_PRESS = Registry.ITEM.get(new Identifier(MOD_ID, "logic_processor_press"));
                Item SILICON_PRESS = Registry.ITEM.get(new Identifier(MOD_ID, "silicon_press"));
    
                LootPool pool = LootPool.builder()
                        .rolls(UniformLootNumberProvider.create(1, 3))
                        .with(ItemEntry.builder(CALCULATION_PRESS).build())
                        .with(ItemEntry.builder(ENGINEERING_PRESS).build())
                        .with(ItemEntry.builder(LOGIC_PRESS).build())
                        .with(ItemEntry.builder(SILICON_PRESS).build())
                        .build();
    
                tableBuilder.pool(pool);
            }
        });*/
	}
	
	
}
