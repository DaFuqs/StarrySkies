package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;

public record XMarksTheSpotDecoratorConfig(ResourceKey<LootTable> lootTable,
                                           BlockState markingState) implements SphereDecoratorConfig {

	public static final Codec<XMarksTheSpotDecoratorConfig> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					ResourceKey.codec(Registries.LOOT_TABLE).fieldOf("loot_table").forGetter(decorator -> decorator.lootTable),
					BlockState.CODEC.fieldOf("marking_block").forGetter(decorator -> decorator.markingState)
			).apply(instance, XMarksTheSpotDecoratorConfig::new)
	);

}
