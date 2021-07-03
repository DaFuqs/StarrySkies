package de.dafuqs.starrysky.dimension.spheroid.types.unique;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.dimension.spheroid.SpheroidEntitySpawnDefinition;
import de.dafuqs.starrysky.dimension.spheroid.spheroids.unique.GeodeSpheroid;
import de.dafuqs.starrysky.dimension.spheroid.types.SpheroidType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;

import static org.apache.logging.log4j.Level.ERROR;

public class GeodeSpheroidType extends SpheroidType {

    private final BlockState innerBlockState;
    private final BlockState innerSpecklesBlockState;
    private final float speckleChance;
    private final BlockState middleBlockSate;
    private final BlockState outerBlockState;

    public GeodeSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int minRadius, int maxRadius, BlockState innerBlockState, BlockState innerSpecklesBlockState, float speckleChance, BlockState middleBlockSate, BlockState outerBlockState) {
        super(spheroidAdvancementIdentifier, minRadius, maxRadius);

        if(innerBlockState == Blocks.AIR.getDefaultState()) {
            StarrySkyCommon.log(ERROR, "GeodeSpheroidType: Registered a SpheroidType with null innerBlockState!");
        }
        if(innerSpecklesBlockState == Blocks.AIR.getDefaultState()) {
            StarrySkyCommon.log(ERROR, "GeodeSpheroidType: Registered a SpheroidType with empty innerSpecklesBlockState!");
        }
        if(middleBlockSate == Blocks.AIR.getDefaultState()) {
            StarrySkyCommon.log(ERROR, "GeodeSpheroidType: Registered a SpheroidType with null middleBlockSate!");
        }
        if(outerBlockState == Blocks.AIR.getDefaultState()) {
            StarrySkyCommon.log(ERROR, "GeodeSpheroidType: Registered a SpheroidType with empty outerBlockState!");
        }

        this.innerBlockState = innerBlockState;
        this.innerSpecklesBlockState = innerSpecklesBlockState;
        this.speckleChance = speckleChance;
        this.middleBlockSate = middleBlockSate;
        this.outerBlockState = outerBlockState;
    }

    @Override
    public String getDescription() {
        return "GeodeSpheroid";
    }

    public GeodeSpheroid getRandomSpheroid(ChunkRandom chunkRandom) {
        int radius = getRandomRadius(chunkRandom);

        ArrayList<SpheroidDecorator> spheroidDecorators = getSpheroidDecoratorsWithChance(chunkRandom);
        ArrayList<SpheroidEntitySpawnDefinition> entityTypesToSpawn = getRandomEntityTypesToSpawn(chunkRandom);

        return new GeodeSpheroid(chunkRandom, spheroidAdvancementIdentifier, radius, spheroidDecorators, entityTypesToSpawn, innerBlockState, innerSpecklesBlockState, speckleChance, middleBlockSate, outerBlockState);
    }

}
