package de.dafuqs.starryskies.spheroids.decorators;

import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.dafuqs.starryskies.Support;
import de.dafuqs.starryskies.spheroids.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;


public class HugeHangingPlantDecorator extends HugePlantDecorator {
	
	public HugeHangingPlantDecorator(JsonObject data) throws CommandSyntaxException {
		super(data);
	}
	
	@Override
	public void decorate(StructureWorldAccess world, ChunkPos origin, Spheroid spheroid, Random random) {
		for (BlockPos bp : getBottomBlocks(world, origin, spheroid)) {
			
			if (random.nextFloat() < chance) {
				int thisHeight = Support.getRandomBetween(random, minHeight, maxHeight);
				for (int i = 1; i < thisHeight + 1; i++) {
					if (world.getBlockState(bp.down(i)).isAir()) {
						
						BlockState placementBlockState = block;
						if (i == 1 && firstBlock != null) {
							placementBlockState = firstBlock;
						} else if (i == thisHeight && lastBlock != null) {
							placementBlockState = lastBlock;
						}
						
						world.setBlockState(bp.down(i), placementBlockState, 3);
					} else {
						if (i > 1 && lastBlock != null) {
							world.setBlockState(bp.down(i - 1), lastBlock, 3);
						}
						break;
					}
				}
			}
		}
	}
	
}
