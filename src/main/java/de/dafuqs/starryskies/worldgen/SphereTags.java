package de.dafuqs.starryskies.worldgen;

import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.registries.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.Structure;

public class SphereTags {
	
	public static final TagKey<ConfiguredSphere<?, ?>> EYE_OF_ENDER_LOCATED = of("eye_of_ender_located");
	public static final TagKey<ConfiguredSphere<?, ?>> ON_OCEAN_EXPLORER_MAPS = of("on_ocean_explorer_maps");
	
	private static TagKey<ConfiguredSphere<?, ?>> of(String id) {
		return TagKey.create(StarryRegistryKeys.CONFIGURED_SPHERE, StarrySkies.id(id));
	}
	
	public static TagKey<ConfiguredSphere<?, ?>> getForVanillaStructure(TagKey<Structure> structureTag) {
		return of(structureTag.location().getPath());
	}
}
