package de.dafuqs.starrysky.spheroiddecorators;

import de.dafuqs.starrysky.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;

public class SweetBerryDecorator extends SpheroidDecorator {

    private static final BlockState SWEET_BERRY_BUSH = Blocks.SWEET_BERRY_BUSH.getDefaultState();
    private static final int SWEET_BERRY_BUSH_CHANCE = 16;

    @Override
    public void decorateSpheroid(WorldView worldView, Chunk chunk, Spheroid spheroid, ArrayList<BlockPos> blockPos, ChunkRandom random) {

        blockPos = getDecorationBlockPosInChunk(chunk, blockPos);

        for(BlockPos bp : blockPos) {
            int r = random.nextInt(SWEET_BERRY_BUSH_CHANCE);

            if(r == 0) {
                chunk.setBlockState(bp.up(), SWEET_BERRY_BUSH, false);
            }
        }
    }
}
