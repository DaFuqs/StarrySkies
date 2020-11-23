package de.dafuqs.starrysky.spheroidtypes;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.spheroiddecorators.SpheroidDecorator;
import de.dafuqs.starrysky.spheroids.CoreSpheroid;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;
import java.util.LinkedHashMap;


public class CoreSpheroidType extends SpheroidType {

    private final BlockState coreBlock;
    private final LinkedHashMap<BlockState, Float> shellBlockStates;
    private final int minCoreRadius;
    private final int maxCoreRadius;

    public CoreSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int minRadius, int maxRadius, BlockState coreBlock, BlockState shellBlock, int minCoreRadius, int maxCoreRadius) {
        this(spheroidAdvancementIdentifier, minRadius, maxRadius, coreBlock, new LinkedHashMap<BlockState, Float>(){{put(shellBlock, 1.0F);}}, minCoreRadius, maxCoreRadius);
    }

    public CoreSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int minRadius, int maxRadius, BlockState coreBlock, LinkedHashMap<BlockState, Float> shellBlockStates, int minCoreRadius, int maxCoreRadius) {
        super(spheroidAdvancementIdentifier, minRadius, maxRadius);

        if(coreBlock == null) {
            StarrySkyCommon.LOGGER.error("CoreSpheroidType: Registered a SpheroidType with null coreBlock!");
        }
        if(shellBlockStates == null || shellBlockStates.size() == 0) {
            StarrySkyCommon.LOGGER.error("CoreSpheroidType: Registered a SpheroidType with empty shellBlockStates!");
        }

        this.coreBlock = coreBlock;
        this.shellBlockStates = shellBlockStates;
        this.minCoreRadius = minCoreRadius;
        this.maxCoreRadius = maxCoreRadius;
    }

    public String getDescription() {
        return "CoreSpheroid";
    }

    public CoreSpheroid getRandomSphere(ChunkRandom chunkRandom) {
        int radius = getRandomRadius(chunkRandom);
        ArrayList<SpheroidDecorator> spheroidDecorators = getSpheroidDecoratorsWithChance(chunkRandom);
        BlockState shellBlockState = Support.getWeightedRandom(shellBlockStates, chunkRandom);
        int coreRadius = chunkRandom.nextInt(this.maxCoreRadius - this.minCoreRadius + 1) + this.minCoreRadius;

        return new CoreSpheroid(chunkRandom, spheroidAdvancementIdentifier, radius, spheroidDecorators, coreBlock, shellBlockState, coreRadius);
    }

}