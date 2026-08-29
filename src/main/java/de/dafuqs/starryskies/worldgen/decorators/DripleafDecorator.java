package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.*;

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
			if (!world.getBlockState(bp.above()).is(Blocks.WATER)) {
				continue;
			}

			// the dripleaf
			Direction randomDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
			int dripLeafHeight = random.nextInt(3) + 2;
			for (int i = 1; i <= dripLeafHeight; i++) {
				BlockState dripleafState = DRIPLEAF_BLOCK_STATE.setValue(HorizontalDirectionalBlock.FACING, randomDirection);
				BlockPos currPos = bp.above(i);
				if (dripleafState.canSurvive(world, currPos)) {
					boolean waterLogged = world.getBlockState(currPos).is(Blocks.WATER);

					if (i == dripLeafHeight) {
						world.setBlock(currPos, DRIPLEAF_BLOCK_STATE
								.setValue(HorizontalDirectionalBlock.FACING, randomDirection)
								.setValue(BlockStateProperties.WATERLOGGED, waterLogged), 3);
					} else {
						world.setBlock(currPos, DRIPLEAF_STEM_BLOCK_STATE
								.setValue(HorizontalDirectionalBlock.FACING, randomDirection)
								.setValue(BlockStateProperties.WATERLOGGED, waterLogged), 3);
					}
				}
			}
		}

		return true;
	}

}
