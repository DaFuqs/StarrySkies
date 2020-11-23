package de.dafuqs.starrysky.spheroidtypes.special_overworld;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.spheroids.special_overworld.DungeonSpheroid;
import de.dafuqs.starrysky.spheroidtypes.SpheroidType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.world.gen.ChunkRandom;

import java.util.LinkedHashMap;
import java.util.Random;

public class DungeonSpheroidType extends SpheroidType {

    private final EntityType entityType;
    private final LinkedHashMap<BlockState, Float> validShellBlocks;
    private final int minShellRadius;
    private final int maxShellRadius;

    public DungeonSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, EntityType entityType, LinkedHashMap<BlockState, Float> validShellBlocks, int minRadius, int maxRadius, int minShellRadius, int maxShellRadius) {
        super();

        if(entityType == null) {
            StarrySkyCommon.LOGGER.error("DungeonSpheroidType: Registered a SpheroidType with null entity!");
        }

        this.spheroidAdvancementIdentifier = spheroidAdvancementIdentifier;
        this.entityType = entityType;
        this.validShellBlocks = validShellBlocks;
        this.minRadius = minRadius;
        this.maxRadius = maxRadius;
        this.minShellRadius = minShellRadius;
        this.maxShellRadius = maxShellRadius;
    }

    @Override
    public String getDescription() {
        return "DungeonSpheroid";
    }

    public EntityType getEntityType () {
        return this.entityType;
    }

    public BlockState getRandomShellBlock(ChunkRandom random) {
        return Support.getWeightedRandom(validShellBlocks, random);
    }

    public int getRandomShellRadius(Random random) {
        return random.nextInt(maxShellRadius - minShellRadius  + 1) + minShellRadius;
    }

    public DungeonSpheroid getRandomSphere(ChunkRandom chunkRandom) {
        return new DungeonSpheroid(this, chunkRandom);
    }

}
