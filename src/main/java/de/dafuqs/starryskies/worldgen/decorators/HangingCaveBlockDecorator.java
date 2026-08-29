package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.*;

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

		outer:
		for (BlockPos bp : getCaveCeilingBlocks(world, origin, sphere)) {
			if (world.getBlockState(bp).isAir() && random.nextFloat() < config.chance()) {
				BlockState base = config.getBlockFor(0, 1).getState(world, random, bp);
				if(!base.canSurvive(world, bp)) {
					continue;
				}
				int height = config.height().sample(random);

				// is there enough room?
				for(int i = 0; i < height; i++) {
					if(!world.getBlockState(bp.below(i)).isAir()) {
						continue outer;
					}
				}

				// place
				for(int i = 0; i < height; i++) {
					BlockState stateToPlace = config.getBlockFor(i, height).getState(world, random, bp);
					world.setBlock(bp.below(i), stateToPlace, 2);
				}
			}
		}

		return true;
	}

}
