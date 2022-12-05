package de.dafuqs.starryskies.spheroids.decorators;

import de.dafuqs.starryskies.spheroids.SpheroidDecorator;
import de.dafuqs.starryskies.spheroids.spheroids.Spheroid;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;

import java.util.Iterator;


public class SugarCanePondDecorator extends SpheroidDecorator {
	
	private static final Block SUGAR_CANE_BLOCK = Blocks.SUGAR_CANE;
	private static final BlockState SUGAR_CANE_BLOCKSTATE = Blocks.SUGAR_CANE.getDefaultState();
	private static final int WATER_POND_TRIES = 3;
	private static final int SUGAR_CANE_CHANCE = 2;
	
	@Override
	public void decorateSpheroid(StructureWorldAccess world, ChunkPos origin, Spheroid spheroid, Random random) {
		for(BlockPos pos : getTopBlocks(world, origin, spheroid, random, WATER_POND_TRIES)) {
			boolean canGenerate;
			// check if all 4 sides of the future water pond are solid
			canGenerate = true;
			Iterator<Direction> direction = Direction.Type.HORIZONTAL.iterator();
			while (direction.hasNext() && canGenerate) {
				BlockPos currentCheckBlockPos = pos.offset(direction.next());
				
				if (!world.getBlockState(currentCheckBlockPos).isSolidBlock(world, currentCheckBlockPos)
						|| !world.getBlockState(currentCheckBlockPos.up()).isAir()) {
					canGenerate = false;
				}
			}
			
			if (canGenerate) {
				world.setBlockState(pos, Blocks.WATER.getDefaultState(), 3);
				
				// place sugar cane with chance
				direction = Direction.Type.HORIZONTAL.iterator();
				while (direction.hasNext()) {
					Direction currentDirection = direction.next();
					if (random.nextInt(SUGAR_CANE_CHANCE) == 0) {
						BlockPos sugarCaneBlockPos = pos.up().offset(currentDirection);
						int sugarCaneHeight = random.nextInt(3);
						for (int i = 0; i <= sugarCaneHeight; i++) {
							if (SUGAR_CANE_BLOCK.canPlaceAt(SUGAR_CANE_BLOCKSTATE, world, sugarCaneBlockPos.up(i))) {
								world.setBlockState(sugarCaneBlockPos.up(i), SUGAR_CANE_BLOCKSTATE, 3);
							}
						}
					}
				}
			}
		}
	}
}
