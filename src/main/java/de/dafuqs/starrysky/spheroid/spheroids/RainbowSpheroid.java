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

public class RainbowSpheroid extends Spheroid {

    private final ArrayList<BlockState> rainbowBlocks;

    public RainbowSpheroid(ChunkRandom random, SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int radius, ArrayList<SpheroidDecorator> spheroidDecorators, ArrayList<SpheroidEntitySpawnDefinition> entityTypesToSpawn, ArrayList<BlockState> rainbowBlocks) {
        super(spheroidAdvancementIdentifier, random, spheroidDecorators, radius, entityTypesToSpawn);
        this.radius = radius;
        this.rainbowBlocks = rainbowBlocks;
    }

    @Override
    public String getDescription() {
        return "+++ RainbowSpheroid +++" +
                "\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
                "\nRadius: " + this.radius +
                "\nRainbow Blocks ( + " + this.getRainbowBlockCount() + "): " + this.rainbowBlocks.toString();
    }

    public int getRainbowBlockCount() {
        return this.rainbowBlocks.size();
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
                     if (d <= this.radius) {
                        int currentBlockID = (Math.abs(x2) /* 16* 16*/ + Math.abs(y2) /* *16 */ + Math.abs(z2)) % this.getRainbowBlockCount();
                        BlockState currentBlockState = this.rainbowBlocks.get(currentBlockID);
                        chunk.setBlockState(currBlockPos, currentBlockState, false);
                    }
                }
            }
        }

        this.setChunkFinished(chunk.getPos());
    }

}