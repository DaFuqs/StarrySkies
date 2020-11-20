package de.dafuqs.starrysky.spheroids;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.spheroidtypes.LiquidSpheroidType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

public class LiquidSpheroid extends Spheroid {

    private final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
    private final BlockState liquid;
    private final BlockState shellBlock;
    private final int shellRadius;
    private final int fillPercent;
    private final boolean holeInBottom;
    private final BlockState coreBlock;
    private int coreRadius;


    public LiquidSpheroid(LiquidSpheroidType liquidSpheroidType, ChunkRandom random) {
        super(liquidSpheroidType, random);
        this.radius = liquidSpheroidType.getRandomRadius(random);
        this.liquid = liquidSpheroidType.getLiquid();
        this.shellBlock = liquidSpheroidType.getRandomShellBlock(random);
        this.shellRadius = liquidSpheroidType.getRandomShellRadius(random);
        this.fillPercent = liquidSpheroidType.getRandomFillPercent(random);
        this.holeInBottom = liquidSpheroidType.getRandomHoleInBottom(random);
        this.coreBlock = liquidSpheroidType.getCoreBlock();
        if(this.coreBlock != null) {
            this.coreRadius = liquidSpheroidType.getRandomCoreRadius(random);
        } else {
            this.coreRadius = 0;
        }
        if(this.coreRadius >= this.radius - this.shellRadius - 1) {
            this.coreRadius = this.radius - this.shellRadius - 2;
        }
    }

    public String getDescription() {
        String s = this.spheroidType.getDescription() +
                "\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
                "\nRadius: " + this.radius +
                "\nShell: " + this.shellBlock.toString() + "(Radius: " + this.shellRadius  + ")" +
                "\nLiquid: " + this.liquid.toString();
        if(this.coreBlock != null) {
            s += "\nCore: " +this.coreBlock.toString() + "(Radius: " + this.coreRadius + ")";
        } else {
            s += "\nNo Core";
        }
        s +=     "\nFill Percent: " + this.fillPercent +
                "\nHole in bottom: " + this.holeInBottom;

        return s;
    }

    @Override
    public void generate(Chunk chunk) {
        int chunkX = chunk.getPos().x;
        int chunkZ = chunk.getPos().z;

        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        int z = this.getPosition().getZ();

        int liquidRadius = this.radius - this.shellRadius;
        float maxLiquidY = y + ((this.fillPercent / 100.0F) * liquidRadius * 2 - liquidRadius);

        random.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
        for (int x2 = Math.max(chunkX * 16, x - this.radius); x2 <= Math.min(chunkX * 16 + 15, x + this.radius); x2++) {
            for (int y2 = y - this.radius; y2 <= y + this.radius; y2++) {
                for (int z2 = Math.max(chunkZ * 16, z - this.radius); z2 <= Math.min(chunkZ * 16 + 15, z + this.radius); z2++) {
                    BlockPos currBlockPos = new BlockPos(x2, y2, z2);
                    long d = Math.round(Support.distance(x, y, z, x2, y2, z2));
                    if(this.holeInBottom && (x-x2) == 0 && (z-z2) == 0 && (y-y2+1) >= liquidRadius) {
                        chunk.setBlockState(new BlockPos(currBlockPos), this.liquid, false);
                        chunk.markBlockForPostProcessing(currBlockPos); //for making it drop down after generation
                    } else if (this.coreBlock != null && d <= this.coreRadius) {
                        chunk.setBlockState(currBlockPos, this.coreBlock, false);
                    } else if (d <= liquidRadius) {
                        if(y2 <= maxLiquidY) {
                            chunk.setBlockState(currBlockPos, this.liquid, false);
                            if(isAboveCaveFloorBlock(d, x2, y2, z2)) {
                                this.decorationBlocks.add(currBlockPos.down());
                            }
                        } else {
                            //air
                            chunk.setBlockState(currBlockPos, CAVE_AIR, false);
                        }
                    } else if (d <= this.radius) {
                        chunk.setBlockState(currBlockPos, this.shellBlock, false);
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
