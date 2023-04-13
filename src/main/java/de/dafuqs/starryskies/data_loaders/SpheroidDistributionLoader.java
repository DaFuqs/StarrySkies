package de.dafuqs.starryskies.data_loaders;

import com.google.gson.*;
import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.dimension.*;
import net.fabricmc.fabric.api.resource.*;
import net.minecraft.resource.*;
import net.minecraft.util.*;
import net.minecraft.util.profiler.*;
import net.minecraft.world.gen.random.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class SpheroidDistributionLoader extends JsonDataLoader implements IdentifiableResourceReloadListener {
	
	public static final String ID = "starry_skies/distributions";
	public static final SpheroidDistributionLoader INSTANCE = new SpheroidDistributionLoader();
	
	private static Map<SpheroidDimensionType, Map<Identifier, Float>> DISTRIBUTION_TYPES;
	
	protected SpheroidDistributionLoader() {
		super(new Gson(), ID);
		
		// initialize spheroid types list with empty LinkedHashMaps
		DISTRIBUTION_TYPES = new HashMap<>();
		for (SpheroidDimensionType dimensionType : SpheroidDimensionType.values()) {
			DISTRIBUTION_TYPES.put(dimensionType, new LinkedHashMap<>());
		}
	}
	
	@Override
	protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
		prepared.forEach((identifier, jsonElement) -> {
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			SpheroidDimensionType dimensionType = SpheroidDimensionType.of(JsonHelper.getString(jsonObject, "dimension"));
			float weight = JsonHelper.getFloat(jsonObject, "weight");
			
			register(dimensionType, identifier, weight);
		});
	}
	
	public void register(@NotNull SpheroidDimensionType dimensionType, Identifier identifier, float weight) {
		DISTRIBUTION_TYPES.get(dimensionType).put(identifier, weight);
	}
	
	@Override
	public Identifier getFabricId() {
		return StarrySkies.locate(ID);
	}
	
	public static Identifier getWeightedRandomDistributionType(SpheroidDimensionType dimensionType, ChunkRandom systemRandom) {
		Map<Identifier, Float> entry = DISTRIBUTION_TYPES.get(dimensionType);
		return Support.getWeightedRandom(entry, systemRandom);
	}
	
	public static List<Identifier> getAll() {
		List<Identifier> ids = new ArrayList<>();
		for (Map<Identifier, Float> entry : DISTRIBUTION_TYPES.values()) {
			ids.addAll(entry.keySet());
		}
		return ids;
	}
	
}