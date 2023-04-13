package de.dafuqs.starryskies.spheroids.decorators;

import com.google.gson.*;
import com.mojang.brigadier.exceptions.*;
import de.dafuqs.starryskies.spheroids.*;
import de.dafuqs.starryskies.spheroids.spheroids.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;


public class DripleafDecorator extends SpheroidDecorator {
	
	private static final Block DRIPLEAF_BLOCK = Blocks.BIG_DRIPLEAF;
	private static final BlockState DRIPLEAF_BLOCK_STATE = Blocks.BIG_DRIPLEAF.getDefaultState();
	private static final BlockState DRIPLEAF_STEM_BLOCK_STATE = Blocks.BIG_DRIPLEAF_STEM.getDefaultState();
	private static final BlockState WATER_BLOCK_STATE = Blocks.WATER.getDefaultState();
	private static final BlockState CLAY_BLOCK_STATE = Blocks.CLAY.getDefaultState();
	
	private final int tries;
	
	public DripleafDecorator(JsonObject data) throws CommandSyntaxException {
		super(data);
		this.tries = JsonHelper.getInt(data, "tries");
	}
	
	@Override
	public void decorate(StructureWorldAccess world, ChunkPos origin, Spheroid spheroid, Random random) {
		for (BlockPos bp : getCaveBottomBlocks(world, origin, spheroid, random, tries)) {
			boolean canGenerate;
			
			// check if all 4 sides of the future water pond are solid
			canGenerate = true;
			Iterator<Direction> direction = Direction.Type.HORIZONTAL.iterator();
			while (direction.hasNext() && canGenerate) {
				BlockPos currentCheckBlockPos = bp.offset(direction.next());
				
				if (!world.getBlockState(currentCheckBlockPos).isSolidBlock(world, currentCheckBlockPos) || !world.getBlockState(currentCheckBlockPos.up()).isAir()) {
					canGenerate = false;
				}
			}
			
			if (canGenerate) {
				// clay
				world.setBlockState(bp, CLAY_BLOCK_STATE, 3);
				
				// the dripleaf
				Direction randomDirection = Direction.Type.HORIZONTAL.random(random);
				int dripLeafHeight = random.nextInt(3) + 1;
				for (int i = 0; i <= dripLeafHeight; i++) {
					if (DRIPLEAF_BLOCK.canPlaceAt(DRIPLEAF_BLOCK_STATE.with(HorizontalFacingBlock.FACING, randomDirection), world, bp.up(i))) {
						if (i == dripLeafHeight) {
							world.setBlockState(bp.up(i), DRIPLEAF_BLOCK_STATE.with(HorizontalFacingBlock.FACING, randomDirection), 3);
						} else {
							world.setBlockState(bp.up(i), DRIPLEAF_STEM_BLOCK_STATE.with(HorizontalFacingBlock.FACING, randomDirection), 3);
						}
						
					}
				}
				
				// surrounding water
				direction = Direction.Type.HORIZONTAL.iterator();
				while (direction.hasNext()) {
					Direction currentDirection = direction.next();
					BlockPos offsetPos = bp.offset(currentDirection);
					if (world.getBlockState(offsetPos.up()).isAir()) {
						world.setBlockState(offsetPos, WATER_BLOCK_STATE, 3);
					}
				}
			}
		}
	}
}
