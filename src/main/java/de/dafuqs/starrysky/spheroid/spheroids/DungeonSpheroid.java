package de.dafuqs.starrysky.spheroid.spheroids;

import de.dafuqs.starrysky.spheroid.SpheroidEntitySpawnDefinition;
import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.spheroid.SpheroidDecorator;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootTables;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.random.ChunkRandom;

import java.util.ArrayList;

public class DungeonSpheroid extends Spheroid {

    private final EntityType entityType;
    private final BlockState shellBlock;
    private final int shellRadius;

    public DungeonSpheroid(ChunkRandom random, SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int radius, ArrayList<SpheroidDecorator> spheroidDecorators, ArrayList<SpheroidEntitySpawnDefinition> entityTypesToSpawn, EntityType entityType, BlockState shellBlock, int shellRadius) {
        super(spheroidAdvancementIdentifier, random, spheroidDecorators, radius, entityTypesToSpawn);

        this.entityType = entityType;
        this.shellBlock = shellBlock;
        this.shellRadius = shellRadius;
    }
    
    @Override
    public void generate(Chunk chunk) {
        int chunkX = chunk.getPos().x;
        int chunkZ = chunk.getPos().z;

        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        int z = this.getPosition().getZ();

        BlockState chestBlockState = Blocks.CHEST.getDefaultState();

        random.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
        for (int x2 = Math.max(chunkX * 16, x - this.radius); x2 <= Math.min(chunkX * 16 + 15, x + this.radius); x2++) {
            for (int y2 = y - this.radius; y2 <= y + this.radius; y2++) {
                for (int z2 = Math.max(chunkZ * 16, z - this.radius); z2 <= Math.min(chunkZ * 16 + 15, z + this.radius); z2++) {
                    BlockPos currBlockPos = new BlockPos(x2, y2, z2);
                    long d = Math.round(Support.getDistance(x, y, z, x2, y2, z2));
                    if(d == 0) {
                        chunk.setBlockState(currBlockPos, Blocks.SPAWNER.getDefaultState(), false);
                        chunk.setBlockEntity(new MobSpawnerBlockEntity(currBlockPos, Blocks.SPAWNER.getDefaultState()));
                        BlockEntity blockEntity_1 = chunk.getBlockEntity(currBlockPos);
                        if (blockEntity_1 instanceof MobSpawnerBlockEntity) {
                            ((MobSpawnerBlockEntity) blockEntity_1).getLogic().setEntityId(this.entityType);
                        }
                    } else if (d == (this.radius - this.shellRadius -1) &&
                            Math.round(Support.getDistance(x, y, z, x2, y2-1, z2)) == (this.radius - this.shellRadius) &&
                            random.nextInt(radius * 8) == 0) {
                            chunk.setBlockState(currBlockPos, chestBlockState, false);
                            chunk.setBlockEntity(new ChestBlockEntity(currBlockPos, chestBlockState));
                            BlockEntity chestBlockEntity = chunk.getBlockEntity(currBlockPos);
                        if (chestBlockEntity instanceof ChestBlockEntity) {
                            ((ChestBlockEntity)chestBlockEntity).setLootTable(LootTables.SIMPLE_DUNGEON_CHEST, random.nextLong());
                        }
                    } else if (d <= (this.radius - this.shellRadius)) {
                        chunk.setBlockState(currBlockPos, Blocks.CAVE_AIR.getDefaultState(), false);
                    } else if (d <= this.radius) {
                        chunk.setBlockState(currBlockPos, this.shellBlock, false);
                    }
                }
            }
        }
    }

    @Override
    public String getDescription() {
        return "+++ DungeonSpheroid +++" +
                "\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
                "\nRadius: " + this.radius +
                "\nShellBlock: " + this.shellBlock +
                "\nShellRadius: " + this.shellRadius +
                "\nEntityType: " + this.entityType.getName();
    }
}
