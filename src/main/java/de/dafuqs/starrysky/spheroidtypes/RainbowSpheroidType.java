package de.dafuqs.starrysky.spheroidtypes;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.spheroiddecorators.SpheroidDecorator;
import de.dafuqs.starrysky.spheroids.RainbowSpheroid;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;

public class RainbowSpheroidType extends SpheroidType {

    private final ArrayList<BlockState> rainbowBlocks;

    public RainbowSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int minRadius, int maxRadius, ArrayList<BlockState> rainbowBlocks) {
        super(spheroidAdvancementIdentifier, minRadius, maxRadius);

        if(rainbowBlocks == null || rainbowBlocks.size() == 0) {
            StarrySkyCommon.LOGGER.error("RainbowSpheroidType: Registered a SpheroidType with empty rainbowBlocks!");
        }

        this.rainbowBlocks = rainbowBlocks;
    }

    @Override
    public String getDescription() {
        return "RainbowSpheroid";
    }

    public RainbowSpheroid getRandomSphere(ChunkRandom chunkRandom) {
        int radius = getRandomRadius(chunkRandom);
        ArrayList<SpheroidDecorator> spheroidDecorators = getSpheroidDecoratorsWithChance(chunkRandom);
        return new RainbowSpheroid(chunkRandom, spheroidAdvancementIdentifier, radius, spheroidDecorators, rainbowBlocks);
    }

}
