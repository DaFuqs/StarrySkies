package de.dafuqs.starryskies.spheroids;

import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.dimension.SpheroidDecoratorFeature;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class DecoratorFeatures {
	
	public static Identifier SPHEROID_DECORATOR_FEATURE_ID = StarrySkies.locate("spheroid_decorator");
	public static Feature<DefaultFeatureConfig> SPHEROID_DECORATOR_FEATURE;
	
	public static void initialize() {
		SPHEROID_DECORATOR_FEATURE = Registry.register(Registries.FEATURE, SPHEROID_DECORATOR_FEATURE_ID, new SpheroidDecoratorFeature(DefaultFeatureConfig.CODEC));
	}
}