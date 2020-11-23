package de.dafuqs.starrysky.spheroidtypes;

import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.spheroiddecorators.SpheroidDecorator;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class SpheroidType {

    protected SpheroidAdvancementIdentifier spheroidAdvancementIdentifier;
    protected int minRadius;
    protected int maxRadius;
    protected LinkedHashMap<SpheroidDecorator, Float> spheroidDecorators = new LinkedHashMap<>();

    public int getRandomRadius(ChunkRandom random) {
        int randomInt = random.nextInt(this.maxRadius - this.minRadius + 1);
        return randomInt + this.minRadius;
    }

    public SpheroidType addDecorator(SpheroidDecorator spheroidDecorator, float decoratorChance) {
        spheroidDecorators.put(spheroidDecorator, decoratorChance);
        return this;
    }

    public ArrayList<SpheroidDecorator> getSpheroidDecoratorsWithChance(ChunkRandom random) {
        ArrayList<SpheroidDecorator> resultingSpheroidDecorators = new ArrayList<>();
        for(Map.Entry<SpheroidDecorator, Float> spheroidDecorator : spheroidDecorators.entrySet()) {
            if(random.nextFloat() < spheroidDecorator.getValue()) {
                resultingSpheroidDecorators.add(spheroidDecorator.getKey());
            }
        }
        return resultingSpheroidDecorators;
    }

    public abstract String getDescription();

    public SpheroidAdvancementIdentifier getSpheroidTypeIdentifier() {
        return spheroidAdvancementIdentifier;
    }

}
