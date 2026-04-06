package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.*;

public record BottomBlocksDecoratorConfig(Optional<BlockStateProvider> topState, BlockStateProvider state,
                                          Optional<BlockStateProvider> bottomState,
                                          IntProvider height) implements SphereDecoratorConfig {
	
	public static final Codec<BottomBlocksDecoratorConfig> CODEC = RecordCodecBuilder.create((instance) ->
			instance.group(
					BlockStateProvider.CODEC.optionalFieldOf("top_block").forGetter(decorator -> decorator.topState),
					BlockStateProvider.CODEC.fieldOf("block").forGetter(decorator -> decorator.state),
					BlockStateProvider.CODEC.optionalFieldOf("bottom_block").forGetter(decorator -> decorator.bottomState),
					IntProvider.codec(0, 8).fieldOf("height").forGetter(decorator -> decorator.height)
			).apply(instance, BottomBlocksDecoratorConfig::new));
	
}
