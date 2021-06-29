package de.dafuqs.starrysky.dimension.decorators;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.Random;


public class CenterPondDecorator extends SpheroidDecorator {

    private final Identifier lootTable;
    private final float lootTableChance;
    private final BlockState beachBlockState;
    private final BlockState liquidBlockState;

    public CenterPondDecorator(BlockState beachBlockState, BlockState liquidBlockState, Identifier lootTable, float lootTableChance) {
        this.beachBlockState = beachBlockState;
        this.liquidBlockState = liquidBlockState;
        this.lootTable = lootTable;
        this.lootTableChance = lootTableChance;
    }

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, Random random) {
        // doesn't make sense on small spheroids
        if(spheroid.getRadius() > 9) {
            int pondRadius = (int) (spheroid.getRadius() / 2.5);
            BlockPos spheroidTop = spheroid.getPosition().up(spheroid.getRadius());

            int waterLevelY = spheroidTop.getY();
            boolean waterLevelSet = false;

            for (int x = -pondRadius - 1; x <= pondRadius; x++) {
                for (int y = -pondRadius; y < 1; y++) {
                    for (int z = -pondRadius -1; z <= pondRadius; z++) {
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

            // if there is not enough room for water: just cancel
            // not nice, but eh
            if(waterLevelY - spheroid.getPosition().getY() < pondRadius * 1.5) {
                return;
            }

            boolean hasLootChest = random.nextFloat() < this.lootTableChance;
            BlockPos lootChestPosition = null;

            int pond15 = (int) Math.round(pondRadius * 1.5);
            for (int x = -pond15; x <= pond15; x++) {
                for (int y = -pondRadius; y < pondRadius; y++) {
                    for (int z = -pond15; z <= pond15; z++) {
                        BlockPos currentBlockPos = spheroidTop.add(x, y, z);

                        BlockState blockState = null;
                        if (currentBlockPos.getY() > waterLevelY) {
                            blockState = Blocks.AIR.getDefaultState();
                        } else {
                            double distance = Support.getDistance(currentBlockPos, spheroidTop);
                            double pondDistance = distance / pondRadius;
                            if (pondDistance < 1.1) {
                                if(hasLootChest && x == 0 && z == 0 && lootChestPosition == null) {
                                    lootChestPosition = currentBlockPos;
                                }
                                blockState = liquidBlockState;
                            } else if (pondDistance < 1.70) {
                                blockState = beachBlockState;
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
