package de.dafuqs.starrysky;

import de.dafuqs.starrysky.callbacks.SkyPropertiesCallback;
import de.dafuqs.starrysky.dimension.StarrySkyDimension;
import de.dafuqs.starrysky.dimension.sky.StarrySkyProperties;
import net.fabricmc.api.ClientModInitializer;

public class StarrySkyClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Sky Properties callback
        SkyPropertiesCallback.EVENT.register((properties) -> properties.put(StarrySkyDimension.STARRY_SKY_DIMENSION_ID, new StarrySkyProperties()));
    }

}
