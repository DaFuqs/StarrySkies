package de.dafuqs.starrysky.spheroid.decorators;

import de.dafuqs.starrysky.spheroid.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;

import java.util.ArrayList;

public class PointedDripstoneDecorator extends SpheroidDecorator {

    private final float chance;
    private final PointedDripstoneDecoratorMode mode;

    public enum PointedDripstoneDecoratorMode {
        UP,
        DOWN,
        CAVE
    }

    public PointedDripstoneDecorator(float chance, PointedDripstoneDecoratorMode mode) {
        this.chance = chance;
        this.mode = mode;
    }

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, ArrayList<BlockPos> decorationBlockPositions, Random random) {
        for(BlockPos bp : decorationBlockPositions) {
            if(random.nextFloat() < chance) {
                int height = random.nextInt(5);
                switch (this.mode) {
                    case UP -> placeDripstoneBlocks(world, bp, height, Direction.UP);
                    case DOWN -> placeDripstoneBlocks(world, bp, height, Direction.DOWN);
                    case CAVE -> {
                        if(random.nextBoolean()) {
                            placeDripstoneBlocks(world, bp, height, Direction.UP);
                        } else {
                            for(int i = 0; i < spheroid.getRadius(); ++i) {
                                if (!world.isAir(bp)) {
                                    placeDripstoneBlocks(world, bp, height, Direction.UP);
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    private void placeDripstoneBlocks(StructureWorldAccess world, BlockPos pos, int height, Direction direction) {
        BlockPos.Mutable mutable = pos.mutableCopy();
        for(int i = 0; i < height; ++i) {
            if (!generateDripstoneBlock(world, mutable)) {
                return;
            }
            mutable.move(direction);
        }
    }

    protected static boolean generateDripstoneBlock(StructureWorldAccess world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        if (blockState.isIn(BlockTags.DRIPSTONE_REPLACEABLE_BLOCKS)) {
            world.setBlockState(pos, Blocks.DRIPSTONE_BLOCK.getDefaultState(), 2);
            return true;
        } else {
            return false;
        }
    }

}
