package de.dafuqs.starrysky.dimension.decorators;

import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.ArrayList;
import java.util.Random;


public class DoublePlantDecorator extends SpheroidDecorator {

    private final BlockState PLANT_BLOCKSTATE;
    private final float PLANT_CHANCE;

    /**
     * A chance of 0 = 0%, 1 = 100%
     */
    public DoublePlantDecorator(BlockState plant_blockstate, float plant_chance) {
        PLANT_BLOCKSTATE = plant_blockstate;
        PLANT_CHANCE = plant_chance;
    }

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, ArrayList<BlockPos> decorationBlockPositions, Random random) {
        for(BlockPos bp : decorationBlockPositions) {
            if (!world.getBlockState(bp).isAir() && world.getBlockState(bp.up()).isAir() && world.getBlockState(bp.up(2)).isAir()) {
                if (random.nextFloat() < PLANT_CHANCE) {
                    world.setBlockState(bp.up(), PLANT_BLOCKSTATE.with(TallFlowerBlock.HALF, DoubleBlockHalf.LOWER), 3);
                    world.setBlockState(bp.up(2), PLANT_BLOCKSTATE.with(TallFlowerBlock.HALF, DoubleBlockHalf.UPPER), 3);
                }
            }
        }
    }
}
