package de.dafuqs.starrysky.dimension.decorators;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.dimension.DecorationMode;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.dimension.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.Random;


public class BottomBlockStackDecorator extends SpheroidDecorator {

    private final BlockState BLOCK_STATE;
    private BlockState FIRST_BLOCK_STATE;
    private BlockState LAST_BLOCK_STATE;
    private final float chance;
    private final int MIN_HEIGHT;
    private final int MAX_HEIGHT;

    /**
     * A chance of 0 = 0%, 100 = 100%
     */
    public BottomBlockStackDecorator(BlockState blockState, float chance, int minHeight, int maxHeight) {
        BLOCK_STATE = blockState;
        this.chance = chance;
        MIN_HEIGHT = minHeight;
        MAX_HEIGHT = maxHeight;
    }

    public BottomBlockStackDecorator setFirstBlockState(BlockState blockState) {
        this.FIRST_BLOCK_STATE = blockState;
        return this;
    }

    public BottomBlockStackDecorator setLastBlockState(BlockState blockState) {
        this.LAST_BLOCK_STATE = blockState;
        return this;
    }

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, BlockPos origin, Random random) {
        for(BlockPos bp : getDecorationPositionsInChunk(spheroid, world, origin, random, chance, DecorationMode.BOTTOM)) {
            int thisHeight = Support.getRandomBetween(random, MIN_HEIGHT, MAX_HEIGHT);
            for (int i = 0; i < thisHeight; i++) {
                if (world.getBlockState(bp.down()).isAir()) {
                    BlockState placementBlockState = BLOCK_STATE;
                    if(i == 1 && FIRST_BLOCK_STATE != null) {
                        placementBlockState = FIRST_BLOCK_STATE;
                    } else if(i == thisHeight -1 && LAST_BLOCK_STATE != null) {
                        placementBlockState = LAST_BLOCK_STATE;
                    }

                    world.setBlockState(bp, placementBlockState, 3);
                } else {
                    if(i > 0 && LAST_BLOCK_STATE != null) {
                        world.setBlockState(bp, LAST_BLOCK_STATE, 3);
                    }
                    break;
                }
            }
        }
    }

}
