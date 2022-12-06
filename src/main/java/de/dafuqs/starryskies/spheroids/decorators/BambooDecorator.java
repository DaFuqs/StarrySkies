package de.dafuqs.starryskies.spheroids.decorators;

import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.dafuqs.starryskies.spheroids.SpheroidDecorator;
import de.dafuqs.starryskies.spheroids.spheroids.Spheroid;
import net.minecraft.block.BambooBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.BambooLeaves;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;

public class BambooDecorator extends SpheroidDecorator {
	
	private static final Block bambooBlock = Blocks.BAMBOO;
	private static final int BAMBOO_CHANCE = 13;
	private static final BlockState bambooBlockState = Blocks.BAMBOO.getDefaultState().with(BambooBlock.AGE, 0).with(BambooBlock.STAGE, 0);
	private static final BlockState bambooSaplingBlockState = Blocks.BAMBOO_SAPLING.getDefaultState();
	
	public BambooDecorator(JsonObject data) throws CommandSyntaxException {
		super(data);
	}
	
	@Override
	public void decorate(StructureWorldAccess world, ChunkPos origin, Spheroid spheroid, Random random) {
		for (BlockPos bp : getTopBlocks(world, origin, spheroid)) {
			int r = random.nextInt(BAMBOO_CHANCE);
			
			if (r == 0) {
				if (bambooBlock.canPlaceAt(bambooBlockState, world, bp.up())) {
					world.setBlockState(bp.up(), bambooSaplingBlockState, 3);
				}
			} else if (r < 8) {
				for (int i = 1; i < r; i++) {
					if (bambooBlock.canPlaceAt(bambooBlockState, world, bp.up(i))) {
						if (i == 3 && r < 5) {
							world.setBlockState(bp.up(i), bambooBlockState.with(BambooBlock.LEAVES, BambooLeaves.NONE), 3);
						} else if (i > 4) {
							world.setBlockState(bp.up(i), bambooBlockState.with(BambooBlock.LEAVES, BambooLeaves.LARGE), 3);
						} else if (i > 2) {
							world.setBlockState(bp.up(i), bambooBlockState.with(BambooBlock.LEAVES, BambooLeaves.SMALL), 3);
						} else {
							world.setBlockState(bp.up(i), bambooBlockState.with(BambooBlock.LEAVES, BambooLeaves.NONE), 3);
						}
					}
				}
			}
		}
	}
}
