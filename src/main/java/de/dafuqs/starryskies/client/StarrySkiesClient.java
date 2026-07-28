package de.dafuqs.starryskies.client;

import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.client.sky.*;
import de.dafuqs.starryskies.configs.*;
import net.minecraft.resources.*;
import net.neoforged.api.distmarker.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.*;
import net.neoforged.fml.common.*;
import net.neoforged.fml.event.lifecycle.*;
import net.neoforged.neoforge.client.event.*;

@Mod(value = StarrySkies.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = StarrySkies.MOD_ID)
public class StarrySkiesClient {

	public static StarrySkyBox SKYBOX;

    public StarrySkiesClient(IEventBus modBus, ModContainer modContainer) {
        modBus.addListener(StarrySkiesClient::registerSkyBox);
    }

    public static final Identifier SKY_RENDERER_ID = StarrySkies.id("overworld");

    @SubscribeEvent
    public static void registerSkyBox(RegisterCustomEnvironmentEffectRendererEvent event) {
        event.registerSkyboxRenderer(SKY_RENDERER_ID, new StarrySkyBox());
    }

    @SubscribeEvent
    public static void extractLevelRenderStateEvent(ExtractLevelRenderStateEvent event) {
        if (StarrySkyConfig.CONFIG.rainbowSkybox.get()) {
            StarrySkyBoxTextures.INSTANCE.set("textures/skybox/rainbow_up.png", "textures/skybox/rainbow_down.png", "textures/skybox/rainbow_hori.png", "textures/skybox/rainbow_hori.png", "textures/skybox/rainbow_hori.png", "textures/skybox/rainbow_hori.png");
        } else {
            StarrySkyBoxTextures.INSTANCE.set("textures/skybox/light.png", "textures/skybox/darker.png", "textures/skybox/hori.png", "textures/skybox/hori.png", "textures/skybox/hori.png", "textures/skybox/hori.png");
        }
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {

    }

}
