package de.dafuqs.starryskies.worldgen.spheres;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.state_providers.*;
import de.dafuqs.starryskies.worldgen.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import org.jspecify.annotations.Nullable;

import java.awt.*;
import java.util.List;
import java.util.*;

public class CaveSphere extends Sphere<CaveSphere.Config> {
	
	public CaveSphere(Codec<CaveSphere.Config> codec) {
		super(codec);
	}
	
	@Override
	public PlacedSphere<?> generate(ConfiguredSphere<? extends Sphere<CaveSphere.Config>, Config> configuredSphere, Config config, WorldgenRandom random, WorldGenLevel level, BlockPos pos, float radius) {
		BlockStateProvider shellProvider = config.shellBlock.getForSphere(level, random, pos);
		
		return new CaveSphere.Placed(configuredSphere, radius, configuredSphere.getDecorators(random), configuredSphere.getSpawns(random), random,
				shellProvider,
				config.topBlock != null ? config.topBlock.getForSphere(level, random, pos) : shellProvider,
				config.bottomBlock != null ? config.bottomBlock.getForSphere(level, random, pos) : shellProvider,
				config.caveFloorBlock != null  ? config.caveFloorBlock.getForSphere(level, random, pos) : shellProvider,
				config.shellThickness.sample(random));
	}
	
	public static class Config extends SphereConfig {
		
		public static final Codec<CaveSphere.Config> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
				SphereConfig.CONFIG_CODEC.forGetter((config) -> config),
				SphereStateProvider.CODEC.fieldOf("shell_block").forGetter((config) -> config.shellBlock),
				SphereStateProvider.CODEC.optionalFieldOf("top_block").forGetter((config) -> Optional.ofNullable(config.topBlock)),
				SphereStateProvider.CODEC.optionalFieldOf("bottom_block").forGetter((config) -> Optional.ofNullable(config.bottomBlock)),
				SphereStateProvider.CODEC.optionalFieldOf("cave_floor_block").forGetter((config) -> Optional.ofNullable(config.caveFloorBlock)),
				FloatProviders.codec(1.0F, 32.0F).fieldOf("shell_thickness").forGetter((config) -> config.shellThickness)
		).apply(instance, (sphereConfig, shellBlock, topBlock, bottomBlock, caveFloorBlock, shellRadius) -> new Config(sphereConfig.size, sphereConfig.decorators, sphereConfig.spawns, sphereConfig.generation, shellBlock, topBlock.orElse(null), bottomBlock.orElse(null), caveFloorBlock.orElse(null), shellRadius)));
		
		private final SphereStateProvider shellBlock;
		private final @Nullable SphereStateProvider topBlock;
		private final @Nullable SphereStateProvider bottomBlock;
		private final @Nullable SphereStateProvider caveFloorBlock;
		private final FloatProvider shellThickness;
		
		public Config(FloatProvider size, Map<Holder<ConfiguredSphereDecorator<?, ?>>, Float> decorators, List<SphereEntitySpawnDefinition> spawns, Generation generation, SphereStateProvider shellBlock,
                      @Nullable SphereStateProvider topBlock, @Nullable SphereStateProvider bottomBlock, @Nullable SphereStateProvider caveFloorBlock, FloatProvider shellThickness) {
			super(size, decorators, spawns, generation);
			
			this.shellBlock = shellBlock;
			this.topBlock = topBlock;
			this.bottomBlock = bottomBlock;
			this.caveFloorBlock = caveFloorBlock;
			this.shellThickness = shellThickness;
		}
		
	}
	
	public static class Placed extends PlacedSphere<CaveSphere.Config> {
		
		private final BlockStateProvider shellBlock;
		private final BlockStateProvider topBlock;
		private final BlockStateProvider bottomBlock;
		private final BlockStateProvider caveFloorBlock;
		private final float shellThickness;
		
		public Placed(ConfiguredSphere<? extends Sphere<Config>, Config> configuredSphere, float radius, List<Holder<ConfiguredSphereDecorator<?, ?>>> decorators, List<Tuple<EntityType<?>, Integer>> spawns, WorldgenRandom random,
                      BlockStateProvider shellBlock, BlockStateProvider topBlock, BlockStateProvider bottomBlock, BlockStateProvider caveFloorBlock, float shellRadius) {
			super(configuredSphere, radius, decorators, spawns, random);
			this.shellBlock = shellBlock;
			this.topBlock = topBlock;
			this.bottomBlock = bottomBlock;
			this.caveFloorBlock = caveFloorBlock;
			this.shellThickness = shellRadius;
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
			
			Map<Point, Integer> floorBlocks = new Object2ObjectArrayMap<>();
			
			BlockPos.MutableBlockPos currBlockPos = new BlockPos.MutableBlockPos();
			for (int x2 = Math.max(chunkX * 16, x - ceiledRadius); x2 <= maxX; x2++) {
				for (int y2 = y - ceiledRadius; y2 <= y + ceiledRadius; y2++) {
					for (int z2 = Math.max(chunkZ * 16, z - ceiledRadius); z2 <= maxZ; z2++) {
						long d = Math.round(Support.getDistance(x, y, z, x2, y2, z2));
						if (d > this.radius) {
							continue;
						}
						currBlockPos.set(x2, y2, z2);
						
						if (d > this.radius - 1) {
							if (isBottomBlock(d, x2, y2, z2)) {
								chunk.setBlockState(currBlockPos, this.bottomBlock.getState(level, random, currBlockPos));
							} else if (isTopBlock(d, x2, y2, z2)) {
								chunk.setBlockState(currBlockPos, this.topBlock.getState(level, random, currBlockPos));
							} else {
								chunk.setBlockState(currBlockPos, this.shellBlock.getState(level, random, currBlockPos));
							}
						} else if (d <= this.radius - this.shellThickness) {
							Point point = new Point(x2, z2);
							if (!floorBlocks.containsKey(point)) {
								floorBlocks.put(new Point(x2, z2), y2);
								chunk.setBlockState(currBlockPos.below(), this.caveFloorBlock.getState(level, random, currBlockPos));
							}
						} else if (d < this.radius) {
							chunk.setBlockState(currBlockPos, this.shellBlock.getState(level, random, currBlockPos));
						}
					}
				}
			}
		}
		
		@Override
		public String getDescription(RegistryAccess registryManager) {
			return "+++ CaveSphere +++" +
					"\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
					"\nTemplateID: " + this.getID(registryManager) +
					"\nRadius: " + this.radius +
					"\nShellBlock: " + this.shellBlock +
					"\nShellThickness: " + this.shellThickness +
					"\nCaveFloorBlock: " + this.caveFloorBlock +
					"\nTopBlock: " + this.topBlock +
					"\nBottomBlock: " + this.bottomBlock;
		}
	}
	
}
	
