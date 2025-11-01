package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.block.*;
import net.minecraft.registry.*;
import net.minecraft.util.dynamic.*;
import net.minecraft.util.math.intprovider.*;

public record PointedDripstoneDecoratorConfig(Block block, IntProvider height,
											  float chance) implements SphereDecoratorConfig {
	
	public static final Codec<PointedDripstoneDecoratorConfig> CODEC = RecordCodecBuilder.create((instance) ->
			instance.group(
					Registries.BLOCK.getCodec().fieldOf("block").forGetter(decorator -> decorator.block),
					IntProvider.POSITIVE_CODEC.fieldOf("height").forGetter(decorator -> decorator.height),
					Codecs.POSITIVE_FLOAT.fieldOf("chance").forGetter(decorator -> decorator.chance)
			).apply(instance, PointedDripstoneDecoratorConfig::new));
	
}
