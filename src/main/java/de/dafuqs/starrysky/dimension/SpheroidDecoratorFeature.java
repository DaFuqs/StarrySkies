package de.dafuqs.starrysky.dimension;

import com.mojang.serialization.Codec;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.List;
import java.util.Random;

public class SpheroidDecoratorFeature extends Feature {

    public SpheroidDecoratorFeature(Codec configCodec) {
        super(DefaultFeatureConfig.CODEC);
    }

    // the vanilla method
    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, FeatureConfig config) {
        if(chunkGenerator instanceof StarrySkyChunkGenerator) {
            SystemGenerator systemGenerator = SystemGenerator.getSystemGeneratorOfWorld(world.toServerWorld().getRegistryKey());
            List<Spheroid> localSystem = systemGenerator.getSystemAtChunkPos(blockPos.getX() / 16, blockPos.getZ() / 16);
            for(Spheroid spheroid : localSystem) {
                if(spheroid.shouldDecorate(blockPos)) {
                    spheroid.decorate(world, random);
                }
            }
        }
        return false;
    }
}
