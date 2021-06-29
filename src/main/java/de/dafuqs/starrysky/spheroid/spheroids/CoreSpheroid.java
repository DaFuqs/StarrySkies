package de.dafuqs.starrysky.spheroid.spheroids;

import de.dafuqs.starrysky.spheroid.SpheroidEntitySpawnDefinition;
import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;

public class CoreSpheroid extends Spheroid {

    //STATIC CONFIG STUFF
    private final BlockState coreBlock;
    private final BlockState shellBlock;
    private int coreRadius;

    public CoreSpheroid(ChunkRandom random, SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int radius, ArrayList<SpheroidDecorator> spheroidDecorators, ArrayList<SpheroidEntitySpawnDefinition> entityTypesToSpawn, BlockState coreBlock, BlockState shellBlock, int coreRadius) {
        super(spheroidAdvancementIdentifier, random, spheroidDecorators, radius, entityTypesToSpawn);
        this.coreBlock = coreBlock;
        this.shellBlock = shellBlock;
        this.coreRadius = coreRadius;

        if(this.coreRadius >= this.radius) {
            this.coreRadius = this.radius - 1;
        }
    }

    public String getDescription() {
        return "+++ CoreSpheroid +++" +
                "\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
                "\nRadius: " + this.radius +
                "\nShell: " + this.shellBlock.toString() +
                "\nCore: " +this.coreBlock.toString() + " (Radius: " + this.coreRadius + ")";
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
                    long d = Math.round(Support.getDistance(x, y, z, x2, y2, z2));
                    if (d <= this.coreRadius) {
                        chunk.setBlockState(currBlockPos, this.coreBlock, false);
                    } else if (d <= this.radius) {
                        chunk.setBlockState(currBlockPos, this.shellBlock, false);
                    }
                }
            }
        }
    }

}
