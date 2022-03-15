package de.dafuqs.starrysky.spheroid.decorators;

import de.dafuqs.starrysky.spheroid.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.GlowLichenFeature;
import net.minecraft.world.gen.feature.GlowLichenFeatureConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GlowLichenDecorator extends SpheroidDecorator {
    
    private final RegistryEntryList.Direct<Block> placeableOn = RegistryEntryList.of(Block::getRegistryEntry, Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.DRIPSTONE_BLOCK, Blocks.CALCITE, Blocks.TUFF, Blocks.DEEPSLATE);
    private final GlowLichenFeatureConfig glowLichenFeatureConfig = new GlowLichenFeatureConfig(5, true, true, true, 0.5F, placeableOn);
    private final float chance;

    public GlowLichenDecorator(float chance) {
        this.chance = chance;
    }

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, ArrayList<BlockPos> decorationBlockPositions, Random random) {
        int spheroidY = spheroid.getPosition().getY();
        for(BlockPos bp : decorationBlockPositions) {
            if(random.nextFloat() < chance) {
                BlockPos currentPos = new BlockPos(bp.getX(), spheroidY, bp.getZ());
                for (int i = 0; i < spheroid.getRadius(); i++) {
                    if (!world.getBlockState(currentPos.up(i)).isAir()) {
                        if (world.getBlockState(currentPos.up(i - 1)).isAir()) {
                            GlowLichenFeature.generate(world, currentPos, world.getBlockState(bp), glowLichenFeatureConfig, random, Arrays.asList(Direction.values()));
                        }
                        break;
                    }
                }
            }
        }
    }
}
