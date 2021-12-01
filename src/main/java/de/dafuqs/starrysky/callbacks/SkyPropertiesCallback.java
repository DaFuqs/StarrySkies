package de.dafuqs.starrysky.callbacks;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.util.Identifier;

public interface SkyPropertiesCallback {
	
	Event<SkyPropertiesCallback> EVENT = EventFactory.createArrayBacked(SkyPropertiesCallback.class, (listeners) -> (properties) -> {
		for (SkyPropertiesCallback listener : listeners) {
			listener.handle(properties);
		}
	});

	/** Handle the properties. */
	void handle(Object2ObjectMap<Identifier, DimensionEffects> properties);
	
}