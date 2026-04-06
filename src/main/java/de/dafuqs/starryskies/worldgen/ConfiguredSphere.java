package de.dafuqs.starryskies.worldgen;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.registries.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ConfiguredSphere<S extends Sphere<SC>, SC extends SphereConfig> {
	
	public static final Codec<ConfiguredSphere<?, ?>> CODEC = StarryRegistries.SPHERE.byNameCodec().dispatch((configuredSphere) -> configuredSphere.sphere, Sphere::getCodec);

	protected final S sphere;
	protected final SC config;

	public ConfiguredSphere(S sphere, SC config) {
		this.sphere = sphere;
		this.config = config;
	}

	public SC config() {
		return config;
	}
	
	public float getSize(WorldgenRandom random) {
		return config.size.sample(random);
	}
	
	public List<Holder<ConfiguredSphereDecorator<?, ?>>> getDecorators(WorldgenRandom random) {
		return config.selectDecorators(random);
	}
	
	public List<Tuple<EntityType<?>, Integer>> getSpawns(WorldgenRandom random) {
		return config.selectSpawns(random);
	}
	
	public @Nullable SphereConfig.Generation getGenerationGroup() {
		return config.generation;
	}
	
	public PlacedSphere<?> generate(WorldgenRandom systemRandom, RegistryAccess registryManager, BlockPos pos, float radius) {
		return this.sphere.generate(this, this.config, systemRandom, registryManager, pos, radius);
	}
}
