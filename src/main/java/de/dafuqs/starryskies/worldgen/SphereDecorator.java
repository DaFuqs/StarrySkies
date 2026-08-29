package de.dafuqs.starryskies.worldgen;

import com.mojang.serialization.*;
import net.minecraft.core.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jspecify.annotations.*;

import java.util.*;

public abstract class SphereDecorator<FC extends SphereDecoratorConfig> {

	private final MapCodec<ConfiguredSphereDecorator<FC, SphereDecorator<FC>>> codec;

	public SphereDecorator(Codec<FC> configCodec) {
		this.codec = configCodec.fieldOf("config").xmap((config) -> new ConfiguredSphereDecorator<>(this, config), ConfiguredSphereDecorator::config);
	}

	public MapCodec<ConfiguredSphereDecorator<FC, SphereDecorator<FC>>> getCodec() {
		return this.codec;
	}

	public abstract boolean generate(SphereFeatureContext<FC> context);

	public boolean generateIfValid(FC config, WorldGenLevel world, RandomSource random, BlockPos pos, PlacedSphere<?> sphere) {
		return world.ensureCanWrite(pos) && this.generate(new SphereFeatureContext<>(world, random, ChunkPos.containing(pos), sphere, config));
	}

	protected void placeLootChest(@NonNull WorldGenLevel world, BlockPos blockPos, ResourceKey<LootTable> lootTable, RandomSource random) {
		BlockState chestBlockState = Blocks.CHEST.defaultBlockState();

		// if the chest is placed in water: waterlog it!
		if (world.getBlockState(blockPos) == Blocks.WATER.defaultBlockState()) {
			chestBlockState = chestBlockState.setValue(ChestBlock.WATERLOGGED, true);
		}

		// Random direction placement for the chest
		int r = random.nextInt(4);
		Direction randomDirection;
		switch (r) {
			case 0 -> randomDirection = Direction.NORTH;
			case 1 -> randomDirection = Direction.SOUTH;
			case 2 -> randomDirection = Direction.EAST;
			default -> randomDirection = Direction.WEST;
		}

		// set the chest and add loot table
		world.setBlock(blockPos, chestBlockState.setValue(ChestBlock.FACING, randomDirection), 3);
		BlockEntity chestBlockEntity = world.getBlockEntity(blockPos);
		if (chestBlockEntity instanceof ChestBlockEntity) {
			((ChestBlockEntity) chestBlockEntity).setLootTable(lootTable, random.nextLong());
		}
	}
	
	protected @Nullable BlockPos findNextNonAirBlockInDirection(WorldGenLevel world, BlockPos blockPos, Direction direction, int maxBlocks) {
		for (int i = 0; i < maxBlocks; i++) {
			if (!world.getBlockState(blockPos.relative(direction, i)).isAir()) {
				return blockPos.relative(direction, i);
			}
		}
		return null;
	}
	
	protected List<BlockPos> getTopBlocks(WorldGenLevel world, ChunkPos chunkPos, PlacedSphere<?> sphere) {
		List<BlockPos> list = new ArrayList<>();
		
		int x = sphere.getPosition().getX();
		int y = sphere.getPosition().getY();
		int z = sphere.getPosition().getZ();
		
		int rad = sphere.getRadius();
		int minX = Math.max(chunkPos.getMinBlockX(), x - rad);
		int minZ = Math.max(chunkPos.getMinBlockZ(), z - rad);
		int maxX = Math.min(chunkPos.getMaxBlockX(), x + rad);
		int maxZ = Math.min(chunkPos.getMaxBlockZ(), z + rad);
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		for (int x2 = minX; x2 <= maxX; x2++) {
			for (int z2 = minZ; z2 <= maxZ; z2++) {
				for (int y2 = y + rad; y2 > y; y2--) {
					mutable.set(x2, y2, z2);
					if (!world.getBlockState(mutable).isAir()) {
						list.add(mutable.immutable());
						break;
					}
				}
			}
		}
		return list;
	}

	protected List<BlockPos> getAllBlocksInChunk(WorldGenLevel world, ChunkPos chunkPos, PlacedSphere<?> sphere) {
		List<BlockPos> list = new ArrayList<>();

		int x = sphere.getPosition().getX();
		int y = sphere.getPosition().getY();
		int z = sphere.getPosition().getZ();

		int rad = sphere.getRadius();
		int minX = Math.max(chunkPos.getMinBlockX(), x - rad);
		int minZ = Math.max(chunkPos.getMinBlockZ(), z - rad);
		int maxX = Math.min(chunkPos.getMaxBlockX(), x + rad);
		int maxZ = Math.min(chunkPos.getMaxBlockZ(), z + rad);

		for (int x2 = minX; x2 <= maxX; x2++) {
			for (int z2 = minZ; z2 <= maxZ; z2++) {
				for (int y2 = y + rad; y2 > y - rad; y2--) {
					list.add(new BlockPos(x2, y2, z2));
				}
			}
		}
		return list;
	}
	
	protected List<BlockPos> getBottomBlocks(WorldGenLevel world, ChunkPos chunkPos, PlacedSphere<?> sphere) {
		List<BlockPos> list = new ArrayList<>();
		
		int x = sphere.getPosition().getX();
		int y = sphere.getPosition().getY();
		int z = sphere.getPosition().getZ();
		
		int rad = sphere.getRadius();
		int minX = Math.max(chunkPos.getMinBlockX(), x - rad);
		int minZ = Math.max(chunkPos.getMinBlockZ(), z - rad);
		int maxX = Math.min(chunkPos.getMaxBlockX(), x + rad);
		int maxZ = Math.min(chunkPos.getMaxBlockZ(), z + rad);
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		for (int x2 = minX; x2 <= maxX; x2++) {
			for (int z2 = minZ; z2 <= maxZ; z2++) {
				for (int y2 = y - rad; y2 < y; y2++) {
					mutable.set(x2, y2, z2);
					if (!world.getBlockState(mutable).isAir()) {
						list.add(mutable.immutable());
						break;
					}
				}
			}
		}
		return list;
	}
	
	protected List<BlockPos> getTopBlocks(WorldGenLevel world, ChunkPos chunkPos, PlacedSphere<?> sphere, RandomSource random, int amount) {
		List<BlockPos> list = new ArrayList<>();
		
		int x = sphere.getPosition().getX();
		int y = sphere.getPosition().getY();
		int z = sphere.getPosition().getZ();
		
		int rad = sphere.getRadius();
		int minX = Math.max(chunkPos.getMinBlockX(), x - rad);
		int minZ = Math.max(chunkPos.getMinBlockZ(), z - rad);
		int maxX = Math.min(chunkPos.getMaxBlockX(), x + rad);
		int maxZ = Math.min(chunkPos.getMaxBlockZ(), z + rad);
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

		for (int i = 0; i < amount; i++) {
			int x2 = minX + random.nextInt(maxX - minX + 1);
			int z2 = minZ + random.nextInt(maxZ - minZ + 1);
			for (int y2 = y + rad; y2 > y; y2--) {
				mutable.set(x2, y2, z2);
				if (!world.getBlockState(mutable).isAir()) {
					list.add(mutable.immutable());
					break;
				}
			}
		}

		return list;
	}
	
	protected List<BlockPos> getCaveBottomBlocks(WorldGenLevel world, ChunkPos chunkPos, PlacedSphere<?> sphere) {
		List<BlockPos> list = new ArrayList<>();
		
		int x = sphere.getPosition().getX();
		int y = sphere.getPosition().getY();
		int z = sphere.getPosition().getZ();
		
		int rad = sphere.getRadius();
		int minX = Math.max(chunkPos.getMinBlockX(), x - rad);
		int minZ = Math.max(chunkPos.getMinBlockZ(), z - rad);
		int maxX = Math.min(chunkPos.getMaxBlockX(), x + rad);
		int maxZ = Math.min(chunkPos.getMaxBlockZ(), z + rad);
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		for (int x2 = minX; x2 <= maxX; x2++) {
			for (int z2 = minZ; z2 <= maxZ; z2++) {
				boolean hitShell = false;
				for (int y2 = y - rad; y2 < y; y2++) {
					mutable.set(x2, y2, z2);
					BlockState state = world.getBlockState(mutable);
					boolean airOrFluid = state.isAir() || state.getFluidState().getType() != Fluids.EMPTY;
					if (airOrFluid && !hitShell) {

					} else if (!airOrFluid) {
						hitShell = true;
					} else {
						list.add(mutable.below().immutable());
						break;
					}
				}
			}
		}

		return list;
	}

	protected List<BlockPos> getCaveCeilingBlocks(WorldGenLevel world, ChunkPos chunkPos, PlacedSphere<?> sphere) {
		List<BlockPos> list = new ArrayList<>();

		int x = sphere.getPosition().getX();
		int y = sphere.getPosition().getY();
		int z = sphere.getPosition().getZ();

		int rad = sphere.getRadius();
		int minX = Math.max(chunkPos.getMinBlockX(), x - rad);
		int minZ = Math.max(chunkPos.getMinBlockZ(), z - rad);
		int maxX = Math.min(chunkPos.getMaxBlockX(), x + rad);
		int maxZ = Math.min(chunkPos.getMaxBlockZ(), z + rad);
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		for (int x2 = minX; x2 <= maxX; x2++) {
			for (int z2 = minZ; z2 <= maxZ; z2++) {
				boolean hitShell = false;
				for (int y2 = y + rad; y2 > y; y2--) {
					mutable.set(x2, y2, z2);
					BlockState state = world.getBlockState(mutable);
					boolean airOrFluid = state.isAir() || state.getFluidState().getType() != Fluids.EMPTY;
					if (airOrFluid && !hitShell) {

					} else if (!airOrFluid) {
						hitShell = true;
					} else {
						list.add(mutable.immutable());
						break;
					}
				}
			}
		}

		return list;
	}
	
	protected @Nullable BlockPos getCaveBottomBlock(WorldGenLevel world, BlockPos pos, PlacedSphere<?> sphere) {
		int x = sphere.getPosition().getX();
		int z = sphere.getPosition().getZ();
		int y = sphere.getPosition().getY();
		
		int rad = sphere.getRadius();
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		boolean hitShell = false;
		
		for (int y2 = y - rad; y2 < y; y2++) {
			mutable.set(x, y2, z);
			BlockState state = world.getBlockState(mutable);
			boolean airOrFluid = state.isAir() || state.getFluidState().getType() != Fluids.EMPTY;
			if (airOrFluid && !hitShell) {
			
			} else if (!airOrFluid) {
				hitShell = true;
			} else {
				return mutable.below();
			}
		}
		
		return null;
	}
	
	protected List<BlockPos> getRandomCaveBottomBlocks(WorldGenLevel world, ChunkPos chunkPos, PlacedSphere<?> sphere, RandomSource random, int amount) {
		List<BlockPos> list = new ArrayList<>();
		
		int x = sphere.getPosition().getX();
		int y = sphere.getPosition().getY();
		int z = sphere.getPosition().getZ();
		
		int rad = sphere.getRadius();
		int minX = Math.max(chunkPos.getMinBlockX(), x - rad);
		int minZ = Math.max(chunkPos.getMinBlockZ(), z - rad);
		int maxX = Math.min(chunkPos.getMaxBlockX(), x + rad);
		int maxZ = Math.min(chunkPos.getMaxBlockZ(), z + rad);
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

		for (int i = 0; i < amount; i++) {
			int x2 = minX + random.nextInt(maxX - minX + 1);
			int z2 = minZ + random.nextInt(maxZ - minZ + 1);
			boolean hitShell = false;
			for (int y2 = y - rad; y2 < y; y2++) {
				mutable.set(x2, y2, z2);
				BlockState state = world.getBlockState(mutable);
				boolean airOrFluid = state.isAir() || state.getFluidState().getType() != Fluids.EMPTY;
				if (airOrFluid && !hitShell) {

				} else if (!airOrFluid) {
					hitShell = true;
				} else {
					list.add(mutable.below().immutable());
					break;
				}
			}
		}
		return list;
	}


}
