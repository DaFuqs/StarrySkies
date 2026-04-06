package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;

public record CenterPondDecoratorConfig(BlockState beachState, BlockState fluidState, ResourceKey<LootTable> lootTable,
                                        float lootTableChance) implements SphereDecoratorConfig {

	public static final Codec<CenterPondDecoratorConfig> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					BlockState.CODEC.fieldOf("beach_block").forGetter(decorator -> decorator.beachState),
					BlockState.CODEC.fieldOf("fluid_block").forGetter(decorator -> decorator.fluidState),
					ResourceKey.codec(Registries.LOOT_TABLE).fieldOf("loot_table").forGetter(decorator -> decorator.lootTable),
					Codec.FLOAT.fieldOf("loot_table_chance").forGetter(decorator -> decorator.lootTableChance)
			).apply(instance, CenterPondDecoratorConfig::new)
	);

}
