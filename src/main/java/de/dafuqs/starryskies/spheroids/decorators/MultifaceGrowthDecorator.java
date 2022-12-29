package de.dafuqs.starryskies.spheroids.decorators;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.spheroids.SpheroidDecorator;
import de.dafuqs.starryskies.spheroids.spheroids.Spheroid;
import net.minecraft.block.Block;
import net.minecraft.block.MultifaceGrowthBlock;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.MultifaceGrowthFeature;
import net.minecraft.world.gen.feature.MultifaceGrowthFeatureConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultifaceGrowthDecorator extends SpheroidDecorator {
	
	private final MultifaceGrowthFeatureConfig featureConfig;
	private final float chance;
	
	public MultifaceGrowthDecorator(JsonObject data) throws CommandSyntaxException {
		super(data);
		Block block = StarrySkies.getBlockFromString(JsonHelper.getString(data, "block"));
		List<Block> placeableOn = new ArrayList<>();
		for (JsonElement e : data.get("placeable_on_blocks").getAsJsonArray()) {
			Block block2 = StarrySkies.getBlockFromString(e.getAsString());
			placeableOn.add(block2);
		}
		featureConfig = new MultifaceGrowthFeatureConfig((MultifaceGrowthBlock) block, 20, false, true, true, 0.5F, RegistryEntryList.of(Block::getRegistryEntry, placeableOn));
		chance = JsonHelper.getFloat(data, "chance");
	}
	
	@Override
	public void decorate(StructureWorldAccess world, ChunkPos origin, Spheroid spheroid, Random random) {
		int spheroidY = spheroid.getPosition().getY();
		
		for (BlockPos bp : getCaveBottomBlocks(world, origin, spheroid)) {
			if (random.nextFloat() < chance) {
				BlockPos currentPos = new BlockPos(bp.getX(), spheroidY, bp.getZ());
				for (int i = 0; i < spheroid.getRadius(); i++) {
					if (!world.getBlockState(currentPos.up(i)).isAir()) {
						if (world.getBlockState(currentPos.up(i - 1)).isAir()) {
							MultifaceGrowthFeature.generate(world, currentPos, world.getBlockState(bp), featureConfig, random, Arrays.asList(Direction.values()));
						}
						break;
					}
				}
			}
		}
	}
}
