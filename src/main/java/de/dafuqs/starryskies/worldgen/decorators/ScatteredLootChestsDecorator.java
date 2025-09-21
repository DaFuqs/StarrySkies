package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;

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
		StructureWorldAccess world = context.getWorld();
		PlacedSphere<?> sphere = context.getSphere();
		ChunkPos origin = context.getChunkPos();
		Random random = context.getRandom();
		ScatteredLootChestsDecoratorConfig config = context.getConfig();
		
		ScatteredLootChestsDecoratorConfig.Position position = config.position();
		
		List<BlockPos> chestPos = switch (position) {
			case TOP -> getTopBlocks(world, origin, sphere);
			case CAVE_FLOOR -> getCaveBottomBlocks(world, origin, sphere);
		};
		
		for (BlockPos pos : chestPos) {
			if (random.nextFloat() < config.chancePerValidPosition()) {
				placeLootChest(world, pos.up(), config.lootTable(), random);
			}
		}
		
		return false;
	}
	
}
