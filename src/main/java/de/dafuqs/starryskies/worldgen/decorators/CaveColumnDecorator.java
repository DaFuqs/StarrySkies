package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;


public class CaveColumnDecorator extends SphereDecorator<CaveColumnDecoratorConfig> {
	
	public CaveColumnDecorator(Codec<CaveColumnDecoratorConfig> codec) {
		super(codec);
	}
	
	@Override
	public boolean generate(SphereFeatureContext<CaveColumnDecoratorConfig> context) {
		WorldGenLevel world = context.world();
		PlacedSphere<?> sphere = context.sphere();
		ChunkPos origin = context.chunkPos();
		RandomSource random = context.random();
		CaveColumnDecoratorConfig config = context.config();
		
		if (!sphere.isCenterInChunk(origin)) {
			return false;
		}
		
		BlockPos spherePos = sphere.getPosition();
		int sphereY = spherePos.getY();
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		
		world.setBlock(spherePos, config.centerState().getState(world, random, mutable), Block.UPDATE_ALL);
		
		mutable.set(spherePos.getX(), spherePos.getY() + 1, spherePos.getZ());
		int maxY = findNextNonAirBlockInDirection(world, mutable, Direction.UP, sphere.getRadius()).getY();
		for (int y = sphereY + 1; y < maxY; y++) {
			mutable.set(spherePos.getX(), y, spherePos.getZ());
			world.setBlock(mutable, config.columnState().getState(world, random, mutable), Block.UPDATE_ALL);
		}
		
		mutable.set(spherePos.getX(), spherePos.getY() - 1, spherePos.getZ());
		int minY = findNextNonAirBlockInDirection(world, mutable, Direction.DOWN, sphere.getRadius()).getY();
		for (int y = sphereY - 1; y > minY; y--) {
			mutable.set(spherePos.getX(), y, spherePos.getZ());
			world.setBlock(mutable, config.columnState().getState(world, random, mutable), Block.UPDATE_ALL);
		}
		
		return true;
	}
	
}
