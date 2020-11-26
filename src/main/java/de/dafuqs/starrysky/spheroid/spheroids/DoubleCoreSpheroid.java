package de.dafuqs.starrysky.spheroid.spheroids;

import de.dafuqs.starrysky.SpheroidEntitySpawnDefinition;
import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;

public class DoubleCoreSpheroid extends Spheroid {

    private final BlockState innerCoreBlock;
    private final BlockState outerCoreBlock;
    private final BlockState shellBlock;
    private final int innerCoreRadius;
    private final int shellRadius;

    public DoubleCoreSpheroid(ChunkRandom random, SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int radius, ArrayList<SpheroidDecorator> spheroidDecorators, ArrayList<SpheroidEntitySpawnDefinition> entityTypesToSpawn, BlockState innerCoreBlock, BlockState outerCoreBlock, BlockState shellBlock, int innerCoreRadius, int shellRadius) {
        super(spheroidAdvancementIdentifier, random, spheroidDecorators, radius, entityTypesToSpawn);
        this.innerCoreBlock  = innerCoreBlock;
        this.outerCoreBlock  = outerCoreBlock;
        this.shellBlock      = shellBlock;
        this.shellRadius     = shellRadius;

        if(radius <= shellRadius + innerCoreRadius) { //inner core radius <= 0
            this.innerCoreRadius = Math.max(1, radius - shellRadius - 2); //Reduce inner core up to a min of 1
        } else {
            this.innerCoreRadius = innerCoreRadius;
        }
    }

    @Override
    public String getDescription() {
        return  "+++ DoubleCoreSpheroid +++" +
                "\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
                "\nRadius: " + this.radius +
                "\nShell: " + this.shellBlock.toString() + " (Radius: " + this.shellRadius + ")" +
                "\nInner Core: " +this.innerCoreBlock.toString() + " (Radius: " + this.innerCoreRadius + ")" +
                "\nOuter Core: " +this.outerCoreBlock.toString();
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
                    if (d <= this.innerCoreRadius) {
                        chunk.setBlockState(currBlockPos, this.innerCoreBlock, false);
                    } else if (d <= this.radius - this.shellRadius) {
                        chunk.setBlockState(currBlockPos, this.outerCoreBlock, false);
                    } else if (d <= this.radius) {
                        chunk.setBlockState(currBlockPos, this.shellBlock, false);
                        if(isTopBlock(d, x2, y2, z2)) {
                            addDecorationBlockPosition(currBlockPos);
                        }
                    }
                }
            }
        }

        this.setChunkFinished(chunk.getPos());
    }

}
