package de.dafuqs.starrysky.spheroids.special_overworld;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.spheroids.ShellSpheroid;
import de.dafuqs.starrysky.spheroidtypes.special_overworld.MushroomSpheroidType;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

public class MushroomSpheroid extends ShellSpheroid {

    public MushroomSpheroid(MushroomSpheroidType mushroomSpheroidType, ChunkRandom random) {
        super(mushroomSpheroidType, random);
    }

    public String getDescription() {
        return getSpheroidType().getDescription() +
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
                    } else if (d <= this.radius - 1.25) {
                        chunk.setBlockState(currBlockPos, placementBlockstateInner, false);
                    } else if (rounded <= this.radius) {
                        boolean up    = y2 - y > 0;
                        boolean down  = y2 - y < 0;
                        boolean north = z2 - z < 0;
                        boolean east  = x2 - x > 0;
                        boolean south = z2 - z > 0;
                        boolean west  = x2 - x < 0;

                        BlockState placementBlockstateOuter = this.shellBlock.with(Properties.UP, up).with(Properties.NORTH, north).with(Properties.EAST, east).with(Properties.SOUTH, south).with(Properties.WEST, west).with(Properties.DOWN, down);
                        chunk.setBlockState(currBlockPos, placementBlockstateOuter, false);
                    }
                }
            }
        }

        this.setChunkFinished(chunk.getPos());
    }

}