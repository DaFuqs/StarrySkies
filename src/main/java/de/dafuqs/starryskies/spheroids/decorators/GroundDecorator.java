package de.dafuqs.starryskies.spheroids.decorators;

import de.dafuqs.starryskies.spheroids.SpheroidDecorator;
import de.dafuqs.starryskies.spheroids.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;

public class GroundDecorator extends SpheroidDecorator {
	
	private final BlockState GROUND_BLOCK_STATE;
	private final float GROUND_CHANCE;
	
	/**
	 * Replaces the block in the ground
	 * A chance of 0 = 0%, 100 = 100%
	 */
	public GroundDecorator(BlockState groundBlockState, float ground_chance) {
		GROUND_BLOCK_STATE = groundBlockState;
		GROUND_CHANCE = ground_chance;
	}
	
	@Override
	public void decorateSpheroid(StructureWorldAccess world, ChunkPos origin, Spheroid spheroid, Random random) {
		for (BlockPos bp : getTopBlocks(world, origin, spheroid)) {
			if (random.nextFloat() < GROUND_CHANCE) {
				world.setBlockState(bp, GROUND_BLOCK_STATE, 3);
			}
		}
	}
}