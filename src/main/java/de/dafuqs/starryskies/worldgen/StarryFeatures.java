package de.dafuqs.starryskies.worldgen;

import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.worldgen.dimension.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class StarryFeatures {

	public static ResourceLocation SPHERE_DECORATOR_FEATURE_ID = StarrySkies.id("sphere_decoration");
	public static Feature<NoneFeatureConfiguration> SPHERE_DECORATION;

	public static void initialize() {
		SPHERE_DECORATION = Registry.register(BuiltInRegistries.FEATURE, SPHERE_DECORATOR_FEATURE_ID, new SphereDecorationFeature(NoneFeatureConfiguration.CODEC));
	}
}