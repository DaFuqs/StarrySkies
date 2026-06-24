package de.dafuqs.starryskies.worldgen.spheres;

import com.mojang.datafixers.util.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.chunk.*;
import net.minecraft.world.level.levelgen.*;
import org.jspecify.annotations.*;

import java.util.*;

public class OceanMonumentSphere extends Sphere<OceanMonumentSphere.Config> {
	
	private static final BlockState WATER = Blocks.WATER.defaultBlockState();
	private static final BlockState PRISMARINE = Blocks.PRISMARINE.defaultBlockState();
	private static final BlockState PRISMARINE_BRICKS = Blocks.PRISMARINE_BRICKS.defaultBlockState();
	private static final BlockState DARK_PRISMARINE = Blocks.DARK_PRISMARINE.defaultBlockState();
	private static final BlockState SEA_LANTERN = Blocks.SEA_LANTERN.defaultBlockState();
	private static final BlockState TREASURE = Blocks.WET_SPONGE.defaultBlockState();
	
	public OceanMonumentSphere(Codec<OceanMonumentSphere.Config> codec) {
		super(codec);
	}
	
	@Override
	public PlacedSphere<?> generate(ConfiguredSphere<? extends Sphere<OceanMonumentSphere.Config>, Config> configuredSphere, Config config, WorldgenRandom random, WorldGenLevel level, BlockPos pos, float radius) {
		return new OceanMonumentSphere.Placed(configuredSphere, radius, configuredSphere.getDecorators(random), configuredSphere.getSpawns(random), random, config.coreRadius.sample(random), config.shellThickness.sample(random));
	}
	
	public static class Config extends SphereConfig {
		
		public static final Codec<OceanMonumentSphere.Config> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
				SphereConfig.CONFIG_CODEC.forGetter((config) -> config),
				FloatProviders.codec(1.0F, 32.0F).fieldOf("core_radius").forGetter((config) -> config.coreRadius),
				IntProviders.POSITIVE_CODEC.fieldOf("shell_thickness").forGetter((config) -> config.shellThickness)
		).apply(instance, (sphereConfig, coreRadius, shellThickness) -> new Config(sphereConfig.size, sphereConfig.decorators, sphereConfig.spawns, sphereConfig.generation, coreRadius, shellThickness)));
		
		protected final FloatProvider coreRadius;
		protected final IntProvider shellThickness;
		
		public Config(FloatProvider size, Map<Holder<ConfiguredSphereDecorator<?, ?>>, Float> decorators, List<SphereEntitySpawnDefinition> spawns, @Nullable Generation generation, FloatProvider coreRadius, IntProvider shellThickness) {
			super(size, decorators, spawns, generation);
			this.coreRadius = coreRadius;
			this.shellThickness = shellThickness;
		}
		
	}
	
	public static class Placed extends PlacedSphere<OceanMonumentSphere.Config> {
		
		private final float coreRadius;
		private final float shellRadius;

        public Placed(ConfiguredSphere<? extends Sphere<OceanMonumentSphere.Config>, OceanMonumentSphere.Config> configuredSphere, float radius, List<Holder<ConfiguredSphereDecorator<?, ?>>> decorators, List<Pair<EntityType<?>, Integer>> spawns, WorldgenRandom random, float coreRadius, float shellRadius) {
			super(configuredSphere, radius, decorators, spawns, random);
			this.coreRadius = coreRadius;
			this.shellRadius = shellRadius;
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
			
			float shellDistance = this.radius - this.shellRadius;
			
			BlockPos.MutableBlockPos currBlockPos = new BlockPos.MutableBlockPos();
			for (int x2 = Math.max(chunkX * 16, x - ceiledRadius); x2 <= maxX; x2++) {
				for (int y2 = y - ceiledRadius; y2 <= y + ceiledRadius; y2++) {
					for (int z2 = Math.max(chunkZ * 16, z - ceiledRadius); z2 <= maxZ; z2++) {
						long d = Math.round(Support.getDistance(x, y, z, x2, y2, z2));
						if (d > this.radius) {
							continue;
						}
						currBlockPos.set(x2, y2, z2);
						
						if (d <= this.coreRadius) {
							chunk.setBlockState(currBlockPos, TREASURE);
						} else if (d < coreRadius + 3) {
							chunk.setBlockState(currBlockPos, WATER);
						} else if (d == coreRadius + 3) {
							if (Math.abs(x2 - x) < 2 || Math.abs(z2 - z) < 2) {
								chunk.setBlockState(currBlockPos, WATER);
							} else {
								chunk.setBlockState(currBlockPos, DARK_PRISMARINE);
							}
						} else if (d <= shellDistance) {
							if (y2 % 10 == 0 || x2 % 10 == 0 || z2 % 10 == 0) {
								if ((y2 - y) % 6 == 0 && ((x2 - x) % 4 == 2 || (z2 - z) % 4 == 0)) {
									chunk.setBlockState(currBlockPos, SEA_LANTERN);
								} else {
									chunk.setBlockState(currBlockPos, PRISMARINE_BRICKS);
								}
							} else {
								chunk.setBlockState(currBlockPos, WATER);
							}
						} else {
							if (y2 % 2 == 0) {
								chunk.setBlockState(currBlockPos, PRISMARINE);
							} else {
								chunk.setBlockState(currBlockPos, PRISMARINE_BRICKS);
							}
						}
					}
				}
			}
		}
		
		@Override
		public String getDescription(RegistryAccess registryManager) {
			return "+++ OceanMonumentSphere +++" +
					"\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
					"\nTemplateID: " + this.getID(registryManager) +
					"\nRadius: " + this.radius +
					"\nCoreRadius: " + this.coreRadius +
					"\nShellRadius: " + this.shellRadius;
		}
		
		@Override
		public void populateEntities(ChunkPos chunkPos, WorldGenLevel world, WorldgenRandom chunkRandom) {
			super.populateEntities(chunkPos, world, chunkRandom);
			
			BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
			int iRadius = (int) radius - 5;
			for (int x2 = position.getX() - iRadius; x2 <= position.getX() + iRadius; x2 += 10) {
				for (int y2 = position.getY() - iRadius; y2 <= position.getY() + iRadius; y2 += 10) {
					for (int z2 = position.getZ() - iRadius; z2 <= position.getZ() + iRadius; z2 += 10) {
						long d = Math.round(Support.getDistance(position.getX(), position.getY(), position.getZ(), x2, y2, z2));
						if (d < this.radius - this.shellRadius) {
							mutable.set(x2, y2, z2);
							if (Support.isBlockPosInChunkPos(chunkPos, mutable)) {
								if (spawnGuardian(chunkPos, world, mutable)) return;
							}
						}
					}
				}
			}
		}
		
		private boolean spawnGuardian(ChunkPos chunkPos, WorldGenLevel chunkRegion, BlockPos guardianPosition) {
			Mob mobentity;
			if (random.nextFloat() < 0.08) {
                mobentity = EntityTypes.ELDER_GUARDIAN.create(chunkRegion.getLevel(), EntitySpawnReason.CHUNK_GENERATION);
			} else {
                mobentity = EntityTypes.GUARDIAN.create(chunkRegion.getLevel(), EntitySpawnReason.CHUNK_GENERATION);
			}
			
			if (mobentity != null) {
				float width = mobentity.getBbWidth();
				double xLength = Mth.clamp(guardianPosition.getX(), (double) chunkPos.getMinBlockX() + (double) width, (double) chunkPos.getMinBlockX() + 16.0D - (double) width);
				double zLength = Mth.clamp(guardianPosition.getZ(), (double) chunkPos.getMinBlockZ() + (double) width, (double) chunkPos.getMinBlockZ() + 16.0D - (double) width);
				
				try {
					mobentity.snapTo(xLength, guardianPosition.getY(), zLength, random.nextFloat() * 360.0F, 0.0F);
					mobentity.setPersistenceRequired();
					if (mobentity.checkSpawnRules(chunkRegion, EntitySpawnReason.CHUNK_GENERATION) && mobentity.checkSpawnObstruction(chunkRegion)) {
						mobentity.finalizeSpawn(chunkRegion, chunkRegion.getCurrentDifficultyAt(mobentity.blockPosition()), EntitySpawnReason.CHUNK_GENERATION, null);
						boolean success = chunkRegion.addFreshEntity(mobentity);
						if (!success) {
							return true;
						}
					}
				} catch (Exception exception) {
					StarrySkies.LOGGER.warn("Failed to spawn mob on sphere {}\nException: {}", this.getDescription(chunkRegion.registryAccess()), exception);
				}
			}
			return false;
		}
		
	}
	
}
	
