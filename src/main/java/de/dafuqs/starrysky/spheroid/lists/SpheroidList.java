package de.dafuqs.starrysky.spheroid.lists;

import de.dafuqs.starrysky.StarrySkyCommon;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CoralParentBlock;
import net.minecraft.block.SeaPickleBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.apache.logging.log4j.Level.ERROR;

public abstract class SpheroidList {

    protected static BlockState getDefaultBlockState(String modId, String string) {
        BlockState blockState = Registry.BLOCK.get(new Identifier(modId, string)).getDefaultState();

        // So mod compatibility problems can be easily debugged
        if(blockState.getBlock() == Blocks.AIR && !string.equals("air")) {
            StarrySkyCommon.log(ERROR, "Block " + modId + ":" + string + " does not exist! It will be treated as air");
        }

        return blockState;
    }


    //COMMONLY USED LISTS
    public static final LinkedHashMap<BlockState, Float> MAP_STONES = new LinkedHashMap<>() {{
        put(Blocks.STONE.getDefaultState(), 5.0F);
        put(Blocks.GRANITE.getDefaultState(), 1.0F);
        put(Blocks.DIORITE.getDefaultState(), 1.0F);
        put(Blocks.ANDESITE.getDefaultState(), 1.0F);
        put(Blocks.COBBLESTONE.getDefaultState(), 0.1F);
        put(Blocks.MOSSY_COBBLESTONE.getDefaultState(), 0.005F);
        put(Blocks.INFESTED_STONE.getDefaultState(), 0.05F);
        put(Blocks.INFESTED_COBBLESTONE.getDefaultState(), 0.002F);
    }};

    public static final LinkedHashMap<BlockState, Float> MAP_DUNGEON_STONES = new LinkedHashMap<>() {{
        put(Blocks.MOSSY_COBBLESTONE.getDefaultState(), 5.0F);
        put(Blocks.INFESTED_COBBLESTONE.getDefaultState(), 0.5F);
        put(Blocks.STONE.getDefaultState(), 1.0F);
        put(Blocks.DEEPSLATE.getDefaultState(), 1.0F);
        put(Blocks.GRANITE.getDefaultState(), 0.2F);
        put(Blocks.DIORITE.getDefaultState(), 0.2F);
        put(Blocks.ANDESITE.getDefaultState(), 0.2F);
        put(Blocks.COBBLESTONE.getDefaultState(), 0.1F);
        put(Blocks.COBBLED_DEEPSLATE.getDefaultState(), 0.1F);
        put(Blocks.INFESTED_STONE.getDefaultState(), 0.1F);
        put(Blocks.INFESTED_DEEPSLATE.getDefaultState(), 0.1F);
    }};

    public static final LinkedHashMap<BlockState, Float> MAP_DEEPSLATE = new LinkedHashMap<>() {{
        put(Blocks.DEEPSLATE.getDefaultState(), 5.0F);
        put(Blocks.INFESTED_DEEPSLATE.getDefaultState(), 0.5F);
        put(Blocks.COBBLED_DEEPSLATE.getDefaultState(), 1.0F);
    }};

    public static final LinkedHashMap<BlockState, Float> MAP_GLASS = new LinkedHashMap<>() {{
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
        put(Blocks.TINTED_GLASS.getDefaultState(), 1.0F);
    }};

    public static final LinkedHashMap<BlockState, Float> MAP_MOUNTAIN_STONES  = new LinkedHashMap<>() {{
        put(Blocks.STONE.getDefaultState(), 1.0F);
        put(Blocks.INFESTED_STONE.getDefaultState(), 1.0F);
    }};
    public static final LinkedHashMap<BlockState, Float> MAP_OCEAN_STONES  = new LinkedHashMap<>() {{
        put(Blocks.CLAY.getDefaultState(), 1.0F);
        put(Blocks.PRISMARINE.getDefaultState(), 1.0F);
    }};
    public static final LinkedHashMap<BlockState, Float> MAP_JUNGLE_STONES  = new LinkedHashMap<>() {{
        put(Blocks.COARSE_DIRT.getDefaultState(), 1.0F);
    }};


    // RAINBOW STUFF
    public static final ArrayList<BlockState> LIST_WOOL = new ArrayList<>() {{
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

    public static final ArrayList<BlockState> LIST_STAINED_GLASS = new ArrayList<>() {{
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

    public static final ArrayList<BlockState> LIST_CONCRETE = new ArrayList<>() {{
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

    public static final ArrayList<BlockState> LIST_TERRACOTTA = new ArrayList<>() {{
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

    public static final ArrayList<BlockState> LIST_FULL_CORAL_BLOCKS = new ArrayList<>() {{
        add(Blocks.BRAIN_CORAL_BLOCK.getDefaultState());
        add(Blocks.TUBE_CORAL_BLOCK.getDefaultState());
        add(Blocks.BUBBLE_CORAL_BLOCK.getDefaultState());
        add(Blocks.FIRE_CORAL_BLOCK.getDefaultState());
        add(Blocks.HORN_CORAL_BLOCK.getDefaultState());
    }};

    public static final ArrayList<BlockState> LIST_WATERLOGGABLE_CORAL_BLOCKS = new ArrayList<>() {{
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

    public static ArrayList<BlockState> LIST_FLOWERS = new ArrayList<>() {{
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
        add(Blocks.AZALEA.getDefaultState());
        add(Blocks.FLOWERING_AZALEA.getDefaultState());
    }};

    public static ArrayList<BlockState> LIST_TALL_FLOWERS = new ArrayList<>() {{
        add(Blocks.SUNFLOWER.getDefaultState());
        add(Blocks.LILAC.getDefaultState());
        add(Blocks.ROSE_BUSH.getDefaultState());
        add(Blocks.PEONY.getDefaultState());
        //add(Blocks.TALL_GRASS.getDefaultState()); // generates more often than enough on grass planet
        add(Blocks.LARGE_FERN.getDefaultState());
    }};

    //// NETHER
    public static final LinkedHashMap<BlockState, Float> MAP_NETHER_STONES = new LinkedHashMap<>() {{
        put(Blocks.NETHERRACK.getDefaultState(), 5.0F);
        put(Blocks.BASALT.getDefaultState(), 1.0F);
        put(Blocks.MAGMA_BLOCK.getDefaultState(), 1.0F);
    }};

    public static final LinkedHashMap<BlockState, Float> MAP_NETHER_DUNGEON_STONES = new LinkedHashMap<>() {{
        put(Blocks.NETHER_BRICKS.getDefaultState(), 5.0F);
        put(Blocks.BLACKSTONE.getDefaultState(), 1.0F);
        put(Blocks.NETHERRACK.getDefaultState(), 0.5F);
        put(Blocks.MAGMA_BLOCK.getDefaultState(), 0.2F);
        put(Blocks.OBSIDIAN.getDefaultState(), 0.2F);
    }};

    public static final LinkedHashMap<BlockState, Float> MAP_NETHER_GLASS = new LinkedHashMap<>() {{
        put(Blocks.BLACK_STAINED_GLASS.getDefaultState(), 1.0F);
        put(Blocks.ORANGE_STAINED_GLASS.getDefaultState(), 1.0F);
        put(Blocks.RED_STAINED_GLASS.getDefaultState(), 1.0F);
        put(Blocks.YELLOW_STAINED_GLASS.getDefaultState(), 1.0F);
    }};


    //// END
    public static final LinkedHashMap<BlockState, Float> MAP_END_STONES = new LinkedHashMap<>() {{
        put(Blocks.END_STONE.getDefaultState(), 10.0F);
    }};

    public static final LinkedHashMap<BlockState, Float> MAP_END_DUNGEON_STONES = new LinkedHashMap<>() {{
        put(Blocks.END_STONE_BRICKS.getDefaultState(), 10.0F);
        put(Blocks.PURPUR_BLOCK.getDefaultState(), 5.0F);
        put(Blocks.END_STONE.getDefaultState(), 1.0F);
    }};

}