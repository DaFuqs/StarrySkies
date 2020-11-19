package de.dafuqs.starrysky.spheroidtypes;

import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import net.minecraft.world.gen.ChunkRandom;

public abstract class SpheroidType {

    protected SpheroidAdvancementIdentifier spheroidAdvancementIdentifier;
    protected int minRadius;
    protected int maxRadius;

    public int getRandomRadius(ChunkRandom random) {
        int randomInt = random.nextInt(this.maxRadius - this.minRadius + 1);
        return randomInt + this.minRadius;
    }

    public abstract String getDescription();

    public SpheroidAdvancementIdentifier getSpheroidTypeIdentifier() {
        return spheroidAdvancementIdentifier;
    }

}
