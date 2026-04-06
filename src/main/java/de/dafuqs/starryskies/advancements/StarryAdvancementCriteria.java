package de.dafuqs.starryskies.advancements;

import de.dafuqs.starryskies.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class StarryAdvancementCriteria {

	public static SphereDiscoveredCriterion SPHERE_DISCOVERED;

	public static void register() {
		SPHERE_DISCOVERED = Registry.register(BuiltInRegistries.TRIGGER_TYPES, StarrySkies.idPlain("sphere_discovered"), new SphereDiscoveredCriterion());
	}

}