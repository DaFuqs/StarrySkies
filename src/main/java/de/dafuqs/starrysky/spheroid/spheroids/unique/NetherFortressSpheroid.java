package de.dafuqs.starrysky.spheroid.spheroids.unique;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.spheroid.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.SpheroidEntitySpawnDefinition;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootTables;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.random.ChunkRandom;

import java.util.ArrayList;
import java.util.Random;

public class NetherFortressSpheroid extends Spheroid {

    private final int shellRadius;
    private final ArrayList<BlockPos> interiorDecoratorPositions = new ArrayList<>();

    private final Identifier NETHER_BRIDGE_CHEST = LootTables.NETHER_BRIDGE_CHEST;

    private final BlockState NETHER_BRICKS = Blocks.NETHER_BRICKS.getDefaultState();
    private final BlockState AIR = Blocks.AIR.getDefaultState();
    private final BlockState NETHER_WART = Blocks.NETHER_WART.getDefaultState();
    private final BlockState SOUL_SAND = Blocks.SOUL_SAND.getDefaultState();
    private final BlockState NETHER_BRICK_FENCE = Blocks.NETHER_BRICK_FENCE.getDefaultState();
    private final BlockState LAVA = Blocks.LAVA.getDefaultState();


    public NetherFortressSpheroid(ChunkRandom random, SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int radius, ArrayList<SpheroidDecorator> spheroidDecorators, ArrayList<SpheroidEntitySpawnDefinition> entityTypesToSpawn, int shellRadius) {
        super(spheroidAdvancementIdentifier, random, spheroidDecorators, radius, entityTypesToSpawn);
        this.shellRadius = shellRadius;
    }

    @Override
    public String getDescription() {
        return "+++ NetherFortressSpheroid +++" +
                "\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
                "\nRadius: " + this.radius +
                "\nShellRadius: " + this.shellRadius;
    }

    @Override
    public void generate(Chunk chunk) {
        int chunkX = chunk.getPos().x;
        int chunkZ = chunk.getPos().z;

        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        int z = this.getPosition().getZ();

        int shellDistance = this.radius - this.shellRadius;

        random.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
        for (int x2 = Math.max(chunkX * 16, x - this.radius); x2 <= Math.min(chunkX * 16 + 15, x + this.radius); x2++) {
            for (int y2 = y - this.radius; y2 <= y + this.radius; y2++) {
                for (int z2 = Math.max(chunkZ * 16, z - this.radius); z2 <= Math.min(chunkZ * 16 + 15, z + this.radius); z2++) {

                    BlockPos currBlockPos = new BlockPos(x2, y2, z2);
                    long d = Math.round(Support.getDistance(x, y, z, x2, y2, z2));

                    if (d <= shellDistance) {
                        chunk.setBlockState(currBlockPos, NETHER_BRICKS, false);
                    }
                    if(d < this.getRadius() - 9 && (y2 % 10 == (this.position.getY() + 9) % 10 && x2 % 10 == (this.position.getX()) % 10 && z2 % 10 == (this.position.getZ()) % 10)) {
                        interiorDecoratorPositions.add(currBlockPos);
                    }
                }
            }
        }
    }

    /**
     * NetherFortressSpheroid uses the decorator to place all the
     * internal rooms more easily
     * @param world The world
     * @param random The decoration random
     */
    @Override
    public void decorate(StructureWorldAccess world, BlockPos origin, Random random) {
        for (BlockPos interiorDecoratorPosition : interiorDecoratorPositions) {
            int randomStructure = random.nextInt(7);
            switch (randomStructure) {
                case 0 -> placeBlazeSpawnerRoom(world, interiorDecoratorPosition);
                case 1 -> placeWitherSkeletonRoom(world, interiorDecoratorPosition);
                case 2 -> placeNetherWartRoom(world, interiorDecoratorPosition);
                case 3 -> placeSolid(world, interiorDecoratorPosition);
                case 4 -> placeEmpty(world, interiorDecoratorPosition);
                case 5 -> placeLava(world, interiorDecoratorPosition);
                default -> placeChestRoom(world, interiorDecoratorPosition);
            }
        }
    }

    private void placeSolid(WorldAccess worldAccess, BlockPos blockPos) {
        for (int x2 = - 4; x2 < 5; x2++) {
            for (int y2 = 0; y2 < 9; y2++) {
                for (int z2 = -4; z2 < 5; z2++) {
                    BlockPos destinationBlockPos = blockPos.add(x2, y2, z2);
                    worldAccess.setBlockState(destinationBlockPos, NETHER_BRICKS, 3);
                }
            }
        }
    }

    private void placeEmpty(WorldAccess worldAccess, BlockPos blockPos) {
        for (int x2 = - 4; x2 < 5; x2++) {
            for (int y2 = 0; y2 < 9; y2++) {
                for (int z2 = -4; z2 < 5; z2++) {
                    BlockPos destinationBlockPos = blockPos.add(x2, y2, z2);
                    worldAccess.setBlockState(destinationBlockPos, AIR, 3);
                }
            }
        }
    }

    private void placeLava(WorldAccess worldAccess, BlockPos blockPos) {
        for (int x2 = - 4; x2 < 5; x2++) {
            for (int y2 = 0; y2 < 9; y2++) {
                for (int z2 = -4; z2 < 5; z2++) {
                    BlockPos destinationBlockPos = blockPos.add(x2, y2, z2);
                    worldAccess.setBlockState(destinationBlockPos, LAVA, 3);
                }
            }
        }
    }

    private void placeChestRoom(WorldAccess worldAccess, BlockPos blockPos) {
        for (int x2 = - 4; x2 < 5; x2++) {
            for (int y2 = 0; y2 < 9; y2++) {
                for (int z2 = -4; z2 < 5; z2++) {
                    BlockPos destinationBlockPos = blockPos.add(x2, y2, z2);
                    worldAccess.setBlockState(destinationBlockPos, AIR, 3);
                }
            }
        }

        placeCenterChestWithLootTable(worldAccess.getChunk(blockPos), blockPos, NETHER_BRIDGE_CHEST, random, false);
    }

    private void placeBlazeSpawnerRoom(WorldAccess worldAccess, BlockPos blockPos) {
        for (int x2 = - 4; x2 < 5; x2++) {
            for (int y2 = 0; y2 < 9; y2++) {
                for (int z2 = -4; z2 < 5; z2++) {
                    BlockPos destinationBlockPos = blockPos.add(x2, y2, z2);
                    worldAccess.setBlockState(destinationBlockPos, AIR, 3);
                }
            }
        }

        BlockPos spawnerPos = blockPos.up(4);
        for (int x2 = -1; x2 < 2; x2++) {
            for (int y2 = -1; y2 < 2; y2++) {
                for (int z2 = -1; z2 < 2; z2++) {
                    BlockPos destinationBlockPos = spawnerPos.add(x2, y2, z2);
                    worldAccess.setBlockState(destinationBlockPos, NETHER_BRICK_FENCE, 3);
                }
            }
        }

        worldAccess.setBlockState(spawnerPos.up(2), NETHER_BRICK_FENCE, 3);
        worldAccess.setBlockState(spawnerPos.up(3), NETHER_BRICK_FENCE, 3);
        worldAccess.setBlockState(spawnerPos.up(4), NETHER_BRICK_FENCE, 3);

        placeSpawner(worldAccess, spawnerPos, EntityType.BLAZE);
    }

    private void placeWitherSkeletonRoom(WorldAccess worldAccess, BlockPos blockPos) {
        for (int x2 = - 4; x2 < 5; x2++) {
            for (int y2 = 0; y2 < 9; y2++) {
                for (int z2 = -4; z2 < 5; z2++) {
                    BlockPos destinationBlockPos = blockPos.add(x2, y2, z2);
                    worldAccess.setBlockState(destinationBlockPos, AIR, 3);
                }
            }
        }

        BlockPos spawnerPos = blockPos.up(4);
        for (int x2 = -1; x2 < 2; x2++) {
            for (int y2 = -1; y2 < 2; y2++) {
                for (int z2 = -1; z2 < 2; z2++) {
                    BlockPos destinationBlockPos = spawnerPos.add(x2, y2, z2);
                    worldAccess.setBlockState(destinationBlockPos, NETHER_BRICK_FENCE, 3);
                }
            }
        }

        worldAccess.setBlockState(spawnerPos.up(2), NETHER_BRICK_FENCE, 3);
        worldAccess.setBlockState(spawnerPos.up(3), NETHER_BRICK_FENCE, 3);
        worldAccess.setBlockState(spawnerPos.up(4), NETHER_BRICK_FENCE, 3);

        placeSpawner(worldAccess, spawnerPos, EntityType.WITHER_SKELETON);
    }

    private void placeNetherWartRoom(WorldAccess worldAccess, BlockPos blockPos) {
        for (int x2 = - 4; x2 < 5; x2++) {
            for (int y2 = 0; y2 < 9; y2++) {
                for (int z2 = -4; z2 < 5; z2++) {
                    BlockPos destinationBlockPos = blockPos.add(x2, y2, z2);
                    worldAccess.setBlockState(destinationBlockPos, AIR, 3);
                }
            }
        }

        for (int x2 = - 4; x2 < 5; x2++) {
            for (int z2 = -4; z2 < 5; z2++) {
                BlockPos destinationBlockPos = blockPos.add(x2, 0, z2);
                if(Math.abs(x2) < 3 && Math.abs(z2) < 3) {
                    worldAccess.setBlockState(destinationBlockPos, SOUL_SAND, 3);
                    int randomAge = random.nextInt(3);
                    worldAccess.setBlockState(destinationBlockPos.up(), NETHER_WART.with(NetherWartBlock.AGE, randomAge), 3);
                } else {
                    worldAccess.setBlockState(destinationBlockPos, NETHER_BRICKS, 3);
                }
            }
        }
    }



}
