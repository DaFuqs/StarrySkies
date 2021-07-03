package de.dafuqs.starrysky.dimension.spheroid.types;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.dimension.spheroid.SpheroidEntitySpawnDefinition;
import de.dafuqs.starrysky.dimension.spheroid.spheroids.CaveSpheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;

import static org.apache.logging.log4j.Level.ERROR;


public class CaveSpheroidType extends SpheroidType {

    private final BlockState shellBlock;
    private final int minShellRadius;
    private final int maxShellRadius;
    private final BlockState caveFloorBlock;
    private BlockState topBlock;
    private BlockState bottomBlock;
    private Identifier lootTable;
    float lootTableChance;

    public CaveSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int minRadius, int maxRadius, BlockState shellBlock, BlockState caveFloorBlock, int minShellRadius, int maxShellRadius) {
        super(spheroidAdvancementIdentifier, minRadius, maxRadius);

        if(shellBlock == Blocks.AIR.getDefaultState()) {
            StarrySkyCommon.log(ERROR, "CaveSpheroidType: Registered a SpheroidType with null shellBlock!");
        }
        if(caveFloorBlock == Blocks.AIR.getDefaultState()) {
            StarrySkyCommon.log(ERROR, "CaveSpheroidType: Registered a SpheroidType with null caveFloorBlock!");
        }

        this.caveFloorBlock = caveFloorBlock;
        this.shellBlock = shellBlock;
        this.minShellRadius = minShellRadius;
        this.maxShellRadius = maxShellRadius;
    }

    public CaveSpheroidType setTopBlockState(BlockState state) {
        topBlock = state;
        return this;
    }

    public CaveSpheroidType setBottomBlockState(BlockState state) {
        bottomBlock = state;
        return this;
    }

    public CaveSpheroidType addChestWithLootTable(Identifier lootTable, float chance) {
        this.lootTable = lootTable;
        this.lootTableChance = chance;
        return this;
    }

    public BlockState getTopBlock() {
        return topBlock != null ? topBlock : shellBlock;
    }

    public BlockState getBottomBlock() {
        return bottomBlock != null ? bottomBlock : shellBlock;
    }

    public String getDescription() {
        return "CaveSpheroid";
    }

    public CaveSpheroid getRandomSpheroid(ChunkRandom chunkRandom) {
        int radius = getRandomRadius(chunkRandom);
        int shellRadius = Support.getRandomBetween(chunkRandom, this.minShellRadius, this.maxShellRadius);

        ArrayList<SpheroidDecorator> spheroidDecorators = getSpheroidDecoratorsWithChance(chunkRandom);
        ArrayList<SpheroidEntitySpawnDefinition> entityTypesToSpawn = getRandomEntityTypesToSpawn(chunkRandom);
        BlockState topBlock = getTopBlock();
        BlockState bottomBlock = getBottomBlock();

        Identifier lootTable = null;
        if(chunkRandom.nextFloat() < lootTableChance) {
            lootTable = this.lootTable;
        }

        return new CaveSpheroid(chunkRandom, spheroidAdvancementIdentifier, radius, spheroidDecorators, entityTypesToSpawn, caveFloorBlock, shellBlock, shellRadius, topBlock, bottomBlock, lootTable);
    }

}