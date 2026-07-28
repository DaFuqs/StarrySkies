package de.dafuqs.starryskies.client;

import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.client.sky.*;
import de.dafuqs.starryskies.registries.StarryDimensionKeys;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelExtractionEvents;

public class StarrySkiesClient implements ClientModInitializer {
	public static StarrySkyBox SKYBOX;
    public static RenderStateDataKey<Boolean> USE_SKYBOX;

	@Override
	public void onInitializeClient() {
        LevelExtractionEvents.END_EXTRACTION.register(ctx -> ctx.levelState().setData(USE_SKYBOX, ctx.level() != null && StarryDimensionKeys.OVERWORLD_KEY.equals(ctx.level().dimension())));
        if (StarrySkies.CONFIG.rainbowSkybox)
            StarrySkyBoxTextures.INSTANCE.set("textures/skybox/rainbow_up.png", "textures/skybox/rainbow_down.png", "textures/skybox/rainbow_hori.png", "textures/skybox/rainbow_hori.png", "textures/skybox/rainbow_hori.png", "textures/skybox/rainbow_hori.png");
        else
            StarrySkyBoxTextures.INSTANCE.set("textures/skybox/light.png", "textures/skybox/darker.png", "textures/skybox/hori.png", "textures/skybox/hori.png", "textures/skybox/hori.png", "textures/skybox/hori.png");
	}

}
