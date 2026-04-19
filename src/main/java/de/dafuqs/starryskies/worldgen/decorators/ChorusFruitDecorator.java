package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.ChorusFlowerBlock;

public class ChorusFruitDecorator extends SphereDecorator<ChorusFruitDecoratorConfig> {

	public ChorusFruitDecorator(Codec<ChorusFruitDecoratorConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(SphereFeatureContext<ChorusFruitDecoratorConfig> context) {
		WorldGenLevel world = context.world();
		PlacedSphere<?> sphere = context.sphere();
		ChunkPos origin = context.chunkPos();
		RandomSource random = context.random();
		ChorusFruitDecoratorConfig config = context.config();

		boolean success = false;
		for (BlockPos bp : getTopBlocks(world, origin, sphere)) {
			if (random.nextFloat() < config.chorusChance) {
				ChorusFlowerBlock.generatePlant(world, bp.above(), random, 8);
				success = true;
			}
		}

		return success;
	}

}
