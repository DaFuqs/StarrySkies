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


public class HugePlantDecorator extends SpheroidDecorator {
	
	protected final BlockState block;
	protected final BlockState firstBlock;
	protected final BlockState lastBlock;
	protected final float chance;
	protected final int minHeight;
	protected final int maxHeight;
	
	/**
	 * A chance of 0 = 0%, 100 = 100%
	 */
	public HugePlantDecorator(JsonObject data) throws CommandSyntaxException {
		super(data);
		block = BlockArgumentParser.block(Registry.BLOCK, JsonHelper.getString(data, "block"), false).blockState();
		chance = JsonHelper.getFloat(data, "chance");
		if (JsonHelper.hasString(data, "first_block")) {
			firstBlock = BlockArgumentParser.block(Registry.BLOCK, JsonHelper.getString(data, "first_block"), false).blockState();
		} else {
			firstBlock = null;
		}
		if (JsonHelper.hasString(data, "last_block")) {
			lastBlock = BlockArgumentParser.block(Registry.BLOCK, JsonHelper.getString(data, "last_block"), false).blockState();
		} else {
			lastBlock = null;
		}
		minHeight = JsonHelper.getInt(data, "min_height");
		maxHeight = JsonHelper.getInt(data, "max_height");
	}
	
	@Override
	public void decorate(StructureWorldAccess world, ChunkPos origin, Spheroid spheroid, Random random) {
		for (BlockPos bp : getTopBlocks(world, origin, spheroid)) {
			BlockState posState = world.getBlockState(bp);
			if (!posState.isFullCube(world, bp)) {
				continue;
			}
			
			if (random.nextFloat() < chance) {
				int thisHeight = Support.getRandomBetween(random, minHeight, maxHeight);
				for (int i = 1; i < thisHeight + 1; i++) {
					if (world.getBlockState(bp.up(i)).isAir()) {
						
						BlockState placementBlockState = block;
						if (i == 1 && firstBlock != null) {
							placementBlockState = firstBlock;
						} else if (i == thisHeight && lastBlock != null) {
							placementBlockState = lastBlock;
						}
						
						world.setBlockState(bp.up(), placementBlockState, 3);
					} else {
						break;
					}
				}
			}
		}
	}
}
