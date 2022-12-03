package de.dafuqs.starryskies.spheroids.types;

import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.dafuqs.starryskies.Support;
import de.dafuqs.starryskies.spheroids.SpheroidDecorator;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.command.argument.BlockArgumentParser;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.chunk.Chunk;

import java.util.List;

public class CaveSpheroid extends Spheroid {
	
	private final BlockState coreBlock = Blocks.CAVE_AIR.getDefaultState();
	private final BlockState caveFloorBlock;
	private final BlockState topBlock;
	private final BlockState bottomBlock;
	private final BlockState shellBlock;
	private final float shellRadius;
	Identifier chestLootTable;
	
	public CaveSpheroid(Spheroid.Template template, float radius, List<SpheroidDecorator> decorators, List<Pair<EntityType, Integer>> spawns, ChunkRandom random,
	                    BlockState caveFloorBlock, BlockState shellBlock, float shellRadius, BlockState topBlock, BlockState bottomBlock, Identifier chestLootTable) {
		
		super(template, radius, decorators, spawns, random);
		
		this.caveFloorBlock = caveFloorBlock;
		this.shellBlock = shellBlock;
		this.shellRadius = shellRadius;
		this.topBlock = topBlock;
		this.bottomBlock = bottomBlock;
		this.chestLootTable = chestLootTable;
	}
	
	public static class Template extends Spheroid.Template {
		
		private final BlockState shellBlock;
		private final int minShellRadius;
		private final int maxShellRadius;
		private final BlockState caveFloorBlock;
		private BlockState topBlock = null;
		private BlockState bottomBlock = null;
		private Identifier lootTable = null;
		private float lootTableChance = 0;
		
		public Template(JsonObject data) throws CommandSyntaxException {
			super(data);
			
			JsonObject typeData = JsonHelper.getObject(data, "type_data");
			this.minShellRadius = JsonHelper.getInt(typeData, "min_shell_size");
			this.maxShellRadius = JsonHelper.getInt(typeData, "max_shell_size");
			this.shellBlock = BlockArgumentParser.block(Registry.BLOCK, JsonHelper.getString(typeData, "shell_block"), false).blockState();
			if(JsonHelper.hasString(typeData, "top_block")) {
				this.topBlock = BlockArgumentParser.block(Registry.BLOCK, JsonHelper.getString(typeData, "top_block"), false).blockState();
			}
			if(JsonHelper.hasString(typeData, "bottom_block")) {
				this.bottomBlock = BlockArgumentParser.block(Registry.BLOCK, JsonHelper.getString(typeData, "bottom_block"), false).blockState();
			}
			if(JsonHelper.hasString(typeData, "cave_floor_block")) {
				this.caveFloorBlock = BlockArgumentParser.block(Registry.BLOCK, JsonHelper.getString(typeData, "cave_floor_block"), false).blockState();
			} else {
				this.caveFloorBlock = shellBlock;
			}
			if(JsonHelper.hasJsonObject(typeData, "treasure_chest")) {
				JsonObject treasureChestObject = JsonHelper.getObject(typeData, "treasure_chest");
				this.lootTable = Identifier.tryParse(JsonHelper.getString(treasureChestObject, "loot_table"));
				this.lootTableChance = JsonHelper.getFloat(treasureChestObject, "chance");
			}
		}
		
		public BlockState getTopBlock() {
			return topBlock != null ? topBlock : shellBlock;
		}
		
		public BlockState getBottomBlock() {
			return bottomBlock != null ? bottomBlock : shellBlock;
		}
		
		@Override
		public CaveSpheroid generate(ChunkRandom random) {
			int shellRadius = Support.getRandomBetween(random, this.minShellRadius, this.maxShellRadius);
			BlockState topBlock = getTopBlock();
			BlockState bottomBlock = getBottomBlock();
			
			Identifier lootTable = null;
			if (random.nextFloat() < lootTableChance) {
				lootTable = this.lootTable;
			}
			return new CaveSpheroid(this, randomBetween(random, minSize, maxSize), selectDecorators(random), selectSpawns(random), random, caveFloorBlock, shellBlock, shellRadius, getTopBlock(), getBottomBlock(), lootTable);
		}
		
	}
	
	@Override
	public String getDescription() {
		String s = "+++ CaveSpheroid +++" +
				"\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
				"\nTemplateID: " + this.template.getID() +
				"\nRadius: " + this.radius +
				"\nShellBlock: " + this.shellBlock +
				"\nShellRadius: " + this.shellRadius +
				"\nCaveFloorBlock: " + this.caveFloorBlock;
		
		if (this.topBlock != null) {
			s += "\nTopBlock: " + this.topBlock;
		}
		if (this.bottomBlock != null) {
			s += "\nBottomBlock: " + this.bottomBlock;
		}
		return s;
	}
	
	@Override
	public void generate(Chunk chunk) {
		int chunkX = chunk.getPos().x;
		int chunkZ = chunk.getPos().z;
		
		int x = this.getPosition().getX();
		int y = this.getPosition().getY();
		int z = this.getPosition().getZ();
		
		boolean hasChest = this.chestLootTable != null;
		
		for (float x2 = Math.max(chunkX * 16, x - this.radius); x2 <= Math.min(chunkX * 16 + 15, x + this.radius); x2++) {
			for (float y2 = y - this.radius; y2 <= y + this.radius; y2++) {
				for (float z2 = Math.max(chunkZ * 16, z - this.radius); z2 <= Math.min(chunkZ * 16 + 15, z + this.radius); z2++) {
					BlockPos currBlockPos = new BlockPos(x2, y2, z2);
					long d = Math.round(Support.getDistance(x, y, z, x2, y2, z2));
					if (d == this.radius) {
						if (isBottomBlock(d, x2, y2, z2)) {
							chunk.setBlockState(currBlockPos, this.bottomBlock, false);
						} else if (isTopBlock(d, x2, y2, z2)) {
							chunk.setBlockState(currBlockPos, this.topBlock, false);
						} else {
							chunk.setBlockState(currBlockPos, this.shellBlock, false);
						}
					} else if (isAboveCaveFloorBlock(d, x2, y2, z2, shellRadius)) {
						chunk.setBlockState(currBlockPos.down(), this.caveFloorBlock, false);
						addDecorationBlockPosition(currBlockPos.down());
						if (hasChest && x2 - x == 0 && z2 - z == 0) {
							placeCenterChestWithLootTable(chunk, currBlockPos, chestLootTable, random, false);
						}
					} else if (d <= this.radius - this.shellRadius) {
						chunk.setBlockState(currBlockPos, this.coreBlock, false); // always CAVE_AIR
					} else if (d < this.radius) {
						chunk.setBlockState(currBlockPos, this.shellBlock, false);
					}
				}
			}
		}
	}
	
}
