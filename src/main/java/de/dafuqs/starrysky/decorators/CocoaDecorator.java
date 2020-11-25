package de.dafuqs.starrysky.decorators;

import com.mojang.serialization.Codec;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CocoaBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.ArrayList;
import java.util.Random;

import static de.dafuqs.starrysky.Support.isBlockPosInChunkPos;

public class CocoaDecorator extends SpheroidDecorator {


    private static final BlockState GROWN_COCOA_BLOCKSTATE = Blocks.COCOA.getDefaultState().with(CocoaBlock.AGE, 2); // 2 = fully grown
    private static final BlockState AIR_BLOCKSTATE = Blocks.CAVE_AIR.getDefaultState();

    public CocoaDecorator(Codec configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, FeatureConfig config) {
        return false;
    }

    @Override
    public void decorateSpheroid(ChunkRegion chunkRegion, Chunk chunk, Spheroid spheroid, ArrayList<BlockPos> blockPos, ChunkRandom random) {
        for(int x = -2; x < 3; x++) {
            for (int y = -2; y < 3; y++) {
                for (int z = -2; z < 3; z++) {
                    BlockPos bp = spheroid.getPosition().up(y).north(x).east(z);
                    if(isBlockPosInChunkPos(chunk.getPos(), bp)) {
                        if(y == 0 && ((Math.abs(x) == 2) && Math.abs(z) == 1 || (Math.abs(z) == 2 && Math.abs(x) == 1))) {
                            Direction direction;
                            if(x == 0) {
                                if(z < 0) {
                                    direction = Direction.WEST;
                                } else {
                                    direction = Direction.EAST;
                                }

                            } else {
                                if(x < 0) {
                                    direction = Direction.SOUTH;
                                } else {
                                    direction = Direction.NORTH;
                                }
                            }
                            chunk.setBlockState(bp, GROWN_COCOA_BLOCKSTATE.with(HorizontalFacingBlock.FACING, direction), false);
                        } else {
                            if (Math.abs(y) != 2 || (Math.abs(x) != 2 && Math.abs(z) != 2)) {
                                chunk.setBlockState(bp, AIR_BLOCKSTATE, false);
                            }
                        }
                    }
                }
            }
        }
    }
}
