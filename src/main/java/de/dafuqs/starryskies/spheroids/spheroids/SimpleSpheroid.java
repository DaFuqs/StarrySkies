package de.dafuqs.starryskies.spheroids.spheroids;

import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.Support;
import de.dafuqs.starryskies.spheroids.SpheroidDecorator;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.world.chunk.Chunk;

import java.util.List;

public class SimpleSpheroid extends Spheroid {
	
	private final BlockState blockState;
	
	public SimpleSpheroid(Spheroid.Template template, float radius, List<SpheroidDecorator> decorators, List<Pair<EntityType<?>, Integer>> spawns, ChunkRandom random,
	                      BlockState blockState) {
		
		super(template, radius, decorators, spawns, random);
		this.blockState = blockState;
	}
	
	public static class Template extends Spheroid.Template {
		
		protected final BlockState blockState;
		
		public Template(Identifier identifier, JsonObject data) throws CommandSyntaxException {
			super(identifier, data);
			
			JsonObject typeData = JsonHelper.getObject(data, "type_data");
			this.blockState = StarrySkies.getStateFromString(JsonHelper.getString(typeData, "block"));
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
		int x = this.getPosition().getX();
		int y = this.getPosition().getY();
		int z = this.getPosition().getZ();
		
		int ceiledRadius = (int) Math.ceil(this.radius);
		int maxX = Math.min(chunkX * 16 + 15, x + ceiledRadius);
		int maxZ = Math.min(chunkZ * 16 + 15, z + ceiledRadius);
		BlockPos.Mutable currBlockPos = new BlockPos.Mutable();
		for (int x2 = Math.max(chunkX * 16, x - ceiledRadius); x2 <= maxX; x2++) {
			for (int y2 = y - ceiledRadius; y2 <= y + ceiledRadius; y2++) {
				for (int z2 = Math.max(chunkZ * 16, z - ceiledRadius); z2 <= maxZ; z2++) {
					long d = Math.round(Support.getDistance(x, y, z, x2, y2, z2));
					if (d > this.radius) {
						continue;
					}
					currBlockPos.set(x2, y2, z2);
					
					chunk.setBlockState(currBlockPos, this.blockState, false);
				}
			}
		}
	}
	
}
