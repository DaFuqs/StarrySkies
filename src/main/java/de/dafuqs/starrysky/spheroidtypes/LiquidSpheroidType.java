package de.dafuqs.starrysky.spheroidtypes;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.spheroiddecorators.SpheroidDecorator;
import de.dafuqs.starrysky.spheroids.LiquidSpheroid;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;
import java.util.LinkedHashMap;

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

    public LiquidSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int minRadius, int maxRadius, BlockState liquid, BlockState shellBlock, int minShellRadius, int maxShellRadius, int minFillPercent, int maxFillPercent, int holeInBottomPercent) {
        this (spheroidAdvancementIdentifier, minRadius, maxRadius, liquid, new LinkedHashMap<BlockState, Float>(){{put(shellBlock, 1.0F);}}, minShellRadius, maxShellRadius, minFillPercent, maxFillPercent, holeInBottomPercent);
    }

    public LiquidSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int minRadius, int maxRadius, BlockState liquid, LinkedHashMap<BlockState, Float> validShellBlocks, int minShellRadius, int maxShellRadius, int minFillPercent, int maxFillPercent, int holeInBottomPercent) {
        super(spheroidAdvancementIdentifier, minRadius, maxRadius);

        if(liquid == null) {
            StarrySkyCommon.LOGGER.error("LiquidSpheroidType: Registered a SpheroidType with null liquid!");
        }
        if(validShellBlocks == null || validShellBlocks.size() == 0) {
            StarrySkyCommon.LOGGER.error("LiquidSpheroidType: Registered a SpheroidType with empty validShellBlocks!");
        }

        this.liquid = liquid;
        this.validShellBlocks = validShellBlocks;
        this.minShellRadius = minShellRadius;
        this.maxShellRadius = maxShellRadius;
        this.minFillPercent = minFillPercent;
        this.maxFillPercent = maxFillPercent;
        this.holeInBottomPercent = holeInBottomPercent;
    }

    public String getDescription() {
        return "LiquidSpheroid";
    }

    public LiquidSpheroidType setCoreBlock(BlockState coreBlock, int minCoreRadius, int maxCoreRadius) {
        this.coreBlock = coreBlock;
        this.minCoreRadius = minCoreRadius;
        this.maxCoreRadius = maxCoreRadius;
        return this;
    }

    public LiquidSpheroid getRandomSphere(ChunkRandom chunkRandom) {
        int radius = getRandomRadius(chunkRandom);
        ArrayList<SpheroidDecorator> spheroidDecorators = getSpheroidDecoratorsWithChance(chunkRandom);

        BlockState shellBlock = Support.getWeightedRandom(validShellBlocks, chunkRandom);
        int shellRadius = chunkRandom.nextInt(maxShellRadius - minShellRadius  + 1) + minShellRadius;
        int fillPercent = chunkRandom.nextInt(maxFillPercent - minFillPercent  + 1) + minFillPercent;
        boolean holeInBottom = chunkRandom.nextInt(100) < this.holeInBottomPercent;
        int coreRadius = chunkRandom.nextInt(maxCoreRadius - minCoreRadius  + 1) + minCoreRadius;

        return new LiquidSpheroid(chunkRandom, spheroidAdvancementIdentifier, radius, spheroidDecorators, liquid, shellBlock, shellRadius, fillPercent, holeInBottom, coreBlock, coreRadius);
    }

}
