package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.registries.*;
import net.minecraft.util.*;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.block.*;

public record SpeleothemDecoratorConfig(Block block, IntProvider height,
                                        float chance) implements SphereDecoratorConfig {

	public static final Codec<SpeleothemDecoratorConfig> CODEC = RecordCodecBuilder.create((instance) ->
			instance.group(
					BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block").forGetter(decorator -> decorator.block),
					IntProviders.POSITIVE_CODEC.fieldOf("height").forGetter(decorator -> decorator.height),
					ExtraCodecs.POSITIVE_FLOAT.fieldOf("chance").forGetter(decorator -> decorator.chance)
			).apply(instance, SpeleothemDecoratorConfig::new));
	
}
