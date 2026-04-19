package de.dafuqs.starryskies.worldgen;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;

public class SphereEntitySpawnDefinition {

	public static final Codec<SphereEntitySpawnDefinition> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("type").forGetter(def -> def.entityType),
					Codec.INT.fieldOf("min_count").forGetter(def -> def.minCount),
					Codec.INT.fieldOf("max_count").forGetter(def -> def.maxCount),
					Codec.FLOAT.fieldOf("chance").forGetter(def -> def.chance)
			).apply(instance, SphereEntitySpawnDefinition::new)
	);

	public EntityType<?> entityType;
	public float chance;
	public int minCount;
	public int maxCount;

	public SphereEntitySpawnDefinition(EntityType<?> entityType, int minCount, int maxCount, float chance) {
		this.entityType = entityType;
		this.minCount = minCount;
		this.maxCount = maxCount;
		this.chance = chance;
	}

}
