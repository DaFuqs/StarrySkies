package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.world.gen.stateprovider.*;

public record SingleBlockProviderDecoratorConfig(BlockStateProvider state,
												 float chance) implements SphereDecoratorConfig {
	
	public static final Codec<SingleBlockProviderDecoratorConfig> CODEC = RecordCodecBuilder.create((instance) ->
			instance.group(
					BlockStateProvider.TYPE_CODEC.fieldOf("block").forGetter(decorator -> decorator.state),
					Codec.floatRange(0.0F, 1.0F).fieldOf("chance").forGetter(decorator -> decorator.chance)
			).apply(instance, SingleBlockProviderDecoratorConfig::new));
	
}
