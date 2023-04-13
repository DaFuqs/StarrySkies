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

public class HangingCaveBlockDecorator extends SpheroidDecorator {
	
	private final BlockState block;
	private final float chance;
	
	public HangingCaveBlockDecorator(JsonObject data) throws CommandSyntaxException {
		super(data);
		block = new BlockArgumentParser(new StringReader(JsonHelper.getString(data, "block")), false).parse(false).getBlockState();
		chance = JsonHelper.getFloat(data, "chance");
	}
	
	@Override
	public void decorate(StructureWorldAccess world, ChunkPos origin, Spheroid spheroid, Random random) {
		for (BlockPos bp : getBottomBlocks(world, origin, spheroid)) {
			if (!world.getBlockState(bp).isAir() && random.nextFloat() < chance) {
				if (world.getBlockState(bp.down()).isAir()) {
					world.setBlockState(bp.down(), block, 3);
				}
				break;
			}
		}
	}
}
