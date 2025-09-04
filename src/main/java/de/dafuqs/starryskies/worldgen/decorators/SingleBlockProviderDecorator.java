package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.*;
import net.minecraft.world.*;

public class SingleBlockProviderDecorator extends SphereDecorator<SingleBlockProviderDecoratorConfig> {
	
	public SingleBlockProviderDecorator(Codec<SingleBlockProviderDecoratorConfig> codec) {
		super(codec);
	}
	
	@Override
	public boolean generate(SphereFeatureContext<SingleBlockProviderDecoratorConfig> context) {
		StructureWorldAccess world = context.getWorld();
		PlacedSphere<?> sphere = context.getSphere();
		ChunkPos origin = context.getChunkPos();
		Random random = context.getRandom();
		SingleBlockProviderDecoratorConfig config = context.getConfig();
		
		for (BlockPos bp : getTopBlocks(world, origin, sphere)) {
			BlockState posState = world.getBlockState(bp);
			if (posState.isSolidBlock(world, bp) && world.getBlockState(bp.up()).isAir()) {
				if (random.nextFloat() < config.chance()) {
					world.setBlockState(bp.up(), config.state().get(random, bp), Block.NOTIFY_ALL);
				}
			}
		}
		
		return true;
	}
	
}
