package de.dafuqs.starrysky.dimension.decorators;

import de.dafuqs.starrysky.dimension.DecorationMode;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.dimension.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.Random;


public class SingleBlockDecorator extends SpheroidDecorator {

    private final BlockState BLOCK_STATE;
    private final float CHANCE;
    private boolean REQUIRES_AIR;

    /**
     * A chance of 0 = 0%, 1.0 = 100%
     */
    public SingleBlockDecorator(BlockState plantBlockState, float plantChance) {
        BLOCK_STATE = plantBlockState;
        CHANCE = plantChance;
        REQUIRES_AIR = false;
    }

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, BlockPos origin, Random random) {
        for(BlockPos bp : getDecorationPositionsInChunk(spheroid, world, origin, random, CHANCE, DecorationMode.TOP)) {
            if (!world.getBlockState(bp.down()).isAir() && world.getBlockState(bp).isAir()) {
                if(!REQUIRES_AIR || surroundedByAir(world, bp)) {
                    world.setBlockState(bp, BLOCK_STATE, 3);
                }

            }
        }
    }

    public SingleBlockDecorator requiresAirAroundOfIt() {
        this.REQUIRES_AIR = true;
        return this;
    }

}
