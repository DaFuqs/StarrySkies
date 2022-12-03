package de.dafuqs.starryskies.spheroids;

import com.google.gson.*;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.dafuqs.starryskies.Support;
import net.minecraft.block.BlockState;
import net.minecraft.command.argument.BlockArgumentParser;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BlockStateSupplier {
	
	public static BlockStateSupplier of(JsonElement jsonElement) throws CommandSyntaxException {
		if(jsonElement instanceof JsonPrimitive) {
			return new SingleBlockStateSupplier(jsonElement);
		} else if(jsonElement instanceof JsonArray) {
			return new BlockStateListSupplier(jsonElement);
		} else if(jsonElement instanceof JsonObject) {
			return new WeightedBlockStateSupplier(jsonElement);
		}
		throw new JsonParseException("Could not parse json values as BlockStateSupplier: " + jsonElement);
	}
	
	public abstract BlockState get(Random random);
	
	public static class SingleBlockStateSupplier extends BlockStateSupplier {
		
		BlockState state;
		
		public SingleBlockStateSupplier(JsonElement json) throws CommandSyntaxException {
			state = BlockArgumentParser.block(Registry.BLOCK, json.getAsString(), false).blockState();
		}
		
		public BlockState get(Random random) {
			return state;
		}
		
	}
	
	public static class BlockStateListSupplier extends BlockStateSupplier {
		
		List<BlockState> states = new ArrayList<>();
		
		public BlockStateListSupplier(JsonElement json) throws CommandSyntaxException {
			for(JsonElement e : json.getAsJsonArray()) {
				states.add(BlockArgumentParser.block(Registry.BLOCK, e.getAsString(), false).blockState());
			}
		}
		
		public BlockState get(Random random) {
			return states.get(random.nextInt(states.size()));
		}
		
	}
	
	public static class WeightedBlockStateSupplier extends BlockStateSupplier {
		
		Map<BlockState, Float> states = new HashMap<>();
		
		public WeightedBlockStateSupplier(JsonElement json) throws CommandSyntaxException {
			for(Map.Entry<String, JsonElement> e : json.getAsJsonObject().entrySet()) {
				BlockState state = BlockArgumentParser.block(Registry.BLOCK, e.getKey(), false).blockState();
				float weight = e.getValue().getAsFloat();
				states.put(state, weight);
			}
		}
		
		public BlockState get(Random random) {
			return Support.getWeightedRandom(states, random);
		}
		
	}
	
	
}
