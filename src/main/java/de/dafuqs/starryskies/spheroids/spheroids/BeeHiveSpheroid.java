package de.dafuqs.starryskies.spheroids.spheroids;

import com.google.gson.*;
import com.mojang.brigadier.exceptions.*;
import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.data_loaders.*;
import de.dafuqs.starryskies.spheroids.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.block.enums.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.math.random.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.*;

import java.util.*;

public class BeeHiveSpheroid extends Spheroid {
	
	private final int shellRadius;
	private final int flowerRingRadius;
	private final int flowerRingSpacing;
	private BeehiveBlockEntity queenBeehiveBlockEntity;
	private final List<BeehiveBlockEntity> outerBeehiveBlockEntities;
	
	public BeeHiveSpheroid(Spheroid.Template template, float radius, List<SpheroidDecorator> decorators, List<Pair<EntityType<?>, Integer>> spawns, ChunkRandom random,
	                       int shellRadius, int flowerRingRadius, int flowerRingSpacing) {
		
		super(template, radius, decorators, spawns, random);
		
		this.shellRadius = shellRadius;
		this.flowerRingRadius = flowerRingRadius;
		this.flowerRingSpacing = flowerRingSpacing;
		this.outerBeehiveBlockEntities = new ArrayList<>();
	}
	
	
	public static class Template extends Spheroid.Template {
		
		private final int minShellSize;
		private final int maxShellSize;
		private final int minFlowerRingRadius;
		private final int maxFlowerRingRadius;
		private final int minFlowerRingSpacing;
		private final int maxFlowerRingSpacing;
		
		public Template(Identifier identifier, JsonObject data) throws CommandSyntaxException {
			super(identifier, data);
			
			JsonObject typeData = JsonHelper.getObject(data, "type_data");
			this.minShellSize = JsonHelper.getInt(typeData, "min_shell_size");
			this.maxShellSize = JsonHelper.getInt(typeData, "max_shell_size");
			this.minFlowerRingRadius = JsonHelper.getInt(typeData, "min_flower_ring_size");
			this.maxFlowerRingRadius = JsonHelper.getInt(typeData, "min_flower_ring_size");
			this.minFlowerRingSpacing = JsonHelper.getInt(typeData, "min_flower_ring_spacing");
			this.maxFlowerRingSpacing = JsonHelper.getInt(typeData, "min_flower_ring_spacing");
		}
		
		@Override
		public BeeHiveSpheroid generate(ChunkRandom random) {
			int shellRadius = Support.getRandomBetween(random, minShellSize, maxShellSize);
			int flowerRingRadius = Support.getRandomBetween(random, minFlowerRingRadius, maxFlowerRingRadius);
			int flowerRingSpacing = Support.getRandomBetween(random, minFlowerRingSpacing, maxFlowerRingSpacing);
			return new BeeHiveSpheroid(this, randomBetween(random, minSize, maxSize), selectDecorators(random), selectSpawns(random), random, shellRadius, flowerRingRadius, flowerRingSpacing);
		}
		
	}
	
	@Override
	public String getDescription() {
		return "+++ BeeHiveSpheroid +++" +
				"\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
				"\nTemplateID: " + this.template.getID() +
				"\nRadius: " + this.radius +
				"\nShellRadius: " + this.shellRadius +
				"\nFlowerRingRadius: " + this.flowerRingRadius +
				"\nFlowerRingSpacing: " + this.flowerRingSpacing;
	}
	
	@Override
	public void generate(Chunk chunk) {
		int chunkX = chunk.getPos().x;
		int chunkZ = chunk.getPos().z;
		
		int x = this.getPosition().getX();
		int y = this.getPosition().getY();
		int z = this.getPosition().getZ();
		
		float endRingDistance = this.radius;
		float startRingDistance = this.radius - this.flowerRingRadius;
		float shellDistance = startRingDistance - this.flowerRingSpacing;
		float coreDistance = shellDistance - shellRadius;
		
		BlockState beeHiveBlockState = Blocks.BEE_NEST.getDefaultState();
		random.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
		int ceiledRadius = (int) Math.ceil(this.radius);
		int maxX = Math.min(chunkX * 16 + 15, x + ceiledRadius);
		int maxZ = Math.min(chunkZ * 16 + 15, z + ceiledRadius);
		for (int x2 = Math.max(chunkX * 16, x - ceiledRadius); x2 <= maxX; x2++) {
			for (int y2 = y - ceiledRadius; y2 <= y + ceiledRadius; y2++) {
				for (int z2 = Math.max(chunkZ * 16, z - ceiledRadius); z2 <= maxZ; z2++) {
					long d = Math.round(Support.getDistance(x, y, z, x2, y2, z2));
					if (d > this.radius) {
						continue;
					}
					BlockPos currBlockPos = new BlockPos(x2, y2, z2);
					
					if (d == 0) {
						// bee hive in center
						chunk.setBlockState(currBlockPos, beeHiveBlockState, false);
						this.queenBeehiveBlockEntity = new BeehiveBlockEntity(currBlockPos, beeHiveBlockState);
						chunk.setBlockEntity(queenBeehiveBlockEntity);
					} else if (d == shellDistance && y2 - y == 0 && random.nextInt(10) == 0) {
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
						// set the block
						BlockState blockState = Blocks.BEE_NEST.getDefaultState().with(BeehiveBlock.FACING, direction);
						chunk.setBlockState(currBlockPos, blockState, false);
						
						// set and save the blockentity
						BeehiveBlockEntity outerBeehiveBlockEntity = new BeehiveBlockEntity(currBlockPos, blockState);
						chunk.setBlockEntity(outerBeehiveBlockEntity);
						this.outerBeehiveBlockEntities.add(outerBeehiveBlockEntity);
					} else if (d <= coreDistance) {
						// core
						int r = random.nextInt((int) Math.ceil(coreDistance / 3F)); // way more honey in the middle
						if (coreDistance - r <= d) {
							chunk.setBlockState(currBlockPos, Blocks.HONEY_BLOCK.getDefaultState(), false);
						} else {
							chunk.setBlockState(currBlockPos, Blocks.AIR.getDefaultState(), false);
						}
					} else if (d <= shellDistance) {
						// shell
						if (random.nextInt(10) == 0) {
							chunk.setBlockState(currBlockPos, Blocks.HONEY_BLOCK.getDefaultState(), false);
						} else {
							chunk.setBlockState(currBlockPos, Blocks.HONEYCOMB_BLOCK.getDefaultState(), false);
						}
					} else if (y - y2 == 0 && d > startRingDistance && d <= endRingDistance) {
						chunk.setBlockState(currBlockPos, Blocks.GRASS_BLOCK.getDefaultState(), false);
						int rand = random.nextInt(4);
						if (rand == 0) {
							chunk.setBlockState(currBlockPos.up(), getRandomFlower(random), false);
						} else if (rand == 1) {
							BlockState randomTallFlower = getRandomTallFlower(random);
							chunk.setBlockState(currBlockPos.up(), randomTallFlower.with(TallPlantBlock.HALF, DoubleBlockHalf.LOWER), false);
							chunk.setBlockState(currBlockPos.up(2), randomTallFlower.with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER), false);
						}
					}
				}
			}
		}
	}
	
	private static final Identifier FLOWERS_GROUP_ID = StarrySkies.locate("flowers");
	private static final Identifier TALL_FLOWERS_GROUP_ID = StarrySkies.locate("tall_flowers");
	
	public BlockState getRandomFlower(ChunkRandom random) {
		return WeightedBlockGroupsLoader.getRandomStateInGroup(FLOWERS_GROUP_ID, random);
	}
	
	public BlockState getRandomTallFlower(ChunkRandom random) {
		return WeightedBlockGroupsLoader.getRandomStateInGroup(TALL_FLOWERS_GROUP_ID, random);
	}
	
	@Override
	public void populateEntities(ChunkPos chunkPos, ServerWorldAccess chunkRegion, Random random) {
		if (isCenterInChunk(chunkPos)) {
			if (queenBeehiveBlockEntity != null) {
				queenBeehiveBlockEntity.addBee(getBee(), random.nextInt(599), false);
			}
			
			for (BeehiveBlockEntity beehiveBlockEntity : this.outerBeehiveBlockEntities) {
				int beeCount = 2 + random.nextInt(2);
				for(int j = 0; j < beeCount; ++j) {
					beehiveBlockEntity.addBee(getBee(), random.nextInt(599), false);
				}
			}
		}
	}
	
	public NbtCompound getBee() {
		NbtCompound nbtCompound = new NbtCompound();
		nbtCompound.putString("id", Registry.ENTITY_TYPE.getId(EntityType.BEE).toString());
		return nbtCompound;
	}
	
}
