package de.dafuqs.starrysky.dimension.decorators;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.Random;

public abstract class SpheroidDecorator {

    /**
     * In contrast to vanilla the spheroid decorators are queried by the spheroid
     * not ran after the chunk generation
     * The spheroid tracks all blocks that can be decorated and the decorator
     * takes them, checks for spawning criteria and
     */
    public abstract void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, ArrayList<BlockPos> decorationBlockPositions, Random random);

    protected ArrayList<BlockPos> getDecorationBlockPosInChunk(Chunk chunk, ArrayList<BlockPos> decorationBlocks) {
        ArrayList<BlockPos> decorationBlockPosInChunk = new ArrayList<>();
        for(BlockPos currentDecorationPos : decorationBlocks) {
            if(Support.isBlockPosInChunkPos(chunk.getPos(), currentDecorationPos)) {
                decorationBlockPosInChunk.add(currentDecorationPos);
            }
        }
        return decorationBlockPosInChunk;
    }

}
