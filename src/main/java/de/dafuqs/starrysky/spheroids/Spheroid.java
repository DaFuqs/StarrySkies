package de.dafuqs.starrysky.spheroids;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.spheroiddecorators.SpheroidDecorator;
import de.dafuqs.starrysky.spheroidtypes.SpheroidType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public abstract class Spheroid implements Serializable {

    protected SpheroidType spheroidType;

    /** Chunks this spheroid should be still generated in  **/
    protected HashSet<ChunkPos> unfinishedChunks = new HashSet<>();
    /** Chunks this spheroid was already generated in **/
    protected HashSet<ChunkPos> finishedChunks = new HashSet<>();

    /** The decorators that should be ran after generation **/
    protected ArrayList<SpheroidDecorator> spheroidDecorators;
    /** The tracker for blocks to be decorated. Filled in generate() **/
    protected ArrayList<BlockPos> decorationBlocks = new ArrayList<>();

    private BlockPos position; //Position, local to the chunk
    protected int radius;
    protected ChunkRandom random;


    public Spheroid(SpheroidType spheroidType, ChunkRandom random) {
        this.spheroidType = spheroidType;
        this.random = random;
        this.spheroidDecorators = spheroidType.getSpheroidDecoratorsWithChance(random);
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

    public void decorate(WorldView worldView, Chunk chunk) {
        for(SpheroidDecorator decorator : this.spheroidDecorators) {
            decorator.decorateSpheroid(worldView, chunk, this, this.decorationBlocks, this.random);
        }
    }

    protected boolean isTopBlock(long d, double x, double y, double z) {
        if(d == this.radius) {
            long dist2 = Math.round(Support.distance(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), x, y + 1, z));
            return dist2 > this.radius;
        } else {
            return false;
        }
    }

    protected boolean isBottomBlock(long d, double x, double y, double z) {
        if(d == this.radius) {
            long dist2 = Math.round(Support.distance(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), x, y - 1, z));
            return dist2 > this.radius;
        } else {
            return false;
        }
    }

}
