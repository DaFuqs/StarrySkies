package de.dafuqs.starryskies.registries;

import de.dafuqs.starryskies.worldgen.*;
import de.dafuqs.starryskies.worldgen.dimension.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.neoforged.neoforge.registries.*;

public class StarryRegistries {

	public static final Registry<Sphere<?>> SPHERE = create(StarryRegistryKeys.SPHERE, true); // has to be synced for the sphere locate command suggestions
	public static final Registry<SphereDecorator<?>> SPHERE_DECORATOR = create(StarryRegistryKeys.SPHERE_DECORATOR, false);

	public static void registerRegistries(NewRegistryEvent event) {
		event.register(SPHERE);
		event.register(SPHERE_DECORATOR);
	}

	public static void registerDynamicRegistries(NewDatapackRegistryEvent event) {
		event.worldRegistry(StarryRegistryKeys.SYSTEM_GENERATOR, SystemGenerator.CODEC);
		event.worldRegistry(StarryRegistryKeys.GENERATION_GROUP, GenerationGroup.CODEC);
		event.worldRegistry(StarryRegistryKeys.CONFIGURED_SPHERE, ConfiguredSphere.CODEC);
		event.worldRegistry(StarryRegistryKeys.CONFIGURED_SPHERE_DECORATOR, ConfiguredSphereDecorator.CODEC);
	}

	private static <T> Registry<T> create(ResourceKey<? extends Registry<T>> key, boolean synced) {
		return new RegistryBuilder<>(key).sync(synced).create();
	}

}
