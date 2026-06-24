package de.dafuqs.starryskies.worldgen.spheres;

import com.mojang.datafixers.util.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.data_loaders.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.chunk.*;
import net.minecraft.world.level.levelgen.*;
import org.jspecify.annotations.*;

import java.util.*;

public class BeeHiveSphere extends Sphere<BeeHiveSphere.Config> {
	
	private static final String FLOWERS_GROUP = "flowers";
	private static final String TALL_FLOWERS_GROUP = "tall_flowers";
	
	public BeeHiveSphere(Codec<BeeHiveSphere.Config> codec) {
		super(codec);
	}
	
	@Override
	public PlacedSphere<?> generate(ConfiguredSphere<? extends Sphere<BeeHiveSphere.Config>, Config> configuredSphere, Config config, WorldgenRandom random, WorldGenLevel level, BlockPos pos, float radius) {
		return new BeeHiveSphere.Placed(configuredSphere, radius, configuredSphere.getDecorators(random), configuredSphere.getSpawns(random), random, config.shellThickness.sample(random), config.flowerRingRadius.sample(random), config.flowerRingSpacing.sample(random), config.beeNestChance);
	}
	
	public static class Config extends SphereConfig {
		
		public static final Codec<BeeHiveSphere.Config> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
				SphereConfig.CONFIG_CODEC.forGetter((config) -> config),
				IntProviders.POSITIVE_CODEC.fieldOf("shell_thickness").forGetter((config) -> config.shellThickness),
				IntProviders.POSITIVE_CODEC.fieldOf("flower_ring_radius").forGetter((config) -> config.shellThickness),
				IntProviders.POSITIVE_CODEC.fieldOf("flower_ring_spacing").forGetter((config) -> config.shellThickness),
				ExtraCodecs.POSITIVE_FLOAT.fieldOf("bee_nest_chance").forGetter((config) -> config.beeNestChance)
		).apply(instance, (sphereConfig, shellThickness, flowerRingRadius, flowerRingSpacing, beeNestChance) -> new Config(sphereConfig.size, sphereConfig.decorators, sphereConfig.spawns, sphereConfig.generation, shellThickness, flowerRingRadius, flowerRingSpacing, beeNestChance)));
		
		protected final IntProvider shellThickness;
		protected final IntProvider flowerRingRadius;
		protected final IntProvider flowerRingSpacing;
		protected final float beeNestChance;
		
		public Config(FloatProvider size, Map<Holder<ConfiguredSphereDecorator<?, ?>>, Float> decorators, List<SphereEntitySpawnDefinition> spawns, @Nullable Generation generation, IntProvider shellThickness, IntProvider flowerRingRadius, IntProvider flowerRingSpacing, float beeNestChance) {
			super(size, decorators, spawns, generation);
			this.shellThickness = shellThickness;
			this.flowerRingRadius = flowerRingRadius;
			this.flowerRingSpacing = flowerRingSpacing;
			this.beeNestChance = beeNestChance;
		}
		
	}
	
	public static class Placed extends PlacedSphere<BeeHiveSphere.Config> {
		
		private final int shellThickness;
		private final int flowerRingRadius;
		private final int flowerRingSpacing;
		private final float beeNestChance;

        public Placed(ConfiguredSphere<? extends Sphere<BeeHiveSphere.Config>, BeeHiveSphere.Config> configuredSphere, float radius, List<Holder<ConfiguredSphereDecorator<?, ?>>> decorators, List<Pair<EntityType<?>, Integer>> spawns, WorldgenRandom random,
                      int shellThickness, int flowerRingRadius, int flowerRingSpacing, float beeNestChance) {
			super(configuredSphere, radius, decorators, spawns, random);
			this.shellThickness = shellThickness;
			this.flowerRingRadius = flowerRingRadius;
			this.flowerRingSpacing = flowerRingSpacing;
			this.beeNestChance = beeNestChance;
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
			
			float endRingDistance = this.radius;
			float startRingDistance = this.radius - this.flowerRingRadius;
			float shellDistance = startRingDistance - this.flowerRingSpacing;
			float coreDistance = shellDistance - shellThickness;
			
			BlockState beeHiveBlockState = Blocks.BEE_NEST.defaultBlockState();
			
			BlockPos.MutableBlockPos currBlockPos = new BlockPos.MutableBlockPos();
			for (int x2 = Math.max(chunkX * 16, x - ceiledRadius); x2 <= maxX; x2++) {
				for (int y2 = y - ceiledRadius; y2 <= y + ceiledRadius; y2++) {
					for (int z2 = Math.max(chunkZ * 16, z - ceiledRadius); z2 <= maxZ; z2++) {
						long d = Math.round(Support.getDistance(x, y, z, x2, y2, z2));
						if (d > this.radius) {
							continue;
						}
						currBlockPos.set(x2, y2, z2);
						
						if (d == 0) {
							// bee hive in center
							chunk.setBlockState(currBlockPos, beeHiveBlockState);
							BeehiveBlockEntity beehiveBlockEntity = new BeehiveBlockEntity(currBlockPos, beeHiveBlockState);
							addBees(beehiveBlockEntity);
							chunk.setBlockEntity(beehiveBlockEntity);
						} else if (d <= coreDistance) {
							// core
							int r = random.nextInt((int) Math.ceil(coreDistance / 3F)); // way more honey in the middle
							if (coreDistance - r <= d) {
								chunk.setBlockState(currBlockPos, Blocks.HONEY_BLOCK.defaultBlockState());
							} else {
								chunk.setBlockState(currBlockPos, Blocks.AIR.defaultBlockState());
							}
						} else if (d <= shellDistance) {
							if (y2 - y == 0 && random.nextFloat() < beeNestChance) {
								// middle outer shell: random hives
								Direction direction;
								float xDist = x2 - x;
								float zDist = z2 - z;
								if (xDist > 0) {
									if (Math.abs(xDist) > Math.abs(zDist)) {
										direction = Direction.EAST;
									} else {
										if (zDist > 0) {
											direction = Direction.SOUTH;
										} else {
											direction = Direction.NORTH;
										}
									}
								} else {
									if (Math.abs(xDist) < Math.abs(zDist)) {
										if (zDist > 0) {
											direction = Direction.SOUTH;
										} else {
											direction = Direction.NORTH;
										}
									} else {
										direction = Direction.WEST;
									}
								}
								
								// is the block the potential beehive facing to the outside of the sphere?
								float dist2 = Math.round(Support.getDistance(new BlockPos(x2, y2, z2).relative(direction), spherePos));
								if (dist2 > this.radius) {
									chunk.setBlockState(currBlockPos, Blocks.HONEYCOMB_BLOCK.defaultBlockState());
								} else {
									BlockState blockState = Blocks.BEE_NEST.defaultBlockState().setValue(BeehiveBlock.FACING, direction);
									chunk.setBlockState(currBlockPos, blockState);
									
									// set and save the blockentity
									BeehiveBlockEntity outerBeehiveBlockEntity = new BeehiveBlockEntity(currBlockPos, blockState);
									addBees(outerBeehiveBlockEntity);
									chunk.setBlockEntity(outerBeehiveBlockEntity);
								}
							} else {
								// shell
								if (random.nextInt(10) == 0) {
									chunk.setBlockState(currBlockPos, Blocks.HONEY_BLOCK.defaultBlockState());
								} else {
									chunk.setBlockState(currBlockPos, Blocks.HONEYCOMB_BLOCK.defaultBlockState());
								}
							}
						} else if (y - y2 == 0 && d > startRingDistance && d <= endRingDistance) {
							chunk.setBlockState(currBlockPos, Blocks.GRASS_BLOCK.defaultBlockState());
							int rand = random.nextInt(4);
							if (rand == 0) {
								chunk.setBlockState(currBlockPos.above(), getRandomFlower(random));
							} else if (rand == 1) {
								BlockState randomTallFlower = getRandomTallFlower(random);
								chunk.setBlockState(currBlockPos.above(), randomTallFlower.setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
								chunk.setBlockState(currBlockPos.above(2), randomTallFlower.setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER));
							}
						}
					}
				}
			}
		}
		
		public BlockState getRandomFlower(WorldgenRandom random) {
			return WeightedBlockGroupDataLoader.INSTANCE.getEntry(FLOWERS_GROUP, random);
		}
		
		public BlockState getRandomTallFlower(WorldgenRandom random) {
			return WeightedBlockGroupDataLoader.INSTANCE.getEntry(TALL_FLOWERS_GROUP, random);
		}
		
		protected void addBees(BeehiveBlockEntity beeHive) {
			int beeCount = 2 + random.nextInt(2);
			for (int j = 0; j < beeCount; ++j) {
				beeHive.storeBee(getBee());
			}
		}
		
		
		public BeehiveBlockEntity.Occupant getBee() {
			return BeehiveBlockEntity.Occupant.create(random.nextInt(599));
		}
		
		@Override
		public String getDescription(RegistryAccess registryManager) {
			return "+++ BeeHiveSphere +++" +
					"\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
					"\nTemplateID: " + this.getID(registryManager) +
					"\nRadius: " + this.radius +
					"\nShellRadius: " + this.shellThickness +
					"\nFlowerRingRadius: " + this.flowerRingRadius +
					"\nFlowerRingSpacing: " + this.flowerRingSpacing;
		}
	}
	
}

