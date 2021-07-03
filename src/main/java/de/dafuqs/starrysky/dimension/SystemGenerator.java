package de.dafuqs.starrysky.dimension;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.dimension.spheroid.lists.SpheroidListVanilla;
import de.dafuqs.starrysky.dimension.spheroid.lists.SpheroidListVanillaEnd;
import de.dafuqs.starrysky.dimension.spheroid.lists.SpheroidListVanillaNether;
import de.dafuqs.starrysky.dimension.spheroid.spheroids.Spheroid;
import de.dafuqs.starrysky.dimension.spheroid.types.SpheroidType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkRandom;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.apache.logging.log4j.Level.DEBUG;

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
     * Returns the system at the given chunk coordinates
     * If a system does not exist yet it will be generated
     * @param chunkX chunk chunkX location
     * @param chunkZ chunk chunkZ location
     * @return List of planetoids representing the system this chunk is in
     */
    public List<Spheroid> getSystemAtChunkPos(int chunkX, int chunkZ) {
        Point systemPos = Support.getSystemCoordinateFromChunkCoordinate(chunkX, chunkZ);
        return getSystemAtPoint(systemPos);
    }

    public List<Spheroid> getSystemAtPoint(Point systemPos) {
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
        StarrySkyCommon.log(DEBUG, "Generated seed for system at " + systemPoint.x + "," + systemPoint.y + "(first chunk: " + firstChunkPosX + "," + firstChunkPosZ);
        return systemRandom;
    }

    private BlockPos getBlockPosInSystem(Point systemPoint, int radius, BlockPos originalBlockPos) {
        int newX = originalBlockPos.getX();
        int newZ = originalBlockPos.getZ();

        if(originalBlockPos.getX() - radius < systemPoint.x * StarrySkyCommon.STARRY_SKY_CONFIG.systemSizeChunks * 16) {
            newX = systemPoint.x * StarrySkyCommon.STARRY_SKY_CONFIG.systemSizeChunks * 16 + radius;
        }
        if(originalBlockPos.getX() + radius > (systemPoint.x+1) * StarrySkyCommon.STARRY_SKY_CONFIG.systemSizeChunks * 16 -1) {
            newX = (systemPoint.x+1) * StarrySkyCommon.STARRY_SKY_CONFIG.systemSizeChunks * 16 - radius;
        }
        if(originalBlockPos.getZ() - radius < systemPoint.y * StarrySkyCommon.STARRY_SKY_CONFIG.systemSizeChunks * 16) {
            newX = systemPoint.y * StarrySkyCommon.STARRY_SKY_CONFIG.systemSizeChunks * 16 + radius;
        }
        if(originalBlockPos.getZ() + radius > (systemPoint.y+1) * StarrySkyCommon.STARRY_SKY_CONFIG.systemSizeChunks * 16 -1) {
            newX = (systemPoint.y+1) * StarrySkyCommon.STARRY_SKY_CONFIG.systemSizeChunks * 16 - radius;
        }

        return new BlockPos(newX, originalBlockPos.getY(), newZ);
    }


    private List<Spheroid> generateSpheroidsAtSystemPoint(Point systemPoint) {
        int systemPointX = systemPoint.x;
        int systemPointZ = systemPoint.y;

        ChunkRandom systemRandom = getSystemRandom(systemPoint);
        ArrayList<Spheroid> spheroids = new ArrayList<>();

        // Places a log/leaf planet at 16, 16 in the overworld etc.
        ArrayList<Spheroid> defaultSpheroids = getDefaultSpheroids(systemPointX, systemPointZ, systemRandom);
        spheroids.addAll(defaultSpheroids);

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

        StarrySkyCommon.log(DEBUG, "Created a new system with " + spheroids.size() + " spheroids at system position " + systemPointX + "," + systemPointZ);

        return spheroids;
    }

    private ArrayList<Spheroid> getDefaultSpheroids(int systemPointX, int systemPointZ, ChunkRandom random) {
        ArrayList<Spheroid> defaultSpheroids = new ArrayList<>();
        Spheroid spheroid;
        switch (this.spheroidDimensionType) {
            case NETHER:
                if (systemPointX == 0 && systemPointZ == 0) {
                    spheroid = SpheroidListVanillaNether.NETHERRACK.getRandomSpheroid(random);
                    spheroid.setPositionAndCalculateChunks(new BlockPos(16, 70, 16));
                    defaultSpheroids.add(spheroid);
                }
                break;
            case END:
                if (systemPointX == 0 && systemPointZ == 0) {
                    spheroid = SpheroidListVanillaEnd.END_SPAWN_WITH_PORTAL_AND_DRAGON.getRandomSpheroid(random);
                    spheroid.setPositionAndCalculateChunks(new BlockPos(0, 29, 0));
                    defaultSpheroids.add(spheroid);
                } else if ((systemPointX == -1 && systemPointZ ==  0)
                        || (systemPointX ==  0 && systemPointZ == -1)
                        || (systemPointX == -1 && systemPointZ == -1)) {
                    spheroid = SpheroidListVanillaEnd.END_SPAWN_WITH_PORTAL.getRandomSpheroid(random);
                    spheroid.setPositionAndCalculateChunks(new BlockPos(0, 29, 0));
                    defaultSpheroids.add(spheroid);
                }
                break;
            default:
                if (systemPointX == 0 && systemPointZ == 0) {
                    spheroid = SpheroidListVanilla.OAK_WOOD.getRandomSpheroid(random);
                    spheroid.setPositionAndCalculateChunks(new BlockPos(16, 70, 16));
                    defaultSpheroids.add(spheroid);
                }
        }
        return defaultSpheroids;
    }

    private Spheroid getRandomSpheroid(ChunkRandom systemRandom) {
        SpheroidType spheroidType;
        do {
            spheroidType = SpheroidLoader.getWeightedRandomSpheroid(spheroidDimensionType, systemRandom);
        } while(spheroidType == null);

        StarrySkyCommon.log(DEBUG, "Created a new sphere of type " + spheroidType + " Next random: " + systemRandom.nextInt());
        return spheroidType.getRandomSpheroid(systemRandom);
    }

}
