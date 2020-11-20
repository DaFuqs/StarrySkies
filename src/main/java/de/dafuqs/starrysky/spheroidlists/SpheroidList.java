package de.dafuqs.starrysky.spheroidlists;

import de.dafuqs.starrysky.spheroidtypes.SpheroidType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CoralParentBlock;
import net.minecraft.block.SeaPickleBlock;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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
    public static final List<BlockState> LIST_WOOL = new ArrayList<BlockState>() {{
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

    public static final List<BlockState> LIST_STAINED_GLASS = new ArrayList<BlockState>() {{
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

    public static final List<BlockState> LIST_CONCRETE = new ArrayList<BlockState>() {{
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

    public static final List<BlockState> LIST_TERRACOTTA = new ArrayList<BlockState>() {{
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

    /**
     * Checks if the mod that provides the blocks in the list is loaded
     *
     * @return true if the mod is loaded
     */
    public static boolean isModPresent() {
        return false;
    }

    /**
     * Returns a hash map of blocks that can be dictionaried.
     * Think ores (so there aren't 5 types of copper ores)
     *
     * @return LinkedHashMap of "dictEntry" and BlockState
     */
    public static LinkedHashMap<String, BlockState> getDictionaryEntries() {
        return new LinkedHashMap<>();
    }

    /**
     * List of all SpheroidTypes the mod provides
     * The dictionaried ones don't need their separate spheroids, they'll get generated dynamically
     *
     * @return All provided SpheroidTypes
     */
    public static LinkedHashMap<SpheroidType, Float> getSpheroidTypesWithProbabilities() {
        return new LinkedHashMap<>();
    }
}
