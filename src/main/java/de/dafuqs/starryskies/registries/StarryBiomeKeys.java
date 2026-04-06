package de.dafuqs.starryskies.registries;

import de.dafuqs.starryskies.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public class StarryBiomeKeys {

	public static final ResourceKey<Biome> OVERWORLD_KEY = getBiomeKey("overworld");
	public static final ResourceKey<Biome> NETHER_KEY = getBiomeKey("nether");
	public static final ResourceKey<Biome> END_KEY = getBiomeKey("end");

	private static ResourceKey<Biome> getBiomeKey(String name) {
		return ResourceKey.create(Registries.BIOME, StarrySkies.id(name));
	}

}
