package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.block.state.*;

public record VinesDecoratorConfig(BlockState block, float chance, IntProvider height) implements SphereDecoratorConfig {

	public static final Codec<VinesDecoratorConfig> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					BlockState.CODEC.fieldOf("block").forGetter(decorator -> decorator.block),
					Codec.FLOAT.fieldOf("chance").forGetter(decorator -> decorator.chance),
					IntProviders.CODEC.fieldOf("height").forGetter(decorator -> decorator.height)
			).apply(instance, VinesDecoratorConfig::new)
	);

}
