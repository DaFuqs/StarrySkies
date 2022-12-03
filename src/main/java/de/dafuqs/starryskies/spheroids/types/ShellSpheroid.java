package de.dafuqs.starryskies.spheroids.types;

import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.dafuqs.starryskies.Support;
import de.dafuqs.starryskies.spheroids.BlockStateSupplier;
import de.dafuqs.starryskies.spheroids.SpheroidDecorator;
import net.minecraft.block.BlockState;
import net.minecraft.command.argument.BlockArgumentParser;
import net.minecraft.entity.EntityType;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.chunk.Chunk;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShellSpheroid extends Spheroid {
	
	protected BlockState innerBlock;
	protected BlockState shellBlock;
	protected float shellRadius;
	private final LinkedHashMap<BlockState, Float> shellSpeckleBlockStates;
	
	public ShellSpheroid(Spheroid.Template template, float radius, List<SpheroidDecorator> decorators, List<Pair<EntityType, Integer>> spawns, ChunkRandom random,
	                     BlockState innerBlock, BlockState shellBlock, float shellRadius, LinkedHashMap<BlockState, Float> shellSpeckleBlockStates) {
		
		super(template, radius, decorators, spawns, random);
		this.radius = radius;
		this.innerBlock = innerBlock;
		this.shellBlock = shellBlock;
		this.shellRadius = shellRadius;
		this.shellSpeckleBlockStates = shellSpeckleBlockStates;
	}
	
	public static class Template extends Spheroid.Template {
		
		private final BlockState innerBlock;
		private final BlockStateSupplier shellBlock;
		private final int minShellRadius;
		private final int maxShellRadius;
		private final LinkedHashMap<BlockState, Float> shellSpeckleBlockStates = new LinkedHashMap<>();
		
		public Template(JsonObject data) throws CommandSyntaxException {
			super(data);
			
			JsonObject typeData = JsonHelper.getObject(data, "type_data");
			this.minShellRadius = JsonHelper.getInt(typeData, "min_shell_size");
			this.maxShellRadius = JsonHelper.getInt(typeData, "max_shell_size");
			this.innerBlock = BlockArgumentParser.block(Registry.BLOCK, JsonHelper.getString(typeData, "main_block"), false).blockState();
			this.shellBlock = BlockStateSupplier.of(typeData.get("shell_block"));
		}
		
		@Override
		public ShellSpheroid generate(ChunkRandom random) {
			return new ShellSpheroid(this, randomBetween(random, minSize, maxSize), selectDecorators(random), selectSpawns(random), random, innerBlock, shellBlock.get(random), randomBetween(random, minShellRadius, maxShellRadius), shellSpeckleBlockStates);
		}
		
	}
	
	public String getDescription() {
		return "+++ ShellSpheroid +++" +
				"\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
				"\nTemplateID: " + this.template.getID() +
				"\nRadius: " + this.radius +
				"\nShell: " + this.shellBlock.toString() + " (Radius: " + this.shellRadius + ")" +
				"\nCore: " + this.innerBlock.toString();
	}
	
	@Override
	public void generate(Chunk chunk) {
		int chunkX = chunk.getPos().x;
		int chunkZ = chunk.getPos().z;
		
		int x = this.getPosition().getX();
		int y = this.getPosition().getY();
		int z = this.getPosition().getZ();
		
		boolean hasSpeckles = hasSpeckles();
		
		random.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
		for (float x2 = Math.max(chunkX * 16, x - this.radius); x2 <= Math.min(chunkX * 16 + 15, x + this.radius); x2++) {
			for (float y2 = y - this.radius; y2 <= y + this.radius; y2++) {
				for (float z2 = Math.max(chunkZ * 16, z - this.radius); z2 <= Math.min(chunkZ * 16 + 15, z + this.radius); z2++) {
					BlockPos currBlockPos = new BlockPos(x2, y2, z2);
					long d = Math.round(Support.getDistance(x, y, z, x2, y2, z2));
					if (d <= (this.radius - this.shellRadius)) {
						chunk.setBlockState(currBlockPos, this.innerBlock, false);
					} else if (d <= this.radius) {
						if (hasSpeckles) {
							BlockState finalBlockState = shellBlock;
							for (Map.Entry<BlockState, Float> shellSpeckleBlockState : shellSpeckleBlockStates.entrySet()) {
								if (random.nextFloat() < shellSpeckleBlockState.getValue()) {
									finalBlockState = shellSpeckleBlockState.getKey();
									break;
								}
							}
							chunk.setBlockState(currBlockPos, finalBlockState, false);
						} else {
							chunk.setBlockState(currBlockPos, this.shellBlock, false);
						}
						if (isTopBlock(d, x2, y2, z2)) {
							addDecorationBlockPosition(currBlockPos);
						}
					}
				}
			}
		}
	}
	
	private boolean hasSpeckles() {
		return this.shellSpeckleBlockStates.size() > 0;
	}
	
}
