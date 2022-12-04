package de.dafuqs.starryskies.data_loaders;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.dafuqs.starryskies.StarrySkies;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.command.argument.BlockArgumentParser;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UniqueBlockGroupsLoader extends JsonDataLoader implements IdentifiableResourceReloadListener {
	
	public static final String ID = "starry_skies/unique_block_groups";
	public static final UniqueBlockGroupsLoader INSTANCE = new UniqueBlockGroupsLoader();
	
	private static final Map<Identifier, List<BlockState>> BLOCK_GROUPS = new HashMap<>();
	
	protected UniqueBlockGroupsLoader() {
		super(new Gson(), ID);
	}
	
	@Override
	protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
		prepared.forEach((identifier, jsonElement) -> {
			
			List<BlockState> list;
			boolean newMap = false;
			if(BLOCK_GROUPS.containsKey(identifier)) {
				list = BLOCK_GROUPS.get(identifier);
			} else {
				list = new ArrayList<>();
				newMap = true;
			}
			
			for(JsonElement e : jsonElement.getAsJsonArray()) {
				try {
					BlockState state = BlockArgumentParser.block(Registry.BLOCK, e.getAsString(), false).blockState();
					list.add(state);
				} catch (CommandSyntaxException ex) {
					StarrySkies.log(Level.WARN, "Block group " + identifier + " tries to load a non-existing block: " + e + ". Will be ignored.");
				}
			}
			
			if(newMap) {
				BLOCK_GROUPS.put(identifier, list);
			}
		
		});
	}
	
	@Override
	public Identifier getFabricId() {
		return StarrySkies.locate(ID);
	}
	
	public static boolean existsGroup(Identifier identifier) {
		return BLOCK_GROUPS.containsKey(identifier);
	}
	
	public static BlockState getFirstStateInGroup(Identifier identifier) {
		List<BlockState> group = BLOCK_GROUPS.get(identifier);
		if(group == null || group.size() == 0) {
			StarrySkies.log(Level.WARN, "Referencing empty/non-existing UniqueBlockGroup: " + identifier + ". Using AIR instead.");
			return Blocks.AIR.getDefaultState();
		}
		return group.get(0);
	}
	
}