package de.dafuqs.starrysky.dimension;

import de.dafuqs.starrysky.StarrySkyCommon;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;

public enum SpheroidDimensionType {
	OVERWORLD,
	NETHER,
	END;
	
	public int getFloorHeight() {
		switch (this) {
			case OVERWORLD -> {
				return StarrySkyCommon.STARRY_SKY_CONFIG.floorHeightOverworld;
			}
			case NETHER -> {
				return StarrySkyCommon.STARRY_SKY_CONFIG.floorHeightNether;
			}
			default -> {
				return StarrySkyCommon.STARRY_SKY_CONFIG.floorHeightEnd;
			}
		}
	}
	
	public BlockState getFloorBlockState() {
		switch (this) {
			case OVERWORLD -> {
				return Registry.BLOCK.get(new Identifier(StarrySkyCommon.STARRY_SKY_CONFIG.floorBlockOverworld.toLowerCase())).getDefaultState();
			}
			case NETHER -> {
				return Registry.BLOCK.get(new Identifier(StarrySkyCommon.STARRY_SKY_CONFIG.floorBlockNether.toLowerCase())).getDefaultState();
			}
			default -> {
				return Registry.BLOCK.get(new Identifier(StarrySkyCommon.STARRY_SKY_CONFIG.floorBlockEnd.toLowerCase())).getDefaultState();
			}
		}
	}
	
	public BlockState getBottomBlockState() {
		switch (this) {
			case OVERWORLD -> {
				return Registry.BLOCK.get(new Identifier(StarrySkyCommon.STARRY_SKY_CONFIG.bottomBlockOverworld.toLowerCase())).getDefaultState();
			}
			case NETHER -> {
				return Registry.BLOCK.get(new Identifier(StarrySkyCommon.STARRY_SKY_CONFIG.bottomBlockNether.toLowerCase())).getDefaultState();
			}
			default -> {
				return Registry.BLOCK.get(new Identifier(StarrySkyCommon.STARRY_SKY_CONFIG.bottomBlockEnd.toLowerCase())).getDefaultState();
			}
		}
	}
	
	public RegistryEntry<Biome> getBiome() {
		Registry<Biome> biomeRegistry = StarrySkyCommon.minecraftServer.getRegistryManager().get(Registry.BIOME_KEY);
		switch (this) {
			case OVERWORLD -> {
				return biomeRegistry.getOrCreateEntry(StarrySkyBiomeKeys.OVERWORLD);
			}
			case NETHER -> {
				return biomeRegistry.getOrCreateEntry(StarrySkyBiomeKeys.NETHER);
			}
			default -> {
				return biomeRegistry.getOrCreateEntry(StarrySkyBiomeKeys.END);
			}
		}
	}
}
