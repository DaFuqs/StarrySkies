package de.dafuqs.starrysky.spheroid.decorators;

import de.dafuqs.starrysky.spheroid.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class SugarCanePondDecorator extends SpheroidDecorator {

    private static final Block SUGAR_CANE_BLOCK = Blocks.SUGAR_CANE;
    private static final BlockState SUGAR_CANE_BLOCKSTATE = Blocks.SUGAR_CANE.getDefaultState();
    private static final int WATER_POND_TRIES  = 3;
    private static final int SUGAR_CANE_CHANCE = 2;

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, ArrayList<BlockPos> decorationBlockPositions, Random random) {
        if(decorationBlockPositions.size() > 0) {
            int currentTries = 0;
            boolean canGenerate;
            do {
                BlockPos randomBlockPos = decorationBlockPositions.get(random.nextInt(decorationBlockPositions.size()));

                // check if all 4 sides of the future water pond are solid
                canGenerate = true;
                Iterator<Direction> direction = Direction.Type.HORIZONTAL.iterator();
                while(direction.hasNext() && canGenerate) {
                    BlockPos currentCheckBlockPos = randomBlockPos.offset(direction.next());

                    if (!world.getBlockState(currentCheckBlockPos).isSolidBlock(world, currentCheckBlockPos)
                            || !world.getBlockState(currentCheckBlockPos.up()).isAir()) {
                        canGenerate = false;
                    }
                }

                if (canGenerate) {
                    world.setBlockState(randomBlockPos, Blocks.WATER.getDefaultState(), 3);

                    // place sugar cane with chance
                    direction = Direction.Type.HORIZONTAL.iterator();
                    while(direction.hasNext()) {
                        Direction currentDirection = direction.next();
                        if (random.nextInt(SUGAR_CANE_CHANCE) == 0) {
                            BlockPos sugarCaneBlockPos = randomBlockPos.up().offset(currentDirection);
                            int sugarCaneHeight = random.nextInt(3);
                            for (int i = 0; i <= sugarCaneHeight; i++) {
                                if (SUGAR_CANE_BLOCK.canPlaceAt(SUGAR_CANE_BLOCKSTATE, world, sugarCaneBlockPos.up(i))) {
                                    world.setBlockState(sugarCaneBlockPos.up(i), SUGAR_CANE_BLOCKSTATE, 3);
                                }
                            }
                        }
                    }
                }
                currentTries++;
            } while (currentTries < WATER_POND_TRIES);
        }
    }
}
