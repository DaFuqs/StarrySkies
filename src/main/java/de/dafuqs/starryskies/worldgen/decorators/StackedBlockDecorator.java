package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;

public class StackedBlockDecorator extends SphereDecorator<StackedBlockDecoratorConfig> {

	public StackedBlockDecorator(Codec<StackedBlockDecoratorConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(SphereFeatureContext<StackedBlockDecoratorConfig> context) {
		WorldGenLevel world = context.world();
		PlacedSphere<?> sphere = context.sphere();
		ChunkPos origin = context.chunkPos();
		RandomSource random = context.random();
		StackedBlockDecoratorConfig config = context.config();

		for (BlockPos bp : getTopBlocks(world, origin, sphere)) {
			if (random.nextFloat() < config.chance()) {
				int height = Support.getRandomBetween(random, config.minHeight(), config.maxHeight());
				for (int i = 0; i < height; i++) {
					if (config.block().canSurvive(world, bp.above(i + 1))) {
						world.setBlock(bp.above(i + 1), config.block(), Block.UPDATE_ALL);
					}
				}
			}
		}

		return true;
	}

}