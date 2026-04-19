package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;


public class CaveBottomDecorator extends SphereDecorator<CaveBottomDecoratorConfig> {

	public CaveBottomDecorator(Codec<CaveBottomDecoratorConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(SphereFeatureContext<CaveBottomDecoratorConfig> context) {
		WorldGenLevel world = context.world();
		PlacedSphere<?> sphere = context.sphere();
		ChunkPos origin = context.chunkPos();
		RandomSource random = context.random();
		CaveBottomDecoratorConfig config = context.config();

		for (BlockPos bp : getCaveBottomBlocks(world, origin, sphere)) {
			if (random.nextFloat() < config.chance() && config.state().canSurvive(world, bp.above())) {
				world.setBlock(bp.above(), config.state(), 3);
			}
		}

		return true;
	}

}
