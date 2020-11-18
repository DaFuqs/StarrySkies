package de.dafuqs.starrysky.SpheroidLists;

import de.dafuqs.starrysky.spheroidtypes.SpheroidType;
import net.minecraft.block.BlockState;

import java.util.*;

public class SpheroidLoader {

    LinkedHashMap<SpheroidType, Float> availableSpheroidTypesWithWeight = new LinkedHashMap<>();

    public SpheroidLoader() {
        LinkedHashMap<String, List<BlockState>> dynamicOres = new LinkedHashMap<>();

        // initialize lists
        if(SpheroidListVanilla.isModPresent()) {
            availableSpheroidTypesWithWeight.putAll(SpheroidListVanilla.getSpheroidTypesWithProbabilities());
            addOres(dynamicOres, SpheroidListVanilla.getDictionaryEntries());
        }
        if(SpheroidListTechReborn.isModPresent()) {
            availableSpheroidTypesWithWeight.putAll(SpheroidListTechReborn.getSpheroidTypesWithProbabilities());
            addOres(dynamicOres, SpheroidListTechReborn.getDictionaryEntries());
        }
        if(SpheroidListAstromine.isModPresent()) {
            availableSpheroidTypesWithWeight.putAll(SpheroidListAstromine.getSpheroidTypesWithProbabilities());
            addOres(dynamicOres, SpheroidListAstromine.getDictionaryEntries());
        }
        if(SpheroidListAppliedEnergistics2.isModPresent()) {
            availableSpheroidTypesWithWeight.putAll(SpheroidListAppliedEnergistics2.getSpheroidTypesWithProbabilities());
            addOres(dynamicOres, SpheroidListAppliedEnergistics2.getDictionaryEntries());
        }
        if(SpheroidListModernIndustrialization.isModPresent()) {
            availableSpheroidTypesWithWeight.putAll(SpheroidListModernIndustrialization.getSpheroidTypesWithProbabilities());
            addOres(dynamicOres, SpheroidListModernIndustrialization.getDictionaryEntries());
        }
        if(SpheroidListIndustrialRevolution.isModPresent()) {
            availableSpheroidTypesWithWeight.putAll(SpheroidListIndustrialRevolution.getSpheroidTypesWithProbabilities());
            addOres(dynamicOres, SpheroidListIndustrialRevolution.getDictionaryEntries());
        }

        // dynamically generate ore spheroids
        // this way we got only 1 "copper" spheroids even though lots of mods add a copper ore block
        LinkedHashMap<SpheroidType, Float> dynamicOreSpheroids = DynamicOreSpheroids.getOreSpheroidTypesBasedOnDict(dynamicOres);
        availableSpheroidTypesWithWeight.putAll(dynamicOreSpheroids);
    }

    protected void addOres(LinkedHashMap<String, List<BlockState>> dynamicOres, LinkedHashMap<String, BlockState> newEntries) {
        for(Map.Entry<String, BlockState> newEntry : newEntries.entrySet()) {
            if (dynamicOres.containsKey(newEntry.getKey())) {
                dynamicOres.get(newEntry.getKey()).add(newEntry.getValue());
            } else {
                ArrayList<BlockState> newArrayList = new ArrayList<>();
                newArrayList.add(newEntry.getValue());
                dynamicOres.put(newEntry.getKey(), newArrayList);
            }
        }
    }

    public LinkedHashMap<SpheroidType, Float> getAvailableSpheroidTypesWithWeight() {
        return this.availableSpheroidTypesWithWeight;
    }
}
