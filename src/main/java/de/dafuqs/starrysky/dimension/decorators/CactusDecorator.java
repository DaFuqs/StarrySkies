package de.dafuqs.starrysky.dimension.decorators;

import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.ArrayList;
import java.util.Random;


public class CactusDecorator extends SpheroidDecorator {

    private static final Block cactusBlock = Blocks.CACTUS;
    private static final BlockState cactusBlockState = Blocks.CACTUS.getDefaultState();
    private static final int CACTUS_CHANCE = 40; // random. 0 = cactus with 1 height, 2 = 3 height

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, ArrayList<BlockPos> decorationBlockPositions, Random random) {
        for(BlockPos bp : decorationBlockPositions) {
            int r = random.nextInt(CACTUS_CHANCE);

            if(r < 4) {
                for(int i = 1; i < r; i++) {
                    if(cactusBlock.canPlaceAt(cactusBlockState, world, bp.up(i))) {
                        world.setBlockState(bp.up(i), cactusBlockState, 3);
                    }
                }
            }
        }
    }
}
