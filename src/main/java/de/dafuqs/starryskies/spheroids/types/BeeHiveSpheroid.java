package de.dafuqs.starryskies.spheroids.types;

import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.dafuqs.starryskies.Support;
import de.dafuqs.starryskies.spheroids.SpheroidDecorator;
import de.dafuqs.starryskies.spheroids.lists.SpheroidList;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.text.Text;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.List;

public class BeeHiveSpheroid extends Spheroid {
	
	private final int shellRadius;
	private final int flowerRingRadius;
	private final int flowerRingSpacing;
	private BeehiveBlockEntity queenBeehiveBlockEntity;
	private final List<BeehiveBlockEntity> outerBeehiveBlockEntities;
	
	public BeeHiveSpheroid(Spheroid.Template template, float radius, List<SpheroidDecorator> decorators, List<Pair<EntityType, Integer>> spawns, ChunkRandom random,
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
		
		public Template(JsonObject data) throws CommandSyntaxException {
			super(data);
			
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
		
		random.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
		BlockState beeHiveBlockState = Blocks.BEE_NEST.getDefaultState();
		for (float x2 = Math.max(chunkX * 16, x - this.radius); x2 <= Math.min(chunkX * 16 + 15, x + this.radius); x2++) {
			for (float y2 = y - this.radius; y2 <= y + this.radius; y2++) {
				for (float z2 = Math.max(chunkZ * 16, z - this.radius); z2 <= Math.min(chunkZ * 16 + 15, z + this.radius); z2++) {
					BlockPos currBlockPos = new BlockPos(x2, y2, z2);
					double d = Support.getDistance(x, y, z, x2, y2, z2);
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
	
	public BlockState getRandomFlower(ChunkRandom random) {
		return SpheroidList.LIST_FLOWERS.get(random.nextInt(SpheroidList.LIST_FLOWERS.size()));
	}
	
	public BlockState getRandomTallFlower(ChunkRandom random) {
		return SpheroidList.LIST_TALL_FLOWERS.get(random.nextInt(SpheroidList.LIST_TALL_FLOWERS.size()));
	}
	
	@Override
	public void populateEntities(ChunkPos chunkPos, ChunkRegion chunkRegion, ChunkRandom chunkRandom) {
		if (shouldPopulateEntities(chunkPos)) {
			if (queenBeehiveBlockEntity != null) {
				// queen
				BeeEntity queen = new BeeEntity(EntityType.BEE, chunkRegion.toServerWorld());
				setRandomQueenProperties(queen, chunkRandom);
				queenBeehiveBlockEntity.tryEnterHive(queen, false);
			}
			
			for (BeehiveBlockEntity beehiveBlockEntity : this.outerBeehiveBlockEntities) {
				// add 2-3 bees to each hive
				int j = 2 + random.nextInt(2);
				for (int k = 0; k < j; ++k) {
					BeeEntity beeEntity = new BeeEntity(EntityType.BEE, chunkRegion.toServerWorld());
					beehiveBlockEntity.tryEnterHive(beeEntity, false, random.nextInt(599));
				}
			}
		}
	}
	
	public void setRandomQueenProperties(BeeEntity beeEntity, ChunkRandom chunkRandom) {
		beeEntity.setCustomName(Text.translatable("bee.queen"));
		beeEntity.setHealth(beeEntity.getHealth() * (random.nextFloat() * 3 + 5)); //way higher than default
		beeEntity.setMovementSpeed((float) (beeEntity.getMovementSpeed() * (random.nextFloat() * 0.5 + 0.5))); //slower than default
		beeEntity.setAbsorptionAmount((float) (beeEntity.getAbsorptionAmount() * (random.nextFloat() * 1.5 + 1))); //higher than default
		
		StatusEffectInstance statusEffectInstance1 = new StatusEffectInstance(StatusEffects.HASTE, Integer.MAX_VALUE, 1);
		StatusEffectInstance statusEffectInstance2 = new StatusEffectInstance(StatusEffects.STRENGTH, Integer.MAX_VALUE, 3);
		StatusEffectInstance statusEffectInstance3 = new StatusEffectInstance(StatusEffects.REGENERATION, Integer.MAX_VALUE, 1);
		beeEntity.addStatusEffect(statusEffectInstance1);
		beeEntity.addStatusEffect(statusEffectInstance2);
		beeEntity.addStatusEffect(statusEffectInstance3);
		
		beeEntity.setAngerTime(Integer.MAX_VALUE);
	}
	
}
