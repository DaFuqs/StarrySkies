package de.dafuqs.starryskies.spheroids.spheroids;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.dafuqs.starryskies.Support;
import de.dafuqs.starryskies.spheroids.SpheroidDecorator;
import net.minecraft.block.BlockState;
import net.minecraft.command.argument.BlockArgumentParser;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.List;

public class RainbowSpheroid extends Spheroid {
	
	private final List<BlockState> rainbowBlocks;
	
	public RainbowSpheroid(Spheroid.Template template, float radius, List<SpheroidDecorator> decorators, List<Pair<EntityType, Integer>> spawns, ChunkRandom random,
	                       List<BlockState> rainbowBlocks) {
		
		super(template, radius, decorators, spawns, random);
		this.radius = radius;
		this.rainbowBlocks = rainbowBlocks;
	}
	
	public static class Template extends Spheroid.Template {
		
		private final List<BlockState> rainbowBlocks = new ArrayList<>();
		
		public Template(Identifier identifier, JsonObject data) throws CommandSyntaxException {
			super(identifier, data);
			
			JsonObject typeData = JsonHelper.getObject(data, "type_data");
			for(JsonElement e : JsonHelper.getArray(typeData, "blocks")) {
				BlockState state = BlockArgumentParser.block(Registry.BLOCK, e.getAsString(), false).blockState();
				rainbowBlocks.add(state);
			}
		}
		
		@Override
		public RainbowSpheroid generate(ChunkRandom random) {
			return new RainbowSpheroid(this, randomBetween(random, minSize, maxSize), selectDecorators(random), selectSpawns(random), random, rainbowBlocks);
		}
		
	}
	
	@Override
	public String getDescription() {
		return "+++ RainbowSpheroid +++" +
				"\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
				"\nTemplateID: " + this.template.getID() +
				"\nRadius: " + this.radius +
				"\nRainbow Blocks ( + " + this.getRainbowBlockCount() + "): " + this.rainbowBlocks.toString();
	}
	
	public int getRainbowBlockCount() {
		return this.rainbowBlocks.size();
	}
	
	@Override
	public void generate(Chunk chunk) {
		int chunkX = chunk.getPos().x;
		int chunkZ = chunk.getPos().z;
		
		int x = this.getPosition().getX();
		int y = this.getPosition().getY();
		int z = this.getPosition().getZ();
		
		random.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
		int ceiledRadius = (int) Math.ceil(this.radius);
		int maxX = Math.min(chunkX * 16 + 15, x + ceiledRadius);
		int maxZ =  Math.min(chunkZ * 16 + 15, z + ceiledRadius);
		for (int x2 = Math.max(chunkX * 16, x - ceiledRadius); x2 <= maxX; x2++) {
			for (int y2 = y - ceiledRadius; y2 <= y + ceiledRadius; y2++) {
				for (int z2 = Math.max(chunkZ * 16, z - ceiledRadius); z2 <= maxZ; z2++) {
					long d = Math.round(Support.getDistance(x, y, z, x2, y2, z2));
					if (d > this.radius) {
						continue;
					}
					BlockPos currBlockPos = new BlockPos(x2, y2, z2);
					
					int currentBlockID = (Math.abs(x2) + Math.abs(y2) + Math.abs(z2)) % this.getRainbowBlockCount();
					BlockState currentBlockState = this.rainbowBlocks.get(currentBlockID);
					chunk.setBlockState(currBlockPos, currentBlockState, false);
				}
			}
		}
	}
	
}