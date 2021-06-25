package de.dafuqs.starrysky.spheroid.lists;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.dimension.SpheroidDistributionType;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import de.dafuqs.starrysky.spheroid.types.CoreSpheroidType;
import de.dafuqs.starrysky.spheroid.types.DoubleCoreSpheroidType;
import de.dafuqs.starrysky.spheroid.types.ModularSpheroidType;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static de.dafuqs.starrysky.dimension.SpheroidLoader.SpheroidDimensionType.OVERWORLD;
import static org.apache.logging.log4j.Level.DEBUG;
import static org.apache.logging.log4j.Level.INFO;

public class SpheroidListAppliedEnergistics2 extends SpheroidList {

    private static final String MOD_ID = "appliedenergistics2";
    private static final Identifier APPLIED_ENERGISTICS_METEOR_CHEST_LOOT_TABLE = new Identifier("starry_sky", "appliedenergistics2_meteor_chest");

    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateAppliedEnergistics2Spheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        StarrySkyCommon.log(INFO, "Loading Applied Energistics 2 integration...");

        BlockState appliedenergistics_quartz          = getDefaultBlockState(MOD_ID, "quartz_ore");
        BlockState appliedenergistics_charged_quartz  = getDefaultBlockState(MOD_ID,"charged_quartz_ore");
        BlockState appliedenergistics_sky_stone       = getDefaultBlockState(MOD_ID,"sky_stone_block");

        Identifier  appliedenergistics_sky_stone_chest_loot_table = new Identifier(StarrySkyCommon.MOD_ID, "appliedenergistics2_meteor_chest");

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
        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) -> {
            if (APPLIED_ENERGISTICS_METEOR_CHEST_LOOT_TABLE.equals(id)) {
                StarrySkyCommon.log(DEBUG, "Creating AE2 loot table...");

                Item CALCULATION_PRESS = Registry.ITEM.get(new Identifier("appliedenergistics2", "calculation_processor_press"));
                Item ENGINEERING_PRESS = Registry.ITEM.get(new Identifier("appliedenergistics2", "engineering_processor_press"));
                Item LOGIC_PRESS = Registry.ITEM.get(new Identifier("appliedenergistics2", "logic_processor_press"));
                Item SILICON_PRESS = Registry.ITEM.get(new Identifier("appliedenergistics2", "silicon_press"));

                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(UniformLootNumberProvider.create(1, 3))
                        .withEntry(ItemEntry.builder(CALCULATION_PRESS).build())
                        .withEntry(ItemEntry.builder(ENGINEERING_PRESS).build())
                        .withEntry(ItemEntry.builder(LOGIC_PRESS).build())
                        .withEntry(ItemEntry.builder(SILICON_PRESS).build());
                supplier.withPool(poolBuilder.build());
            }
        });
    }



}
