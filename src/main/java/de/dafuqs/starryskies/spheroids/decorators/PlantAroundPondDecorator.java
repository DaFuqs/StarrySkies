package de.dafuqs.starryskies.spheroids.decorators;

import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.dafuqs.starryskies.Support;
import de.dafuqs.starryskies.spheroids.SpheroidDecorator;
import de.dafuqs.starryskies.spheroids.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;

import java.util.Iterator;


public class PlantAroundPondDecorator extends SpheroidDecorator {
	
	private final BlockState block = Blocks.SUGAR_CANE.getDefaultState();
	private static final int pond_tries = 3;
	private static final float plant_chance = 0.5F;
	private static final int minHeight = 1;
	private static final int maxHeight = 3;
	
	public PlantAroundPondDecorator(JsonObject data) throws CommandSyntaxException {
		super(data);
	}
	
	@Override
	public void decorate(StructureWorldAccess world, ChunkPos origin, Spheroid spheroid, Random random) {
		for (BlockPos pos : getTopBlocks(world, origin, spheroid, random, pond_tries)) {
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
					if (random.nextFloat() < plant_chance) {
						BlockPos sugarCaneBlockPos = pos.up().offset(currentDirection);
						int sugarCaneHeight = Support.getRandomBetween(random, minHeight, maxHeight);
						for (int i = 0; i <= sugarCaneHeight; i++) {
							if (block.canPlaceAt(world, sugarCaneBlockPos.up(i))) {
								world.setBlockState(sugarCaneBlockPos.up(i), block, 3);
							}
						}
					}
				}
			}
		}
	}
}
