package de.dafuqs.starrysky.spheroidtypes;

import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.Support;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.ChunkRandom;

import java.util.LinkedHashMap;


public class ShellSpheroidType extends SpheroidType {

    private final BlockState coreBlock;
    private final LinkedHashMap<BlockState, Float> validShellBlocks;
    private final int minShellRadius; //Minimum shell thickness, should be at least
    private final int maxShellRadius; //Maximum shell thickness

    public ShellSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, BlockState coreBlock, BlockState shellBlock, int minSize, int maxSize, int minShellRadius, int maxShellRadius) {
        this(spheroidAdvancementIdentifier, coreBlock, new LinkedHashMap<BlockState, Float>(){{put(shellBlock, 1.0F);}}, minSize, maxSize, minShellRadius, maxShellRadius);
    }

    public ShellSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, BlockState coreBlock, LinkedHashMap<BlockState, Float> validShellBlocks, int minSize, int maxSize, int minShellRadius, int maxShellRadius) {
        super();

        this.spheroidAdvancementIdentifier = spheroidAdvancementIdentifier;
        this.coreBlock = coreBlock;
        this.validShellBlocks = validShellBlocks;
        this.minRadius = minSize;
        this.maxRadius = maxSize;
        this.minShellRadius = minShellRadius;
        this.maxShellRadius = maxShellRadius;
    }

    public BlockState getCoreBlock() {
        return coreBlock;
    }

    public int getRandomShellRadius(ChunkRandom random) {
        return random.nextInt(this.maxShellRadius - this.minShellRadius + 1) + this.minShellRadius;
    }

    public BlockState getRandomShellBlock(ChunkRandom random) {
        return Support.getWeightedRandom(validShellBlocks, random);
    }

    public String getDescription() {
        return "ShellSpheroid";
    }

}