package de.dafuqs.starrysky.spheroid.spheroids;

import de.dafuqs.starrysky.spheroid.SpheroidEntitySpawnDefinition;
import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;

public class MushroomSpheroid extends Spheroid {

    BlockState coreBlock;
    BlockState shellBlock;
    int shellRadius;

    public MushroomSpheroid(ChunkRandom random, SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int radius, ArrayList<SpheroidDecorator> spheroidDecorators, ArrayList<SpheroidEntitySpawnDefinition> entityTypesToSpawn, BlockState coreBlock, BlockState shellBlock, int shellRadius) {
        super(spheroidAdvancementIdentifier, random, spheroidDecorators, radius, entityTypesToSpawn);

        this.coreBlock = coreBlock;
        this.shellBlock = shellBlock;
        this.shellRadius = shellRadius;
    }

    public String getDescription() {
        return "+++ MushroomSpheroid +++" +
                "\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
                "\nRadius: " + this.radius +
                "\nShell: " + this.shellBlock.toString() + " (Radius: " + this.shellRadius + ")" +
                "\nCore: " +this.coreBlock.toString();
    }

    @Override
    public void generate(Chunk chunk) {
        int chunkX = chunk.getPos().x;
        int chunkZ = chunk.getPos().z;

        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        int z = this.getPosition().getZ();
        random.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);

        // see: HugeRedMushroomFeature
        BlockState placementBlockstateInner = this.shellBlock.with(Properties.UP, false).with(Properties.NORTH, false).with(Properties.EAST, false).with(Properties.SOUTH, false).with(Properties.WEST, false).with(Properties.DOWN, false);

        for (int x2 = Math.max(chunkX * 16, x - this.radius); x2 <= Math.min(chunkX * 16 + 15, x + this.radius); x2++) {
            for (int y2 = y - this.radius; y2 <= y + this.radius; y2++) {
                for (int z2 = Math.max(chunkZ * 16, z - this.radius); z2 <= Math.min(chunkZ * 16 + 15, z + this.radius); z2++) {
                    BlockPos currBlockPos = new BlockPos(x2, y2, z2);
                    double d = Support.distance(x, y, z, x2, y2, z2);
                    long rounded = Math.round(d);
                    if (rounded <= (this.radius - this.shellRadius)) {
                        chunk.setBlockState(currBlockPos, this.coreBlock, false);
                    } else if (d <= this.radius - 0.5) {
                        chunk.setBlockState(currBlockPos, placementBlockstateInner, false);
                    } else if (rounded <= this.radius) {
                        // not perfectly correct, but eh
                        BlockState placementBlockstateOuter = this.shellBlock.with(Properties.UP, true).with(Properties.NORTH, true).with(Properties.EAST, true).with(Properties.SOUTH, true).with(Properties.WEST, true).with(Properties.DOWN, true);
                        chunk.setBlockState(currBlockPos, placementBlockstateOuter, false);
                    }
                }
            }
        }
    }

}
