package de.dafuqs.starryskies.dimension;

import com.mojang.serialization.Codec;
import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.spheroids.spheroids.Spheroid;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpheroidDecoratorFeature extends Feature<DefaultFeatureConfig> {
	
	public SpheroidDecoratorFeature(Codec<DefaultFeatureConfig> configCodec) {
		super(DefaultFeatureConfig.CODEC);
	}
	
	@Override
	public boolean generate(@NotNull FeatureContext featureContext) {
		if (featureContext.getGenerator() instanceof StarrySkyChunkGenerator) {
			SystemGenerator systemGenerator = SystemGenerator.getSystemGeneratorOfWorld(featureContext.getWorld().toServerWorld().getRegistryKey());
			List<Spheroid> localSystem = systemGenerator.getSystemAtChunkPos(featureContext.getOrigin().getX() / 16, featureContext.getOrigin().getZ() / 16);
			for (Spheroid spheroid : localSystem) {
				if (spheroid.isInChunk(new ChunkPos(featureContext.getOrigin()))) {
					
					StarrySkies.log(Level.DEBUG, "Decorating spheroid at x:" + featureContext.getOrigin().getX() + " z:" + featureContext.getOrigin().getZ() + spheroid.getDescription());
					spheroid.decorate(featureContext.getWorld(), featureContext.getOrigin(), featureContext.getRandom());
					StarrySkies.log(Level.DEBUG, "Finished decorating.");
				}
			}
		}
		return false;
	}
	
}
