package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.levelgen.feature.*;

import java.util.*;

public class MultifaceGrowthDecorator extends SphereDecorator<MultifaceGrowthDecoratorConfig> {

	public MultifaceGrowthDecorator(Codec<MultifaceGrowthDecoratorConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(SphereFeatureContext<MultifaceGrowthDecoratorConfig> context) {
		WorldGenLevel world = context.world();
		PlacedSphere<?> sphere = context.sphere();
		ChunkPos origin = context.chunkPos();
		RandomSource random = context.random();
		MultifaceGrowthDecoratorConfig config = context.config();

		int sphereY = sphere.getPosition().getY();
		if (!(config.featureConfig.placeBlock instanceof MultifaceSpreadeableBlock multifaceSpreadeableBlock)) {
			return false;
		}

		for (BlockPos bp : getCaveBottomBlocks(world, origin, sphere)) {
			if (random.nextFloat() < config.chance) {
				BlockPos currentPos = new BlockPos(bp.getX(), sphereY, bp.getZ());
				for (int i = 0; i < sphere.getRadius(); i++) {
					if (!world.getBlockState(currentPos.above(i)).isAir()) {
						if (world.getBlockState(currentPos.above(i - 1)).isAir()) {
							MultifaceGrowthFeature.placeGrowthIfPossible(multifaceSpreadeableBlock, world, currentPos, world.getBlockState(bp), config.featureConfig, random, Arrays.asList(Direction.values()));
						}
						break;
					}
				}
			}
		}

		return true;
	}

}
