package de.dafuqs.starrysky.dimension.decorators.end;

import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.ArrayList;
import java.util.Random;

public class ChorusFruitDecorator extends SpheroidDecorator {

    private static final float chorusChance = 0.033F;

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, Random random) {
        for(BlockPos bp : decorationBlockPositions) {
            if(random.nextFloat() < chorusChance) {
                ChorusFlowerBlock.generate(world, bp.up(), random, 8);
            }
        }
    }

}
