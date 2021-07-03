package de.dafuqs.starrysky.dimension.decorators;

import de.dafuqs.starrysky.dimension.DecorationMode;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.dimension.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.Random;


public class BottomBlockDecorator extends SpheroidDecorator {

    private final BlockState BLOCK_STATE;
    private final float CHANCE;

    /**
     * A chance of 0 = 0%, 100 = 100%
     */
    public BottomBlockDecorator(BlockState blockState, float chance) {
        BLOCK_STATE = blockState;
        CHANCE = chance;
    }

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, BlockPos origin, Random random) {
        for(BlockPos bp : getDecorationPositionsInChunk(spheroid, world, origin, random, CHANCE, DecorationMode.BOTTOM)) {
            if (world.getBlockState(bp).isAir()) {
                world.setBlockState(bp, BLOCK_STATE, 3);
            }
        }
    }
}
