package de.dafuqs.starrysky.spheroid.decorators;

import de.dafuqs.starrysky.spheroid.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;


public class DripleafDecorator extends SpheroidDecorator {
    
    private static final Block DRIPLEAF_BLOCK = Blocks.BIG_DRIPLEAF;
    private static final BlockState DRIPLEAF_BLOCK_STATE = Blocks.BIG_DRIPLEAF.getDefaultState();
    private static final BlockState DRIPLEAF_STEM_BLOCK_STATE = Blocks.BIG_DRIPLEAF_STEM.getDefaultState();
    private static final BlockState WATER_BLOCK_STATE = Blocks.WATER.getDefaultState();
    private static final BlockState CLAY_BLOCK_STATE = Blocks.CLAY.getDefaultState();
    
    private final int tries;
    
    public DripleafDecorator(int tries) {
        this.tries = tries;
    }
    
    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, @NotNull ArrayList<BlockPos> decorationBlockPositions, Random random) {
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
                    // clay
                    world.setBlockState(randomBlockPos, CLAY_BLOCK_STATE, 3);
    
                    // the dripleaf
                    Direction randomDirection = Direction.Type.HORIZONTAL.random(random);
                    int dripLeafHeight = random.nextInt(3) + 1;
                    for (int i = 0; i <= dripLeafHeight; i++) {
                        if (DRIPLEAF_BLOCK.canPlaceAt(DRIPLEAF_BLOCK_STATE.with(HorizontalFacingBlock.FACING, randomDirection), world, randomBlockPos.up(i))) {
                            if(i == dripLeafHeight) {
                                world.setBlockState(randomBlockPos.up(i), DRIPLEAF_BLOCK_STATE.with(HorizontalFacingBlock.FACING, randomDirection), 3);
                            } else {
                                world.setBlockState(randomBlockPos.up(i), DRIPLEAF_STEM_BLOCK_STATE.with(HorizontalFacingBlock.FACING, randomDirection), 3);
                            }
                            
                        }
                    }
                    
                    // surrounding water
                    direction = Direction.Type.HORIZONTAL.iterator();
                    while(direction.hasNext()) {
                        Direction currentDirection = direction.next();
                        BlockPos offsetPos = randomBlockPos.offset(currentDirection);
                        if(world.getBlockState(offsetPos.up()).isAir()) {
                            world.setBlockState(offsetPos, WATER_BLOCK_STATE, 3);
                        }
                    }
                    
                }
                currentTries++;
            } while (currentTries < tries);
        }
    }
}
