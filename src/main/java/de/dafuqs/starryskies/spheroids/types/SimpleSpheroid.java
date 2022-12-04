package de.dafuqs.starryskies.spheroids.types;

import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.dafuqs.starryskies.Support;
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

import java.util.List;

public class SimpleSpheroid extends Spheroid {
	
	private final BlockState blockState;
	
	public SimpleSpheroid(Spheroid.Template template, float radius, List<SpheroidDecorator> decorators, List<Pair<EntityType, Integer>> spawns, ChunkRandom random,
	                      BlockState blockState) {
		
		super(template, radius, decorators, spawns, random);
		this.blockState = blockState;
	}
	
	public static class Template extends Spheroid.Template {
		
		protected final BlockState blockState;
		
		public Template(JsonObject data) throws CommandSyntaxException {
			super(data);
			
			JsonObject typeData = JsonHelper.getObject(data, "type_data");
			this.blockState = BlockArgumentParser.block(Registry.BLOCK, JsonHelper.getString(typeData, "block"), false).blockState();
		}
		
		@Override
		public SimpleSpheroid generate(ChunkRandom random) {
			return new SimpleSpheroid(this, randomBetween(random, minSize, maxSize), selectDecorators(random), selectSpawns(random), random, blockState);
		}
		
	}
	
	public String getDescription() {
		return "+++ SimpleSpheroid +++" +
				"\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
				"\nTemplateID: " + this.template.getID() +
				"\nRadius: " + this.radius +
				"\nBlock: " + this.blockState.toString();
	}
	
	public void generate(Chunk chunk) {
		int chunkX = chunk.getPos().x;
		int chunkZ = chunk.getPos().z;
		
		random.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
		int posX = this.getPosition().getX();
		int posY = this.getPosition().getY();
		int posZ = this.getPosition().getZ();
		
		int ceiledRadius = (int) Math.ceil(this.radius);
		for (int currX = Math.max(chunkX * 16, posX - ceiledRadius); currX <= Math.min(chunkX * 16 + 15, posX + ceiledRadius); currX++) {
			for (int currY = posY - ceiledRadius; currY <= posY + ceiledRadius; currY++) {
				for (int currZ = Math.max(chunkZ * 16, posZ - ceiledRadius); currZ <= Math.min(chunkZ * 16 + 15, posZ + ceiledRadius); currZ++) {
					BlockPos currBlockPos = new BlockPos(currX, currY, currZ);
					double distance = Support.getDistance(posX, posY, posZ, currX, currY, currZ);
					
					if (distance <= this.radius) {
						chunk.setBlockState(currBlockPos, this.blockState, false);
					}
				}
			}
		}
	}
	
}
