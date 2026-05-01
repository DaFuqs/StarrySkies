package de.dafuqs.starryskies.portal;

import de.dafuqs.starryskies.configs.StarrySkyConfig;
import de.dafuqs.starryskies.registries.StarryBlocks;
import de.dafuqs.starryskies.registries.StarryPoiTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.BlockUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.Comparator;
import java.util.Optional;

public class StarryPortalForcer {

    private static final int RADIUS = 16;
    private static final int FRAME_HEIGHT_START = -1;
    private static final int FRAME_HEIGHT_END = 4;
    private static final int FRAME_WIDTH_START = -1;
    private static final int FRAME_WIDTH_END = 3;

    public static Optional<BlockPos> findClosestPortalPosition(ServerLevel level, BlockPos approximateExitPos, WorldBorder worldBorder) {
        PoiManager poiManager = level.getPoiManager();
        poiManager.ensureLoadedAndValid(level, approximateExitPos, RADIUS);
        return poiManager.getInSquare(type -> type.is(StarryPoiTypes.STARRY_PORTAL), approximateExitPos, RADIUS, PoiManager.Occupancy.ANY)
                .map(PoiRecord::getPos)
                .filter(worldBorder::isWithinBounds)
                .filter(pos -> level.getBlockState(pos).hasProperty(BlockStateProperties.HORIZONTAL_AXIS))
                .min(Comparator.<BlockPos>comparingDouble(p -> p.distSqr(approximateExitPos)).thenComparingInt(Vec3i::getY));
    }

    public static Optional<BlockUtil.FoundRectangle> createPortal(ServerLevel level, BlockPos origin, Direction.Axis portalAxis) {
        Direction direction = Direction.get(Direction.AxisDirection.POSITIVE, portalAxis);
        double closestFullDistanceSqr = -1.0;
        BlockPos closestFullPosition = null;
        double closestPartialDistanceSqr = -1.0;
        BlockPos closestPartialPosition = null;
        WorldBorder worldBorder = level.getWorldBorder();
        int maxPlaceableY = Math.min(level.getMaxY(), level.getMinY() + level.getLogicalHeight() - 1);
        BlockPos.MutableBlockPos mutable = origin.mutable();

        for (BlockPos.MutableBlockPos columnPos : BlockPos.spiralAround(origin, RADIUS, Direction.EAST, Direction.SOUTH)) {
            int height = Math.min(maxPlaceableY, level.getHeight(Heightmap.Types.MOTION_BLOCKING, columnPos.getX(), columnPos.getZ()));
            if (worldBorder.isWithinBounds(columnPos) && worldBorder.isWithinBounds(columnPos.move(direction, 1))) {
                columnPos.move(direction.getOpposite(), 1);

                for (int y = height; y >= level.getMinY(); y--) {
                    columnPos.setY(y);
                    if (canPortalReplaceBlock(level, columnPos)) {
                        int firstEmptyY = y;

                        while (y > level.getMinY() && canPortalReplaceBlock(level, columnPos.move(Direction.DOWN))) {
                            y--;
                        }

                        if (y + 4 <= maxPlaceableY) {
                            int deltaY = firstEmptyY - y;
                            if (deltaY <= 0 || deltaY >= 3) {
                                columnPos.setY(y);
                                if (canHostFrame(level, columnPos, mutable, direction, 0)) {
                                    double distance = origin.distSqr(columnPos);
                                    if (canHostFrame(level, columnPos, mutable, direction, -1)
                                            && canHostFrame(level, columnPos, mutable, direction, 1)
                                            && (closestFullDistanceSqr == -1.0 || closestFullDistanceSqr > distance)) {
                                        closestFullDistanceSqr = distance;
                                        closestFullPosition = columnPos.immutable();
                                    }

                                    if (closestFullDistanceSqr == -1.0 && (closestPartialDistanceSqr == -1.0 || closestPartialDistanceSqr > distance)) {
                                        closestPartialDistanceSqr = distance;
                                        closestPartialPosition = columnPos.immutable();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (closestFullDistanceSqr == -1.0 && closestPartialDistanceSqr != -1.0) {
            closestFullPosition = closestPartialPosition;
            closestFullDistanceSqr = closestPartialDistanceSqr;
        }

        if (closestFullDistanceSqr == -1.0) {
            int minStartY = Math.max(level.getMinY() - -1, 70);
            int maxStartY = maxPlaceableY - 9;
            if (maxStartY < minStartY) {
                return Optional.empty();
            }

            closestFullPosition = new BlockPos(origin.getX() - direction.getStepX(), Mth.clamp(origin.getY(), minStartY, maxStartY), origin.getZ() - direction.getStepZ()).immutable();
            closestFullPosition = worldBorder.clampToBounds(closestFullPosition);
            Direction clockWise = direction.getClockWise();

            for (int box = -1; box < 2; box++) {
                for (int width = 0; width < 2; width++) {
                    for (int height = -1; height < 3; height++) {
                        BlockState blockState = height < 0 ? StarrySkyConfig.CONFIG.getPortalFrameBlock().defaultBlockState() : Blocks.AIR.defaultBlockState();
                        mutable.setWithOffset(closestFullPosition, width * direction.getStepX() + box * clockWise.getStepX(), height, width * direction.getStepZ() + box * clockWise.getStepZ());
                        level.setBlockAndUpdate(mutable, blockState);
                    }
                }
            }
        }

        for (int width = -1; width < 3; width++) {
            for (int height = -1; height < 4; height++) {
                if (width == -1 || width == 2 || height == -1 || height == 3) {
                    mutable.setWithOffset(closestFullPosition, width * direction.getStepX(), height, width * direction.getStepZ());
                    level.setBlock(mutable, StarrySkyConfig.CONFIG.getPortalFrameBlock().defaultBlockState(), 3);
                }
            }
        }

        BlockState portalBlockState = StarryBlocks.STARRY_PORTAL.get().defaultBlockState().setValue(NetherPortalBlock.AXIS, portalAxis);

        for (int width = 0; width < 2; width++) {
            for (int heightx = 0; heightx < 3; heightx++) {
                mutable.setWithOffset(closestFullPosition, width * direction.getStepX(), heightx, width * direction.getStepZ());
                level.setBlock(mutable, portalBlockState, 18);
            }
        }

        return Optional.of(new BlockUtil.FoundRectangle(closestFullPosition.immutable(), 2, 3));
    }

    private static boolean canPortalReplaceBlock(ServerLevel level, BlockPos.MutableBlockPos pos) {
        BlockState blockState = level.getBlockState(pos);
        return blockState.canBeReplaced() && blockState.getFluidState().isEmpty();
    }

    private static boolean canHostFrame(ServerLevel level, BlockPos origin, BlockPos.MutableBlockPos mutable, Direction direction, int offset) {
        Direction clockWise = direction.getClockWise();

        for (int width = FRAME_WIDTH_START; width < FRAME_WIDTH_END; width++) {
            for (int height = FRAME_HEIGHT_START; height < FRAME_HEIGHT_END; height++) {
                mutable.setWithOffset(origin, direction.getStepX() * width + clockWise.getStepX() * offset, height, direction.getStepZ() * width + clockWise.getStepZ() * offset);
                if (height < 0 && !level.getBlockState(mutable).isSolid()) {
                    return false;
                }

                if (height >= 0 && !canPortalReplaceBlock(level, mutable)) {
                    return false;
                }
            }
        }

        return true;
    }
}
