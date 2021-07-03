package de.dafuqs.starrysky.dimension.spheroid.types.unique;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.dimension.spheroid.SpheroidEntitySpawnDefinition;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.dimension.spheroid.spheroids.unique.BeeHiveSpheroid;
import de.dafuqs.starrysky.dimension.spheroid.types.SpheroidType;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;

public class BeeHiveSpheroidType extends SpheroidType {

    private final int minShellRadius;
    private final int maxShellRadius;
    private final int minFlowerRingRadius;
    private final int maxFlowerRingRadius;
    private final int minFlowerRingSpacing;
    private final int maxFlowerRingSpacing;

    public BeeHiveSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int minRadius, int maxRadius, int minShellRadius, int maxShellRadius, int minFlowerRingRadius, int maxFlowerRingRadius, int minFlowerRingSpacing, int maxFlowerRingSpacing) {
        super(spheroidAdvancementIdentifier, minRadius, maxRadius);

        this.minShellRadius = minShellRadius;
        this.maxShellRadius = maxShellRadius;
        this.minFlowerRingRadius = minFlowerRingRadius;
        this.maxFlowerRingRadius = maxFlowerRingRadius;
        this.minFlowerRingSpacing = minFlowerRingSpacing;
        this.maxFlowerRingSpacing = maxFlowerRingSpacing;
    }

    @Override
    public String getDescription() {
        return "BeeHiveSpheroid";
    }

    public BeeHiveSpheroid getRandomSpheroid(ChunkRandom chunkRandom) {
        int radius = getRandomRadius(chunkRandom);
        int shellRadius = Support.getRandomBetween(chunkRandom, minShellRadius, maxShellRadius);
        int flowerRingRadius = Support.getRandomBetween(chunkRandom, minFlowerRingRadius, maxFlowerRingRadius);
        int flowerRingSpacing = Support.getRandomBetween(chunkRandom, minFlowerRingSpacing, maxFlowerRingSpacing);

        ArrayList<SpheroidDecorator> spheroidDecorators = getSpheroidDecoratorsWithChance(chunkRandom);
        ArrayList<SpheroidEntitySpawnDefinition> entityTypesToSpawn = getRandomEntityTypesToSpawn(chunkRandom);

        return new BeeHiveSpheroid(chunkRandom, spheroidAdvancementIdentifier, radius, spheroidDecorators, entityTypesToSpawn, shellRadius, flowerRingRadius, flowerRingSpacing);
    }

}
