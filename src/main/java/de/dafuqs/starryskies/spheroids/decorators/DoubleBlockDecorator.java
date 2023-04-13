package de.dafuqs.starryskies.spheroids.decorators;

import com.google.gson.*;
import com.mojang.brigadier.*;
import com.mojang.brigadier.exceptions.*;
import de.dafuqs.starryskies.spheroids.*;
import de.dafuqs.starryskies.spheroids.spheroids.*;
import net.minecraft.block.*;
import net.minecraft.block.enums.*;
import net.minecraft.command.argument.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;


public class DoubleBlockDecorator extends SpheroidDecorator {
	
	private final BlockState block;
	private final float chance;
	
	public DoubleBlockDecorator(JsonObject data) throws CommandSyntaxException {
		super(data);
		block = new BlockArgumentParser(new StringReader(JsonHelper.getString(data, "block")), false).parse(false).getBlockState();
		chance = JsonHelper.getFloat(data, "chance");
	}
	
	@Override
	public void decorate(StructureWorldAccess world, ChunkPos origin, Spheroid spheroid, Random random) {
		for (BlockPos bp : getTopBlocks(world, origin, spheroid)) {
			if (!world.getBlockState(bp).isAir() && world.getBlockState(bp.up()).isAir() && world.getBlockState(bp.up(2)).isAir()) {
				if (random.nextFloat() < chance) {
					world.setBlockState(bp.up(), block.with(TallFlowerBlock.HALF, DoubleBlockHalf.LOWER), 3);
					world.setBlockState(bp.up(2), block.with(TallFlowerBlock.HALF, DoubleBlockHalf.UPPER), 3);
				}
			}
		}
	}
}
