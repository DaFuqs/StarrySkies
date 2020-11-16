package de.dafuqs.starrysky.spheroids.special_overworld;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.spheroids.ShellSpheroid;
import de.dafuqs.starrysky.spheroidtypes.CaveSpheroidType;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

public class CaveSpheroid extends ShellSpheroid {

    private BlockState caveFloorBlock;
    private BlockState topBlock;
    private BlockState bottomBlock;

    public CaveSpheroid(CaveSpheroidType caveSpheroidType, ChunkRandom random) {
        super(caveSpheroidType, random);

        this.radius = caveSpheroidType.getRandomRadius(random);
        this.caveFloorBlock = caveSpheroidType.getCaveFloorBlock();
        this.shellRadius = caveSpheroidType.getRandomShellRadius(random);
        this.topBlock = caveSpheroidType.getTopBlock();
        this.bottomBlock = caveSpheroidType.getBottomBlock();
    }


    @Override
    public void generate(Chunk chunk) {
        int chunkX = chunk.getPos().x;
        int chunkZ = chunk.getPos().z;

        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        int z = this.getPosition().getZ();

        for (int x2 = Math.max(chunkX * 16, x - this.radius); x2 <= Math.min(chunkX * 16 + 15, x + this.radius); x2++) {
            for (int y2 = y - this.radius; y2 <= y + this.radius; y2++) {
                for (int z2 = Math.max(chunkZ * 16, z - this.radius); z2 <= Math.min(chunkZ * 16 + 15, z + this.radius); z2++) {
                    BlockPos currBlockPos = new BlockPos(x2, y2, z2);
                    long d = Math.round(Support.distance(x, y, z, x2, y2, z2));
                    if (d == this.radius) {
                        if (isBottomBlock(x2, y2, z2))
                            chunk.setBlockState(currBlockPos, this.bottomBlock, false);
                        else if (isTopBlock(x2, y2, z2))
                            chunk.setBlockState(currBlockPos, this.topBlock, false);
                        else
                            chunk.setBlockState(currBlockPos, this.shellBlock, false);
                    } else if (d < this.radius) {
                        if(isCaveFloorBlock(x2, y2, z2, y)) {
                            chunk.setBlockState(currBlockPos, caveFloorBlock, false);
                        } else {
                            chunk.setBlockState(currBlockPos, this.coreBlock, false); // always CAVE_AIR
                        }
                    }
                }
            }
        }

        this.setChunkFinished(chunk.getPos());
    }

    private boolean isCaveFloorBlock(double x, double y, double z, double y2) {
        int distance  = (int) Math.round(Support.distance(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), x, y, z));
        int distance1 = (int) Math.round(Support.distance(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), x, y-1, z));
        return distance == (this.radius -this.shellRadius +1) && distance1 > (this.radius -this.shellRadius +1);
    }

    private boolean isTopBlock(double x, double y, double z) {
        int distance  = (int) Math.round(Support.distance(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), x, y, z));
        int distance1 = (int) Math.round(Support.distance(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), x, y+1, z));
        return distance == this.radius && distance1 > this.radius;
    }

    private boolean isBottomBlock(double x, double y, double z) {
        int distance  = (int) Math.round(Support.distance(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), x, y, z));
        int distance1 = (int) Math.round(Support.distance(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), x, y-1, z));
        return distance >= this.radius && distance1 > this.radius;
    }


    @Override
    public String getDescription() {
        String s = this.getSpheroidType().getDescription() +
                "\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
                "\nRadius: " + this.radius +
                "\nShellBlock: " + this.shellBlock +
                "\nShellRadius: " + this.shellRadius +
                "\nCaveFloorBlock: " + this.caveFloorBlock;

        if(this.topBlock != null) {
            s += "\nTopBlock: " + this.topBlock.toString();
        }
        if(this.bottomBlock != null) {
            s += "\nBottomBlock: " + this.bottomBlock.toString();
        }
        return s;
    }

}
