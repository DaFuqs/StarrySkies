package de.dafuqs.starryskies.spheroids;

import net.minecraft.entity.EntityType;

public class SpheroidEntitySpawnDefinition {
	
	public EntityType entityType;
	public float chance;
	public int minCount;
	public int maxCount;
	
	public SpheroidEntitySpawnDefinition(EntityType entityType, int minCount, int maxCount, float chance) {
		this.entityType = entityType;
		this.minCount = minCount;
		this.maxCount = maxCount;
		this.chance = chance;
	}
	
}
