package de.dafuqs.starrysky.spheroids;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.spheroidtypes.ModularSpheroidType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

public class ModularSpheroid extends Spheroid {

    private BlockState mainBlock;
    private BlockState topBlock;
    private BlockState bottomBlock;

    public ModularSpheroid(ModularSpheroidType modularSpheroidType, ChunkRandom random) {
        super(modularSpheroidType, random);
        this.radius = modularSpheroidType.getRandomRadius(random);
        this.mainBlock = modularSpheroidType.getMainBlock();
        this.topBlock = modularSpheroidType.getTopBlock();
        this.bottomBlock = modularSpheroidType.getBottomBlock();
    }

    public String getDescription() {
        String s = this.spheroidType.getDescription() +
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

    private boolean isTopBlock(double x, double y, double z) {
        return Math.round(Support.distance(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), x, y, z)) == this.radius && Math.round(Support.distance(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), x, y+1, z)) > this.radius;
    }

    private boolean isBottomBlock(double x, double y, double z) {
        return Math.round(Support.distance(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), x, y, z)) >= this.radius && Math.round(Support.distance(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), x, y-1, z)) > this.radius;
    }

    public void generate(Chunk chunk) {
        int chunkX = chunk.getPos().x;
        int chunkZ = chunk.getPos().z;

        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        int z = this.getPosition().getZ();

        boolean hasCenterChest = ((ModularSpheroidType) this.getSpheroidType()).hasCenterChest();

        random.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
        for (int x2 = Math.max(chunkX * 16, x - this.radius); x2 <= Math.min(chunkX * 16 + 15, x + this.radius); x2++) {
            for (int y2 = y - this.radius; y2 <= y + this.radius; y2++) {
                for (int z2 = Math.max(chunkZ * 16, z - this.radius); z2 <= Math.min(chunkZ * 16 + 15, z + this.radius); z2++) {
                    BlockPos currBlockPos = new BlockPos(x2, y2, z2);
                    long d = Math.round(Support.distance(x, y, z, x2, y2, z2));
                    if (hasCenterChest && d == 0) {
                        placeCenterChestWithLootTable(chunk, currBlockPos);
                    } else if (d == this.radius) {
                        if (isBottomBlock(x2, y2, z2))
                            chunk.setBlockState(currBlockPos, this.bottomBlock, false);
                        else if (isTopBlock(x2, y2, z2))
                            chunk.setBlockState(currBlockPos, this.topBlock, false);
                        else
                            chunk.setBlockState(currBlockPos, this.mainBlock, false);
                    } else if (d < this.radius) {
                        chunk.setBlockState(currBlockPos, this.mainBlock, false);
                    }
                }
            }
        }

        if(((ModularSpheroidType) this.getSpheroidType()).hasCenterChest()) {

        }

        this.setChunkFinished(chunk.getPos());
    }

    private void placeCenterChestWithLootTable(Chunk chunk, BlockPos blockPos) {
        chunk.setBlockState(blockPos, Blocks.CHEST.getDefaultState(), false);
        chunk.setBlockEntity(blockPos, new ChestBlockEntity());
        LootableContainerBlockEntity.setLootTable(chunk, random, blockPos, ((ModularSpheroidType) this.getSpheroidType()).getCenterChestLootTable());
    }

}
