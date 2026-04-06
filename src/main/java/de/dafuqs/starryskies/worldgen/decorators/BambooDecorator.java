package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BambooStalkBlock;
import net.minecraft.world.level.block.state.properties.BambooLeaves;

public class BambooDecorator extends SphereDecorator<BambooDecoratorConfig> {

	public BambooDecorator(Codec<BambooDecoratorConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(SphereFeatureContext<BambooDecoratorConfig> context) {
		WorldGenLevel world = context.world();
		PlacedSphere<?> sphere = context.sphere();
		ChunkPos origin = context.chunkPos();
		RandomSource random = context.random();
		BambooDecoratorConfig config = context.config();

		for (BlockPos bp : getTopBlocks(world, origin, sphere)) {
			if (random.nextFloat() < config.chance()) {
				if (random.nextFloat() < config.saplingChance()) {
					if (config.bambooBlockState().canSurvive(world, bp.above())) {
						world.setBlock(bp.above(), config.bambooBlockState(), 3);
					}
				} else {
					int height = random.nextInt(8);
					for (int i = 1; i < height; i++) {
						if (config.bambooBlockState().canSurvive(world, bp.above(i))) {
							if (i == 3 && height < 5) {
								world.setBlock(bp.above(i), config.bambooBlockState().setValue(BambooStalkBlock.LEAVES, BambooLeaves.NONE), 3);
							} else if (i > 4) {
								world.setBlock(bp.above(i), config.bambooBlockState().setValue(BambooStalkBlock.LEAVES, BambooLeaves.LARGE), 3);
							} else if (i > 2) {
								world.setBlock(bp.above(i), config.bambooBlockState().setValue(BambooStalkBlock.LEAVES, BambooLeaves.SMALL), 3);
							} else {
								world.setBlock(bp.above(i), config.bambooBlockState().setValue(BambooStalkBlock.LEAVES, BambooLeaves.NONE), 3);
							}
						}
					}
				}
			}
		}

		return true;
	}

}
