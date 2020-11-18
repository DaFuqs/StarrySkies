package de.dafuqs.starrysky.spheroidtypes;

import de.dafuqs.starrysky.SpheroidData.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.SpheroidData.SpheroidAdvancementGroup;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.ChunkRandom;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;

public class DoubleCoreSpheroidType extends SpheroidType {

    private final BlockState innerCoreBlock;
    private final BlockState outerCoreBlock;
    private final LinkedHashMap<BlockState, Float> validShellBlocks;
    private final int minInnerCoreRadius;
    private final int maxInnerCoreRadius;
    private final int minShellRadius;
    private final int maxShellRadius;

    public DoubleCoreSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, BlockState innerCoreBlock, BlockState outerCoreBlock, BlockState shellBlock, int minSize, int maxSize, int minInnerCoreRadius, int maxInnerCoreRadius, int minShellRadius, int maxShellRadius) {
        this(spheroidAdvancementIdentifier, innerCoreBlock, outerCoreBlock, new LinkedHashMap<BlockState, Float>(){{put(shellBlock, 1.0F);}}, minSize, maxSize, minInnerCoreRadius, maxInnerCoreRadius,  minShellRadius, maxShellRadius);
    }

    public DoubleCoreSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, BlockState innerCoreBlock, BlockState outerCoreBlock, LinkedHashMap<BlockState, Float> validShellBlocks, int minSize, int maxSize, int minInnerCoreRadius, int maxInnerCoreRadius, int minShellRadius, int maxShellRadius) {
        this.spheroidAdvancementIdentifier = spheroidAdvancementIdentifier;
        this.minRadius = minSize;
        this.maxRadius = maxSize;
        this.innerCoreBlock = innerCoreBlock;
        this.outerCoreBlock = outerCoreBlock;
        this.validShellBlocks = validShellBlocks;
        this.minInnerCoreRadius = minInnerCoreRadius;
        this.maxInnerCoreRadius = maxInnerCoreRadius;
        this.minShellRadius = minShellRadius;
        this.maxShellRadius = maxShellRadius;
    }

    public int getRandomInnerCoreRadius(Random random) {
        return random.nextInt(this.maxInnerCoreRadius - this.minInnerCoreRadius + 1) + this.minInnerCoreRadius;
    }

    public int getRandomShellRadius(Random random) {
        return random.nextInt(this.maxShellRadius - this.minShellRadius + 1) + this.minShellRadius;
    }

    public BlockState getRandomShellBlock(ChunkRandom random) {
        return Support.getWeightedRandom(validShellBlocks, random);
    }

    public BlockState getInnerCoreBlock() {
        return innerCoreBlock;
    }

    public BlockState getOuterCoreBlock() {
        return outerCoreBlock;
    }

    public String getDescription() {
        return "DoubleCoreSpheroid";
    }



}
