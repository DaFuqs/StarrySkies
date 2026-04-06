package de.dafuqs.starryskies.worldgen;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;

public record SphereFeatureContext<FC extends SphereDecoratorConfig>(WorldGenLevel world, RandomSource random,
																	 ChunkPos chunkPos, PlacedSphere<?> sphere,
																	 FC config) {}
