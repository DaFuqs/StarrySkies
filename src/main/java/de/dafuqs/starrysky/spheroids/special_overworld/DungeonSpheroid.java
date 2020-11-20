package de.dafuqs.starrysky.spheroids.special_overworld;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.spheroids.Spheroid;
import de.dafuqs.starrysky.spheroidtypes.special_overworld.DungeonSpheroidType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootTables;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

public class DungeonSpheroid extends Spheroid {

    private EntityType entityType;
    private BlockState shellBlock;
    private int shellRadius;

    public DungeonSpheroid(DungeonSpheroidType dungeonSpheroidType, ChunkRandom random) {
        super(dungeonSpheroidType, random);

        this.radius = dungeonSpheroidType.getRandomRadius(random);
        this.entityType = dungeonSpheroidType.getEntityType();
        this.shellBlock = dungeonSpheroidType.getRandomShellBlock(random);
        this.shellRadius = dungeonSpheroidType.getRandomShellRadius(random);
    }


    @Override
    public void generate(Chunk chunk) {
        int chunkX = chunk.getPos().x;
        int chunkZ = chunk.getPos().z;

        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        int z = this.getPosition().getZ();

        random.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
        for (int x2 = Math.max(chunkX * 16, x - this.radius); x2 <= Math.min(chunkX * 16 + 15, x + this.radius); x2++) {
            for (int y2 = y - this.radius; y2 <= y + this.radius; y2++) {
                for (int z2 = Math.max(chunkZ * 16, z - this.radius); z2 <= Math.min(chunkZ * 16 + 15, z + this.radius); z2++) {
                    BlockPos currBlockPos = new BlockPos(x2, y2, z2);
                    long d = Math.round(Support.distance(x, y, z, x2, y2, z2));
                    if(d == 0) {
                        chunk.setBlockState(currBlockPos, Blocks.SPAWNER.getDefaultState(), false);
                        chunk.setBlockEntity(currBlockPos, new MobSpawnerBlockEntity());
                        BlockEntity blockEntity_1 = chunk.getBlockEntity(currBlockPos);
                        if (blockEntity_1 instanceof MobSpawnerBlockEntity) {
                            ((MobSpawnerBlockEntity) blockEntity_1).getLogic().setEntityId(this.entityType);
                        }
                    } else if (d == (this.radius - this.shellRadius -1) &&
                            Math.round(Support.distance(x, y, z, x2, y2-1, z2)) == (this.radius - this.shellRadius) &&
                            random.nextInt(radius * 8) == 0) {
                        chunk.setBlockState(currBlockPos, Blocks.CHEST.getDefaultState(), false);
                        chunk.setBlockEntity(currBlockPos, new ChestBlockEntity());
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

        this.setChunkFinished(chunk.getPos());
    }

    @Override
    public String getDescription() {
        return this.getSpheroidType().getDescription() +
                "\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
                "\nRadius: " + this.radius +
                "\nShellBlock: " + this.shellBlock +
                "\nShellRadius: " + this.shellRadius +
                "\nEntityType: " + this.entityType.getName();
    }
}
