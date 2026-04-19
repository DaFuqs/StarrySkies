package de.dafuqs.starryskies.registries;

import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.worldgen.*;
import de.dafuqs.starryskies.worldgen.dimension.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class StarryRegistryKeys {
	
	// Builtin Registries
	public static final ResourceKey<Registry<Sphere<?>>> SPHERE = of("sphere_type");
	public static final ResourceKey<Registry<SphereDecorator<?>>> SPHERE_DECORATOR = of("sphere_decorator");
	
	// Dynamic Registries
	public static final ResourceKey<Registry<ConfiguredSphereDecorator<?, ?>>> CONFIGURED_SPHERE_DECORATOR = of("configured_decorator");
	public static final ResourceKey<Registry<ConfiguredSphere<?, ?>>> CONFIGURED_SPHERE = of("configured_sphere");
	public static final ResourceKey<Registry<GenerationGroup>> GENERATION_GROUP = of("generation_group");
	public static final ResourceKey<Registry<SystemGenerator>> SYSTEM_GENERATOR = of("system_generator");
	
	private static <T> ResourceKey<Registry<T>> of(String name) {
		return ResourceKey.createRegistryKey(StarrySkies.id(name));
	}

}
