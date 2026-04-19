package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;

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
				
				DripstoneThickness thickness = i == height ? DripstoneThickness.TIP : i == height - 1 ? DripstoneThickness.FRUSTUM : i == 1 ? DripstoneThickness.BASE : DripstoneThickness.MIDDLE;
				BlockState state = config.block().defaultBlockState().setValue(BlockStateProperties.VERTICAL_DIRECTION, Direction.DOWN).setValue(BlockStateProperties.DRIPSTONE_THICKNESS, thickness);
				world.setBlock(currPos, state, 3);
			}
			
		}
		
		return true;
	}
	
}
