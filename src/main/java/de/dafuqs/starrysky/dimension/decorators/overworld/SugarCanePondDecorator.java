package de.dafuqs.starrysky.dimension.decorators.overworld;

import de.dafuqs.starrysky.dimension.DecorationMode;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.dimension.spheroid.spheroids.Spheroid;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;

import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class SugarCanePondDecorator extends SpheroidDecorator {

    private static final Block SUGAR_CANE_BLOCK = Blocks.SUGAR_CANE;
    private static final BlockState SUGAR_CANE_BLOCK_STATE = Blocks.SUGAR_CANE.getDefaultState();
    private static final int WATER_POND_TRIES  = 3;
    private static final float SUGAR_CANE_CHANCE = 0.5F;

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, BlockPos origin, Random random) {
        List<BlockPos> decorationBlockPos = getDecorationPositionsInChunk(spheroid, world, origin, random, 0.1F, DecorationMode.TOP);

        int currentTries = 0;
        do {
            BlockPos randomBlockPos = decorationBlockPos.get(random.nextInt(decorationBlockPos.size()));

            // check if at least 2 sides of the future water pond are solid
            int neighboringSolidBlockCount = 0;
            Iterator<Direction> direction = Direction.Type.HORIZONTAL.iterator();
            while(direction.hasNext() && neighboringSolidBlockCount < 2) {
                BlockPos currentCheckBlockPos = randomBlockPos.offset(direction.next());

                if (world.getBlockState(currentCheckBlockPos).isSolidBlock(world, currentCheckBlockPos)) {
                    neighboringSolidBlockCount++;
                }
            }

            if (neighboringSolidBlockCount > 1) {
                world.setBlockState(randomBlockPos, Blocks.WATER.getDefaultState(), 3);
                world.getChunk(randomBlockPos).markBlockForPostProcessing(randomBlockPos);

                // place sugar cane with chance
                direction = Direction.Type.HORIZONTAL.iterator();
                while(direction.hasNext()) {
                    Direction currentDirection = direction.next();
                    if (random.nextFloat() < SUGAR_CANE_CHANCE) {
                        BlockPos sugarCaneBlockPos = randomBlockPos.up().offset(currentDirection);
                        int sugarCaneHeight = random.nextInt(3);
                        for (int i = 0; i <= sugarCaneHeight; i++) {
                            if (SUGAR_CANE_BLOCK.canPlaceAt(SUGAR_CANE_BLOCK_STATE, world, sugarCaneBlockPos.up(i))) {
                                world.setBlockState(sugarCaneBlockPos.up(i), SUGAR_CANE_BLOCK_STATE, 3);
                            }
                        }
                    }
                }
            }
            currentTries++;
        } while (currentTries < WATER_POND_TRIES);
    }

}
