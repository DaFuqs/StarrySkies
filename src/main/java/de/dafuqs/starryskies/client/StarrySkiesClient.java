package de.dafuqs.starryskies.client;

import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.client.sky.*;
import net.fabricmc.api.*;

public class StarrySkiesClient implements ClientModInitializer {
	
	public static final StarrySkyBox skyBox = StarrySkies.CONFIG.rainbowSkybox
			? new StarrySkyBox("textures/skybox/rainbow_up.png", "textures/skybox/rainbow_down.png", "textures/skybox/rainbow_hori.png", "textures/skybox/rainbow_hori.png", "textures/skybox/rainbow_hori.png", "textures/skybox/rainbow_hori.png")
			: new StarrySkyBox("textures/skybox/light.png", "textures/skybox/darker.png", "textures/skybox/hori.png", "textures/skybox/hori.png", "textures/skybox/hori.png", "textures/skybox/hori.png");
			
	@Override
	public void onInitializeClient() {
	
	}

}
