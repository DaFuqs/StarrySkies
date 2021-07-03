package de.dafuqs.starrysky.dimension.spheroid.spheroids.unique;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.dimension.spheroid.SpheroidEntitySpawnDefinition;
import de.dafuqs.starrysky.dimension.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;

import static org.apache.logging.log4j.Level.WARN;

public class OceanMonumentSpheroid extends Spheroid {

    private final BlockState water = Blocks.WATER.getDefaultState();
    private final BlockState prismarine = Blocks.PRISMARINE.getDefaultState();
    private final BlockState prismarine_bricks = Blocks.PRISMARINE_BRICKS.getDefaultState();
    private final BlockState dark_prismarine = Blocks.DARK_PRISMARINE.getDefaultState();
    private final BlockState sea_lantern = Blocks.SEA_LANTERN.getDefaultState();
    private final BlockState treasure = Blocks.WET_SPONGE.getDefaultState();

    private final int treasureRadius;
    private final int shellRadius;

    private final ArrayList<BlockPos> guardianPositions = new ArrayList<>();

    public OceanMonumentSpheroid(ChunkRandom random, SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int radius, ArrayList<SpheroidDecorator> spheroidDecorators, ArrayList<SpheroidEntitySpawnDefinition> entityTypesToSpawn, int treasureRadius, int shellRadius) {
        super(spheroidAdvancementIdentifier, random, spheroidDecorators, radius, entityTypesToSpawn);
        this.treasureRadius = treasureRadius;
        this.shellRadius = shellRadius;
    }

    @Override
    public String getDescription() {
        return "+++ OceanMonumentSpheroid +++" +
                "\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
                "\nRadius: " + this.radius +
                "\nTreasure: " + this.treasure.toString() + " (Radius: " + this.treasureRadius + ")";
    }

    @Override
    public void generate(Chunk chunk) {
        int chunkX = chunk.getPos().x;
        int chunkZ = chunk.getPos().z;

        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        int z = this.getPosition().getZ();

        int shellDistance = this.radius - this.shellRadius;

        random.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
        for (int x2 = Math.max(chunkX * 16, x - this.radius); x2 <= Math.min(chunkX * 16 + 15, x + this.radius); x2++) {
            for (int y2 = y - this.radius; y2 <= y + this.radius; y2++) {
                for (int z2 = Math.max(chunkZ * 16, z - this.radius); z2 <= Math.min(chunkZ * 16 + 15, z + this.radius); z2++) {
                    BlockPos currBlockPos = new BlockPos(x2, y2, z2);
                    long d = Math.round(Support.getDistance(x, y, z, x2, y2, z2));
                    if (d <= this.treasureRadius) {
                        chunk.setBlockState(currBlockPos, this.treasure, false);
                    } else if (d < treasureRadius + 3) {
                        chunk.setBlockState(currBlockPos, water, false);
                    } else if (d == treasureRadius + 3) {
                        if(Math.abs(x2-x) < 2 || Math.abs(z2-z) < 2) {
                            chunk.setBlockState(currBlockPos, water, false);
                        } else {
                            chunk.setBlockState(currBlockPos, dark_prismarine, false);
                        }
                    } else if (d <= shellDistance) {
                        if (y2 % 10 == 0 || x2 % 10 == 0 || z2 % 10 == 0) {
                            if ((y2-y) % 6 == 0 && ((x2-x) % 4 == 2 || (z2-z) % 4 == 0)) {
                                chunk.setBlockState(currBlockPos, this.sea_lantern, false);
                            } else {
                                chunk.setBlockState(currBlockPos, this.prismarine_bricks, false);
                            }
                        } else {
                            if(y2 % 10 == 5 && x2 % 10 == 5 && z2 % 10 == 5) {
                                guardianPositions.add(currBlockPos);
                            }
                            chunk.setBlockState(currBlockPos, this.water, false);
                        }
                    } else if (d <= this.radius) {
                        if (y2 % 2 == 0) {
                            chunk.setBlockState(currBlockPos, this.prismarine, false);
                        } else {
                            chunk.setBlockState(currBlockPos, this.prismarine_bricks, false);
                        }
                    }
                }
            }
        }
    }

    /**
     * Populate guardians in every chunk the spheroid is in
     * The default would be just the center chunk
     * @param chunkPos
     * @return
     */
    @Override
    public boolean shouldPopulateEntities(ChunkPos chunkPos) {
        return isInChunk(chunkPos);
    }

    @Override
    public void populateEntities(ChunkPos chunkPos, ChunkRegion chunkRegion, ChunkRandom chunkRandom) {
        if (isCenterInChunk(chunkPos)) {
            for (BlockPos guardianPosition : guardianPositions) {
                if (Support.isBlockPosInChunkPos(chunkPos, guardianPosition)) {
                    int xCord = chunkPos.x;
                    int zCord = chunkPos.z;

                    ChunkRandom sharedSeedRandom = new ChunkRandom();
                    sharedSeedRandom.setPopulationSeed(chunkRegion.getSeed(), xCord, zCord);

                    MobEntity mobentity;
                    if (random.nextFloat() < 0.08) {
                        mobentity = EntityType.ELDER_GUARDIAN.create(chunkRegion.toServerWorld());
                    } else {
                        mobentity = EntityType.GUARDIAN.create(chunkRegion.toServerWorld());
                    }

                    if (mobentity != null) {
                        float width = mobentity.getWidth();
                        double xLength = MathHelper.clamp(guardianPosition.getX(), (double) chunkPos.getStartX() + (double) width, (double) chunkPos.getStartX() + 16.0D - (double) width);
                        double zLength = MathHelper.clamp(guardianPosition.getZ(), (double) chunkPos.getStartZ() + (double) width, (double) chunkPos.getStartZ() + 16.0D - (double) width);

                        try {
                            mobentity.refreshPositionAndAngles(xLength, guardianPosition.getY(), zLength, sharedSeedRandom.nextFloat() * 360.0F, 0.0F);
                            mobentity.setPersistent();
                            if (mobentity.canSpawn(chunkRegion, SpawnReason.CHUNK_GENERATION) && mobentity.canSpawn(chunkRegion)) {
                                mobentity.initialize(chunkRegion, chunkRegion.getLocalDifficulty(new BlockPos(mobentity.getPos())), SpawnReason.CHUNK_GENERATION, null, null);
                                boolean success = chunkRegion.spawnEntity(mobentity);
                                if (!success) {
                                    return;
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
