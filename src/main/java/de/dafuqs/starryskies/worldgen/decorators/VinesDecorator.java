package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.levelgen.feature.*;

public class VinesDecorator extends SphereDecorator<VinesDecoratorConfig> {

	public VinesDecorator(Codec<VinesDecoratorConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(SphereFeatureContext<VinesDecoratorConfig> context) {
		WorldGenLevel level = context.world();
		PlacedSphere<?> sphere = context.sphere();
		ChunkPos origin = context.chunkPos();
		RandomSource random = context.random();
		VinesDecoratorConfig config = context.config();

		outer:
		for(BlockPos pos : getAllBlocksInChunk(level, origin, sphere)) {
			if (!level.isEmptyBlock(pos)) {
				continue;
			}
			if(config.chance() > random.nextFloat()) {
				continue;
			}

			int height = config.height().sample(random);
			if(height <= 0) {
				continue;
			}

			for (Direction direction : Direction.allShuffled(random)) {
				if(!direction.getAxis().isHorizontal()) {
					continue;
				}
				if (!VineBlock.isAcceptableNeighbour(level, pos.relative(direction), direction)) {
					continue;
				}
				BlockState placeState = config.block().setValue(VineBlock.getPropertyForFace(direction), true);
				level.setBlock(pos, placeState, 2);

				for(int i = 1; i < height; i++) {
					BlockPos below = pos.below(i);
					if (!level.isEmptyBlock(below)) {
						continue outer;
					}

					level.setBlock(below, placeState, 2);
					break;
				}
			}
		}

		return true;
	}

}
