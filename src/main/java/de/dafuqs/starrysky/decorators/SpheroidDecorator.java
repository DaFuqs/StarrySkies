package de.dafuqs.starrysky.decorators;

import com.mojang.serialization.Codec;
import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.ArrayList;
import java.util.Random;

public abstract class SpheroidDecorator extends Feature {

    public SpheroidDecorator(Codec configCodec) {
        super(DefaultFeatureConfig.CODEC);
    }

    // the vanilla method
    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, FeatureConfig config) {
        return false;
        /*for(BlockPos bp : getDecorationBlockPosInChunk(chunk, blockPos)) {

        }*/
    }

    /**
     * In contrast to vanilla the spheroid decorators are queried by the spheroid
     * not ran after the chunk generation
     * The spheroid tracks all blocks that can be decorated and the decorator
     * takes them, checks for spawning criteria and
     * @param chunk the chunk to generate the feature in
     * @param blockPos the blockPos to generate this feature in / on
     * @param random the chunkRandom
     */
    public abstract void decorateSpheroid(ChunkRegion chunkRegion, Chunk chunk, Spheroid spheroid, ArrayList<BlockPos> blockPos, ChunkRandom random);

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
