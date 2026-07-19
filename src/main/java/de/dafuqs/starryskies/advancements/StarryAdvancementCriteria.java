package de.dafuqs.starryskies.advancements;

import de.dafuqs.starryskies.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;

public class StarryAdvancementCriteria {

	public static SphereDiscoveredCriterion SPHERE_DISCOVERED;

	public static void register() {
		SPHERE_DISCOVERED = Registry.register(BuiltInRegistries.TRIGGER_TYPES, StarrySkies.id("sphere_discovered"), new SphereDiscoveredCriterion());
	}

}