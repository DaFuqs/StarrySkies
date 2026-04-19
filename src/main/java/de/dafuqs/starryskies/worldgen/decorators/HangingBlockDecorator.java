package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;

public class HangingBlockDecorator extends SphereDecorator<HangingBlockDecoratorConfig> {

	public HangingBlockDecorator(Codec<HangingBlockDecoratorConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(SphereFeatureContext<HangingBlockDecoratorConfig> context) {
		WorldGenLevel world = context.world();
		PlacedSphere<?> sphere = context.sphere();
		ChunkPos origin = context.chunkPos();
		RandomSource random = context.random();
		HangingBlockDecoratorConfig config = context.config();

		int sphereY = sphere.getPosition().getY();
		for (BlockPos bp : getBottomBlocks(world, origin, sphere)) {
			BlockPos flippedBlockPos = bp.below((bp.getY() - sphereY) * 2);

			if (world.getBlockState(flippedBlockPos.below()).isAir()) {
				if (random.nextFloat() < config.chance()) {
					world.setBlock(flippedBlockPos.below(), config.state(), 3);
				}
			}
		}

		return true;
	}

}
