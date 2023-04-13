package de.dafuqs.starryskies.dimension;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.spheroids.spheroids.*;
import net.minecraft.block.*;
import net.minecraft.structure.*;
import net.minecraft.util.dynamic.*;
import net.minecraft.util.math.*;
import net.minecraft.util.registry.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.biome.source.*;
import net.minecraft.world.biome.source.util.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.chunk.*;
import net.minecraft.world.gen.random.*;
import org.apache.logging.log4j.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.concurrent.*;

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
			.create(instance -> method_41042(instance)
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
	public void buildSurface(ChunkRegion region, StructureAccessor structures, @NotNull Chunk chunk) {
		ChunkPos chunkPos = chunk.getPos();
		
		int chunkPosStartX = chunkPos.getStartX();
		int chunkPosStartZ = chunkPos.getStartZ();
		
		// Generate floor if set
		if (floorHeight > 0) {
			for (int y = 0; y <= getSeaLevel(); y++) {
				for (int x = 0; x < 16; x++) {
					for (int z = 0; z < 16; z++) {
						chunk.setBlockState(new BlockPos(chunkPosStartX+x, y, chunkPosStartZ+z), getSeaBlock(y), false);
					}
				}
			}
		}
	}
	
	@Override
	public void carve(ChunkRegion chunkRegion, long seed, BiomeAccess biomeAccess, StructureAccessor structureAccessor, Chunk chunk, GenerationStep.Carver generationStep) {
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
	public CompletableFuture<Chunk> populateNoise(Executor executor, Blender blender, StructureAccessor structureAccessor, Chunk chunk) {
		placeSpheroids(chunk); // generate spheres
		return CompletableFuture.completedFuture(chunk);
	}
	
	@Override
	public void populateEntities(@NotNull ChunkRegion chunkRegion) {
		ChunkPos chunkPos = chunkRegion.getCenterPos();
		RegistryEntry<Biome> biome = chunkRegion.getBiome(chunkPos.getStartPos().withY(chunkRegion.getTopY() - 1));
		ChunkRandom chunkRandom = new ChunkRandom(new AtomicSimpleRandom(RandomSeed.getSeed()));
		chunkRandom.setPopulationSeed(chunkRegion.getSeed(), chunkPos.getStartX(), chunkPos.getStartZ());
		SpawnHelper.populateEntities(chunkRegion, biome, chunkPos, chunkRandom);
		
		List<Spheroid> localSystem = systemGenerator.getSystemAtChunkPos(chunkPos.x, chunkPos.z);
		for(Spheroid spheroid : localSystem) {
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
	public int getHeight(int x, int z, Heightmap.Type heightmap, HeightLimitView world) {
		return floorHeight;
	}
	
	@Override
	public void getDebugHudText(List<String> text, BlockPos pos) {
	}
	
	public void placeSpheroids(@NotNull Chunk chunk) {
		ChunkRandom chunkRandom = new ChunkRandom(new SimpleRandom(StarrySkies.starryWorld.getSeed()));
		chunkRandom.setCarverSeed(StarrySkies.starryWorld.getSeed(), chunk.getPos().getRegionX(), chunk.getPos().getRegionZ());
		
		List<Spheroid> localSystem = systemGenerator.getSystemAtChunkPos(chunk.getPos().x, chunk.getPos().z);
		for (Spheroid spheroid : localSystem) {
			if (spheroid.isInChunk(chunk.getPos())) {
				StarrySkies.log(Level.DEBUG, "Generating spheroid in chunk x:" + chunk.getPos().x + " z:" + chunk.getPos().z + " (StartX:" + chunk.getPos().getStartX() + " StartZ:" + chunk.getPos().getStartZ() + ") " + spheroid.getDescription());
				spheroid.generate(chunk);
				StarrySkies.log(Level.DEBUG, "Generation Finished.");
			}
		}
	}
	
	@Override
	public ChunkGenerator withSeed(long seed) {
		return new StarrySkyChunkGenerator(this.field_37053, biomeRegistry, this.spheroidDimensionType.ordinal(), seed);
	}
	
	@Override
	public MultiNoiseUtil.MultiNoiseSampler getMultiNoiseSampler() {
		return MultiNoiseUtil.method_40443();
	}
	
	@Override
	public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world) {
		BlockState[] states = new BlockState[world.getHeight()];
		Arrays.fill(states, Blocks.AIR.getDefaultState());
		return new VerticalBlockSample(world.getBottomY(), states);
	}
	
}