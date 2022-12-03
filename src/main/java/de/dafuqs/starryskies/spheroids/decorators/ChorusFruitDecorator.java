package de.dafuqs.starryskies.spheroids.decorators;

import de.dafuqs.starryskies.spheroids.SpheroidDecorator;
import de.dafuqs.starryskies.spheroids.types.Spheroid;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;

import java.util.ArrayList;

public class ChorusFruitDecorator extends SpheroidDecorator {
	
	private static final int chorusChance = 30;
	
	@Override
	public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, ArrayList<BlockPos> decorationBlockPositions, Random random) {
		for (BlockPos bp : decorationBlockPositions) {
			int r = random.nextInt(chorusChance);
			
			if (r == 0) {
				ChorusFlowerBlock.generate(world, bp.up(), random, 8);
			}
		}
	}
	
	
}
