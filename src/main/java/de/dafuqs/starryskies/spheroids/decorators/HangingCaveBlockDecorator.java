package de.dafuqs.starryskies.spheroids.decorators;

import de.dafuqs.starryskies.spheroids.SpheroidDecorator;
import de.dafuqs.starryskies.spheroids.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;

public class HangingCaveBlockDecorator extends SpheroidDecorator {
	
	private final BlockState BLOCKSTATE;
	private final float CHANCE;
	
	public HangingCaveBlockDecorator(BlockState blockState, float chance) {
		BLOCKSTATE = blockState;
		CHANCE = chance;
	}
	
	@Override
	public void decorateSpheroid(StructureWorldAccess world, ChunkPos origin, Spheroid spheroid, Random random) {
		for (BlockPos bp : getBottomBlocks(world, origin, spheroid)) {
			if (!world.getBlockState(bp).isAir() && random.nextFloat() < CHANCE) {
				if (world.getBlockState(bp.down()).isAir()) {
					world.setBlockState(bp.down(), BLOCKSTATE, 3);
				}
				break;
			}
		}
	}
}
