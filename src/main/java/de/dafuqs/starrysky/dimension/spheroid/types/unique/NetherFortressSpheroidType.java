package de.dafuqs.starrysky.dimension.spheroid.types.unique;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.dimension.spheroid.SpheroidEntitySpawnDefinition;
import de.dafuqs.starrysky.dimension.spheroid.spheroids.unique.NetherFortressSpheroid;
import de.dafuqs.starrysky.dimension.spheroid.types.SpheroidType;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;

public class NetherFortressSpheroidType extends SpheroidType {

    int minShellRadius = 2;
    int maxShellRadius = 4;

    public NetherFortressSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int minRadius, int maxRadius) {
        super(spheroidAdvancementIdentifier, minRadius, maxRadius);
    }

    public String getDescription() {
        return "NetherFortressSpheroid";
    }

    public NetherFortressSpheroid getRandomSpheroid(ChunkRandom chunkRandom) {
        int radius = getRandomRadius(chunkRandom);

        ArrayList<SpheroidDecorator> spheroidDecorators = getSpheroidDecoratorsWithChance(chunkRandom);
        ArrayList<SpheroidEntitySpawnDefinition> entityTypesToSpawn = getRandomEntityTypesToSpawn(chunkRandom);
        int shellRadius = Support.getRandomBetween(chunkRandom, minShellRadius, maxShellRadius);

        return new NetherFortressSpheroid(chunkRandom, spheroidAdvancementIdentifier, radius, spheroidDecorators, entityTypesToSpawn, shellRadius);
    }

}
