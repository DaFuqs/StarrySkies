package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.*;
import net.minecraft.world.*;
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
		StructureWorldAccess world = context.getWorld();
		PlacedSphere<?> sphere = context.getSphere();
		ChunkPos origin = context.getChunkPos();
		Random random = context.getRandom();
		LootChestDecoratorConfig config = context.getConfig();
		
		if (!sphere.isCenterInChunk(origin)) {
			return false;
		}
		
		LootChestDecoratorConfig.Position position = config.position();
		
		@Nullable BlockPos chestPos = switch (position) {
			case TOP_CENTER -> sphere.getPosition().up(sphere.getRadius() + 1);
			case CENTER -> sphere.getPosition();
			case CAVE_FLOOR -> getCaveBottomBlock(world, sphere.getPosition().up(), sphere);
		};
		
		if (chestPos != null) {
			placeLootChest(world, chestPos, config.lootTable(), random);
			return true;
		}
		
		return false;
	}
	
}
