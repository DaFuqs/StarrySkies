package de.dafuqs.starrysky.dimension.spheroid.types;

import de.dafuqs.starrysky.dimension.spheroid.SpheroidEntitySpawnDefinition;
import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.dimension.spheroid.spheroids.CoralSpheroid;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.apache.logging.log4j.Level.ERROR;


public class CoralSpheroidType extends SpheroidType {

    private final LinkedHashMap<BlockState, Float> validShellBlocks;
    private final int minShellRadius;
    private final int maxShellRadius;

    private Identifier lootTable;
    float lootTableChance;

    public CoralSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int minRadius, int maxRadius, LinkedHashMap<BlockState, Float> validShellBlocks, int minShellRadius, int maxShellRadius) {
        super(spheroidAdvancementIdentifier, minRadius, maxRadius);

        if(validShellBlocks == null || validShellBlocks.size() == 0) {
            StarrySkyCommon.log(ERROR, "CoralSpheroidType: Registered a SpheroidType with empty validShellBlocks!");
        }

        this.validShellBlocks = validShellBlocks;
        this.minShellRadius = minShellRadius;
        this.maxShellRadius = maxShellRadius;
    }

    public CoralSpheroidType addChestWithLootTable(Identifier lootTable, float chance) {
        this.lootTable = lootTable;
        this.lootTableChance = chance;
        return this;
    }

    public String getDescription() {
        return "CoralSpheroid";
    }

    public CoralSpheroid getRandomSpheroid(ChunkRandom chunkRandom) {
        int radius = getRandomRadius(chunkRandom);
        int shellRadius = Support.getRandomBetween(chunkRandom, this.minShellRadius, this.maxShellRadius);

        ArrayList<SpheroidDecorator> spheroidDecorators = getSpheroidDecoratorsWithChance(chunkRandom);
        ArrayList<SpheroidEntitySpawnDefinition> entityTypesToSpawn = getRandomEntityTypesToSpawn(chunkRandom);
        BlockState shellBlockState = Support.getWeightedRandom(validShellBlocks, chunkRandom);

        Identifier lootTable = null;
        if( chunkRandom.nextFloat() < lootTableChance) {
            lootTable = this.lootTable;
        }

        return new CoralSpheroid(chunkRandom, spheroidAdvancementIdentifier, radius, spheroidDecorators, entityTypesToSpawn, shellBlockState, shellRadius, lootTable);
    }

}