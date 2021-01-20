package de.dafuqs.starrysky.spheroid.spheroids;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.SpheroidEntitySpawnDefinition;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.MobSpawnerEntry;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import static org.apache.logging.log4j.Level.WARN;

public abstract class Spheroid implements Serializable {

    protected SpheroidAdvancementIdentifier spheroidAdvancementIdentifier;
    protected ArrayList<SpheroidEntitySpawnDefinition> entityTypesToSpawn;
    protected BlockPos position;
    protected int radius;
    protected ChunkRandom random;
    private boolean isDecorated = false;

    /**
     * Chunks this spheroid should be still generated in
     **/
    private final HashSet<ChunkPos> chunksOfSpheroid = new HashSet<>();
    /**
     * The decorators that should be ran after generation
     **/
    private final ArrayList<SpheroidDecorator> spheroidDecorators;
    /**
     * The tracker for blocks to be decorated. Filled in generate()
     **/
    private final ArrayList<BlockPos> decorationBlockPositions = new ArrayList<>();

    public Spheroid(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, ChunkRandom random, ArrayList<SpheroidDecorator> spheroidDecorators, int radius, ArrayList<SpheroidEntitySpawnDefinition> entityTypesToSpawn) {
        this.spheroidAdvancementIdentifier = spheroidAdvancementIdentifier;
        this.random = random;
        this.spheroidDecorators = spheroidDecorators;
        this.radius = radius;
        this.entityTypesToSpawn = entityTypesToSpawn;
    }

    public abstract void generate(Chunk chunk);

    public BlockPos getPosition() {
        return position;
    }

    public void setPositionAndCalculateChunks(BlockPos blockPos) {
        this.position = blockPos;

        for (int currXPos = blockPos.getX() - Math.round(radius); currXPos <= blockPos.getX() + Math.round(radius); currXPos++) {
            for (int currZPos = blockPos.getZ() - Math.round(radius); currZPos <= blockPos.getZ() + Math.round(radius); currZPos++) {
                int cx = (int) Math.floor(currXPos / 16.0D);
                int cz = (int) Math.floor(currZPos / 16.0D);
                this.chunksOfSpheroid.add(new ChunkPos(cx, cz));
            }
        }
    }

    public SpheroidAdvancementIdentifier getSpheroidAdvancementIdentifier() {
        return spheroidAdvancementIdentifier;
    }

    public int getRadius() {
        return radius;
    }

    public abstract String getDescription();

    public boolean isInChunk(ChunkPos chunkPos) {
        return this.chunksOfSpheroid.contains(chunkPos);
    }

    public boolean hasDecorators() {
        return this.spheroidDecorators.size() > 0;
    }

    public void addDecorationBlockPosition(BlockPos blockPos) {
        if (hasDecorators()) {
            this.decorationBlockPositions.add(blockPos);
        }
    }

    public void decorate(StructureWorldAccess world, Random random) {
        if (!isDecorated) {
            for (SpheroidDecorator decorator : this.spheroidDecorators) {
                try {
                    decorator.decorateSpheroid(world, this, this.decorationBlockPositions, random);
                } catch (RuntimeException e) {
                    // We are asking a region for a chunk out of bound ಠ_ಠ
                }
            }
            isDecorated = true;
        }
    }

    protected boolean isTopBlock(long d, double x, double y, double z) {
        if (d == this.radius) {
            long dist2 = Math.round(Support.getDistance(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), x, y + 1, z));
            return dist2 > this.radius;
        } else {
            return false;
        }
    }

    protected boolean isBottomBlock(long d, double x, double y, double z) {
        if (d == this.radius) {
            long dist2 = Math.round(Support.getDistance(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), x, y - 1, z));
            return dist2 > this.radius;
        } else {
            return false;
        }
    }

    protected boolean isAboveCaveFloorBlock(long d, double x, double y, double z, int shellRadius) {
        int distance1 = (int) Math.round(Support.getDistance(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), x, y - 1, z));
        return d == (this.radius - shellRadius) && distance1 > (this.radius - shellRadius);
    }

    protected void placeCenterChestWithLootTable(Chunk chunk, BlockPos blockPos, Identifier lootTable, Random random, boolean waterLogged) {
        BlockState chestBlockState;
        if (waterLogged) {
            chestBlockState = Blocks.CHEST.getDefaultState();
        } else {
            chestBlockState = Blocks.CHEST.getDefaultState();
        }
        chunk.setBlockState(blockPos, chestBlockState, false);
        chunk.setBlockEntity(blockPos, new ChestBlockEntity());
        LootableContainerBlockEntity.setLootTable(chunk, random, blockPos, lootTable);
    }

    public boolean shouldPopulateEntities(ChunkPos chunkPos) {
        return (this.getPosition().getX() >= chunkPos.getStartX()
                && this.getPosition().getX() <= chunkPos.getStartX() + 15
                && this.getPosition().getZ() >= chunkPos.getStartZ()
                && this.getPosition().getZ() <= chunkPos.getStartZ() + 15);
    }

    public void populateEntities(ChunkPos chunkPos, ChunkRegion chunkRegion, ChunkRandom chunkRandom) {
        if (shouldPopulateEntities(chunkPos)) {
            for (SpheroidEntitySpawnDefinition entityTypeToSpawn : entityTypesToSpawn) {
                int xChunk = chunkRegion.getCenterChunkX();
                int zChunk = chunkRegion.getCenterChunkZ();
                int xCord = xChunk << 4;
                int zCord = zChunk << 4;

                ChunkRandom sharedseedrandom = new ChunkRandom();
                sharedseedrandom.setPopulationSeed(chunkRegion.getSeed(), xCord, zCord);

                int randomAmount = Support.getRandomBetween(random, entityTypeToSpawn.minAmount, entityTypeToSpawn.maxAmount);
                for (int i = 0; i < randomAmount; i++) {
                    int startingX = this.getPosition().getX(); //xCord + sharedseedrandom.nextInt(4);
                    int startingY = this.getPosition().getY() + this.getRadius();
                    int startingZ = this.getPosition().getZ(); //zCord + sharedseedrandom.nextInt(4);
                    int minHeight = this.getPosition().getY() - this.getRadius();
                    BlockPos.Mutable blockpos = new BlockPos.Mutable(startingX, startingY, startingZ);
                    int height = Support.getLowerGroundBlock(chunkRegion, blockpos, minHeight) + 1;

                    if (height != 0) {
                        Entity entity = entityTypeToSpawn.entityType.create(chunkRegion.toServerWorld());
                        if (entity != null) {
                            float width = entity.getWidth();
                            double xLength = MathHelper.clamp(startingX, (double) xCord + (double) width, (double) xCord + 16.0D - (double) width);
                            double zLength = MathHelper.clamp(startingZ, (double) zCord + (double) width, (double) zCord + 16.0D - (double) width);

                            try {
                                entity.refreshPositionAndAngles(xLength, height, zLength, sharedseedrandom.nextFloat() * 360.0F, 0.0F);
                                if (entity instanceof MobEntity) {
                                    MobEntity mobentity = (MobEntity) entity;
                                    if (mobentity.canSpawn(chunkRegion, SpawnReason.CHUNK_GENERATION) && mobentity.canSpawn(chunkRegion)) {
                                        mobentity.initialize(chunkRegion, chunkRegion.getLocalDifficulty(new BlockPos(mobentity.getPos())), SpawnReason.CHUNK_GENERATION, null, null);
                                        boolean success = chunkRegion.spawnEntity(mobentity);
                                        if (!success) {
                                            return;
                                        }
                                    }
                                }
                            } catch (Exception exception) {
                                StarrySkyCommon.log(WARN, "Failed to spawn mob on sphere" + this.getDescription() + "\nException: " + exception);
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean shouldDecorate(BlockPos blockPos) {
        // blockPos and center of spheroid in same chunk
        return (!isDecorated && (blockPos.getX() / 16 == this.getPosition().getX() / 16) && (blockPos.getZ() / 16 == this.getPosition().getZ() / 16));
    }

    protected void placeSpawner(WorldAccess worldAccess, BlockPos blockPos, EntityType entityType) {
        worldAccess.setBlockState(blockPos, Blocks.SPAWNER.getDefaultState(), 3);
        BlockEntity blockEntity = worldAccess.getBlockEntity(blockPos);
        if (blockEntity instanceof MobSpawnerBlockEntity) {
            ((MobSpawnerBlockEntity) blockEntity).getLogic().setEntityId(entityType);
        }
    }
    protected void placeSpawner(WorldAccess worldAccess, BlockPos blockPos, MobSpawnerEntry mobSpawnerEntry) {
        worldAccess.setBlockState(blockPos, Blocks.SPAWNER.getDefaultState(), 3);
        BlockEntity blockEntity = worldAccess.getBlockEntity(blockPos);
        if (blockEntity instanceof MobSpawnerBlockEntity) {
            ((MobSpawnerBlockEntity) blockEntity).getLogic().setSpawnEntry(mobSpawnerEntry);
        }
    }

}
