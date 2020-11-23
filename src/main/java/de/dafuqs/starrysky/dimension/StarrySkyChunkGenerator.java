package de.dafuqs.starrysky.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.spheroids.Spheroid;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.chunk.VerticalBlockSample;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;


public class StarrySkyChunkGenerator extends ChunkGenerator {

    public static StarrySkyChunkGenerator STARRY_SKY_CHUNK_GENERATOR;

    private final long seed;
    protected final ChunkGeneratorSettings settings;
    public static SystemGenerator systemGenerator;

    // from the config
    private final int FLOOR_HEIGHT;
    private final BlockState FLOOR_BLOCK_STATE;
    private final BlockState BOTTOM_BLOCK_STATE;

    public static final Codec<StarrySkyChunkGenerator> CODEC = RecordCodecBuilder.create(
            (instance) -> instance.group(
                    BiomeSource.CODEC.fieldOf("biome_source").forGetter((surfaceChunkGenerator) -> surfaceChunkGenerator.biomeSource),
                    Codec.LONG.fieldOf("seed").stable().forGetter((surfaceChunkGenerator) -> surfaceChunkGenerator.seed),
                    ChunkGeneratorSettings.REGISTRY_CODEC.fieldOf("settings").forGetter((surfaceChunkGenerator) -> () -> surfaceChunkGenerator.settings))
                .apply(instance, instance.stable(StarrySkyChunkGenerator::new)));

    public StarrySkyChunkGenerator(BiomeSource biomeSource, long l, Supplier<ChunkGeneratorSettings> chunkGeneratorType) {
        this(biomeSource, biomeSource, l, chunkGeneratorType.get());
    }

    private StarrySkyChunkGenerator(BiomeSource biomeSource, BiomeSource biomeSource2, long seed, ChunkGeneratorSettings chunkGeneratorType) {
        super(biomeSource, biomeSource2, new StructuresConfig(Optional.empty(), Collections.emptyMap()), seed); // no structures
        this.seed = seed;
        this.settings = chunkGeneratorType;

        systemGenerator = new SystemGenerator();

        // config values
        this.FLOOR_HEIGHT = StarrySkyCommon.STARRY_SKY_CONFIG.floorHeight;
        this.FLOOR_BLOCK_STATE = Registry.BLOCK.get(new Identifier(StarrySkyCommon.STARRY_SKY_CONFIG.floorBlock.toLowerCase())).getDefaultState();
        this.BOTTOM_BLOCK_STATE = Registry.BLOCK.get(new Identifier(StarrySkyCommon.STARRY_SKY_CONFIG.bottomBlock.toLowerCase())).getDefaultState();

        STARRY_SKY_CHUNK_GENERATOR = this;
    }

    @Override
    protected Codec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public ChunkGenerator withSeed(long seed) {
        StarrySkyChunkGenerator starrySkyChunkGenerator = new StarrySkyChunkGenerator(this.biomeSource.withSeed(seed), seed, () -> this.settings);
        STARRY_SKY_CHUNK_GENERATOR = starrySkyChunkGenerator;
        return starrySkyChunkGenerator;
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmapType) {
        return FLOOR_HEIGHT;
    }

    @Override
    public BlockView getColumnSample(int x, int z) {
        BlockState[] states = new BlockState[256];
        Arrays.fill(states, Blocks.AIR.getDefaultState());
        return new VerticalBlockSample(states);
    }

    @Override
    public void buildSurface(ChunkRegion region, Chunk chunk) {
        ChunkPos chunkPos = chunk.getPos();

        /*
        int i = chunkPos.x;
        int j = chunkPos.z;
        ChunkRandom chunkRandom = new ChunkRandom();
        chunkRandom.setTerrainSeed(i, j);
        */

        int chunkPosStartX = chunkPos.getStartX();
        int chunkPosStartZ = chunkPos.getStartZ();

        // Generate floor if set
        if (FLOOR_HEIGHT > 0) {
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
    public void populateNoise(WorldAccess world, StructureAccessor accessor, Chunk chunk) {
        // generate spheres
        placeSpheroids(world, chunk);
    }

    private BlockState getSeaBlock(int heightY) {
        if (heightY == 0) {
            return BOTTOM_BLOCK_STATE;
        } else {
            return FLOOR_BLOCK_STATE;
        }
    }

    @Override
    public int getWorldHeight() {
        return StarrySkyCommon.starryWorld.getHeight();
    }


    /**
     * For spawning specific mobs in certain places like structures.
     */
    /*@Override
    public List<SpawnSettings.SpawnEntry> getEntitySpawnList(Biome biome, StructureAccessor accessor, SpawnGroup group, BlockPos pos) {
        return super.getEntitySpawnList(biome, accessor, group, pos);
    }*/

    @Override
    public void populateEntities(ChunkRegion chunkRegion) {
        int xChunk = chunkRegion.getCenterChunkX();
        int zChunk = chunkRegion.getCenterChunkZ();
        int xCord = xChunk << 4;
        int zCord = zChunk << 4;

        List<Spheroid> localSystem = systemGenerator.getSystemAtChunkPos(xChunk, zChunk);
        ChunkRandom sharedseedrandom = new ChunkRandom();
        sharedseedrandom.setPopulationSeed(chunkRegion.getSeed(), xCord, zCord);

        ChunkPos chunkPos = new ChunkPos(xChunk, zChunk);
        for(Spheroid spheroid : localSystem) {
            if (spheroid.shouldPopulateEntities(chunkPos)) {
                spheroid.populateEntities(chunkPos, chunkRegion, sharedseedrandom);
            }
        }
    }

    @Override
    public int getSeaLevel() {
        return FLOOR_HEIGHT;
    }

    public void placeSpheroids(WorldAccess world, Chunk chunk) {
        ChunkRandom chunkRandom = new ChunkRandom(StarrySkyCommon.starryWorld.getSeed());
        chunkRandom.setTerrainSeed(chunk.getPos().getRegionX(), chunk.getPos().getRegionZ());

        List<Spheroid> localSystem = systemGenerator.getSystemAtChunkPos(chunk.getPos().x, chunk.getPos().z);

        for(Spheroid spheroid : localSystem) {
            if (spheroid.isInChunk(chunk.getPos())) {
                spheroid.generate(chunk);
                spheroid.decorate(world, chunk);
            }
        }
    }



}