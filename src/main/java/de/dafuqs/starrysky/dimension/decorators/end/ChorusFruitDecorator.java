package de.dafuqs.starrysky.dimension.decorators.end;

import de.dafuqs.starrysky.dimension.DecorationMode;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.dimension.spheroid.spheroids.Spheroid;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.Random;

public class ChorusFruitDecorator extends SpheroidDecorator {

    private static final float chance = 0.033F;

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, BlockPos origin, Random random) {
        for(BlockPos bp : getDecorationPositionsInChunk(spheroid, world, origin, random, chance, DecorationMode.TOP)) {
            ChorusFlowerBlock.generate(world, bp, random, 8);
        }
    }

}
