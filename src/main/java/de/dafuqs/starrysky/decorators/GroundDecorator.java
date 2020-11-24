package de.dafuqs.starrysky.decorators;

import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;

public class GroundDecorator extends SpheroidDecorator {

    private final BlockState GROUND_BLOCKSTATE;
    private final float GROUND_CHANCE;

    /**
     * Replaces the block in the ground
     * A chance of 0 = 0%, 100 = 100%
      */
    public GroundDecorator(BlockState blockState, float chance) {
        this.GROUND_BLOCKSTATE = blockState;
        this.GROUND_CHANCE = chance;
    }
    
    @Override
    public void decorateSpheroid(WorldView worldView, Chunk chunk, Spheroid spheroid, ArrayList<BlockPos> blockPos, ChunkRandom random) {

        blockPos = getDecorationBlockPosInChunk(chunk, blockPos);

        for(BlockPos bp : blockPos) {
            if(random.nextFloat() < GROUND_CHANCE) {
                chunk.setBlockState(bp, GROUND_BLOCKSTATE, false);
            }
        }

    }
}
