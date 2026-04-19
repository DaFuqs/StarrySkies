package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;

public class HangingCaveBlockDecorator extends SphereDecorator<HangingCaveBlockDecoratorConfig> {

	public HangingCaveBlockDecorator(Codec<HangingCaveBlockDecoratorConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(SphereFeatureContext<HangingCaveBlockDecoratorConfig> context) {
		WorldGenLevel world = context.world();
		PlacedSphere<?> sphere = context.sphere();
		ChunkPos origin = context.chunkPos();
		RandomSource random = context.random();
		HangingCaveBlockDecoratorConfig config = context.config();

		// TODO: is that correct?
		for (BlockPos bp : getBottomBlocks(world, origin, sphere)) {
			if (!world.getBlockState(bp).isAir() && random.nextFloat() < config.chance()) {
				if (world.getBlockState(bp.below()).isAir()) {
					world.setBlock(bp.below(), config.block(), 3);
				}
				return true;
			}
		}

		return false;
	}

}
