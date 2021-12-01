package de.dafuqs.starrysky.dimension;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.spheroid.lists.*;
import de.dafuqs.starrysky.spheroid.types.SpheroidType;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.random.ChunkRandom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.apache.logging.log4j.Level.INFO;

public class SpheroidLoader {

    public enum SpheroidDimensionType {
        OVERWORLD,
        NETHER,
        END
    }

    private static boolean initialized = false;

    private static final LinkedHashMap<SpheroidDistributionType, LinkedHashMap<SpheroidType, Float>> availableSpheroidTypesByDistributionTypeWithWeight = new LinkedHashMap<>();
    private static final LinkedHashMap<SpheroidDistributionType, LinkedHashMap<SpheroidType, Float>> availableSpheroidTypesByDistributionTypeWithWeightNether = new LinkedHashMap<>();
    private static final LinkedHashMap<SpheroidDistributionType, LinkedHashMap<SpheroidType, Float>> availableSpheroidTypesByDistributionTypeWithWeightEnd = new LinkedHashMap<>();

    private static final HashMap<SpheroidDimensionType, LinkedHashMap<String, ArrayList<BlockState>>> dynamicOresByDimensionType = new LinkedHashMap() {{
        put(SpheroidDimensionType.OVERWORLD, new LinkedHashMap<String, ArrayList<BlockState>>());
        put(SpheroidDimensionType.NETHER, new LinkedHashMap<String, ArrayList<BlockState>>());
        put(SpheroidDimensionType.END, new LinkedHashMap<String, ArrayList<BlockState>>());
    }};

    private static final LinkedHashMap<SpheroidDistributionType, Float> spheroidDistributionTypeWeights = new LinkedHashMap<SpheroidDistributionType, Float>() {{
        put(SpheroidDistributionType.ESSENTIAL,  10.0F);
        put(SpheroidDistributionType.DECORATIVE, 10.0F);
        put(SpheroidDistributionType.ORE,        10.0F);
        put(SpheroidDistributionType.FLUID,       6.0F);
        put(SpheroidDistributionType.WOOD,        4.0F);
        put(SpheroidDistributionType.TREASURE,    1.0F);
        put(SpheroidDistributionType.DUNGEON,     0.1F);
    }};

    private static final LinkedHashMap<SpheroidDistributionType, Float> spheroidDistributionTypeWeightsNether = new LinkedHashMap<SpheroidDistributionType, Float>() {{
        put(SpheroidDistributionType.ESSENTIAL,  10.0F);
        put(SpheroidDistributionType.DECORATIVE, 10.0F);
        put(SpheroidDistributionType.ORE,        10.0F);
        put(SpheroidDistributionType.FLUID,       6.0F);
        put(SpheroidDistributionType.WOOD,        4.0F);
        put(SpheroidDistributionType.TREASURE,    1.0F);
        put(SpheroidDistributionType.DUNGEON,     0.1F);
    }};

    private static final LinkedHashMap<SpheroidDistributionType, Float> spheroidDistributionTypeWeightsEnd = new LinkedHashMap<SpheroidDistributionType, Float>() {{
        put(SpheroidDistributionType.ESSENTIAL,  50.0F);
        put(SpheroidDistributionType.DECORATIVE, 10.0F);
        put(SpheroidDistributionType.ORE,         6.0F);
        put(SpheroidDistributionType.WOOD,        3.0F);
        put(SpheroidDistributionType.FLUID,       1.0F);
        put(SpheroidDistributionType.TREASURE,    1.0F);
        put(SpheroidDistributionType.DUNGEON,     0.1F);
    }};

    public void registerSpheroidType(SpheroidDimensionType spheroidDimensionType, SpheroidDistributionType spheroidDistributionType, Float weight, SpheroidType spheroidType) {
        switch (spheroidDimensionType) {
            case OVERWORLD:
                availableSpheroidTypesByDistributionTypeWithWeight.get(spheroidDistributionType).put(spheroidType, weight);
                return;
            case NETHER:
                availableSpheroidTypesByDistributionTypeWithWeightNether.get(spheroidDistributionType).put(spheroidType, weight);
                return;
            case END:
                availableSpheroidTypesByDistributionTypeWithWeightEnd.get(spheroidDistributionType).put(spheroidType, weight);
        }
    }

    public void registerDynamicOre(SpheroidDimensionType spheroidDimensionType, String oreName, BlockState oreBlockState) {
        LinkedHashMap<String, ArrayList<BlockState>> dynamicOres = dynamicOresByDimensionType.get(spheroidDimensionType);

        if (dynamicOres.containsKey(oreName)) {
            dynamicOres.get(oreName).add(oreBlockState);
        } else {
            ArrayList<BlockState> newArrayList = new ArrayList<>();
            newArrayList.add(oreBlockState);
            dynamicOres.put(oreName, newArrayList);
        }
    }

    public SpheroidLoader() {
        if (!initialized) {

            StarrySkyCommon.log(INFO, "Loading Integration Packs...");

            // initialize spheroid types list with empty LinkedHashMaps
            for (SpheroidDistributionType spheroidDistributionType : SpheroidDistributionType.values()) {
                availableSpheroidTypesByDistributionTypeWithWeight.put(spheroidDistributionType, new LinkedHashMap<>());
            }
            for (SpheroidDistributionType spheroidDistributionType : SpheroidDistributionType.values()) {
                availableSpheroidTypesByDistributionTypeWithWeightNether.put(spheroidDistributionType, new LinkedHashMap<>());
            }
            for (SpheroidDistributionType spheroidDistributionType : SpheroidDistributionType.values()) {
                availableSpheroidTypesByDistributionTypeWithWeightEnd.put(spheroidDistributionType, new LinkedHashMap<>());
            }

            // initialize all existing lists
            SpheroidListVanilla.setup(this);
            SpheroidListVanillaNether.setup(this);
            SpheroidListVanillaEnd.setup(this);

            if (SpheroidListLGBT.shouldGenerate()) {
                SpheroidListLGBT.setup(this);
            }
            if (SpheroidListTechReborn.shouldGenerate()) {
                SpheroidListTechReborn.setup(this);
            }
            if (SpheroidListAstromine.shouldGenerate()) {
                SpheroidListAstromine.setup(this);
            }
            if (SpheroidListAppliedEnergistics2.shouldGenerate()) {
                SpheroidListAppliedEnergistics2.setup(this);
            }
            if (SpheroidListModernIndustrialization.shouldGenerate()) {
                SpheroidListModernIndustrialization.setup(this);
            }
            if (SpheroidListIndustrialRevolution.shouldGenerate()) {
                SpheroidListIndustrialRevolution.setup(this);
            }
            if (SpheroidListSakuraRosea.shouldGenerate()) {
                SpheroidListSakuraRosea.setup(this);
            }
            if (SpheroidListBlockus.shouldGenerate()) {
                SpheroidListBlockus.setup(this);
            }
            if (SpheroidListBYG.shouldGenerate()) {
                SpheroidListBYG.setup(this);
            }
            if (SpheroidListTerrestria.shouldGenerate()) {
                SpheroidListTerrestria.setup(this);
            }
            if (SpheroidListTraverse.shouldGenerate()) {
                SpheroidListTraverse.setup(this);
            }
            if (SpheroidListUnearthed.shouldGenerate()) {
                SpheroidListUnearthed.setup(this);
            }
            if (SpheroidListSandwichable.shouldGenerate()) {
                SpheroidListSandwichable.setup(this);
            }
            if (SpheroidListMythicMetals.shouldGenerate()) {
                SpheroidListMythicMetals.setup(this);
            }
            if (SpheroidListBetterNether.shouldGenerate()) {
                SpheroidListBetterNether.setup(this);
            }
            if (SpheroidListBetterEnd.shouldGenerate()) {
                SpheroidListBetterEnd.setup(this);
            }
            if (SpheroidListCinderscapes.shouldGenerate()) {
                SpheroidListCinderscapes.setup(this);
            }
            if (SpheroidListEcotones.shouldGenerate()) {
                SpheroidListEcotones.setup(this);
            }
            if (SpheroidListWoodsAndMires.shouldGenerate()) {
                SpheroidListWoodsAndMires.setup(this);
            }
            if (SpheroidListBiomeMakeover.shouldGenerate()) {
                SpheroidListBiomeMakeover.setup(this);
            }

            // dynamically generate ore spheroids
            // this way we got only 1 "copper" spheroids even though lots of mods add a copper ore block
            // Overworld
            LinkedHashMap<SpheroidType, Float> dynamicOreSpheroids = DynamicOreSpheroids.getOreSpheroidTypesBasedOnDict(dynamicOresByDimensionType.get(SpheroidDimensionType.OVERWORLD));
            availableSpheroidTypesByDistributionTypeWithWeight.get(SpheroidDistributionType.ORE).putAll(dynamicOreSpheroids);

            // Nether
            dynamicOreSpheroids = DynamicOreSpheroids.getOreSpheroidTypesBasedOnDict(dynamicOresByDimensionType.get(SpheroidDimensionType.NETHER));
            availableSpheroidTypesByDistributionTypeWithWeightNether.get(SpheroidDistributionType.ORE).putAll(dynamicOreSpheroids);

            // End
            dynamicOreSpheroids = DynamicOreSpheroids.getOreSpheroidTypesBasedOnDict(dynamicOresByDimensionType.get(SpheroidDimensionType.END));
            availableSpheroidTypesByDistributionTypeWithWeightEnd.get(SpheroidDistributionType.ORE).putAll(dynamicOreSpheroids);

            initialized = true;
            StarrySkyCommon.log(INFO, "Initializing complete!");
        }
    }

    public static SpheroidType getWeightedRandomSpheroid(SpheroidDimensionType spheroidDimensionType, ChunkRandom systemRandom) {
        SpheroidDistributionType chosenDistributionType;
        switch (spheroidDimensionType) {
            case OVERWORLD -> {
                chosenDistributionType = Support.getWeightedRandom(spheroidDistributionTypeWeights, systemRandom);
                return Support.getWeightedRandom(availableSpheroidTypesByDistributionTypeWithWeight.get(chosenDistributionType), systemRandom);
            }
            case NETHER -> {
                chosenDistributionType = Support.getWeightedRandom(spheroidDistributionTypeWeightsNether, systemRandom);
                return Support.getWeightedRandom(availableSpheroidTypesByDistributionTypeWithWeightNether.get(chosenDistributionType), systemRandom);
            }
            default -> {
                chosenDistributionType = Support.getWeightedRandom(spheroidDistributionTypeWeightsEnd, systemRandom);
                return Support.getWeightedRandom(availableSpheroidTypesByDistributionTypeWithWeightEnd.get(chosenDistributionType), systemRandom);
            }
        }
    }
}
