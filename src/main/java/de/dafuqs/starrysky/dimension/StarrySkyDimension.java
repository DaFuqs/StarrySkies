package de.dafuqs.starrysky.dimension;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.dimension.biome.StarrySkyBiomeProvider;
import net.kyrptonaught.customportalapi.CustomPortalApiRegistry;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class StarrySkyDimension {
    public static final RegistryKey<World> STARRY_SKY_WORLD_KEY = RegistryKey.of(Registry.DIMENSION, StarrySkyCommon.MOD_DIMENSION_ID);

    public static void setupDimension(){
        Registry.register(Registry.CHUNK_GENERATOR, new Identifier(StarrySkyCommon.MOD_ID, "starry_sky_chunk_generator"), StarrySkyChunkGenerator.CODEC);
        StarrySkyBiomeProvider.registerBiomeProvider();
    }

    public static void setupPortal() {
        Block portalFrameBlock = Registry.BLOCK.get(new Identifier(StarrySkyCommon.STARRY_SKY_CONFIG.portalFrameBlock.toLowerCase()));
        CustomPortalApiRegistry.addPortal(portalFrameBlock, StarrySkyCommon.MOD_DIMENSION_ID, StarrySkyCommon.STARRY_SKY_CONFIG.portalColor);
    }

}
