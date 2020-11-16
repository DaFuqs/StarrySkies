package de.dafuqs.starrysky;

import de.dafuqs.starrysky.spheroids.Spheroid;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.ChunkRandom;

import java.util.List;
import java.util.Map;
import java.util.Random;

import static de.dafuqs.starrysky.generation.StarrySkyChunkGenerator.systemGenerator;

public class Support {

    public static class SpheroidDistance {
        public Spheroid spheroid;
        public double distance;

        public SpheroidDistance(Spheroid spheroid, double distance) {
            this.spheroid = spheroid;
            this.distance = distance;
        }
    }

    public static SpheroidDistance getClosestSpheroidToPlayer(ServerPlayerEntity serverPlayerEntity) {
        Vec3d playerPos = serverPlayerEntity.getPos();
        BlockPos playerPosBlock = new BlockPos((int) playerPos.x, (int) playerPos.y, (int) playerPos.z);
        List<Spheroid> localSystem = systemGenerator.getSystemAtChunkPos( playerPosBlock.getX() / 16, playerPosBlock.getZ() / 16);

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

    public static <E> E getWeightedRandom(Map<E, Float> weights, ChunkRandom random) {
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

    public static double distance(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2));
    }

    public static double distance(BlockPos blockPos1, BlockPos blockpos2) {
        return distance(blockPos1.getX(), blockPos1.getY(), blockPos1.getZ(), blockpos2.getX(), blockpos2.getY(), blockpos2.getZ());
    }

}
