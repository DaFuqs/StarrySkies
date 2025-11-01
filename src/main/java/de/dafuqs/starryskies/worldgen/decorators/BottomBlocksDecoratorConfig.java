package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.util.math.intprovider.*;
import net.minecraft.world.gen.stateprovider.*;

import java.util.*;

public record BottomBlocksDecoratorConfig(Optional<BlockStateProvider> topState, BlockStateProvider state,
										  Optional<BlockStateProvider> bottomState,
										  IntProvider height) implements SphereDecoratorConfig {
	
	public static final Codec<BottomBlocksDecoratorConfig> CODEC = RecordCodecBuilder.create((instance) ->
			instance.group(
					BlockStateProvider.TYPE_CODEC.optionalFieldOf("top_block").forGetter(decorator -> decorator.topState),
					BlockStateProvider.TYPE_CODEC.fieldOf("block").forGetter(decorator -> decorator.state),
					BlockStateProvider.TYPE_CODEC.optionalFieldOf("bottom_block").forGetter(decorator -> decorator.bottomState),
					IntProvider.createValidatingCodec(0, 8).fieldOf("height").forGetter(decorator -> decorator.height)
			).apply(instance, BottomBlocksDecoratorConfig::new));
	
}
