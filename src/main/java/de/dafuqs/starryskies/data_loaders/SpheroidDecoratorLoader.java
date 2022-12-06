package de.dafuqs.starryskies.data_loaders;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.spheroids.SpheroidDecorator;
import de.dafuqs.starryskies.spheroids.StarryRegistries;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.profiler.Profiler;
import org.apache.logging.log4j.Level;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

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
					StarrySkies.log(Level.ERROR, "Error reading sphere json definition " + identifier + ": Spheroid Type " + decoratorTypeID + " is not known.");
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