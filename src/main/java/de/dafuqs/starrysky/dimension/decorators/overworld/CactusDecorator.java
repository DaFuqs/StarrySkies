package de.dafuqs.starrysky.dimension.decorators.overworld;

import de.dafuqs.starrysky.dimension.SpheroidDecorator;
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
    private BlockState placementBlockState;
    private static final int CACTUS_CHANCE = 40; // random. 0 = cactus with 1 height, 2 = 3 height

    public CactusDecorator(BlockState placementBlockState) {
        this.placementBlockState = placementBlockState;
    }

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, ArrayList<BlockPos> decorationBlockPositions, Random random) {
        for(BlockPos bp : decorationBlockPositions) {
            int r = random.nextInt(CACTUS_CHANCE);

            if(r < 4) {
                for(int i = 1; i < r; i++) {
                    if(cactusBlock.canPlaceAt(placementBlockState, world, bp.up(i))) {
                        world.setBlockState(bp.up(i), placementBlockState, 3);
                    }
                }
            }
        }
    }
}
