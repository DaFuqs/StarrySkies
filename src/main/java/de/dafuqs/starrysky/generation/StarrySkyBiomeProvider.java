package de.dafuqs.starrysky.generation;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.generation.layer.StarrySkyBiomeLayer;
import de.dafuqs.starrysky.generation.layer.StarrySkyBiomePillarLayer;
import de.dafuqs.starrysky.generation.layer.StarrySkyBiomeScalePillarLayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.SharedConstants;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.layer.ScaleLayer;
import net.minecraft.world.biome.layer.type.ParentedLayer;
import net.minecraft.world.biome.layer.util.*;
import net.minecraft.world.biome.source.BiomeLayerSampler;
import net.minecraft.world.biome.source.BiomeSource;
import de.dafuqs.starrysky.mixin.BiomeLayerSamplerAccessor;

import java.util.List;
import java.util.function.LongFunction;

public class StarrySkyBiomeProvider extends BiomeSource {
    public static void registerBiomeProvider() {
        Registry.register(Registry.BIOME_SOURCE, new Identifier(StarrySkyCommon.MOD_ID, "biome_source"), StarrySkyBiomeProvider.CODEC);
    }

    public static final Codec<StarrySkyBiomeProvider> CODEC =
            RecordCodecBuilder.create((instance) -> instance.group(
                    Codec.LONG.fieldOf("seed").stable().forGetter((StarrySkyBiomeProvider) -> StarrySkyBiomeProvider.SEED),
                    RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter((vanillaLayeredBiomeSource) -> vanillaLayeredBiomeSource.BIOME_REGISTRY))
            .apply(instance, instance.stable(StarrySkyBiomeProvider::new)));

    private final BiomeLayerSampler BIOME_SAMPLER;
    private final long SEED;
    private final Registry<Biome> BIOME_REGISTRY;
    public static Registry<Biome> layersBiomeRegistry;
    private static final List<RegistryKey<Biome>> BIOMES = ImmutableList.of(
            // all the files in data.starrysky.worldgen.biome
            // https://minecraft.gamepedia.com/Custom_world_generation#Biome
            RegistryKey.of(Registry.BIOME_KEY, new Identifier(StarrySkyCommon.MOD_ID, "starry_sky_biome"))
    );

    public StarrySkyBiomeProvider(long seed, Registry<Biome> biomeRegistry) {
        super(BIOMES.stream().map((registryKey) -> () -> (Biome)biomeRegistry.get(registryKey))); // is "get" correct?
        StarrySkyBiomeLayer.setSeed(seed);
        this.SEED = seed;
        this.BIOME_REGISTRY = biomeRegistry;
        StarrySkyBiomeProvider.layersBiomeRegistry = biomeRegistry;
        this.BIOME_SAMPLER = buildWorldProcedure(seed);
    }

    public static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> stack(long seed, ParentedLayer parent, LayerFactory<T> incomingArea, int count, LongFunction<C> contextFactory) {
        LayerFactory<T> LayerFactory = incomingArea;

        for (int i = 0; i < count; ++i) {
            LayerFactory = parent.create(contextFactory.apply(seed + (long) i), LayerFactory);
        }

        return LayerFactory;
    }

    public static BiomeLayerSampler buildWorldProcedure(long seed) {
        LayerFactory<CachingLayerSampler> layerFactory = build((salt) -> new CachingLayerContext(25, seed, salt));
        return new BiomeLayerSampler(layerFactory);
    }

    public static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> build(LongFunction<C> contextFactory) {
        LayerFactory<T> layer = StarrySkyBiomeLayer.INSTANCE.create(contextFactory.apply(200L));
        layer = StarrySkyBiomePillarLayer.INSTANCE.create(contextFactory.apply(1008L), layer);
        layer = StarrySkyBiomeScalePillarLayer.INSTANCE.create(contextFactory.apply(1055L), layer);
        layer = ScaleLayer.FUZZY.create(contextFactory.apply(2003L), layer);
        layer = ScaleLayer.FUZZY.create(contextFactory.apply(2523L), layer);
        return layer;
    }

    public Biome getBiomeForNoiseGen(int x, int y, int z) {
        return this.sample(this.BIOME_REGISTRY, x, z);
    }

    public Biome sample(Registry<Biome> registry, int i, int j) {
        int k = ((BiomeLayerSamplerAccessor)this.BIOME_SAMPLER).getSampler().sample(i, j);
        Biome biome = registry.get(k);
        if (biome == null) {
            if (SharedConstants.isDevelopment) {
                throw Util.throwOrPause(new IllegalStateException("Unknown biome id: " + k));
            } else {
                return registry.get(0);
            }
        } else {
            return biome;
        }
    }

    @Override
    protected Codec<? extends BiomeSource> getCodec() {
        return CODEC;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public BiomeSource withSeed(long seed) {
        return new StarrySkyBiomeProvider(seed, this.BIOME_REGISTRY);
    }
}
