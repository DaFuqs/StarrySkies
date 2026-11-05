package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.*;
import net.minecraft.core.registries.codec.RegistryCodecs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.MultifaceGrowthFeature;

public class MultifaceGrowthDecoratorConfig implements SphereDecoratorConfig {

	public static final Codec<MultifaceGrowthDecoratorConfig> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block").forGetter(decorator -> decorator.featureConfig.placeBlock()),
					RegistryCodecs.holderSet(Registries.BLOCK, true).fieldOf("placeable_on_blocks").forGetter(decorator -> decorator.featureConfig.canBePlacedOn()),
					Codec.FLOAT.fieldOf("chance").forGetter(decorator -> decorator.chance)
			).apply(instance, MultifaceGrowthDecoratorConfig::new)
	);

	public final MultifaceGrowthFeature featureConfig;
	public final float chance;

	public MultifaceGrowthDecoratorConfig(Block block, HolderSet<Block> placeableOn, float chance) {
		this.featureConfig = new MultifaceGrowthFeature(block, 20, false, true, true, 0.5F, placeableOn);
		this.chance = chance;
	}
}
