package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.loot.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;

public record LootChestDecoratorConfig(RegistryKey<LootTable> lootTable,
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
	
	public static final Codec<LootChestDecoratorConfig> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					RegistryKey.createCodec(RegistryKeys.LOOT_TABLE).fieldOf("loot_table").forGetter(decorator -> decorator.lootTable),
					StringIdentifiable.createCodec(Position::values).fieldOf("position").forGetter(decorator -> decorator.position)
			).apply(instance, LootChestDecoratorConfig::new)
	);
	
}
