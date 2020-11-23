package de.dafuqs.starrysky.spheroidtypes.special_overworld;

import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.spheroidtypes.SpheroidType;

import java.util.Random;

public class OceanMonumentSpheroidType extends SpheroidType {

    private final int minTreasureRadius;
    private final int maxTreasureRadius;
    private final int minShellRadius;
    private final int maxShellRadius;

    public OceanMonumentSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int minSize, int maxSize, int minTreasureRadius, int maxTreasureRadius, int minShellRadius, int maxShellRadius) {
        this.spheroidAdvancementIdentifier = spheroidAdvancementIdentifier;
        this.minRadius = minSize;
        this.maxRadius = maxSize;
        this.minTreasureRadius = minTreasureRadius;
        this.maxTreasureRadius = maxTreasureRadius;
        this.minShellRadius = minShellRadius;
        this.maxShellRadius = maxShellRadius;
    }

    public int getRandomTreasureRadius(Random random) {
        return random.nextInt(this.maxTreasureRadius - this.minTreasureRadius + 1) + this.minTreasureRadius;
    }

    public int getRandomShellRadius(Random random) {
        return random.nextInt(this.maxShellRadius - this.minShellRadius + 1) + this.minShellRadius;
    }

    public String getDescription() {
        return "OceanMonumentSpheroid";
    }



}
