package de.dafuqs.starrysky.spheroid.types.unique;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.SpheroidEntitySpawnDefinition;
import de.dafuqs.starrysky.spheroid.spheroids.unique.EndCitySpheroid;
import de.dafuqs.starrysky.spheroid.types.SpheroidType;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;

public class EndCitySpheroidType extends SpheroidType {

    int minShellRadius = 1;
    int maxShellRadius = 3;

    public EndCitySpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int minRadius, int maxRadius) {
        super(spheroidAdvancementIdentifier, minRadius, maxRadius);
    }

    public String getDescription() {
        return "EndCityMonumentSpheroid";
    }

    public EndCitySpheroid getRandomSpheroid(ChunkRandom chunkRandom) {
        int radius = getRandomRadius(chunkRandom);

        ArrayList<SpheroidDecorator> spheroidDecorators = getSpheroidDecoratorsWithChance(chunkRandom);
        ArrayList<SpheroidEntitySpawnDefinition> entityTypesToSpawn = getRandomEntityTypesToSpawn(chunkRandom);
        int shellRadius = Support.getRandomBetween(chunkRandom, minShellRadius, maxShellRadius);

        return new EndCitySpheroid(chunkRandom, spheroidAdvancementIdentifier, radius, spheroidDecorators, entityTypesToSpawn, shellRadius);
    }

}
