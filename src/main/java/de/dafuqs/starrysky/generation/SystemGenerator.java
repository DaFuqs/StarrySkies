package de.dafuqs.starrysky.generation;

import de.dafuqs.starrysky.SpheroidData.SpheroidList;
import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.configs.StarrySkyConfig;
import de.dafuqs.starrysky.spheroids.*;
import de.dafuqs.starrysky.spheroids.special_overworld.BeeHiveSpheroid;
import de.dafuqs.starrysky.spheroids.special_overworld.CaveSpheroid;
import de.dafuqs.starrysky.spheroids.special_overworld.DungeonSpheroid;
import de.dafuqs.starrysky.spheroidtypes.*;
import de.dafuqs.starrysky.spheroidtypes.special_overworld.BeeHiveSpheroidType;
import de.dafuqs.starrysky.spheroidtypes.special_overworld.DungeonSpheroidType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.ChunkRandom;
import org.apache.logging.log4j.Level;

import java.awt.*;
import java.util.*;
import java.util.List;

public class SystemGenerator {

    // spawning probabilities
    // normal hashmaps are not stable
    private static final LinkedHashMap<SpheroidType, Float> spheroidDistribution = new LinkedHashMap<>();
    private HashMap<Point, List<Spheroid>> cache = new HashMap<>();


    public SystemGenerator() {
        putAvailableSpheroidDistribution();
    }

    /**
     *
     * System size of 50 results in system 0,0 at 0>+800, -1,0 at -800>0
     * @param chunkX X coordinate of chunk
     * @param chunkZ Z coordinate of chunk
     * @return the system point
     */
    private Point getSystemCoordinateFromChunkCoordinate(int chunkX, int chunkZ) {
        int sysX;
        if (chunkX >= 0) {
            sysX = chunkX / StarrySkyCommon.STARRY_SKY_CONFIG.systemSizeChunks;
        } else {
            sysX = (int) Math.floor(chunkX / (float) StarrySkyCommon.STARRY_SKY_CONFIG.systemSizeChunks);
        }

        int sysZ;
        if (chunkZ >= 0) {
            sysZ = chunkZ / StarrySkyCommon.STARRY_SKY_CONFIG.systemSizeChunks;
        } else {
            sysZ = (int) Math.floor(chunkZ / (float) StarrySkyCommon.STARRY_SKY_CONFIG.systemSizeChunks);
        }
        return new Point(sysX, sysZ);
    }

    /**
     * @param chunkX chunk chunkX location
     * @param chunkZ chunk chunkZ location
     * @return List of planetoids representing the system this chunk is in
     */
    public List<Spheroid> getSystemAtChunkPos(int chunkX, int chunkZ) {

        //check if the system of this chunk is cached
        Point systemPos = getSystemCoordinateFromChunkCoordinate(chunkX, chunkZ);
        List<Spheroid> curSystem = cache.get(systemPos);

        if (curSystem == null) {
            //generate and cache
            curSystem = generatePlanetsAtSystemPosition(systemPos);
            cache.put(systemPos, curSystem);
        }

        return curSystem;
    }


    private ChunkRandom getSystemRandom(Point systemPoint) {
        int firstChunkPosX = systemPoint.x * StarrySkyCommon.STARRY_SKY_CONFIG.systemSizeChunks;
        int firstChunkPosZ = systemPoint.y * StarrySkyCommon.STARRY_SKY_CONFIG.systemSizeChunks;
        ChunkRandom systemRandom = new ChunkRandom(StarrySkyCommon.starryWorld.getSeed());
        systemRandom.setTerrainSeed(firstChunkPosX, firstChunkPosZ); // and the seed from the first chunk+
        StarrySkyCommon.LOGGER.log(Level.INFO, "generated seed for system at " + systemPoint.x + "," + systemPoint.y + "(first chunk: " + firstChunkPosX + "," + firstChunkPosZ);
        return systemRandom;
    }


    private List<Spheroid> generatePlanetsAtSystemPosition(Point systemPoint) {
        int systemPointX = systemPoint.x;
        int systemPointZ = systemPoint.y;

        ChunkRandom systemRandom = getSystemRandom(systemPoint);
        List<Spheroid> spheroids = new ArrayList<Spheroid>();

        //If systemPointX and Z are zero, generate a log/leaf planet at 16,16
        if (systemPointX == 0 && systemPointZ == 0) {
            Spheroid homeSpheroid = new ShellSpheroid(SpheroidList.OAK_WOOD, systemRandom);
            homeSpheroid.setPositionAndCalculateGenerationChunks(new BlockPos(16, 70, 16));
            spheroids.add(homeSpheroid);
        }

        // try to create DENSITY planets in system
        int worldHeight = StarrySkyCommon.starryWorld.getHeight();
        for (int currentDensity = 0; currentDensity < StarrySkyCommon.STARRY_SKY_CONFIG.sphereDensity; currentDensity++) {

            // create new planets
            Spheroid currentSpheroid = getRandomSpheroid(systemRandom);
            TempPosition tempPosition = new TempPosition();

            // set position, check bounds with system edges
            tempPosition.xPos = -1;
            while (tempPosition.xPos == -1) {
                int curTry = systemRandom.nextInt(StarrySkyCommon.STARRY_SKY_CONFIG.systemSizeChunks * 16);
                if (curTry + currentSpheroid.getRadius() < StarrySkyCommon.STARRY_SKY_CONFIG.systemSizeChunks * 16 && curTry - currentSpheroid.getRadius() >= 0) {
                    tempPosition.xPos = curTry;
                }
            }
            tempPosition.xPos += StarrySkyCommon.STARRY_SKY_CONFIG.systemSizeChunks * 16 * systemPointX;

            tempPosition.yPos = systemRandom.nextInt(worldHeight - currentSpheroid.getRadius() * 2 - StarrySkyCommon.STARRY_SKY_CONFIG.floorHeight) + currentSpheroid.getRadius() + StarrySkyCommon.STARRY_SKY_CONFIG.floorHeight;

            tempPosition.zPos = -1;
            while (tempPosition.zPos == -1) {
                int curTry = systemRandom.nextInt(StarrySkyCommon.STARRY_SKY_CONFIG.systemSizeChunks * 16);
                if (curTry + currentSpheroid.getRadius() < StarrySkyCommon.STARRY_SKY_CONFIG.systemSizeChunks * 16 && curTry - currentSpheroid.getRadius() >= 0) {
                    tempPosition.zPos = curTry;
                }
            }
            tempPosition.zPos += StarrySkyCommon.STARRY_SKY_CONFIG.systemSizeChunks * 16 * systemPointZ;

            // check for collisions with existing spheroids
            // if any collision, discard it
            boolean discard = false;
            for (Spheroid pl : spheroids) {
                //each spheroid has to be at least pl1.radius + pl2.radius + min distance apart
                int distMin = pl.getRadius() + currentSpheroid.getRadius() + StarrySkyCommon.STARRY_SKY_CONFIG.minDistanceBetweenSpheres;
                if (distanceSquared(pl, tempPosition) < distMin * distMin) {
                    discard = true;
                    break;
                }
            }
            if (!discard) {
                BlockPos finalSpheroidBlockPos = tempPosition.toBlockPos();
                currentSpheroid.setPositionAndCalculateGenerationChunks(finalSpheroidBlockPos);

                // add it to the list
                spheroids.add(currentSpheroid);
            }
        }
        StarrySkyCommon.LOGGER.log(Level.INFO, "Created a new system with " + spheroids.size() + " spheroids at system position " + systemPointX + "," + systemPointZ);
        return spheroids;
    }


    private int distanceSquared(Spheroid pl1, TempPosition tempPosition) {
        int xDist = tempPosition.xPos - pl1.getPosition().getX();
        int yDist = tempPosition.yPos - pl1.getPosition().getY();
        int zDist = tempPosition.zPos - pl1.getPosition().getZ();

        return xDist * xDist + yDist * yDist + zDist * zDist;
    }


    private void putAvailableSpheroidDistribution() {
        //BASIC
        spheroidDistribution.put(SpheroidList.GRASS, 3.5F);
        spheroidDistribution.put(SpheroidList.MYCELIUM, 0.01F);
        spheroidDistribution.put(SpheroidList.PODZOL, 0.4F);
        spheroidDistribution.put(SpheroidList.STONE, 0.2F);
        spheroidDistribution.put(SpheroidList.GRANITE, 0.2F);
        spheroidDistribution.put(SpheroidList.DIORITE, 0.2F);
        spheroidDistribution.put(SpheroidList.ANDESITE, 1.0F);
        spheroidDistribution.put(SpheroidList.SAND, 2.0F);
        spheroidDistribution.put(SpheroidList.RED_SAND, 0.2F);
        spheroidDistribution.put(SpheroidList.GRAVEL, 0.3F);
        spheroidDistribution.put(SpheroidList.COBBLESTONE, 0.2F);
        spheroidDistribution.put(SpheroidList.MOSSY_COBBLESTONE, 0.05F);
        spheroidDistribution.put(SpheroidList.COARSE_DIRT, 0.05F);

        // CAVE
        spheroidDistribution.put(SpheroidList.HUGE_MONSTER_CAVE, 0.05F);

        //GLASS
        spheroidDistribution.put(SpheroidList.GLASS, 0.1F);
        spheroidDistribution.put(SpheroidList.BLACK_STAINED_GLASS, 0.01F);
        spheroidDistribution.put(SpheroidList.BLUE_STAINED_GLASS, 0.01F);
        spheroidDistribution.put(SpheroidList.BROWN_STAINED_GLASS, 0.01F);
        spheroidDistribution.put(SpheroidList.CYAN_STAINED_GLASS, 0.01F);
        spheroidDistribution.put(SpheroidList.GRAY_STAINED_GLASS, 0.01F);
        spheroidDistribution.put(SpheroidList.GREEN_STAINED_GLASS, 0.01F);
        spheroidDistribution.put(SpheroidList.LIGHT_BLUE_STAINED_GLASS, 0.01F);
        spheroidDistribution.put(SpheroidList.LIGHT_GRAY_STAINED_GLASS, 0.01F);
        spheroidDistribution.put(SpheroidList.LIME_STAINED_GLASS, 0.01F);
        spheroidDistribution.put(SpheroidList.MAGENTA_STAINED_GLASS, 0.01F);
        spheroidDistribution.put(SpheroidList.ORANGE_STAINED_GLASS, 0.01F);
        spheroidDistribution.put(SpheroidList.PINK_STAINED_GLASS, 0.01F);
        spheroidDistribution.put(SpheroidList.PURPLE_STAINED_GLASS, 0.01F);
        spheroidDistribution.put(SpheroidList.RED_STAINED_GLASS, 0.01F);
        spheroidDistribution.put(SpheroidList.WHITE_STAINED_GLASS, 0.01F);
        spheroidDistribution.put(SpheroidList.YELLOW_STAINED_GLASS, 0.01F);

        //RARE
        spheroidDistribution.put(SpheroidList.OBSIDIAN, 0.1F);
        spheroidDistribution.put(SpheroidList.GLOWSTONE, 2.0F);
        spheroidDistribution.put(SpheroidList.BEDROCK, 0.01F);
        spheroidDistribution.put(SpheroidList.STONE_HOLLOW, 0.2F);

        //ORES
        spheroidDistribution.put(SpheroidList.COAL, 4.0F);
        spheroidDistribution.put(SpheroidList.IRON, 2.0F);
        spheroidDistribution.put(SpheroidList.GOLD, 0.5F);
        spheroidDistribution.put(SpheroidList.DIAMOND, 0.2F);
        spheroidDistribution.put(SpheroidList.REDSTONE, 1.0F);
        spheroidDistribution.put(SpheroidList.LAPIS, 0.3F);
        spheroidDistribution.put(SpheroidList.EMERALD, 0.05F);

        // "ORES"
        spheroidDistribution.put(SpheroidList.BONE, 0.02F);
        spheroidDistribution.put(SpheroidList.HAY, 0.02F);
        spheroidDistribution.put(SpheroidList.PRISMARINE, 0.02F);
        spheroidDistribution.put(SpheroidList.SLIME, 0.02F);
        spheroidDistribution.put(SpheroidList.TNT, 0.02F);

        //WOOD
        spheroidDistribution.put(SpheroidList.OAK_WOOD, 1.0F);
        spheroidDistribution.put(SpheroidList.SPRUCE_WOOD, 0.5F);
        spheroidDistribution.put(SpheroidList.JUNGLE_WOOD, 0.5F);
        spheroidDistribution.put(SpheroidList.DARK_OAK_WOOD, 0.5F);
        spheroidDistribution.put(SpheroidList.BIRCH_WOOD, 0.5F);
        spheroidDistribution.put(SpheroidList.ACACIA_WOOD, 0.5F);

        //FLUIDS
        spheroidDistribution.put(SpheroidList.WATER_GLASS, 1.5F);
        spheroidDistribution.put(SpheroidList.WATER_CLAY, 0.5F);
        spheroidDistribution.put(SpheroidList.WATER_SPONGE, 0.1F);
        spheroidDistribution.put(SpheroidList.WATER_SLIME, 0.1F);
        spheroidDistribution.put(SpheroidList.WATER_ICE, 0.4F);
        spheroidDistribution.put(SpheroidList.WATER_PACKED_ICE, 0.1F);
        spheroidDistribution.put(SpheroidList.LAVA_STONE, 2.0F);
        spheroidDistribution.put(SpheroidList.LAVA_MAGMA, 0.4F);
        spheroidDistribution.put(SpheroidList.LAVA_OBSIDIAN, 1.0F);
        spheroidDistribution.put(SpheroidList.LAVA_GLASS, 0.5F);

        //COLD
        spheroidDistribution.put(SpheroidList.ICE, 0.4F);
        spheroidDistribution.put(SpheroidList.GLASS_ICE, 0.4F);
        spheroidDistribution.put(SpheroidList.SNOW_ICE, 0.4F);
        spheroidDistribution.put(SpheroidList.SNOW_BLUE_ICE, 0.2F);
        spheroidDistribution.put(SpheroidList.ICE_BLUE_ICE, 0.2F);

        //RAINBOW
        spheroidDistribution.put(SpheroidList.RAINBOW_WOOL, 0.1F);
        spheroidDistribution.put(SpheroidList.RAINBOW_GLASS, 0.1F);
        spheroidDistribution.put(SpheroidList.RAINBOW_CONCRETE, 0.1F);
        spheroidDistribution.put(SpheroidList.RAINBOW_TERRACOTTA, 0.1F);

        // SPAWNERS
        spheroidDistribution.put(SpheroidList.DUNGEON_ZOMBIE, 0.2F);
        spheroidDistribution.put(SpheroidList.DUNGEON_SKELETON, 0.1F);
        spheroidDistribution.put(SpheroidList.DUNGEON_SPIDER, 0.1F);
        spheroidDistribution.put(SpheroidList.DUNGEON_CREEPER, 0.02F);
        spheroidDistribution.put(SpheroidList.DUNGEON_CAVE_SPIDER, 0.05F);
        spheroidDistribution.put(SpheroidList.DUNGEON_SLIME, 0.05F);
        spheroidDistribution.put(SpheroidList.DUNGEON_DROWNED, 0.05F);
        spheroidDistribution.put(SpheroidList.DUNGEON_HUSK, 0.05F);
        spheroidDistribution.put(SpheroidList.DUNGEON_STRAY, 0.05F);
        spheroidDistribution.put(SpheroidList.DUNGEON_WITCH, 0.05F);
        spheroidDistribution.put(SpheroidList.DUNGEON_SILVERFISH, 0.05F);

        // SPECIAL
        //spheroidDistribution.put(SpheroidList.BEE_HIVE, 0.1F);
        spheroidDistribution.put(SpheroidList.BEE_HIVE, 0.2F);
        spheroidDistribution.put(SpheroidList.THE_SUN, 0.01F);
    }


    private Spheroid getRandomSpheroid(ChunkRandom systemRandom) {
        SpheroidType spheroidType = Support.getWeightedRandom(spheroidDistribution, systemRandom);

        StarrySkyCommon.LOGGER.log(Level.DEBUG, "Created a new sphere of type " + spheroidType);
        if(spheroidType instanceof CoreSpheroidType) {
            return new CoreSpheroid( (CoreSpheroidType) spheroidType, systemRandom);
        } else if(spheroidType instanceof CaveSpheroidType) { // has to be checked before shellSpheroid
            return new CaveSpheroid( (CaveSpheroidType) spheroidType, systemRandom);
        } else if(spheroidType instanceof ShellSpheroidType) {
            return new ShellSpheroid( (ShellSpheroidType) spheroidType, systemRandom);
        } else if(spheroidType instanceof LiquidSpheroidType) {
            return new LiquidSpheroid( (LiquidSpheroidType) spheroidType, systemRandom);
        } else if(spheroidType instanceof ModularSpheroidType) {
            return new ModularSpheroid( (ModularSpheroidType) spheroidType, systemRandom);
        } else if(spheroidType instanceof DoubleCoreSpheroidType) {
            return new DoubleCoreSpheroid( (DoubleCoreSpheroidType) spheroidType, systemRandom);
        } else if(spheroidType instanceof RainbowSpheroidType) {
            return new RainbowSpheroid( (RainbowSpheroidType) spheroidType, systemRandom);
        } else if(spheroidType instanceof BeeHiveSpheroidType) {
            return new BeeHiveSpheroid( (BeeHiveSpheroidType) spheroidType, systemRandom);
        } else if(spheroidType instanceof DungeonSpheroidType) {
            return new DungeonSpheroid( (DungeonSpheroidType) spheroidType, systemRandom);
        } else {
            return null;
        }
    }


}
