package de.dafuqs.starryskies.client;

import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.client.sky.*;
import net.fabricmc.api.*;

public class StarrySkiesClient implements ClientModInitializer {
	public static StarrySkyBox SKYBOX;

	@Override
	public void onInitializeClient() {
        if (StarrySkies.CONFIG.rainbowSkybox)
            StarrySkyBoxTextures.INSTANCE.set("textures/skybox/rainbow_up.png", "textures/skybox/rainbow_down.png", "textures/skybox/rainbow_hori.png", "textures/skybox/rainbow_hori.png", "textures/skybox/rainbow_hori.png", "textures/skybox/rainbow_hori.png");
        else
            StarrySkyBoxTextures.INSTANCE.set("textures/skybox/light.png", "textures/skybox/darker.png", "textures/skybox/hori.png", "textures/skybox/hori.png", "textures/skybox/hori.png", "textures/skybox/hori.png");
	}

}
