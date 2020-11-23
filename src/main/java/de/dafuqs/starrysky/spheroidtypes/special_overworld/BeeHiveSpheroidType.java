package de.dafuqs.starrysky.spheroidtypes.special_overworld;

import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.spheroiddecorators.SpheroidDecorator;
import de.dafuqs.starrysky.spheroids.special_overworld.BeeHiveSpheroid;
import de.dafuqs.starrysky.spheroidtypes.SpheroidType;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;

public class BeeHiveSpheroidType extends SpheroidType {

    private final int minShellRadius;
    private final int maxShellRadius;
    private final int minFlowerRingRadius;
    private final int maxFlowerRingRadius;
    private final int minFlowerRingSpacing;
    private final int maxFlowerRingSpacing;

    public BeeHiveSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int minRadius, int maxRadius, int minShellRadius, int maxShellRadius, int minFlowerRingRadius, int maxFlowerRingRadius, int minFlowerRingSpacing, int maxFlowerRingSpacing) {
        super(spheroidAdvancementIdentifier, minRadius, maxRadius);

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

    public BeeHiveSpheroid getRandomSphere(ChunkRandom chunkRandom) {
        int radius = getRandomRadius(chunkRandom);
        ArrayList<SpheroidDecorator> spheroidDecorators = getSpheroidDecoratorsWithChance(chunkRandom);
        int shellRadius = chunkRandom.nextInt(maxShellRadius - minShellRadius  + 1) + minShellRadius;
        int flowerRingRadius = chunkRandom.nextInt(maxFlowerRingRadius - minFlowerRingRadius  + 1) + minFlowerRingRadius;
        int flowerRingSpacing = chunkRandom.nextInt(maxFlowerRingSpacing - minFlowerRingSpacing  + 1) + minFlowerRingSpacing;
        return new BeeHiveSpheroid(chunkRandom, spheroidAdvancementIdentifier, radius, spheroidDecorators, shellRadius, flowerRingRadius, flowerRingSpacing);
    }

}
