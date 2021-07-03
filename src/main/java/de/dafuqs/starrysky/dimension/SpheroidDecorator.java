package de.dafuqs.starrysky.dimension;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.dimension.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public abstract class SpheroidDecorator {

    /**
     * In contrast to vanilla the spheroid decorators are queried by the spheroid
     * not ran after the chunk generation
     * The spheroid tracks all blocks that can be decorated and the decorator
     * takes them and checks for spawning criteria
     */
    public abstract void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, BlockPos blockPos, Random random);

    protected void placeLootChestAtPosition(StructureWorldAccess world, BlockPos blockPos, Identifier lootTable, Random random) {
        BlockState chestBlockState = Blocks.CHEST.getDefaultState();

        // if the chest is placed in water: water log it
        if(world.getBlockState(blockPos) == Blocks.WATER.getDefaultState()) {
            chestBlockState = chestBlockState.with(ChestBlock.WATERLOGGED, true);
        }

        // Random direction placement for the chest
        int r = random.nextInt(4);
        Direction randomDirection;
        switch (r) {
            case 0 -> {
                randomDirection = Direction.NORTH;
            }
            case 1 -> {
                randomDirection = Direction.SOUTH;
            }
            case 2 -> {
                randomDirection = Direction.EAST;
            }
            default -> {
                randomDirection = Direction.WEST;
            }
        }

        // set the chest and add loot table
        world.setBlockState(blockPos, chestBlockState.with(ChestBlock.FACING, randomDirection), 3);
        BlockEntity chestBlockEntity = world.getBlockEntity(blockPos);
        if (chestBlockEntity instanceof ChestBlockEntity) {
            ((ChestBlockEntity) chestBlockEntity).setLootTable(lootTable, random.nextLong());
        }
    }

    protected BlockPos findNextNonAirBlockInDirection(StructureWorldAccess world, BlockPos blockPos, Direction direction, int maxBlocks) {
        for(int i = 0; i < maxBlocks; i++) {
            if(!world.getBlockState(blockPos.offset(direction, i)).isAir()) {
                return blockPos;
            }
        }
        return null;
    }

    protected List<BlockPos> getDecorationPositionsInChunk(Spheroid spheroid, StructureWorldAccess world, BlockPos origin, Random random, float chance, DecorationMode decorationMode) {
        ArrayList<BlockPos> blockPosList = new ArrayList<>();
        if(random == null || chance <= 0F) {
            return blockPosList;
        } else if(decorationMode == DecorationMode.CENTER) {
            if(spheroid.isCenterInChunkBlockPos(origin)) {
                blockPosList.add(spheroid.getPosition());
            }
            return blockPosList;
        } else if(decorationMode == DecorationMode.TOP_CENTER) {
            if(spheroid.isCenterInChunkBlockPos(origin)) {
                blockPosList.add(spheroid.getPosition().add(0, spheroid.getRadius() + 1, 0));
            }
            return blockPosList;
        } else if(decorationMode == DecorationMode.BOTTOM_CENTER) {
            if(spheroid.isCenterInChunkBlockPos(origin)) {
                blockPosList.add(spheroid.getPosition().add(0, -spheroid.getRadius() - 1, 0));
            }
            return blockPosList;
        } else {
            int y = 0;
            for(int x = origin.getX(); x < origin.getX() + 16; x++) {
                for(int z = origin.getZ(); z < origin.getZ() + 16; z++) {
                    if (chance >= 1 || random.nextFloat() < chance) {
                        if(isSpheroidAtCoords(spheroid, x, z)) {
                            switch (decorationMode) {
                                case TOP -> {
                                    y = Support.getLowerGroundBlock(world, new BlockPos(x, spheroid.getPosition().getY() + spheroid.getRadius(), z), spheroid.getPosition().getY());
                                    if(y == spheroid.getPosition().getY()) {
                                        continue;
                                    }
                                }
                                case BOTTOM -> {
                                    y = Support.getUpperGroundBlock(world, new BlockPos(x, spheroid.getPosition().getY() - spheroid.getRadius(), z), spheroid.getPosition().getY());
                                    if(y == spheroid.getPosition().getY()) {
                                        continue;
                                    }
                                }
                                case CAVE_BOTTOM -> {
                                    BlockPos startPos = new BlockPos(x, spheroid.getPosition().getY(), z);
                                    int minHeight = spheroid.getPosition().getY() - spheroid.getRadius();
                                    if(world.isAir(startPos) || !world.getFluidState(startPos).isEmpty()) { // when we are in the shell: continue
                                        y = Support.getLowerGroundBlock(world, startPos, minHeight);
                                    } else {
                                        continue;
                                    }
                                    if(y == minHeight) {
                                        continue;
                                    }
                                }
                                case CAVE_TOP -> {
                                    BlockPos startPos = new BlockPos(x, spheroid.getPosition().getY(), z);
                                    int maxHeight = spheroid.getPosition().getY() + spheroid.getRadius();
                                    if(world.isAir(startPos) || !world.getFluidState(startPos).isEmpty()) { // when we are in the shell: skip
                                        y = Support.getUpperGroundBlock(world, startPos, maxHeight);
                                    } else {
                                        continue;
                                    }
                                    if(y == maxHeight) {
                                        continue;
                                    }
                                }
                            }
                            blockPosList.add(new BlockPos(x, y, z));
                        }
                    }
                }
            }
            return blockPosList;
        }
    }

    protected boolean isSpheroidAtCoords(Spheroid spheroid, int x, int z) {
        return Support.getSquaredDistance(x, 0, z, spheroid.getPosition().getX(), 0, spheroid.getPosition().getZ()) <= spheroid.getRadius() * spheroid.getRadius();
    }


    public boolean surroundedByAir(StructureWorldAccess world, BlockPos pos) {
        Iterator<Direction> directionIterator = Direction.Type.HORIZONTAL.iterator();
        do {
            Direction direction = directionIterator.next();
            BlockState blockState = world.getBlockState(pos.offset(direction));
            Material material = blockState.getMaterial();

            if(material.isSolid()) {
                return false;
            }
        } while(directionIterator.hasNext());

        return true;
    }

}
