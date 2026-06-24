package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;


public class RuinedPortalDecorator extends SphereDecorator<RuinedPortalDecoratorConfig> {

	private static final BlockState NETHERRACK = Blocks.NETHERRACK.defaultBlockState();
	private static final BlockState MAGMA_BLOCK = Blocks.MAGMA_BLOCK.defaultBlockState();
	private static final BlockState LAVA = Blocks.LAVA.defaultBlockState();
	private static final BlockState OBSIDIAN = Blocks.OBSIDIAN.defaultBlockState();
	private static final float OBSIDIAN_CHANCE = 0.9F;

	public RuinedPortalDecorator(Codec<RuinedPortalDecoratorConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(SphereFeatureContext<RuinedPortalDecoratorConfig> context) {
		WorldGenLevel world = context.world();
		PlacedSphere<?> sphere = context.sphere();
		ChunkPos origin = context.chunkPos();
		RandomSource random = context.random();
		RuinedPortalDecoratorConfig config = context.config();

		if (!sphere.isCenterInChunk(origin)) {
			return false;
		}
		BlockPos spherePosition = sphere.getPosition();

		// place the floor
		for (int x = -sphere.getRadius(); x <= sphere.getRadius(); x++) {
			for (int z = -sphere.getRadius(); z <= sphere.getRadius(); z++) {

				int startY = spherePosition.getY() + sphere.getRadius() + 1;
				int upperY = Support.getLowerGroundBlock(world, new BlockPos(spherePosition.getX() + x, startY, spherePosition.getZ() + z), spherePosition.getY());

				if (upperY > spherePosition.getY()) {
					int randomI = random.nextInt(sphere.getRadius() + 1);
					if (Math.abs(x * z) * 1.5 < randomI * randomI) {
						BlockPos currentBlockPos = new BlockPos(spherePosition.getX() + x, upperY, spherePosition.getZ() + z);
						switch (random.nextInt(6)) {
							case 0 -> world.setBlock(currentBlockPos, MAGMA_BLOCK, 3);
							case 1 -> {
								world.setBlock(currentBlockPos, LAVA, 3);
                                world.getChunk(currentBlockPos).markPosForPostProcessing(currentBlockPos);
							}
							default -> world.setBlock(currentBlockPos, NETHERRACK, 3);
						}
					}
				}
			}
		}

		// place portal
		int centerTopBlockY = Support.getLowerGroundBlock(world, new BlockPos(spherePosition.getX(), spherePosition.getY() + sphere.getRadius() + 1, spherePosition.getZ()), spherePosition.getY());
		BlockPos currentBlockPos = new BlockPos(spherePosition.getX(), centerTopBlockY, spherePosition.getZ());

		placePortalBlock(world, currentBlockPos, random);
		placePortalBlock(world, currentBlockPos.relative(Direction.SOUTH, 1), random);
		placePortalBlock(world, currentBlockPos.relative(Direction.NORTH, 1), random);
		placePortalBlock(world, currentBlockPos.relative(Direction.SOUTH, 2), random);
		placePortalBlock(world, currentBlockPos.relative(Direction.NORTH, 2), random);

		placePortalBlock(world, currentBlockPos.relative(Direction.SOUTH, 2).above(), random);
		placePortalBlock(world, currentBlockPos.relative(Direction.NORTH, 2).above(), random);
		placePortalBlock(world, currentBlockPos.relative(Direction.SOUTH, 2).above(1), random);
		placePortalBlock(world, currentBlockPos.relative(Direction.NORTH, 2).above(1), random);
		placePortalBlock(world, currentBlockPos.relative(Direction.SOUTH, 2).above(2), random);
		placePortalBlock(world, currentBlockPos.relative(Direction.NORTH, 2).above(2), random);
		placePortalBlock(world, currentBlockPos.relative(Direction.SOUTH, 2).above(3), random);
		placePortalBlock(world, currentBlockPos.relative(Direction.NORTH, 2).above(3), random);
		placePortalBlock(world, currentBlockPos.relative(Direction.SOUTH, 2).above(4), random);
		placePortalBlock(world, currentBlockPos.relative(Direction.NORTH, 2).above(4), random);

		placePortalBlock(world, currentBlockPos.above(5), random);
		placePortalBlock(world, currentBlockPos.relative(Direction.SOUTH, 1).above(5), random);
		placePortalBlock(world, currentBlockPos.relative(Direction.NORTH, 1).above(5), random);
		placePortalBlock(world, currentBlockPos.relative(Direction.SOUTH, 2).above(5), random);
		placePortalBlock(world, currentBlockPos.relative(Direction.NORTH, 2).above(5), random);

		// place loot chest
		int randomX = Support.getRandomBetween(random, spherePosition.getX() - sphere.getRadius() / 2, spherePosition.getX() + sphere.getRadius() / 2);
		int randomZ = Support.getRandomBetween(random, spherePosition.getZ() - sphere.getRadius() / 2, spherePosition.getZ() + sphere.getRadius() / 2);
		centerTopBlockY = Support.getLowerGroundBlock(world, new BlockPos(randomX, spherePosition.getY() + sphere.getRadius() + 2, randomZ), spherePosition.getY());

		if (centerTopBlockY != spherePosition.getY()) {
			BlockPos lootChestPosition = new BlockPos(randomX, centerTopBlockY, randomZ).above();
			placeLootChest(world, lootChestPosition, config.lootTable(), random);
		}

		return true;
	}

	private void placePortalBlock(WorldGenLevel world, BlockPos blockPos, RandomSource random) {
		if (random.nextFloat() < OBSIDIAN_CHANCE) {
			world.setBlock(blockPos, OBSIDIAN, 3);
		}
	}

}