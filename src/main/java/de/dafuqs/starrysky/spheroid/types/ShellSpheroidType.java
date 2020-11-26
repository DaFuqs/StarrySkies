package de.dafuqs.starrysky.spheroid.types;

import de.dafuqs.starrysky.SpheroidEntitySpawnDefinition;
import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.dimension.decorators.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.spheroids.ShellSpheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;
import java.util.LinkedHashMap;


public class ShellSpheroidType extends SpheroidType {

    private final BlockState coreBlock;
    private final LinkedHashMap<BlockState, Float> validShellBlocks;
    private final int minShellRadius; //Minimum shell thickness, should be at least
    private final int maxShellRadius; //Maximum shell thickness
    private final LinkedHashMap<BlockState, Float> shellSpeckleBlockStates = new LinkedHashMap<>();

    public ShellSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int minRadius, int maxRadius, BlockState coreBlock, BlockState shellBlock, int minShellRadius, int maxShellRadius) {
        this(spheroidAdvancementIdentifier, minRadius, maxRadius, coreBlock, new LinkedHashMap<BlockState, Float>(){{put(shellBlock, 1.0F);}}, minShellRadius, maxShellRadius);
    }

    public ShellSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int minRadius, int maxRadius, BlockState coreBlock, LinkedHashMap<BlockState, Float> validShellBlocks, int minShellRadius, int maxShellRadius) {
        super(spheroidAdvancementIdentifier, minRadius, maxRadius);

        if(coreBlock == Blocks.AIR.getDefaultState()) {
            StarrySkyCommon.LOGGER.error("ShellSpheroidType: Registered a SpheroidType with empty coreBlock!");
        }
        if(validShellBlocks == null || validShellBlocks.size() == 0) {
            StarrySkyCommon.LOGGER.error("ShellSpheroidType: Registered a SpheroidType with empty validShellBlocks!");
        }

        this.coreBlock = coreBlock;
        this.validShellBlocks = validShellBlocks;
        this.minShellRadius = minShellRadius;
        this.maxShellRadius = maxShellRadius;
    }

    public BlockState getRandomShellBlock(ChunkRandom random) {
        return Support.getWeightedRandom(validShellBlocks, random);
    }

    public ShellSpheroidType addShellSpeckles(BlockState blockState, Float weight) {
        shellSpeckleBlockStates.put(blockState, weight);
        return this;
    }

    public String getDescription() {
        return "ShellSpheroid";
    }

    public ShellSpheroid getRandomSphere(ChunkRandom chunkRandom) {
        int radius = getRandomRadius(chunkRandom);
        ArrayList<SpheroidDecorator> spheroidDecorators = getSpheroidDecoratorsWithChance(chunkRandom);
        ArrayList<SpheroidEntitySpawnDefinition> entityTypesToSpawn = getRandomEntityTypesToSpawn(chunkRandom);
        BlockState shellBlock = getRandomShellBlock(chunkRandom);
        int shellRadius = chunkRandom.nextInt(this.maxShellRadius - this.minShellRadius + 1) + this.minShellRadius;

        return new ShellSpheroid(chunkRandom, spheroidAdvancementIdentifier, radius, spheroidDecorators, entityTypesToSpawn, coreBlock, shellBlock, shellRadius, shellSpeckleBlockStates);
    }

}