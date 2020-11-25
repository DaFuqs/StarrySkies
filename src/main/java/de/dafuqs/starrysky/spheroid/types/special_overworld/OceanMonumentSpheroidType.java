package de.dafuqs.starrysky.spheroid.types.special_overworld;

import de.dafuqs.starrysky.SpheroidEntitySpawnDefinition;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.decorators.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.spheroids.special_overworld.OceanMonumentSpheroid;
import de.dafuqs.starrysky.spheroid.types.SpheroidType;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;

public class OceanMonumentSpheroidType extends SpheroidType {

    private final int minTreasureRadius;
    private final int maxTreasureRadius;
    private final int minShellRadius;
    private final int maxShellRadius;

    public OceanMonumentSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int minRadius, int maxRadius, int minTreasureRadius, int maxTreasureRadius, int minShellRadius, int maxShellRadius) {
        super(spheroidAdvancementIdentifier, minRadius, maxRadius);

        this.minTreasureRadius = minTreasureRadius;
        this.maxTreasureRadius = maxTreasureRadius;
        this.minShellRadius = minShellRadius;
        this.maxShellRadius = maxShellRadius;
    }

    public String getDescription() {
        return "OceanMonumentSpheroid";
    }

    public OceanMonumentSpheroid getRandomSphere(ChunkRandom chunkRandom) {
        int radius = getRandomRadius(chunkRandom);
        int treasureRadius = chunkRandom.nextInt(this.maxTreasureRadius - this.minTreasureRadius + 1) + this.minTreasureRadius;
        int shellRadius = chunkRandom.nextInt(this.maxShellRadius - this.minShellRadius + 1) + this.minShellRadius;
        ArrayList<SpheroidDecorator> spheroidDecorators = getSpheroidDecoratorsWithChance(chunkRandom);
        ArrayList<SpheroidEntitySpawnDefinition> entityTypesToSpawn = getRandomEntityTypesToSpawn(chunkRandom);

        return new OceanMonumentSpheroid(chunkRandom, spheroidAdvancementIdentifier, radius, spheroidDecorators, entityTypesToSpawn, treasureRadius, shellRadius);
    }

}
