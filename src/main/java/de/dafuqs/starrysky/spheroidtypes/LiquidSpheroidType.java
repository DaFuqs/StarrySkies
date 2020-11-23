package de.dafuqs.starrysky.spheroidtypes;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.Support;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.ChunkRandom;

import java.util.LinkedHashMap;
import java.util.Random;

public class LiquidSpheroidType extends SpheroidType {

    private final BlockState liquid;
    private final LinkedHashMap<BlockState, Float> validShellBlocks;

    private final int minShellRadius;
    private final int maxShellRadius;
    private final int minFillPercent;
    private final int maxFillPercent;
    private final int holeInBottomPercent;

    private BlockState coreBlock;
    private int minCoreRadius;
    private int maxCoreRadius;

    public LiquidSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, BlockState liquid, BlockState shellBlock, int minSize, int maxSize, int minShellRadius, int maxShellRadius, int minFillPercent, int maxFillPercent, int holeInBottomPercent) {
        this (spheroidAdvancementIdentifier,  liquid, new LinkedHashMap<BlockState, Float>(){{put(shellBlock, 1.0F);}}, minSize, maxSize, minShellRadius, maxShellRadius, minFillPercent, maxFillPercent, holeInBottomPercent);
    }

    public LiquidSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, BlockState liquid, LinkedHashMap<BlockState, Float> validShellBlocks, int minSize, int maxSize, int minShellRadius, int maxShellRadius, int minFillPercent, int maxFillPercent, int holeInBottomPercent) {
        super();

        if(liquid == null) {
            StarrySkyCommon.LOGGER.error("LiquidSpheroidType: Registered a SpheroidType with null liquid!");
        }
        if(validShellBlocks == null || validShellBlocks.size() == 0) {
            StarrySkyCommon.LOGGER.error("LiquidSpheroidType: Registered a SpheroidType with empty validShellBlocks!");
        }

        this.spheroidAdvancementIdentifier = spheroidAdvancementIdentifier;
        this.liquid = liquid;
        this.validShellBlocks = validShellBlocks;
        this.minRadius = minSize;
        this.maxRadius = maxSize;
        this.minShellRadius = minShellRadius;
        this.maxShellRadius = maxShellRadius;
        this.minFillPercent = minFillPercent;
        this.maxFillPercent = maxFillPercent;
        this.holeInBottomPercent = holeInBottomPercent;
    }

    public String getDescription() {
        return "LiquidSpheroid";
    }

    public BlockState getCoreBlock() {
        return coreBlock;
    }

    public final BlockState getLiquid() {
        return liquid;
    }

    public LiquidSpheroidType setCoreBlock(BlockState coreBlock, int minCoreRadius, int maxCoreRadius) {
        this.coreBlock = coreBlock;
        this.minCoreRadius = minCoreRadius;
        this.maxCoreRadius = maxCoreRadius;
        return this;
    }

    public BlockState getRandomShellBlock(ChunkRandom random) {
        return Support.getWeightedRandom(validShellBlocks, random);
    }

    public boolean getRandomHoleInBottom(Random random) {
        return random.nextInt(100) < this.holeInBottomPercent;
    }

    public int getRandomFillPercent(Random random) {
        return random.nextInt(maxFillPercent - minFillPercent  + 1) + minFillPercent;
    }

    public int getRandomShellRadius(Random random) {
        return random.nextInt(maxShellRadius - minShellRadius  + 1) + minShellRadius;
    }

    public int getRandomCoreRadius(Random random) {
        return random.nextInt(maxCoreRadius - minCoreRadius  + 1) + minCoreRadius;
    }
}
