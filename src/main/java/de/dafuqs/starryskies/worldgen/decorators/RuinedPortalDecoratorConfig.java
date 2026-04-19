package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public record RuinedPortalDecoratorConfig(ResourceKey<LootTable> lootTable) implements SphereDecoratorConfig {

	public static final Codec<RuinedPortalDecoratorConfig> CODEC = RecordCodecBuilder.create((instance) ->
			instance.group(
					ResourceKey.codec(Registries.LOOT_TABLE).fieldOf("loot_table").forGetter(config -> config.lootTable)
			).apply(instance, RuinedPortalDecoratorConfig::new));

}
