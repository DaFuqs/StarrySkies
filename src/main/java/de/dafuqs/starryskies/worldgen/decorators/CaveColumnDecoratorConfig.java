package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record CaveColumnDecoratorConfig(BlockStateProvider centerState,
                                        BlockStateProvider columnState) implements SphereDecoratorConfig {
	
	public static final Codec<CaveColumnDecoratorConfig> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					BlockStateProvider.CODEC.fieldOf("center_block").forGetter(decorator -> decorator.centerState),
					BlockStateProvider.CODEC.fieldOf("column_block").forGetter(decorator -> decorator.columnState)
			).apply(instance, CaveColumnDecoratorConfig::new)
	);
	
}