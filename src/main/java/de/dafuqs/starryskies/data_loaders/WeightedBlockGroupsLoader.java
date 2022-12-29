package de.dafuqs.starryskies.data_loaders;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.Support;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.profiler.Profiler;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class WeightedBlockGroupsLoader extends JsonDataLoader implements IdentifiableResourceReloadListener {
	
	public static final String ID = "starry_skies/weighted_block_groups";
	public static final WeightedBlockGroupsLoader INSTANCE = new WeightedBlockGroupsLoader();
	
	private static final Map<Identifier, Map<BlockState, Float>> BLOCK_GROUPS = new HashMap<>();
	
	protected WeightedBlockGroupsLoader() {
		super(new Gson(), ID);
	}
	
	@Override
	protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
		prepared.forEach((identifier, jsonElement) -> {
			
			Map<BlockState, Float> map;
			boolean newMap = false;
			if (BLOCK_GROUPS.containsKey(identifier)) {
				map = BLOCK_GROUPS.get(identifier);
			} else {
				map = new LinkedHashMap<>();
				newMap = true;
			}
			
			for (Map.Entry<String, JsonElement> e : jsonElement.getAsJsonObject().entrySet()) {
				try {
					BlockState state = StarrySkies.getStateFromString(e.getKey());
					float weight = e.getValue().getAsFloat();
					map.put(state, weight);
				} catch (CommandSyntaxException ex) {
					if (StarrySkies.CONFIG.packCreatorMode) {
						StarrySkies.log(Level.WARN, "Block group " + identifier + " tries to load a non-existing block: " + e.getKey() + ". Will be ignored.");
					}
				}
			}
			
			if (newMap) {
				BLOCK_GROUPS.put(identifier, map);
			}
			
		});
	}
	
	@Override
	public Identifier getFabricId() {
		return StarrySkies.locate(ID);
	}
	
	public static boolean existsGroup(Identifier identifier) {
		return BLOCK_GROUPS.containsKey(identifier) && BLOCK_GROUPS.get(identifier).size() > 0;
	}
	
	public static BlockState getRandomStateInGroup(Identifier identifier, Random random) {
		Map<BlockState, Float> group = BLOCK_GROUPS.get(identifier);
		if (group == null || group.size() == 0) {
			StarrySkies.log(Level.WARN, "Referencing empty/non-existing WeightedBlockGroup: " + identifier + ". Using AIR instead.");
			return Blocks.AIR.getDefaultState();
		}
		return Support.getWeightedRandom(group, random);
	}
	
}