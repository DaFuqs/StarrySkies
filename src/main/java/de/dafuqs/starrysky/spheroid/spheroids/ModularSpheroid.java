package de.dafuqs.starrysky.spheroid.spheroids;

import de.dafuqs.starrysky.spheroid.SpheroidEntitySpawnDefinition;
import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;

public class ModularSpheroid extends Spheroid {

    private final BlockState mainBlock;
    private final BlockState topBlock;
    private final BlockState bottomBlock;
    private final Identifier centerChestLootTable;

    public ModularSpheroid(ChunkRandom random, SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int radius, ArrayList<SpheroidDecorator> spheroidDecorators, ArrayList<SpheroidEntitySpawnDefinition> entityTypesToSpawn, BlockState mainBlock, BlockState topBlock, BlockState bottomBlock, Identifier centerChestLootTable) {
        super(spheroidAdvancementIdentifier, random, spheroidDecorators, radius, entityTypesToSpawn);
        this.mainBlock = mainBlock;
        this.topBlock = topBlock;
        this.bottomBlock = bottomBlock;
        this.centerChestLootTable = centerChestLootTable;
    }

    public String getDescription() {
        String s = "+++ ModularSpheroid +++" +
                "\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
                "\nRadius: " + this.radius +
                "\nMaterial: " + this.mainBlock.toString();

        if(this.topBlock != null) {
            s += "\nTopBlock: " + this.topBlock.toString();
        }
        if(this.bottomBlock != null) {
            s += "\nBottomBlock: " + this.bottomBlock.toString();
        }
        return s;
    }

    public void generate(Chunk chunk) {
        int chunkX = chunk.getPos().x;
        int chunkZ = chunk.getPos().z;

        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        int z = this.getPosition().getZ();

        boolean hasCenterChest = centerChestLootTable != null;

        random.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
        for (int x2 = Math.max(chunkX * 16, x - this.radius); x2 <= Math.min(chunkX * 16 + 15, x + this.radius); x2++) {
            for (int y2 = y - this.radius; y2 <= y + this.radius; y2++) {
                for (int z2 = Math.max(chunkZ * 16, z - this.radius); z2 <= Math.min(chunkZ * 16 + 15, z + this.radius); z2++) {
                    BlockPos currBlockPos = new BlockPos(x2, y2, z2);
                    long d = Math.round(Support.distance(x, y, z, x2, y2, z2));
                    if (hasCenterChest && d == 0) {
                        placeCenterChestWithLootTable(chunk, currBlockPos, this.centerChestLootTable, random, false);
                    } else if (d == this.radius) {
                        if (isBottomBlock(d, x2, y2, z2)) {
                            chunk.setBlockState(currBlockPos, this.bottomBlock, false);
                        } else if (isTopBlock(d, x2, y2, z2)) {
                            chunk.setBlockState(currBlockPos, this.topBlock, false);
                            addDecorationBlockPosition(currBlockPos);
                        } else {
                            chunk.setBlockState(currBlockPos, this.mainBlock, false);
                        }
                    } else if (d <= this.radius) {
                        chunk.setBlockState(currBlockPos, this.mainBlock, false);
                    }
                }
            }
        }
    }

}
