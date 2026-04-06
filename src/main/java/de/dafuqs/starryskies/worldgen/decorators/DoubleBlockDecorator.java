package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class DoubleBlockDecorator extends SphereDecorator<DoubleBlockDecoratorConfig> {

	public DoubleBlockDecorator(Codec<DoubleBlockDecoratorConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(SphereFeatureContext<DoubleBlockDecoratorConfig> context) {
		WorldGenLevel world = context.world();
		PlacedSphere<?> sphere = context.sphere();
		ChunkPos origin = context.chunkPos();
		RandomSource random = context.random();
		DoubleBlockDecoratorConfig config = context.config();

		for (BlockPos bp : getTopBlocks(world, origin, sphere)) {
			if (!world.getBlockState(bp).isAir() && world.getBlockState(bp.above()).isAir() && world.getBlockState(bp.above(2)).isAir()) {
				if (random.nextFloat() < config.chance()) {
					world.setBlock(bp.above(), config.state().setValue(TallFlowerBlock.HALF, DoubleBlockHalf.LOWER), 3);
					world.setBlock(bp.above(2), config.state().setValue(TallFlowerBlock.HALF, DoubleBlockHalf.UPPER), 3);
				}
			}
		}

		return true;
	}

}
