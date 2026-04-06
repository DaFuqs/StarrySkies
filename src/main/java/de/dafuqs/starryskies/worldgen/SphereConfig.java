package de.dafuqs.starryskies.worldgen;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.*;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class SphereConfig {
	
	public static final MapCodec<SphereConfig> CONFIG_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
			FloatProvider.codec(1.0F, 64.0F).fieldOf("size").forGetter(sphereConfig -> sphereConfig.size),
			Codec.unboundedMap(ConfiguredSphereDecorator.REGISTRY_CODEC, Codec.FLOAT).fieldOf("decorators").forGetter(sphereConfig -> sphereConfig.decorators),
			SphereEntitySpawnDefinition.CODEC.listOf().fieldOf("spawns").forGetter(sphereConfig -> sphereConfig.spawns),
			Generation.CODEC.optionalFieldOf("generation").forGetter(sphereConfig -> Optional.ofNullable(sphereConfig.generation))
	).apply(instance, (size, decorators, spawns, generation) -> new SphereConfig(size, decorators, spawns, generation.orElse(null))));
	
	public record Generation(ResourceLocation group, float weight) {
		public static final Codec<Generation> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
						ResourceLocation.CODEC.fieldOf("group").forGetter(Generation::group),
						Codec.FLOAT.fieldOf("weight").forGetter(Generation::weight)
				).apply(instance, Generation::new)
		);
	}
	
	public final FloatProvider size;
	public final Map<Holder<ConfiguredSphereDecorator<?, ?>>, Float> decorators;
	public final List<SphereEntitySpawnDefinition> spawns;
	public final @Nullable Generation generation;

	public SphereConfig(FloatProvider size, Map<Holder<ConfiguredSphereDecorator<?, ?>>, Float> decorators, List<SphereEntitySpawnDefinition> spawns, @Nullable Generation generation) {
		this.size = size;
		this.decorators = decorators;
		this.spawns = spawns;
		this.generation = generation;
	}
	
	List<Holder<ConfiguredSphereDecorator<?, ?>>> selectDecorators(RandomSource random) {
		List<Holder<ConfiguredSphereDecorator<?, ?>>> result = new ArrayList<>();
		for (Map.Entry<Holder<ConfiguredSphereDecorator<?, ?>>, Float> entry : decorators.entrySet()) {
			if (random.nextFloat() < entry.getValue()) {
				result.add(entry.getKey());
			}
		}
		return result;
	}

	List<Tuple<EntityType<?>, Integer>> selectSpawns(RandomSource random) {
		List<Tuple<EntityType<?>, Integer>> result = new ArrayList<>();
		for (SphereEntitySpawnDefinition entry : spawns) {
			if (random.nextFloat() < entry.chance) {
				int count = Support.getRandomBetween(random, entry.minCount, entry.maxCount);
				result.add(new Tuple<>(entry.entityType, count));
			}
		}
		return result;
	}
	
}
