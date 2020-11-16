package de.dafuqs.starrysky.spheroidtypes;

import de.dafuqs.starrysky.SpheroidData.SpheroidAdvancementGroup;
import de.dafuqs.starrysky.SpheroidData.SpheroidAdvancementIdentifier;
import net.minecraft.block.BlockState;

public class ModularSpheroidType extends SpheroidType {

    //protected BlockState coreBlock;
    private BlockState mainBlock;
    private BlockState topBlock;
    private BlockState bottomBlock;


    public ModularSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, BlockState mainBlock, int minSize, int maxSize) {
        super();

        this.spheroidAdvancementIdentifier = spheroidAdvancementIdentifier;
        this.mainBlock = mainBlock;
        this.minRadius = minSize;
        this.maxRadius = maxSize;
    }

    public String getDescription() {
        return "ModularSpheroid";
    }

    public BlockState getTopBlock() {
        return topBlock != null ? topBlock : mainBlock;
    }

    public BlockState getBottomBlock() {
        return bottomBlock != null ? bottomBlock : mainBlock;
    }

    public BlockState getMainBlock() {
        return mainBlock;
    }

    public ModularSpheroidType setTopBlockState(BlockState state) {
        topBlock = state;
        return this;
    }

    public ModularSpheroidType setBottomBlockState(BlockState state) {
        bottomBlock = state;
        return this;
    }

}
