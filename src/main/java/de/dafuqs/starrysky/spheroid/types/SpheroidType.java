package de.dafuqs.starrysky.spheroid.types;

import de.dafuqs.starrysky.spheroid.SpheroidEntitySpawnDefinition;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class SpheroidType {

    protected SpheroidAdvancementIdentifier spheroidAdvancementIdentifier;
    protected int minRadius;
    protected int maxRadius;
    protected LinkedHashMap<SpheroidDecorator, Float> spheroidDecorators = new LinkedHashMap<>();
    protected LinkedHashMap<SpheroidEntitySpawnDefinition, Float> spawnableEntities = new LinkedHashMap<>();

    protected SpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int minRadius, int maxRadius) {
        this.spheroidAdvancementIdentifier = spheroidAdvancementIdentifier;
        this.minRadius = minRadius;
        this.maxRadius = maxRadius;
    }

    public int getRandomRadius(ChunkRandom random) {
        int randomInt = random.nextInt(this.maxRadius - this.minRadius + 1);
        return randomInt + this.minRadius;
    }

    public SpheroidType addDecorator(SpheroidDecorator spheroidDecorator, float decoratorChance) {
        spheroidDecorators.put(spheroidDecorator, decoratorChance);
        return this;
    }

    public SpheroidType addSpawn(SpheroidEntitySpawnDefinition entityType, float spawnChance) {
        spawnableEntities.put(entityType, spawnChance);
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

    public abstract Spheroid getRandomSphere(ChunkRandom chunkRandom);

    protected ArrayList<SpheroidEntitySpawnDefinition> getRandomEntityTypesToSpawn(ChunkRandom chunkRandom) {
        ArrayList<SpheroidEntitySpawnDefinition> entityTypes = new ArrayList<>();
        for(Map.Entry<SpheroidEntitySpawnDefinition, Float> spawnableEntitiy : spawnableEntities.entrySet()) {
            if(chunkRandom.nextFloat() < spawnableEntitiy.getValue()) {
                entityTypes.add(spawnableEntitiy.getKey());
            }
        }
        return entityTypes;
    }

}
