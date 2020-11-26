package de.dafuqs.starrysky.dimension.decorators;

import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;

import java.util.ArrayList;
import java.util.Random;


/**
 * Creates a small X on one side of the spheroid
 * Puts a loot chest in the absolute center (could be fun on lava spheroids!)
 */
public class XMarksTheSpotDecorator extends SpheroidDecorator {

    private final Identifier lootTable;
    private final BlockState markBlockState;
    private final boolean[] theX = {
        true,  false, false, false,  true,
        false,  true, false,  true, false,
        false, false,  true, false, false,
        false,  true, false,  true, false,
        true, false,  false, false,  true
    };

    public XMarksTheSpotDecorator(Identifier lootTable, BlockState markBlockState) {
        this.lootTable = lootTable;
        this.markBlockState = markBlockState;
    }

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, ArrayList<BlockPos> decorationBlockPositions, Random random) {
        placeTreasureChestInCenter(world, spheroid, random);


        int r = random.nextInt(6);
        int amountOfXMarks = random.nextInt(2) + 1;
        for(int i = 0; i < amountOfXMarks; i++) {
            Direction randomDirection = Direction.values()[(r+i) % 6];
            paintXInDirection(world, spheroid, randomDirection);
        }
    }

    /**
     * Draws an "X" in a 5x5 pattern on a sphere.
     */
    private void paintXInDirection(StructureWorldAccess world, Spheroid spheroid, Direction direction) {
        int startX;
        int startY;
        int startZ;

        BlockPos spheroidPos = spheroid.getPosition();
        int radius = spheroid.getRadius();
        switch (direction) {
            case UP:
                startX = spheroidPos.getX() - 2;
                startY = spheroidPos.getY() - radius;
                startZ = spheroidPos.getZ() - 2;
                break;
            case DOWN:
                startX = spheroidPos.getX() - 2;
                startY = spheroidPos.getY() + radius;
                startZ = spheroidPos.getZ() - 2;
                break;
            case EAST:
                startX = spheroidPos.getX() - radius;
                startY = spheroidPos.getY() - 2;
                startZ = spheroidPos.getZ() - 2;
                break;
            case WEST:
                startX = spheroidPos.getX() + radius;
                startY = spheroidPos.getY() - 2;
                startZ = spheroidPos.getZ() - 2;
                break;
            case NORTH:
                startX = spheroidPos.getX() - 2;
                startY = spheroidPos.getY() - 2;
                startZ = spheroidPos.getZ() + spheroid.getRadius();
                break;
            default:
                startX = spheroidPos.getX() - 2;
                startY = spheroidPos.getY() - 2;
                startZ = spheroidPos.getZ() - spheroid.getRadius();
                break;
        }

        for(int i = -0; i < 5; i++) {
            for(int j = -0; j < 5; j++){
                if(theX[i*5+j]) {
                    BlockPos startBlockPos;
                    switch (direction) {
                        case UP:
                        case DOWN: {
                            startBlockPos = new BlockPos(startX + i, startY, startZ + j);
                            break;
                        }
                        case EAST:
                        case WEST: {
                            startBlockPos = new BlockPos(startX, startY + i, startZ + j);
                            break;
                        }
                        default: {
                            startBlockPos = new BlockPos(startX + i, startY + j, startZ);
                            break;
                        }
                    }
                    BlockPos currentBlockPos = findNextNonAirBlockInDirection(world, startBlockPos, direction, spheroid.getRadius());
                    if (currentBlockPos != null) {
                        world.setBlockState(currentBlockPos, markBlockState, 3);
                    }
                }
            }
        }

    }


    private BlockPos findNextNonAirBlockInDirection(StructureWorldAccess world, BlockPos blockPos, Direction direction, int maxBlocks) {
        for(int i = 0; i < maxBlocks; i++) {
            if(!world.getBlockState(blockPos.offset(direction, i)).isAir()) {
                return blockPos;
            }
        }
        return null;
    }

    private void placeTreasureChestInCenter(StructureWorldAccess world, Spheroid spheroid, Random random) {
        BlockState chestBlockState = Blocks.CHEST.getDefaultState();
        BlockPos chestBlockPos = spheroid.getPosition();

        // if the chest is placed in water: waterlog it!
        if(world.getBlockState(chestBlockPos) == Blocks.WATER.getDefaultState()) {
            chestBlockState = chestBlockState.with(ChestBlock.WATERLOGGED, true);
        }

        // Random direction placement for the chest
        int r = random.nextInt(4);
        Direction randomDirection;
        switch (r) {
            case 0: {
                randomDirection = Direction.NORTH;
                break;
            }
            case 1: {
                randomDirection = Direction.SOUTH;
                break;
            }
            case 2: {
                randomDirection = Direction.EAST;
                break;
            }
            default: {
                randomDirection = Direction.WEST;
                break;
            }
        }

        // set the chest and add loot table
        world.setBlockState(chestBlockPos, chestBlockState.with(ChestBlock.FACING, randomDirection), 3);
        BlockEntity chestBlockEntity = world.getBlockEntity(chestBlockPos);
        if (chestBlockEntity instanceof ChestBlockEntity) {
            ((ChestBlockEntity) chestBlockEntity).setLootTable(this.lootTable, random.nextLong());
        }
    }

}
