package de.dafuqs.starrysky.dimension.decorators;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.ArrayList;
import java.util.Random;


public class CenterPondDecorator extends SpheroidDecorator {

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, ArrayList<BlockPos> decorationBlockPositions, Random random) {
        // doesn't make sense on small spheroids
        if(spheroid.getRadius() > 10) {
            int pondSize = (int) (spheroid.getRadius() / 3.5);
            BlockPos spheroidTop = spheroid.getPosition().up(spheroid.getRadius());

            int waterLevelY = spheroidTop.getY();
            boolean waterLevelSet = false;

            for (int i = -pondSize - 1; i < pondSize + 1; i++) {
                for (int j = -pondSize; j < 1; j++) {
                    for (int k = -pondSize -1; k < pondSize; k++) {
                        BlockPos currentBlockPos = spheroidTop.add(i, j, k);
                        if (world.getBlockState(currentBlockPos).isAir()) {
                            waterLevelY = currentBlockPos.getY() - 1;
                            waterLevelSet = true;
                            break;
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
                            } else if (pondDistance < 1.70) {
                                blockState = Blocks.SAND.getDefaultState();
                            }
                        }

                        if (blockState != null) {
                            if (!world.getBlockState(currentBlockPos).isAir()) {
                                world.setBlockState(currentBlockPos, blockState, 3);
                            }
                        }
                    }
                }
            }
        }
    }
}
