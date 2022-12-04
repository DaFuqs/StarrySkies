package de.dafuqs.starryskies.spheroids.decorators;

import de.dafuqs.starryskies.spheroids.SpheroidDecorator;
import de.dafuqs.starryskies.spheroids.spheroids.Spheroid;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MultifaceGrowthBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.MultifaceGrowthFeature;
import net.minecraft.world.gen.feature.MultifaceGrowthFeatureConfig;

import java.util.ArrayList;
import java.util.Arrays;

public class GlowLichenDecorator extends SpheroidDecorator {
	
	private static final RegistryEntryList.Direct<Block> placeableOn = RegistryEntryList.of(Block::getRegistryEntry, Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.DRIPSTONE_BLOCK, Blocks.CALCITE, Blocks.TUFF, Blocks.DEEPSLATE);
	private static final MultifaceGrowthFeatureConfig glowLichenFeatureConfig = new MultifaceGrowthFeatureConfig((MultifaceGrowthBlock) Blocks.GLOW_LICHEN, 20, false, true, true, 0.5F, placeableOn);
	private final float chance;
	
	public GlowLichenDecorator(float chance) {
		this.chance = chance;
	}
	
	@Override
	public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, ArrayList<BlockPos> decorationBlockPositions, Random random) {
		int spheroidY = spheroid.getPosition().getY();
		
		for (BlockPos bp : decorationBlockPositions) {
			if (random.nextFloat() < chance) {
				BlockPos currentPos = new BlockPos(bp.getX(), spheroidY, bp.getZ());
				for (int i = 0; i < spheroid.getRadius(); i++) {
					if (!world.getBlockState(currentPos.up(i)).isAir()) {
						if (world.getBlockState(currentPos.up(i - 1)).isAir()) {
							MultifaceGrowthFeature.generate(world, currentPos, world.getBlockState(bp), glowLichenFeatureConfig, random, Arrays.asList(Direction.values()));
						}
						break;
					}
				}
			}
		}
	}
}
