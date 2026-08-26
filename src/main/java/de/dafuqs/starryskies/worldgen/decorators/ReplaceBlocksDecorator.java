package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;

public class ReplaceBlocksDecorator extends SphereDecorator<ReplaceBlocksDecoratorConfig> {

	public ReplaceBlocksDecorator(Codec<ReplaceBlocksDecoratorConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(SphereFeatureContext<ReplaceBlocksDecoratorConfig> context) {
		WorldGenLevel world = context.world();
		PlacedSphere<?> sphere = context.sphere();
		ChunkPos origin = context.chunkPos();
		RandomSource random = context.random();
		ReplaceBlocksDecoratorConfig config = context.config();

		BlockPos spherePos = sphere.getPosition();
		int cx = spherePos.getX();
		int cy = spherePos.getY();
		int cz = spherePos.getZ();

		int r = sphere.getCeiledRadius();
		int a = config.depth().sample(random);

		Iterable<BlockPos> positions = switch (config.towardsDirection()) {
			case DOWN -> BlockPos.betweenClosed(
					cx - r, cy + r - a, cz - r,
					cx + r, cy + r,     cz + r
			);
			case UP -> BlockPos.betweenClosed(
					cx - r, cy - r,     cz - r,
					cx + r, cy - r + a, cz + r
			);
			case SOUTH -> BlockPos.betweenClosed(
					cx - r, cy - r, cz - r,
					cx + r, cy + r, cz - r + a
			);
			case NORTH -> BlockPos.betweenClosed(
					cx - r, cy - r, cz + r - a,
					cx + r, cy + r, cz + r
			);
			case EAST -> BlockPos.betweenClosed(
					cx - r,     cy - r, cz - r,
					cx - r + a, cy + r, cz + r
			);
			case WEST -> BlockPos.betweenClosed(
					cx + r - a, cy - r, cz - r,
					cx + r,     cy + r, cz + r
			);
		};

		for (BlockPos bp : positions) {
			if(!origin.contains(bp)) continue;
			if(world.getBlockState(bp).isAir()) continue;

			BlockState state = config.state().getState(world, random, bp);
			world.setBlock(bp, state, 3);
		}

		return true;
	}

}
