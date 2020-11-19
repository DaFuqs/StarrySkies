package de.dafuqs.starrysky.spheroidtypes;

import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.Support;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.ChunkRandom;

import java.util.LinkedHashMap;
import java.util.Random;


public class CoreSpheroidType extends SpheroidType {

    private final BlockState coreBlock;
    private final LinkedHashMap<BlockState, Float> shellBlockStates;
    private final int minCoreRadius;
    private final int maxCoreRadius;

    public CoreSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, BlockState coreBlock, BlockState shellBlock, int minSize, int maxSize, int minCoreRadius, int maxCoreRadius) {
        this(spheroidAdvancementIdentifier, coreBlock, new LinkedHashMap<BlockState, Float>(){{put(shellBlock, 1.0F);}}, minSize, maxSize, minCoreRadius, maxCoreRadius);
    }

    public CoreSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, BlockState coreBlock, LinkedHashMap<BlockState, Float> shellBlockStates, int minRadius, int maxRadius, int minCoreRadius, int maxCoreRadius) {
        super();

        this.spheroidAdvancementIdentifier = spheroidAdvancementIdentifier;
        this.coreBlock = coreBlock;
        this.shellBlockStates = shellBlockStates;
        this.minRadius = minRadius;
        this.maxRadius = maxRadius;
        this.minCoreRadius = minCoreRadius;
        this.maxCoreRadius = maxCoreRadius;
    }

    public int getRandomCoreRadius(Random random) {
        return random.nextInt(this.maxCoreRadius - this.minCoreRadius + 1) + this.minCoreRadius;
    }

    public BlockState getRandomShellBlock(ChunkRandom random) {
        return Support.getWeightedRandom(shellBlockStates, random);
    }

    public BlockState getCoreBlock() {
        return this.coreBlock;
    }

    public String getDescription() {
        return "CoreSpheroid";
    }

}