package de.dafuqs.starrysky.dimension.decorators;

import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.ArrayList;
import java.util.Random;


public class PlantDecorator extends SpheroidDecorator {

    private final BlockState PLANT_BLOCKSTATE;
    private final float PLANT_CHANCE;

    /**
     * A chance of 0 = 0%, 100 = 100%
     */
    public PlantDecorator(BlockState plant_blockState, float plant_chance) {
        PLANT_BLOCKSTATE = plant_blockState;
        PLANT_CHANCE = plant_chance;
    }

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, ArrayList<BlockPos> decorationBlockPositions, Random random) {
        for(BlockPos bp : decorationBlockPositions) {
            if (world.getBlockState(bp.up()).isAir()) {
                if(random.nextFloat() < PLANT_CHANCE) {
                    world.setBlockState(bp.up(), PLANT_BLOCKSTATE, 3);
                }
            }
        }
    }
}
