package de.dafuqs.starryskies.worldgen.dimension;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.jetbrains.annotations.*;

public class SphereDecorationFeature extends Feature<NoneFeatureConfiguration> {

	public SphereDecorationFeature(Codec<NoneFeatureConfiguration> configCodec) {
		super(configCodec);
	}

	@Override
	public boolean place(@NotNull FeaturePlaceContext featureContext) {
		if (featureContext.chunkGenerator() instanceof StarrySkyChunkGenerator starrySkyChunkGenerator) {
			SystemGenerator systemGenerator = starrySkyChunkGenerator.getSystemGenerator();
			
			for (PlacedSphere<?> sphere : systemGenerator.getSystem(featureContext.level(), featureContext.origin())) {
				if (sphere.isInChunk(new ChunkPos(featureContext.origin()))) {
					sphere.decorate(featureContext.level(), featureContext.origin(), featureContext.random());
				}
			}
		}
		return false;
	}

}
