package de.dafuqs.starrysky.spheroidtypes;

import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;


public class CaveSpheroidType extends ShellSpheroidType {

    private BlockState shellBlock;
    private BlockState caveFloorBlock;
    private BlockState topBlock;
    private BlockState bottomBlock;

    public CaveSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, BlockState shellBlock, BlockState caveFloorBlock, int minRadius, int maxRadius, int minShellRadius, int maxShellRadius) {
        //BlockState coreBlock, BlockState shellBlock, int minSize, int maxSize, int minShellRadius, int maxShellRadius) {
        super(spheroidAdvancementIdentifier, Blocks.CAVE_AIR.getDefaultState(), shellBlock, minRadius, maxRadius, minShellRadius, maxShellRadius);

        this.caveFloorBlock = caveFloorBlock;
        this.shellBlock = shellBlock;
    }

    public BlockState getCaveFloorBlock() {
        return this.caveFloorBlock;
    }

    public CaveSpheroidType setTopBlockState(BlockState state) {
        topBlock = state;
        return this;
    }

    public CaveSpheroidType setBottomBlockState(BlockState state) {
        bottomBlock = state;
        return this;
    }

    public BlockState getTopBlock() {
        return topBlock != null ? topBlock : shellBlock;
    }

    public BlockState getBottomBlock() {
        return bottomBlock != null ? bottomBlock : shellBlock;
    }

    public String getDescription() {
        return "CoreSpheroid";
    }

}