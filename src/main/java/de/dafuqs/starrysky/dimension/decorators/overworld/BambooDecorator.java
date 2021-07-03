package de.dafuqs.starrysky.dimension.decorators.overworld;

import de.dafuqs.starrysky.dimension.DecorationMode;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.dimension.spheroid.spheroids.Spheroid;
import net.minecraft.block.BambooBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.BambooLeaves;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.Random;

public class BambooDecorator extends SpheroidDecorator {

    private final Block bambooBlock = Blocks.BAMBOO;
    private final int CHANCE = 13;
    private final BlockState bambooBlockState;
    private final BlockState bambooSaplingBlockState;

    public BambooDecorator(BlockState placementBlockState, BlockState bambooSaplingBlockState) {
        this.bambooBlockState = placementBlockState;
        this.bambooSaplingBlockState = bambooSaplingBlockState;
    }

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, BlockPos origin, Random random) {
        for(BlockPos bp : getDecorationPositionsInChunk(spheroid, world, origin, random, CHANCE, DecorationMode.TOP)) {
            int r = random.nextInt(CHANCE);

            if(r == 0) {
                if(bambooBlock.canPlaceAt(bambooBlockState, world, bp)) {
                    world.setBlockState(bp, bambooSaplingBlockState, 3);
                }
            } else if(r < 7) {
                for(int i = 0; i < r; i++) {
                    if(bambooBlock.canPlaceAt(bambooBlockState, world, bp.up(i))) {
                        if(i == 3 && r < 5) {
                            world.setBlockState(bp.up(i), bambooBlockState.with(BambooBlock.LEAVES, BambooLeaves.NONE), 3);
                        } else if(i > 4) {
                            world.setBlockState(bp.up(i), bambooBlockState.with(BambooBlock.LEAVES, BambooLeaves.LARGE), 3);
                        } else if(i > 2) {
                            world.setBlockState(bp.up(i), bambooBlockState.with(BambooBlock.LEAVES, BambooLeaves.SMALL), 3);
                        } else {
                            world.setBlockState(bp.up(i), bambooBlockState.with(BambooBlock.LEAVES, BambooLeaves.NONE), 3);
                        }
                    }
                }
            }
        }
    }
}
