package de.dafuqs.starryskies.spheroids.decorators;

import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.spheroids.SpheroidDecorator;
import de.dafuqs.starryskies.spheroids.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;


public class CaveBottomDecorator extends SpheroidDecorator {
	
	private final BlockState block;
	private final float chance;
	
	public CaveBottomDecorator(JsonObject data) throws CommandSyntaxException {
		super(data);
		block = StarrySkies.getStateFromString(JsonHelper.getString(data, "block"));
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
