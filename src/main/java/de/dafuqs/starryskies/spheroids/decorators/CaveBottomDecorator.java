package de.dafuqs.starryskies.spheroids.decorators;

import com.google.gson.*;
import com.mojang.brigadier.*;
import com.mojang.brigadier.exceptions.*;
import de.dafuqs.starryskies.spheroids.*;
import de.dafuqs.starryskies.spheroids.spheroids.*;
import net.minecraft.block.*;
import net.minecraft.command.argument.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;


public class CaveBottomDecorator extends SpheroidDecorator {
	
	private final BlockState block;
	private final float chance;
	
	public CaveBottomDecorator(JsonObject data) throws CommandSyntaxException {
		super(data);
		block = new BlockArgumentParser(new StringReader(JsonHelper.getString(data, "block")), false).parse(false).getBlockState();
		chance = JsonHelper.getFloat(data, "chance");
	}
	
	@Override
	public void decorate(StructureWorldAccess world, ChunkPos origin, Spheroid spheroid, Random random) {
		for (BlockPos bp : getCaveBottomBlocks(world, origin, spheroid)) {
			if (random.nextFloat() < chance && block.canPlaceAt(world, bp.up())) {
				world.setBlockState(bp.up(), block, 3);
			}
		}
	}
}
