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
import net.minecraft.util.registry.*;
import net.minecraft.world.*;
import net.minecraft.world.gen.feature.*;

import java.util.*;

public class MultifaceGrowthDecorator extends SpheroidDecorator {
	
	private final GlowLichenFeatureConfig featureConfig;
	private final float chance;
	
	public MultifaceGrowthDecorator(JsonObject data) throws CommandSyntaxException {
		super(data);
		List<Block> placeableOn = new ArrayList<>();
		for (JsonElement e : data.get("placeable_on_blocks").getAsJsonArray()) {
			placeableOn.add(new BlockArgumentParser(new StringReader(e.getAsString()), true).parse(false).getBlockState().getBlock());
		}
		featureConfig = new GlowLichenFeatureConfig(20, false, true, true, 0.5F, RegistryEntryList.of(Block::getRegistryEntry, placeableOn));
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
							GlowLichenFeature.generate(world, currentPos, world.getBlockState(bp), featureConfig, random, Arrays.asList(Direction.values()));
						}
						break;
					}
				}
			}
		}
	}
}
