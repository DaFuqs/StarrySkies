package de.dafuqs.starryskies.spheroids.decorators;

import de.dafuqs.starryskies.spheroids.SpheroidDecorator;
import de.dafuqs.starryskies.spheroids.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;


public class UnderPlantDecorator extends SpheroidDecorator {
	
	private final BlockState PLANT_BLOCKSTATE;
	private final float PLANT_CHANCE;
	
	/**
	 * A chance of 0 = 0%, 100 = 100%
	 */
	public UnderPlantDecorator(BlockState plant_blockState, float plant_chance) {
		PLANT_BLOCKSTATE = plant_blockState;
		PLANT_CHANCE = plant_chance;
	}
	
	@Override
	public void decorateSpheroid(StructureWorldAccess world, ChunkPos origin, Spheroid spheroid, Random random) {
		int spheroidY = spheroid.getPosition().getY();
		for (BlockPos bp : getBottomBlocks(world, origin, spheroid)) {
			BlockPos flippedBlockPos = bp.down((bp.getY() - spheroidY) * 2);
			
			if (world.getBlockState(flippedBlockPos.down()).isAir()) {
				if (random.nextFloat() < PLANT_CHANCE) {
					world.setBlockState(flippedBlockPos.down(), PLANT_BLOCKSTATE, 3);
				}
			}
		}
	}
}
