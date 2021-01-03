package de.dafuqs.starrysky.dimension.decorators.end;

import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.ArrayList;
import java.util.Random;

public class ChorusFruitDecorator extends SpheroidDecorator {

    private static final int chorusChance = 30;

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, ArrayList<BlockPos> decorationBlockPositions, Random random) {
        for(BlockPos bp : decorationBlockPositions) {
            int r = random.nextInt(chorusChance);

            if(r == 0) {
                ChorusFlowerBlock.generate(world, bp.up(), random, 8);
            }
        }
    }


}
