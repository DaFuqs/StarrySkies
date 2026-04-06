package de.dafuqs.starryskies.registries;

import de.dafuqs.starryskies.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class StarryDimensionKeys {
	
	public static final BlockPos STARRY_END_SPAWN_BLOCK_POS = new BlockPos(10, 64, 0);
	public static final BlockPos STARRY_OVERWORLD_SPAWN_BLOCK_POS = new BlockPos(16, 85, 16);
	
	
	public static final ResourceLocation STARRY_SKIES_DIMENSION_ID = StarrySkies.id("overworld");
	public static final ResourceLocation STARRY_SKIES_NETHER_DIMENSION_ID = StarrySkies.id("nether");
	public static final ResourceLocation STARRY_SKIES_END_DIMENSION_ID = StarrySkies.id("end");

	public static final ResourceKey<Level> OVERWORLD_KEY = getWorld(STARRY_SKIES_DIMENSION_ID);
	public static final ResourceKey<Level> NETHER_KEY = getWorld(STARRY_SKIES_NETHER_DIMENSION_ID);
	public static final ResourceKey<Level> END_KEY = getWorld(STARRY_SKIES_END_DIMENSION_ID);

	private static ResourceKey<Level> getWorld(ResourceLocation id) {
		return ResourceKey.create(Registries.DIMENSION, id);
	}

}
