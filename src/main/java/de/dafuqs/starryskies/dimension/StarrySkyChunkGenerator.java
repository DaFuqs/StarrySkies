package de.dafuqs.starryskies.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.spheroids.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.structure.StructureSet;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.dynamic.RegistryOps;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.RandomSeed;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.FixedBiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.Blender;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.noise.NoiseConfig;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class StarrySkyChunkGenerator extends ChunkGenerator {
	
	private final long seed;
	private final SystemGenerator systemGenerator;
	private final Registry<Biome> biomeRegistry;
	
	// Dimension Type values
	private final SpheroidDimensionType spheroidDimensionType;
	private final int floorHeight;
	private final BlockState floorBlockState;
	private final BlockState bottomBlockState;
	
	public static final Codec<StarrySkyChunkGenerator> CODEC = RecordCodecBuilder
			.create(instance -> createStructureSetRegistryGetter(instance)
					.and(instance.group(RegistryOps.createRegistryCodec(Registry.BIOME_KEY).forGetter(source -> source.biomeRegistry),
							Codecs.NONNEGATIVE_INT.fieldOf("settings").forGetter((generator -> generator.spheroidDimensionType.ordinal())),
							Codec.LONG.fieldOf("seed").stable().forGetter((generator) -> generator.seed)))
					.apply(instance, instance.stable(StarrySkyChunkGenerator::new)));
	
	public StarrySkyChunkGenerator(Registry<StructureSet> structureSets, Registry<Biome> biomeRegistry, int spheroidDimensionTypeOrdinal, long seed) {
		super(structureSets, Optional.empty(), createBiomeSource(biomeRegistry, spheroidDimensionTypeOrdinal));
		
		this.biomeRegistry = biomeRegistry;
		this.spheroidDimensionType = SpheroidDimensionType.values()[spheroidDimensionTypeOrdinal];
		this.seed = seed;
		
		this.systemGenerator = new SystemGenerator(spheroidDimensionType);
		this.floorBlockState = spheroidDimensionType.getFloorBlockState();
		this.bottomBlockState = spheroidDimensionType.getBottomBlockState();
		this.floorHeight = spheroidDimensionType.getFloorHeight();
	}
	
	private static FixedBiomeSource createBiomeSource(Registry<Biome> biomeRegistry, int spheroidDimensionTypeOrdinal) {
		switch (spheroidDimensionTypeOrdinal) {
			case 0 -> {
				return new FixedBiomeSource(biomeRegistry.getOrCreateEntry(StarrySkyBiomes.OVERWORLD_KEY));
			}
			case 1 -> {
				return new FixedBiomeSource(biomeRegistry.getOrCreateEntry(StarrySkyBiomes.NETHER_KEY));
			}
			default -> {
				return new FixedBiomeSource(biomeRegistry.getOrCreateEntry(StarrySkyBiomes.END_KEY));
			}
		}
	}
	
	@Override
	protected Codec<? extends ChunkGenerator> getCodec() {
		return CODEC;
	}
	
	@Override
	public void buildSurface(ChunkRegion region, StructureAccessor structures, NoiseConfig noiseConfig, Chunk chunk) {
		ChunkPos chunkPos = chunk.getPos();
		
		int chunkPosStartX = chunkPos.getStartX();
		int chunkPosStartZ = chunkPos.getStartZ();
		
		// Generate floor if set
		if (floorHeight > 0) {
			for (int y = 0; y <= getSeaLevel(); y++) {
				for (int x = 0; x < 16; x++) {
					for (int z = 0; z < 16; z++) {
						chunk.setBlockState(new BlockPos(chunkPosStartX + x, y, chunkPosStartZ + z), getSeaBlock(y), false);
					}
				}
			}
		}
	}
	
	@Override
	public void carve(ChunkRegion chunkRegion, long seed, NoiseConfig noiseConfig, BiomeAccess world, StructureAccessor structureAccessor, Chunk chunk, GenerationStep.Carver carverStep) {
		// no carver
	}
	
	private BlockState getSeaBlock(int heightY) {
		if (heightY == 0) {
			return bottomBlockState;
		} else {
			return floorBlockState;
		}
	}
	
	@Override
	public int getWorldHeight() {
		return StarrySkies.starryWorld.getHeight();
	}
	
	@Override
	public CompletableFuture<Chunk> populateNoise(Executor executor, Blender blender, NoiseConfig noiseConfig, StructureAccessor structureAccessor, Chunk chunk) {
		placeSpheroids(chunk); // generate spheres
		return CompletableFuture.completedFuture(chunk);
	}
	
	@Override
	public void populateEntities(@NotNull ChunkRegion chunkRegion) {
		ChunkPos chunkPos = chunkRegion.getCenterPos();
		RegistryEntry<Biome> biome = chunkRegion.getBiome(chunkPos.getStartPos().withY(chunkRegion.getTopY() - 1));
		ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(RandomSeed.getSeed()));
		chunkRandom.setPopulationSeed(chunkRegion.getSeed(), chunkPos.getStartX(), chunkPos.getStartZ());
		SpawnHelper.populateEntities(chunkRegion, biome, chunkPos, chunkRandom);
		
		List<Spheroid> localSystem = systemGenerator.getSystemAtChunkPos(chunkPos.x, chunkPos.z);
		for (Spheroid spheroid : localSystem) {
			spheroid.populateEntities(chunkPos, chunkRegion, chunkRandom);
		}
	}
	
	@Override
	public int getSeaLevel() {
		return floorHeight;
	}
	
	@Override
	public int getMinimumY() {
		return 0;
	}
	
	@Override
	public int getHeight(int x, int z, Heightmap.Type heightmap, HeightLimitView world, NoiseConfig noiseConfig) {
		return floorHeight;
	}
	
	@Override
	public void getDebugHudText(List<String> text, NoiseConfig noiseConfig, BlockPos pos) {
	
	}
	
	@Override
	public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world, NoiseConfig noiseConfig) {
		BlockState[] states = new BlockState[world.getHeight()];
		Arrays.fill(states, Blocks.AIR.getDefaultState());
		return new VerticalBlockSample(world.getBottomY(), states);
	}
	
	public void placeSpheroids(@NotNull Chunk chunk) {
		ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(StarrySkies.starryWorld.getSeed()));
		chunkRandom.setCarverSeed(StarrySkies.starryWorld.getSeed(), chunk.getPos().getRegionX(), chunk.getPos().getRegionZ());
		
		List<Spheroid> localSystem = systemGenerator.getSystemAtChunkPos(chunk.getPos().x, chunk.getPos().z);
		for (Spheroid spheroid : localSystem) {
			if (spheroid.isInChunk(chunk.getPos())) {
				StarrySkies.log(Level.DEBUG, "Generating spheroid in chunk x:" + chunk.getPos().x + " z:" + chunk.getPos().z + " (StartX:" + chunk.getPos().getStartX() + " StartZ:" + chunk.getPos().getStartZ() + ") " + spheroid.getDescription());
				spheroid.generate(chunk);
				StarrySkies.log(Level.DEBUG, "Finished.");
			}
		}
	}
	
}