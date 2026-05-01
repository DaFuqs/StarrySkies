package de.dafuqs.starryskies.portal;

import com.mojang.logging.*;
import com.mojang.serialization.*;
import de.dafuqs.starryskies.registries.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.border.*;
import net.minecraft.world.level.dimension.*;
import net.minecraft.world.level.portal.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;
import org.jspecify.annotations.*;
import org.slf4j.*;

import java.util.*;

public class StarryPortalBlock extends Block implements Portal {

    private static final Logger LOGGER = LogUtils.getLogger();
    public static final MapCodec<StarryPortalBlock> CODEC = simpleCodec(StarryPortalBlock::new);
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    private static final Map<Direction.Axis, VoxelShape> SHAPES = Shapes.rotateHorizontalAxis(Block.column(4.0, 16.0, 0.0, 16.0));

    @Override
    public MapCodec<StarryPortalBlock> codec() {
        return CODEC;
    }

    public StarryPortalBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.X));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(AXIS));
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {

    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        Direction.Axis updateAxis = directionToNeighbour.getAxis();
        Direction.Axis axis = state.getValue(AXIS);
        boolean wrongAxis = axis != updateAxis && updateAxis.isHorizontal();
        return !wrongAxis && !neighbourState.is(this) && !StarryPortalShape.findAnyShape(level, pos, axis).isComplete()
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise) {
        if (entity.canUsePortal(false)) {
            entity.setAsInsidePortal(this, pos);
        }
    }

    @Override
    public @Nullable TeleportTransition getPortalDestination(ServerLevel currentLevel, Entity entity, BlockPos portalEntryPos) {
        ResourceKey<Level> newDimension = currentLevel.dimension() == StarryDimensionKeys.OVERWORLD_KEY ? Level.OVERWORLD : StarryDimensionKeys.OVERWORLD_KEY;
        ServerLevel newLevel = currentLevel.getServer().getLevel(newDimension);
        if (newLevel == null) {
            return null;
        }

        WorldBorder newWorldBorder = newLevel.getWorldBorder();
        double teleportationScale = DimensionType.getTeleportationScale(currentLevel.dimensionType(), newLevel.dimensionType());
        BlockPos approximateExitPos = newWorldBorder.clampToBounds(entity.getX() * teleportationScale, entity.getY(), entity.getZ() * teleportationScale);
        return this.getExitPortal(newLevel, entity, portalEntryPos, approximateExitPos, newWorldBorder);
    }

    protected @Nullable TeleportTransition getExitPortal(ServerLevel newLevel, Entity entity, BlockPos portalEntryPos, BlockPos approximateExitPos, WorldBorder worldBorder) {
        Optional<BlockPos> exitPortalPos = StarryPortalForcer.findClosestPortalPosition(newLevel, approximateExitPos, worldBorder);
        BlockUtil.FoundRectangle exitPortal;
        TeleportTransition.PostTeleportTransition post;
        if (exitPortalPos.isPresent()) {
            BlockPos pos = exitPortalPos.get();
            BlockState portalState = newLevel.getBlockState(pos);
            exitPortal = BlockUtil.getLargestRectangleAround(pos, portalState.getValue(BlockStateProperties.HORIZONTAL_AXIS), 21, Direction.Axis.Y, 21, blockPos -> newLevel.getBlockState(blockPos) == portalState);
            post = TeleportTransition.PLAY_PORTAL_SOUND.then(e -> e.placePortalTicket(pos));
        } else {
            Direction.Axis sourcePortalAxis = entity.level().getBlockState(portalEntryPos).getOptionalValue(AXIS).orElse(Direction.Axis.X);
            Optional<BlockUtil.FoundRectangle> createdExit = StarryPortalForcer.createPortal(newLevel, approximateExitPos, sourcePortalAxis);
            if (createdExit.isEmpty()) {
                LOGGER.error("Unable to create a starry skies portal, likely target out of worldborder");
                return null;
            }

            exitPortal = createdExit.get();
            post = TeleportTransition.PLAY_PORTAL_SOUND.then(TeleportTransition.PLACE_PORTAL_TICKET);
        }

        return getDimensionTransitionFromExit(entity, portalEntryPos, exitPortal, newLevel, post);
    }

    private static TeleportTransition getDimensionTransitionFromExit(Entity entity, BlockPos portalEntryPos, BlockUtil.FoundRectangle exitPortal, ServerLevel newLevel, TeleportTransition.PostTeleportTransition postTeleportTransition) {
        BlockState blockState = entity.level().getBlockState(portalEntryPos);
        Direction.Axis axis;
        Vec3 offset;
        if (blockState.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
            axis = blockState.getValue(BlockStateProperties.HORIZONTAL_AXIS);
            BlockUtil.FoundRectangle portalArea = BlockUtil.getLargestRectangleAround(portalEntryPos, axis, 21, Direction.Axis.Y, 21, pos -> entity.level().getBlockState(pos) == blockState);
            offset = entity.getRelativePortalPosition(axis, portalArea);
        } else {
            axis = Direction.Axis.X;
            offset = new Vec3(0.5, 0.0, 0.0);
        }

        return createDimensionTransition(newLevel, exitPortal, axis, offset, entity, postTeleportTransition);
    }

    private static TeleportTransition createDimensionTransition(ServerLevel newLevel, BlockUtil.FoundRectangle foundRectangle, Direction.Axis portalAxis, Vec3 offset, Entity entity, TeleportTransition.PostTeleportTransition postTeleportTransition) {
        BlockPos bottomLeft = foundRectangle.minCorner;
        BlockState blockState = newLevel.getBlockState(bottomLeft);
        Direction.Axis axis = blockState.getOptionalValue(BlockStateProperties.HORIZONTAL_AXIS).orElse(Direction.Axis.X);
        double width = foundRectangle.axis1Size;
        double height = foundRectangle.axis2Size;
        EntityDimensions dimensions = entity.getDimensions(entity.getPose());
        int outputRotation = portalAxis == axis ? 0 : 90;
        double offsetRight = dimensions.width() / 2.0 + (width - dimensions.width()) * offset.x();
        double offsetUp = (height - dimensions.height()) * offset.y();
        double offsetForward = 0.5 + offset.z();
        boolean xAligned = axis == Direction.Axis.X;
        Vec3 targetPos = new Vec3(
                bottomLeft.getX() + (xAligned ? offsetRight : offsetForward),
                bottomLeft.getY() + offsetUp,
                bottomLeft.getZ() + (xAligned ? offsetForward : offsetRight)
        );
        Vec3 collisionFreePos = StarryPortalShape.findCollisionFreePosition(targetPos, newLevel, entity, dimensions);
        return new TeleportTransition(
                newLevel, collisionFreePos, Vec3.ZERO, outputRotation, 0.0F, Relative.union(Relative.DELTA, Relative.ROTATION), postTeleportTransition
        );
    }

    @Override
    public Portal.Transition getLocalTransition() {
        return Portal.Transition.CONFUSION;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(100) == 0) {
            level.playLocalSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS, 0.5F, random.nextFloat() * 0.4F + 0.8F, false);
        }

        double x = pos.getX() + random.nextDouble();
        double y = pos.getY() + random.nextDouble();
        double z = pos.getZ() + random.nextDouble();
        double xa = (random.nextFloat() - 0.5) * 0.5;
        double ya = (random.nextFloat() - 0.5) * 0.5;
        double za = (random.nextFloat() - 0.5) * 0.5;
        int flip = random.nextInt(2) * 2 - 1;
        if (!level.getBlockState(pos.west()).is(this) && !level.getBlockState(pos.east()).is(this)) {
            x = pos.getX() + 0.5 + 0.25 * flip;
            xa = random.nextFloat() * 2.0F * flip;
        } else {
            z = pos.getZ() + 0.5 + 0.25 * flip;
            za = random.nextFloat() * 2.0F * flip;
        }

        level.addParticle(ParticleTypes.WAX_OFF, x, y, z, xa, ya, za);
    }

    @Override
    protected ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData) {
        return ItemStack.EMPTY;
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return switch (rotation) {
            case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> switch (state.getValue(AXIS)) {
                case X -> state.setValue(AXIS, Direction.Axis.Z);
                case Z -> state.setValue(AXIS, Direction.Axis.X);
                default -> state;
            };
            default -> state;
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }
}
