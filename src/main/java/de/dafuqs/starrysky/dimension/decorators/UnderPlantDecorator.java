package de.dafuqs.starrysky.dimension.decorators;

import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.ArrayList;
import java.util.Random;


public class UnderPlantDecorator extends SpheroidDecorator {

    private final BlockState PLANT_BLOCK_STATE;
    private final float PLANT_CHANCE;

    /**
     * A chance of 0 = 0%, 100 = 100%
     */
    public UnderPlantDecorator(BlockState plantBlockState, float plant_chance) {
        PLANT_BLOCK_STATE = plantBlockState;
        PLANT_CHANCE = plant_chance;
    }

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, Random random) {
        int spheroidY = spheroid.getPosition().getY();
        for(BlockPos bp : decorationBlockPositions) {
             BlockPos flippedBlockPos = bp.down((bp.getY() - spheroidY) * 2);

            if (world.getBlockState(flippedBlockPos.down()).isAir()) {
                if(random.nextFloat() < PLANT_CHANCE) {
                    world.setBlockState(flippedBlockPos.down(), PLANT_BLOCK_STATE, 3);
                }
            }
        }
    }
}
