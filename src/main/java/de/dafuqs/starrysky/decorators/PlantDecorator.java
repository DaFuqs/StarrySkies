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

public class PlantDecorator extends SpheroidDecorator {

    private final BlockState PLANT_BLOCKSTATE;
    private final float PLANT_CHANCE;

    /**
     * A chance of 0 = 0%, 100 = 100%
     */
    public PlantDecorator(Codec configCodec, BlockState plant_blockstate, float plant_chance) {
        super(configCodec);
        PLANT_BLOCKSTATE = plant_blockstate;
        PLANT_CHANCE = plant_chance;
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, FeatureConfig config) {
        return false;
    }

    @Override
    public void decorateSpheroid(ChunkRegion chunkRegion, Chunk chunk, Spheroid spheroid, ArrayList<BlockPos> blockPos, ChunkRandom random) {
        blockPos = getDecorationBlockPosInChunk(chunk, blockPos);
        for(BlockPos bp : blockPos) {
            if (chunk.getBlockState(bp.up()).isAir()) {
                if(random.nextFloat() < PLANT_CHANCE) {
                    chunk.setBlockState(bp.up(), PLANT_BLOCKSTATE, false);
                }
            }
        }
    }

}
