package de.dafuqs.starryskies.dimension;

import de.dafuqs.starryskies.StarrySkies;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import org.apache.logging.log4j.Level;

import java.util.Locale;

public enum SpheroidDimensionType {
	OVERWORLD,
	NETHER,
	END;
	
	public static SpheroidDimensionType of(String s) {
		try {
			return valueOf(s.toUpperCase(Locale.ROOT));
		} catch (Throwable t) {
			StarrySkies.log(Level.WARN, "Dimension type of `" + s + "` was requested, but it does not exist. Falling back to OVERWORLD");
			return SpheroidDimensionType.OVERWORLD;
		}
	}
	
	public int getFloorHeight() {
		switch (this) {
			case OVERWORLD -> {
				return StarrySkies.CONFIG.floorHeightOverworld;
			}
			case NETHER -> {
				return StarrySkies.CONFIG.floorHeightNether;
			}
			default -> {
				return StarrySkies.CONFIG.floorHeightEnd;
			}
		}
	}
	
	public BlockState getFloorBlockState() {
		switch (this) {
			case OVERWORLD -> {
				return Registry.BLOCK.get(new Identifier(StarrySkies.CONFIG.floorBlockOverworld.toLowerCase())).getDefaultState();
			}
			case NETHER -> {
				return Registry.BLOCK.get(new Identifier(StarrySkies.CONFIG.floorBlockNether.toLowerCase())).getDefaultState();
			}
			default -> {
				return Registry.BLOCK.get(new Identifier(StarrySkies.CONFIG.floorBlockEnd.toLowerCase())).getDefaultState();
			}
		}
	}
	
	public BlockState getBottomBlockState() {
		switch (this) {
			case OVERWORLD -> {
				return Registry.BLOCK.get(new Identifier(StarrySkies.CONFIG.bottomBlockOverworld.toLowerCase())).getDefaultState();
			}
			case NETHER -> {
				return Registry.BLOCK.get(new Identifier(StarrySkies.CONFIG.bottomBlockNether.toLowerCase())).getDefaultState();
			}
			default -> {
				return Registry.BLOCK.get(new Identifier(StarrySkies.CONFIG.bottomBlockEnd.toLowerCase())).getDefaultState();
			}
		}
	}
	
	public RegistryEntry<Biome> getBiome(Registry<Biome> biomeRegistry) {
		switch (this) {
			case OVERWORLD -> {
				return biomeRegistry.getOrCreateEntry(StarrySkyBiomes.OVERWORLD_KEY);
			}
			case NETHER -> {
				return biomeRegistry.getOrCreateEntry(StarrySkyBiomes.NETHER_KEY);
			}
			default -> {
				return biomeRegistry.getOrCreateEntry(StarrySkyBiomes.END_KEY);
			}
		}
	}
}
