package de.dafuqs.starrysky.spheroids;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.spheroiddecorators.SpheroidDecorator;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;

public class CaveSpheroid extends Spheroid {

    private final BlockState coreBlock = Blocks.CAVE_AIR.getDefaultState();
    private final BlockState caveFloorBlock;
    private final BlockState topBlock;
    private final BlockState bottomBlock;
    private final BlockState shellBlock;
    private final int shellRadius;

    public CaveSpheroid(ChunkRandom random, SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int radius, ArrayList<SpheroidDecorator> spheroidDecorators, BlockState caveFloorBlock, BlockState shellBlock, int shellRadius, BlockState topBlock, BlockState bottomBlock) {
        super(spheroidAdvancementIdentifier, random, spheroidDecorators, radius);

        this.caveFloorBlock = caveFloorBlock;
        this.shellBlock = shellBlock;
        this.shellRadius = shellRadius;
        this.topBlock = topBlock;
        this.bottomBlock = bottomBlock;
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
                        if (isBottomBlock(d, x2, y2, z2)) {
                            chunk.setBlockState(currBlockPos, this.bottomBlock, false);
                        } else if (isTopBlock(d, x2, y2, z2)) {
                            chunk.setBlockState(currBlockPos, this.topBlock, false);
                        } else {
                            chunk.setBlockState(currBlockPos, this.shellBlock, false);
                        }
                    } else if(isAboveCaveFloorBlock(d, x2, y2, z2, shellRadius)) {
                        chunk.setBlockState(currBlockPos.down(), this.caveFloorBlock, false);
                        addDecorationBlockPosition(currBlockPos.down());
                    } else if(d <= this.radius - this.shellRadius) {
                        chunk.setBlockState(currBlockPos, this.coreBlock, false); // always CAVE_AIR
                    } else if (d < this.radius) {
                        chunk.setBlockState(currBlockPos, this.shellBlock, false);
                    }
                }
            }
        }

        this.setChunkFinished(chunk.getPos());
    }

    @Override
    public String getDescription() {
        String s = "+++ CaveSpheroid +++" +
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
