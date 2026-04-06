package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;

import java.util.*;


public class PlantAroundPondDecorator extends SphereDecorator<PlantAroundPondDecoratorConfig> {

	public PlantAroundPondDecorator(Codec<PlantAroundPondDecoratorConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(SphereFeatureContext<PlantAroundPondDecoratorConfig> context) {
		WorldGenLevel world = context.world();
		PlacedSphere<?> sphere = context.sphere();
		ChunkPos origin = context.chunkPos();
		RandomSource random = context.random();
		PlantAroundPondDecoratorConfig config = context.config();
		
		for (BlockPos pos : getTopBlocks(world, origin, sphere, random, PlantAroundPondDecoratorConfig.pond_tries)) {
			boolean canGenerate;
			// check if all 4 sides of the future water pond are solid
			canGenerate = true;
			Iterator<Direction> direction = Direction.Plane.HORIZONTAL.iterator();
			while (direction.hasNext() && canGenerate) {
				BlockPos currentCheckBlockPos = pos.relative(direction.next());

				if (!world.getBlockState(currentCheckBlockPos).isRedstoneConductor(world, currentCheckBlockPos)
						|| !world.getBlockState(currentCheckBlockPos.above()).isAir()) {
					canGenerate = false;
				}
			}

			if (canGenerate) {
				world.setBlock(pos, Blocks.WATER.defaultBlockState(), 3);

				// place sugar cane with chance
				direction = Direction.Plane.HORIZONTAL.iterator();
				while (direction.hasNext()) {
					Direction currentDirection = direction.next();
					if (random.nextFloat() < config.plant_chance) {
						BlockPos sugarCaneBlockPos = pos.above().relative(currentDirection);
						int sugarCaneHeight = Support.getRandomBetween(random, config.minHeight, config.maxHeight);
						for (int i = 0; i <= sugarCaneHeight; i++) {
							if (config.block.canSurvive(world, sugarCaneBlockPos.above(i))) {
								world.setBlock(sugarCaneBlockPos.above(i), config.block, 3);
							}
						}
					}
				}
			}
		}

		return true;
	}

}



