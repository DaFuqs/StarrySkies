package de.dafuqs.starrysky.spheroiddecorators;

import de.dafuqs.starrysky.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;

public class DoublePlantDecorator extends SpheroidDecorator {

    private final BlockState PLANT_BLOCKSTATE;
    private final float PLANT_CHANCE;

    /**
     * A chance of 0 = 0%, 100 = 100%
      */
    public DoublePlantDecorator(BlockState blockState, float chance) {
        this.PLANT_BLOCKSTATE = blockState;
        this.PLANT_CHANCE = chance;
    }

    @Override
    public void decorateSpheroid(WorldView worldView, Chunk chunk, Spheroid spheroid, ArrayList<BlockPos> blockPos, ChunkRandom random) {
        blockPos = getDecorationBlockPosInChunk(chunk, blockPos);
        for(BlockPos bp : blockPos) {
            if (chunk.getBlockState(bp.up()).isAir() && chunk.getBlockState(bp.up(2)).isAir()) {
                if (random.nextFloat() < PLANT_CHANCE) {
                    chunk.setBlockState(bp.up(), PLANT_BLOCKSTATE, false);
                }
            }
        }
    }

}
