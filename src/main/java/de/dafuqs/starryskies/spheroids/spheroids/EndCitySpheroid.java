package de.dafuqs.starryskies.spheroids.spheroids;

import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.dafuqs.starryskies.Support;
import de.dafuqs.starryskies.spheroids.SpheroidDecorator;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.EndRodBlock;
import net.minecraft.block.WallSkullBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.List;

public class EndCitySpheroid extends Spheroid {
	
	private final BlockState AIR = Blocks.AIR.getDefaultState();
	private final BlockState PURPUR_BLOCK = Blocks.PURPUR_BLOCK.getDefaultState();
	private final BlockState PURPUR_PILLAR = Blocks.PURPUR_PILLAR.getDefaultState();
	private final BlockState MAGENTA_STAINED_GLASS = Blocks.MAGENTA_STAINED_GLASS.getDefaultState();
	private final BlockState END_STONE_BRICKS = Blocks.END_STONE_BRICKS.getDefaultState();
	private final BlockState DRAGON_WALL_HEAD = Blocks.DRAGON_WALL_HEAD.getDefaultState();
	
	private final Identifier END_CITY_TREASURE_CHEST = LootTables.END_CITY_TREASURE_CHEST;
	
	private final int shellRadius;
	private final ArrayList<BlockPos> interiorDecoratorPositions = new ArrayList<>();
	
	
	public EndCitySpheroid(Spheroid.Template template, float radius, List<SpheroidDecorator> decorators, List<Pair<EntityType, Integer>> spawns, ChunkRandom random,
	                       int shellRadius) {
		
		super(template, radius, decorators, spawns, random);
		this.shellRadius = shellRadius;
	}
	
	public static class Template extends Spheroid.Template {
		
		int minShellRadius;
		int maxShellRadius;
		
		public Template(JsonObject data) throws CommandSyntaxException {
			super(data);
			
			JsonObject typeData = JsonHelper.getObject(data, "type_data");
			this.minShellRadius = JsonHelper.getInt(typeData, "min_shell_size");
			this.maxShellRadius = JsonHelper.getInt(typeData, "max_shell_size");
		}
		
		@Override
		public EndCitySpheroid generate(ChunkRandom random) {
			int shellRadius = Support.getRandomBetween(random, minShellRadius, maxShellRadius);
			return new EndCitySpheroid(this, randomBetween(random, minSize, maxSize), selectDecorators(random), selectSpawns(random), random, shellRadius);
		}
		
	}
	
	@Override
	public String getDescription() {
		return "+++ EndCitySpheroid +++" +
				"\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
				"\nTemplateID: " + this.template.getID() +
				"\nRadius: " + this.radius +
				"\nShellRadius: " + this.shellRadius;
	}
	
	@Override
	public void generate(Chunk chunk) {
		int chunkX = chunk.getPos().x;
		int chunkZ = chunk.getPos().z;
		
		int x = this.getPosition().getX();
		int y = this.getPosition().getY();
		int z = this.getPosition().getZ();
		
		random.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
		for (float x2 = Math.max(chunkX * 16, x - this.radius); x2 <= Math.min(chunkX * 16 + 15, x + this.radius); x2++) {
			for (float y2 = y - this.radius; y2 <= y + this.radius; y2++) {
				for (float z2 = Math.max(chunkZ * 16, z - this.radius); z2 <= Math.min(chunkZ * 16 + 15, z + this.radius); z2++) {
					
					BlockPos currBlockPos = new BlockPos(x2, y2, z2);
					long d = Math.round(Support.getDistance(x, y, z, x2, y2, z2));
					
					if (d <= (this.radius - this.shellRadius)) {
						chunk.setBlockState(currBlockPos, PURPUR_BLOCK, false);
					} else if (d <= this.radius) {
						if (y2 % 2 == 0) {
							chunk.setBlockState(currBlockPos, END_STONE_BRICKS, false);
						} else {
							chunk.setBlockState(currBlockPos, PURPUR_BLOCK, false);
						}
					}
					
					if (d < this.getRadius() - 9 && (y2 % 10 == (this.position.getY() + 9) % 10 && x2 % 10 == (this.position.getX()) % 10 && z2 % 10 == (this.position.getZ()) % 10)) {
						interiorDecoratorPositions.add(currBlockPos);
					}
				}
			}
		}
	}
	
	/**
	 * EndCitySpheroid uses the decorator to place all the
	 * internal rooms more easily
	 *
	 * @param world  The world
	 * @param random The decoration random
	 */
	@Override
	public void decorate(StructureWorldAccess world, BlockPos origin, Random random) {
		ChunkPos originChunkPos = new ChunkPos(origin);
		for (BlockPos interiorDecoratorPosition : interiorDecoratorPositions) {
			if (Support.isBlockPosInChunkPos(originChunkPos, interiorDecoratorPosition)) {
				int randomStructure = random.nextInt(8);
				switch (randomStructure) {
					case 0 -> placeSolid(world, interiorDecoratorPosition);
					case 1 -> placeEmpty(world, interiorDecoratorPosition);
					case 2 -> placeElytra(world, interiorDecoratorPosition);
					case 3 -> placeTreasure(world, interiorDecoratorPosition);
					case 4 -> placeBrewingStand(world, interiorDecoratorPosition);
					case 5 -> placeDragonHead(world, interiorDecoratorPosition);
					default -> // double chance
							placeShulkerSpawner(world, interiorDecoratorPosition);
				}
			}
		}
	}
	
	private void placeSolid(WorldAccess worldAccess, BlockPos blockPos) {
		for (int x2 = -4; x2 < 5; x2++) {
			for (int y2 = 0; y2 < 9; y2++) {
				for (int z2 = -4; z2 < 5; z2++) {
					BlockPos destinationBlockPos = blockPos.add(x2, y2, z2);
					worldAccess.setBlockState(destinationBlockPos, PURPUR_BLOCK, 3);
				}
			}
		}
	}
	
	private void placeEmpty(WorldAccess worldAccess, BlockPos blockPos) {
		for (int x2 = -4; x2 < 5; x2++) {
			for (int y2 = 0; y2 < 9; y2++) {
				for (int z2 = -4; z2 < 5; z2++) {
					BlockPos destinationBlockPos = blockPos.add(x2, y2, z2);
					worldAccess.setBlockState(destinationBlockPos, AIR, 3);
				}
			}
		}
	}
	
	private void placeShulkerSpawner(StructureWorldAccess world, BlockPos blockPos) {
		for (int x2 = -4; x2 < 5; x2++) {
			for (int y2 = 0; y2 < 9; y2++) {
				for (int z2 = -4; z2 < 5; z2++) {
					BlockPos destinationBlockPos = blockPos.add(x2, y2, z2);
					world.setBlockState(destinationBlockPos, AIR, 3);
				}
			}
		}
		
		BlockPos spawnerPos = blockPos.up(5);
		for (int x2 = -1; x2 < 2; x2++) {
			for (int y2 = -1; y2 < 2; y2++) {
				for (int z2 = -1; z2 < 2; z2++) {
					BlockPos destinationBlockPos = spawnerPos.add(x2, y2, z2);
					world.setBlockState(destinationBlockPos, MAGENTA_STAINED_GLASS, 3);
				}
			}
		}
		
		world.setBlockState(spawnerPos.up(1), PURPUR_PILLAR, 3);
		world.setBlockState(spawnerPos.up(2), PURPUR_PILLAR, 3);
		world.setBlockState(spawnerPos.up(3), PURPUR_PILLAR, 3);
		
		placeSpawner(world, spawnerPos, EntityType.SHULKER);
	}
	
	private void placeBrewingStand(WorldAccess worldAccess, BlockPos blockPos) {
		for (int x2 = -4; x2 < 5; x2++) {
			for (int y2 = 0; y2 < 9; y2++) {
				for (int z2 = -4; z2 < 5; z2++) {
					BlockPos destinationBlockPos = blockPos.add(x2, y2, z2);
					worldAccess.setBlockState(destinationBlockPos, AIR, 3);
				}
			}
		}
		
		worldAccess.setBlockState(blockPos, PURPUR_PILLAR, 3);
		worldAccess.setBlockState(blockPos.up(), Blocks.BREWING_STAND.getDefaultState(), 3);
		
		BlockEntity blockEntity = worldAccess.getBlockEntity(blockPos.up());
		
		ItemStack healingPotionStack = new ItemStack(Items.POTION, 1);
		NbtCompound potionTag = new NbtCompound();
		potionTag.putString("Potion", new Identifier("strong_healing").toString());
		healingPotionStack.setNbt(potionTag);
		
		if (blockEntity instanceof BrewingStandBlockEntity) {
			((BrewingStandBlockEntity) blockEntity).setStack(0, healingPotionStack.copy());
			((BrewingStandBlockEntity) blockEntity).setStack(1, healingPotionStack.copy());
			((BrewingStandBlockEntity) blockEntity).setStack(2, healingPotionStack.copy());
		}
	}
	
	private void placeTreasure(WorldAccess worldAccess, BlockPos blockPos) {
		for (int x2 = -4; x2 < 5; x2++) {
			for (int y2 = 0; y2 < 9; y2++) {
				for (int z2 = -4; z2 < 5; z2++) {
					BlockPos destinationBlockPos = blockPos.add(x2, y2, z2);
					worldAccess.setBlockState(destinationBlockPos, AIR, 3);
				}
			}
		}
		
		BlockState enderChestBlockState = Blocks.ENDER_CHEST.getDefaultState();
		BlockPos randomPos = blockPos.add(random.nextInt(9) - 4, 0, random.nextInt(9) - 4);
		worldAccess.setBlockState(randomPos, enderChestBlockState, 3);
		
		// may override the ender chest in very rare circumstances
		placeCenterChestWithLootTable(worldAccess.getChunk(blockPos), blockPos, END_CITY_TREASURE_CHEST, random, false);
	}
	
	private void placeElytra(WorldAccess worldAccess, BlockPos blockPos) {
		for (int x2 = -4; x2 < 5; x2++) {
			for (int y2 = 0; y2 < 9; y2++) {
				for (int z2 = -4; z2 < 5; z2++) {
					BlockPos destinationBlockPos = blockPos.add(x2, y2, z2);
					worldAccess.setBlockState(destinationBlockPos, AIR, 3);
				}
			}
		}
		
		worldAccess.setBlockState(blockPos, PURPUR_PILLAR, 3);
		worldAccess.setBlockState(blockPos.up(), Blocks.CHEST.getDefaultState(), 3);
		ItemStack elytraItemStack = new ItemStack(Items.ELYTRA, 1);
		
		BlockEntity blockEntity = worldAccess.getBlockEntity(blockPos.up());
		if (blockEntity instanceof ChestBlockEntity chestBlockEntity) {
			chestBlockEntity.setStack(0, elytraItemStack);
		}
	}
	
	private void placeDragonHead(WorldAccess worldAccess, BlockPos blockPos) {
		for (int x2 = -4; x2 < 5; x2++) {
			for (int y2 = 0; y2 < 9; y2++) {
				for (int z2 = -4; z2 < 5; z2++) {
					BlockPos destinationBlockPos = blockPos.add(x2, y2, z2);
					worldAccess.setBlockState(destinationBlockPos, AIR, 3);
				}
			}
		}
		
		worldAccess.setBlockState(blockPos, PURPUR_PILLAR, 3);
		worldAccess.setBlockState(blockPos.up(), PURPUR_PILLAR, 3);
		worldAccess.setBlockState(blockPos.up(2), Blocks.END_ROD.getDefaultState().with(EndRodBlock.FACING, Direction.UP), 3);
		
		int randomPosition = random.nextInt(4);
		BlockState dragonHeadBlockState;
		switch (randomPosition) {
			case 0 -> {
				dragonHeadBlockState = DRAGON_WALL_HEAD.with(WallSkullBlock.FACING, Direction.NORTH);
				worldAccess.setBlockState(blockPos.up().north(), dragonHeadBlockState, 3);
			}
			case 1 -> {
				dragonHeadBlockState = DRAGON_WALL_HEAD.with(WallSkullBlock.FACING, Direction.EAST);
				worldAccess.setBlockState(blockPos.up().east(), dragonHeadBlockState, 3);
			}
			case 2 -> {
				dragonHeadBlockState = DRAGON_WALL_HEAD.with(WallSkullBlock.FACING, Direction.SOUTH);
				worldAccess.setBlockState(blockPos.up().south(), dragonHeadBlockState, 3);
			}
			default -> {
				dragonHeadBlockState = DRAGON_WALL_HEAD.with(WallSkullBlock.FACING, Direction.WEST);
				worldAccess.setBlockState(blockPos.up().west(), dragonHeadBlockState, 3);
			}
		}
	}
	
}
