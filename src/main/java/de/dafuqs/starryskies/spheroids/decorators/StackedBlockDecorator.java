package de.dafuqs.starryskies.spheroids.decorators;

import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.dafuqs.starryskies.Support;
import de.dafuqs.starryskies.spheroids.SpheroidDecorator;
import de.dafuqs.starryskies.spheroids.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.command.argument.BlockArgumentParser;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.StructureWorldAccess;


public class StackedBlockDecorator extends SpheroidDecorator {
	
	private final BlockState block;
	private final float chance;
	private final int minHeight;
	private final int maxHeight;
	
	public StackedBlockDecorator(JsonObject data) throws CommandSyntaxException {
		super(data);
		block = BlockArgumentParser.block(Registry.BLOCK, JsonHelper.getString(data, "block"), false).blockState();
		chance = JsonHelper.getFloat(data, "chance");
		minHeight = JsonHelper.getInt(data, "min_height");
		maxHeight = JsonHelper.getInt(data, "max_height");
	}
	
	@Override
	public void decorate(StructureWorldAccess world, ChunkPos origin, Spheroid spheroid, Random random) {
		for (BlockPos bp : getTopBlocks(world, origin, spheroid)) {
			if(random.nextFloat() < chance) {
				int height = Support.getRandomBetween(random, minHeight, maxHeight);
				for (int i = 0; i < height; i++) {
					if (block.canPlaceAt(world, bp.up(i+1))) {
						world.setBlockState(bp.up(i+1), block, 3);
					}
				}
			}
		}
	}
}
