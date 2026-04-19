package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class DripleafDecorator extends SphereDecorator<DripleafDecoratorConfig> {

	private static final BlockState DRIPLEAF_BLOCK_STATE = Blocks.BIG_DRIPLEAF.defaultBlockState();
	private static final BlockState DRIPLEAF_STEM_BLOCK_STATE = Blocks.BIG_DRIPLEAF_STEM.defaultBlockState();
	private static final BlockState WATER_BLOCK_STATE = Blocks.WATER.defaultBlockState();
	private static final BlockState CLAY_BLOCK_STATE = Blocks.CLAY.defaultBlockState();

	public DripleafDecorator(Codec<DripleafDecoratorConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(SphereFeatureContext<DripleafDecoratorConfig> context) {
		WorldGenLevel world = context.world();
		PlacedSphere<?> sphere = context.sphere();
		ChunkPos origin = context.chunkPos();
		RandomSource random = context.random();
		DripleafDecoratorConfig config = context.config();
		
		for (BlockPos bp : getRandomCaveBottomBlocks(world, origin, sphere, random, config.tries())) {
			boolean canGenerate;

			// check if all 4 sides of the future water pond are solid
			canGenerate = true;
			Iterator<Direction> direction = Direction.Plane.HORIZONTAL.iterator();
			while (direction.hasNext() && canGenerate) {
				BlockPos currentCheckBlockPos = bp.relative(direction.next());

				if (!world.getBlockState(currentCheckBlockPos).isRedstoneConductor(world, currentCheckBlockPos) || !world.getBlockState(currentCheckBlockPos.above()).isAir()) {
					canGenerate = false;
				}
			}

			if (canGenerate) {
				// clay
				world.setBlock(bp, CLAY_BLOCK_STATE, 3);

				// the dripleaf
				Direction randomDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
				int dripLeafHeight = random.nextInt(3) + 1;
				for (int i = 0; i <= dripLeafHeight; i++) {
					BlockState dripleafState = DRIPLEAF_BLOCK_STATE.setValue(HorizontalDirectionalBlock.FACING, randomDirection);
					if (dripleafState.canSurvive(world, bp.above(i))) {
						if (i == dripLeafHeight) {
							world.setBlock(bp.above(i), DRIPLEAF_BLOCK_STATE.setValue(HorizontalDirectionalBlock.FACING, randomDirection), 3);
						} else {
							world.setBlock(bp.above(i), DRIPLEAF_STEM_BLOCK_STATE.setValue(HorizontalDirectionalBlock.FACING, randomDirection), 3);
						}

					}
				}

				// surrounding water
				direction = Direction.Plane.HORIZONTAL.iterator();
				while (direction.hasNext()) {
					Direction currentDirection = direction.next();
					BlockPos offsetPos = bp.relative(currentDirection);
					if (world.getBlockState(offsetPos.above()).isAir()) {
						world.setBlock(offsetPos, WATER_BLOCK_STATE, 3);
					}
				}
			}
		}

		return true;
	}

}
