package de.dafuqs.starryskies;

import com.mojang.datafixers.util.*;
import de.dafuqs.starryskies.worldgen.*;
import de.dafuqs.starryskies.worldgen.dimension.*;
import net.minecraft.core.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import org.jspecify.annotations.*;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.function.*;

public class Support {

	private static final List<Point> AROUND_POINTS = new ArrayList<>() {{
		add(new Point(0, 0));
		add(new Point(1, -1));
		add(new Point(1, 0));
		add(new Point(1, 1));
		add(new Point(0, -1));
		add(new Point(0, 1));
		add(new Point(-1, -1));
		add(new Point(-1, 0));
		add(new Point(-1, 1));
	}};
	
	public static Optional<SphereDistance> getClosestSphere(ServerLevel world, BlockPos pos) {
		if (!(world.getChunkSource().getGenerator() instanceof StarrySkyChunkGenerator starrySkyChunkGenerator)) {
			return Optional.empty();
		}
		
		SystemGenerator systemGenerator = starrySkyChunkGenerator.getSystemGenerator();
		if (systemGenerator != null) {
			PlacedSphere<?> closestSphere = null;
			double currentMinDistance = Double.MAX_VALUE;
			
			for (PlacedSphere<?> p : systemGenerator.getSystem(world, pos)) {
				double currDist = pos.distSqr(p.getPosition());
				if (currDist < currentMinDistance) {
					currentMinDistance = currDist;
					closestSphere = p;
				}
			}
			
			return Optional.of(new SphereDistance(closestSphere, currentMinDistance));
		} else {
			return Optional.empty();
		}
	}
	
	public static Optional<Pair<BlockPos, Holder<ConfiguredSphere<?, ?>>>> getClosestSphere3x3(@NonNull ServerLevel serverWorld, BlockPos position, Predicate<Holder<ConfiguredSphere<?, ?>>> predicate, RegistryAccess registryManager) {
		if (!(serverWorld.getChunkSource().getGenerator() instanceof StarrySkyChunkGenerator starrySkyChunkGenerator)) {
			return Optional.empty();
		}
		
		SystemGenerator systemGenerator = starrySkyChunkGenerator.getSystemGenerator();
		
		PlacedSphere<?> closestSphere = null;
		double currentMinDistance = Double.MAX_VALUE;
		for (Point currentPoint : AROUND_POINTS) {
			Point systemPos = getSystemCoordinateFromChunkCoordinate(position.getX() / 16, position.getZ() / 16);
			
			for (PlacedSphere<?> p : systemGenerator.getSystem(serverWorld, new Point(systemPos.x + currentPoint.x, systemPos.y + currentPoint.y))) {
				if (predicate.test(p.getRegistryEntry(registryManager))) {
					double currDist = position.distSqr(p.getPosition());
					if (currDist < currentMinDistance) {
						currentMinDistance = currDist;
						closestSphere = p;
					}
				}
			}
			
			if (closestSphere != null) {
				return Optional.of(new Pair<>(closestSphere.getPosition(), closestSphere.getRegistryEntry(registryManager)));
			}
		}
		
		return Optional.empty();
	}

	public static <E> E getWeightedRandom(@NonNull Map<E, Float> weights, RandomSource random) {
		E result = null;
		double bestValue = Double.MAX_VALUE;

		for (E element : weights.keySet()) {
			double value = -Math.log(random.nextDouble()) / (weights.get(element));

			if (value < bestValue) {
				bestValue = value;
				result = element;
			}
		}
		return result;
	}

	public static @NonNull Point getSystemCoordinateFromChunkCoordinate(int chunkX, int chunkZ) {
		int systemSizeChunks = StarrySkies.CONFIG.systemSizeChunks;

		int sysX;
		if (chunkX >= 0) {
			sysX = chunkX / systemSizeChunks;
		} else {
			sysX = (int) Math.floor(chunkX / (float) systemSizeChunks);
		}

		int sysZ;
		if (chunkZ >= 0) {
			sysZ = chunkZ / systemSizeChunks;
		} else {
			sysZ = (int) Math.floor(chunkZ / (float) systemSizeChunks);
		}

		return new Point(sysX, sysZ);
	}

	/**
	 * Returns a random number between lowest and highest
	 *
	 * @param lowest  The lowest number (inclusive)
	 * @param highest The highest number (inclusive)
	 * @return The random number between lowest and highest
	 */
	public static int getRandomBetween(@NonNull RandomSource random, int lowest, int highest) {
		return lowest + random.nextInt(highest - lowest + 1);
	}

	public static float getRandomBetween(@NonNull RandomSource random, float lowest, float highest) {
		return lowest + random.nextFloat() * (highest - lowest);
	}

	public static double getDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2));
	}

	public static double getDistance(@NonNull BlockPos blockPos1, @NonNull BlockPos blockpos2) {
		return getDistance(blockPos1.getX(), blockPos1.getY(), blockPos1.getZ(), blockpos2.getX(), blockpos2.getY(), blockpos2.getZ());
	}

	public static boolean isBlockPosInChunkPos(@NonNull ChunkPos chunkPos, @NonNull BlockPos blockPos) {
		return (blockPos.getX() >= chunkPos.getMinBlockX()
				&& blockPos.getX() < chunkPos.getMinBlockX() + 16
				&& blockPos.getZ() >= chunkPos.getMinBlockZ()
				&& blockPos.getZ() < chunkPos.getMinBlockZ() + 16);
	}

	public static int getLowerGroundBlock(LevelAccessor world, @NonNull BlockPos position, int minHeight) {
		BlockPos.MutableBlockPos blockPos$Mutable = new BlockPos.MutableBlockPos(position.getX(), position.getY(), position.getZ());

		//if height is an air block, move down until we reached a solid block. We are now on the surface of a piece of land
		while (blockPos$Mutable.getY() > minHeight) {
			if (!world.isEmptyBlock(blockPos$Mutable)) {
				break;
			}
			blockPos$Mutable.move(Direction.DOWN);
		}
		return blockPos$Mutable.getY();
	}

	public static int getUpperGroundBlock(LevelAccessor world, @NonNull BlockPos position, int minHeight) {
		BlockPos.MutableBlockPos blockPos$Mutable = new BlockPos.MutableBlockPos(position.getX(), position.getY(), position.getZ());

		//if height is an air block, move down until we reached a solid block. We are now on the surface of a piece of land
		while (blockPos$Mutable.getY() > minHeight) {
			if (!world.isEmptyBlock(blockPos$Mutable)) {
				return blockPos$Mutable.getY();
			}
			blockPos$Mutable.move(Direction.UP);
		}
		return -1;
	}
	
	public static class SphereDistance {
		public PlacedSphere<?> sphere;
		public double squaredDistance;
		
		public SphereDistance(PlacedSphere<?> sphere, double squaredDistance) {
			this.sphere = sphere;
			this.squaredDistance = squaredDistance;
		}
	}
	
}
