package de.dafuqs.starrysky.spheroids;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.spheroidtypes.ShellSpheroidType;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

public class ShellSpheroid extends Spheroid {

    //STATIC CONFIG STUFF
    protected BlockState coreBlock;
    protected BlockState shellBlock;
    protected int shellRadius;

    public ShellSpheroid(ShellSpheroidType shellSpheroidType, ChunkRandom random) {
        super(shellSpheroidType, random);
        this.radius = shellSpheroidType.getRandomRadius(random);
        this.coreBlock = shellSpheroidType.getCoreBlock();
        this.shellBlock = shellSpheroidType.getRandomShellBlock(random);
        this.shellRadius = shellSpheroidType.getRandomShellRadius(random);
    }

    public String getDescription() {
        return this.spheroidType.getDescription() +
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
        for (int x2 = Math.max(chunkX * 16, x - this.radius); x2 <= Math.min(chunkX * 16 + 15, x + this.radius); x2++) {
            for (int y2 = y - this.radius; y2 <= y + this.radius; y2++) {
                for (int z2 = Math.max(chunkZ * 16, z - this.radius); z2 <= Math.min(chunkZ * 16 + 15, z + this.radius); z2++) {
                    BlockPos currBlockPos = new BlockPos(x2, y2, z2);
                    long d = Math.round(Support.distance(x, y, z, x2, y2, z2));
                    if (d <= (this.radius - this.shellRadius)) {
                        chunk.setBlockState(currBlockPos, this.coreBlock, false);
                    } else if (d <= this.radius) {
                        chunk.setBlockState(currBlockPos, this.shellBlock, false);
                        if(isTopBlock(d, x2, y2, z2)) {
                            this.decorationBlocks.add(currBlockPos);
                        }
                    }
                }
            }
        }

        this.setChunkFinished(chunk.getPos());
    }

    protected boolean isAboveCaveFloorBlock(long d, double x, double y, double z) {
        if(d == (this.radius - this.shellRadius)) {
            int distance1 = (int) Math.round(Support.distance(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), x, y-1, z));
            return distance1 > (this.radius - this.shellRadius);
        } else {
            return false;
        }
    }

}
