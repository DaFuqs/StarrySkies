package de.dafuqs.starrysky.spheroidtypes.special_overworld;

import de.dafuqs.starrysky.SpheroidData.SpheroidAdvancementGroup;
import de.dafuqs.starrysky.SpheroidData.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.spheroidtypes.SpheroidType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;
import java.util.List;

public class BeeHiveSpheroidType extends SpheroidType {

    private final List<BlockState> flowers = new ArrayList<BlockState>() {{
        add(Blocks.DANDELION.getDefaultState());
        add(Blocks.POPPY.getDefaultState());
        add(Blocks.BLUE_ORCHID.getDefaultState());
        add(Blocks.ALLIUM.getDefaultState());
        add(Blocks.AZURE_BLUET.getDefaultState());
        add(Blocks.ORANGE_TULIP.getDefaultState());
        add(Blocks.PINK_TULIP.getDefaultState());
        add(Blocks.RED_TULIP.getDefaultState());
        add(Blocks.WHITE_TULIP.getDefaultState());
        add(Blocks.OXEYE_DAISY.getDefaultState());
        add(Blocks.CORNFLOWER.getDefaultState());
        add(Blocks.LILY_OF_THE_VALLEY.getDefaultState());
        //add(Blocks.WITHER_ROSE.getDefaultState()); //That would be pretty fun, actually
        //add(Blocks.SUNFLOWER.getDefaultState()); //lacks top, only stem generates
        add(Blocks.LILAC.getDefaultState());
        add(Blocks.ROSE_BUSH.getDefaultState());
        add(Blocks.PEONY.getDefaultState());
    }};
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

    public BeeEntity getRandomQueen(ChunkRandom random, World world) {
        BeeEntity queen = new BeeEntity(EntityType.BEE, world);
        queen.setCustomName(new TranslatableText("bee.queen"));
        queen.setHealth(queen.getHealth() * (random.nextFloat()*3 + 1)); //way higher than default
        queen.setMovementSpeed((float) (queen.getMovementSpeed() * (random.nextFloat()* 0.5 + 0.5))); //slower than default
        queen.setAbsorptionAmount((float) (queen.getAbsorptionAmount() * (random.nextFloat()*1.5 + 1))); //higher than default
        queen.setGlowing(true);
        queen.setAngerTime(Integer.MAX_VALUE);
        return queen;
    }

    public BlockState getRandomFlower(ChunkRandom random) {
        return this.flowers.get(random.nextInt(flowers.size()));
    }

}
