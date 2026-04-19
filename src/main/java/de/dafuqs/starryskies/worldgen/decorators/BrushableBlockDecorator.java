package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;

public class BrushableBlockDecorator extends SphereDecorator<BrushableBlockDecoratorConfig> {
	
	public BrushableBlockDecorator(Codec<BrushableBlockDecoratorConfig> codec) {
		super(codec);
	}
	
	@Override
	public boolean generate(SphereFeatureContext<BrushableBlockDecoratorConfig> context) {
		WorldGenLevel world = context.world();
		PlacedSphere<?> sphere = context.sphere();
		ChunkPos origin = context.chunkPos();
		RandomSource random = context.random();
		BrushableBlockDecoratorConfig config = context.config();
		
		for (BlockPos bp : getTopBlocks(world, origin, sphere)) {
			if (random.nextFloat() < config.chance()) {
				world.setBlock(bp, config.state(), 3);
				if (world.getBlockEntity(bp) instanceof BrushableBlockEntity brushableBlockEntity) {
					brushableBlockEntity.setLootTable(config.lootTable(), random.nextLong());
				}
			}
		}
		
		return true;
	}
	
}
