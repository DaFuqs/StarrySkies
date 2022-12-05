package de.dafuqs.starryskies;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import de.dafuqs.starryskies.data_loaders.UniqueBlockGroupsLoader;
import de.dafuqs.starryskies.data_loaders.WeightedBlockGroupsLoader;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class StarryResourceConditions {
	
	public static final Identifier UNIQUE_BLOCK_GROUP_EXISTS = StarrySkies.locate("unique_block_group_exist");
	public static final Identifier WEIGHTED_BLOCK_GROUP_EXISTS = StarrySkies.locate("weighted_block_group_exist");
	
	public static void register() {
		ResourceConditions.register(UNIQUE_BLOCK_GROUP_EXISTS, object -> uniqueBlockGroupExists(object));
		ResourceConditions.register(WEIGHTED_BLOCK_GROUP_EXISTS, object -> weightedBlockGroupExists(object));
	}
	
	private static boolean uniqueBlockGroupExists(JsonObject object) {
		JsonArray array = JsonHelper.getArray(object, "values");
		
		for (JsonElement element : array) {
			if (element.isJsonPrimitive()) {
				Identifier identifier = Identifier.tryParse(element.getAsString());
				return UniqueBlockGroupsLoader.existsGroup(identifier);
			} else {
				throw new JsonParseException("Invalid unique block group resource condition entry: " + element);
			}
		}
		
		return false;
	}
	
	private static boolean weightedBlockGroupExists(JsonObject object) {
		JsonArray array = JsonHelper.getArray(object, "values");
		
		for (JsonElement element : array) {
			if (element.isJsonPrimitive()) {
				Identifier identifier = Identifier.tryParse(element.getAsString());
				return WeightedBlockGroupsLoader.existsGroup(identifier);
			} else {
				throw new JsonParseException("Invalid weighted block group resource condition entry: " + element);
			}
		}
		
		return false;
	}
	
}