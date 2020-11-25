package de.dafuqs.starrysky.dimension;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.dimension.biome.StarrySkyBiomeProvider;
import me.shedaniel.cloth.api.dynamic.registry.v1.BiomesRegistry;
import me.shedaniel.cloth.api.dynamic.registry.v1.DynamicRegistryCallback;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

public class DecoratorFeatures {

    public static final Identifier SPHEROID_DECORATOR_ID = new Identifier(StarrySkyCommon.MOD_ID, "starry_sky_spheroid_decorator");
    public static final Feature<DefaultFeatureConfig> SPHEROID_DECORATOR = register(new SpheroidDecoratorFeature(DefaultFeatureConfig.CODEC), SPHEROID_DECORATOR_ID);
    public static final RegistryKey<ConfiguredFeature<?, ?>> SPHEROID_DECORATOR_KEY = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN, SPHEROID_DECORATOR_ID);

    public static <T extends FeatureConfig> Feature<T> register(Feature<T> feature, Identifier id) {
        return Registry.register(Registry.FEATURE, id, feature);
    }

    /**
     * Apply starry_sky_spheroid decorator
     * to the starry_sky_biome
     */
    public static void initialize() {
        DynamicRegistryCallback.callback(Registry.BIOME_KEY).register((manager, key, biome) -> {
            if(key.getValue().equals(StarrySkyBiomeProvider.STARRY_SKY_BIOME_IDENTIFIER)) {
                BiomesRegistry.registerFeature(manager, biome, GenerationStep.Feature.LAKES, SPHEROID_DECORATOR_KEY);
            }
        });
    }

}
