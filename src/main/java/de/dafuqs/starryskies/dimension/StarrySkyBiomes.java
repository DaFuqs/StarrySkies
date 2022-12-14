package de.dafuqs.starryskies.dimension;

import de.dafuqs.starryskies.StarrySkies;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class StarrySkyBiomes {
	
	public static final RegistryKey<Biome> OVERWORLD_KEY = getBiomeKey("overworld");
	public static final RegistryKey<Biome> NETHER_KEY = getBiomeKey("nether");
	public static final RegistryKey<Biome> END_KEY = getBiomeKey("end");
	
	private static RegistryKey<Biome> getBiomeKey(String name) {
		return RegistryKey.of(Registry.BIOME_KEY, new Identifier(StarrySkies.MOD_ID, name));
	}
	
	public static void initialize() {
	
	}
	
}
