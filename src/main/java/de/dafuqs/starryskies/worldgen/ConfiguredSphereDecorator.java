package de.dafuqs.starryskies.worldgen;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.registries.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.level.WorldGenLevel;
import org.jetbrains.annotations.NotNull;

public record ConfiguredSphereDecorator<FC extends SphereDecoratorConfig, F extends SphereDecorator<FC>>(F feature,
																										 FC config) {

	public static final Codec<ConfiguredSphereDecorator<?, ?>> CODEC = StarryRegistries.SPHERE_DECORATOR.byNameCodec().dispatch((f) -> f.feature, SphereDecorator::getCodec);
	public static final Codec<Holder<ConfiguredSphereDecorator<?, ?>>> REGISTRY_CODEC = RegistryFileCodec.create(StarryRegistryKeys.CONFIGURED_SPHERE_DECORATOR, CODEC);
	
	public boolean generate(WorldGenLevel world, net.minecraft.util.RandomSource random, BlockPos pos, PlacedSphere<?> sphere) {
		return this.feature.generateIfValid(this.config, world, random, pos, sphere);
	}

	public @NotNull String toString() {
		return "Decorator: " + this.feature + ": " + this.config;
	}

	public F feature() {
		return this.feature;
	}

	public FC config() {
		return this.config;
	}
}