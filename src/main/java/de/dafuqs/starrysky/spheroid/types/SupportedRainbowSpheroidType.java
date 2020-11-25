package de.dafuqs.starrysky.spheroid.types;

import de.dafuqs.starrysky.SpheroidEntitySpawnDefinition;
import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.dimension.decorators.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.spheroids.SupportedRainbowSpheroid;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;

public class SupportedRainbowSpheroidType extends SpheroidType {

    private final ArrayList<BlockState> rainbowBlocks;
    private final BlockState floorBlock;

    public SupportedRainbowSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int minRadius, int maxRadius, ArrayList<BlockState> rainbowBlocks, BlockState floorBlock) {
        super(spheroidAdvancementIdentifier, minRadius, maxRadius);

        if(rainbowBlocks == null || rainbowBlocks.size() == 0) {
            StarrySkyCommon.LOGGER.error("SupportedRainbowSpheroidType: Registered a SpheroidType with empty rainbowBlocks!");
        }

        this.rainbowBlocks = rainbowBlocks;
        this.floorBlock = floorBlock;
    }

    @Override
    public String getDescription() {
        return "SupportedRainbowSpheroid";
    }

    public SupportedRainbowSpheroid getRandomSphere(ChunkRandom chunkRandom) {
        int radius = getRandomRadius(chunkRandom);
        ArrayList<SpheroidDecorator> spheroidDecorators = getSpheroidDecoratorsWithChance(chunkRandom);
        ArrayList<SpheroidEntitySpawnDefinition> entityTypesToSpawn = getRandomEntityTypesToSpawn(chunkRandom);
        return new SupportedRainbowSpheroid(chunkRandom, spheroidAdvancementIdentifier, radius, spheroidDecorators, entityTypesToSpawn, rainbowBlocks, floorBlock);
    }
}
