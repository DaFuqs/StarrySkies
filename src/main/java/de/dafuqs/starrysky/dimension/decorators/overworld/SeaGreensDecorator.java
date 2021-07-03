package de.dafuqs.starrysky.dimension.decorators.overworld;

import de.dafuqs.starrysky.dimension.DecorationMode;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.dimension.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallSeagrassBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.Random;


public class SeaGreensDecorator extends SpheroidDecorator {

    // those are all always "waterlogged"
    private static final BlockState KELP = Blocks.KELP.getDefaultState(); // the top
    private static final BlockState KELP_PLANT = Blocks.KELP_PLANT.getDefaultState(); // the middle
    private static final BlockState SEAGRASS_BLOCK_STATE = Blocks.SEAGRASS.getDefaultState();
    private static final BlockState TALL_SEAGRASS_UPPER = Blocks.TALL_SEAGRASS.getDefaultState().with(TallSeagrassBlock.HALF, DoubleBlockHalf.UPPER);
    private static final BlockState TALL_SEAGRASS_LOWER = Blocks.TALL_SEAGRASS.getDefaultState().with(TallSeagrassBlock.HALF, DoubleBlockHalf.LOWER);

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, BlockPos origin, Random random) {
        for(BlockPos bp : getDecorationPositionsInChunk(spheroid, world, origin, random, 0.75F, DecorationMode.CAVE_BOTTOM)) {
            int r = random.nextInt(3);

            if (r == 0) {
                int kelpHeight = random.nextInt(8);
                for(int i = 0; i < kelpHeight; i++) {
                    if (world.getBlockState(bp.up(i)).getBlock() == Blocks.WATER) {
                        if (world.getBlockState(bp.up(i+1)).getBlock() == Blocks.WATER && i < kelpHeight - 1) {
                            world.setBlockState(bp.up(i), KELP_PLANT, 3); // middle parts
                        } else {
                            world.setBlockState(bp.up(i), KELP, 3); // the top
                        }
                    }
                }
            } else if (r == 1) {
                if (world.getBlockState(bp).getBlock() == Blocks.WATER) {
                    world.setBlockState(bp, SEAGRASS_BLOCK_STATE, 3);
                }
            } else {
                if (world.getBlockState(bp.up()).getBlock() == Blocks.WATER) {
                    world.setBlockState(bp.up(), TALL_SEAGRASS_UPPER, 3);
                    world.setBlockState(bp, TALL_SEAGRASS_LOWER, 3);
                } else {
                    if (world.getBlockState(bp).getBlock() == Blocks.WATER) {
                        world.setBlockState(bp, SEAGRASS_BLOCK_STATE, 3);
                    }
                }
            }
        }
    }
}
