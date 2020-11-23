package de.dafuqs.starrysky.dimension;

import net.minecraft.util.math.BlockPos;

public class TempPosition {

    int xPos;
    int yPos;
    int zPos;

    public BlockPos toBlockPos() {
        return new BlockPos(xPos, yPos, zPos);
    }
}
