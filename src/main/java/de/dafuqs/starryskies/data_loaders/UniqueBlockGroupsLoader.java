package de.dafuqs.starryskies.data_loaders;

import com.google.gson.*;
import com.mojang.brigadier.*;
import com.mojang.brigadier.exceptions.*;
import de.dafuqs.starryskies.*;
import net.fabricmc.fabric.api.resource.*;
import net.minecraft.block.*;
import net.minecraft.command.argument.*;
import net.minecraft.resource.*;
import net.minecraft.util.*;
import net.minecraft.util.profiler.*;
import org.apache.logging.log4j.*;

import java.util.*;

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
			if (BLOCK_GROUPS.containsKey(identifier)) {
				list = BLOCK_GROUPS.get(identifier);
			} else {
				list = new ArrayList<>();
				newMap = true;
			}
			
			for (JsonElement e : jsonElement.getAsJsonArray()) {
				try {
					BlockState state = new BlockArgumentParser(new StringReader(e.getAsString()), true).parse(false).getBlockState();
					list.add(state);
				} catch (CommandSyntaxException ex) {
					if (StarrySkies.CONFIG.packCreatorMode) {
						StarrySkies.log(Level.WARN, "Block group " + identifier + " tries to load a non-existing block: " + e + ". Will be ignored.");
					}
				}
			}
			
			if (newMap) {
				BLOCK_GROUPS.put(identifier, list);
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
	
	public static BlockState getFirstStateInGroup(Identifier identifier) {
		List<BlockState> group = BLOCK_GROUPS.get(identifier);
		if (group == null || group.size() == 0) {
			StarrySkies.log(Level.WARN, "Referencing empty/non-existing UniqueBlockGroup: " + identifier + ". Using AIR instead.");
			return Blocks.AIR.getDefaultState();
		}
		return group.get(0);
	}
	
}