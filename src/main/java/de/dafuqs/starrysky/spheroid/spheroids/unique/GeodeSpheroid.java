package de.dafuqs.starrysky.spheroid.spheroids.unique;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.SpheroidEntitySpawnDefinition;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;

public class GeodeSpheroid extends Spheroid {

    private final BlockState innerBlockState;
    private final BlockState innerSpecklesBlockState;
    private final float speckleChance;
    private final BlockState middleBlockSate;
    private final BlockState outerBlockState;

    public GeodeSpheroid(ChunkRandom random, SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int radius, ArrayList<SpheroidDecorator> spheroidDecorators, ArrayList<SpheroidEntitySpawnDefinition> entityTypesToSpawn, BlockState innerBlockState, BlockState innerSpecklesBlockState, float speckleChance, BlockState middleBlockSate, BlockState outerBlockState) {
        super(spheroidAdvancementIdentifier, random, spheroidDecorators, radius, entityTypesToSpawn);

        this.innerBlockState = innerBlockState;
        this.innerSpecklesBlockState = innerSpecklesBlockState;
        this.speckleChance = speckleChance;
        this.middleBlockSate = middleBlockSate;
        this.outerBlockState = outerBlockState;
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
                    float d = Math.round(Support.getDistance(x, y, z, x2, y2, z2));
                    if (!(d < radius - 4)) {
                        if (d < radius -3) {
                            if(random.nextFloat() < speckleChance) {
                                chunk.setBlockState(currBlockPos, innerSpecklesBlockState, false);
                            } else {
                                chunk.setBlockState(currBlockPos, innerBlockState, false);
                            }
                        } else if (d < radius -2) {
                            chunk.setBlockState(currBlockPos, middleBlockSate, false);
                        } else if (d < radius -1) {
                            chunk.setBlockState(currBlockPos, outerBlockState, false);
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getDescription() {
        return "+++ GeodeSpheroid +++" +
                "\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
                "\nRadius: " + this.radius +
                "\nInnerBlock: " + this.innerBlockState +
                "\nInnerSpecklesBlock: " + this.innerSpecklesBlockState +
                "\nSpeckleChance: " + this.speckleChance +
                "\nMiddleBlock: " + this.middleBlockSate +
                "\nOuterBlock: " + this.outerBlockState;
    }

}
