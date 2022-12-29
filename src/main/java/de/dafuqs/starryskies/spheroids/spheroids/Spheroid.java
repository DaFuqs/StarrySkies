package de.dafuqs.starryskies.spheroids.spheroids;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.Support;
import de.dafuqs.starryskies.data_loaders.SpheroidDecoratorLoader;
import de.dafuqs.starryskies.spheroids.SpheroidDecorator;
import de.dafuqs.starryskies.spheroids.SpheroidEntitySpawnDefinition;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.chunk.Chunk;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.apache.logging.log4j.Level.WARN;

public abstract class Spheroid implements Serializable {
	
	protected Spheroid.Template template;
	protected float radius;
	protected List<SpheroidDecorator> decorators;
	protected List<Pair<EntityType<?>, Integer>> spawns;
	
	protected BlockPos position;
	protected ChunkRandom random;
	
	public Spheroid(Spheroid.Template template, float radius, List<SpheroidDecorator> decorators, List<Pair<EntityType<?>, Integer>> spawns, ChunkRandom random) {
		this.template = template;
		this.radius = radius;
		this.decorators = decorators;
		this.spawns = spawns;
		this.random = random;
	}
	
	public void setPosition(BlockPos position) {
		this.position = position;
	}
	
	public abstract void generate(Chunk chunk);
	
	public BlockPos getPosition() {
		return position;
	}
	
	public int getRadius() {
		return Math.round(radius);
	}
	
	public abstract String getDescription();
	
	public boolean isInChunk(@NotNull ChunkPos chunkPos) {
		int radius = getRadius();
		int xMin = this.position.getX() - radius - 16;
		int xMax = this.position.getX() + radius + 15;
		int zMin = this.position.getZ() - radius - 16;
		int zMax = this.position.getZ() + radius + 15;
		return (chunkPos.getStartX() >= xMin && chunkPos.getEndX() <= xMax) && (chunkPos.getStartZ() >= zMin && chunkPos.getEndZ() <= zMax);
	}
	
	public boolean isCenterInChunk(@NotNull ChunkPos chunkPos) {
		return (this.getPosition().getX() >= chunkPos.getStartX()
				&& this.getPosition().getX() <= chunkPos.getStartX() + 15
				&& this.getPosition().getZ() >= chunkPos.getStartZ()
				&& this.getPosition().getZ() <= chunkPos.getStartZ() + 15);
	}
	
	public void decorate(StructureWorldAccess world, BlockPos origin, Random random) {
		if (this.decorators.size() > 0) {
			for (SpheroidDecorator decorator : this.decorators) {
				StarrySkies.log(Level.DEBUG, "Decorator: " + decorator.getClass());
				try {
					decorator.decorate(world, new ChunkPos(origin), this, random);
				} catch (RuntimeException e) {
					// Are we asking a region for a chunk out of bounds? ಠ_ಠ
				}
				StarrySkies.log(Level.DEBUG, "Decorator finished");
			}
		}
	}
	
	protected boolean isTopBlock(long d, double x, double y, double z) {
		if (d > this.radius - 1) {
			long dist2 = Math.round(Support.getDistance(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), x, y + 1, z));
			return dist2 > this.radius;
		} else {
			return false;
		}
	}
	
	protected boolean isBottomBlock(long d, double x, double y, double z) {
		if (d > this.radius - 1) {
			long dist2 = Math.round(Support.getDistance(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), x, y - 1, z));
			return dist2 > this.radius;
		} else {
			return false;
		}
	}
	
	protected boolean isAboveCaveFloorBlock(long d, double x, double y, double z, float shellRadius) {
		int distance1 = (int) Math.round(Support.getDistance(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), x, y - 1, z));
		return d == (this.radius - shellRadius) && distance1 > (this.radius - shellRadius);
	}
	
	protected void placeCenterChestWithLootTable(Chunk chunk, BlockPos blockPos, Identifier lootTable, Random random, boolean waterLogged) {
		BlockState chestBlockState;
		if (waterLogged) {
			chestBlockState = Blocks.CHEST.getDefaultState().with(ChestBlock.WATERLOGGED, true);
		} else {
			chestBlockState = Blocks.CHEST.getDefaultState();
		}
		chunk.setBlockState(blockPos, chestBlockState, false);
		chunk.setBlockEntity(new ChestBlockEntity(blockPos, chestBlockState));
		LootableContainerBlockEntity.setLootTable(chunk, random, blockPos, lootTable);
	}
	
	public void populateEntities(ChunkPos chunkPos, ChunkRegion chunkRegion, ChunkRandom chunkRandom) {
		if (isCenterInChunk(chunkPos)) {
			StarrySkies.log(Level.DEBUG, "Populating entities for spheroid in chunk x:" + chunkPos.x + " z:" + chunkPos.z + " (StartX:" + chunkPos.getStartX() + " StartZ:" + chunkPos.getStartZ() + ") " + this.getDescription());
			for (Pair<EntityType<?>, Integer> spawnEntry : spawns) {
				
				int xCord = chunkPos.getStartX();
				int zCord = chunkPos.getStartZ();
				
				chunkRandom.setPopulationSeed(chunkRegion.getSeed(), xCord, zCord);
				
				for (int i = 0; i < spawnEntry.getRight(); i++) {
					int startingX = this.getPosition().getX();
					int startingY = this.getPosition().getY() + this.getRadius() + 1;
					int startingZ = this.getPosition().getZ();
					int minHeight = this.getPosition().getY() - this.getRadius();
					BlockPos.Mutable blockPos = new BlockPos.Mutable(startingX, startingY, startingZ);
					int height = Support.getLowerGroundBlock(chunkRegion, blockPos, minHeight) + 1;
					
					if (height != 0) {
						Entity entity = spawnEntry.getLeft().create(chunkRegion.toServerWorld());
						if (entity != null) {
							float width = entity.getWidth();
							double xPos = MathHelper.clamp(startingX, (double) xCord + (double) width, (double) xCord + 16.0D - (double) width);
							double zLength = MathHelper.clamp(startingZ, (double) zCord + (double) width, (double) zCord + 16.0D - (double) width);
							
							try {
								entity.refreshPositionAndAngles(xPos, height, zLength, chunkRandom.nextFloat() * 360.0F, 0.0F);
								if (entity instanceof MobEntity mobentity) {
									if (mobentity.canSpawn(chunkRegion, SpawnReason.CHUNK_GENERATION) && mobentity.canSpawn(chunkRegion)) {
										mobentity.initialize(chunkRegion, chunkRegion.getLocalDifficulty(new BlockPos(mobentity.getPos())), SpawnReason.CHUNK_GENERATION, null, null);
										boolean success = chunkRegion.spawnEntity(mobentity);
										if (!success) {
											return;
										}
									}
								}
							} catch (Exception exception) {
								StarrySkies.log(WARN, "Failed to spawn mob on sphere" + this.getDescription() + "\nException: " + exception);
							}
						}
					}
				}
			}
			StarrySkies.log(Level.DEBUG, "Finished populating");
		}
	}
	
	protected void placeSpawner(@NotNull WorldAccess worldAccess, BlockPos blockPos, EntityType<?> entityType) {
		worldAccess.setBlockState(blockPos, Blocks.SPAWNER.getDefaultState(), 3);
		BlockEntity blockEntity = worldAccess.getBlockEntity(blockPos);
		if (blockEntity instanceof MobSpawnerBlockEntity) {
			((MobSpawnerBlockEntity) blockEntity).getLogic().setEntityId(entityType, null, worldAccess.getRandom(), blockPos);
		}
	}
	
	public Spheroid.Template getTemplate() {
		return this.template;
	}
	
	public static abstract class Template {
		
		protected final Identifier id;
		protected final int minSize;
		protected final int maxSize;
		protected Map<SpheroidDecorator, Float> decorators;
		protected List<SpheroidEntitySpawnDefinition> spawns;
		
		public Template(Identifier identifier, JsonObject jsonObject) throws CommandSyntaxException {
			this(identifier,
					JsonHelper.getInt(jsonObject, "min_size"),
					JsonHelper.getInt(jsonObject, "max_size"),
					readDecorators(JsonHelper.getObject(jsonObject, "decorators"), identifier),
					readSpawns(JsonHelper.getArray(jsonObject, "spawns"))
			);
		}
		
		public Identifier getID() {
			return id;
		}
		
		protected static float randomBetween(Random random, int min, int max) {
			return min + random.nextFloat() * (max - min);
		}
		
		private static Map<SpheroidDecorator, Float> readDecorators(JsonObject jsonObject, Identifier identifier) {
			Map<SpheroidDecorator, Float> d = new LinkedHashMap<>();
			
			for (Map.Entry<String, JsonElement> e : jsonObject.entrySet()) {
				SpheroidDecorator decorator = SpheroidDecoratorLoader.getDecorator(Identifier.tryParse(e.getKey()));
				if (decorator == null) {
					if (StarrySkies.CONFIG.packCreatorMode) {
						StarrySkies.log(Level.WARN, "Spheroid " + identifier + " specifies non-existing decorator " + e.getKey() + ". Will be ignored.");
					}
				} else {
					float chance = e.getValue().getAsFloat();
					d.put(decorator, chance);
				}
			}
			
			return d;
		}
		
		private static List<SpheroidEntitySpawnDefinition> readSpawns(JsonArray jsonArray) {
			List<SpheroidEntitySpawnDefinition> spawns = new ArrayList<>();
			
			for (JsonElement e : jsonArray) {
				JsonObject o = e.getAsJsonObject();
				EntityType<?> entityType = Registries.ENTITY_TYPE.get(Identifier.tryParse(JsonHelper.getString(o, "type")));
				int minCount = JsonHelper.getInt(o, "min_count");
				int maxCount = JsonHelper.getInt(o, "max_count");
				float chance = JsonHelper.getFloat(o, "chance");
				spawns.add(new SpheroidEntitySpawnDefinition(entityType, minCount, maxCount, chance));
			}
			
			return spawns;
		}
		
		protected List<SpheroidDecorator> selectDecorators(Random random) {
			List<SpheroidDecorator> decorators = new ArrayList<>();
			for (Map.Entry<SpheroidDecorator, Float> entry : this.decorators.entrySet()) {
				if (random.nextFloat() < entry.getValue()) {
					decorators.add(entry.getKey());
				}
			}
			return decorators;
		}
		
		protected List<Pair<EntityType<?>, Integer>> selectSpawns(Random random) {
			List<Pair<EntityType<?>, Integer>> spawns = new ArrayList<>();
			for (SpheroidEntitySpawnDefinition entry : this.spawns) {
				if (random.nextFloat() < entry.chance) {
					int count = Support.getRandomBetween(random, entry.minCount, entry.maxCount);
					spawns.add(new Pair<>(entry.entityType, count));
				}
			}
			return spawns;
		}
		
		public Template(Identifier id, int minSize, int maxSize, Map<SpheroidDecorator, Float> decorators, List<SpheroidEntitySpawnDefinition> spawns) {
			this.id = id;
			this.minSize = minSize;
			this.maxSize = maxSize;
			this.decorators = decorators;
			this.spawns = spawns;
		}
		
		public abstract Spheroid generate(ChunkRandom systemRandom);
		
	}
	
}
