package de.dafuqs.starrysky.spheroid.types;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.spheroid.SpheroidEntitySpawnDefinition;
import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.spheroids.MushroomSpheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;


/**
 * Very similar to ShellSpheroid
 * but uses the mushroom blocks "sides" properties
 */
public class MushroomSpheroidType extends SpheroidType {

    BlockState coreBlock;
    BlockState shellBlock;
    int minShellRadius;
    int maxShellRadius;

    public MushroomSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int minRadius, int maxRadius, BlockState coreBlock, BlockState shellBlock, int minShellRadius, int maxShellRadius) {
        super(spheroidAdvancementIdentifier, minRadius, maxRadius);

        this.coreBlock = coreBlock;
        this.shellBlock = shellBlock;
        this.minShellRadius = minShellRadius;
        this.maxShellRadius = maxShellRadius;

        if(coreBlock == Blocks.AIR.getDefaultState()) {
            StarrySkyCommon.LOGGER.error("MushroomSpheroidType: Registered a SpheroidType with null coreBlock!");
        }
        if(shellBlock == Blocks.AIR.getDefaultState()) {
            StarrySkyCommon.LOGGER.error("MushroomSpheroidType: Registered a SpheroidType with null shellBlock!");
        }
    }

    public String getDescription() {
        return "MushroomSpheroid";
    }

    public MushroomSpheroid getRandomSpheroid(ChunkRandom chunkRandom) {
        int radius = getRandomRadius(chunkRandom);
        int shellRadius = Support.getRandomBetween(chunkRandom, this.minShellRadius, this.maxShellRadius);

        ArrayList<SpheroidDecorator> spheroidDecorators = getSpheroidDecoratorsWithChance(chunkRandom);
        ArrayList<SpheroidEntitySpawnDefinition> entityTypesToSpawn = getRandomEntityTypesToSpawn(chunkRandom);

        return new MushroomSpheroid(chunkRandom, spheroidAdvancementIdentifier, radius, spheroidDecorators, entityTypesToSpawn, coreBlock, shellBlock, shellRadius);
    }

}