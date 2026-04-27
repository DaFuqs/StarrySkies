package de.dafuqs.starryskies.registries;

import de.dafuqs.starryskies.worldgen.*;
import de.dafuqs.starryskies.worldgen.dimension.SystemGenerator;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class StarryRegistries {

	public static final Registry<Sphere<?>> SPHERE = create(StarryRegistryKeys.SPHERE);
	public static final Registry<SphereDecorator<?>> SPHERE_DECORATOR = create(StarryRegistryKeys.SPHERE_DECORATOR);

	public static void register() {
		DynamicRegistries.register(StarryRegistryKeys.SYSTEM_GENERATOR, SystemGenerator.CODEC);
		DynamicRegistries.register(StarryRegistryKeys.GENERATION_GROUP, GenerationGroup.CODEC);
		DynamicRegistries.registerSynced(StarryRegistryKeys.CONFIGURED_SPHERE, ConfiguredSphere.CODEC); // Synced since it is used in the locate command
		DynamicRegistries.registerSynced(StarryRegistryKeys.CONFIGURED_SPHERE_DECORATOR, ConfiguredSphereDecorator.CODEC);
	}

	public static <T> Registry<T> create(ResourceKey<Registry<T>> key) {
		return FabricRegistryBuilder.create(key).attribute(RegistryAttribute.MODDED).buildAndRegister();
	}

}
