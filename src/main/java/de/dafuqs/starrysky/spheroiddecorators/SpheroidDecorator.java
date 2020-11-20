package de.dafuqs.starrysky.spheroiddecorators;

import de.dafuqs.starrysky.spheroids.Spheroid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;

public abstract class SpheroidDecorator {

    /**
     * In contrast to vanilla the spheroid decorators are queried by the spheroid
     * not ran after the chunk generation
     * The spheroid tracks all blocks that can be decorated and the decorator
     * takes them, checks for spawning criteria and
     * @param chunk the chunk to generate the feature in
     * @param blockPos the blockPos to generate this feature in / on
     * @param random the chunkRandom
     */
    public abstract void decorateSpheroid(WorldView worldView, Chunk chunk, Spheroid spheroid, ArrayList<BlockPos> blockPos, ChunkRandom random);

    protected ArrayList<BlockPos> getDecorationBlockPosInChunk(Chunk chunk, ArrayList<BlockPos> decorationBlocks) {
        ArrayList<BlockPos> decorationBlockPosInChunk = new ArrayList();
        for(BlockPos currentDecorationPos : decorationBlocks) {
            if(isBlockPosInChunk(chunk, currentDecorationPos)) {
                decorationBlockPosInChunk.add(currentDecorationPos);
            }
        }
        return decorationBlockPosInChunk;
    }

    protected boolean isBlockPosInChunk(Chunk chunk, BlockPos blockPos) {
        return (blockPos.getX() >= chunk.getPos().getStartX()
                && blockPos.getX() < chunk.getPos().getStartX() + 16
                && blockPos.getZ() >= chunk.getPos().getStartZ()
                && blockPos.getZ() < chunk.getPos().getStartZ() + 16);
    }


}
