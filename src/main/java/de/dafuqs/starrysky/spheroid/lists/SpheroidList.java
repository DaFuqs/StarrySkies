package de.dafuqs.starrysky.spheroid.lists;

import de.dafuqs.starrysky.decorators.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CoralParentBlock;
import net.minecraft.block.SeaPickleBlock;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public abstract class SpheroidList {

    //COMMONLY USED LISTS
    public static final LinkedHashMap<BlockState, Float> MAP_STONES = new LinkedHashMap<BlockState, Float>() {{
        put(Blocks.STONE.getDefaultState(), 5.0F);
        put(Blocks.GRANITE.getDefaultState(), 1.0F);
        put(Blocks.DIORITE.getDefaultState(), 1.0F);
        put(Blocks.ANDESITE.getDefaultState(), 1.0F);
        put(Blocks.COBBLESTONE.getDefaultState(), 0.1F);
        put(Blocks.MOSSY_COBBLESTONE.getDefaultState(), 0.005F);
        put(Blocks.INFESTED_STONE.getDefaultState(), 0.05F);
        put(Blocks.INFESTED_COBBLESTONE.getDefaultState(), 0.002F);
    }};

    public static final LinkedHashMap<BlockState, Float> MAP_DUNGEON_STONES = new LinkedHashMap<BlockState, Float>() {{
        put(Blocks.MOSSY_COBBLESTONE.getDefaultState(), 5.0F);
        put(Blocks.INFESTED_COBBLESTONE.getDefaultState(), 0.5F);
        put(Blocks.STONE.getDefaultState(), 1.0F);
        put(Blocks.GRANITE.getDefaultState(), 0.2F);
        put(Blocks.DIORITE.getDefaultState(), 0.2F);
        put(Blocks.ANDESITE.getDefaultState(), 0.2F);
        put(Blocks.COBBLESTONE.getDefaultState(), 0.1F);
        put(Blocks.INFESTED_STONE.getDefaultState(), 0.1F);
    }};

    public static final LinkedHashMap<BlockState, Float> MAP_GLASS = new LinkedHashMap<BlockState, Float>() {{
        put(Blocks.GLASS.getDefaultState(), 100F);
        put(Blocks.BLACK_STAINED_GLASS.getDefaultState(), 1.0F);
        put(Blocks.BLUE_STAINED_GLASS.getDefaultState(), 1.0F);
        put(Blocks.BROWN_STAINED_GLASS.getDefaultState(), 1.0F);
        put(Blocks.CYAN_STAINED_GLASS.getDefaultState(), 1.0F);
        put(Blocks.GRAY_STAINED_GLASS.getDefaultState(), 1.0F);
        put(Blocks.GREEN_STAINED_GLASS.getDefaultState(), 1.0F);
        put(Blocks.LIGHT_BLUE_STAINED_GLASS.getDefaultState(), 1.0F);
        put(Blocks.LIGHT_GRAY_STAINED_GLASS.getDefaultState(), 1.0F);
        put(Blocks.LIME_STAINED_GLASS.getDefaultState(), 1.0F);
        put(Blocks.MAGENTA_STAINED_GLASS.getDefaultState(), 1.0F);
        put(Blocks.ORANGE_STAINED_GLASS.getDefaultState(), 1.0F);
        put(Blocks.PINK_STAINED_GLASS.getDefaultState(), 1.0F);
        put(Blocks.PURPLE_STAINED_GLASS.getDefaultState(), 1.0F);
        put(Blocks.RED_STAINED_GLASS.getDefaultState(), 1.0F);
        put(Blocks.WHITE_STAINED_GLASS.getDefaultState(), 1.0F);
        put(Blocks.YELLOW_STAINED_GLASS.getDefaultState(), 1.0F);
    }};

    // RAINBOW STUFF
    public static final ArrayList<BlockState> LIST_WOOL = new ArrayList<BlockState>() {{
        add(Blocks.WHITE_WOOL.getDefaultState());
        add(Blocks.LIGHT_GRAY_WOOL.getDefaultState());
        add(Blocks.GRAY_WOOL.getDefaultState());
        add(Blocks.BLACK_WOOL.getDefaultState());
        add(Blocks.BROWN_WOOL.getDefaultState());
        add(Blocks.RED_WOOL.getDefaultState());
        add(Blocks.ORANGE_WOOL.getDefaultState());
        add(Blocks.YELLOW_WOOL.getDefaultState());
        add(Blocks.LIME_WOOL.getDefaultState());
        add(Blocks.GREEN_WOOL.getDefaultState());
        add(Blocks.CYAN_WOOL.getDefaultState());
        add(Blocks.LIGHT_BLUE_WOOL.getDefaultState());
        add(Blocks.BLUE_WOOL.getDefaultState());
        add(Blocks.PURPLE_WOOL.getDefaultState());
        add(Blocks.MAGENTA_WOOL.getDefaultState());
        add(Blocks.PINK_WOOL.getDefaultState());
    }};

    public static final ArrayList<BlockState> LIST_STAINED_GLASS = new ArrayList<BlockState>() {{
        add(Blocks.WHITE_STAINED_GLASS.getDefaultState());
        add(Blocks.LIGHT_GRAY_STAINED_GLASS.getDefaultState());
        add(Blocks.GRAY_STAINED_GLASS.getDefaultState());
        add(Blocks.BLACK_STAINED_GLASS.getDefaultState());
        add(Blocks.BROWN_STAINED_GLASS.getDefaultState());
        add(Blocks.RED_STAINED_GLASS.getDefaultState());
        add(Blocks.ORANGE_STAINED_GLASS.getDefaultState());
        add(Blocks.YELLOW_STAINED_GLASS.getDefaultState());
        add(Blocks.LIME_STAINED_GLASS.getDefaultState());
        add(Blocks.GREEN_STAINED_GLASS.getDefaultState());
        add(Blocks.CYAN_STAINED_GLASS.getDefaultState());
        add(Blocks.LIGHT_BLUE_STAINED_GLASS.getDefaultState());
        add(Blocks.BLUE_STAINED_GLASS.getDefaultState());
        add(Blocks.PURPLE_STAINED_GLASS.getDefaultState());
        add(Blocks.MAGENTA_STAINED_GLASS.getDefaultState());
        add(Blocks.PINK_STAINED_GLASS.getDefaultState());
    }};

    public static final ArrayList<BlockState> LIST_CONCRETE = new ArrayList<BlockState>() {{
        add(Blocks.WHITE_CONCRETE.getDefaultState());
        add(Blocks.LIGHT_GRAY_CONCRETE.getDefaultState());
        add(Blocks.GRAY_CONCRETE.getDefaultState());
        add(Blocks.BLACK_CONCRETE.getDefaultState());
        add(Blocks.BROWN_CONCRETE.getDefaultState());
        add(Blocks.RED_CONCRETE.getDefaultState());
        add(Blocks.ORANGE_CONCRETE.getDefaultState());
        add(Blocks.YELLOW_CONCRETE.getDefaultState());
        add(Blocks.LIME_CONCRETE.getDefaultState());
        add(Blocks.GREEN_CONCRETE.getDefaultState());
        add(Blocks.CYAN_CONCRETE.getDefaultState());
        add(Blocks.LIGHT_BLUE_CONCRETE.getDefaultState());
        add(Blocks.BLUE_CONCRETE.getDefaultState());
        add(Blocks.PURPLE_CONCRETE.getDefaultState());
        add(Blocks.MAGENTA_CONCRETE.getDefaultState());
        add(Blocks.PINK_CONCRETE.getDefaultState());
    }};

    public static final ArrayList<BlockState> LIST_TERRACOTTA = new ArrayList<BlockState>() {{
        add(Blocks.WHITE_TERRACOTTA.getDefaultState());
        add(Blocks.LIGHT_GRAY_TERRACOTTA.getDefaultState());
        add(Blocks.GRAY_TERRACOTTA.getDefaultState());
        add(Blocks.BLACK_TERRACOTTA.getDefaultState());
        add(Blocks.BROWN_TERRACOTTA.getDefaultState());
        add(Blocks.RED_TERRACOTTA.getDefaultState());
        add(Blocks.ORANGE_TERRACOTTA.getDefaultState());
        add(Blocks.YELLOW_TERRACOTTA.getDefaultState());
        add(Blocks.LIME_TERRACOTTA.getDefaultState());
        add(Blocks.GREEN_TERRACOTTA.getDefaultState());
        add(Blocks.CYAN_TERRACOTTA.getDefaultState());
        add(Blocks.LIGHT_BLUE_TERRACOTTA.getDefaultState());
        add(Blocks.BLUE_TERRACOTTA.getDefaultState());
        add(Blocks.PURPLE_TERRACOTTA.getDefaultState());
        add(Blocks.MAGENTA_TERRACOTTA.getDefaultState());
        add(Blocks.PINK_TERRACOTTA.getDefaultState());
    }};

    public static final ArrayList<BlockState> LIST_FULL_CORAL_BLOCKS = new ArrayList<BlockState>() {{
        add(Blocks.BRAIN_CORAL_BLOCK.getDefaultState());
        add(Blocks.TUBE_CORAL_BLOCK.getDefaultState());
        add(Blocks.BUBBLE_CORAL_BLOCK.getDefaultState());
        add(Blocks.FIRE_CORAL_BLOCK.getDefaultState());
        add(Blocks.HORN_CORAL_BLOCK.getDefaultState());
    }};

    public static final ArrayList<BlockState> LIST_WATERLOGGABLE_CORAL_BLOCKS = new ArrayList<BlockState>() {{
        add(Blocks.BRAIN_CORAL.getDefaultState().with(CoralParentBlock.WATERLOGGED, true));
        add(Blocks.TUBE_CORAL.getDefaultState().with(CoralParentBlock.WATERLOGGED, true));
        add(Blocks.BUBBLE_CORAL.getDefaultState().with(CoralParentBlock.WATERLOGGED, true));
        add(Blocks.FIRE_CORAL.getDefaultState().with(CoralParentBlock.WATERLOGGED, true));
        add(Blocks.HORN_CORAL.getDefaultState().with(CoralParentBlock.WATERLOGGED, true));

        add(Blocks.BRAIN_CORAL_FAN.getDefaultState().with(CoralParentBlock.WATERLOGGED, true));
        add(Blocks.TUBE_CORAL_FAN.getDefaultState().with(CoralParentBlock.WATERLOGGED, true));
        add(Blocks.BUBBLE_CORAL_FAN.getDefaultState().with(CoralParentBlock.WATERLOGGED, true));
        add(Blocks.FIRE_CORAL_FAN.getDefaultState().with(CoralParentBlock.WATERLOGGED, true));
        add(Blocks.HORN_CORAL_FAN.getDefaultState().with(CoralParentBlock.WATERLOGGED, true));

        add(Blocks.SEA_PICKLE.getDefaultState().with(SeaPickleBlock.WATERLOGGED, true).with(SeaPickleBlock.PICKLES, 1));
        add(Blocks.SEA_PICKLE.getDefaultState().with(SeaPickleBlock.WATERLOGGED, true).with(SeaPickleBlock.PICKLES, 2));
        add(Blocks.SEA_PICKLE.getDefaultState().with(SeaPickleBlock.WATERLOGGED, true).with(SeaPickleBlock.PICKLES, 3));
        add(Blocks.SEA_PICKLE.getDefaultState().with(SeaPickleBlock.WATERLOGGED, true).with(SeaPickleBlock.PICKLES, 4));
    }};

    public static ArrayList<BlockState> LIST_FLOWERS = new ArrayList<BlockState>() {{
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
        add(Blocks.LILAC.getDefaultState());
        add(Blocks.ROSE_BUSH.getDefaultState());
        add(Blocks.PEONY.getDefaultState());
    }};

    public static ArrayList<BlockState> LIST_TALL_FLOWERS = new ArrayList<BlockState>() {{
        add(Blocks.SUNFLOWER.getDefaultState());
        add(Blocks.LILAC.getDefaultState());
        add(Blocks.ROSE_BUSH.getDefaultState());
        add(Blocks.PEONY.getDefaultState());
        //add(Blocks.TALL_GRASS.getDefaultState()); // generates more often than enough on grass planet
        add(Blocks.LARGE_FERN.getDefaultState());
    }};

    public static class SpheroidDecorators {
        public static SpheroidDecorator CACTUS = new CactusDecorator();
        public static SpheroidDecorator SEA_GREENS = new SeaGreensDecorator();
        public static SpheroidDecorator COCOA = new CocoaDecorator();
        public static SpheroidDecorator BAMBOO = new BambooDecorator();
        public static SpheroidDecorator SUGAR_CANE_POND = new SugarCanePondDecorator();
        public static SpheroidDecorator MUSHROOMS = new MushroomDecorator();
        public static SpheroidDecorator DEAD_GRASS = new PlantDecorator(Blocks.DEAD_BUSH.getDefaultState(), 0.05F);
        public static SpheroidDecorator SWEET_BERRIES = new PlantDecorator(Blocks.SWEET_BERRY_BUSH.getDefaultState(), 0.03F);
        public static PlantDecorator FERNS_DECORATOR = new PlantDecorator(Blocks.FERN.getDefaultState(), 0.1F);
        public static DoublePlantDecorator LARGE_FERNS_DECORATOR = new DoublePlantDecorator(Blocks.LARGE_FERN.getDefaultState(), 0.1F);
    }

}
