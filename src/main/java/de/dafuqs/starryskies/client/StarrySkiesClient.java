package de.dafuqs.starryskies.client;

import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.client.sky.StarrySkyBox;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(value = StarrySkies.MOD_ID, dist = Dist.CLIENT)
public class StarrySkiesClient {

	public static StarrySkyBox SKYBOX;

    public StarrySkiesClient(IEventBus modBus, ModContainer modContainer) {
        if (StarrySkies.CONFIG.rainbowSkybox.get())
            StarrySkyBoxTextures.INSTANCE.set("textures/skybox/rainbow_up.png", "textures/skybox/rainbow_down.png", "textures/skybox/rainbow_hori.png", "textures/skybox/rainbow_hori.png", "textures/skybox/rainbow_hori.png", "textures/skybox/rainbow_hori.png");
        else
            StarrySkyBoxTextures.INSTANCE.set("textures/skybox/light.png", "textures/skybox/darker.png", "textures/skybox/hori.png", "textures/skybox/hori.png", "textures/skybox/hori.png", "textures/skybox/hori.png");

    }

}
