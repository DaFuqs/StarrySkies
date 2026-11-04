package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

public class WallAttachedCenterDecorator extends SphereDecorator<WallAttachedCenterDecoratorConfig> {

    private final BlockState AIR = Blocks.CAVE_AIR.defaultBlockState();

    public WallAttachedCenterDecorator(Codec<WallAttachedCenterDecoratorConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(SphereFeatureContext<WallAttachedCenterDecoratorConfig> context) {
        WorldGenLevel world = context.world();
        PlacedSphere<?> sphere = context.sphere();
        ChunkPos origin = context.chunkPos();

        if (!sphere.isCenterInChunk(origin)) {
            return false;
        }

        WallAttachedCenterDecoratorConfig config = context.config();

        for (int x = -2; x < 3; x++) {
            for (int y = -2; y < 3; y++) {
                for (int z = -2; z < 3; z++) {
                    BlockPos bp = sphere.getPosition().above(y).north(x).east(z);
                    if (y == 0 && ((Math.abs(x) == 2) && Math.abs(z) == 1 || (Math.abs(z) == 2 && Math.abs(x) == 1)) && context.random().nextFloat() < config.chance()) {
                        Direction direction = Direction.getNearest(x, y, z, Direction.NORTH).getCounterClockWise();
                        if (!config.facingOutwards()) {
                            direction = direction.getOpposite();
                        }
                        world.setBlockAndUpdate(bp, config.state().setValue(HorizontalDirectionalBlock.FACING, direction));
                    } else {
                        if (Math.abs(y) != 2 || (Math.abs(x) != 2 && Math.abs(z) != 2)) {
                            world.setBlockAndUpdate(bp, AIR);
                        }
                    }
                }
            }
        }

        return true;
    }

}
