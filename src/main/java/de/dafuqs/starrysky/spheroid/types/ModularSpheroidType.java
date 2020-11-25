package de.dafuqs.starrysky.spheroid.types;

import de.dafuqs.starrysky.SpheroidEntitySpawnDefinition;
import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.dimension.decorators.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.spheroids.ModularSpheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;

public class ModularSpheroidType extends SpheroidType {

    private final BlockState mainBlock;
    private BlockState topBlock;
    private BlockState bottomBlock;
    private Identifier centerChestLootTable;

    public ModularSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int minRadius, int maxRadius, BlockState mainBlock) {
        super(spheroidAdvancementIdentifier, minRadius, maxRadius);

        if(mainBlock == Blocks.AIR.getDefaultState()) {
            StarrySkyCommon.LOGGER.error("ModularSpheroidType: Registered a SpheroidType with null mainBlock!");
        }

        this.mainBlock = mainBlock;
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

    public ModularSpheroid getRandomSphere(ChunkRandom chunkRandom) {
        int radius = getRandomRadius(chunkRandom);
        ArrayList<SpheroidDecorator> spheroidDecorators = getSpheroidDecoratorsWithChance(chunkRandom);
        ArrayList<SpheroidEntitySpawnDefinition> entityTypesToSpawn = getRandomEntityTypesToSpawn(chunkRandom);

        BlockState topBlock = getTopBlock();
        BlockState bottomBlock = getBottomBlock();

        return new ModularSpheroid(chunkRandom, spheroidAdvancementIdentifier, radius, spheroidDecorators, entityTypesToSpawn, mainBlock, topBlock, bottomBlock, centerChestLootTable);
    }

}
