package de.dafuqs.starryskies.spheroids.spheroids;

import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.dafuqs.starryskies.Support;
import de.dafuqs.starryskies.spheroids.BlockStateSupplier;
import de.dafuqs.starryskies.spheroids.SpheroidDecorator;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.chunk.Chunk;

import java.util.List;

public class FluidCoreSpheroid extends Spheroid {
	
	private final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
	private final BlockState fluidBlock;
	private final BlockState shellBlock;
	private final float shellRadius;
	private final float fillAmount;
	private final boolean holeInBottom;
	private final BlockState coreBlock;
	private float coreRadius;
	
	public FluidCoreSpheroid(Spheroid.Template template, float radius, List<SpheroidDecorator> decorators, List<Pair<EntityType, Integer>> spawns, ChunkRandom random,
	                         BlockState fluidBlock, BlockState shellBlock, float shellRadius, float fillAmount, boolean holeInBottom, BlockState coreBlock, float coreRadius) {
		
		super(template, radius, decorators, spawns, random);
		this.fluidBlock = fluidBlock;
		this.shellBlock = shellBlock;
		this.shellRadius = shellRadius;
		this.fillAmount = fillAmount;
		this.holeInBottom = holeInBottom;
		this.coreBlock = coreBlock;
		
		if (this.coreBlock != null) {
			this.coreRadius = coreRadius;
		} else {
			this.coreRadius = 0;
		}
		
		if (this.coreRadius >= this.radius - this.shellRadius - 1) {
			this.coreRadius = this.radius - this.shellRadius - 2;
		}
	}
	
	public static class Template extends Spheroid.Template {
		
		private final Fluid fluid;
		private final float minFillAmount;
		private final float maxFillAmount;
		private final float holeInBottomChance;
		
		private final BlockStateSupplier shellBlock;
		private final int minShellRadius;
		private final int maxShellRadius;
		
		private final BlockStateSupplier coreBlock;
		private final int minCoreRadius;
		private final int maxCoreRadius;
		
		public Template(Identifier identifier, JsonObject data) throws CommandSyntaxException {
			super(identifier, data);
			
			JsonObject typeData = JsonHelper.getObject(data, "type_data");
			this.fluid = Registry.FLUID.get(Identifier.tryParse(JsonHelper.getString(typeData, "fluid")));
			this.minShellRadius = JsonHelper.getInt(typeData, "min_shell_size");
			this.maxShellRadius = JsonHelper.getInt(typeData, "max_shell_size");
			this.minFillAmount = JsonHelper.getFloat(typeData, "min_fill_amount");
			this.maxFillAmount = JsonHelper.getFloat(typeData, "max_fill_amount");
			this.holeInBottomChance = JsonHelper.getFloat(typeData, "hole_in_bottom_chance");
			this.shellBlock = BlockStateSupplier.of(typeData.get("shell_block"));
			this.coreBlock = BlockStateSupplier.of(typeData.get("core_block"));
			this.minCoreRadius = JsonHelper.getInt(typeData, "min_core_size");
			this.maxCoreRadius = JsonHelper.getInt(typeData, "max_core_size");
		}
		
		@Override
		public FluidCoreSpheroid generate(ChunkRandom random) {
			int shellRadius = Support.getRandomBetween(random, this.minShellRadius, this.maxShellRadius);
			int coreRadius = Support.getRandomBetween(random, this.minCoreRadius, this.maxCoreRadius);
			float fillAmount = Support.getRandomBetween(random, this.minFillAmount, this.maxFillAmount);
			boolean holeInBottom = random.nextFloat() < this.holeInBottomChance;
			BlockState fluidBlockState = this.fluid.getDefaultState().getBlockState();
			return new FluidCoreSpheroid(this, randomBetween(random, minSize, maxSize), selectDecorators(random), selectSpawns(random), random, fluidBlockState, shellBlock.get(random), shellRadius, fillAmount, holeInBottom, coreBlock.get(random), coreRadius);
		}
		
	}
	
	public String getDescription() {
		return "+++ FluidCoreSpheroid +++" +
				"\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
				"\nTemplateID: " + this.template.getID() +
				"\nRadius: " + this.radius +
				"\nShell: " + this.shellBlock.toString() + "(Radius: " + this.shellRadius + ")" +
				"\nLiquid: " + this.fluidBlock.toString() +
				"\nCore: " + this.coreBlock + "(Radius: " + this.coreRadius + ")" +
				"\nFill Percent: " + this.fillAmount +
				"\nHole in bottom: " + this.holeInBottom;
	}
	
	@Override
	public void generate(Chunk chunk) {
		int chunkX = chunk.getPos().x;
		int chunkZ = chunk.getPos().z;
		
		int x = this.getPosition().getX();
		int y = this.getPosition().getY();
		int z = this.getPosition().getZ();
		
		float liquidRadius = this.radius - this.shellRadius;
		float maxLiquidY = y + (this.fillAmount * liquidRadius * 2 - liquidRadius);
		
		random.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
		int ceiledRadius = (int) Math.ceil(this.radius);
		for (float x2 = Math.max(chunkX * 16, x - ceiledRadius); x2 <= Math.min(chunkX * 16 + 15, x + ceiledRadius); x2++) {
			for (float y2 = y - ceiledRadius; y2 <= y + ceiledRadius; y2++) {
				for (float z2 = Math.max(chunkZ * 16, z - ceiledRadius); z2 <= Math.min(chunkZ * 16 + 15, z + ceiledRadius); z2++) {
					BlockPos currBlockPos = new BlockPos(x2, y2, z2);
					long d = Math.round(Support.getDistance(x, y, z, x2, y2, z2));
					if (this.holeInBottom && (x - x2) == 0 && (z - z2) == 0 && (y - y2 + 1) >= liquidRadius) {
						chunk.setBlockState(new BlockPos(currBlockPos), this.fluidBlock, false);
						chunk.markBlockForPostProcessing(currBlockPos); // making it drop down after generation
					} else if (this.coreBlock != null && d <= this.coreRadius) {
						chunk.setBlockState(currBlockPos, this.coreBlock, false);
					} else if (d <= liquidRadius) {
						if (y2 <= maxLiquidY) {
							chunk.setBlockState(currBlockPos, this.fluidBlock, false);
							if (isAboveCaveFloorBlock(d, x2, y2, z2, shellRadius)) {
								addDecorationBlockPosition(currBlockPos.down());
							}
						} else {
							chunk.setBlockState(currBlockPos, CAVE_AIR, false);
						}
					} else if (d <= this.radius) {
						chunk.setBlockState(currBlockPos, this.shellBlock, false);
					}
				}
			}
		}
	}
	
}
