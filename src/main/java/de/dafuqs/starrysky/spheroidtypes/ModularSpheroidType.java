package de.dafuqs.starrysky.spheroidtypes;

import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;

public class ModularSpheroidType extends SpheroidType {

    private final BlockState mainBlock;
    private BlockState topBlock;
    private BlockState bottomBlock;

    private Identifier centerChestLootTable;


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

    public ModularSpheroidType addCenterChestWithLoot(Identifier lootTable) {
        this.centerChestLootTable = lootTable;
        return this;
    }

    public boolean hasCenterChest() {
        return this.centerChestLootTable != null;
    }

    public Identifier getCenterChestLootTable() {
        return this.centerChestLootTable;
    }

}
