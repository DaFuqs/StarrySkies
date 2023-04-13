package de.dafuqs.starryskies.data_loaders;

import com.google.gson.*;
import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.spheroids.*;
import net.fabricmc.fabric.api.resource.*;
import net.minecraft.resource.*;
import net.minecraft.util.*;
import net.minecraft.util.profiler.*;
import org.apache.logging.log4j.*;

import java.lang.reflect.*;
import java.util.*;

public class SpheroidDecoratorLoader extends JsonDataLoader implements IdentifiableResourceReloadListener {
	
	public static final String ID = "starry_skies/sphere_decorators";
	public static final SpheroidDecoratorLoader INSTANCE = new SpheroidDecoratorLoader();
	
	private static final LinkedHashMap<Identifier, SpheroidDecorator> DECORATORS = new LinkedHashMap<>();
	
	protected SpheroidDecoratorLoader() {
		super(new Gson(), ID);
	}
	
	@Override
	protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
		prepared.forEach((identifier, jsonElement) -> {
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			SpheroidDecorator decorator;
			Identifier decoratorTypeID;
			try {
				decoratorTypeID = Identifier.tryParse(JsonHelper.getString(jsonObject, "type"));
				
				try {
					Class<? extends SpheroidDecorator> templateClass = StarryRegistries.SPHEROID_DECORATOR_TYPE.get(decoratorTypeID);
					JsonObject typeData = JsonHelper.getObject(jsonObject, "type_data", null);
					decorator = templateClass.getConstructor(JsonObject.class).newInstance(typeData);
				} catch (NullPointerException e) {
					if (StarrySkies.CONFIG.packCreatorMode) {
						StarrySkies.log(Level.WARN, "Error reading sphere json definition " + identifier + ": Spheroid Type " + decoratorTypeID + " is not known.");
					}
					return;
				}
				
				DECORATORS.put(identifier, decorator);
			} catch (InvocationTargetException e) {
				StarrySkies.log(Level.ERROR, "Error reading decorator json definition " + identifier + ": " + e.getTargetException());
			} catch (Exception e) {
				StarrySkies.log(Level.ERROR, "Error reading decorator json definition " + identifier + ": " + e);
				e.printStackTrace();
			}
		});
	}
	
	@Override
	public Identifier getFabricId() {
		return StarrySkies.locate(ID);
	}
	
	public static SpheroidDecorator getDecorator(Identifier identifier) {
		return DECORATORS.getOrDefault(identifier, null);
	}
	
}