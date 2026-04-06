package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.*;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public record MobSpawnerDecoratorConfig(ResourceKey<EntityType<?>> entityType,
                                        Position position) implements SphereDecoratorConfig {
	
	public enum Position implements StringRepresentable {
		CENTER("center"),
		TOP_CENTER("top_center"),
		CAVE_FLOOR("cave_floor");
		
		private final String id;
		
		Position(final String id) {
			this.id = id;
		}
		
		@Override
		public @NotNull String getSerializedName() {
			return id;
		}
	}
	
	public static final Codec<MobSpawnerDecoratorConfig> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					ResourceKey.codec(Registries.ENTITY_TYPE).fieldOf("entity_type").forGetter(decorator -> decorator.entityType),
					StringRepresentable.fromEnum(Position::values).fieldOf("position").forGetter(decorator -> decorator.position)
			).apply(instance, MobSpawnerDecoratorConfig::new)
	);
	
}
