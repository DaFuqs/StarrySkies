package de.dafuqs.starryskies.spheroids.decorators;

import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.dafuqs.starryskies.Support;
import de.dafuqs.starryskies.spheroids.SpheroidDecorator;
import de.dafuqs.starryskies.spheroids.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.command.argument.BlockArgumentParser;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.StructureWorldAccess;


public class CenterPondDecorator extends SpheroidDecorator {
	
	private final Identifier lootTable;
	private final float lootTableChance;
	private final BlockState beachBlock;
	private final BlockState fluidBlock;
	
	public CenterPondDecorator(JsonObject data) throws CommandSyntaxException {
		super(data);
		this.beachBlock = BlockArgumentParser.block(Registry.BLOCK, JsonHelper.getString(data, "beach_block"), false).blockState();
		this.fluidBlock = BlockArgumentParser.block(Registry.BLOCK, JsonHelper.getString(data, "fluid_block"), false).blockState();
		this.lootTable = Identifier.tryParse(JsonHelper.getString(data, "loot_table"));
		this.lootTableChance = JsonHelper.getFloat(data, "loot_table_chance");
	}

	@Override
	public void decorate(StructureWorldAccess world, ChunkPos origin, Spheroid spheroid, Random random) {
		if(!spheroid.isCenterInChunk(origin)) {
			return;
		}
		
		// doesn't make sense on small spheroids
		if (spheroid.getRadius() > 9) {
			int pondRadius = (int) (spheroid.getRadius() / 2.5);
			BlockPos spheroidTop = spheroid.getPosition().up(spheroid.getRadius());
			
			int waterLevelY = spheroidTop.getY();
			boolean waterLevelSet = false;
			
			for (int x = -pondRadius - 1; x <= pondRadius; x++) {
				for (int y = -pondRadius; y < 1; y++) {
					for (int z = -pondRadius - 1; z <= pondRadius; z++) {
						BlockPos currentBlockPos = spheroidTop.add(x, y, z);
						if (world.getBlockState(currentBlockPos).isAir()) {
							waterLevelY = currentBlockPos.getY() - 1;
							waterLevelSet = true;
							break;
						}
					}
					if (waterLevelSet) {
						break;
					}
				}
				if (waterLevelSet) {
					break;
				}
			}
			
			// if there is not enough room for water: just cancel
			// not nice, but eh
			if (waterLevelY - spheroid.getPosition().getY() < pondRadius * 1.5) {
				return;
			}
			
			boolean hasLootChest = random.nextFloat() < this.lootTableChance;
			BlockPos lootChestPosition = null;
			
			int pond15 = (int) Math.round(pondRadius * 1.5);
			for (int x = -pond15; x <= pond15; x++) {
				for (int y = -pondRadius; y < pondRadius; y++) {
					for (int z = -pond15; z <= pond15; z++) {
						BlockPos currentBlockPos = spheroidTop.add(x, y, z);
						
						BlockState blockState = null;
						if (currentBlockPos.getY() > waterLevelY) {
							blockState = Blocks.AIR.getDefaultState();
						} else {
							double distance = Support.getDistance(currentBlockPos, spheroidTop);
							double pondDistance = distance / pondRadius;
							if (pondDistance < 1.1) {
								if (hasLootChest && x == 0 && z == 0 && lootChestPosition == null) {
									lootChestPosition = currentBlockPos;
								}
								blockState = fluidBlock;
							} else if (pondDistance < 1.70) {
								blockState = beachBlock;
							}
						}
						
						if (blockState != null) {
							if (!world.getBlockState(currentBlockPos).isAir()) {
								world.setBlockState(currentBlockPos, blockState, 3);
							}
						}
						
					}
				}
			}
			
			if (lootChestPosition != null) {
				placeLootChest(world, lootChestPosition, lootTable, random);
			}
		}
	}
	
}
