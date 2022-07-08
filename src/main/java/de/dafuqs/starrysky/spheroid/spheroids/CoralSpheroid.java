package de.dafuqs.starrysky.spheroid.spheroids;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.spheroid.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.SpheroidEntitySpawnDefinition;
import de.dafuqs.starrysky.spheroid.lists.SpheroidList;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;

public class CoralSpheroid extends Spheroid {

    //STATIC CONFIG STUFF
    protected BlockState shellBlock;
    protected int shellRadius;
    protected BlockState WATER = Blocks.WATER.getDefaultState();
    protected Identifier centerChestLootTable;

    public CoralSpheroid(ChunkRandom random, SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int radius, ArrayList<SpheroidDecorator> spheroidDecorators, ArrayList<SpheroidEntitySpawnDefinition> entityTypesToSpawn, BlockState shellBlock, int shellRadius, Identifier centerChestLootTable) {
        super(spheroidAdvancementIdentifier, random, spheroidDecorators, radius, entityTypesToSpawn);
        this.shellBlock = shellBlock;
        this.shellRadius = shellRadius;
        this.centerChestLootTable = centerChestLootTable;
    }

    @Override
    public void generate(Chunk chunk) {
        int chunkX = chunk.getPos().x;
        int chunkZ = chunk.getPos().z;

        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        int z = this.getPosition().getZ();

        boolean hasChest = this.centerChestLootTable != null;

        random.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
        for (int x2 = Math.max(chunkX * 16, x - this.radius); x2 <= Math.min(chunkX * 16 + 15, x + this.radius); x2++) {
            for (int y2 = y - this.radius; y2 <= y + this.radius; y2++) {
                for (int z2 = Math.max(chunkZ * 16, z - this.radius); z2 <= Math.min(chunkZ * 16 + 15, z + this.radius); z2++) {
                    BlockPos currBlockPos = new BlockPos(x2, y2, z2);
                    long d = Math.round(Support.getDistance(x, y, z, x2, y2, z2));
                    if(d == 0 && hasChest) {
                        placeCenterChestWithLootTable(chunk, currBlockPos, this.centerChestLootTable, random, true);
                    } else if (d <= (this.radius - this.shellRadius - 1)) {
                        int rand = random.nextInt(7);
                        if (rand < 2) {
                            BlockState coral = getRandomCoralBlock(random);
                            if (rand == 0 && chunk.getBlockState(currBlockPos.down()).getBlock() == Blocks.WATER) {
                                chunk.setBlockState(currBlockPos.down(), coral, false);
                                chunk.setBlockState(currBlockPos, getRandomWaterLoggableBlock(random), false);
                            } else {
                                chunk.setBlockState(currBlockPos, coral, false);
                            }
                        } else {
                            chunk.setBlockState(currBlockPos, WATER, false);
                        }
                    } else if (d <= (this.radius - this.shellRadius)) {
                        chunk.setBlockState(currBlockPos, WATER, false);
                    } else if (d <= this.radius) {
                        chunk.setBlockState(currBlockPos, this.shellBlock, false);
                    }
                }
            }
        }
    }

    public String getDescription() {
        return "+++ CoralSpheroid +++" +
                "\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
                "\nRadius: " + this.radius +
                "\nShell: " + this.shellBlock.toString() + " (Radius: " + this.shellRadius + ")";
    }

    public BlockState getRandomCoralBlock(ChunkRandom random) {
        return SpheroidList.LIST_FULL_CORAL_BLOCKS.get(random.nextInt(SpheroidList.LIST_FULL_CORAL_BLOCKS.size()));
    }

    public BlockState getRandomWaterLoggableBlock(ChunkRandom random) {
        return SpheroidList.LIST_WATERLOGGABLE_CORAL_BLOCKS.get(random.nextInt(SpheroidList.LIST_WATERLOGGABLE_CORAL_BLOCKS.size()));
    }

}
