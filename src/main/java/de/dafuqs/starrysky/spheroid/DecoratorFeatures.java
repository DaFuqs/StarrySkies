package de.dafuqs.starrysky.spheroid;

import de.dafuqs.starrysky.StarrySkyCommon;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.decorator.PlacementModifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.List;

public class DecoratorFeatures {

    public static Identifier SPHEROID_DECORATOR_ID = new Identifier(StarrySkyCommon.MOD_ID, "starry_sky_spheroid_decorator");
    public static Feature<DefaultFeatureConfig> SPHEROID_DECORATOR;

    public static <T extends FeatureConfig> Feature<T> register(Feature<T> feature, Identifier id) {
        return Registry.register(Registry.FEATURE, id, feature);
    }
    
    private static void registerConfiguredAndPlacedFeature(Identifier identifier, ConfiguredFeature<?, ?> configuredFeature, List<PlacementModifier> placementModifiers) {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, identifier, configuredFeature);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, identifier, configuredFeature.withPlacement(placementModifiers));
    }

    public static void initialize() {
        //SPHEROID_DECORATOR = register(new SpheroidDecoratorFeature(DefaultFeatureConfig.CODEC), SPHEROID_DECORATOR_ID);
    }
    
}