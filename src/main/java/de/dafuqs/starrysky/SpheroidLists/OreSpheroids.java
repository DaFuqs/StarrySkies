package de.dafuqs.starrysky.SpheroidLists;

import de.dafuqs.starrysky.spheroidtypes.CoreSpheroidType;
import de.dafuqs.starrysky.spheroidtypes.SpheroidType;
import net.minecraft.block.BlockState;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OreSpheroids {

    private static class OreSpheroidDefinition {
        LinkedHashMap<BlockState, Float> shellBlockStates; // the core
        int minRadius;
        int maxRadius;
        int minCoreRadius;
        int maxCoreRadius;
        float generationWeight;

        public OreSpheroidDefinition(LinkedHashMap<BlockState, Float> shellBlockStates, int minRadius, int maxRadius, int minCoreRadius, int maxCoreRadius, float generationWeight) {
            this.shellBlockStates = shellBlockStates;
            this.minRadius = minRadius;
            this.maxRadius = maxRadius;
            this.minCoreRadius = minCoreRadius;
            this.maxCoreRadius = maxCoreRadius;
            this.generationWeight = generationWeight;
        }
    }

    private static final LinkedHashMap<String, OreSpheroidDefinition> dynamicOreSpheroidDefinitions = new LinkedHashMap<String, OreSpheroidDefinition>() {{
        put("copper",       new OreSpheroidDefinition(SpheroidList.MAP_STONES, 8, 12, 5, 8, 10F));
        put("tin",          new OreSpheroidDefinition(SpheroidList.MAP_STONES, 8, 12, 5, 8, 10F));
        put("silver",       new OreSpheroidDefinition(SpheroidList.MAP_STONES, 8, 12, 5, 8, 10F));
        put("lead",         new OreSpheroidDefinition(SpheroidList.MAP_STONES, 8, 12, 5, 8, 10F));
        put("bauxite",      new OreSpheroidDefinition(SpheroidList.MAP_STONES, 8, 12, 5, 8, 10F));
        put("galena",       new OreSpheroidDefinition(SpheroidList.MAP_STONES, 8, 12, 5, 8, 10F));
        put("iridium",      new OreSpheroidDefinition(SpheroidList.MAP_STONES, 8, 12, 1, 2, 10F));
        put("ruby",         new OreSpheroidDefinition(SpheroidList.MAP_STONES, 8, 12, 5, 8, 10F));
        put("sapphire",     new OreSpheroidDefinition(SpheroidList.MAP_STONES, 8, 12, 5, 8, 10F));
        put("lignite_coal", new OreSpheroidDefinition(SpheroidList.MAP_STONES, 8, 12, 5, 8, 10F));
        put("nickel",       new OreSpheroidDefinition(SpheroidList.MAP_STONES, 8, 12, 5, 8, 10F));
        put("antimony",     new OreSpheroidDefinition(SpheroidList.MAP_STONES, 8, 12, 5, 8, 10F));
    }};

    /**
     * Returns a core spheroid for each dynamic entry that exists here
     * @param dynamicOres List of "ore strings" to generate dynamic spheroids from
     * @return List of finished dynamic ore spheroids with weight
     */
    public static LinkedHashMap<SpheroidType, Float> getOreSpheroidTypesBasedOnDict(LinkedHashMap<String, List<BlockState>> dynamicOres) {
        LinkedHashMap<SpheroidType, Float> dynamicSpheroidTypes = new LinkedHashMap<>();

        for(Map.Entry<String, List<BlockState>> dynamicOre : dynamicOres.entrySet()) {
            BlockState firstEntry = dynamicOre.getValue().get(0); // get first entry for "copper"
            OreSpheroidDefinition entrySpheroidDefinition = dynamicOreSpheroidDefinitions.get(dynamicOre.getKey());

            // add a single "copper" spheroid type, even though a list of mods may add copper ore blocks
            dynamicSpheroidTypes.put(
                    new CoreSpheroidType(
                        null,
                        firstEntry,
                        entrySpheroidDefinition.shellBlockStates,
                        entrySpheroidDefinition.minRadius,
                        entrySpheroidDefinition.maxRadius,
                        entrySpheroidDefinition.minCoreRadius,
                        entrySpheroidDefinition.maxCoreRadius),
                    entrySpheroidDefinition.generationWeight);
        }

        return dynamicSpheroidTypes;
    }

}
