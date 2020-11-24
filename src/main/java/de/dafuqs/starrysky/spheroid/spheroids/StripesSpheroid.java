package de.dafuqs.starrysky.spheroid.spheroids;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.decorators.SpheroidDecorator;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;

public class StripesSpheroid extends Spheroid {

    private final ArrayList<BlockState> stripesBlockStates;

    public StripesSpheroid(ChunkRandom random, SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int radius, ArrayList<SpheroidDecorator> spheroidDecorators, ArrayList<BlockState> stripesBlockStates) {
        super(spheroidAdvancementIdentifier, random, spheroidDecorators, radius);
        this.stripesBlockStates = stripesBlockStates;
    }

    @Override
    public void generate(Chunk chunk) {
        int chunkX = chunk.getPos().x;
        int chunkZ = chunk.getPos().z;

        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        int z = this.getPosition().getZ();

        random.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
        for (int y2 = y - this.radius; y2 <= y + this.radius; y2++) {

            int currentSpheroidHeight = y - y2 + this.radius;
            int currentBlockStateIndex = (currentSpheroidHeight * stripesBlockStates.size()-1) / (this.radius * 2);

            if(currentBlockStateIndex >= stripesBlockStates.size()) {
                StarrySkyCommon.LOGGER.error(":(");
            }
            BlockState currentBlockState = this.stripesBlockStates.get(currentBlockStateIndex);

            for (int x2 = Math.max(chunkX * 16, x - this.radius); x2 <= Math.min(chunkX * 16 + 15, x + this.radius); x2++) {
                    for (int z2 = Math.max(chunkZ * 16, z - this.radius); z2 <= Math.min(chunkZ * 16 + 15, z + this.radius); z2++) {
                    BlockPos currBlockPos = new BlockPos(x2, y2, z2);
                    long d = Math.round(Support.distance(x, y, z, x2, y2, z2));
                     if (d < this.radius) {
                        chunk.setBlockState(currBlockPos, currentBlockState, false);
                    }
                }
            }
        }

        this.setChunkFinished(chunk.getPos());
    }

    @Override
    public String getDescription() {
        return "+++ StripesSpheroid +++" +
                "\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
                "\nRadius: " + this.radius +
                "\nStripes Blocks ( + " + stripesBlockStates.size() + "): " + this.stripesBlockStates.toString();
    }

}