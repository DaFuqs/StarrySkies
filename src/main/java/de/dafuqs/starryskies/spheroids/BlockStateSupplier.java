package de.dafuqs.starryskies.spheroids;

import com.google.gson.*;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.Support;
import de.dafuqs.starryskies.data_loaders.UniqueBlockGroupsLoader;
import de.dafuqs.starryskies.data_loaders.WeightedBlockGroupsLoader;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BlockStateSupplier {
	
	@Contract("_ -> new")
	public static @NotNull BlockStateSupplier of(JsonElement jsonElement) throws CommandSyntaxException {
		if (jsonElement instanceof JsonPrimitive) {
			if (jsonElement.getAsString().startsWith("$")) {
				return new WeightedBlockStateGroupSupplier(jsonElement);
			} else if (jsonElement.getAsString().startsWith("%")) {
				return new UniqueBlockStateGroupSupplier(jsonElement);
			} else {
				return new SingleBlockStateSupplier(jsonElement);
			}
		} else if (jsonElement instanceof JsonArray) {
			return new BlockStateListSupplier(jsonElement);
		} else if (jsonElement instanceof JsonObject) {
			return new WeightedBlockStateSupplier(jsonElement);
		}
		throw new JsonParseException("Could not parse json values as BlockStateSupplier: " + jsonElement);
	}
	
	public abstract BlockState get(Random random);
	
	public static class SingleBlockStateSupplier extends BlockStateSupplier {
		
		BlockState state;
		
		public SingleBlockStateSupplier(@NotNull JsonElement json) throws CommandSyntaxException {
			state = StarrySkies.getStateFromString(json.getAsString());
		}
		
		public BlockState get(Random random) {
			return state;
		}
		
	}
	
	public static class BlockStateListSupplier extends BlockStateSupplier {
		
		List<BlockState> states = new ArrayList<>();
		
		public BlockStateListSupplier(@NotNull JsonElement json) throws CommandSyntaxException {
			for (JsonElement e : json.getAsJsonArray()) {
				states.add(StarrySkies.getStateFromString(e.getAsString()));
			}
		}
		
		public BlockState get(@NotNull Random random) {
			return states.get(random.nextInt(states.size()));
		}
		
	}
	
	public static class WeightedBlockStateSupplier extends BlockStateSupplier {
		
		Map<BlockState, Float> states = new HashMap<>();
		
		public WeightedBlockStateSupplier(@NotNull JsonElement json) throws CommandSyntaxException {
			for (Map.Entry<String, JsonElement> e : json.getAsJsonObject().entrySet()) {
				BlockState state = StarrySkies.getStateFromString(e.getKey());
				float weight = e.getValue().getAsFloat();
				states.put(state, weight);
			}
		}
		
		public BlockState get(Random random) {
			return Support.getWeightedRandom(states, random);
		}
		
	}
	
	public static class WeightedBlockStateGroupSupplier extends BlockStateSupplier {
		
		Identifier identifier;
		
		public WeightedBlockStateGroupSupplier(@NotNull JsonElement json) {
			String s = json.getAsString();
			s = s.substring(1);
			identifier = Identifier.tryParse(s);
		}
		
		public BlockState get(Random random) {
			return WeightedBlockGroupsLoader.getRandomStateInGroup(identifier, random);
		}
		
	}
	
	public static class UniqueBlockStateGroupSupplier extends BlockStateSupplier {
		
		Identifier identifier;
		
		public UniqueBlockStateGroupSupplier(@NotNull JsonElement json) {
			String s = json.getAsString();
			s = s.substring(1);
			identifier = Identifier.tryParse(s);
		}
		
		public BlockState get(Random random) {
			return UniqueBlockGroupsLoader.getFirstStateInGroup(identifier);
		}
		
	}
	
	
}
