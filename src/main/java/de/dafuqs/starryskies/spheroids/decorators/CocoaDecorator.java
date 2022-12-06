package de.dafuqs.starryskies.spheroids.decorators;

import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.dafuqs.starryskies.spheroids.SpheroidDecorator;
import de.dafuqs.starryskies.spheroids.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CocoaBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;

public class CocoaDecorator extends SpheroidDecorator {
	
	private final BlockState AIR = Blocks.CAVE_AIR.getDefaultState();
	private final BlockState COCOA;
	
	public CocoaDecorator(JsonObject data) throws CommandSyntaxException {
		super(data);
		this.COCOA = Blocks.COCOA.getDefaultState().with(CocoaBlock.AGE, 2); // 2 = fully grown
	}
	
	@Override
	public void decorate(StructureWorldAccess world, ChunkPos origin, Spheroid spheroid, Random random) {
		if(!spheroid.isCenterInChunk(origin)) {
			return;
		}
		
		for (int x = -2; x < 3; x++) {
			for (int y = -2; y < 3; y++) {
				for (int z = -2; z < 3; z++) {
					BlockPos bp = spheroid.getPosition().up(y).north(x).east(z);
					if (y == 0 && ((Math.abs(x) == 2) && Math.abs(z) == 1 || (Math.abs(z) == 2 && Math.abs(x) == 1))) {
						Direction direction;
						if (x == 0) {
							if (z < 0) {
								direction = Direction.WEST;
							} else {
								direction = Direction.EAST;
							}
							
						} else {
							if (x < 0) {
								direction = Direction.SOUTH;
							} else {
								direction = Direction.NORTH;
							}
						}
						world.setBlockState(bp, COCOA.with(HorizontalFacingBlock.FACING, direction), 3);
					} else {
						if (Math.abs(y) != 2 || (Math.abs(x) != 2 && Math.abs(z) != 2)) {
							world.setBlockState(bp, AIR, 3);
						}
					}
				}
			}
		}
	}

}
