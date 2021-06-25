package de.dafuqs.starrysky.dimension;

import de.dafuqs.starrysky.StarrySkyCommon;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

public class DecoratorFeatures {

    public static final Identifier SPHEROID_DECORATOR_ID = new Identifier(StarrySkyCommon.MOD_ID, "starry_sky_spheroid_decorator");
    public static final Feature<DefaultFeatureConfig> SPHEROID_DECORATOR = register(new SpheroidDecoratorFeature(DefaultFeatureConfig.CODEC), SPHEROID_DECORATOR_ID);
    public static final RegistryKey<ConfiguredFeature<?, ?>> SPHEROID_DECORATOR_KEY = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, SPHEROID_DECORATOR_ID);

    public static <T extends FeatureConfig> Feature<T> register(Feature<T> feature, Identifier id) {
        return Registry.register(Registry.FEATURE, id, feature);
    }

    public static void initialize() {
    }
}
