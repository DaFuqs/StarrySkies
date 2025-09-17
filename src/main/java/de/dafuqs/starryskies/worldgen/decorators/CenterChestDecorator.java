package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.*;
import net.minecraft.world.*;

/**
 * Places a chest with loot in the center of the sphere
 */
public class CenterChestDecorator extends SphereDecorator<CenterChestDecoratorConfig> {
	
	public CenterChestDecorator(Codec<CenterChestDecoratorConfig> codec) {
		super(codec);
	}
	
	@Override
	public boolean generate(SphereFeatureContext<CenterChestDecoratorConfig> context) {
		StructureWorldAccess world = context.getWorld();
		PlacedSphere<?> sphere = context.getSphere();
		ChunkPos origin = context.getChunkPos();
		Random random = context.getRandom();
		CenterChestDecoratorConfig config = context.getConfig();
		
		if (!sphere.isCenterInChunk(origin)) {
			return false;
		}
		
		placeLootChest(world, sphere.getPosition(), config.lootTable(), random);
		
		return true;
	}
	
}
