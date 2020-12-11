package de.dafuqs.starrysky;

import de.dafuqs.starrysky.dimension.SystemGenerator;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldAccess;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

public class Support {

    public static class SpheroidDistance {
        public Spheroid spheroid;
        public double squaredDistance;

        public SpheroidDistance(Spheroid spheroid, double squaredDistance) {
            this.spheroid = spheroid;
            this.squaredDistance = squaredDistance;
        }
    }

    public static SpheroidDistance getClosestSpheroidToPlayer(PlayerEntity serverPlayerEntity) {
        Vec3d playerPos = serverPlayerEntity.getPos();
        BlockPos playerPosBlock = new BlockPos((int) playerPos.x, (int) playerPos.y, (int) playerPos.z);
        List<Spheroid> localSystem = SystemGenerator.getSystemGeneratorOfWorld(serverPlayerEntity.getEntityWorld().getRegistryKey()).getSystemAtChunkPos(playerPosBlock.getX() / 16, playerPosBlock.getZ() / 16);

        Spheroid closestSpheroid = null;
        double currentMinDistance = Double.MAX_VALUE;
        for (Spheroid p : localSystem) {
            double currDist = playerPosBlock.getSquaredDistance(p.getPosition());
            if(currDist < currentMinDistance) {
                currentMinDistance = currDist;
                closestSpheroid = p;
            }
        }

        return new SpheroidDistance(closestSpheroid, currentMinDistance);
    }

    public static <E> E getWeightedRandom(LinkedHashMap<E, Float> weights, Random random) {
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

    /**
     * The bounds values lowest and highest are part of the result set
     * @param lowest
     * @param highest
     * @return
     */
    public static int getRandomBetween(Random random, int lowest, int highest) {
        return random.nextInt(highest - lowest + 1) + lowest;
    }

    public static double getDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2));
    }

    public static double getDistance(BlockPos blockPos1, BlockPos blockpos2) {
        return getDistance(blockPos1.getX(), blockPos1.getY(), blockPos1.getZ(), blockpos2.getX(), blockpos2.getY(), blockpos2.getZ());
    }

    public static boolean isBlockPosInChunkPos(ChunkPos chunkPos, BlockPos blockPos) {
        return (blockPos.getX() >= chunkPos.getStartX()
                && blockPos.getX() < chunkPos.getStartX() + 16
                && blockPos.getZ() >= chunkPos.getStartZ()
                && blockPos.getZ() < chunkPos.getStartZ() + 16);
    }

    public static int getLowerGroundBlock(WorldAccess world, BlockPos position, int minHeight) {
        BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position.getX(), position.getY(), position.getZ());

        //if height is an air block, move down until we reached a solid block. We are now on the surface of a piece of land
        while (blockpos$Mutable.getY() > minHeight) {
            if (!world.isAir(blockpos$Mutable)) {
                break;
            }
            blockpos$Mutable.move(Direction.DOWN);
        }
        return blockpos$Mutable.getY();
    }

    public static int getUpperGroundBlock(WorldAccess world, BlockPos position, int minHeight) {
        BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position.getX(), position.getY(), position.getZ());

        //if height is an air block, move down until we reached a solid block. We are now on the surface of a piece of land
        while (blockpos$Mutable.getY() > minHeight) {
            if (!world.isAir(blockpos$Mutable)) {
                return blockpos$Mutable.getY();
            }
            blockpos$Mutable.move(Direction.UP);
        }
        return -1;
    }

}
