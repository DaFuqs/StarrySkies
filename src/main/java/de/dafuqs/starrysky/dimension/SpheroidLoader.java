package de.dafuqs.starrysky.dimension;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.spheroidlists.*;
import de.dafuqs.starrysky.spheroidtypes.SpheroidType;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.ChunkRandom;

import java.util.*;

public class SpheroidLoader {

    public LinkedHashMap<SpheroidDistributionType, LinkedHashMap<SpheroidType, Float>> availableSpheroidTypesByDistributionTypeWithWeight = new LinkedHashMap<>();
    private LinkedHashMap<String, List<BlockState>> dynamicOres = new LinkedHashMap<>();

    private static final LinkedHashMap<SpheroidDistributionType, Float> spheroidDistributionTypeWeights = new LinkedHashMap<SpheroidDistributionType, Float>() {{
        put(SpheroidDistributionType.ESSENTIAL,  10.0F);
        put(SpheroidDistributionType.DECORATIVE, 10.0F);
        put(SpheroidDistributionType.ORE,        10.0F);
        put(SpheroidDistributionType.FLUID,       5.0F);
        put(SpheroidDistributionType.WOOD,        4.0F);
        put(SpheroidDistributionType.TREASURE,    1.0F);
        put(SpheroidDistributionType.DUNGEON,     0.1F);
    }};

    public void registerSpheroidType(SpheroidDistributionType spheroidDistributionType, Float weight, SpheroidType spheroidType) {
        availableSpheroidTypesByDistributionTypeWithWeight.get(spheroidDistributionType).put(spheroidType, weight);
    }

    public void registerDynamicOre(String oreName, BlockState oreBlockState) {
        if (dynamicOres.containsKey(oreName)) {
            dynamicOres.get(oreName).add(oreBlockState);
        } else {
            ArrayList<BlockState> newArrayList = new ArrayList<>();
            newArrayList.add(oreBlockState);
            dynamicOres.put(oreName, newArrayList);
        }
    }

    public SpheroidLoader() {
        // initialize spheroid types list with empty LinkedHashMaps
        for(SpheroidDistributionType spheroidDistributionType : SpheroidDistributionType.values()) {
            availableSpheroidTypesByDistributionTypeWithWeight.put(spheroidDistributionType, new LinkedHashMap<>());
        }

        // initialize all existing lists
        if(SpheroidListVanilla.shouldGenerate())                 { SpheroidListVanilla.setup(this); }
        if(SpheroidListLGBT.shouldGenerate())                    { SpheroidListLGBT.setup(this); }
        if(SpheroidListTechReborn.shouldGenerate())              { SpheroidListTechReborn.setup(this); }
        if(SpheroidListAstromine.shouldGenerate())               { SpheroidListAstromine.setup(this); }
        if(SpheroidListAppliedEnergistics2.shouldGenerate())     { SpheroidListAppliedEnergistics2.setup(this); }
        if(SpheroidListModernIndustrialization.shouldGenerate()) { SpheroidListModernIndustrialization.setup(this); }
        if(SpheroidListIndustrialRevolution.shouldGenerate())    { SpheroidListIndustrialRevolution.setup(this); }
        if(SpheroidListSakuraRosea.shouldGenerate())             { SpheroidListSakuraRosea.setup(this); }
        if(SpheroidListBlockus.shouldGenerate())                 { SpheroidListBlockus.setup(this); }
        if(SpheroidListBYG.shouldGenerate())                     { SpheroidListBYG.setup(this); }
        if(SpheroidListTerrestria.shouldGenerate())              { SpheroidListTerrestria.setup(this); }
        if(SpheroidListTraverse.shouldGenerate())                { SpheroidListTraverse.setup(this); }
        if(SpheroidListUnearthed.shouldGenerate())               { SpheroidListUnearthed.setup(this); }
        if(SpheroidListSandwichable.shouldGenerate())            { SpheroidListSandwichable.setup(this); }

        // dynamically generate ore spheroids
        // this way we got only 1 "copper" spheroids even though lots of mods add a copper ore block
        LinkedHashMap<SpheroidType, Float> dynamicOreSpheroids = DynamicOreSpheroids.getOreSpheroidTypesBasedOnDict(dynamicOres);
        availableSpheroidTypesByDistributionTypeWithWeight.get(SpheroidDistributionType.ORE).putAll(dynamicOreSpheroids);
    }

    public SpheroidType getWeightedRandomSpheroid(ChunkRandom systemRandom) {
        SpheroidDistributionType chosenDistributionType = Support.getWeightedRandom(spheroidDistributionTypeWeights, systemRandom);
        return Support.getWeightedRandom(this.availableSpheroidTypesByDistributionTypeWithWeight.get(chosenDistributionType), systemRandom);
    }

}
