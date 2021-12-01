package de.dafuqs.starrysky.spheroid.decorators;

import com.google.common.collect.ImmutableList;
import de.dafuqs.starrysky.spheroid.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.GlowLichenFeature;
import net.minecraft.world.gen.feature.GlowLichenFeatureConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GlowLichenDecorator extends SpheroidDecorator {
    
    private final List<Block> placeableOn = ImmutableList.of(Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.DRIPSTONE_BLOCK, Blocks.CALCITE, Blocks.TUFF, Blocks.DEEPSLATE);
    private final GlowLichenFeatureConfig glowLichenFeatureConfig = new GlowLichenFeatureConfig(5, true, true, true, 0.5F, placeableOn);
    private final float chance;

    public GlowLichenDecorator(float chance) {
        this.chance = chance;
    }

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, ArrayList<BlockPos> decorationBlockPositions, Random random) {
        for(BlockPos bp : decorationBlockPositions) {
            if(random.nextFloat() < chance) {
                GlowLichenFeature.generate(world, bp, world.getBlockState(bp), glowLichenFeatureConfig, random, Arrays.asList(Direction.values()));
            }
        }
    }
}
