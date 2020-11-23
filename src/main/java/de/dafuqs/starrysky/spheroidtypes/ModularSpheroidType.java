package de.dafuqs.starrysky.spheroidtypes;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.spheroids.ModularSpheroid;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.ChunkRandom;

public class ModularSpheroidType extends SpheroidType {

    private final BlockState mainBlock;
    private BlockState topBlock;
    private BlockState bottomBlock;

    private Identifier centerChestLootTable;


    public ModularSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, BlockState mainBlock, int minSize, int maxSize) {
        super();

        if(mainBlock == null) {
            StarrySkyCommon.LOGGER.error("ModularSpheroidType: Registered a SpheroidType with null mainBlock!");
        }

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
        if(mainBlock == null) {
            StarrySkyCommon.LOGGER.error("ModularSpheroidType: Registered a SpheroidType with null mainBlock!");
        } else {
            topBlock = state;
        }
        return this;
    }

    public ModularSpheroidType setBottomBlockState(BlockState state) {
        if(mainBlock == null) {
            StarrySkyCommon.LOGGER.error("ModularSpheroidType: Registered a SpheroidType with null mainBlock!");
        } else {
            bottomBlock = state;
        }
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

    public ModularSpheroid getRandomSphere(ChunkRandom chunkRandom) {
        return new ModularSpheroid(this, chunkRandom);
    }

}
