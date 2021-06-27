package de.dafuqs.starrysky.dimension.decorators.end;

import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.Blocks;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class EndPortalDecorator extends SpheroidDecorator {

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, ArrayList<BlockPos> decorationBlockPositions, Random random) {
        this.generate(world, new BlockPos(0, 63, 0), false);
    }

    public boolean generate(StructureWorldAccess structureWorldAccess, BlockPos blockPos, boolean open) {
        Iterator var6 = BlockPos.iterate(new BlockPos(blockPos.getX() - 4, blockPos.getY() - 1, blockPos.getZ() - 4), new BlockPos(blockPos.getX() + 4, blockPos.getY() + 32, blockPos.getZ() + 4)).iterator();

        while(true) {
            BlockPos blockPos2;
            boolean bl;
            do {
                if (!var6.hasNext()) {
                    for(int i = 0; i < 4; ++i) {
                        structureWorldAccess.setBlockState(blockPos.up(i), Blocks.BEDROCK.getDefaultState(), 3);
                    }

                    BlockPos blockPos3 = blockPos.up(2);
                    Iterator var11 = Direction.Type.HORIZONTAL.iterator();

                    while(var11.hasNext()) {
                        Direction direction = (Direction)var11.next();
                        structureWorldAccess.setBlockState(blockPos3.offset(direction), Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, direction), 3);
                    }

                    return true;
                }

                blockPos2 = (BlockPos)var6.next();
                bl = blockPos2.isWithinDistance(blockPos, 2.5D);
            } while(!bl && !blockPos2.isWithinDistance(blockPos, 3.5D));

            if (blockPos2.getY() < blockPos.getY()) {
                if (bl) {
                    structureWorldAccess.setBlockState(blockPos2, Blocks.BEDROCK.getDefaultState(), 3);
                } else if (blockPos2.getY() < blockPos.getY()) {
                    structureWorldAccess.setBlockState(blockPos2, Blocks.END_STONE.getDefaultState(), 3);
                }
            } else if (blockPos2.getY() > blockPos.getY()) {
                structureWorldAccess.setBlockState(blockPos2, Blocks.AIR.getDefaultState(), 3);
            } else if (!bl) {
                structureWorldAccess.setBlockState(blockPos2, Blocks.BEDROCK.getDefaultState(), 3);
            } else if (open) {
                structureWorldAccess.setBlockState(new BlockPos(blockPos2), Blocks.END_PORTAL.getDefaultState(), 3);
            } else {
                structureWorldAccess.setBlockState(new BlockPos(blockPos2), Blocks.AIR.getDefaultState(), 3);
            }
        }
    }
}
