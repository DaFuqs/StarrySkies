package de.dafuqs.starrysky.dimension.decorators;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.ArrayList;
import java.util.Random;


public class HugeUnderPlantDecorator extends SpheroidDecorator {

    private final BlockState PLANT_BLOCK_STATE;
    private BlockState FIRST_BLOCK_STATE;
    private BlockState LAST_BLOCK_STATE;
    private final float PLANT_CHANCE;
    private final int MIN_HEIGHT;
    private final int MAX_HEIGHT;

    /**
     * A chance of 0 = 0%, 100 = 100%
     */
    public HugeUnderPlantDecorator(BlockState plantBlockState, float plantChance, int minHeight, int maxHeight) {
        PLANT_BLOCK_STATE = plantBlockState;
        PLANT_CHANCE = plantChance;
        MIN_HEIGHT = minHeight;
        MAX_HEIGHT = maxHeight;
    }

    public HugeUnderPlantDecorator setFirstBlockState(BlockState blockState) {
        this.FIRST_BLOCK_STATE = blockState;
        return this;
    }

    public HugeUnderPlantDecorator setLastBlockState(BlockState blockState) {
        this.LAST_BLOCK_STATE = blockState;
        return this;
    }

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, Random random) {

        int spheroidY = spheroid.getPosition().getY();
        for(BlockPos bp : decorationBlockPositions) {
            if (random.nextFloat() < PLANT_CHANCE) {
                BlockPos flippedBlockPos = bp.down((bp.getY() - spheroidY) * 2);

                int thisHeight = Support.getRandomBetween(random, MIN_HEIGHT, MAX_HEIGHT);
                for (int i = 1; i < thisHeight + 1; i++) {
                    if (world.getBlockState(flippedBlockPos.down(i)).isAir()) {

                        BlockState placementBlockState = PLANT_BLOCK_STATE;
                        if(i == 1 && FIRST_BLOCK_STATE != null) {
                            placementBlockState = FIRST_BLOCK_STATE;
                        } else if(i == thisHeight && LAST_BLOCK_STATE != null) {
                            placementBlockState = LAST_BLOCK_STATE;
                        }

                        world.setBlockState(flippedBlockPos.down(i), placementBlockState, 3);
                    } else {
                        if(i > 1 && LAST_BLOCK_STATE != null) {
                            world.setBlockState(flippedBlockPos.down(i-1), LAST_BLOCK_STATE, 3);
                        }
                        break;
                    }
                }
            }
        }
    }

}
