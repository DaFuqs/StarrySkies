package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.*;
import net.minecraft.world.*;
import net.minecraft.world.gen.stateprovider.*;

public class BottomBlocksDecorator extends SphereDecorator<BottomBlocksDecoratorConfig> {
	
	public BottomBlocksDecorator(Codec<BottomBlocksDecoratorConfig> codec) {
		super(codec);
	}
	
	@Override
	public boolean generate(SphereFeatureContext<BottomBlocksDecoratorConfig> context) {
		StructureWorldAccess world = context.getWorld();
		PlacedSphere<?> sphere = context.getSphere();
		ChunkPos origin = context.getChunkPos();
		Random random = context.getRandom();
		BottomBlocksDecoratorConfig config = context.getConfig();
		
		boolean isTopBlockSet = config.topState().isPresent();
		boolean isBottomBlockSet = config.bottomState().isPresent();
		
		BlockPos.Mutable currPos = new BlockPos.Mutable();
		for (BlockPos bp : getBottomBlocks(world, origin, sphere)) {
			int height = config.height().get(random);
			
			for (int i = 0; i < height; i++) {
				currPos.set(bp.getX(), bp.getY() + i, bp.getZ());
				
				BlockStateProvider provider =
						isBottomBlockSet && i == 0
								? config.bottomState().get()
								: isTopBlockSet && i == height - 1
								? config.topState().get()
								: config.state();
				
				BlockState state = provider.get(random, currPos);
				world.setBlockState(currPos, state, 3);
			}
			
		}
		
		return true;
	}
	
}
