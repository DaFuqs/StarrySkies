package de.dafuqs.starryskies.spheroids;

import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.dimension.SpheroidDecoratorFeature;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DecoratorFeatures {
	
	public static String SPHEROID_DECORATOR = "starry_skies_spheroid_decorator";
	public static Identifier SPHEROID_DECORATOR_ID = new Identifier(StarrySkies.MOD_ID, SPHEROID_DECORATOR);
	public static Feature<DefaultFeatureConfig> SPHEROID_DECORATOR_FEATURE;
	public static RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> SPHEROID_DECORATOR_CONFIGURED;
	public static RegistryEntry<PlacedFeature> SPHEROID_DECORATOR_PLACED;
	
	public static <T extends FeatureConfig> Feature<T> registerFeature(Feature<T> feature, Identifier id) {
		return Registry.register(Registry.FEATURE, id, feature);
	}
	
	protected static <FC extends FeatureConfig, F extends Feature<FC>> RegistryEntry<ConfiguredFeature<FC, ?>> registerConfiguredFeature(String id, F feature, FC featureConfig) {
		return registerConfiguredFeature(BuiltinRegistries.CONFIGURED_FEATURE, id, new ConfiguredFeature<>(feature, featureConfig));
	}
	
	private static <V extends T, T> RegistryEntry<V> registerConfiguredFeature(Registry<T> registry, String id, V value) {
		return (RegistryEntry<V>) BuiltinRegistries.add(registry, locate(id), value);
	}
	
	static RegistryEntry<PlacedFeature> registerPlacedFeature(String id, RegistryEntry<? extends ConfiguredFeature<?, ?>> feature, PlacementModifier... modifiers) {
		return BuiltinRegistries.add(BuiltinRegistries.PLACED_FEATURE, locate(id), new PlacedFeature(RegistryEntry.upcast(feature), List.of(modifiers)));
	}
	
	@Contract(pure = true)
	static @NotNull String locate(String name) {
		return StarrySkies.MOD_ID + ':' + name;
	}
	
	public static void initialize() {
		SPHEROID_DECORATOR_FEATURE = registerFeature(new SpheroidDecoratorFeature(DefaultFeatureConfig.CODEC), SPHEROID_DECORATOR_ID);
		SPHEROID_DECORATOR_CONFIGURED = registerConfiguredFeature("spheroid_decorator", SPHEROID_DECORATOR_FEATURE, new DefaultFeatureConfig());
		SPHEROID_DECORATOR_PLACED = registerPlacedFeature(SPHEROID_DECORATOR, SPHEROID_DECORATOR_CONFIGURED, BiomePlacementModifier.of());
	}
}