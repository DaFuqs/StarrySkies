package de.dafuqs.starrysky.dimension.decorators;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.ArrayList;
import java.util.Random;


public class CenterPondDecorator extends SpheroidDecorator {

    private final Identifier lootTable;
    private final float chance;


    public CenterPondDecorator(Identifier lootTable, float chance) {
        this.lootTable = lootTable;
        this.chance = chance;
    }

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, ArrayList<BlockPos> decorationBlockPositions, Random random) {
        // doesn't make sense on small spheroids
        if(spheroid.getRadius() > 10) {
            int pondSize = (int) (spheroid.getRadius() / 3.5);
            BlockPos spheroidTop = spheroid.getPosition().up(spheroid.getRadius());

            int waterLevelY = spheroidTop.getY();
            boolean waterLevelSet = false;

            for (int x = -pondSize - 1; x < pondSize + 1; x++) {
                for (int y = -pondSize; y < 1; y++) {
                    for (int z = -pondSize -1; z < pondSize; z++) {
                        BlockPos currentBlockPos = spheroidTop.add(x, y, z);
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

            // if there is not enouth room for water: just cancel
            // not nice, but eh
            if(spheroid.getPosition().getY() - waterLevelY > pondSize) {
                return;
            }

            boolean hasLootChest = random.nextFloat() < this.chance;
            BlockPos lootChestPosition = null;

            int pond15 = (int) Math.round(pondSize * 1.5);
            for (int x = -pond15; x < pond15; x++) {
                for (int y = -pondSize; y < pondSize; y++) {
                    for (int z = -pond15; z < pond15; z++) {
                        BlockPos currentBlockPos = spheroidTop.add(x, y, z);

                        BlockState blockState = null;
                        if (currentBlockPos.getY() > waterLevelY) {
                            blockState = Blocks.AIR.getDefaultState();
                        } else {
                            double distance = Support.distance(currentBlockPos, spheroidTop);
                            double pondDistance = distance / pondSize;
                            if (pondDistance < 1) {
                                if(hasLootChest && x == 0 && z == 0 && lootChestPosition == null) {
                                    lootChestPosition = currentBlockPos;
                                } else {
                                    blockState = Blocks.WATER.getDefaultState();
                                }
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

            if(lootChestPosition != null) {
                placeLootChestAtPosition(world, lootChestPosition, lootTable, random);
            }
        }
    }
}
