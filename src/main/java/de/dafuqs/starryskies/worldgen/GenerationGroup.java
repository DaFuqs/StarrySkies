package de.dafuqs.starryskies.worldgen;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.resources.ResourceLocation;

public record GenerationGroup(ResourceLocation systemGeneratorId, float weight) {

	public static final Codec<GenerationGroup> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					ResourceLocation.CODEC.fieldOf("system_generator").forGetter(generator -> generator.systemGeneratorId),
					Codec.FLOAT.fieldOf("weight").forGetter(generator -> generator.weight)
			).apply(instance, GenerationGroup::new)
	);
	
}
