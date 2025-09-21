package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.entity.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;

public record MobSpawnerDecoratorConfig(RegistryKey<EntityType<?>> entityType,
										Position position) implements SphereDecoratorConfig {
	
	public enum Position implements StringIdentifiable {
		CENTER("center"),
		TOP_CENTER("top_center"),
		CAVE_FLOOR("cave_floor");
		
		private final String id;
		
		Position(final String id) {
			this.id = id;
		}
		
		@Override
		public String asString() {
			return id;
		}
	}
	
	public static final Codec<MobSpawnerDecoratorConfig> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					RegistryKey.createCodec(RegistryKeys.ENTITY_TYPE).fieldOf("entity_type").forGetter(decorator -> decorator.entityType),
					StringIdentifiable.createCodec(Position::values).fieldOf("position").forGetter(decorator -> decorator.position)
			).apply(instance, MobSpawnerDecoratorConfig::new)
	);
	
}
