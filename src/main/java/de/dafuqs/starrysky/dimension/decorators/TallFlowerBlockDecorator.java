package de.dafuqs.starrysky.dimension.decorators;

import de.dafuqs.starrysky.dimension.DecorationMode;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.dimension.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.Random;


public class TallFlowerBlockDecorator extends SpheroidDecorator {

    private final BlockState BLOCK_STATE;
    private final float CHANCE;

    /**
     * A chance of 0 = 0%, 1 = 100%
     */
    public TallFlowerBlockDecorator(BlockState plantBlockState, float plantChance) {
        BLOCK_STATE = plantBlockState;
        CHANCE = plantChance;
    }

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, BlockPos origin, Random random) {
        for(BlockPos bp : getDecorationPositionsInChunk(spheroid, world, origin, random, CHANCE, DecorationMode.TOP)) {
            if (!world.getBlockState(bp.down()).isAir() && world.getBlockState(bp).isAir() && world.getBlockState(bp.up()).isAir()) {
                if (random.nextFloat() < CHANCE) {
                    world.setBlockState(bp, BLOCK_STATE.with(TallFlowerBlock.HALF, DoubleBlockHalf.LOWER), 3);
                    world.setBlockState(bp.up(), BLOCK_STATE.with(TallFlowerBlock.HALF, DoubleBlockHalf.UPPER), 3);
                }
            }
        }
    }
}
