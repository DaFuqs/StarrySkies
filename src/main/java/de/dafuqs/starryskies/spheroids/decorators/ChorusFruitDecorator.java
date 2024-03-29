package de.dafuqs.starryskies.spheroids.decorators;

import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.dafuqs.starryskies.spheroids.SpheroidDecorator;
import de.dafuqs.starryskies.spheroids.spheroids.Spheroid;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;

public class ChorusFruitDecorator extends SpheroidDecorator {
	
	private final float chorusChance;
	
	public ChorusFruitDecorator(JsonObject data) throws CommandSyntaxException {
		super(data);
		this.chorusChance = 0.03F;
	}
	
	@Override
	public void decorate(StructureWorldAccess world, ChunkPos origin, Spheroid spheroid, Random random) {
		for (BlockPos bp : getTopBlocks(world, origin, spheroid)) {
			if (random.nextFloat() < chorusChance) {
				ChorusFlowerBlock.generate(world, bp.up(), random, 8);
			}
		}
	}
	
}
