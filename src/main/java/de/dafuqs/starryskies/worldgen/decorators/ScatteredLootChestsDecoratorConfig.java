package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.*;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jspecify.annotations.NonNull;

public record ScatteredLootChestsDecoratorConfig(ResourceKey<LootTable> lootTable, Position position,
                                                 float chancePerValidPosition) implements SphereDecoratorConfig {
	
	public enum Position implements StringRepresentable {
		TOP("top"),
		CAVE_FLOOR("cave_floor");
		
		private final String id;
		
		Position(final String id) {
			this.id = id;
		}
		
		@Override
		public @NonNull String getSerializedName() {
			return id;
		}
	}
	
	public static final Codec<ScatteredLootChestsDecoratorConfig> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					ResourceKey.codec(Registries.LOOT_TABLE).fieldOf("loot_table").forGetter(decorator -> decorator.lootTable),
					StringRepresentable.fromEnum(Position::values).fieldOf("position").forGetter(decorator -> decorator.position),
					ExtraCodecs.POSITIVE_FLOAT.fieldOf("chance_per_valid_position").forGetter(decorator -> decorator.chancePerValidPosition)
			).apply(instance, ScatteredLootChestsDecoratorConfig::new)
	);
	
}
