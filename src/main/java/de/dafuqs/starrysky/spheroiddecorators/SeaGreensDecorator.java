package de.dafuqs.starrysky.spheroiddecorators;

import de.dafuqs.starrysky.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallSeagrassBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;

public class SeaGreensDecorator extends SpheroidDecorator {

    // those are all always "waterlogged"
    private static final BlockState KELP = Blocks.KELP.getDefaultState(); // the top
    private static final BlockState KELP_PLANT = Blocks.KELP_PLANT.getDefaultState(); // the middle
    private static final BlockState SEAGRASS = Blocks.SEAGRASS.getDefaultState();
    private static final BlockState TALL_SEAGRASS_UPPER = Blocks.TALL_SEAGRASS.getDefaultState().with(TallSeagrassBlock.HALF, DoubleBlockHalf.UPPER);
    private static final BlockState TALL_SEAGRASS_LOWER = Blocks.TALL_SEAGRASS.getDefaultState().with(TallSeagrassBlock.HALF, DoubleBlockHalf.LOWER);

    public void decorateSpheroid(WorldView worldView, Chunk chunk, Spheroid spheroid, ArrayList<BlockPos> blockPos, ChunkRandom random) {
        for(BlockPos bp : getDecorationBlockPosInChunk(chunk, blockPos)) {
            int r = random.nextInt(4);

            if (r == 0) {
                int kelpHeight = random.nextInt(8);
                for(int i = 0; i < kelpHeight; i++) {
                    if (chunk.getBlockState(bp.up(i+1)).getBlock() == Blocks.WATER) {
                        if (chunk.getBlockState(bp.up(i+2)).getBlock() == Blocks.WATER && i < kelpHeight - 1) {
                            chunk.setBlockState(bp.up(i+1), KELP_PLANT, false); // middle parts
                        } else {
                            chunk.setBlockState(bp.up(i+1), KELP, false); // the top
                        }
                    }
                }
            } else if (r == 1) {
                chunk.setBlockState(bp.up(), SEAGRASS, false);
            } else if (r == 2) {
                if (chunk.getBlockState(bp.up(2)).getBlock() == Blocks.WATER) {
                    chunk.setBlockState(bp.up(2), TALL_SEAGRASS_UPPER, false);
                    chunk.setBlockState(bp.up(), TALL_SEAGRASS_LOWER, false);
                }
            }
        }
    }

}
