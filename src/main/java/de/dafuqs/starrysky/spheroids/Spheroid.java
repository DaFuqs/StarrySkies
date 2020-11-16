package de.dafuqs.starrysky.spheroids;

import de.dafuqs.starrysky.SpheroidData.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.spheroidtypes.SpheroidType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

import java.io.Serializable;
import java.util.HashSet;

public abstract class Spheroid implements Serializable {


    /** Chunks this spheroid should be still generated in  **/
    HashSet<ChunkPos> unfinishedChunks = new HashSet<>();
    /** Chunks this spheroid was already generated in **/
    HashSet<ChunkPos> finishedChunks = new HashSet<>();

    SpheroidType spheroidType;

    private BlockPos position; //Position, local to the chunk
    protected int radius;
    protected ChunkRandom random;


    public Spheroid(SpheroidType spheroidType, ChunkRandom random) {
        this.spheroidType = spheroidType;
        this.random = random;
    }

    public abstract void generate(Chunk chunk);

    public BlockPos getPosition() {
        return position;
    }

    public void setPositionAndCalculateGenerationChunks(BlockPos blockPos) {
        this.position = blockPos;

        for (int currXPos = blockPos.getX()-Math.round(radius); currXPos <= blockPos.getX()+Math.round(radius); currXPos++) {
            for (int currZPos = blockPos.getZ()-Math.round(radius); currZPos <= blockPos.getZ()+Math.round(radius); currZPos++) {
                int cx = (int) Math.floor(currXPos / 16.0D);
                int cz = (int) Math.floor(currZPos / 16.0D);
                addUnfinishedChunk(new ChunkPos(cx, cz));
            }
        }
    }

    public int getRadius() {
        return radius;
    }

    public SpheroidType getSpheroidType() {
        return this.spheroidType;
    }

    public abstract String getDescription();

    public boolean shouldFinishChunk(ChunkPos chunkPos) {
        return this.unfinishedChunks.contains(chunkPos);
    }

    public void setChunkFinished(ChunkPos chunkPos) {
        this.finishedChunks.add(chunkPos);
        this.unfinishedChunks.remove(chunkPos);
    }

    public boolean isFinished() {
        return this.unfinishedChunks.size() == 0;
    }

    public void addUnfinishedChunk(ChunkPos chunkPos) {
        this.unfinishedChunks.add(chunkPos);
    }

    public boolean shouldPopulateEntities(ChunkPos chunkPos) {
        return false;
    }

    public void populateEntities(ChunkPos chunkPos, ChunkRegion chunkRegion, ChunkRandom chunkRandom) {

    }
}
