package de.dafuqs.starrysky.dimension;

import de.dafuqs.starrysky.StarrySkyCommon;
import net.kyrptonaught.customportalapi.CustomPortalApiRegistry;
import net.kyrptonaught.customportalapi.util.PortalLink;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;

import static org.apache.logging.log4j.Level.INFO;

public class StarrySkyDimension {

    public static final Identifier STARRY_SKY_DIMENSION_ID = new Identifier(StarrySkyCommon.MOD_ID, "starry_sky");
    public static final Identifier STARRY_SKY_NETHER_DIMENSION_ID = new Identifier(StarrySkyCommon.MOD_ID, "starry_sky_nether");
    public static final Identifier STARRY_SKY_END_DIMENSION_ID = new Identifier(StarrySkyCommon.MOD_ID, "starry_sky_end");
    public static final RegistryKey<World> OVERWORLD_KEY = registerWorld(STARRY_SKY_DIMENSION_ID);
    public static final RegistryKey<World> NETHER_KEY = registerWorld(STARRY_SKY_NETHER_DIMENSION_ID);
    public static final RegistryKey<World> END_KEY = registerWorld(STARRY_SKY_END_DIMENSION_ID);
    
    public static final Identifier OVERWORLD_DIMENSION_ID = new Identifier(StarrySkyCommon.MOD_ID, "starry_sky");
    public static final Identifier NETHER_DIMENSION_ID = new Identifier(StarrySkyCommon.MOD_ID, "starry_sky_nether");
    public static final Identifier END_DIMENSION_ID = new Identifier(StarrySkyCommon.MOD_ID, "starry_sky_end");
    public static final RegistryKey<DimensionOptions> OVERWORLD_DIMENSION_KEY = registerDimension(OVERWORLD_DIMENSION_ID);
    public static final RegistryKey<DimensionOptions> NETHER_DIMENSION_KEY = registerDimension(NETHER_DIMENSION_ID);
    public static final RegistryKey<DimensionOptions> END_DIMENSION_KEY = registerDimension(END_DIMENSION_ID);
    
    public static final Identifier OVERWORLD_DIMENSION_TYPE_ID = new Identifier(StarrySkyCommon.MOD_ID, "starry_sky_type");
    public static final Identifier NETHER_DIMENSION_TYPE_ID = new Identifier(StarrySkyCommon.MOD_ID, "starry_sky_nether_type");
    public static final Identifier END_DIMENSION_TYPE_ID = new Identifier(StarrySkyCommon.MOD_ID, "starry_sky_end_type");
    public static final RegistryKey<DimensionType> OVERWORLD_DIMENSION_TYPE_KEY = registerDimensionType(OVERWORLD_DIMENSION_TYPE_ID);
    public static final RegistryKey<DimensionType> NETHER_DIMENSION_TYPE_KEY = registerDimensionType(NETHER_DIMENSION_TYPE_ID);
    public static final RegistryKey<DimensionType> END_DIMENSION_TYPE_KEY = registerDimensionType(END_DIMENSION_TYPE_ID);
    
    private static RegistryKey<World> registerWorld(Identifier id) {
        return RegistryKey.of(Registry.WORLD_KEY, id);
    }
    
    private static RegistryKey<DimensionOptions> registerDimension(Identifier id) {
        return RegistryKey.of(Registry.DIMENSION_KEY, id);
    }
    
    private static RegistryKey<DimensionType> registerDimensionType(Identifier id) {
        return RegistryKey.of(Registry.DIMENSION_TYPE_KEY, id);
    }
    
    public static void setupDimension() {
        //Vanilla does that via json now
        //StarrySkyCommon.log(INFO, "Registering chunk generator...");
        //Registry.register(Registry.CHUNK_GENERATOR, new Identifier(StarrySkyCommon.MOD_ID, "starry_sky_chunk_generator"), StarrySkyChunkGenerator.CODEC);
        
        setupPortals();
    }

    public static void setupPortals() {
        StarrySkyCommon.log(INFO, "Setting up portals...");
        
        Identifier portalFrameBlockIdentifier = new Identifier(StarrySkyCommon.STARRY_SKY_CONFIG.starrySkyPortalFrameBlock.toLowerCase());
        Block portalFrameBlock = Registry.BLOCK.get(portalFrameBlockIdentifier);
        
        PortalLink portalLink = new PortalLink(portalFrameBlockIdentifier, STARRY_SKY_DIMENSION_ID, 11983869); // light, greyish blue
        CustomPortalApiRegistry.addPortal(portalFrameBlock, portalLink);
    }

}
