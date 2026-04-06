package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import org.jetbrains.annotations.*;

/**
 * Places a chest with loot
 */
public class LootChestDecorator extends SphereDecorator<LootChestDecoratorConfig> {
	
	public LootChestDecorator(Codec<LootChestDecoratorConfig> codec) {
		super(codec);
	}
	
	@Override
	public boolean generate(SphereFeatureContext<LootChestDecoratorConfig> context) {
		WorldGenLevel world = context.world();
		PlacedSphere<?> sphere = context.sphere();
		ChunkPos origin = context.chunkPos();
		RandomSource random = context.random();
		LootChestDecoratorConfig config = context.config();
		
		if (!sphere.isCenterInChunk(origin)) {
			return false;
		}
		
		LootChestDecoratorConfig.Position position = config.position();
		
		@Nullable BlockPos chestPos = switch (position) {
			case TOP_CENTER -> sphere.getPosition().above(sphere.getRadius() + 1);
			case CENTER -> sphere.getPosition();
			case CAVE_FLOOR -> getCaveBottomBlock(world, sphere.getPosition().above(), sphere);
		};
		
		if (chestPos != null) {
			placeLootChest(world, chestPos, config.lootTable(), random);
			return true;
		}
		
		return false;
	}
	
}
