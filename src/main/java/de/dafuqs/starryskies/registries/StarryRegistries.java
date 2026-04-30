package de.dafuqs.starryskies.registries;

import de.dafuqs.starryskies.worldgen.*;
import de.dafuqs.starryskies.worldgen.dimension.SystemGenerator;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class StarryRegistries {

	public static final Registry<Sphere<?>> SPHERE = create(StarryRegistryKeys.SPHERE, true);
	public static final Registry<SphereDecorator<?>> SPHERE_DECORATOR = create(StarryRegistryKeys.SPHERE_DECORATOR, false);

	public static void registerRegistries(NewRegistryEvent event) {
		event.register(SPHERE);
		event.register(SPHERE_DECORATOR);
	}

	public static void registerDynamicRegistries(DataPackRegistryEvent.NewRegistry event) {
		event.dataPackRegistry(StarryRegistryKeys.SYSTEM_GENERATOR, SystemGenerator.CODEC);
		event.dataPackRegistry(StarryRegistryKeys.GENERATION_GROUP, GenerationGroup.CODEC);
		event.dataPackRegistry(StarryRegistryKeys.CONFIGURED_SPHERE, ConfiguredSphere.CODEC, ConfiguredSphere.CODEC); // Synced since it is used in the locate command
		event.dataPackRegistry(StarryRegistryKeys.CONFIGURED_SPHERE_DECORATOR, ConfiguredSphereDecorator.CODEC, ConfiguredSphereDecorator.CODEC);
	}

	private static <T> Registry<T> create(ResourceKey<? extends Registry<T>> key, boolean synced) {
		return new RegistryBuilder<>(key).sync(synced).create();
	}

}
