package de.dafuqs.starrysky.spheroid.types.special_overworld;

import de.dafuqs.starrysky.SpheroidEntitySpawnDefinition;
import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.dimension.decorators.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.spheroids.special_overworld.CoralSpheroid;
import de.dafuqs.starrysky.spheroid.types.SpheroidType;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;
import java.util.LinkedHashMap;


public class CoralSpheroidType extends SpheroidType {

    private final LinkedHashMap<BlockState, Float> validShellBlocks;
    private final int minShellRadius;
    private final int maxShellRadius;

    private Identifier lootTable;
    float lootTableChance;

    public CoralSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int minRadius, int maxRadius, LinkedHashMap<BlockState, Float> validShellBlocks, int minShellRadius, int maxShellRadius) {
        super(spheroidAdvancementIdentifier, minRadius, maxRadius);

        if(validShellBlocks == null || validShellBlocks.size() == 0) {
            StarrySkyCommon.LOGGER.error("CoralSpheroidType: Registered a SpheroidType with empty validShellBlocks!");
        }

        this.validShellBlocks = validShellBlocks;
        this.minShellRadius = minShellRadius;
        this.maxShellRadius = maxShellRadius;
    }

    public String getDescription() {
        return "CoralSpheroid";
    }

    public CoralSpheroid getRandomSphere(ChunkRandom chunkRandom) {
        int radius = getRandomRadius(chunkRandom);
        ArrayList<SpheroidDecorator> spheroidDecorators = getSpheroidDecoratorsWithChance(chunkRandom);
        ArrayList<SpheroidEntitySpawnDefinition> entityTypesToSpawn = getRandomEntityTypesToSpawn(chunkRandom);

        int shellRadius = chunkRandom.nextInt(this.maxShellRadius - this.minShellRadius + 1) + this.minShellRadius;
        BlockState shellBlockState = Support.getWeightedRandom(validShellBlocks, chunkRandom);

        Identifier lootTable = null;
        if( chunkRandom.nextFloat() < lootTableChance) {
            lootTable = this.lootTable;
        }

        return new CoralSpheroid(chunkRandom, spheroidAdvancementIdentifier, radius, spheroidDecorators, entityTypesToSpawn, shellBlockState, shellRadius, lootTable);
    }

    public CoralSpheroidType addChestWithLootTable(Identifier lootTable, float chance) {
        this.lootTable = lootTable;
        this.lootTableChance = chance;
        return this;
    }

}