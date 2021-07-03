package de.dafuqs.starrysky.dimension.decorators.end;

import de.dafuqs.starrysky.StarrySkyDimensionTravelHandler;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.dimension.spheroid.spheroids.Spheroid;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.Random;

public class EndGatewayDecorator extends SpheroidDecorator {

    @Override
    public void decorateSpheroid(StructureWorldAccess world, Spheroid spheroid, BlockPos origin, Random random) {
        if(!spheroid.isCenterInChunkBlockPos(origin)) {
            return;
        }

        BlockPos exitBlockPos = StarrySkyDimensionTravelHandler.END_SPAWN_BLOCK_POS;
        BlockPos portalBlockPos = spheroid.getPosition();

        for (BlockPos blockPos : BlockPos.iterate(portalBlockPos.add(-1, -2, -1), portalBlockPos.add(1, 2, 1))) {
            boolean bl = blockPos.getX() == portalBlockPos.getX();
            boolean bl2 = blockPos.getY() == portalBlockPos.getY();
            boolean bl3 = blockPos.getZ() == portalBlockPos.getZ();
            boolean bl4 = Math.abs(blockPos.getY() - portalBlockPos.getY()) == 2;
            if (bl && bl2 && bl3) {
                BlockPos blockPos3 = blockPos.toImmutable();

                world.setBlockState(blockPos3, Blocks.END_GATEWAY.getDefaultState(), 3);

                // set exit position
                BlockEntity blockEntity = world.getBlockEntity(blockPos3);
                if (blockEntity instanceof EndGatewayBlockEntity) {
                    EndGatewayBlockEntity endGatewayBlockEntity = (EndGatewayBlockEntity) blockEntity;
                    endGatewayBlockEntity.setExitPortalPos(exitBlockPos, false);
                    blockEntity.markDirty();
                }

            } else if (bl2) {
                world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 3);
            } else if (bl4 && bl && bl3) {
                world.setBlockState(blockPos, Blocks.BEDROCK.getDefaultState(), 3);
            } else if ((bl || bl3) && !bl4) {
                world.setBlockState(blockPos, Blocks.BEDROCK.getDefaultState(), 3);
            } else {
                world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 3);
            }
        }

    }

}
