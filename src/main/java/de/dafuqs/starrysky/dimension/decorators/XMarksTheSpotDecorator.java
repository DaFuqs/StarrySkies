package de.dafuqs.starrysky.dimension.decorators;

import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.ArrayList;
import java.util.Random;


/**
 * Creates a small X on one side of the spheroid
 * Puts a loot chest in the absolute center (could be fun on lava spheroids!)
 */
public class XMarksTheSpotDecorator extends SpheroidDecorator {

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, ArrayList<BlockPos> decorationBlockPositions, Random random) {

    }
}
