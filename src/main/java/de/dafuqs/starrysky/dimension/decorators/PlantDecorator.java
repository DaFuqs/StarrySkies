package de.dafuqs.starrysky.dimension.decorators;

import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.ArrayList;
import java.util.Random;


public class PlantDecorator extends SpheroidDecorator {

    private final BlockState PLANT_BLOCK_STATE;
    private final float PLANT_CHANCE;

    /**
     * A chance of 0 = 0%, 1.0 = 100%
     */
    public PlantDecorator(BlockState plantBlockState, float plantChance) {
        PLANT_BLOCK_STATE = plantBlockState;
        PLANT_CHANCE = plantChance;
    }

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, Random random) {
        for(BlockPos bp : decorationBlockPositions) {
            if (!world.getBlockState(bp).isAir() && world.getBlockState(bp.up()).isAir()) {
                if(random.nextFloat() < PLANT_CHANCE) {
                    world.setBlockState(bp.up(), PLANT_BLOCK_STATE, 3);
                }
            }
        }
    }
}
