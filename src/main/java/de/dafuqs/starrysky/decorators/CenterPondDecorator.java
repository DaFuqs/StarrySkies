package de.dafuqs.starrysky.decorators;

import com.mojang.serialization.Codec;
import de.dafuqs.starrysky.Support;
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

public class CenterPondDecorator extends SpheroidDecorator {

    public CenterPondDecorator(Codec configCodec) {
        super(configCodec);
    }

    public void decorateSpheroid(ChunkRegion chunkRegion, Chunk chunk, Spheroid spheroid, ArrayList<BlockPos> blockPos, ChunkRandom random) {
        // doesn't make sense on small spheroids
        if(spheroid.getRadius() > 10) {
            int pondSize = spheroid.getRadius() / 3;
            BlockPos spheroidTop = spheroid.getPosition().up(spheroid.getRadius());

            int waterLevelY = spheroidTop.getY();
            boolean waterLevelSet = false;

            for (int i = -pondSize - 1; i < pondSize + 1; i++) {
                for (int j = -pondSize - 1; j < pondSize + 1; j++) {
                    for (int k = -pondSize - 1; k < 0; k++) {
                        BlockPos currentBlockPos = spheroidTop.add(i, j, k);
                        if (Support.isBlockPosInChunkPos(chunk.getPos(), currentBlockPos)) {
                            if (chunk.getBlockState(currentBlockPos).isAir()) {
                                waterLevelY = currentBlockPos.getY() - 1;
                                waterLevelSet = true;
                                break;
                            }
                        }
                    }
                    if (waterLevelSet) {
                        break;
                    }
                }
                if (waterLevelSet) {
                    break;
                }
            }

            for (int i = (int) (-pondSize * 1.5); i < pondSize * 1.5; i++) {
                for (int j = -pondSize; j < pondSize; j++) {
                    for (int k = (int) (-pondSize * 1.5); k < pondSize * 1.5; k++) {
                        BlockPos currentBlockPos = spheroidTop.add(i, j, k);

                        BlockState blockState = null;
                        if (currentBlockPos.getY() > waterLevelY) {
                            blockState = Blocks.AIR.getDefaultState();
                        } else {
                            double distance = Support.distance(currentBlockPos, spheroidTop);
                            double pondDistance = distance / pondSize;
                            if (pondDistance < 1) {
                                blockState = Blocks.WATER.getDefaultState();
                            } else if (pondDistance < 1.65) {
                                blockState = Blocks.SAND.getDefaultState();
                            }
                        }

                        if (blockState != null && Support.isBlockPosInChunkPos(chunk.getPos(), currentBlockPos)) {
                            if (!chunk.getBlockState(currentBlockPos).isAir()) {
                                chunk.setBlockState(currentBlockPos, blockState, false);
                            }
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
