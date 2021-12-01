package de.dafuqs.starrysky.dimension;

import de.dafuqs.starrysky.StarrySkyCommon;
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
    }

    public static void setupPortals() {
        StarrySkyCommon.log(INFO, "Setting up portals...");
        
        Identifier portalFrameBlockIdentifier = new Identifier(StarrySkyCommon.STARRY_SKY_CONFIG.starrySkyPortalFrameBlock.toLowerCase());
        Block portalFrameBlock = Registry.BLOCK.get(portalFrameBlockIdentifier);
        
        PortalLink portalLink = new PortalLink(portalFrameBlockIdentifier, STARRY_SKY_DIMENSION_ID, 11983869); // light, greyish blue
        CustomPortalApiRegistry.addPortal(portalFrameBlock, portalLink);
    }

}
