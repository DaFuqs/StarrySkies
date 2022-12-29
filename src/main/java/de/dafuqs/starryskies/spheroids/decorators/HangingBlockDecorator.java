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


public class HangingBlockDecorator extends SpheroidDecorator {
	
	private final BlockState block;
	private final float chance;
	
	public HangingBlockDecorator(JsonObject data) throws CommandSyntaxException {
		super(data);
		block = StarrySkies.getStateFromString(JsonHelper.getString(data, "block"));
		chance = JsonHelper.getFloat(data, "chance");
	}
	
	@Override
	public void decorate(StructureWorldAccess world, ChunkPos origin, Spheroid spheroid, Random random) {
		int spheroidY = spheroid.getPosition().getY();
		for (BlockPos bp : getBottomBlocks(world, origin, spheroid)) {
			BlockPos flippedBlockPos = bp.down((bp.getY() - spheroidY) * 2);
			
			if (world.getBlockState(flippedBlockPos.down()).isAir()) {
				if (random.nextFloat() < chance) {
					world.setBlockState(flippedBlockPos.down(), block, 3);
				}
			}
		}
	}
}
