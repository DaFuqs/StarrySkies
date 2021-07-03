package de.dafuqs.starrysky.dimension.decorators.overworld;

import com.google.common.collect.ImmutableList;
import de.dafuqs.starrysky.dimension.DecorationMode;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.dimension.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.GlowLichenFeature;
import net.minecraft.world.gen.feature.GlowLichenFeatureConfig;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GlowLichenDecorator extends SpheroidDecorator {

    private final List<BlockState> placeableOn = ImmutableList.of(Blocks.STONE.getDefaultState(), Blocks.ANDESITE.getDefaultState(), Blocks.DIORITE.getDefaultState(), Blocks.GRANITE.getDefaultState(), Blocks.DRIPSTONE_BLOCK.getDefaultState(), Blocks.CALCITE.getDefaultState(), Blocks.TUFF.getDefaultState(), Blocks.DEEPSLATE.getDefaultState());
    private final GlowLichenFeatureConfig glowLichenFeatureConfig = new GlowLichenFeatureConfig(5, true, true, true, 0.5F, placeableOn);
    private final float chance;

    public GlowLichenDecorator(float chance) {
        this.chance = chance;
    }

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, BlockPos origin, Random random) {
        for(BlockPos bp : getDecorationPositionsInChunk(spheroid, world, origin, random, chance, DecorationMode.CAVE_BOTTOM)) {
            if(world.isAir(bp)) {
                GlowLichenFeature.generate(world, bp, world.getBlockState(bp), glowLichenFeatureConfig, random, Arrays.asList(Direction.values()));
            }
        }
        for(BlockPos bp : getDecorationPositionsInChunk(spheroid, world, origin, random, chance, DecorationMode.CAVE_TOP)) {
            if(world.isAir(bp)) {
                GlowLichenFeature.generate(world, bp, world.getBlockState(bp), glowLichenFeatureConfig, random, Arrays.asList(Direction.values()));
            }
        }
    }
}
