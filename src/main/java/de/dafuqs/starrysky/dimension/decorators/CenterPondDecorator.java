package de.dafuqs.starrysky.dimension.decorators;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
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

            boolean hasLootChest = random.nextFloat() < this.chance;
            BlockPos lootChestPosition = null;

            for (int x = (int) (-pondSize * 1.5); x < pondSize * 1.5+1; x++) {
                for (int y = -pondSize; y < pondSize; y++) {
                    for (int z = (int) (-pondSize * 1.5); z < pondSize * 1.5+1; z++) {
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
                world.setBlockState(lootChestPosition, Blocks.CHEST.getDefaultState().with(ChestBlock.WATERLOGGED, true), 3);
                BlockEntity chestBlockEntity = world.getBlockEntity(lootChestPosition);
                if (chestBlockEntity instanceof ChestBlockEntity) {
                    ((ChestBlockEntity) chestBlockEntity).setLootTable(this.lootTable, random.nextLong());
                }
            }
        }
    }
}
