package de.dafuqs.starrysky.decorators;

import com.mojang.serialization.Codec;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.ArrayList;
import java.util.Random;

public class GroundDecorator extends SpheroidDecorator {

    private final BlockState GROUND_BLOCKSTATE;
    private final float GROUND_CHANCE;

    /**
     * Replaces the block in the ground
     * A chance of 0 = 0%, 100 = 100%
     */
    public GroundDecorator(Codec configCodec, BlockState ground_blockstate, float ground_chance) {
        super(configCodec);
        GROUND_BLOCKSTATE = ground_blockstate;
        GROUND_CHANCE = ground_chance;
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, FeatureConfig config) {
        return false;
    }

    @Override
    public void decorateSpheroid(ChunkRegion chunkRegion, Chunk chunk, Spheroid spheroid, ArrayList<BlockPos> blockPos, ChunkRandom random) {

        blockPos = getDecorationBlockPosInChunk(chunk, blockPos);

        for(BlockPos bp : blockPos) {
            if(random.nextFloat() < GROUND_CHANCE) {
                chunk.setBlockState(bp, GROUND_BLOCKSTATE, false);
            }
        }

    }
}
