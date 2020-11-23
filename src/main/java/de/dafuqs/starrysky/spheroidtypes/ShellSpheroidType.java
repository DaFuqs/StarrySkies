package de.dafuqs.starrysky.spheroidtypes;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.spheroids.ShellSpheroid;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.ChunkRandom;

import java.util.LinkedHashMap;


public class ShellSpheroidType extends SpheroidType {

    private final BlockState coreBlock;
    private final LinkedHashMap<BlockState, Float> validShellBlocks;
    private final int minShellRadius; //Minimum shell thickness, should be at least
    private final int maxShellRadius; //Maximum shell thickness
    private final LinkedHashMap<BlockState, Float> shellSpeckleBlockStates = new LinkedHashMap<>();

    public ShellSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, BlockState coreBlock, BlockState shellBlock, int minSize, int maxSize, int minShellRadius, int maxShellRadius) {
        this(spheroidAdvancementIdentifier, coreBlock, new LinkedHashMap<BlockState, Float>(){{put(shellBlock, 1.0F);}}, minSize, maxSize, minShellRadius, maxShellRadius);
    }

    public ShellSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, BlockState coreBlock, LinkedHashMap<BlockState, Float> validShellBlocks, int minSize, int maxSize, int minShellRadius, int maxShellRadius) {
        super();

        if(coreBlock == null) {
            StarrySkyCommon.LOGGER.error("ShellSpheroidType: Registered a SpheroidType with empty coreBlock!");
        }
        if(validShellBlocks == null || validShellBlocks.size() == 0) {
            StarrySkyCommon.LOGGER.error("ShellSpheroidType: Registered a SpheroidType with empty validShellBlocks!");
        }

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

    public ShellSpheroidType addShellSpeckles(BlockState blockState, Float weight) {
        shellSpeckleBlockStates.put(blockState, weight);
        return this;
    }

    public LinkedHashMap<BlockState, Float> getShellSpeckleBlockStates() {
        return this.shellSpeckleBlockStates;
    }

    public String getDescription() {
        return "ShellSpheroid";
    }

    public ShellSpheroid getRandomSphere(ChunkRandom chunkRandom) {
        return new ShellSpheroid(this, chunkRandom);
    }

}