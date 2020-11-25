package de.dafuqs.starrysky.decorators;

import com.mojang.serialization.Codec;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.Block;
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

public class CactusDecorator extends SpheroidDecorator {

    private static final Block cactusBlock = Blocks.CACTUS;
    private static final BlockState cactusBlockState = Blocks.CACTUS.getDefaultState();
    private static final int CACTUS_CHANCE = 40; // random. 0 = cactus with 1 height, 2 = 3 height

    public CactusDecorator(Codec configCodec) {
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
            int r = random.nextInt(CACTUS_CHANCE);

            if(r < 4) {
                for(int i = 1; i < r; i++) {
                    if(cactusBlock.canPlaceAt(cactusBlockState, chunkRegion, bp.up(i))) {
                        chunk.setBlockState(bp.up(i), cactusBlockState, false);
                    }
                }
            }
        }
    }


}
