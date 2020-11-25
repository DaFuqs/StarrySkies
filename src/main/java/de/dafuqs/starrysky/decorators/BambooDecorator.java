package de.dafuqs.starrysky.decorators;

import com.mojang.serialization.Codec;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.BambooBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.BambooLeaves;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.ArrayList;
import java.util.Random;

public class BambooDecorator extends SpheroidDecorator {

    private static final Block bambooBlock = Blocks.BAMBOO;
    private static final BlockState bambooBlockState = Blocks.BAMBOO.getDefaultState().with(BambooBlock.AGE, 0).with(BambooBlock.STAGE, 0);
    private static final BlockState bambooSaplingBlockState = Blocks.BAMBOO_SAPLING.getDefaultState();
    private static final int BAMBOO_CHANCE = 10;

    public BambooDecorator(Codec configCodec) {
        super(configCodec);
    }

    @Override
    public void decorateSpheroid(ChunkRegion chunkRegion, Chunk chunk, Spheroid spheroid, ArrayList<BlockPos> blockPos, ChunkRandom random) {

        blockPos = getDecorationBlockPosInChunk(chunk, blockPos);

        for(BlockPos bp : blockPos) {
            int r = random.nextInt(BAMBOO_CHANCE);

            if(r == 0) {
                if(bambooBlock.canPlaceAt(bambooBlockState, chunkRegion, bp.up())) {
                    chunk.setBlockState(bp.up(), bambooSaplingBlockState, false);
                }
            } else if(r < 8) {
                for(int i = 1; i < r; i++) {
                    if(bambooBlock.canPlaceAt(bambooBlockState, chunkRegion, bp.up(i))) {
                        if(i == 3 && r < 5) {
                            chunk.setBlockState(bp.up(i), bambooBlockState.with(BambooBlock.LEAVES, BambooLeaves.NONE), false);
                        } else if(i > 4) {
                            chunk.setBlockState(bp.up(i), bambooBlockState.with(BambooBlock.LEAVES, BambooLeaves.LARGE), false);
                        } else if(i > 2) {
                            chunk.setBlockState(bp.up(i), bambooBlockState.with(BambooBlock.LEAVES, BambooLeaves.SMALL), false);
                        } else {
                            chunk.setBlockState(bp.up(i), bambooBlockState.with(BambooBlock.LEAVES, BambooLeaves.NONE), false);
                        }
                    }
                }
            }
        }

    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, FeatureConfig config) {
        return false;
    }

}
