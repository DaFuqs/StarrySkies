package de.dafuqs.starrysky.decorators;

import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;
import java.util.Iterator;

import static de.dafuqs.starrysky.Support.isBlockPosInChunkPos;

public class SugarCanePondDecorator extends SpheroidDecorator {

    private static final Block SUGAR_CANE_BLOCK = Blocks.SUGAR_CANE;
    private static final BlockState SUGAR_CANE_BLOCKSTATE = Blocks.SUGAR_CANE.getDefaultState();
    private static final int WATER_POND_TRIES  = 3;
    private static final int SUGAR_CANE_CHANCE = 2;

    @Override
    public void decorateSpheroid(WorldView worldView, Chunk chunk, Spheroid spheroid, ArrayList<BlockPos> blockPos, ChunkRandom random) {

        blockPos = getDecorationBlockPosInChunk(chunk, blockPos);

        if(blockPos.size() > 0) {
            int currentTries = 0;
            boolean canGenerate;
            do {
                BlockPos randomBlockPos = blockPos.get(random.nextInt(blockPos.size()));

                // check if all 4 sides of the future water pond are solid
                canGenerate = true;
                Iterator<Direction> direction = Direction.Type.HORIZONTAL.iterator();
                while(direction.hasNext() && canGenerate) {
                    BlockPos currentCheckBlockPos = randomBlockPos.offset(direction.next());

                    if (!worldView.getBlockState(currentCheckBlockPos).isSolidBlock(worldView, currentCheckBlockPos)
                        || !worldView.getBlockState(currentCheckBlockPos.up()).isAir()) {
                        canGenerate = false;
                    }
                }

                if (canGenerate) {
                    chunk.setBlockState(randomBlockPos, Blocks.WATER.getDefaultState(), false);
                    chunk.markBlockForPostProcessing(randomBlockPos); //for making it drop down after generation

                    // place sugar cane with chance
                    direction = Direction.Type.HORIZONTAL.iterator();
                    while(direction.hasNext()) {
                        Direction currentDirection = direction.next();
                        if (random.nextInt(SUGAR_CANE_CHANCE) == 0) {
                            BlockPos sugarCaneBlockPos = randomBlockPos.up().offset(currentDirection);
                            if(isBlockPosInChunkPos(chunk.getPos(), sugarCaneBlockPos)) {
                                int sugarCaneHeight = random.nextInt(3);
                                for (int i = 0; i <= sugarCaneHeight; i++) {
                                    if (SUGAR_CANE_BLOCK.canPlaceAt(SUGAR_CANE_BLOCKSTATE, worldView, sugarCaneBlockPos.up(i))) {
                                        chunk.setBlockState(sugarCaneBlockPos.up(i), SUGAR_CANE_BLOCKSTATE, false);
                                    }
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
