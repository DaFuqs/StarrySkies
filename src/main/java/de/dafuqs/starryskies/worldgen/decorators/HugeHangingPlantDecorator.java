package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;

public class HugeHangingPlantDecorator extends SphereDecorator<HugePlantDecoratorConfig> {

	public HugeHangingPlantDecorator(Codec<HugePlantDecoratorConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(SphereFeatureContext<HugePlantDecoratorConfig> context) {
		WorldGenLevel world = context.world();
		PlacedSphere<?> sphere = context.sphere();
		ChunkPos origin = context.chunkPos();
		RandomSource random = context.random();
		HugePlantDecoratorConfig config = context.config();

		for (BlockPos bp : getBottomBlocks(world, origin, sphere)) {
			if (random.nextFloat() < config.chance()) {
				int thisHeight = Support.getRandomBetween(random, config.minHeight(), config.maxHeight());
				for (int i = 1; i < thisHeight + 1; i++) {
					if (world.getBlockState(bp.below(i)).isAir()) {

						BlockState placementBlockState = config.block();
						if (i == 1 && config.firstBlock() != null) {
							placementBlockState = config.firstBlock();
						} else if (i == thisHeight && config.lastBlock() != null) {
							placementBlockState = config.lastBlock();
						}

						world.setBlock(bp.below(i), placementBlockState, 3);
					} else {
						if (i > 1 && config.lastBlock() != null) {
							world.setBlock(bp.below(i - 1), config.lastBlock(), 3);
						}
						break;
					}
				}
			}
		}

		return true;
	}

}
