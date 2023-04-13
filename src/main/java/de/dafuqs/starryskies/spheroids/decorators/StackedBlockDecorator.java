package de.dafuqs.starryskies.spheroids.decorators;

import com.google.gson.*;
import com.mojang.brigadier.*;
import com.mojang.brigadier.exceptions.*;
import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.spheroids.*;
import de.dafuqs.starryskies.spheroids.spheroids.*;
import net.minecraft.block.*;
import net.minecraft.command.argument.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;


public class StackedBlockDecorator extends SpheroidDecorator {
	
	private final BlockState block;
	private final float chance;
	private final int minHeight;
	private final int maxHeight;
	
	public StackedBlockDecorator(JsonObject data) throws CommandSyntaxException {
		super(data);
		block = new BlockArgumentParser(new StringReader(JsonHelper.getString(data, "block")), false).parse(false).getBlockState();
		chance = JsonHelper.getFloat(data, "chance");
		minHeight = JsonHelper.getInt(data, "min_height");
		maxHeight = JsonHelper.getInt(data, "max_height");
	}
	
	@Override
	public void decorate(StructureWorldAccess world, ChunkPos origin, Spheroid spheroid, Random random) {
		for (BlockPos bp : getTopBlocks(world, origin, spheroid)) {
			if (random.nextFloat() < chance) {
				int height = Support.getRandomBetween(random, minHeight, maxHeight);
				for (int i = 0; i < height; i++) {
					if (block.canPlaceAt(world, bp.up(i + 1))) {
						world.setBlockState(bp.up(i + 1), block, 3);
					}
				}
			}
		}
	}
}
