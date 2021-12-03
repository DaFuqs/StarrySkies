package de.dafuqs.starrysky.spheroid.decorators;

import de.dafuqs.starrysky.spheroid.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.ArrayList;
import java.util.Random;


public class HangingCaveBlockDecorator extends SpheroidDecorator {

    private final BlockState BLOCKSTATE;
    private final float CHANCE;

    /**
     * A chance of 0 = 0%, 1.0 = 100%
     */
    public HangingCaveBlockDecorator(BlockState blockState, float chance) {
        BLOCKSTATE = blockState;
        CHANCE = chance;
    }

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, ArrayList<BlockPos> decorationBlockPositions, Random random) {
        int spheroidY = spheroid.getPosition().getY();
        for(BlockPos bp : decorationBlockPositions) {
            if(random.nextFloat() < CHANCE) {
                BlockPos currentPos = new BlockPos(bp.getX(), spheroidY, bp.getZ());
                for (int i = 0; i < spheroid.getRadius(); i++) {
                    if (!world.getBlockState(currentPos.up(i)).isAir()) {
                        if (world.getBlockState(currentPos.up(i - 1)).isAir()) {
                            world.setBlockState(currentPos.up(i - 1), BLOCKSTATE, 3);
                        }
                        break;
                    }
                }
            }
        }
    }
}
