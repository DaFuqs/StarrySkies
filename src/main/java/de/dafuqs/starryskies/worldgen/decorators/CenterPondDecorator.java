package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class CenterPondDecorator extends SphereDecorator<CenterPondDecoratorConfig> {

	public CenterPondDecorator(Codec<CenterPondDecoratorConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(SphereFeatureContext<CenterPondDecoratorConfig> context) {
		WorldGenLevel world = context.world();
		PlacedSphere<?> sphere = context.sphere();
		ChunkPos origin = context.chunkPos();
		RandomSource random = context.random();
		CenterPondDecoratorConfig config = context.config();

		if (!sphere.isCenterInChunk(origin)) {
			return false;
		}

		// doesn't make sense on small spheres
		if (sphere.getRadius() > 9) {
			int pondRadius = (int) (sphere.getRadius() / 2.5);
			BlockPos sphereTop = sphere.getPosition().above(sphere.getRadius());

			int waterLevelY = sphereTop.getY();
			boolean waterLevelSet = false;

			for (int x = -pondRadius - 1; x <= pondRadius; x++) {
				for (int y = -pondRadius; y < 1; y++) {
					for (int z = -pondRadius - 1; z <= pondRadius; z++) {
						BlockPos currentBlockPos = sphereTop.offset(x, y, z);
						if (world.getBlockState(currentBlockPos).isAir()) {
							waterLevelY = currentBlockPos.getY() - 1;
							waterLevelSet = true;
							break;
						}
					}
					if (waterLevelSet) {
						break;
					}
				}
				if (waterLevelSet) {
					break;
				}
			}

			// if there is not enough room for water: just cancel
			// not nice, but eh
			if (waterLevelY - sphere.getPosition().getY() < pondRadius * 1.5) {
				return false;
			}

			boolean hasLootChest = random.nextFloat() < config.lootTableChance();
			BlockPos lootChestPosition = null;

			int pond15 = (int) Math.round(pondRadius * 1.5);
			for (int x = -pond15; x <= pond15; x++) {
				for (int y = -pondRadius; y < pondRadius; y++) {
					for (int z = -pond15; z <= pond15; z++) {
						BlockPos currentBlockPos = sphereTop.offset(x, y, z);

						BlockState blockState = null;
						if (currentBlockPos.getY() > waterLevelY) {
							blockState = Blocks.AIR.defaultBlockState();
						} else {
							double distance = Support.getDistance(currentBlockPos, sphereTop);
							double pondDistance = distance / pondRadius;
							if (pondDistance < 1.1) {
								if (hasLootChest && x == 0 && z == 0 && lootChestPosition == null) {
									lootChestPosition = currentBlockPos;
								}
								blockState = config.fluidState();
							} else if (pondDistance < 1.70) {
								blockState = config.beachState();
							}
						}

						if (blockState != null) {
							if (!world.getBlockState(currentBlockPos).isAir()) {
								world.setBlock(currentBlockPos, blockState, 3);
							}
						}

					}
				}
			}

			if (lootChestPosition != null) {
				placeLootChest(world, lootChestPosition, config.lootTable(), random);
			}
		}

		return true;
	}

}
