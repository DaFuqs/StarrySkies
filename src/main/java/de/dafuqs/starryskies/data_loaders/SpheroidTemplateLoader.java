package de.dafuqs.starryskies.data_loaders;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.Support;
import de.dafuqs.starryskies.dimension.SpheroidDimensionType;
import de.dafuqs.starryskies.spheroids.StarryRegistries;
import de.dafuqs.starryskies.spheroids.spheroids.Spheroid;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.profiler.Profiler;
import org.apache.logging.log4j.Level;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

public class SpheroidTemplateLoader extends JsonDataLoader implements IdentifiableResourceReloadListener {
	
	public static final String ID = "starry_skies/spheres";
	public static final SpheroidTemplateLoader INSTANCE = new SpheroidTemplateLoader();
	
	private static LinkedHashMap<Identifier, LinkedHashMap<Spheroid.Template, Float>> WEIGHTED_SPHEROID_TYPES;
	
	
	public static Identifier STARTER_OVERWORLD_ID = StarrySkies.locate("spawn/overworld");
	public static Identifier STARTER_NETHER_ID = StarrySkies.locate("spawn/nether");
	public static Identifier STARTER_END_ID = StarrySkies.locate("spawn/end");
	public static Identifier STARTER_END_DRAGON_ID = StarrySkies.locate("spawn/end_dragon");
	public static Spheroid.Template STARTER_OVERWORLD;
	public static Spheroid.Template STARTER_NETHER;
	public static Spheroid.Template STARTER_END;
	public static Spheroid.Template STARTER_END_DRAGON;
	
	protected SpheroidTemplateLoader() {
		super(new Gson(), ID);
	}
	
	@Override
	protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
		// initialize list with empty LinkedHashMaps
		WEIGHTED_SPHEROID_TYPES = new LinkedHashMap<>();
		for (Identifier spheroidDistributionType : SpheroidDistributionLoader.getAll()) {
			WEIGHTED_SPHEROID_TYPES.put(spheroidDistributionType, new LinkedHashMap<>());
		}
		
		prepared.forEach((identifier, jsonElement) -> {
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			Spheroid.Template template;
			Identifier spheroidType;
			try {
				spheroidType = Identifier.tryParse(JsonHelper.getString(jsonObject, "type"));
				
				try {
					Class<? extends Spheroid.Template> templateClass = StarryRegistries.SPHEROID_TYPE.get(spheroidType);
					template = templateClass.getConstructor(Identifier.class, jsonObject.getClass()).newInstance(identifier, jsonObject);
				} catch (NullPointerException e) {
					StarrySkies.log(Level.ERROR, "Error reading sphere json definition " + identifier + ": Spheroid Type " + spheroidType + " is not known.");
					return;
				}
				
				Identifier generationGroup = null;
				float generationWeight = 0;
				if(JsonHelper.hasString(jsonObject, "generation_group")) {
					generationGroup = Identifier.tryParse(JsonHelper.getString(jsonObject, "generation_group"));
					generationWeight = JsonHelper.getFloat(jsonObject, "generation_weight", 0);
				}
				
				if(identifier.equals(STARTER_OVERWORLD_ID)) {
					STARTER_OVERWORLD = template;
				} else if(identifier.equals(STARTER_NETHER_ID)) {
					STARTER_NETHER = template;
				} else if(identifier.equals(STARTER_END_DRAGON_ID)) {
					STARTER_END_DRAGON = template;
				} else if(identifier.equals(STARTER_END_ID)) {
					STARTER_END = template;
				} else if(generationGroup != null && generationWeight > 0) {
					LinkedHashMap<Spheroid.Template, Float> weightedMap = WEIGHTED_SPHEROID_TYPES.get(generationGroup);
					if(weightedMap == null) {
						StarrySkies.log(Level.WARN, "Spheroid " + identifier + "specifies non-existing generation_group "+ generationGroup + ". Will be ignored.");
					} else {
						weightedMap.put(template, generationWeight);
					}
				} else {
					StarrySkies.log(Level.WARN, "Spheroid " + identifier + " does not have generation_group and generation_weight set. Will be ignored.");
				}
			} catch (InvocationTargetException e) {
				StarrySkies.log(Level.ERROR, "Error reading sphere json definition " + identifier + ": " + e.getTargetException());
			} catch (Exception e) {
				StarrySkies.log(Level.ERROR, "Error reading sphere json definition " + identifier + ": " + e);
				e.printStackTrace();
			}
		});
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