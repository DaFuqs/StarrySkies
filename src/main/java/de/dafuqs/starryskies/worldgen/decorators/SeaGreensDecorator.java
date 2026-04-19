package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TallSeagrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;


public class SeaGreensDecorator extends SphereDecorator<SphereDecoratorConfig.DefaultSphereDecoratorConfig> {

	// those are all always waterlogged
	private static final BlockState KELP = Blocks.KELP.defaultBlockState(); // the top
	private static final BlockState KELP_PLANT = Blocks.KELP_PLANT.defaultBlockState(); // the middle
	private static final BlockState SEAGRASS = Blocks.SEAGRASS.defaultBlockState();
	private static final BlockState TALL_SEAGRASS_UPPER = Blocks.TALL_SEAGRASS.defaultBlockState().setValue(TallSeagrassBlock.HALF, DoubleBlockHalf.UPPER);
	private static final BlockState TALL_SEAGRASS_LOWER = Blocks.TALL_SEAGRASS.defaultBlockState().setValue(TallSeagrassBlock.HALF, DoubleBlockHalf.LOWER);

	public SeaGreensDecorator(Codec<SphereDecoratorConfig.DefaultSphereDecoratorConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(SphereFeatureContext<SphereDecoratorConfig.DefaultSphereDecoratorConfig> context) {
		WorldGenLevel world = context.world();
		PlacedSphere<?> sphere = context.sphere();
		ChunkPos origin = context.chunkPos();
		RandomSource random = context.random();

		for (BlockPos bp : getCaveBottomBlocks(world, origin, sphere)) {
			int r = random.nextInt(4);
			
			BlockPos up = bp.above();
			if (world.getBlockState(up).getBlock() != Blocks.WATER) {
				continue;
			}
			
			if (r == 0) {
				int kelpHeight = random.nextInt(8);
				for (int i = 0; i < kelpHeight; i++) {
					if (world.getBlockState(bp.above(i + 1)).getBlock() == Blocks.WATER) {
						if (world.getBlockState(bp.above(i + 2)).getBlock() == Blocks.WATER && i < kelpHeight - 1) {
							world.setBlock(bp.above(i + 1), KELP_PLANT, 3); // middle parts
						} else {
							world.setBlock(bp.above(i + 1), KELP, 3); // the top
						}
					}
				}
			} else if (r == 1) {
				world.setBlock(up, SEAGRASS, 3);
			} else if (r == 2) {
				if (world.getBlockState(bp.above(2)).getBlock() == Blocks.WATER) {
					world.setBlock(bp.above(2), TALL_SEAGRASS_UPPER, 3);
					world.setBlock(up, TALL_SEAGRASS_LOWER, 3);
				}
			}
		}

		return true;
	}

}
