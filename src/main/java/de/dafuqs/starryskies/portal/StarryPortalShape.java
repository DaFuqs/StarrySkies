package de.dafuqs.starryskies.portal;

import de.dafuqs.starryskies.configs.StarrySkyConfig;
import de.dafuqs.starryskies.registries.StarryBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.BlockUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.function.Predicate;

public class StarryPortalShape {
    private static final int MIN_WIDTH = 2;
    public static final int MAX_WIDTH = 21;
    private static final int MIN_HEIGHT = 3;
    public static final int MAX_HEIGHT = 21;
    private static final float SAFE_TRAVEL_MAX_ENTITY_XY = 4.0F;
    private static final double SAFE_TRAVEL_MAX_VERTICAL_DELTA = 1.0;
    private final Direction.Axis axis;
    private final Direction rightDir;
    private final int numPortalBlocks;
    private final BlockPos bottomLeft;
    private final int height;
    private final int width;

    private StarryPortalShape(Direction.Axis axis, int portalBlockCount, Direction rightDir, BlockPos bottomLeft, int width, int height) {
        this.axis = axis;
        this.numPortalBlocks = portalBlockCount;
        this.rightDir = rightDir;
        this.bottomLeft = bottomLeft;
        this.width = width;
        this.height = height;
    }

    public static Optional<StarryPortalShape> findEmptyPortalShape(LevelAccessor level, BlockPos pos, Direction.Axis preferredAxis) {
        return findPortalShape(level, pos, shape -> shape.isValid() && shape.numPortalBlocks == 0, preferredAxis);
    }

    public static Optional<StarryPortalShape> findPortalShape(LevelAccessor level, BlockPos pos, Predicate<StarryPortalShape> isValid, Direction.Axis preferredAxis) {
        Optional<StarryPortalShape> firstAxis = Optional.of(findAnyShape(level, pos, preferredAxis)).filter(isValid);
        if (firstAxis.isPresent()) {
            return firstAxis;
        } else {
            Direction.Axis otherAxis = preferredAxis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
            return Optional.of(findAnyShape(level, pos, otherAxis)).filter(isValid);
        }
    }

    public static StarryPortalShape findAnyShape(BlockGetter level, BlockPos pos, Direction.Axis axis) {
        Direction rightDir = axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
        BlockPos bottomLeft = calculateBottomLeft(level, rightDir, pos);
        if (bottomLeft == null) {
            return new StarryPortalShape(axis, 0, rightDir, pos, 0, 0);
        } else {
            int width = calculateWidth(level, bottomLeft, rightDir);
            if (width == 0) {
                return new StarryPortalShape(axis, 0, rightDir, bottomLeft, 0, 0);
            } else {
                MutableInt portalBlockCountOutput = new MutableInt();
                int height = calculateHeight(level, bottomLeft, rightDir, width, portalBlockCountOutput);
                return new StarryPortalShape(axis, portalBlockCountOutput.intValue(), rightDir, bottomLeft, width, height);
            }
        }
    }

    private static @Nullable BlockPos calculateBottomLeft(BlockGetter level, Direction rightDir, BlockPos pos) {
        int minY = Math.max(level.getMinY(), pos.getY() - 21);

        while (pos.getY() > minY && isEmpty(level.getBlockState(pos.below()))) {
            pos = pos.below();
        }

        Direction leftDir = rightDir.getOpposite();
        int edge = getDistanceUntilEdgeAboveFrame(level, pos, leftDir) - 1;
        return edge < 0 ? null : pos.relative(leftDir, edge);
    }

    private static int calculateWidth(BlockGetter level, BlockPos bottomLeft, Direction rightDir) {
        int width = getDistanceUntilEdgeAboveFrame(level, bottomLeft, rightDir);
        return width >= 2 && width <= 21 ? width : 0;
    }

    private static int getDistanceUntilEdgeAboveFrame(BlockGetter level, BlockPos pos, Direction direction) {
        BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();

        for (int width = 0; width <= 21; width++) {
            blockPos.set(pos).move(direction, width);
            BlockState blockState = level.getBlockState(blockPos);
            if (!isEmpty(blockState)) {
                if (StarrySkyConfig.CONFIG.getPortalFrame().test(blockState, level, blockPos)) {
                    return width;
                }
                break;
            }

            BlockState belowState = level.getBlockState(blockPos.move(Direction.DOWN));
            if (!StarrySkyConfig.CONFIG.getPortalFrame().test(belowState, level, blockPos)) {
                break;
            }
        }

        return 0;
    }

    private static int calculateHeight(BlockGetter level, BlockPos bottomLeft, Direction rightDir, int width, MutableInt portalBlockCount) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        int height = getDistanceUntilTop(level, bottomLeft, rightDir, pos, width, portalBlockCount);
        return height >= 3 && height <= 21 && hasTopFrame(level, bottomLeft, rightDir, pos, width, height) ? height : 0;
    }

    private static boolean hasTopFrame(BlockGetter level, BlockPos bottomLeft, Direction rightDir, BlockPos.MutableBlockPos pos, int width, int height) {
        for (int i = 0; i < width; i++) {
            BlockPos.MutableBlockPos framePos = pos.set(bottomLeft).move(Direction.UP, height).move(rightDir, i);
            if (!StarrySkyConfig.CONFIG.getPortalFrame().test(level.getBlockState(framePos), level, framePos)) {
                return false;
            }
        }

        return true;
    }

    private static int getDistanceUntilTop(
            BlockGetter level, BlockPos bottomLeft, Direction rightDir, BlockPos.MutableBlockPos pos, int width, MutableInt portalBlockCount
    ) {
        for (int height = 0; height < 21; height++) {
            pos.set(bottomLeft).move(Direction.UP, height).move(rightDir, -1);
            if (!StarrySkyConfig.CONFIG.getPortalFrame().test(level.getBlockState(pos), level, pos)) {
                return height;
            }

            pos.set(bottomLeft).move(Direction.UP, height).move(rightDir, width);
            if (!StarrySkyConfig.CONFIG.getPortalFrame().test(level.getBlockState(pos), level, pos)) {
                return height;
            }

            for (int i = 0; i < width; i++) {
                pos.set(bottomLeft).move(Direction.UP, height).move(rightDir, i);
                BlockState state = level.getBlockState(pos);
                if (!isEmpty(state)) {
                    return height;
                }

                if (state.is(StarryBlocks.STARRY_PORTAL.get())) {
                    portalBlockCount.increment();
                }
            }
        }

        return 21;
    }

    private static boolean isEmpty(BlockState state) {
        return state.isAir() || state.is(BlockTags.FIRE) || state.is(StarryBlocks.STARRY_PORTAL.get());
    }

    public boolean isValid() {
        return this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
    }

    public void createPortalBlocks(LevelAccessor level) {
        BlockState portalState = StarryBlocks.STARRY_PORTAL.get().defaultBlockState().setValue(StarryPortalBlock.AXIS, this.axis);
        BlockPos.betweenClosed(this.bottomLeft, this.bottomLeft.relative(Direction.UP, this.height - 1).relative(this.rightDir, this.width - 1))
                .forEach(pos -> level.setBlock(pos, portalState, 18));
    }

    public boolean isComplete() {
        return this.isValid() && this.numPortalBlocks == this.width * this.height;
    }

    public static Vec3 getRelativePosition(BlockUtil.FoundRectangle largestRectangleAround, Direction.Axis axis, Vec3 position, EntityDimensions dimensions) {
        double width = (double) largestRectangleAround.axis1Size - dimensions.width();
        double height = (double) largestRectangleAround.axis2Size - dimensions.height();
        BlockPos bottomMin = largestRectangleAround.minCorner;
        double relativeRight;
        if (width > 0.0) {
            double bottomStart = bottomMin.get(axis) + dimensions.width() / 2.0;
            relativeRight = Mth.clamp(Mth.inverseLerp(position.get(axis) - bottomStart, 0.0, width), 0.0, 1.0);
        } else {
            relativeRight = 0.5;
        }

        double relativeUp;
        if (height > 0.0) {
            Direction.Axis heightAxis = Direction.Axis.Y;
            relativeUp = Mth.clamp(Mth.inverseLerp(position.get(heightAxis) - bottomMin.get(heightAxis), 0.0, height), 0.0, 1.0);
        } else {
            relativeUp = 0.0;
        }

        Direction.Axis forwardAxis = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
        double relativeForward = position.get(forwardAxis) - (bottomMin.get(forwardAxis) + 0.5);
        return new Vec3(relativeRight, relativeUp, relativeForward);
    }

    public static Vec3 findCollisionFreePosition(Vec3 bottomCenter, ServerLevel serverLevel, Entity entity, EntityDimensions dimensions) {
        if (!(dimensions.width() > 4.0F) && !(dimensions.height() > 4.0F)) {
            double halfHeight = dimensions.height() / 2.0;
            Vec3 center = bottomCenter.add(0.0, halfHeight, 0.0);
            VoxelShape allowedCenters = Shapes.create(
                    AABB.ofSize(center, dimensions.width(), 0.0, dimensions.width()).expandTowards(0.0, 1.0, 0.0).inflate(1.0E-6)
            );
            Optional<Vec3> collisionFreePosition = serverLevel.findFreePosition(
                    entity, allowedCenters, center, dimensions.width(), dimensions.height(), dimensions.width()
            );
            Optional<Vec3> collisionFreeBottomCenter = collisionFreePosition.map(vec -> vec.subtract(0.0, halfHeight, 0.0));
            return collisionFreeBottomCenter.orElse(bottomCenter);
        } else {
            return bottomCenter;
        }
    }
}
