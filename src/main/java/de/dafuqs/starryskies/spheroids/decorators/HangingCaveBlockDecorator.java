package de.dafuqs.starryskies.spheroids.decorators;

import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
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

public class HangingCaveBlockDecorator extends SpheroidDecorator {
	
	private final BlockState block;
	private final float chance;
	
	public HangingCaveBlockDecorator(JsonObject data) throws CommandSyntaxException {
		super(data);
		block = BlockArgumentParser.block(Registry.BLOCK, JsonHelper.getString(data, "block"), false).blockState();
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
