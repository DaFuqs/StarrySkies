package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.block.*;
import net.minecraft.block.enums.*;
import net.minecraft.state.property.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.*;
import net.minecraft.world.*;

public class PointedDripstoneDecorator extends SphereDecorator<PointedDripstoneDecoratorConfig> {
	
	public PointedDripstoneDecorator(Codec<PointedDripstoneDecoratorConfig> codec) {
		super(codec);
	}
	
	@Override
	public boolean generate(SphereFeatureContext<PointedDripstoneDecoratorConfig> context) {
		StructureWorldAccess world = context.getWorld();
		PlacedSphere<?> sphere = context.getSphere();
		ChunkPos origin = context.getChunkPos();
		Random random = context.getRandom();
		PointedDripstoneDecoratorConfig config = context.getConfig();
		
		
		BlockPos.Mutable currPos = new BlockPos.Mutable();
		for (BlockPos bp : getBottomBlocks(world, origin, sphere)) {
			if (random.nextFloat() > config.chance()) {
				continue;
			}
			
			int height = config.height().get(random);
			
			for (int i = 1; i <= height; i++) {
				currPos.set(bp.getX(), bp.getY() - i, bp.getZ());
				
				Thickness thickness = i == height ? Thickness.TIP : i == height - 1 ? Thickness.FRUSTUM : i == 1 ? Thickness.BASE : Thickness.MIDDLE;
				BlockState state = config.block().getDefaultState().with(Properties.VERTICAL_DIRECTION, Direction.DOWN).with(Properties.THICKNESS, thickness);
				world.setBlockState(currPos, state, 3);
			}
			
		}
		
		return true;
	}
	
}
