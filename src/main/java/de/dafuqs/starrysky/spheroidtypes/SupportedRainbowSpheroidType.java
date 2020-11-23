package de.dafuqs.starrysky.spheroidtypes;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import net.minecraft.block.BlockState;

import java.util.List;

public class SupportedRainbowSpheroidType extends SpheroidType {

    private final List<BlockState> rainbowBlocks;
    private final BlockState floorBlock;

    public SupportedRainbowSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, List<BlockState> rainbowBlocks, BlockState floorBlock, int minSize, int maxSize) {
        if(rainbowBlocks == null || rainbowBlocks.size() == 0) {
            StarrySkyCommon.LOGGER.error("SupportedRainbowSpheroidType: Registered a SpheroidType with empty rainbowBlocks!");
        }

        this.spheroidAdvancementIdentifier = spheroidAdvancementIdentifier;
        this.minRadius = minSize;
        this.maxRadius = maxSize;
        this.rainbowBlocks = rainbowBlocks;
        this.floorBlock = floorBlock;
    }

    public List<BlockState> getRainbowBlocks() {
        return rainbowBlocks;
    }

    public BlockState getFloorBlock() {
        return floorBlock;
    }

    @Override
    public String getDescription() {
        return "SupportedRainbowSpheroid";
    }


}
