package de.dafuqs.starrysky.spheroid.types;

import de.dafuqs.starrysky.spheroid.SpheroidEntitySpawnDefinition;
import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.spheroid.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.spheroids.RainbowSpheroid;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.random.ChunkRandom;

import java.util.ArrayList;

import static org.apache.logging.log4j.Level.ERROR;

public class RainbowSpheroidType extends SpheroidType {

    private final ArrayList<BlockState> rainbowBlocks;

    public RainbowSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int minRadius, int maxRadius, ArrayList<BlockState> rainbowBlocks) {
        super(spheroidAdvancementIdentifier, minRadius, maxRadius);

        if(rainbowBlocks == null || rainbowBlocks.size() == 0) {
            StarrySkyCommon.log(ERROR, "RainbowSpheroidType: Registered a SpheroidType with empty rainbowBlocks!");
        }

        this.rainbowBlocks = rainbowBlocks;
    }

    @Override
    public String getDescription() {
        return "RainbowSpheroid";
    }

    public RainbowSpheroid getRandomSpheroid(ChunkRandom chunkRandom) {
        int radius = getRandomRadius(chunkRandom);

        ArrayList<SpheroidDecorator> spheroidDecorators = getSpheroidDecoratorsWithChance(chunkRandom);
        ArrayList<SpheroidEntitySpawnDefinition> entityTypesToSpawn = getRandomEntityTypesToSpawn(chunkRandom);

        return new RainbowSpheroid(chunkRandom, spheroidAdvancementIdentifier, radius, spheroidDecorators, entityTypesToSpawn, rainbowBlocks);
    }

}
