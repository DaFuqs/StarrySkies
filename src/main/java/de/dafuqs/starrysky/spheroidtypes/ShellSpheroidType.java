package de.dafuqs.starrysky.spheroidtypes;

import de.dafuqs.starrysky.SpheroidData.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.SpheroidData.SpheroidAdvancementGroup;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.ChunkRandom;

import java.util.HashMap;


public class ShellSpheroidType extends SpheroidType {

    private BlockState coreBlock;
    private HashMap<BlockState, Float> validShellBlocks;
    private int minShellRadius; //Minimum shell thickness, should be at least
    private int maxShellRadius; //Maximum shell thickness

    public ShellSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, BlockState coreBlock, BlockState shellBlock, int minSize, int maxSize, int minShellRadius, int maxShellRadius) {
        this(spheroidAdvancementIdentifier, coreBlock, new HashMap<BlockState, Float>(){{put(shellBlock, 1.0F);}}, minSize, maxSize, minShellRadius, maxShellRadius);
    }

    public ShellSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, BlockState coreBlock, HashMap<BlockState, Float> validShellBlocks, int minSize, int maxSize, int minShellRadius, int maxShellRadius) {
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