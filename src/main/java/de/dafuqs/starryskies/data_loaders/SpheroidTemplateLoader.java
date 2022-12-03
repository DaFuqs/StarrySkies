package de.dafuqs.starryskies.data_loaders;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.Support;
import de.dafuqs.starryskies.dimension.SpheroidDimensionType;
import de.dafuqs.starryskies.spheroids.StarryRegistries;
import de.dafuqs.starryskies.spheroids.types.Spheroid;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.Level;

import java.util.LinkedHashMap;
import java.util.Map;

;

public class SpheroidTemplateLoader extends JsonDataLoader implements IdentifiableResourceReloadListener {
	
	public static final String ID = "starry_skies/spheres";
	public static final SpheroidTemplateLoader INSTANCE = new SpheroidTemplateLoader();
	
	private static LinkedHashMap<Identifier, LinkedHashMap<Spheroid.Template, Float>> WEIGHTED_SPHEROID_TYPES;
	
	protected SpheroidTemplateLoader() {
		super(new Gson(), ID);
		
		// initialize list with empty LinkedHashMaps
		WEIGHTED_SPHEROID_TYPES = new LinkedHashMap<>();
		for (Identifier spheroidDistributionType : SpheroidDistributionLoader.getAll()) {
			WEIGHTED_SPHEROID_TYPES.put(spheroidDistributionType, new LinkedHashMap<>());
		}
	}
	
	@Override
	protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
		prepared.forEach((identifier, jsonElement) -> {
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			try {
				Spheroid.Template template;
				Identifier spheroidType = Identifier.tryParse(JsonHelper.getString(jsonObject, "type"));
				
				Class<? extends Spheroid.Template> templateClass = StarryRegistries.SPHEROID_TYPE.get(spheroidType);
				template = templateClass.getConstructor(jsonObject.getClass()).newInstance(jsonObject);
				
				Identifier generationGroup = null;
				float generationWeight = 0;
				if(JsonHelper.hasString(jsonObject, "generation_group")) {
					generationGroup = Identifier.tryParse(JsonHelper.getString(jsonObject, "generation_group"));
					generationWeight = JsonHelper.getFloat(jsonObject, "generation_weight", 0);
				}
				
				registerSpheroidType(generationGroup, generationWeight, template);
			} catch (Exception e) {
				StarrySkies.log(Level.ERROR, "Error reading sphere json definition " + identifier + ":" + e.getMessage());
				e.printStackTrace();
			}
		});
	}
	
	public void registerSpheroidType(Identifier distributionID, Float weight, Spheroid.Template spheroidType) {
		Registry.register(StarryRegistries.SPHEROID_TEMPLATE, spheroidType.getID(), spheroidType);
		if(distributionID != null && weight > 0) {
			WEIGHTED_SPHEROID_TYPES.get(distributionID).put(spheroidType, weight);
		}
	}
	
	@Override
	public Identifier getFabricId() {
		return StarrySkies.locate(ID);
	}
	
	public static Spheroid.Template getWeightedRandomSpheroid(SpheroidDimensionType dimensionType, ChunkRandom systemRandom) {
		Identifier distributionType = SpheroidDistributionLoader.getWeightedRandomDistributionType(dimensionType, systemRandom);
		return Support.getWeightedRandom(WEIGHTED_SPHEROID_TYPES.get(distributionType), systemRandom);
	}
	
}