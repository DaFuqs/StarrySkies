package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.loot.*;
import net.minecraft.registry.*;

public record CenterChestDecoratorConfig(RegistryKey<LootTable> lootTable) implements SphereDecoratorConfig {
	
	public static final Codec<CenterChestDecoratorConfig> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					RegistryKey.createCodec(RegistryKeys.LOOT_TABLE).fieldOf("loot_table").forGetter(decorator -> decorator.lootTable)
			).apply(instance, CenterChestDecoratorConfig::new)
	);
	
}
