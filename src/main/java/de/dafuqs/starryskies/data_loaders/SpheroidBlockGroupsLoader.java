package de.dafuqs.starryskies.data_loaders;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import de.dafuqs.starryskies.StarrySkies;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.Map;

public class SpheroidBlockGroupsLoader extends JsonDataLoader implements IdentifiableResourceReloadListener {
	
	public static final String ID = "starry_skies/block_group";
	public static final SpheroidBlockGroupsLoader INSTANCE = new SpheroidBlockGroupsLoader();
	
	protected SpheroidBlockGroupsLoader() {
		super(new Gson(), ID);
	}
	
	@Override
	protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
		prepared.forEach((identifier, jsonElement) -> {
			/*
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			FluidPredicate fluidPredicate = FluidPredicate.fromJson(jsonObject.get("fluid"));
			float chance = JsonHelper.getFloat(jsonObject, "chance");
			JsonArray entityArray = JsonHelper.getArray(jsonObject, "entities");
			
			AtomicInteger weightSum = new AtomicInteger();
			DataPool.Builder<EntityType> entityTypesList = DataPool.builder();
			entityArray.forEach(entryElement -> {
				JsonObject entryObject = entryElement.getAsJsonObject();
				
				EntityType entityType = Registry.ENTITY_TYPE.get(new Identifier(JsonHelper.getString(entryObject, "id")));
				int weight = 1;
				if (JsonHelper.hasNumber(jsonObject, "weight")) {
					weight = JsonHelper.getInt(entryObject, "weight");
				}
				weightSum.addAndGet(weight);
				entityTypesList.add(entityType, weight);
			});
			
			// dynamically generate ore spheroids
			// this way we got only 1 "copper" spheroids even though lots of mods add a copper ore block
			// Overworld
			LinkedHashMap<SpheroidType, Float> dynamicOreSpheroids = DynamicOreSpheroids.getOreSpheroidTypesBasedOnDict(dynamicOresByDimensionType.get(SpheroidDimensionType.OVERWORLD));
			WEIGHTED_OVERWORLD_TYPES.get(SpheroidDistributionType.ORE).putAll(dynamicOreSpheroids);
			
			// Nether
			dynamicOreSpheroids = DynamicOreSpheroids.getOreSpheroidTypesBasedOnDict(dynamicOresByDimensionType.get(SpheroidDimensionType.NETHER));
			WEIGHTED_NETHER_TYPES.get(SpheroidDistributionType.ORE).putAll(dynamicOreSpheroids);
			
			// End
			dynamicOreSpheroids = DynamicOreSpheroids.getOreSpheroidTypesBasedOnDict(dynamicOresByDimensionType.get(SpheroidDimensionType.END));
			WEIGHTED_END_TYPES.get(SpheroidDistributionType.ORE).putAll(dynamicOreSpheroids);*/
		});
	}
	
	@Override
	public Identifier getFabricId() {
		return StarrySkies.locate(ID);
	}
	
}