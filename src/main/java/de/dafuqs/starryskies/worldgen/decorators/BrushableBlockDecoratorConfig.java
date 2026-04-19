package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;

public record BrushableBlockDecoratorConfig(BlockState state, ResourceKey<LootTable> lootTable,
                                            float chance) implements SphereDecoratorConfig {
	
	public static final Codec<BrushableBlockDecoratorConfig> CODEC = RecordCodecBuilder.create((instance) ->
			instance.group(
					BlockState.CODEC.fieldOf("block").forGetter(decorator -> decorator.state),
					ResourceKey.codec(Registries.LOOT_TABLE).fieldOf("loot_table").forGetter((t) -> t.lootTable),
					Codec.floatRange(0.0F, 1.0F).fieldOf("chance").forGetter(decorator -> decorator.chance)
			).apply(instance, BrushableBlockDecoratorConfig::new));

}
