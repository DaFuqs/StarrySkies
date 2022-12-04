package de.dafuqs.starryskies.dimension;

import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.Support;
import de.dafuqs.starryskies.data_loaders.SpheroidTemplateLoader;
import de.dafuqs.starryskies.spheroids.spheroids.Spheroid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.apache.logging.log4j.Level.DEBUG;

public class SystemGenerator {
	
	public static HashMap<SpheroidDimensionType, SystemGenerator> systemGeneratorMap = new HashMap<>();
	
	// spawning probabilities
	private final SpheroidDimensionType spheroidDimensionType;
	private final HashMap<Point, List<de.dafuqs.starryskies.spheroids.spheroids.Spheroid>> cache = new HashMap<>();
	public static SpheroidTemplateLoader spheroidLoader;
	
	private final int SYSTEM_SIZE_CHUNKS;
	private final int MIN_DISTANCE_BETWEEN_SPHERES;
	private final int SPHERE_DENSITY;
	private final int FLOOR_HEIGHT;
	
	public static SystemGenerator getSystemGeneratorOfWorld(@NotNull RegistryKey<World> worldRegistryKey) {
		if (worldRegistryKey.equals(StarrySkyDimension.OVERWORLD_KEY)) {
			return systemGeneratorMap.get(SpheroidDimensionType.OVERWORLD);
		} else if (worldRegistryKey.equals(StarrySkyDimension.NETHER_KEY)) {
			return systemGeneratorMap.get(SpheroidDimensionType.NETHER);
		} else {
			return systemGeneratorMap.get(SpheroidDimensionType.END);
		}
	}
	
	public static class TempPosition {
		int xPos;
		int yPos;
		int zPos;
		
		public BlockPos toBlockPos() {
			return new BlockPos(xPos, yPos, zPos);
		}
		
		private int distanceSquared(@NotNull de.dafuqs.starryskies.spheroids.spheroids.Spheroid pl1) {
			int xDist = xPos - pl1.getPosition().getX();
			int yDist = yPos - pl1.getPosition().getY();
			int zDist = zPos - pl1.getPosition().getZ();
			
			return xDist * xDist + yDist * yDist + zDist * zDist;
		}
	}
	
	public SystemGenerator(SpheroidDimensionType spheroidDimensionType) {
		this.spheroidDimensionType = spheroidDimensionType;
		systemGeneratorMap.put(spheroidDimensionType, this);
		
		this.SYSTEM_SIZE_CHUNKS = StarrySkies.CONFIG.systemSizeChunks;
		switch (spheroidDimensionType) {
			case OVERWORLD -> {
				this.MIN_DISTANCE_BETWEEN_SPHERES = StarrySkies.CONFIG.minDistanceBetweenSpheresOverworld;
				this.SPHERE_DENSITY = StarrySkies.CONFIG.sphereDensityOverworld;
				this.FLOOR_HEIGHT = StarrySkies.CONFIG.floorHeightOverworld;
			}
			case NETHER -> {
				this.MIN_DISTANCE_BETWEEN_SPHERES = StarrySkies.CONFIG.minDistanceBetweenSpheresNether;
				this.SPHERE_DENSITY = StarrySkies.CONFIG.sphereDensityNether;
				this.FLOOR_HEIGHT = StarrySkies.CONFIG.floorHeightNether;
			}
			default -> {
				this.MIN_DISTANCE_BETWEEN_SPHERES = StarrySkies.CONFIG.minDistanceBetweenSpheresEnd;
				this.SPHERE_DENSITY = StarrySkies.CONFIG.sphereDensityEnd;
				this.FLOOR_HEIGHT = StarrySkies.CONFIG.floorHeightEnd;
			}
		}
	}
	
	/**
	 * Returns the system at the given chunk coordinates
	 * If a system does not exist yet it will be generated
	 *
	 * @param chunkX chunk chunkX location
	 * @param chunkZ chunk chunkZ location
	 * @return List of planetoids representing the system this chunk is in
	 */
	public List<de.dafuqs.starryskies.spheroids.spheroids.Spheroid> getSystemAtChunkPos(int chunkX, int chunkZ) {
		Point systemPos = Support.getSystemCoordinateFromChunkCoordinate(chunkX, chunkZ);
		return getSystemAtPoint(systemPos);
	}
	
	public List<de.dafuqs.starryskies.spheroids.spheroids.Spheroid> getSystemAtPoint(Point systemPos) {
		List<de.dafuqs.starryskies.spheroids.spheroids.Spheroid> curSystem = cache.get(systemPos);
		
		if (curSystem == null) {
			//doesn't exist. Generate new system and cache it
			curSystem = generateSpheroidsAtSystemPoint(systemPos);
			cache.put(systemPos, curSystem);
		}
		
		return curSystem;
	}
	
	private @NotNull ChunkRandom getSystemRandom(@NotNull Point systemPoint) {
		int firstChunkPosX = systemPoint.x * SYSTEM_SIZE_CHUNKS;
		int firstChunkPosZ = systemPoint.y * SYSTEM_SIZE_CHUNKS;
		ChunkRandom systemRandom = new ChunkRandom(new CheckedRandom(StarrySkies.starryWorld.getSeed()));
		systemRandom.setCarverSeed(StarrySkies.starryWorld.getSeed(), firstChunkPosX, firstChunkPosZ); // and the seed from the first chunk+
		StarrySkies.log(DEBUG, "Generated seed for system at " + systemPoint.x + "," + systemPoint.y + "(first chunk: " + firstChunkPosX + "," + firstChunkPosZ);
		return systemRandom;
	}
	
	@Contract("_, _, _ -> new")
	private @NotNull BlockPos getBlockPosInSystem(@NotNull Point systemPoint, int radius, @NotNull BlockPos originalBlockPos) {
		int newX = originalBlockPos.getX();
		int newZ = originalBlockPos.getZ();
		
		if (originalBlockPos.getX() - radius < systemPoint.x * StarrySkies.CONFIG.systemSizeChunks * 16) {
			newX = systemPoint.x * StarrySkies.CONFIG.systemSizeChunks * 16 + radius;
		}
		if (originalBlockPos.getX() + radius > (systemPoint.x + 1) * StarrySkies.CONFIG.systemSizeChunks * 16 - 1) {
			newX = (systemPoint.x + 1) * StarrySkies.CONFIG.systemSizeChunks * 16 - radius;
		}
		if (originalBlockPos.getZ() - radius < systemPoint.y * StarrySkies.CONFIG.systemSizeChunks * 16) {
			newX = systemPoint.y * StarrySkies.CONFIG.systemSizeChunks * 16 + radius;
		}
		if (originalBlockPos.getZ() + radius > (systemPoint.y + 1) * StarrySkies.CONFIG.systemSizeChunks * 16 - 1) {
			newX = (systemPoint.y + 1) * StarrySkies.CONFIG.systemSizeChunks * 16 - radius;
		}
		
		return new BlockPos(newX, originalBlockPos.getY(), newZ);
	}
	
	
	private @NotNull List<de.dafuqs.starryskies.spheroids.spheroids.Spheroid> generateSpheroidsAtSystemPoint(@NotNull Point systemPoint) {
		int systemPointX = systemPoint.x;
		int systemPointZ = systemPoint.y;
		
		ChunkRandom systemRandom = getSystemRandom(systemPoint);
		
		// Places a log/leaf planet at 16, 16 in the overworld etc.
		ArrayList<de.dafuqs.starryskies.spheroids.spheroids.Spheroid> defaultSpheroids = getDefaultSpheroids(systemPointX, systemPointZ, systemRandom);
		ArrayList<de.dafuqs.starryskies.spheroids.spheroids.Spheroid> spheroids = new ArrayList<>(defaultSpheroids);
		
		// try to create DENSITY planets in system
		int worldHeight = StarrySkies.starryWorld.getHeight();
		for (int currentDensity = 0; currentDensity < SPHERE_DENSITY; currentDensity++) {
			
			// create new planets
			de.dafuqs.starryskies.spheroids.spheroids.Spheroid currentSpheroid = getRandomSpheroid(systemRandom);
			TempPosition tempPosition = new TempPosition();
			
			// set position, check bounds with system edges on x and z
			tempPosition.xPos = Support.getRandomBetween(systemRandom, (int) currentSpheroid.getRadius(), (int) (SYSTEM_SIZE_CHUNKS * 16 - currentSpheroid.getRadius()));
			tempPosition.xPos += SYSTEM_SIZE_CHUNKS * 16 * systemPointX;
			tempPosition.zPos = Support.getRandomBetween(systemRandom, (int) currentSpheroid.getRadius(), (int) (SYSTEM_SIZE_CHUNKS * 16 - currentSpheroid.getRadius()));
			tempPosition.zPos += SYSTEM_SIZE_CHUNKS * 16 * systemPointZ;
			tempPosition.yPos = systemRandom.nextInt((int) ((worldHeight - currentSpheroid.getRadius() * 2 - FLOOR_HEIGHT) + currentSpheroid.getRadius() + FLOOR_HEIGHT));
			
			// check for collisions with existing spheroids
			// if any collision, discard it
			boolean discard = false;
			for (de.dafuqs.starryskies.spheroids.spheroids.Spheroid spheroid : spheroids) {
				//each spheroid has to be at least pl1.radius + pl2.radius + min distance apart
				int distMin = (int) (spheroid.getRadius() + currentSpheroid.getRadius() + MIN_DISTANCE_BETWEEN_SPHERES);
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
		
		StarrySkies.log(DEBUG, "Created a new system with " + spheroids.size() + " spheroids at system position " + systemPointX + "," + systemPointZ);
		
		return spheroids;
	}
	
	private ArrayList<de.dafuqs.starryskies.spheroids.spheroids.Spheroid> getDefaultSpheroids(int systemPointX, int systemPointZ, ChunkRandom random) {
		ArrayList<de.dafuqs.starryskies.spheroids.spheroids.Spheroid> defaultSpheroids = new ArrayList<>();
		de.dafuqs.starryskies.spheroids.spheroids.Spheroid spheroid;
		switch (this.spheroidDimensionType) {
			case NETHER:
				if (systemPointX == 0 && systemPointZ == 0) {
					spheroid = SpheroidTemplateLoader.STARTER_NETHER.generate(random);
					spheroid.setPositionAndCalculateChunks(new BlockPos(16, 70, 16));
					defaultSpheroids.add(spheroid);
				}
				break;
			case END:
				if (systemPointX == 0 && systemPointZ == 0) {
					spheroid = SpheroidTemplateLoader.STARTER_END_DRAGON.generate(random);
					spheroid.setPositionAndCalculateChunks(new BlockPos(0, 30, 0));
					defaultSpheroids.add(spheroid);
				} else if ((systemPointX == -1 && systemPointZ == 0)
						|| (systemPointX == 0 && systemPointZ == -1)
						|| (systemPointX == -1 && systemPointZ == -1)) {
					spheroid = SpheroidTemplateLoader.STARTER_END.generate(random);
					spheroid.setPositionAndCalculateChunks(new BlockPos(0, 30, 0));
					defaultSpheroids.add(spheroid);
				}
				break;
			default:
				if (systemPointX == 0 && systemPointZ == 0) {
					spheroid = SpheroidTemplateLoader.STARTER_OVERWORLD.generate(random);
					spheroid.setPositionAndCalculateChunks(new BlockPos(16, 70, 16));
					defaultSpheroids.add(spheroid);
				}
		}
		return defaultSpheroids;
	}
	
	private de.dafuqs.starryskies.spheroids.spheroids.Spheroid getRandomSpheroid(ChunkRandom systemRandom) {
		Spheroid.Template template;
		do {
			template = SpheroidTemplateLoader.getWeightedRandomSpheroid(spheroidDimensionType, systemRandom);
		} while (template == null);
		
		StarrySkies.log(DEBUG, "Created a new sphere of type " + template + " Next random: " + systemRandom.nextInt());
		return template.generate(systemRandom);
	}
	
}
