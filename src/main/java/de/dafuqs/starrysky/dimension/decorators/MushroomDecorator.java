package de.dafuqs.starrysky.dimension.decorators;

import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.ArrayList;
import java.util.Random;


public class MushroomDecorator extends SpheroidDecorator {

    private static final BlockState BROWN_MUSHROOM_BLOCK_STATE = Blocks.BROWN_MUSHROOM.getDefaultState();
    private static final BlockState RED_MUSHROOM_BLOCK_STATE   = Blocks.RED_MUSHROOM.getDefaultState();
    private static final int MUSHROOM_CHANCE = 50;

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, Random random) {
        for(BlockPos bp : decorationBlockPositions) {
            if(world.getBlockState(bp.up()).isAir()) {
                int r = random.nextInt(MUSHROOM_CHANCE);

                if (r == 0) {
                    world.setBlockState(bp.up(), RED_MUSHROOM_BLOCK_STATE, 3);
                } else if (r < 3) {
                    world.setBlockState(bp.up(), BROWN_MUSHROOM_BLOCK_STATE, 3);
                }
            }
        }
    }
}
