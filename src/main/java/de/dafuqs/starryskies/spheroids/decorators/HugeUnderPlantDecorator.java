package de.dafuqs.starryskies.spheroids.decorators;

import de.dafuqs.starryskies.Support;
import de.dafuqs.starryskies.spheroids.SpheroidDecorator;
import de.dafuqs.starryskies.spheroids.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;


public class HugeUnderPlantDecorator extends SpheroidDecorator {
	
	private final BlockState PLANT_BLOCKSTATE;
	private BlockState FIRST_BLOCKSTATE;
	private BlockState LAST_BLOCKSTATE;
	private final float PLANT_CHANCE;
	private final int MIN_HEIGHT;
	private final int MAX_HEIGHT;
	
	/**
	 * A chance of 0 = 0%, 100 = 100%
	 */
	public HugeUnderPlantDecorator(BlockState plant_blockState, float plant_chance, int minHeight, int maxHeight) {
		PLANT_BLOCKSTATE = plant_blockState;
		PLANT_CHANCE = plant_chance;
		MIN_HEIGHT = minHeight;
		MAX_HEIGHT = maxHeight;
	}
	
	public HugeUnderPlantDecorator setFirstBlockState(BlockState blockState) {
		this.FIRST_BLOCKSTATE = blockState;
		return this;
	}
	
	public HugeUnderPlantDecorator setLastBlockState(BlockState blockState) {
		this.LAST_BLOCKSTATE = blockState;
		return this;
	}
	
	@Override
	public void decorateSpheroid(StructureWorldAccess world, ChunkPos origin, Spheroid spheroid, Random random) {
		for (BlockPos bp : getBottomBlocks(world, origin, spheroid)) {
			
			if (random.nextFloat() < PLANT_CHANCE) {
				int thisHeight = Support.getRandomBetween(random, MIN_HEIGHT, MAX_HEIGHT);
				for (int i = 1; i < thisHeight + 1; i++) {
					if (world.getBlockState(bp.down(i)).isAir()) {
						
						BlockState placementBlockState = PLANT_BLOCKSTATE;
						if (i == 1 && FIRST_BLOCKSTATE != null) {
							placementBlockState = FIRST_BLOCKSTATE;
						} else if (i == thisHeight && LAST_BLOCKSTATE != null) {
							placementBlockState = LAST_BLOCKSTATE;
						}
						
						world.setBlockState(bp.down(i), placementBlockState, 3);
					} else {
						if (i > 1 && LAST_BLOCKSTATE != null) {
							world.setBlockState(bp.down(i - 1), LAST_BLOCKSTATE, 3);
						}
						break;
					}
				}
			}
		}
	}
	
}
