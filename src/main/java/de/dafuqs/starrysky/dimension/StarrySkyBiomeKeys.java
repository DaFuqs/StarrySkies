package de.dafuqs.starrysky.dimension;

import de.dafuqs.starrysky.StarrySkyCommon;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class StarrySkyBiomeKeys {
	
	public static final RegistryKey<Biome> OVERWORLD = register("starry_sky_biome");
	public static final RegistryKey<Biome> NETHER = register("starry_sky_nether_biome");
	public static final RegistryKey<Biome> END = register("starry_sky_end_biome");
	
	private static RegistryKey<Biome> register(String name) {
		return RegistryKey.of(Registry.BIOME_KEY, new Identifier(StarrySkyCommon.MOD_ID, name));
	}
	
}
