package de.dafuqs.starrysky.spheroiddecorators;

import de.dafuqs.starrysky.spheroids.Spheroid;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;

public class CactusDecorator extends SpheroidDecorator {

    private static final Block cactusBlock = Blocks.CACTUS;
    private static final BlockState cactusBlockState = Blocks.CACTUS.getDefaultState();
    private static final int CACTUS_CHANCE = 30; // random. 0 = cactus with 1 height, 2 = 3 height

    @Override
    public void decorateSpheroid(WorldView worldView, Chunk chunk, Spheroid spheroid, ArrayList<BlockPos> blockPos, ChunkRandom random) {

        blockPos = getDecorationBlockPosInChunk(chunk, blockPos);

        for(BlockPos bp : blockPos) {
            int r = random.nextInt(CACTUS_CHANCE);

            if(r < 4) {
                for(int i = 1; i < r; i++) {
                    if(cactusBlock.canPlaceAt(cactusBlockState, worldView, bp.up(i))) {
                        chunk.setBlockState(bp.up(i), cactusBlockState, false);
                    }
                }
            }
        }
    }
}
