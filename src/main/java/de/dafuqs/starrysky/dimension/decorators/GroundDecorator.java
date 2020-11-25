package de.dafuqs.starrysky.dimension.decorators;

import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.ArrayList;
import java.util.Random;


public class GroundDecorator extends SpheroidDecorator {

    private final BlockState GROUND_BLOCKSTATE;
    private final float GROUND_CHANCE;

    /**
     * Replaces the block in the ground
     * A chance of 0 = 0%, 100 = 100%
     */
    public GroundDecorator(BlockState ground_blockstate, float ground_chance) {
        GROUND_BLOCKSTATE = ground_blockstate;
        GROUND_CHANCE = ground_chance;
    }

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, ArrayList<BlockPos> decorationBlockPositions, Random random) {
        for(BlockPos bp : decorationBlockPositions) {
            if(random.nextFloat() < GROUND_CHANCE) {
                world.setBlockState(bp, GROUND_BLOCKSTATE, 3);
            }
        }
    }
}
