package de.dafuqs.starryskies.worldgen;

import com.mojang.datafixers.util.*;
import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.registries.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.chunk.*;
import net.minecraft.world.level.levelgen.*;
import org.jspecify.annotations.*;

import java.util.*;
import java.util.stream.*;

public abstract class PlacedSphere<SC extends SphereConfig> {

	protected ConfiguredSphere<? extends Sphere<SC>, SC> configuredSphere;
	protected float radius;
	protected List<Holder<ConfiguredSphereDecorator<?, ?>>> decorators;
	protected List<Pair<EntityType<?>, Integer>> spawns;

	protected BlockPos position;
	protected WorldgenRandom random;

	public PlacedSphere(ConfiguredSphere<? extends Sphere<SC>, SC> configuredSphere, float radius, List<Holder<ConfiguredSphereDecorator<?, ?>>> decorators, List<Pair<EntityType<?>, Integer>> spawns, WorldgenRandom random) {
		this.configuredSphere = configuredSphere;
		this.radius = radius;
		this.decorators = decorators;
		this.spawns = spawns;
		this.random = random;
	}
	
	public Optional<ResourceKey<ConfiguredSphere<?, ?>>> getRegistryKey(RegistryAccess registryManager) {
		return registryManager.lookupOrThrow(StarryRegistryKeys.CONFIGURED_SPHERE).getResourceKey(this.configuredSphere);
	}
	
	public Holder<ConfiguredSphere<?, ?>> getRegistryEntry(RegistryAccess registryManager) {
		return registryManager.lookupOrThrow(StarryRegistryKeys.CONFIGURED_SPHERE).wrapAsHolder(this.configuredSphere);
	}

	public Identifier getID(RegistryAccess registryManager) {
		Registry<ConfiguredSphere<?, ?>> registry = registryManager.lookupOrThrow(StarryRegistryKeys.CONFIGURED_SPHERE);
		return registry.getKey(this.configuredSphere);
	}

	public abstract void generate(ChunkAccess chunk, WorldGenLevel level);

	public BlockPos getPosition() {
		return position;
	}

	public void setPosition(BlockPos position) {
		this.position = position;
	}

	public int getRadius() {
		return Math.round(radius);
	}

	public int getCeiledRadius() {
		return (int) Math.ceil(radius);
	}

	public abstract String getDescription(RegistryAccess registryManager);

	public boolean isInChunk(@NonNull ChunkPos chunkPos) {
		int radius = getRadius();
		int xMin = this.position.getX() - radius - 16;
		int xMax = this.position.getX() + radius + 15;
		int zMin = this.position.getZ() - radius - 16;
		int zMax = this.position.getZ() + radius + 15;
		return (chunkPos.getMinBlockX() >= xMin && chunkPos.getMaxBlockX() <= xMax) && (chunkPos.getMinBlockZ() >= zMin && chunkPos.getMaxBlockZ() <= zMax);
	}
	
	public Stream<ChunkPos> streamChunksWithSphere() {
		return ChunkPos.rangeClosed(
				new ChunkPos(SectionPos.blockToSectionCoord(position.getX() - this.getRadius()), SectionPos.blockToSectionCoord(position.getZ() - this.getRadius())),
				new ChunkPos(SectionPos.blockToSectionCoord(position.getX() + this.getRadius()), SectionPos.blockToSectionCoord(position.getZ() + this.getRadius()))
		);
	}
	
	public Stream<BlockPos> streamBlockPosesOfSpheres() {
		int r = (int) Math.ceil(radius);
		return BlockPos.betweenClosedStream(
				position.getX() - r, position.getY() - r, position.getZ() - r,
				position.getX() + r, position.getY() + r, position.getZ() + r
		);
	}

	public boolean isCenterInChunk(@NonNull ChunkPos chunkPos) {
		return (this.getPosition().getX() >= chunkPos.getMinBlockX()
				&& this.getPosition().getX() <= chunkPos.getMinBlockX() + 15
				&& this.getPosition().getZ() >= chunkPos.getMinBlockZ()
				&& this.getPosition().getZ() <= chunkPos.getMinBlockZ() + 15);
	}

	public void decorate(WorldGenLevel world, BlockPos origin, RandomSource random) {
		if (!this.decorators.isEmpty()) {
			for (Holder<ConfiguredSphereDecorator<?, ?>> decorator : this.decorators) {
				StarrySkies.LOGGER.debug("Decorator: {}", decorator.getClass());
				try {
					decorator.value().generate(world, random, origin, this);
				} catch (RuntimeException e) {
					// Are we asking a region for a chunk out of bounds? ಠ_ಠ
				}
				StarrySkies.LOGGER.debug("Decorator finished");
			}
		}
	}
	
	protected boolean isTopBlock(long distanceFromSphereCenter, double x, double y, double z) {
		if (distanceFromSphereCenter > this.radius - 1) {
			long dist2 = Math.round(Support.getDistance(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), x, y + 1, z));
			return dist2 > this.radius;
		} else {
			return false;
		}
	}
	
	protected boolean isBottomBlock(long distanceFromSphereCenter, double x, double y, double z) {
		if (distanceFromSphereCenter > this.radius - 1) {
			long dist2 = Math.round(Support.getDistance(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), x, y - 1, z));
			return dist2 > this.radius;
		} else {
			return false;
		}
	}
	
	public void populateEntities(ChunkPos chunkPos, WorldGenLevel chunkRegion, WorldgenRandom chunkRandom) {
		if (isCenterInChunk(chunkPos)) {
			StarrySkies.LOGGER.debug("Populating entities for sphere in chunk x:{} z:{} (StartX:{} StartZ:{}) {}", chunkPos.x(), chunkPos.z(), chunkPos.getMinBlockX(), chunkPos.getMinBlockZ(), this.getDescription(chunkRegion.registryAccess()));
			for (Pair<EntityType<?>, Integer> spawnEntry : spawns) {

				int xCord = chunkPos.getMinBlockX();
				int zCord = chunkPos.getMinBlockZ();

				chunkRandom.setDecorationSeed(chunkRegion.getSeed(), xCord, zCord);

				for (int i = 0; i < spawnEntry.getSecond(); i++) {
					int startingX = this.getPosition().getX();
					int startingY = this.getPosition().getY() + this.getRadius() + 1;
					int startingZ = this.getPosition().getZ();
					int minHeight = this.getPosition().getY() - this.getRadius();
					BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos(startingX, startingY, startingZ);
					int height = Support.getLowerGroundBlock(chunkRegion, blockPos, minHeight) + 1;

					if (height != 0) {
						Entity entity = spawnEntry.getFirst().create(chunkRegion.getLevel(), EntitySpawnReason.CHUNK_GENERATION);
						if (entity != null) {
							float width = entity.getBbWidth();
							double xPos = Mth.clamp(startingX, (double) xCord + (double) width, (double) xCord + 16.0D - (double) width);
							double zLength = Mth.clamp(startingZ, (double) zCord + (double) width, (double) zCord + 16.0D - (double) width);

							try {
								entity.snapTo(xPos, height, zLength, chunkRandom.nextFloat() * 360.0F, 0.0F);
								if (entity instanceof Mob mobentity) {
									if (mobentity.checkSpawnRules(chunkRegion, EntitySpawnReason.CHUNK_GENERATION) && mobentity.checkSpawnObstruction(chunkRegion)) {
										mobentity.finalizeSpawn(chunkRegion, chunkRegion.getCurrentDifficultyAt(mobentity.blockPosition()), EntitySpawnReason.CHUNK_GENERATION, null);
										boolean success = chunkRegion.addFreshEntity(mobentity);
										if (!success) {
											return;
										}
									}
								}
							} catch (Exception exception) {
								StarrySkies.LOGGER.warn("Failed to spawn mob on sphere{}\nException: {}", this.getDescription(chunkRegion.registryAccess()), exception);
							}
						}
					}
				}
			}
			StarrySkies.LOGGER.debug("Finished populating");
		}
	}

}
