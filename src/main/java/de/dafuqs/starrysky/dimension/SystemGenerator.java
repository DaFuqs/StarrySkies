package de.dafuqs.starrysky.dimension;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.spheroid.lists.SpheroidListVanilla;
import de.dafuqs.starrysky.spheroid.lists.SpheroidListVanillaEnd;
import de.dafuqs.starrysky.spheroid.lists.SpheroidListVanillaNether;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import de.dafuqs.starrysky.spheroid.types.SpheroidType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkRandom;
import org.apache.logging.log4j.Level;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SystemGenerator {

    public static HashMap<SpheroidLoader.SpheroidDimensionType, SystemGenerator> systemGeneratorMap = new HashMap<>();

    // spawning probabilities
    private final SpheroidLoader.SpheroidDimensionType spheroidDimensionType;
    private final HashMap<Point, List<Spheroid>> cache = new HashMap<>();
    public static SpheroidLoader spheroidLoader;

    private final int SYSTEM_SIZE_CHUNKS;
    private final int MIN_DISTANCE_BETWEEN_SPHERES;
    private final int SPHERE_DENSITY;
    private final int FLOOR_HEIGHT;

    public static SystemGenerator getSystemGeneratorOfWorld(RegistryKey<World> worldRegistryKey) {
        if(worldRegistryKey.equals(StarrySkyDimension.STARRY_SKY_WORLD_KEY)) {
            return systemGeneratorMap.get(SpheroidLoader.SpheroidDimensionType.OVERWORLD);
        } else if(worldRegistryKey.equals(StarrySkyDimension.STARRY_SKY_NETHER_WORLD_KEY)) {
            return systemGeneratorMap.get(SpheroidLoader.SpheroidDimensionType.NETHER);
        } else {
            return systemGeneratorMap.get(SpheroidLoader.SpheroidDimensionType.END);
        }

    }

    public static class TempPosition {
        int xPos;
        int yPos;
        int zPos;

        public BlockPos toBlockPos() {
            return new BlockPos(xPos, yPos, zPos);
        }

        private int distanceSquared(Spheroid pl1) {
            int xDist = xPos - pl1.getPosition().getX();
            int yDist = yPos - pl1.getPosition().getY();
            int zDist = zPos - pl1.getPosition().getZ();

            return xDist * xDist + yDist * yDist + zDist * zDist;
        }
    }

    public SystemGenerator(SpheroidLoader.SpheroidDimensionType spheroidDimensionType) {
        this.spheroidDimensionType = spheroidDimensionType;
        spheroidLoader = new SpheroidLoader();
        systemGeneratorMap.put(spheroidDimensionType, this);

        this.SYSTEM_SIZE_CHUNKS = StarrySkyCommon.STARRY_SKY_CONFIG.systemSizeChunks;
        switch (spheroidDimensionType) {
            case OVERWORLD:
                this.MIN_DISTANCE_BETWEEN_SPHERES = StarrySkyCommon.STARRY_SKY_CONFIG.minDistanceBetweenSpheresOverworld;
                this.SPHERE_DENSITY = StarrySkyCommon.STARRY_SKY_CONFIG.sphereDensityOverworld;
                this.FLOOR_HEIGHT = StarrySkyCommon.STARRY_SKY_CONFIG.floorHeightOverworld;
                break;
            case NETHER:
                this.MIN_DISTANCE_BETWEEN_SPHERES = StarrySkyCommon.STARRY_SKY_CONFIG.minDistanceBetweenSpheresNether;
                this.SPHERE_DENSITY = StarrySkyCommon.STARRY_SKY_CONFIG.sphereDensityNether;
                this.FLOOR_HEIGHT = StarrySkyCommon.STARRY_SKY_CONFIG.floorHeightNether;
                break;
            default:
                this.MIN_DISTANCE_BETWEEN_SPHERES = StarrySkyCommon.STARRY_SKY_CONFIG.minDistanceBetweenSpheresEnd;
                this.SPHERE_DENSITY = StarrySkyCommon.STARRY_SKY_CONFIG.sphereDensityEnd;
                this.FLOOR_HEIGHT = StarrySkyCommon.STARRY_SKY_CONFIG.floorHeightEnd;
                break;
        }
    }

    /**
     * System size of 50 results in system 0,0 at 0>+800, -1,0 at -800>0
     * @param chunkX X coordinate of chunk
     * @param chunkZ Z coordinate of chunk
     * @return the system point
     */
    private Point getSystemCoordinateFromChunkCoordinate(int chunkX, int chunkZ) {
        int sysX;
        if (chunkX >= 0) {
            sysX = chunkX / SYSTEM_SIZE_CHUNKS;
        } else {
            sysX = (int) Math.floor(chunkX / (float) SYSTEM_SIZE_CHUNKS);
        }

        int sysZ;
        if (chunkZ >= 0) {
            sysZ = chunkZ / SYSTEM_SIZE_CHUNKS;
        } else {
            sysZ = (int) Math.floor(chunkZ / (float) SYSTEM_SIZE_CHUNKS);
        }
        return new Point(sysX, sysZ);
    }

    /**
     * Returns the system at the given chunk coordinates
     * If a system does not exist yet it will be generated
     * @param chunkX chunk chunkX location
     * @param chunkZ chunk chunkZ location
     * @return List of planetoids representing the system this chunk is in
     */
    public List<Spheroid> getSystemAtChunkPos(int chunkX, int chunkZ) {
        //check if the system of this chunk is cached
        Point systemPos = getSystemCoordinateFromChunkCoordinate(chunkX, chunkZ);
        List<Spheroid> curSystem = cache.get(systemPos);

        if (curSystem == null) {
            //doesn't exist. Generate new system and cache it
            curSystem = generateSpheroidsAtSystemPoint(systemPos);
            cache.put(systemPos, curSystem);
        }

        return curSystem;
    }


    private ChunkRandom getSystemRandom(Point systemPoint) {
        int firstChunkPosX = systemPoint.x * SYSTEM_SIZE_CHUNKS;
        int firstChunkPosZ = systemPoint.y * SYSTEM_SIZE_CHUNKS;
        ChunkRandom systemRandom = new ChunkRandom(StarrySkyCommon.starryWorld.getSeed());
        systemRandom.setTerrainSeed(firstChunkPosX, firstChunkPosZ); // and the seed from the first chunk+
        StarrySkyCommon.LOGGER.log(Level.DEBUG, "[StarrySky] Generated seed for system at " + systemPoint.x + "," + systemPoint.y + "(first chunk: " + firstChunkPosX + "," + firstChunkPosZ);
        return systemRandom;
    }


    private List<Spheroid> generateSpheroidsAtSystemPoint(Point systemPoint) {
        int systemPointX = systemPoint.x;
        int systemPointZ = systemPoint.y;

        ChunkRandom systemRandom = getSystemRandom(systemPoint);
        ArrayList<Spheroid> spheroids = new ArrayList<>();

        //If systemPointX and Z are zero, generate a log/leaf planet at 16, 16
        if (systemPointX == 0 && systemPointZ == 0) {
            Spheroid homeSpheroid = getSpawnSpheroid(systemRandom);
            homeSpheroid.setPositionAndCalculateChunks(new BlockPos(16, 70, 16));
            spheroids.add(homeSpheroid);
        }

        // try to create DENSITY planets in system
        int worldHeight = StarrySkyCommon.starryWorld.getHeight();
        for (int currentDensity = 0; currentDensity < SPHERE_DENSITY; currentDensity++) {

            // create new planets
            Spheroid currentSpheroid = getRandomSpheroid(systemRandom);
            TempPosition tempPosition = new TempPosition();

            // set position, check bounds with system edges on x and z
            tempPosition.xPos = Support.getRandomBetween(systemRandom, currentSpheroid.getRadius(), SYSTEM_SIZE_CHUNKS * 16 - currentSpheroid.getRadius());
            tempPosition.xPos += SYSTEM_SIZE_CHUNKS * 16 * systemPointX;
            tempPosition.zPos = Support.getRandomBetween(systemRandom, currentSpheroid.getRadius(), SYSTEM_SIZE_CHUNKS * 16 - currentSpheroid.getRadius());
            tempPosition.zPos += SYSTEM_SIZE_CHUNKS * 16 * systemPointZ;
            tempPosition.yPos = systemRandom.nextInt(worldHeight - currentSpheroid.getRadius() * 2 - FLOOR_HEIGHT) + currentSpheroid.getRadius() + FLOOR_HEIGHT;

            // check for collisions with existing spheroids
            // if any collision, discard it
            boolean discard = false;
            for (Spheroid spheroid : spheroids) {
                //each spheroid has to be at least pl1.radius + pl2.radius + min distance apart
                int distMin = spheroid.getRadius() + currentSpheroid.getRadius() + MIN_DISTANCE_BETWEEN_SPHERES;
                int distSquared = tempPosition.distanceSquared(spheroid);
                if (distSquared < distMin * distMin) {
                    discard = true;
                    break;
                }
            }

            if (!discard) {
                BlockPos finalSpheroidBlockPos = tempPosition.toBlockPos();
                currentSpheroid.setPositionAndCalculateChunks(finalSpheroidBlockPos);

                // add it to the list
                spheroids.add(currentSpheroid);
            }
        }

        StarrySkyCommon.LOGGER.log(Level.INFO, "[StarrySky] Created a new system with " + spheroids.size() + " spheroids at system position " + systemPointX + "," + systemPointZ);

        return spheroids;
    }

    private Spheroid getSpawnSpheroid(ChunkRandom random) {
        switch (this.spheroidDimensionType) {
            case NETHER:
                return SpheroidListVanillaNether.NETHERRACK.getRandomSpheroid(random);
            case END:
                return SpheroidListVanillaEnd.END_PORTAL.getRandomSpheroid(random);
            default:
                return SpheroidListVanilla.OAK_WOOD.getRandomSpheroid(random);
        }
    }

    private Spheroid getRandomSpheroid(ChunkRandom systemRandom) {
        SpheroidType spheroidType;
        do {
            spheroidType = SpheroidLoader.getWeightedRandomSpheroid(spheroidDimensionType, systemRandom);
        } while(spheroidType == null);

        StarrySkyCommon.LOGGER.log(Level.DEBUG, "[StarrySky] Created a new sphere of type " + spheroidType + " Next random: " + systemRandom.nextInt());
        return spheroidType.getRandomSpheroid(systemRandom);
    }

}
