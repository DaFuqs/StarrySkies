package de.dafuqs.starrysky.spheroidtypes.special_overworld;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.spheroidtypes.SpheroidType;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;
import java.util.LinkedHashMap;


public class CoralSpheroidType extends SpheroidType {

    private final ArrayList<BlockState> coralBlockStates;
    private final ArrayList<BlockState> waterloggableBlockStates;
    private final LinkedHashMap<BlockState, Float> validShellBlocks;
    private final int minShellRadius;
    private final int maxShellRadius;

    public CoralSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, LinkedHashMap<BlockState, Float> validShellBlocks, ArrayList<BlockState> coralBlockStates, ArrayList<BlockState> waterloggableBlockStates, int minSize, int maxSize, int minShellRadius, int maxShellRadius) {
        super();

        this.spheroidAdvancementIdentifier = spheroidAdvancementIdentifier;
        this.validShellBlocks = validShellBlocks;
        this.coralBlockStates = coralBlockStates;
        this.waterloggableBlockStates = waterloggableBlockStates;
        this.minRadius = minSize;
        this.maxRadius = maxSize;
        this.minShellRadius = minShellRadius;
        this.maxShellRadius = maxShellRadius;
    }

    public int getRandomShellRadius(ChunkRandom random) {
        return random.nextInt(this.maxShellRadius - this.minShellRadius + 1) + this.minShellRadius;
    }

    public BlockState getRandomShellBlock(ChunkRandom random) {
        return Support.getWeightedRandom(validShellBlocks, random);
    }

    public BlockState getRandomCoralBlock(ChunkRandom random) {
        return this.coralBlockStates.get(random.nextInt(coralBlockStates.size()));
    }

    public BlockState getRandomWaterLoggableBlock(ChunkRandom random) {
        return this.waterloggableBlockStates.get(random.nextInt(waterloggableBlockStates.size()));
    }

    public String getDescription() {
        return "CoralSpheroid";
    }

}