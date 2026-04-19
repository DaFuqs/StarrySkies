package de.dafuqs.starryskies.worldgen.spheres;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.state_providers.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.util.*;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BuddingAmethystBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import org.jspecify.annotations.Nullable;

import java.util.*;

public class GeodeSphere extends Sphere<GeodeSphere.Config> {
	
	public GeodeSphere(Codec<GeodeSphere.Config> codec) {
		super(codec);
	}
	
	@Override
	public PlacedSphere<?> generate(ConfiguredSphere<? extends Sphere<GeodeSphere.Config>, Config> configuredSphere, Config config, WorldgenRandom random, WorldGenLevel level, BlockPos pos, float radius) {
		return new GeodeSphere.Placed(configuredSphere, radius, configuredSphere.getDecorators(random), configuredSphere.getSpawns(random), random,
				config.innerBlockState.getForSphere(level, random, pos), config.innerSpecklesBlockState.getForSphere(level, random, pos), config.speckleChance, config.innerSpecklesAttachedBlockState.getForSphere(level, random, pos), config.middleBlockState.getForSphere(level, random, pos), config.outerBlockState.getForSphere(level, random, pos));
	}
	
	public static class Config extends SphereConfig {
		
		public static final Codec<GeodeSphere.Config> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
				SphereConfig.CONFIG_CODEC.forGetter((config) -> config),
				SphereStateProvider.CODEC.fieldOf("inner_block").forGetter((config) -> config.innerBlockState),
				SphereStateProvider.CODEC.fieldOf("inner_speckles_block").forGetter((config) -> config.innerSpecklesBlockState),
				ExtraCodecs.POSITIVE_FLOAT.fieldOf("inner_speckles_block_chance").forGetter((config) -> config.speckleChance),
				SphereStateProvider.CODEC.fieldOf("inner_speckles_attached_block").forGetter((config) -> config.innerSpecklesAttachedBlockState),
				SphereStateProvider.CODEC.fieldOf("middle_block").forGetter((config) -> config.middleBlockState),
				SphereStateProvider.CODEC.fieldOf("outer_block").forGetter((config) -> config.outerBlockState)
		).apply(instance, (sphereConfig, innerBlockState, innerSpecklesBlockState, speckleChance, innerSpecklesAttachedBlockState, middleBlockState, outerBlockState)
				-> new Config(sphereConfig.size, sphereConfig.decorators, sphereConfig.spawns, sphereConfig.generation, innerBlockState, innerSpecklesBlockState, speckleChance, innerSpecklesAttachedBlockState, middleBlockState, outerBlockState)));
		
		private final SphereStateProvider innerBlockState;
		private final SphereStateProvider innerSpecklesBlockState;
		private final float speckleChance;
		private final SphereStateProvider innerSpecklesAttachedBlockState;
		private final SphereStateProvider middleBlockState;
		private final SphereStateProvider outerBlockState;
		
		public Config(FloatProvider size, Map<Holder<ConfiguredSphereDecorator<?, ?>>, Float> decorators, List<SphereEntitySpawnDefinition> spawns, @Nullable Generation generation,
                      SphereStateProvider innerBlockState, SphereStateProvider innerSpecklesBlockState, float speckleChance, SphereStateProvider innerSpecklesAttachedBlockState, SphereStateProvider middleBlockState, SphereStateProvider outerBlockState) {
			super(size, decorators, spawns, generation);
			this.innerBlockState = innerBlockState;
			this.innerSpecklesBlockState = innerSpecklesBlockState;
			this.speckleChance = speckleChance;
			this.innerSpecklesAttachedBlockState = innerSpecklesAttachedBlockState;
			this.middleBlockState = middleBlockState;
			this.outerBlockState = outerBlockState;
		}
		
	}
	
	public static class Placed extends PlacedSphere<GeodeSphere.Config> {
		
		private final BlockStateProvider innerBlockState;
		private final BlockStateProvider innerSpecklesBlockState;
		private final float speckleChance;
		private final BlockStateProvider innerSpecklesAttachedBlockState;
		private final BlockStateProvider middleBlockState;
		private final BlockStateProvider outerBlockState;
		
		public Placed(ConfiguredSphere<? extends Sphere<GeodeSphere.Config>, GeodeSphere.Config> configuredSphere, float radius, List<Holder<ConfiguredSphereDecorator<?, ?>>> decorators, List<Tuple<EntityType<?>, Integer>> spawns, WorldgenRandom random,
                      BlockStateProvider innerBlockState, BlockStateProvider innerSpecklesBlockState, float speckleChance, BlockStateProvider innerSpecklesAttachedBlockState, BlockStateProvider middleBlockState, BlockStateProvider outerBlockState) {
			super(configuredSphere, radius, decorators, spawns, random);
			this.innerBlockState = innerBlockState;
			this.innerSpecklesBlockState = innerSpecklesBlockState;
			this.speckleChance = speckleChance;
			this.innerSpecklesAttachedBlockState = innerSpecklesAttachedBlockState;
			this.middleBlockState = middleBlockState;
			this.outerBlockState = outerBlockState;
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

						if (d < this.radius - 4) {
							// nothing
						} else if (d < this.radius - 3) {
							if (random.nextFloat() < speckleChance) {
								chunk.setBlockState(currBlockPos, innerSpecklesBlockState.getState(level, random, currBlockPos));

								// since we are operating on a chunk-by-chunk basis,
								// we cannot spill into neighnoring chunks. So if a budding block
								// is at a chunk border it will not have attached crystals. Big sad
								for (Direction direction : Direction.values()) {
									BlockPos posInDirection = currBlockPos.relative(direction);
									BlockState crystalState = innerSpecklesAttachedBlockState.getState(level, random, posInDirection);
									if (Support.isBlockPosInChunkPos(chunk.getPos(), posInDirection) && BuddingAmethystBlock.canClusterGrowAtState(chunk.getBlockState(posInDirection))) {
										if (crystalState.hasProperty(BlockStateProperties.FACING)) {
											crystalState = crystalState.setValue(BlockStateProperties.FACING, direction);
										}
										chunk.setBlockState(posInDirection, crystalState);
									}
								}

							} else {
								chunk.setBlockState(currBlockPos, innerBlockState.getState(level, random, currBlockPos));
							}
						} else if (d < this.radius - 2) {
							chunk.setBlockState(currBlockPos, middleBlockState.getState(level, random, currBlockPos));
						} else if (d < this.radius - 1) {
							chunk.setBlockState(currBlockPos, outerBlockState.getState(level, random, currBlockPos));
						}
					}
				}
			}
		}
		
		@Override
		public String getDescription(RegistryAccess registryManager) {
			return "+++ GeodeSphere +++" +
					"\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
					"\nTemplateID: " + this.getID(registryManager) +
					"\nRadius: " + this.radius +
					"\nInnerBlock: " + this.innerBlockState +
					"\nInnerSpecklesBlock: " + this.innerSpecklesBlockState +
					"\nSpeckleChance: " + this.speckleChance +
					"\nMiddleBlock: " + this.middleBlockState +
					"\nOuterBlock: " + this.outerBlockState;
		}
	}
	
}
	
