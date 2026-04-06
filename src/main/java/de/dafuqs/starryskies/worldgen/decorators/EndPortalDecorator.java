package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallTorchBlock;

import java.util.*;

public class EndPortalDecorator extends SphereDecorator<SphereDecoratorConfig.DefaultSphereDecoratorConfig> {

	public EndPortalDecorator(Codec<SphereDecoratorConfig.DefaultSphereDecoratorConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(SphereFeatureContext<SphereDecoratorConfig.DefaultSphereDecoratorConfig> context) {
		WorldGenLevel world = context.world();
		PlacedSphere<?> sphere = context.sphere();
		ChunkPos origin = context.chunkPos();

		if (!sphere.isCenterInChunk(origin)) {
			return false;
		}
		return this.generatePortal(world, new BlockPos(0, 64, 0), true);
	}

	private boolean generatePortal(WorldGenLevel structureWorldAccess, BlockPos blockPos, boolean open) {
		Iterator<BlockPos> iterator = BlockPos.betweenClosed(new BlockPos(blockPos.getX() - 4, blockPos.getY() - 1, blockPos.getZ() - 4), new BlockPos(blockPos.getX() + 4, blockPos.getY() + 32, blockPos.getZ() + 4)).iterator();

		while (true) {
			BlockPos blockPos2;
			boolean bl;
			do {
				if (!iterator.hasNext()) {
					for (int i = 0; i < 4; ++i) {
						structureWorldAccess.setBlock(blockPos.above(i), Blocks.BEDROCK.defaultBlockState(), 3);
					}

					BlockPos blockPos3 = blockPos.above(2);
					for (Direction direction : Direction.Plane.HORIZONTAL) {
						structureWorldAccess.setBlock(blockPos3.relative(direction), Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, direction), 3);
					}

					return true;
				}

				blockPos2 = iterator.next();
				bl = blockPos2.closerThan(blockPos, 2.5D);
			} while (!bl && !blockPos2.closerThan(blockPos, 3.5D));

			if (blockPos2.getY() < blockPos.getY()) {
				if (bl) {
					structureWorldAccess.setBlock(blockPos2, Blocks.BEDROCK.defaultBlockState(), 3);
				} else if (blockPos2.getY() < blockPos.getY()) {
					structureWorldAccess.setBlock(blockPos2, Blocks.END_STONE.defaultBlockState(), 3);
				}
			} else if (blockPos2.getY() > blockPos.getY()) {
				structureWorldAccess.setBlock(blockPos2, Blocks.AIR.defaultBlockState(), 3);
			} else if (!bl) {
				structureWorldAccess.setBlock(blockPos2, Blocks.BEDROCK.defaultBlockState(), 3);
			} else if (open) {
				structureWorldAccess.setBlock(new BlockPos(blockPos2), Blocks.END_PORTAL.defaultBlockState(), 3);
			} else {
				structureWorldAccess.setBlock(new BlockPos(blockPos2), Blocks.AIR.defaultBlockState(), 3);
			}
		}
	}

}
