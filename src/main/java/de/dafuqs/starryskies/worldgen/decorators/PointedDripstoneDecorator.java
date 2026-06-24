package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;

public class PointedDripstoneDecorator extends SphereDecorator<PointedDripstoneDecoratorConfig> {
	
	public PointedDripstoneDecorator(Codec<PointedDripstoneDecoratorConfig> codec) {
		super(codec);
	}
	
	@Override
	public boolean generate(SphereFeatureContext<PointedDripstoneDecoratorConfig> context) {
		WorldGenLevel world = context.world();
		PlacedSphere<?> sphere = context.sphere();
		ChunkPos origin = context.chunkPos();
		RandomSource random = context.random();
		PointedDripstoneDecoratorConfig config = context.config();
		
		
		BlockPos.MutableBlockPos currPos = new BlockPos.MutableBlockPos();
		for (BlockPos bp : getBottomBlocks(world, origin, sphere)) {
			if (random.nextFloat() > config.chance()) {
				continue;
			}
			
			int height = config.height().sample(random);
			
			for (int i = 1; i <= height; i++) {
				currPos.set(bp.getX(), bp.getY() - i, bp.getZ());

				SpeleothemThickness thickness = i == height ? SpeleothemThickness.TIP : i == height - 1 ? SpeleothemThickness.FRUSTUM : i == 1 ? SpeleothemThickness.BASE : SpeleothemThickness.MIDDLE;
				BlockState state = config.block().defaultBlockState().setValue(BlockStateProperties.VERTICAL_DIRECTION, Direction.DOWN).setValue(BlockStateProperties.SPELEOTHEM_THICKNESS, thickness);
				world.setBlock(currPos, state, 3);
			}
			
		}
		
		return true;
	}
	
}
