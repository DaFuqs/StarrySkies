package de.dafuqs.starryskies.worldgen;

import com.mojang.serialization.*;
import net.minecraft.world.gen.feature.*;

public class DefaultSphereConfig implements FeatureConfig {
	
	public static final DefaultSphereConfig INSTANCE = new DefaultSphereConfig();
	public static final Codec<DefaultSphereConfig> CODEC = MapCodec.unitCodec(INSTANCE);

	public DefaultSphereConfig() {
	}
}
