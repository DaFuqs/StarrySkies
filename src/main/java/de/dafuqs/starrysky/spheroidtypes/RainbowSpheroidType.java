package de.dafuqs.starrysky.spheroidtypes;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import net.minecraft.block.BlockState;

import java.util.List;

public class RainbowSpheroidType extends SpheroidType {

    private final List<BlockState> rainbowBlocks;

    public RainbowSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, List<BlockState> rainbowBlocks, int minSize, int maxSize) {
        if(rainbowBlocks == null || rainbowBlocks.size() == 0) {
            StarrySkyCommon.LOGGER.error("RainbowSpheroidType: Registered a SpheroidType with empty rainbowBlocks!");
        }

        this.spheroidAdvancementIdentifier = spheroidAdvancementIdentifier;
        this.minRadius = minSize;
        this.maxRadius = maxSize;
        this.rainbowBlocks = rainbowBlocks;
    }

    public List<BlockState> getRainbowBlocks() {
        return rainbowBlocks;
    }

    @Override
    public String getDescription() {
        return "RainbowSpheroid";
    }


}
