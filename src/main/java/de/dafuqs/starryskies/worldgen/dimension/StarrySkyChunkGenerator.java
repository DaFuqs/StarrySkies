package de.dafuqs.starryskies.worldgen.dimension;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.registries.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.codec.*;
import net.minecraft.server.level.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.chunk.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.*;
import net.minecraft.world.level.levelgen.densityfunction.*;
import org.jspecify.annotations.*;

import java.util.*;
import java.util.concurrent.*;

public class StarrySkyChunkGenerator extends ChunkGenerator {

	public static final MapCodec<StarrySkyChunkGenerator> CODEC = RecordCodecBuilder.mapCodec(
			(instance) -> instance.group(
					BiomeSource.CODEC.fieldOf("biome_source").forGetter((generator) -> generator.biomeSource),
                    RegistryFileCodec.create(StarryRegistryKeys.SYSTEM_GENERATOR, SystemGenerator.CODEC, true).fieldOf("system_generator").forGetter((generator) -> generator.systemGenerator)
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

    @SuppressWarnings("UnusedReturnValue")
    public ChunkAccess buildSurface(@NonNull ChunkAccess chunk) {
		// Generate floor if set

        // for full correctness use chunk's min Y instead of assuming 0
        final int minY = chunk.getMinY();
        final int dimSeaLevel = getSeaLevel();
        if (dimSeaLevel < minY) return chunk;

        final SystemGenerator sysGen = systemGenerator.value();
        final Heightmap oceanFloor = chunk.getOrCreateHeightmapUnprimed(Heightmap.Types.OCEAN_FLOOR_WG);
        final Heightmap worldSurface = chunk.getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE_WG);

        final int maxY = chunk.getMaxY();
        final int seaLevel = Math.min(dimSeaLevel, maxY);
        final LevelChunkSection[] sections = chunk.getSections();
        final int sectionCount = 1 + (seaLevel - (minY & -16)) >> 4;

        for (int i = 0, sy = minY; i < sectionCount; i++, sy += 16) {
            final int msy = Math.min(seaLevel - sy + 1, 16);
            final LevelChunkSection section = sections[i];
            section.acquire();
            for (int y = 0; y < msy; y++) {
                final BlockState state = sysGen.getSeaBlock(sy + y);
                for (int x = 0; x < 16; x++)
					for (int z = 0; z < 16; z++) {
                        section.setBlockState(x, y, z, state, false);
                        oceanFloor.update(x, y, z, state);
                        worldSurface.update(x, y, z, state);
					}
            }
            section.release();
        }
        return chunk;
    }

    @Override
    public int getGenDepth() {
        return systemGenerator.value().getFloorHeight();
	}

	@Override
    public @NonNull CompletableFuture<ChunkAccess> buildTerrain(@NonNull ChunkAccess chunk, @NonNull Blender blender, @NonNull RandomState randomState, @NonNull StructureManager structureManager, @NonNull BiomeManager biomeManager, @Nullable WorldGenRegion chunkRegion, @NonNull Set<Holder<Biome>> possibleBiomes) {
        buildSurface(chunk);
		// generate spheres
        // TODO: is keeping this in TERRAIN better than moving to FEATURES?
        // randomState.getSeed() == chunkRegion.getSeed() [== chunkRegion.getLevel().getSeed()]
        // the randomState seed is "intended" to be used here, but it's safe to keep using the chunkRegion seed anyway
        for (PlacedSphere<?> sphere : systemGenerator.value().getSystem(chunk, chunkRegion.getSeed(), chunkRegion)) {
			if (sphere.isInChunk(chunk.getPos())) {
                // StarrySkies.LOGGER.debug("Generating sphere in chunk x:{} z:{} (StartX:{} StartZ:{}) {}", chunk.getPos().x(), chunk.getPos().z(), chunk.getPos().getMinBlockX(), chunk.getPos().getMinBlockZ(), sphere.getDescription(structureAccessor.registryAccess()));
				sphere.generate(chunk, chunkRegion);
                // StarrySkies.LOGGER.debug("Generation Finished.");
			}
		}
        // TODO: if sphere gen is moved out of the TERRAIN stage, make buildTerrain async
        // return CompletableFuture.supplyAsync(() -> buildSurface(chunk), Util.backgroundExecutor().forName("starrySkiesTerrain"));
		return CompletableFuture.completedFuture(chunk);
	}

	@Override
	public void spawnOriginalMobs(@NonNull WorldGenRegion chunkRegion) {
		ChunkPos chunkPos = chunkRegion.getCenter();
        BlockPos sourcePos = chunkPos.getWorldPosition().atY(chunkRegion.getMaxY());
		WorldgenRandom chunkRandom = new WorldgenRandom(new LegacyRandomSource(RandomSupport.generateUniqueSeed()));
		chunkRandom.setDecorationSeed(chunkRegion.getSeed(), chunkPos.getMinBlockX(), chunkPos.getMinBlockZ());
        NaturalSpawner.spawnMobsForChunkGeneration(chunkRegion, sourcePos, chunkPos, chunkRandom);
		
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
    public void addDebugScreenInfo(@NonNull List<String> text, @NonNull RandomState noiseConfig, @NonNull BlockPos pos, @NonNull SamplerContext samplerContext) {
	
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