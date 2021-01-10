package de.dafuqs.starrysky.dimension.decorators;

import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.BambooBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.BambooLeaves;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.ArrayList;
import java.util.Random;

public class BambooDecorator extends SpheroidDecorator {

    private static final Block bambooBlock = Blocks.BAMBOO;
    private static BlockState bambooBlockState;
    private static final BlockState bambooSaplingBlockState = Blocks.BAMBOO_SAPLING.getDefaultState();
    private static final int BAMBOO_CHANCE = 11;

    public BambooDecorator(BlockState placementBlockState) {
        this.bambooBlockState = placementBlockState;
    }

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, ArrayList<BlockPos> decorationBlockPositions, Random random) {
        for(BlockPos bp : decorationBlockPositions) {
            int r = random.nextInt(BAMBOO_CHANCE);

            if(r == 0) {
                if(bambooBlock.canPlaceAt(bambooBlockState, world, bp.up())) {
                    world.setBlockState(bp.up(), bambooSaplingBlockState, 3);
                }
            } else if(r < 8) {
                for(int i = 1; i < r; i++) {
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
