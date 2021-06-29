package de.dafuqs.starrysky.dimension.decorators.overworld;

import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.block.enums.Thickness;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;

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
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, Random random) {
        for(BlockPos bp : decorationBlockPositions) {
            if(random.nextFloat() < chance) {
                int height = random.nextInt(6);
                switch (this.mode) {
                    case UP -> {
                        generatePointedDripstone(world, bp.up(), Direction.UP, height, false);
                    }
                    case DOWN -> {
                        generatePointedDripstone(world, bp.down(), Direction.DOWN, height, false);
                    }
                    case CAVE -> {
                        if(random.nextBoolean()) {
                            generatePointedDripstone(world, bp.up(), Direction.UP, height, false);
                        } else {
                            int maxY = spheroid.getPosition().getY() + spheroid.getRadius();
                            for (int i = bp.getY(); i < maxY; ++i) {
                                BlockPos currentBlockPos = new BlockPos(bp.getX(), i, bp.getZ());
                                if (world.isAir(currentBlockPos) && !world.isAir(currentBlockPos.up())) {
                                    generatePointedDripstone(world, currentBlockPos, Direction.DOWN, height, false);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    protected static void generatePointedDripstone(StructureWorldAccess world, BlockPos pos, Direction direction, int height, boolean merge) {
        BlockPos.Mutable mutable = pos.mutableCopy();
        getDripstoneThickness(direction, height, merge, (state) -> {
            if (state.isOf(Blocks.POINTED_DRIPSTONE)) {
                state = state.with(PointedDripstoneBlock.WATERLOGGED, world.isWater(mutable));
            }

            world.setBlockState(mutable, state, 2);
            mutable.move(direction);
        });
    }

    protected static void getDripstoneThickness(Direction direction, int height, boolean merge, Consumer<BlockState> callback) {
        if (height >= 3) {
            callback.accept(getState(direction, Thickness.BASE));

            for(int i = 0; i < height - 3; ++i) {
                callback.accept(getState(direction, Thickness.MIDDLE));
            }
        }

        if (height >= 2) {
            callback.accept(getState(direction, Thickness.FRUSTUM));
        }

        if (height >= 1) {
            callback.accept(getState(direction, merge ? Thickness.TIP_MERGE : Thickness.TIP));
        }
    }

    private static BlockState getState(Direction direction, Thickness thickness) {
        return (Blocks.POINTED_DRIPSTONE.getDefaultState().with(PointedDripstoneBlock.VERTICAL_DIRECTION, direction)).with(PointedDripstoneBlock.THICKNESS, thickness);
    }

}
