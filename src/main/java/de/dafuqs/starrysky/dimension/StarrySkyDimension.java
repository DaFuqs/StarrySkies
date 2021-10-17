package de.dafuqs.starrysky.dimension;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.dimension.biome.StarrySkyBiomeProvider;
import net.kyrptonaught.customportalapi.CustomPortalApiRegistry;
import net.kyrptonaught.customportalapi.util.PortalLink;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import static org.apache.logging.log4j.Level.INFO;

public class StarrySkyDimension {

    public static final Identifier STARRY_SKY_DIMENSION_ID = new Identifier(StarrySkyCommon.MOD_ID, "starry_sky");
    public static final RegistryKey<World> STARRY_SKY_WORLD_KEY = RegistryKey.of(Registry.WORLD_KEY, STARRY_SKY_DIMENSION_ID);

    public static final Identifier STARRY_SKY_NETHER_DIMENSION_ID = new Identifier(StarrySkyCommon.MOD_ID, "starry_sky_nether");
    public static final RegistryKey<World> STARRY_SKY_NETHER_WORLD_KEY = RegistryKey.of(Registry.WORLD_KEY, STARRY_SKY_NETHER_DIMENSION_ID);

    public static final Identifier STARRY_SKY_END_DIMENSION_ID = new Identifier(StarrySkyCommon.MOD_ID, "starry_sky_end");
    public static final RegistryKey<World> STARRY_SKY_END_WORLD_KEY = RegistryKey.of(Registry.WORLD_KEY, STARRY_SKY_END_DIMENSION_ID);

    public static void setupDimension(){
        StarrySkyCommon.log(INFO, "Registering chunk generator...");
        Registry.register(Registry.CHUNK_GENERATOR, new Identifier(StarrySkyCommon.MOD_ID, "starry_sky_chunk_generator"), StarrySkyChunkGenerator.CODEC);
        StarrySkyBiomeProvider.registerBiomeProvider();
    }

    public static void setupOverworldPortal() {
        if(StarrySkyCommon.STARRY_SKY_CONFIG.portalToStarrySky) {
            StarrySkyCommon.log(INFO, "Registering portal between the Overworld and Starry Sky...");
            Block portalFrameBlock = Registry.BLOCK.get(new Identifier(StarrySkyCommon.STARRY_SKY_CONFIG.starrySkyPortalFrameBlock.toLowerCase()));
            PortalLink starryPortalLink = new PortalLink(Registry.BLOCK.getId(portalFrameBlock), STARRY_SKY_DIMENSION_ID, 11983869); // light, greyish blue
            CustomPortalApiRegistry.addPortal(portalFrameBlock, starryPortalLink);
        } else {
            StarrySkyCommon.log(INFO, "Portal between Overworld and Starry Sky is disabled in the config. Will not be registered.");
        }
    }

}
