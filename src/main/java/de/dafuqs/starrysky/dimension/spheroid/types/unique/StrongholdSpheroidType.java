package de.dafuqs.starrysky.dimension.spheroid.types.unique;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.dimension.spheroid.SpheroidEntitySpawnDefinition;
import de.dafuqs.starrysky.dimension.spheroid.spheroids.unique.StrongholdSpheroid;
import de.dafuqs.starrysky.dimension.spheroid.types.SpheroidType;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;

public class StrongholdSpheroidType extends SpheroidType {

    int minShellRadius = 3;
    int maxShellRadius = 6;

    public StrongholdSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int minRadius, int maxRadius) {
        super(spheroidAdvancementIdentifier, minRadius, maxRadius);
    }

    public String getDescription() {
        return "StrongholdMonumentSpheroid";
    }

    public StrongholdSpheroid getRandomSpheroid(ChunkRandom chunkRandom) {
        int radius = getRandomRadius(chunkRandom);

        ArrayList<SpheroidDecorator> spheroidDecorators = getSpheroidDecoratorsWithChance(chunkRandom);
        ArrayList<SpheroidEntitySpawnDefinition> entityTypesToSpawn = getRandomEntityTypesToSpawn(chunkRandom);
        int shellRadius = Support.getRandomBetween(chunkRandom, minShellRadius, maxShellRadius);

        return new StrongholdSpheroid(chunkRandom, spheroidAdvancementIdentifier, radius, spheroidDecorators, entityTypesToSpawn, shellRadius);
    }

}
