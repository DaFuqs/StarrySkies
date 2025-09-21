package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.loot.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;
import net.minecraft.util.dynamic.*;

public record ScatteredLootChestsDecoratorConfig(RegistryKey<LootTable> lootTable, Position position,
												 float chancePerValidPosition) implements SphereDecoratorConfig {
	
	public enum Position implements StringIdentifiable {
		TOP("top"),
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
	
	public static final Codec<ScatteredLootChestsDecoratorConfig> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					RegistryKey.createCodec(RegistryKeys.LOOT_TABLE).fieldOf("loot_table").forGetter(decorator -> decorator.lootTable),
					StringIdentifiable.createCodec(Position::values).fieldOf("position").forGetter(decorator -> decorator.position),
					Codecs.POSITIVE_FLOAT.fieldOf("chance_per_valid_position").forGetter(decorator -> decorator.chancePerValidPosition)
			).apply(instance, ScatteredLootChestsDecoratorConfig::new)
	);
	
}
