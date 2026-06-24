package de.dafuqs.starryskies.worldgen.spheres;

import com.mojang.datafixers.util.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.state_providers.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.chunk.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.*;
import org.jspecify.annotations.*;

import java.util.*;

public class CoreSphere extends Sphere<CoreSphere.Config> {
	
	public CoreSphere(Codec<CoreSphere.Config> codec) {
		super(codec);
	}
	
	@Override
	public PlacedSphere<?> generate(ConfiguredSphere<? extends Sphere<CoreSphere.Config>, Config> configuredSphere, Config config, WorldgenRandom random, WorldGenLevel level, BlockPos pos, float radius) {
		return new CoreSphere.Placed(configuredSphere, radius, configuredSphere.getDecorators(random), configuredSphere.getSpawns(random), random, config.mainBlock.getForSphere(level, random, pos), config.coreBlock.getForSphere(level, random, pos),
				Math.max(radius - 1, config.coreRadius.sample(random)) // enforce a min shell of 1 block
		);
	}
	
	public static class Config extends SphereConfig {
		
		public static final Codec<CoreSphere.Config> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
				SphereConfig.CONFIG_CODEC.forGetter((config) -> config),
				SphereStateProvider.CODEC.fieldOf("main_block").forGetter((config) -> config.mainBlock),
				SphereStateProvider.CODEC.fieldOf("core_block").forGetter((config) -> config.coreBlock),
				FloatProviders.codec(1.0F, 32.0F).fieldOf("core_radius").forGetter((config) -> config.coreRadius)
		).apply(instance, (sphereConfig, mainBlock, coreBlock, shellThickness) -> new Config(sphereConfig.size, sphereConfig.decorators, sphereConfig.spawns, sphereConfig.generation, mainBlock, coreBlock, shellThickness)));
		
		protected final SphereStateProvider mainBlock;
		protected final SphereStateProvider coreBlock;
		protected final FloatProvider coreRadius;
		
		public Config(FloatProvider size, Map<Holder<ConfiguredSphereDecorator<?, ?>>, Float> decorators, List<SphereEntitySpawnDefinition> spawns, @Nullable Generation generation, SphereStateProvider coreBlock, SphereStateProvider mainBlock, FloatProvider coreRadius) {
			super(size, decorators, spawns, generation);
			this.coreBlock = coreBlock;
			this.mainBlock = mainBlock;
			this.coreRadius = coreRadius;
		}
		
	}
	
	public static class Placed extends PlacedSphere<CoreSphere.Config> {
		
		private final BlockStateProvider mainBlock;
		private final BlockStateProvider coreBlock;
		private final float coreRadius;

        public Placed(ConfiguredSphere<? extends Sphere<CoreSphere.Config>, CoreSphere.Config> configuredSphere, float radius, List<Holder<ConfiguredSphereDecorator<?, ?>>> decorators, List<Pair<EntityType<?>, Integer>> spawns, WorldgenRandom random,
                      BlockStateProvider coreBlock, BlockStateProvider mainBlock, float coreRadius) {
			super(configuredSphere, radius, decorators, spawns, random);
			this.coreBlock = coreBlock;
			this.mainBlock = mainBlock;
			this.coreRadius = coreRadius;
		}
		
		@Override
		public void generate(ChunkAccess chunk, WorldGenLevel level) {
			int chunkX = chunk.getPos().x();
			int chunkZ = chunk.getPos().z();
			random.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
			BlockPos spherePos = this.getPosition();
			int x = spherePos.getX();
			int y = spherePos.getY();
			int z = spherePos.getZ();
			
			int ceiledRadius = (int) Math.ceil(this.radius);
			int maxX = Math.min(chunkX * 16 + 15, x + ceiledRadius);
			int maxZ = Math.min(chunkZ * 16 + 15, z + ceiledRadius);
			
			BlockPos.MutableBlockPos currBlockPos = new BlockPos.MutableBlockPos();
			for (int x2 = Math.max(chunkX * 16, x - ceiledRadius); x2 <= maxX; x2++) {
				for (int y2 = y - ceiledRadius; y2 <= y + ceiledRadius; y2++) {
					for (int z2 = Math.max(chunkZ * 16, z - ceiledRadius); z2 <= maxZ; z2++) {
						long d = Math.round(Support.getDistance(x, y, z, x2, y2, z2));
						if (d > this.radius) {
							continue;
						}
						currBlockPos.set(x2, y2, z2);
						
						if (d < this.coreRadius) {
							chunk.setBlockState(currBlockPos, this.coreBlock.getState(level, random, currBlockPos));
						} else {
							chunk.setBlockState(currBlockPos, this.mainBlock.getState(level, random, currBlockPos));
						}
					}
				}
			}
		}
		
		@Override
		public String getDescription(RegistryAccess registryManager) {
			return "+++ CoreSphere +++" +
					"\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
					"\nTemplateID: " + this.getID(registryManager) +
					"\nRadius: " + this.radius +
					"\nMain: " + this.mainBlock.toString() +
					"\nCore: " + this.coreBlock.toString() + " (Radius: " + this.coreRadius + ")";
		}
	}
	
}
	
