package de.dafuqs.starrysky.dimension.decorators;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.dimension.DecorationMode;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.dimension.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.Random;


public class BlockStackDecorator extends SpheroidDecorator {

    private final BlockState MAIN_BLOCK_STATE;
    private BlockState FIRST_BLOCK_STATE;
    private BlockState LAST_BLOCK_STATE;
    private final float CHANCE;
    private final int MIN_HEIGHT;
    private final int MAX_HEIGHT;
    private boolean REQUIRES_AIR;

    /**
     * A chance of 0 = 0%, 100 = 100%
     */
    public BlockStackDecorator(BlockState plantBlockState, float plantChance, int minHeight, int maxHeight) {
        MAIN_BLOCK_STATE = plantBlockState;
        CHANCE = plantChance;
        MIN_HEIGHT = minHeight;
        MAX_HEIGHT = maxHeight;
        REQUIRES_AIR = false;
    }

    public BlockStackDecorator setFirstBlockState(BlockState blockState) {
        this.FIRST_BLOCK_STATE = blockState;
        return this;
    }

    public BlockStackDecorator setLastBlockState(BlockState blockState) {
        this.LAST_BLOCK_STATE = blockState;
        return this;
    }

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, BlockPos origin, Random random) {
        for(BlockPos bp : getDecorationPositionsInChunk(spheroid, world, origin, random, CHANCE, DecorationMode.TOP)) {
            int thisHeight = Support.getRandomBetween(random, MIN_HEIGHT, MAX_HEIGHT);
            for (int i = 0; i < thisHeight; i++) {
                if (world.getBlockState(bp.up(i)).isAir()) {
                    if(!REQUIRES_AIR || surroundedByAir(world, bp)) {
                        BlockState placementBlockState;
                        if (i == 0 && FIRST_BLOCK_STATE != null) {
                            placementBlockState = FIRST_BLOCK_STATE;
                        } else if (i == thisHeight - 1 && LAST_BLOCK_STATE != null) {
                            placementBlockState = LAST_BLOCK_STATE;
                        } else {
                            placementBlockState = MAIN_BLOCK_STATE;
                        }

                        world.setBlockState(bp, placementBlockState, 3);
                    }
                } else {
                    break;
                }
            }
        }
    }

    public BlockStackDecorator requiresAirAroundOfIt() {
        this.REQUIRES_AIR = true;
        return this;
    }


}
