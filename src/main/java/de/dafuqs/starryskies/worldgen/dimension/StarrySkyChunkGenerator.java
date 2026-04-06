package de.dafuqs.starryskies.worldgen.dimension;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.registries.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.RandomSupport;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.blending.Blender;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.concurrent.*;

public class StarrySkyChunkGenerator extends ChunkGenerator {

	public static final MapCodec<StarrySkyChunkGenerator> CODEC = RecordCodecBuilder.mapCodec(
			(instance) -> instance.group(
					BiomeSource.CODEC.fieldOf("biome_source").forGetter((generator) -> generator.biomeSource),
					RegistryFileCodec.create(StarryRegistryKeys.SYSTEM_GENERATOR, SystemGenerator.CODEC).fieldOf("system_generator").forGetter((generator) -> generator.systemGenerator)
			).apply(instance, StarrySkyChunkGenerator::new));

	protected final Holder<SystemGenerator> systemGenerator;

	public StarrySkyChunkGenerator(BiomeSource biomeSource, Holder<SystemGenerator> systemGenerator) {
		super(biomeSource);
		this.systemGenerator = systemGenerator;
	}

	@Override
	protected @NotNull MapCodec<? extends ChunkGenerator> codec() {
		return CODEC;
	}

	@Override
	public void buildSurface(WorldGenRegion region, StructureManager structures, RandomState noiseConfig, ChunkAccess chunk) {
		ChunkPos chunkPos = chunk.getPos();

		int chunkPosStartX = chunkPos.getMinBlockX();
		int chunkPosStartZ = chunkPos.getMinBlockZ();

		// Generate floor if set
		if (systemGenerator.value().getFloorHeight() > 0) {
			for (int y = 0; y < getSeaLevel(); y++) {
				for (int x = 0; x < 16; x++) {
					for (int z = 0; z < 16; z++) {
						chunk.setBlockState(new BlockPos(chunkPosStartX + x, y, chunkPosStartZ + z), systemGenerator.value().getSeaBlock(y));
					}
				}
			}
		}
	}
	
	@Override
	public void applyCarvers(WorldGenRegion chunkRegion, long seed, RandomState noiseConfig, BiomeManager biomeAccess, StructureManager structureAccessor, ChunkAccess chunk) {
		// no carver
		// generate spheres
		for (PlacedSphere<?> sphere : systemGenerator.value().getSystem(chunk, seed, structureAccessor.registryAccess())) {
			if (sphere.isInChunk(chunk.getPos())) {
				StarrySkies.LOGGER.debug("Generating sphere in chunk x:{} z:{} (StartX:{} StartZ:{}) {}", chunk.getPos().x, chunk.getPos().z, chunk.getPos().getMinBlockX(), chunk.getPos().getMinBlockZ(), sphere.getDescription(structureAccessor.registryAccess()));
				sphere.generate(chunk, structureAccessor.registryAccess());
				StarrySkies.LOGGER.debug("Generation Finished.");
			}
		}
	}

	@Override
	public int getGenDepth() {
		return systemGenerator.value().getFloorHeight();
	}
	
	@Override
	public @NotNull CompletableFuture<ChunkAccess> fillFromNoise(Blender blender, RandomState noiseConfig, StructureManager structureAccessor, ChunkAccess chunk) {
		return CompletableFuture.completedFuture(chunk);
	}

	@Override
	public void spawnOriginalMobs(@NotNull WorldGenRegion chunkRegion) {
		ChunkPos chunkPos = chunkRegion.getCenter();
		Holder<Biome> biome = chunkRegion.getBiome(chunkPos.getWorldPosition().atY(chunkRegion.getMaxY()));
		WorldgenRandom chunkRandom = new WorldgenRandom(new LegacyRandomSource(RandomSupport.generateUniqueSeed()));
		chunkRandom.setDecorationSeed(chunkRegion.getSeed(), chunkPos.getMinBlockX(), chunkPos.getMinBlockZ());
		NaturalSpawner.spawnMobsForChunkGeneration(chunkRegion, biome, chunkPos, chunkRandom);
		
		for (PlacedSphere<?> sphere : systemGenerator.value().getSystem(chunkRegion.getLevel(), chunkRegion.getSeed(), chunkPos.x, chunkPos.z)) {
			sphere.populateEntities(chunkPos, chunkRegion, chunkRandom);
		}
	}

	@Override
	public int getSeaLevel() {
		return systemGenerator.value().getFloorHeight();
	}

	@Override
	public int getMinY() {
		return 0;
	}

	@Override
	public int getBaseHeight(int x, int z, Heightmap.Types heightmap, LevelHeightAccessor world, RandomState noiseConfig) {
		return systemGenerator.value().getFloorHeight();
	}
	
	@Override
	public void addDebugScreenInfo(List<String> text, RandomState noiseConfig, BlockPos pos) {
	
	}

	@Override
	public @NotNull NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor world, RandomState noiseConfig) {
		BlockState[] states = new BlockState[world.getHeight()];
		Arrays.fill(states, Blocks.AIR.defaultBlockState());
		return new NoiseColumn(world.getMinY(), states);
	}
	
	public SystemGenerator getSystemGenerator() {
		return this.systemGenerator.value();
	}
}