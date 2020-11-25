package de.dafuqs.starrysky;

import net.minecraft.entity.EntityType;

public class SpheroidEntitySpawnDefinition {

    public EntityType entityType;
    public int minAmount;
    public int maxAmount;

    public SpheroidEntitySpawnDefinition(EntityType entityType, int minAmount, int maxAmount) {
        this.entityType = entityType;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

}
