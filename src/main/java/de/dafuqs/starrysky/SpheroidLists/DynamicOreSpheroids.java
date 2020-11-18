package de.dafuqs.starrysky.SpheroidLists;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.spheroidtypes.CoreSpheroidType;
import de.dafuqs.starrysky.spheroidtypes.SpheroidType;
import net.minecraft.block.BlockState;
import org.apache.logging.log4j.Level;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DynamicOreSpheroids {

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

    /**
     * Builtin data on how big / with which stone the dynamic ores will generate
     */
    private static final LinkedHashMap<String, OreSpheroidDefinition> dynamicOreSpheroidDefinitions = new LinkedHashMap<String, OreSpheroidDefinition>() {{
        put("antimony",     new OreSpheroidDefinition(SpheroidList.MAP_STONES, 5, 8, 3, 4, 0.4F)); // low weight
        put("bauxite",      new OreSpheroidDefinition(SpheroidList.MAP_STONES, 5, 7, 3, 5, 0.3F));
        put("galena",       new OreSpheroidDefinition(SpheroidList.MAP_STONES, 7, 10, 4, 7, 0.3F)); // same as copper, but twice as rare
        put("iridium",      new OreSpheroidDefinition(SpheroidList.MAP_DUNGEON_STONES, 5, 6, 1, 2, 0.03F)); // very low weight
        put("lead",         new OreSpheroidDefinition(SpheroidList.MAP_STONES, 5, 15, 4, 6, 0.8F));
        put("lignite_coal", new OreSpheroidDefinition(SpheroidList.MAP_STONES, 5, 14, 5, 8, 2.0F)); // high weight
        put("nickel",       new OreSpheroidDefinition(SpheroidList.MAP_STONES, 5, 7, 3, 4, 0.4F));
        put("copper",       new OreSpheroidDefinition(SpheroidList.MAP_STONES, 7, 10, 4, 7, 2F));
        put("nikolite",     new OreSpheroidDefinition(SpheroidList.MAP_STONES, 6, 8, 3, 5, 1F));
        put("ruby",         new OreSpheroidDefinition(SpheroidList.MAP_DUNGEON_STONES, 5, 7, 3, 4, 0.3F));
        put("salt",         new OreSpheroidDefinition(SpheroidList.MAP_STONES, 6, 8, 3, 5, 0.2F)); // low weight
        put("sapphire",     new OreSpheroidDefinition(SpheroidList.MAP_DUNGEON_STONES, 5, 7, 3, 4, 0.3F));
        put("silver",       new OreSpheroidDefinition(SpheroidList.MAP_STONES, 5, 10, 3, 6, 0.8F));
        put("tin",          new OreSpheroidDefinition(SpheroidList.MAP_STONES, 6, 10, 5, 7, 1.5F));
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

            if(entrySpheroidDefinition == null) {
                StarrySkyCommon.LOGGER.log(Level.ERROR, "The rarity of ore '" + dynamicOre.getKey() + "' is not defined. Blame the Starry Sky author!");
                continue;
            }

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
