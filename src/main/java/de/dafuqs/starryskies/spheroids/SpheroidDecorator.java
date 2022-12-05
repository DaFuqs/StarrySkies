package de.dafuqs.starryskies.spheroids;

import de.dafuqs.starryskies.spheroids.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class SpheroidDecorator {
	
	/**
	 * In contrast to vanilla the spheroid decorators are queried by the spheroid
	 * not ran after the chunk generation
	 * The spheroid tracks all blocks that can be decorated and the decorator
	 * takes them, checks for spawning criteria and
	 */
	public abstract void decorateSpheroid(StructureWorldAccess world, ChunkPos origin, Spheroid spheroid, Random random);
	
	protected void placeLootChest(@NotNull StructureWorldAccess world, BlockPos blockPos, Identifier lootTable, Random random) {
		BlockState chestBlockState = Blocks.CHEST.getDefaultState();
		
		// if the chest is placed in water: waterlog it!
		if (world.getBlockState(blockPos) == Blocks.WATER.getDefaultState()) {
			chestBlockState = chestBlockState.with(ChestBlock.WATERLOGGED, true);
		}
		
		// Random direction placement for the chest
		int r = random.nextInt(4);
		Direction randomDirection;
		switch (r) {
			case 0 -> {
				randomDirection = Direction.NORTH;
			}
			case 1 -> {
				randomDirection = Direction.SOUTH;
			}
			case 2 -> {
				randomDirection = Direction.EAST;
			}
			default -> {
				randomDirection = Direction.WEST;
			}
		}
		
		// set the chest and add loot table
		world.setBlockState(blockPos, chestBlockState.with(ChestBlock.FACING, randomDirection), 3);
		BlockEntity chestBlockEntity = world.getBlockEntity(blockPos);
		if (chestBlockEntity instanceof ChestBlockEntity) {
			((ChestBlockEntity) chestBlockEntity).setLootTable(lootTable, random.nextLong());
		}
	}
	
	protected BlockPos findNextNonAirBlockInDirection(StructureWorldAccess world, BlockPos blockPos, Direction direction, int maxBlocks) {
		for (int i = 0; i < maxBlocks; i++) {
			if (!world.getBlockState(blockPos.offset(direction, i)).isAir()) {
				return blockPos;
			}
		}
		return null;
	}
	
	protected List<BlockPos> getTopBlocks(StructureWorldAccess world, ChunkPos chunkPos, Spheroid spheroid) {
		List<BlockPos> list = new ArrayList<>();
		
		int x = spheroid.getPosition().getX();
		int y = spheroid.getPosition().getY();
		int z = spheroid.getPosition().getZ();
		
		int rad = spheroid.getRadius();
		int maxX = Math.min(chunkPos.getEndX(), x + rad);
		int maxZ = Math.min(chunkPos.getEndZ(), z + rad);
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		for (int x2 = Math.max(chunkPos.getStartX(), x - rad); x2 <= maxX; x2++) {
			for (int z2 = Math.max(chunkPos.getStartZ(), z - rad); z2 <= maxZ; z2++) {
				for (int y2 = y + rad; y2 > y; y2--) {
					mutable.set(x2, y2, z2);
					if (!world.getBlockState(mutable).isAir()) {
						list.add(mutable.toImmutable());
						break;
					}
				}
			}
		}
		return list;
	}
	
	protected List<BlockPos> getBottomBlocks(StructureWorldAccess world, ChunkPos chunkPos, Spheroid spheroid) {
		List<BlockPos> list = new ArrayList<>();
		
		int x = spheroid.getPosition().getX();
		int y = spheroid.getPosition().getY();
		int z = spheroid.getPosition().getZ();
		
		int rad = spheroid.getRadius();
		int maxX = Math.min(chunkPos.getEndX(), x + rad);
		int maxZ = Math.min(chunkPos.getEndZ(), z + rad);
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		for (int x2 = Math.max(chunkPos.getStartX(), x - rad); x2 <= maxX; x2++) {
			for (int z2 = Math.max(chunkPos.getStartZ(), z - rad); z2 <= maxZ; z2++) {
				for (int y2 = y - rad; y2 < y; y2++) {
					mutable.set(x2, y2, z2);
					if (!world.getBlockState(mutable).isAir()) {
						list.add(mutable.toImmutable());
						break;
					}
				}
			}
		}
		return list;
	}
	
	protected List<BlockPos> getTopBlocks(StructureWorldAccess world, ChunkPos chunkPos, Spheroid spheroid, Random random, int amount) {
		List<BlockPos> list = new ArrayList<>();
		
		int x = spheroid.getPosition().getX();
		int y = spheroid.getPosition().getY();
		int z = spheroid.getPosition().getZ();
		
		int rad = spheroid.getRadius();
		int minX = Math.max(chunkPos.getStartX(), x - rad);
		int minZ = Math.max(chunkPos.getStartZ(), z - rad);
		int maxX = Math.min(chunkPos.getEndX(), x + rad);
		int maxZ = Math.min(chunkPos.getEndZ(), z + rad);
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		
		for (int i = 0; i < amount; i++) {
			int x2 = minX + random.nextInt(maxX - minX + 1);
			int z2 = minX + random.nextInt(maxZ - minZ + 1);
			for (int y2 = y + rad; y2 > y; y2--) {
				mutable.set(x2, y2, z2);
				if (!world.getBlockState(mutable).isAir()) {
					list.add(mutable.toImmutable());
					break;
				}
			}
		}
		
		return list;
	}
	
	protected List<BlockPos> getBottomBlocks(StructureWorldAccess world, ChunkPos chunkPos, Spheroid spheroid, Random random, int amount) {
		List<BlockPos> list = new ArrayList<>();
		
		int x = spheroid.getPosition().getX();
		int y = spheroid.getPosition().getY();
		int z = spheroid.getPosition().getZ();
		
		int rad = spheroid.getRadius();
		int minX = Math.max(chunkPos.getStartX(), x - rad);
		int minZ = Math.max(chunkPos.getStartZ(), z - rad);
		int maxX = Math.min(chunkPos.getEndX(), x + rad);
		int maxZ = Math.min(chunkPos.getEndZ(), z + rad);
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		
		for (int i = 0; i < amount; i++) {
			int x2 = minX + random.nextInt(maxX - minX + 1);
			int z2 = minX + random.nextInt(maxZ - minZ + 1);
			for (int y2 = y - rad; y2 < y; y2++) {
				mutable.set(x2, y2, z2);
				if (!world.getBlockState(mutable).isAir()) {
					list.add(mutable.toImmutable());
					break;
				}
			}
		}
		
		return list;
	}
	
	protected List<BlockPos> getCaveBottomBlocks(StructureWorldAccess world, ChunkPos chunkPos, Spheroid spheroid) {
		List<BlockPos> list = new ArrayList<>();
		
		int x = spheroid.getPosition().getX();
		int y = spheroid.getPosition().getY();
		int z = spheroid.getPosition().getZ();
		
		int rad = spheroid.getRadius();
		int maxX = Math.min(chunkPos.getEndX(), x + rad);
		int maxZ = Math.min(chunkPos.getEndZ(), z + rad);
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		for (int x2 = Math.max(chunkPos.getStartX(), x - rad); x2 <= maxX; x2++) {
			for (int z2 = Math.max(chunkPos.getStartZ(), z - rad); z2 <= maxZ; z2++) {
				boolean hitShell = false;
				for (int y2 = y - rad; y2 < y; y2++) {
					mutable.set(x2, y2, z2);
					BlockState state = world.getBlockState(mutable);
					boolean airOrFluid = state.isAir() || state.getFluidState().getFluid() != Fluids.EMPTY;
					if (airOrFluid && !hitShell) {
					
					} else if (!airOrFluid) {
						hitShell = true;
					} else {
						list.add(mutable.down().toImmutable());
						break;
					}
				}
			}
		}
		
		return list;
	}
	
	protected List<BlockPos> getCaveBottomBlocks(StructureWorldAccess world, ChunkPos chunkPos, Spheroid spheroid, Random random, int amount) {
		List<BlockPos> list = new ArrayList<>();
		
		int x = spheroid.getPosition().getX();
		int y = spheroid.getPosition().getY();
		int z = spheroid.getPosition().getZ();
		
		int rad = spheroid.getRadius();
		int minX = Math.max(chunkPos.getStartX(), x - rad);
		int minZ = Math.max(chunkPos.getStartZ(), z - rad);
		int maxX = Math.min(chunkPos.getEndX(), x + rad);
		int maxZ = Math.min(chunkPos.getEndZ(), z + rad);
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		
		for (int i = 0; i < amount; i++) {
			int x2 = minX + random.nextInt(maxX - minX + 1);
			int z2 = minX + random.nextInt(maxZ - minZ + 1);
			boolean hitShell = false;
			for (int y2 = y - rad; y2 < y; y2++) {
				mutable.set(x2, y2, z2);
				BlockState state = world.getBlockState(mutable);
				boolean airOrFluid = state.isAir() || state.getFluidState().getFluid() != Fluids.EMPTY;
				if (airOrFluid && !hitShell) {
				
				} else if (!airOrFluid) {
					hitShell = true;
				} else {
					list.add(mutable.down().toImmutable());
					break;
				}
			}
		}
		return list;
	}
	
}