package de.dafuqs.starryskies.advancements;

import de.dafuqs.starryskies.*;
import net.minecraft.advancement.criterion.*;

public class StarryAdvancementCriteria {
	
	public static SpheroidDiscoveredCriterion SPHEROID_DISCOVERED;
	
	public static void register() {
		SPHEROID_DISCOVERED = Criteria.register(StarrySkies.locatePlain("spheroid_discovered"), new SpheroidDiscoveredCriterion());
	}
	
}