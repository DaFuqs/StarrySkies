package de.dafuqs.starrysky.spheroid.decorators;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.spheroid.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.ArrayList;
import java.util.Random;


public class HugePlantDecorator extends SpheroidDecorator {

    private final BlockState PLANT_BLOCKSTATE;
    private BlockState FIRST_BLOCKSTATE;
    private BlockState LAST_BLOCKSTATE;
    private final float PLANT_CHANCE;
    private final int MIN_HEIGHT;
    private final int MAX_HEIGHT;

    /**
     * A chance of 0 = 0%, 100 = 100%
     */
    public HugePlantDecorator(BlockState plant_blockState, float plant_chance, int minHeight, int maxHeight) {
        PLANT_BLOCKSTATE = plant_blockState;
        PLANT_CHANCE = plant_chance;
        MIN_HEIGHT = minHeight;
        MAX_HEIGHT = maxHeight;
    }

    public HugePlantDecorator setFirstBlockState(BlockState blockState) {
        this.FIRST_BLOCKSTATE = blockState;
        return this;
    }

    public HugePlantDecorator setLastBlockState(BlockState blockState) {
        this.LAST_BLOCKSTATE = blockState;
        return this;
    }

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, ArrayList<BlockPos> decorationBlockPositions, Random random) {
        for(BlockPos bp : decorationBlockPositions) {

            if (random.nextFloat() < PLANT_CHANCE) {
                int thisHeight = Support.getRandomBetween(random, MIN_HEIGHT, MAX_HEIGHT);
                for (int i = 1; i < thisHeight + 1; i++) {
                    if (world.getBlockState(bp.up(i)).isAir()) {

                        BlockState placementBlockState = PLANT_BLOCKSTATE;
                        if(i == 1 && FIRST_BLOCKSTATE != null) {
                            placementBlockState = FIRST_BLOCKSTATE;
                        } else if(i == thisHeight && LAST_BLOCKSTATE != null) {
                            placementBlockState = LAST_BLOCKSTATE;
                        }

                        world.setBlockState(bp.up(), placementBlockState, 3);
                    } else {
                        break;
                    }
                }
            }
        }
    }
}
