package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.registries.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;

public class EndGatewayDecorator extends SphereDecorator<SphereDecoratorConfig.DefaultSphereDecoratorConfig> {

	public EndGatewayDecorator(Codec<SphereDecoratorConfig.DefaultSphereDecoratorConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(SphereFeatureContext<SphereDecoratorConfig.DefaultSphereDecoratorConfig> context) {
		WorldGenLevel world = context.world();
		PlacedSphere<?> sphere = context.sphere();
		ChunkPos origin = context.chunkPos();

		if (!sphere.isCenterInChunk(origin)) {
			return false;
		}
		
		BlockPos exitBlockPos = StarryDimensionKeys.STARRY_END_SPAWN_BLOCK_POS;
		BlockPos portalBlockPos = sphere.getPosition();

		for (BlockPos blockPos2 : BlockPos.betweenClosed(portalBlockPos.offset(-1, -2, -1), portalBlockPos.offset(1, 2, 1))) {
			boolean bl = blockPos2.getX() == portalBlockPos.getX();
			boolean bl2 = blockPos2.getY() == portalBlockPos.getY();
			boolean bl3 = blockPos2.getZ() == portalBlockPos.getZ();
			boolean bl4 = Math.abs(blockPos2.getY() - portalBlockPos.getY()) == 2;
			if (bl && bl2 && bl3) {
				BlockPos blockPos3 = blockPos2.immutable();

				world.setBlock(blockPos3, Blocks.END_GATEWAY.defaultBlockState(), 3);

				// set exit position
				BlockEntity blockEntity = world.getBlockEntity(blockPos3);
				if (blockEntity instanceof TheEndGatewayBlockEntity endGatewayBlockEntity) {
					endGatewayBlockEntity.setExitPosition(exitBlockPos, false);
					blockEntity.setChanged();
				}

			} else if (bl2) {
				world.setBlock(blockPos2, Blocks.AIR.defaultBlockState(), 3);
			} else if (bl4 && bl && bl3) {
				world.setBlock(blockPos2, Blocks.BEDROCK.defaultBlockState(), 3);
			} else if ((bl || bl3) && !bl4) {
				world.setBlock(blockPos2, Blocks.BEDROCK.defaultBlockState(), 3);
			} else {
				world.setBlock(blockPos2, Blocks.AIR.defaultBlockState(), 3);
			}
		}

		return true;
	}

}
