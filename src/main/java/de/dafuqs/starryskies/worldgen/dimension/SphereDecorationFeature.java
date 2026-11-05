package de.dafuqs.starryskies.worldgen.dimension;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.chunk.*;
import net.minecraft.world.level.levelgen.feature.*;
import org.jspecify.annotations.*;

public enum SphereDecorationFeature implements Feature {
    INSTANCE;
	
	@Override
    public boolean place(@NonNull WorldGenLevel level, @NonNull ChunkGenerator chunkGenerator, @NonNull RandomSource random, @NonNull BlockPos origin) {
        if (chunkGenerator instanceof StarrySkyChunkGenerator starrySkyChunkGenerator) {
			SystemGenerator systemGenerator = starrySkyChunkGenerator.getSystemGenerator();

            for (PlacedSphere<?> sphere : systemGenerator.getSystem(level, origin)) {
                if (sphere.isInChunk(ChunkPos.containing(origin))) {
                    sphere.decorate(level, origin, random);
				}
			}
		}
		return false;
	}

    @Override
    public @NonNull MapCodec<SphereDecorationFeature> codec() {
        return MapCodec.unit(INSTANCE);
    }
}
