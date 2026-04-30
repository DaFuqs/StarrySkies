package de.dafuqs.starryskies.client;

import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.Support;
import de.dafuqs.starryskies.advancements.StarryAdvancementCriteria;
import de.dafuqs.starryskies.client.sky.StarrySkyBox;
import de.dafuqs.starryskies.configs.StarrySkyConfig;
import de.dafuqs.starryskies.worldgen.PlacedSphere;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.Optional;

@Mod(value = StarrySkies.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = StarrySkies.MOD_ID)
public class StarrySkiesClient {

	public static StarrySkyBox SKYBOX;

    public StarrySkiesClient(IEventBus modBus, ModContainer modContainer) {

    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        if (StarrySkyConfig.CONFIG.rainbowSkybox.get()) {
            StarrySkyBoxTextures.INSTANCE.set("textures/skybox/rainbow_up.png", "textures/skybox/rainbow_down.png", "textures/skybox/rainbow_hori.png", "textures/skybox/rainbow_hori.png", "textures/skybox/rainbow_hori.png", "textures/skybox/rainbow_hori.png");
        } else {
            StarrySkyBoxTextures.INSTANCE.set("textures/skybox/light.png", "textures/skybox/darker.png", "textures/skybox/hori.png", "textures/skybox/hori.png", "textures/skybox/hori.png", "textures/skybox/hori.png");
        }
    }

}
