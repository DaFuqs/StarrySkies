package de.dafuqs.starrysky.spheroid.types;

import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.SpheroidEntitySpawnDefinition;
import de.dafuqs.starrysky.spheroid.spheroids.EndGatewaySpheroid;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;

// TODO
public class EndGatewaySpheroidType extends SpheroidType {

    public EndGatewaySpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int minRadius, int maxRadius) {
        super(spheroidAdvancementIdentifier, minRadius, maxRadius);
    }

    @Override
    public String getDescription() {
        return "EndGatewaySpheroid";
    }

    public EndGatewaySpheroid getRandomSpheroid(ChunkRandom chunkRandom) {
        int radius = getRandomRadius(chunkRandom);

        ArrayList<SpheroidDecorator> spheroidDecorators = getSpheroidDecoratorsWithChance(chunkRandom);
        ArrayList<SpheroidEntitySpawnDefinition> entityTypesToSpawn = getRandomEntityTypesToSpawn(chunkRandom);

        return new EndGatewaySpheroid(chunkRandom, spheroidAdvancementIdentifier, radius, spheroidDecorators, entityTypesToSpawn);
    }

}
