package de.dafuqs.starryskies.worldgen;

import com.mojang.serialization.*;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class DefaultSphereConfig implements FeatureConfiguration {
	public static final DefaultSphereConfig INSTANCE = new DefaultSphereConfig();
	public static final Codec<DefaultSphereConfig> CODEC = MapCodec.unitCodec(() -> INSTANCE);

	public DefaultSphereConfig() {
	}
}
