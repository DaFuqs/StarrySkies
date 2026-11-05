package de.dafuqs.starryskies.worldgen;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.registries.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.codec.*;
import net.minecraft.world.level.*;
import org.jspecify.annotations.*;

public record ConfiguredSphereDecorator<FC extends SphereDecoratorConfig, F extends SphereDecorator<FC>>(F feature,
																										 FC config) {

	public static final Codec<ConfiguredSphereDecorator<?, ?>> CODEC = StarryRegistries.SPHERE_DECORATOR.byNameCodec().dispatch((f) -> f.feature, SphereDecorator::getCodec);
    public static final Codec<Holder<ConfiguredSphereDecorator<?, ?>>> REGISTRY_CODEC = RegistryFileCodec.create(StarryRegistryKeys.CONFIGURED_SPHERE_DECORATOR, CODEC, true);
	
	public boolean generate(WorldGenLevel world, net.minecraft.util.RandomSource random, BlockPos pos, PlacedSphere<?> sphere) {
		return this.feature.generateIfValid(this.config, world, random, pos, sphere);
	}

	public @NonNull String toString() {
		return "Decorator: " + this.feature + ": " + this.config;
	}

	public F feature() {
		return this.feature;
	}

	public FC config() {
		return this.config;
	}
}