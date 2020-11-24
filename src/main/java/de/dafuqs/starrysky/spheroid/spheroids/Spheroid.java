package de.dafuqs.starrysky.spheroid.spheroids;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.decorators.SpheroidDecorator;
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

    protected SpheroidAdvancementIdentifier spheroidAdvancementIdentifier;
    protected BlockPos position;
    protected int radius;
    protected ChunkRandom random;

    /** Chunks this spheroid should be still generated in  **/
    private final HashSet<ChunkPos> chunksOfSpheroid = new HashSet<>();
    /** The decorators that should be ran after generation **/
    private final ArrayList<SpheroidDecorator> spheroidDecorators;
    /** The tracker for blocks to be decorated. Filled in generate() **/
    private final ArrayList<BlockPos> decorationBlockPositions = new ArrayList<>();

    public Spheroid(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, ChunkRandom random, ArrayList<SpheroidDecorator> spheroidDecorators, int radius) {
        this.spheroidAdvancementIdentifier = spheroidAdvancementIdentifier;
        this.random = random;
        this.spheroidDecorators = spheroidDecorators;
        this.radius = radius;
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
                this.chunksOfSpheroid.add(new ChunkPos(cx, cz));
            }
        }
    }

    public SpheroidAdvancementIdentifier getSpheroidAdvancementIdentifier() {
        return spheroidAdvancementIdentifier;
    }

    public int getRadius() {
        return radius;
    }

    public abstract String getDescription();

    public boolean isInChunk(ChunkPos chunkPos) {
        return this.chunksOfSpheroid.contains(chunkPos);
    }

    public boolean hasDecorators() {
        return this.spheroidDecorators.size() > 0;
    }

    public void addDecorationBlockPosition(BlockPos blockPos) {
        if(hasDecorators()) {
            this.decorationBlockPositions.add(blockPos);
        }
    }

    // just a small optimization
    public void setChunkFinished(ChunkPos chunkPos) {
        this.chunksOfSpheroid.remove(chunkPos);
    }

    public boolean shouldPopulateEntities(ChunkPos chunkPos) {
        return false;
    }

    public void populateEntities(ChunkPos chunkPos, ChunkRegion chunkRegion, ChunkRandom chunkRandom) { }

    public void decorate(WorldView worldView, Chunk chunk) {
        for(SpheroidDecorator decorator : this.spheroidDecorators) {
            decorator.decorateSpheroid(worldView, chunk, this, this.decorationBlockPositions, this.random);
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

    // TODO: check
    protected boolean isAboveCaveFloorBlock(long d, double x, double y, double z, int shellRadius) {
        int distance1 = (int) Math.round(Support.distance(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), x, y-1, z));
        return d == (this.radius -shellRadius +1) && distance1 > (this.radius -shellRadius +1);
    }

}
