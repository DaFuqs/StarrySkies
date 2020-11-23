package de.dafuqs.starrysky.spheroidtypes.special_overworld;

import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.spheroidlists.SpheroidList;
import de.dafuqs.starrysky.spheroidtypes.SpheroidType;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.ChunkRandom;

public class BeeHiveSpheroidType extends SpheroidType {

    private final int minShellRadius;
    private final int maxShellRadius;
    private final int minFlowerRingRadius;
    private final int maxFlowerRingRadius;
    private final int minFlowerRingSpacing;
    private final int maxFlowerRingSpacing;


    public BeeHiveSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int minRadius, int maxRadius, int minShellRadius, int maxShellRadius, int minFlowerRingRadius, int maxFlowerRingRadius, int minFlowerRingSpacing, int maxFlowerRingSpacing) {
        super();

        this.spheroidAdvancementIdentifier = spheroidAdvancementIdentifier;
        this.minRadius = minRadius;
        this.maxRadius = maxRadius;
        this.minShellRadius = minShellRadius;
        this.maxShellRadius = maxShellRadius;
        this.minFlowerRingRadius = minFlowerRingRadius;
        this.maxFlowerRingRadius = maxFlowerRingRadius;
        this.minFlowerRingSpacing = minFlowerRingSpacing;
        this.maxFlowerRingSpacing = maxFlowerRingSpacing;
    }

    @Override
    public String getDescription() {
        return "BeeHiveSpheroid";
    }

    public int getRandomShellRadius(ChunkRandom random) {
        return random.nextInt(maxShellRadius - minShellRadius  + 1) + minShellRadius;
    }

    public int getRandomFlowerRingRadius(ChunkRandom random) {
        return random.nextInt(maxFlowerRingRadius - minFlowerRingRadius  + 1) + minFlowerRingRadius;
    }

    public int getRandomFlowerRingSpacing(ChunkRandom random) {
        return random.nextInt(maxFlowerRingSpacing - minFlowerRingSpacing  + 1) + minFlowerRingSpacing;
    }

    public BlockState getRandomFlower(ChunkRandom random) {
        return SpheroidList.LIST_FLOWERS.get(random.nextInt(SpheroidList.LIST_FLOWERS.size()));
    }

    public BlockState getRandomTallFlower(ChunkRandom random) {
        return SpheroidList.LIST_TALL_FLOWERS.get(random.nextInt(SpheroidList.LIST_TALL_FLOWERS.size()));
    }
}
