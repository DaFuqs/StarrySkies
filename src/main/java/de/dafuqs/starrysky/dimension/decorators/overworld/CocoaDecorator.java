package de.dafuqs.starrysky.dimension.decorators.overworld;

import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.dimension.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CocoaBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;

import java.util.Random;


public class CocoaDecorator extends SpheroidDecorator {


    private static final BlockState GROWN_COCOA_BLOCK_STATE = Blocks.COCOA.getDefaultState().with(CocoaBlock.AGE, 2); // 2 = fully grown
    private static final BlockState AIR_BLOCK_STATE = Blocks.CAVE_AIR.getDefaultState();

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, BlockPos origin, Random random) {
        if(!spheroid.isCenterInChunkBlockPos(origin)) {
            return;
        }

        for(int x = -2; x < 3; x++) {
            for (int y = -2; y < 3; y++) {
                for (int z = -2; z < 3; z++) {
                    BlockPos bp = spheroid.getPosition().up(y).north(x).east(z);
                    if(y == 0 && ((Math.abs(x) == 2) && Math.abs(z) == 1 || (Math.abs(z) == 2 && Math.abs(x) == 1))) {
                        Direction direction;
                        if(x == 0) {
                            if(z < 0) {
                                direction = Direction.WEST;
                            } else {
                                direction = Direction.EAST;
                            }

                        } else {
                            if(x < 0) {
                                direction = Direction.SOUTH;
                            } else {
                                direction = Direction.NORTH;
                            }
                        }
                        world.setBlockState(bp, GROWN_COCOA_BLOCK_STATE.with(HorizontalFacingBlock.FACING, direction), 3);
                    } else {
                        if (Math.abs(y) != 2 || (Math.abs(x) != 2 && Math.abs(z) != 2)) {
                            world.setBlockState(bp, AIR_BLOCK_STATE, 3);
                        }
                    }
                }
            }
        }
    }
}
