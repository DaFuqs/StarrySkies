package de.dafuqs.starrysky.decorators;

import com.mojang.serialization.Codec;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.ArrayList;
import java.util.Random;

public class MushroomDecorator extends SpheroidDecorator {

    private static final BlockState BROWN_MUSHROOM_BLOCKSTATE = Blocks.BROWN_MUSHROOM.getDefaultState();
    private static final BlockState RED_MUSHROOM_BLOCKSTATE   = Blocks.RED_MUSHROOM.getDefaultState();
    private static final int MUSHROOM_CHANCE = 50;

    public MushroomDecorator(Codec configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, FeatureConfig config) {
        return false;
    }

    @Override
    public void decorateSpheroid(ChunkRegion chunkRegion, Chunk chunk, Spheroid spheroid, ArrayList<BlockPos> blockPos, ChunkRandom random) {

        blockPos = getDecorationBlockPosInChunk(chunk, blockPos);

        for(BlockPos bp : blockPos) {
            if(chunk.getBlockState(bp.up()).isAir()) {
                int r = random.nextInt(MUSHROOM_CHANCE);

                if (r == 0) {
                    chunk.setBlockState(bp.up(), RED_MUSHROOM_BLOCKSTATE, false);
                } else if (r < 3) {
                    chunk.setBlockState(bp.up(), BROWN_MUSHROOM_BLOCKSTATE, false);
                }
            }
        }

    }
}
