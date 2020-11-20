package de.dafuqs.starrysky.spheroids.special_overworld;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.spheroids.Spheroid;
import de.dafuqs.starrysky.spheroidtypes.special_overworld.CoralSpheroidType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

public class CoralSpheroid extends Spheroid {

    //STATIC CONFIG STUFF
    protected BlockState shellBlock;
    protected int shellRadius;
    protected BlockState WATER = Blocks.WATER.getDefaultState();

    public CoralSpheroid(CoralSpheroidType coralSpheroidType, ChunkRandom random) {
        super(coralSpheroidType, random);
        this.radius = coralSpheroidType.getRandomRadius(random);
        this.shellBlock = coralSpheroidType.getRandomShellBlock(random);
        this.shellRadius = coralSpheroidType.getRandomShellRadius(random);
    }

    @Override
    public void generate(Chunk chunk) {
        int chunkX = chunk.getPos().x;
        int chunkZ = chunk.getPos().z;

        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        int z = this.getPosition().getZ();

        CoralSpheroidType coralSpheroidType = (CoralSpheroidType) getSpheroidType();

        random.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
        for (int x2 = Math.max(chunkX * 16, x - this.radius); x2 <= Math.min(chunkX * 16 + 15, x + this.radius); x2++) {
            for (int y2 = y - this.radius; y2 <= y + this.radius; y2++) {
                for (int z2 = Math.max(chunkZ * 16, z - this.radius); z2 <= Math.min(chunkZ * 16 + 15, z + this.radius); z2++) {
                    BlockPos currBlockPos = new BlockPos(x2, y2, z2);
                    long d = Math.round(Support.distance(x, y, z, x2, y2, z2));
                    if (d <= (this.radius - this.shellRadius - 1)) {
                        int rand = random.nextInt(7);
                        if (rand < 2) {
                            BlockState coral = coralSpheroidType.getRandomCoralBlock(random);
                            if (rand == 0 && chunk.getBlockState(currBlockPos.down()).getBlock() == Blocks.WATER) {
                                chunk.setBlockState(currBlockPos.down(), coral, false);
                                chunk.setBlockState(currBlockPos, coralSpheroidType.getRandomWaterLoggableBlock(random), false);
                            } else {
                                chunk.setBlockState(currBlockPos, coral, false);
                            }
                        } else {
                            chunk.setBlockState(currBlockPos, WATER, false);
                        }
                    } else if (d <= (this.radius - this.shellRadius)) {
                        chunk.setBlockState(currBlockPos, WATER, false);
                    } else if (d <= this.radius) {
                        chunk.setBlockState(currBlockPos, this.shellBlock, false);
                    }
                }
            }
        }

        this.setChunkFinished(chunk.getPos());
    }

    public String getDescription() {
        return this.getSpheroidType().getDescription() +
                "\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
                "\nRadius: " + this.radius +
                "\nShell: " + this.shellBlock.toString() + " (Radius: " + this.shellRadius + ")";
    }

}
