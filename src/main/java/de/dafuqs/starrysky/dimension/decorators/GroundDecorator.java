package de.dafuqs.starrysky.dimension.decorators;

import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.ArrayList;
import java.util.Random;


public class GroundDecorator extends SpheroidDecorator {

    private final BlockState GROUND_BLOCK_STATE;
    private final float GROUND_CHANCE;

    /**
     * Replaces the block in the ground
     * A chance of 0 = 0%, 100 = 100%
     */
    public GroundDecorator(BlockState groundBlockState, float ground_chance) {
        GROUND_BLOCK_STATE = groundBlockState;
        GROUND_CHANCE = ground_chance;
    }

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, ArrayList<BlockPos> decorationBlockPositions, Random random) {
        for(BlockPos bp : decorationBlockPositions) {
            if(random.nextFloat() < GROUND_CHANCE) {
                world.setBlockState(bp, GROUND_BLOCK_STATE, 3);
            }
        }
    }
}
