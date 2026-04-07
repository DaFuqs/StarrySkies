package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class BottomBlocksDecorator extends SphereDecorator<BottomBlocksDecoratorConfig> {
	
	public BottomBlocksDecorator(Codec<BottomBlocksDecoratorConfig> codec) {
		super(codec);
	}
	
	@Override
	public boolean generate(SphereFeatureContext<BottomBlocksDecoratorConfig> context) {
		WorldGenLevel world = context.world();
		PlacedSphere<?> sphere = context.sphere();
		ChunkPos origin = context.chunkPos();
		RandomSource random = context.random();
		BottomBlocksDecoratorConfig config = context.config();
		
		boolean isTopBlockSet = config.topState().isPresent();
		boolean isBottomBlockSet = config.bottomState().isPresent();
		
		BlockPos.MutableBlockPos currPos = new BlockPos.MutableBlockPos();
		for (BlockPos bp : getBottomBlocks(world, origin, sphere)) {
			int height = config.height().sample(random);
			
			for (int i = 0; i < height; i++) {
				currPos.set(bp.getX(), bp.getY() + i, bp.getZ());
				
				BlockStateProvider provider =
						isBottomBlockSet && i == 0
								? config.bottomState().get()
								: isTopBlockSet && i == height - 1
								? config.topState().get()
								: config.state();
				
				BlockState state = provider.getState(world, random, currPos);
				world.setBlock(currPos, state, 3);
			}
			
		}
		
		return true;
	}
	
}
