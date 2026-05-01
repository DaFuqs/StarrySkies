package de.dafuqs.starryskies.worldgen.dimension;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.registries.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.chunk.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.*;
import org.jspecify.annotations.*;

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
	protected @NonNull MapCodec<? extends ChunkGenerator> codec() {
		return CODEC;
	}

	@Override
	public void buildSurface(@NonNull WorldGenRegion region, @NonNull StructureManager structures, @NonNull RandomState noiseConfig, ChunkAccess chunk) {
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
	public void applyCarvers(@NonNull WorldGenRegion chunkRegion, long seed, @NonNull RandomState noiseConfig, @NonNull BiomeManager biomeAccess, @NonNull StructureManager structureAccessor, @NonNull ChunkAccess chunk) {
		// no carver
		// generate spheres
		for (PlacedSphere<?> sphere : systemGenerator.value().getSystem(chunk, seed, chunkRegion)) {
			if (sphere.isInChunk(chunk.getPos())) {
                // StarrySkies.LOGGER.debug("Generating sphere in chunk x:{} z:{} (StartX:{} StartZ:{}) {}", chunk.getPos().x(), chunk.getPos().z(), chunk.getPos().getMinBlockX(), chunk.getPos().getMinBlockZ(), sphere.getDescription(structureAccessor.registryAccess()));
				sphere.generate(chunk, chunkRegion);
                // StarrySkies.LOGGER.debug("Generation Finished.");
			}
		}
	}

	@Override
	public int getGenDepth() {
		return systemGenerator.value().getFloorHeight();
	}
	
	@Override
	public @NonNull CompletableFuture<ChunkAccess> fillFromNoise(@NonNull Blender blender, @NonNull RandomState noiseConfig, @NonNull StructureManager structureAccessor, @NonNull ChunkAccess chunk) {
		return CompletableFuture.completedFuture(chunk);
	}

	@Override
	public void spawnOriginalMobs(@NonNull WorldGenRegion chunkRegion) {
		ChunkPos chunkPos = chunkRegion.getCenter();
		Holder<Biome> biome = chunkRegion.getBiome(chunkPos.getWorldPosition().atY(chunkRegion.getMaxY()));
		WorldgenRandom chunkRandom = new WorldgenRandom(new LegacyRandomSource(RandomSupport.generateUniqueSeed()));
		chunkRandom.setDecorationSeed(chunkRegion.getSeed(), chunkPos.getMinBlockX(), chunkPos.getMinBlockZ());
		NaturalSpawner.spawnMobsForChunkGeneration(chunkRegion, biome, chunkPos, chunkRandom);
		
		for (PlacedSphere<?> sphere : systemGenerator.value().getSystem(chunkRegion, chunkRegion.getSeed(), chunkPos.x(), chunkPos.z())) {
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
	public int getBaseHeight(int x, int z, Heightmap.@NonNull Types heightmap, @NonNull LevelHeightAccessor world, @NonNull RandomState noiseConfig) {
		return systemGenerator.value().getFloorHeight();
	}
	
	@Override
	public void addDebugScreenInfo(@NonNull List<String> text, @NonNull RandomState noiseConfig, @NonNull BlockPos pos) {
	
	}

	@Override
	public @NonNull NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor world, @NonNull RandomState noiseConfig) {
		BlockState[] states = new BlockState[world.getHeight()];
		Arrays.fill(states, Blocks.AIR.defaultBlockState());
		return new NoiseColumn(world.getMinY(), states);
	}
	
	public SystemGenerator getSystemGenerator() {
		return this.systemGenerator.value();
	}
}