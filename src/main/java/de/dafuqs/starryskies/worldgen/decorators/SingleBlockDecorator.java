package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class SingleBlockDecorator extends SphereDecorator<SingleBlockDecoratorConfig> {

	public SingleBlockDecorator(Codec<SingleBlockDecoratorConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(SphereFeatureContext<SingleBlockDecoratorConfig> context) {
		WorldGenLevel world = context.world();
		PlacedSphere<?> sphere = context.sphere();
		ChunkPos origin = context.chunkPos();
		RandomSource random = context.random();
		SingleBlockDecoratorConfig config = context.config();

		for (BlockPos bp : getTopBlocks(world, origin, sphere)) {
			BlockState posState = world.getBlockState(bp);
			if (posState.isRedstoneConductor(world, bp) && world.getBlockState(bp.above()).isAir()) {
				if (random.nextFloat() < config.chance()) {
					world.setBlock(bp.above(), config.state(), Block.UPDATE_ALL);
				}
			}
		}

		return true;
	}

}
