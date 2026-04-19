package de.dafuqs.starryskies.worldgen.spheres;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.*;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import org.jspecify.annotations.Nullable;

import java.util.*;

public class HorizontalStackedSphere extends Sphere<HorizontalStackedSphere.Config> {
	
	public HorizontalStackedSphere(Codec<Config> codec) {
		super(codec);
	}
	
	@Override
	public PlacedSphere<?> generate(ConfiguredSphere<? extends Sphere<HorizontalStackedSphere.Config>, HorizontalStackedSphere.Config> configuredSphere, HorizontalStackedSphere.Config config, WorldgenRandom random, WorldGenLevel level, BlockPos pos, float radius) {
		return new Placed(configuredSphere, radius, configuredSphere.getDecorators(random), configuredSphere.getSpawns(random), random, config.states);
	}
	
	public static class Config extends SphereConfig {
		
		public static final Codec<HorizontalStackedSphere.Config> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
				SphereConfig.CONFIG_CODEC.forGetter((config) -> config),
				BlockState.CODEC.listOf().fieldOf("blocks").forGetter((config) -> config.states)
		).apply(instance, (sphereConfig, states) -> new Config(sphereConfig.size, sphereConfig.decorators, sphereConfig.spawns, sphereConfig.generation, states)));
		
		protected final List<BlockState> states;
		
		public Config(FloatProvider size, Map<Holder<ConfiguredSphereDecorator<?, ?>>, Float> decorators, List<SphereEntitySpawnDefinition> spawns, @Nullable Generation generation, List<BlockState> states) {
			super(size, decorators, spawns, generation);
			this.states = states;
		}
		
	}
	
	public static class Placed extends PlacedSphere<HorizontalStackedSphere.Config> {
		
		private final List<BlockState> states;
		
		public Placed(ConfiguredSphere<? extends Sphere<HorizontalStackedSphere.Config>, HorizontalStackedSphere.Config> configuredSphere, float radius, List<Holder<ConfiguredSphereDecorator<?, ?>>> decorators,
                      List<Tuple<EntityType<?>, Integer>> spawns, WorldgenRandom random, List<BlockState> states) {
			super(configuredSphere, radius, decorators, spawns, random);
			this.states = states;
		}
		
		@Override
		public String getDescription(RegistryAccess registryManager) {
			return "+++ HorizontalStackedSphere +++" +
					"\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
					"\nTemplateID: " + this.getID(registryManager) +
					"\nRadius: " + this.radius;
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
			for (int y2 = y - ceiledRadius; y2 <= y + ceiledRadius; y2++) {
				float currentSphereHeight = y - y2 + ceiledRadius;
				int currentBlockStateIndex = (int) ((currentSphereHeight * states.size() - 1) / (ceiledRadius * 2));
				BlockState currentBlockState = this.states.get(currentBlockStateIndex);
				
				for (int x2 = Math.max(chunkX * 16, x - ceiledRadius); x2 <= maxX; x2++) {
					for (int z2 = Math.max(chunkZ * 16, z - ceiledRadius); z2 <= maxZ; z2++) {
						long d = Math.round(Support.getDistance(x, y, z, x2, y2, z2));
						if (d > this.radius) {
							continue;
						}
						
						currBlockPos.set(x2, y2, z2);
						chunk.setBlockState(currBlockPos, currentBlockState);
					}
				}
			}
		}
		
	}
	
}
