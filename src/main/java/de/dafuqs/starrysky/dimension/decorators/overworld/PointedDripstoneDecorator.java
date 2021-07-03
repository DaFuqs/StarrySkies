package de.dafuqs.starrysky.dimension.decorators.overworld;

import de.dafuqs.starrysky.dimension.DecorationMode;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.dimension.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.block.enums.Thickness;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;

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
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, BlockPos origin, Random random) {
        int height = random.nextInt(6);
        switch (this.mode) {
            case UP -> {
                for(BlockPos bp : getDecorationPositionsInChunk(spheroid, world, origin, random, chance, DecorationMode.TOP)) {
                    generatePointedDripstone(world, bp, Direction.UP, height, false);
                }
            }
            case DOWN -> {
                for(BlockPos bp : getDecorationPositionsInChunk(spheroid, world, origin, random, chance, DecorationMode.BOTTOM)) {
                    generatePointedDripstone(world, bp, Direction.DOWN, height, false);
                }
            }
            case CAVE -> {
                for(BlockPos bp : getDecorationPositionsInChunk(spheroid, world, origin, random, chance, DecorationMode.CAVE_BOTTOM)) {
                    generatePointedDripstone(world, bp, Direction.UP, height, false);
                }
                for(BlockPos bp : getDecorationPositionsInChunk(spheroid, world, origin, random, chance, DecorationMode.CAVE_TOP)) {
                    generatePointedDripstone(world, bp, Direction.DOWN, height, false);
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
