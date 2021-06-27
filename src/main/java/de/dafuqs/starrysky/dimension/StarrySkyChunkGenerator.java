package de.dafuqs.starrysky.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.*;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.feature.StructureFeature;
import org.apache.logging.log4j.Level;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;


public class StarrySkyChunkGenerator extends ChunkGenerator {

    private final long seed;
    protected final ChunkGeneratorSettings settings;
    private final SystemGenerator systemGenerator;

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

        // Is it overworld, nether or end?
        // There doesn't seem to be a better way to distinguish these currently?
        SpheroidLoader.SpheroidDimensionType spheroidDimensionType;
        if (Blocks.NETHERRACK.getDefaultState().equals(chunkGeneratorType.getDefaultBlock())) {
            spheroidDimensionType = SpheroidLoader.SpheroidDimensionType.NETHER;

            // config values
            this.FLOOR_HEIGHT = StarrySkyCommon.STARRY_SKY_CONFIG.floorHeightNether;
            this.FLOOR_BLOCK_STATE = Registry.BLOCK.get(new Identifier(StarrySkyCommon.STARRY_SKY_CONFIG.floorBlockNether.toLowerCase())).getDefaultState();
            this.BOTTOM_BLOCK_STATE = Registry.BLOCK.get(new Identifier(StarrySkyCommon.STARRY_SKY_CONFIG.bottomBlockNether.toLowerCase())).getDefaultState();
        } else if (Blocks.END_STONE.getDefaultState().equals(chunkGeneratorType.getDefaultBlock())) {
            spheroidDimensionType = SpheroidLoader.SpheroidDimensionType.END;

            // config values
            this.FLOOR_HEIGHT = StarrySkyCommon.STARRY_SKY_CONFIG.floorHeightEnd;
            this.FLOOR_BLOCK_STATE = Registry.BLOCK.get(new Identifier(StarrySkyCommon.STARRY_SKY_CONFIG.floorBlockEnd.toLowerCase())).getDefaultState();
            this.BOTTOM_BLOCK_STATE = Registry.BLOCK.get(new Identifier(StarrySkyCommon.STARRY_SKY_CONFIG.bottomBlockEnd.toLowerCase())).getDefaultState();
        } else {
            spheroidDimensionType = SpheroidLoader.SpheroidDimensionType.OVERWORLD;

            // config values
            this.FLOOR_HEIGHT = StarrySkyCommon.STARRY_SKY_CONFIG.floorHeightOverworld;
            this.FLOOR_BLOCK_STATE = Registry.BLOCK.get(new Identifier(StarrySkyCommon.STARRY_SKY_CONFIG.floorBlockOverworld.toLowerCase())).getDefaultState();
            this.BOTTOM_BLOCK_STATE = Registry.BLOCK.get(new Identifier(StarrySkyCommon.STARRY_SKY_CONFIG.bottomBlockOverworld.toLowerCase())).getDefaultState();
        }
        systemGenerator = new SystemGenerator(spheroidDimensionType);
    }

    private static BlockPos returnClosestStrongholdSphere(BlockPos blockPos, ServerWorld world, int radius) {
        ChunkGenerator chunkGenerator = world.getChunkManager().getChunkGenerator();
        if(chunkGenerator instanceof StarrySkyChunkGenerator) {
            Support.SpheroidDistance spheroidDistance = Support.getClosestSpheroid3x3(world, blockPos, SpheroidAdvancementIdentifier.stronghold);

            if(Math.sqrt(spheroidDistance.squaredDistance) <= radius * 16) {
                return spheroidDistance.spheroid.getPosition();
            }
        }
        return null;
    }

    @Override
    public BlockPos locateStructure(ServerWorld world, StructureFeature<?> feature, BlockPos center, int radius, boolean skipExistingChunks) {
        if(feature.getName().equals("stronghold")) {
            return returnClosestStrongholdSphere(center, world, radius);
        }
        return super.locateStructure(world, feature, center, radius, skipExistingChunks);
    }

    @Override
    protected Codec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public ChunkGenerator withSeed(long seed) {
        return new StarrySkyChunkGenerator(this.biomeSource.withSeed(seed), seed, () -> this.settings);
    }

    @Override
    public void buildSurface(ChunkRegion region, Chunk chunk) {
        ChunkPos chunkPos = chunk.getPos();

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

    @Override
    public CompletableFuture<Chunk> populateNoise(Executor executor, StructureAccessor accessor, Chunk chunk) {
        // generate spheres

        placeSpheroids(chunk);

        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public void populateEntities(ChunkRegion chunkRegion) {
        ChunkPos chunkPos = chunkRegion.getCenterPos();

        List<Spheroid> localSystem = systemGenerator.getSystemAtChunkPos(chunkPos.x, chunkPos.z);
        ChunkRandom chunkRandom = new ChunkRandom();
        chunkRandom.setPopulationSeed(chunkRegion.getSeed(), chunkPos.x, chunkPos.z);

        for(Spheroid spheroid : localSystem) {
            spheroid.populateEntities(chunkPos, chunkRegion, chunkRandom);
        }
    }

    @Override
    public int getSeaLevel() {
        return FLOOR_HEIGHT;
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmap, HeightLimitView world) {
        return FLOOR_HEIGHT;
    }

    @Override
    public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world) {
        BlockState[] states = new BlockState[world.getHeight()];
        Arrays.fill(states, Blocks.AIR.getDefaultState());
        return new VerticalBlockSample(world.getBottomY(), states);
    }

    public void placeSpheroids(Chunk chunk) {

        ChunkRandom chunkRandom = new ChunkRandom(StarrySkyCommon.starryWorld.getSeed());
        chunkRandom.setTerrainSeed(chunk.getPos().getRegionX(), chunk.getPos().getRegionZ());

        List<Spheroid> localSystem = systemGenerator.getSystemAtChunkPos(chunk.getPos().x, chunk.getPos().z);

        for(Spheroid spheroid : localSystem) {
            if (spheroid.isInChunk(chunk.getPos())) {
                StarrySkyCommon.log(Level.DEBUG, "Generating spheroid in chunk x:" + chunk.getPos().x + " z:" + chunk.getPos().z + " (StartX:" + chunk.getPos().getStartX() + " StartZ:" + chunk.getPos().getStartZ() + ") " + spheroid.getDescription());
                spheroid.generate(chunk);
                StarrySkyCommon.log(Level.DEBUG, "Finished.");
            }
        }
    }

}