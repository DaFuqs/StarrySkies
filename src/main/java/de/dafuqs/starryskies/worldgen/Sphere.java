package de.dafuqs.starryskies.worldgen;

import com.mojang.serialization.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.WorldgenRandom;

public abstract class Sphere<SC extends SphereConfig> {
	
	private final MapCodec<ConfiguredSphere<Sphere<SC>, SC>> codec;
	
	public Sphere(Codec<SC> configCodec) {
		this.codec = configCodec.fieldOf("config").xmap((config) -> new ConfiguredSphere<>(this, config), ConfiguredSphere::config);
	}

	public abstract PlacedSphere<?> generate(ConfiguredSphere<? extends Sphere<SC>, SC> configuredSphere, SC config, WorldgenRandom random, WorldGenLevel level, BlockPos pos, float radius);
	
	public MapCodec<ConfiguredSphere<Sphere<SC>, SC>> getCodec() {
		return this.codec;
	}
	
}
