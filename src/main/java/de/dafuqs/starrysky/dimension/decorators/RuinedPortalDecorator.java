package de.dafuqs.starrysky.dimension.decorators;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;

import java.util.ArrayList;
import java.util.Random;


public class RuinedPortalDecorator extends SpheroidDecorator {

    private final Identifier lootTable;
    private final BlockState NETHERRACK = Blocks.NETHERRACK.getDefaultState();
    private final BlockState MAGMA_BLOCK = Blocks.MAGMA_BLOCK.getDefaultState();
    private final BlockState LAVA = Blocks.LAVA.getDefaultState();
    private final BlockState OBSIDIAN = Blocks.OBSIDIAN.getDefaultState();

    public RuinedPortalDecorator(Identifier lootTable) {
        this.lootTable = lootTable;
    }

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, ArrayList<BlockPos> decorationBlockPositions, Random random) {
        BlockPos spheroidPosition = spheroid.getPosition();

        // place floor
        for (int x = -spheroid.getRadius(); x <= spheroid.getRadius(); x++) {
            for (int z = -spheroid.getRadius(); z <= spheroid.getRadius(); z++) {

                int startY = spheroidPosition.getY() + spheroid.getRadius() + 1;
                int upperY = Support.getLowerGroundBlock(world, new BlockPos(spheroidPosition.getX() + x, startY, spheroidPosition.getZ() + z), spheroidPosition.getY());

                if(upperY > spheroidPosition.getY()) {
                    int randomI = random.nextInt(spheroid.getRadius() + 1);
                    if(Math.abs(x*z) * 1.5 < randomI * randomI) {
                        BlockPos currentBlockPos = new BlockPos(spheroidPosition.getX() + x, upperY, spheroidPosition.getZ() + z);
                        switch (random.nextInt(6)) {
                            case 0:
                                world.setBlockState(currentBlockPos, MAGMA_BLOCK, 3);
                                break;
                            case 1:
                                world.setBlockState(currentBlockPos, LAVA, 3);
                                break;
                            default:
                                world.setBlockState(currentBlockPos, NETHERRACK, 3);
                                break;
                        }
                    }
                }
            }
        }

        // place portal
        int centerTopBlockY = Support.getLowerGroundBlock(world, new BlockPos(spheroidPosition.getX(), spheroidPosition.getY() + spheroid.getRadius() + 1, spheroidPosition.getZ()), spheroidPosition.getY());
        BlockPos currentBlockPos = new BlockPos(spheroidPosition.getX(), centerTopBlockY, spheroidPosition.getZ());

        world.setBlockState(currentBlockPos, OBSIDIAN, 3);
        world.setBlockState(currentBlockPos.offset(Direction.SOUTH, 1), OBSIDIAN, 3);
        world.setBlockState(currentBlockPos.offset(Direction.NORTH, 1), OBSIDIAN, 3);
        world.setBlockState(currentBlockPos.offset(Direction.SOUTH, 2), OBSIDIAN, 3);
        world.setBlockState(currentBlockPos.offset(Direction.NORTH, 2), OBSIDIAN, 3);

        world.setBlockState(currentBlockPos.offset(Direction.SOUTH, 2).up(), OBSIDIAN, 3);
        world.setBlockState(currentBlockPos.offset(Direction.NORTH, 2).up(), OBSIDIAN, 3);
        world.setBlockState(currentBlockPos.offset(Direction.SOUTH, 2).up(1), OBSIDIAN, 3);
        world.setBlockState(currentBlockPos.offset(Direction.NORTH, 2).up(1), OBSIDIAN, 3);
        world.setBlockState(currentBlockPos.offset(Direction.SOUTH, 2).up(2), OBSIDIAN, 3);
        world.setBlockState(currentBlockPos.offset(Direction.NORTH, 2).up(2), OBSIDIAN, 3);
        world.setBlockState(currentBlockPos.offset(Direction.SOUTH, 2).up(3), OBSIDIAN, 3);
        world.setBlockState(currentBlockPos.offset(Direction.NORTH, 2).up(3), OBSIDIAN, 3);
        world.setBlockState(currentBlockPos.offset(Direction.SOUTH, 2).up(4), OBSIDIAN, 3);
        world.setBlockState(currentBlockPos.offset(Direction.NORTH, 2).up(4), OBSIDIAN, 3);

        world.setBlockState(currentBlockPos.up(5), OBSIDIAN, 3);
        world.setBlockState(currentBlockPos.offset(Direction.SOUTH, 1).up(5), OBSIDIAN, 3);
        world.setBlockState(currentBlockPos.offset(Direction.NORTH, 1).up(5), OBSIDIAN, 3);
        world.setBlockState(currentBlockPos.offset(Direction.SOUTH, 2).up(5), OBSIDIAN, 3);
        world.setBlockState(currentBlockPos.offset(Direction.NORTH, 2).up(5), OBSIDIAN, 3);

        // place loot chest
        int randomX = Support.getRandomBetween(random, spheroidPosition.getX() - spheroid.getRadius() / 2, spheroidPosition.getX() + spheroid.getRadius() / 2);
        int randomZ = Support.getRandomBetween(random, spheroidPosition.getZ() - spheroid.getRadius() / 2, spheroidPosition.getZ() + spheroid.getRadius() / 2);
        centerTopBlockY = Support.getLowerGroundBlock(world, new BlockPos(randomX, spheroidPosition.getY() + spheroid.getRadius() + 2, randomZ), spheroidPosition.getY());

        if(centerTopBlockY != spheroidPosition.getY()) {
            BlockPos lootChestPosition = new BlockPos(randomX, centerTopBlockY, randomZ).up();
            placeLootChestAtPosition(world, lootChestPosition, lootTable, random);
        }
    }
}