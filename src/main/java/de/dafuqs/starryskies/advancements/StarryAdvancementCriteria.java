package de.dafuqs.starryskies.advancements;

import net.fabricmc.fabric.mixin.object.builder.*;

public class StarryAdvancementCriteria {
	
	public static SpheroidDiscoveredCriterion SPHEROID_DISCOVERED;
	
	public static void register() {
		SPHEROID_DISCOVERED = CriteriaAccessor.callRegister(new SpheroidDiscoveredCriterion());
	}
	
}