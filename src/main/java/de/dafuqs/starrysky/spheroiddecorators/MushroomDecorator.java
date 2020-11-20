package de.dafuqs.starrysky.spheroiddecorators;

import de.dafuqs.starrysky.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;

public class MushroomDecorator extends SpheroidDecorator {

    private static final BlockState BROWN_MUSHROOM_BLOCKSTATE = Blocks.BROWN_MUSHROOM.getDefaultState();
    private static final BlockState RED_MUSHROOM_BLOCKSTATE   = Blocks.RED_MUSHROOM.getDefaultState();
    private static final int MUSHROOM_CHANCE = 50;

    @Override
    public void decorateSpheroid(WorldView worldView, Chunk chunk, Spheroid spheroid, ArrayList<BlockPos> blockPos, ChunkRandom random) {

        blockPos = getDecorationBlockPosInChunk(chunk, blockPos);

        for(BlockPos bp : blockPos) {
            int r = random.nextInt(MUSHROOM_CHANCE);

            if(r == 0) {
                chunk.setBlockState(bp.up(), RED_MUSHROOM_BLOCKSTATE, false);
            } else if(r < 3) {
                chunk.setBlockState(bp.up(), BROWN_MUSHROOM_BLOCKSTATE, false);
            }
        }

    }
}
