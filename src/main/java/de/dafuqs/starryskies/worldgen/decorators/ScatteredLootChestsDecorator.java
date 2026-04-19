package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;

import java.util.*;

/**
 * Places a chest with loot
 */
public class ScatteredLootChestsDecorator extends SphereDecorator<ScatteredLootChestsDecoratorConfig> {
	
	public ScatteredLootChestsDecorator(Codec<ScatteredLootChestsDecoratorConfig> codec) {
		super(codec);
	}
	
	@Override
	public boolean generate(SphereFeatureContext<ScatteredLootChestsDecoratorConfig> context) {
		WorldGenLevel world = context.world();
		PlacedSphere<?> sphere = context.sphere();
		ChunkPos origin = context.chunkPos();
		RandomSource random = context.random();
		ScatteredLootChestsDecoratorConfig config = context.config();
		
		ScatteredLootChestsDecoratorConfig.Position position = config.position();
		
		List<BlockPos> chestPos = switch (position) {
			case TOP -> getTopBlocks(world, origin, sphere);
			case CAVE_FLOOR -> getCaveBottomBlocks(world, origin, sphere);
		};
		
		for (BlockPos pos : chestPos) {
			if (random.nextFloat() < config.chancePerValidPosition()) {
				placeLootChest(world, pos.above(), config.lootTable(), random);
			}
		}
		
		return false;
	}
	
}
