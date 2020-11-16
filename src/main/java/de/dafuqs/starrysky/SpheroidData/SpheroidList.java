package de.dafuqs.starrysky.SpheroidData;

import de.dafuqs.starrysky.spheroidtypes.*;
import de.dafuqs.starrysky.spheroidtypes.special_overworld.BeeHiveSpheroidType;
import de.dafuqs.starrysky.spheroidtypes.special_overworld.DungeonSpheroidType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.state.property.Properties;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class SpheroidList {

    //COMMONLY USED LISTS
    public static final LinkedHashMap MAP_STONES = new LinkedHashMap() {{
        put(Blocks.STONE.getDefaultState(), 5.0F);
        put(Blocks.GRANITE.getDefaultState(), 1.0F);
        put(Blocks.DIORITE.getDefaultState(), 1.0F);
        put(Blocks.ANDESITE.getDefaultState(), 1.0F);
        put(Blocks.COBBLESTONE.getDefaultState(), 0.1F);
        put(Blocks.MOSSY_COBBLESTONE.getDefaultState(), 0.005F);
        put(Blocks.INFESTED_STONE.getDefaultState(), 0.05F);
        put(Blocks.INFESTED_COBBLESTONE.getDefaultState(), 0.002F);
    }};

    public static final LinkedHashMap MAP_DUNGEON_STONES = new LinkedHashMap() {{
        put(Blocks.MOSSY_COBBLESTONE.getDefaultState(), 5.0F);
        put(Blocks.INFESTED_COBBLESTONE.getDefaultState(), 0.5F);
        put(Blocks.STONE.getDefaultState(), 1.0F);
        put(Blocks.GRANITE.getDefaultState(), 0.2F);
        put(Blocks.DIORITE.getDefaultState(), 0.2F);
        put(Blocks.ANDESITE.getDefaultState(), 0.2F);
        put(Blocks.COBBLESTONE.getDefaultState(), 0.1F);
        put(Blocks.INFESTED_STONE.getDefaultState(), 0.1F);
    }};

    public static final LinkedHashMap MAP_GLASS = new LinkedHashMap() {{
        put(Blocks.GLASS.getDefaultState(), 80F);
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

    //SPHEROID TYPES
    //BASIC
    public static final ModularSpheroidType GRASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.grass, Blocks.DIRT.getDefaultState(), 5, 20).setTopBlockState(Blocks.GRASS_BLOCK.getDefaultState());
    public static final ModularSpheroidType MYCELIUM = new ModularSpheroidType(SpheroidAdvancementIdentifier.mycelium, Blocks.DIRT.getDefaultState(), 5, 8).setTopBlockState(Blocks.MYCELIUM.getDefaultState());
    public static final ModularSpheroidType PODZOL = new ModularSpheroidType(SpheroidAdvancementIdentifier.podzol, Blocks.DIRT.getDefaultState(), 5, 12).setTopBlockState(Blocks.PODZOL.getDefaultState());
    public static final ModularSpheroidType STONE = new ModularSpheroidType(SpheroidAdvancementIdentifier.stone, Blocks.STONE.getDefaultState(), 5, 18);
    public static final ModularSpheroidType GRANITE = new ModularSpheroidType(SpheroidAdvancementIdentifier.granite, Blocks.GRANITE.getDefaultState(), 6, 13);
    public static final ModularSpheroidType DIORITE = new ModularSpheroidType(SpheroidAdvancementIdentifier.diorite, Blocks.DIORITE.getDefaultState(), 6, 13);
    public static final ModularSpheroidType ANDESITE = new ModularSpheroidType(SpheroidAdvancementIdentifier.andesite, Blocks.ANDESITE.getDefaultState(), 6, 13);
    public static final ModularSpheroidType SAND = new ModularSpheroidType(SpheroidAdvancementIdentifier.sand, Blocks.SAND.getDefaultState(), 5, 15).setBottomBlockState(Blocks.SANDSTONE.getDefaultState());
    public static final ModularSpheroidType RED_SAND = new ModularSpheroidType(SpheroidAdvancementIdentifier.red_sand, Blocks.RED_SAND.getDefaultState(), 5, 12).setBottomBlockState(Blocks.RED_SANDSTONE.getDefaultState());
    public static final ModularSpheroidType GRAVEL = new ModularSpheroidType(SpheroidAdvancementIdentifier.gravel, Blocks.GRAVEL.getDefaultState(), 6, 10).setBottomBlockState(Blocks.COBBLESTONE.getDefaultState());
    public static final ModularSpheroidType COBBLESTONE = new ModularSpheroidType(SpheroidAdvancementIdentifier.cobblestone, Blocks.COBBLESTONE.getDefaultState(), 5, 15);
    public static final ModularSpheroidType MOSSY_COBBLESTONE = new ModularSpheroidType(SpheroidAdvancementIdentifier.mossy_cobblestone, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 5, 6);
    public static final ModularSpheroidType COARSE_DIRT = new ModularSpheroidType(SpheroidAdvancementIdentifier.coarse_dirt, Blocks.COARSE_DIRT.getDefaultState(), 5, 6);

    // GIANT SPHERE WITH HUGE CAVE INSIDE
    public static final CaveSpheroidType HUGE_MONSTER_CAVE = new CaveSpheroidType(SpheroidAdvancementIdentifier.cave, Blocks.DIRT.getDefaultState(), Blocks.COARSE_DIRT.getDefaultState(), 20, 30, 2, 5).setTopBlockState(Blocks.GRASS_BLOCK.getDefaultState());
    public static final DoubleCoreSpheroidType THE_SUN = new DoubleCoreSpheroidType(SpheroidAdvancementIdentifier.the_sun, Blocks.NETHERITE_BLOCK.getDefaultState(), Blocks.GOLD_BLOCK.getDefaultState(), Blocks.GLOWSTONE.getDefaultState(), 50, 50, 2, 3, 45, 45);

    //GLASS
    public static final ModularSpheroidType GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.glass, Blocks.GLASS.getDefaultState(), 5, 8);
    public static final ModularSpheroidType BLACK_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, Blocks.BLACK_STAINED_GLASS.getDefaultState(), 5, 10);
    public static final ModularSpheroidType BLUE_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, Blocks.BLUE_STAINED_GLASS.getDefaultState(), 5, 10);
    public static final ModularSpheroidType BROWN_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, Blocks.BROWN_STAINED_GLASS.getDefaultState(), 5, 10);
    public static final ModularSpheroidType CYAN_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, Blocks.CYAN_STAINED_GLASS.getDefaultState(), 5, 10);
    public static final ModularSpheroidType GRAY_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, Blocks.GRAY_STAINED_GLASS.getDefaultState(), 5, 10);
    public static final ModularSpheroidType GREEN_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, Blocks.GREEN_STAINED_GLASS.getDefaultState(), 5, 10);
    public static final ModularSpheroidType LIGHT_BLUE_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, Blocks.LIGHT_BLUE_STAINED_GLASS.getDefaultState(), 5, 10);
    public static final ModularSpheroidType LIGHT_GRAY_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, Blocks.LIGHT_GRAY_STAINED_GLASS.getDefaultState(), 5, 10);
    public static final ModularSpheroidType LIME_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, Blocks.LIME_STAINED_GLASS.getDefaultState(), 5, 10);
    public static final ModularSpheroidType MAGENTA_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, Blocks.MAGENTA_STAINED_GLASS.getDefaultState(), 5, 10);
    public static final ModularSpheroidType ORANGE_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, Blocks.ORANGE_STAINED_GLASS.getDefaultState(), 5, 10);
    public static final ModularSpheroidType PINK_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, Blocks.PINK_STAINED_GLASS.getDefaultState(), 5, 10);
    public static final ModularSpheroidType PURPLE_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, Blocks.PURPLE_STAINED_GLASS.getDefaultState(), 5, 10);
    public static final ModularSpheroidType RED_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, Blocks.RED_STAINED_GLASS.getDefaultState(), 5, 10);
    public static final ModularSpheroidType WHITE_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, Blocks.WHITE_STAINED_GLASS.getDefaultState(), 5, 10);
    public static final ModularSpheroidType YELLOW_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, Blocks.YELLOW_STAINED_GLASS.getDefaultState(), 5, 10);

    //RARE
    public static final ModularSpheroidType OBSIDIAN = new ModularSpheroidType(SpheroidAdvancementIdentifier.obsidian, Blocks.OBSIDIAN.getDefaultState(),   3, 6);
    public static final ModularSpheroidType GLOWSTONE = new ModularSpheroidType(SpheroidAdvancementIdentifier.glowstone, Blocks.GLOWSTONE.getDefaultState(), 5, 10);
    public static final ModularSpheroidType BEDROCK = new ModularSpheroidType(SpheroidAdvancementIdentifier.bedrock, Blocks.BEDROCK.getDefaultState(), 3, 5);
    public static final ShellSpheroidType STONE_HOLLOW = new ShellSpheroidType(SpheroidAdvancementIdentifier.cave, Blocks.CAVE_AIR.getDefaultState(), MAP_STONES, 5, 20, 3, 8);

    // ORES
    public static final CoreSpheroidType COAL     = new CoreSpheroidType(SpheroidAdvancementIdentifier.coal, Blocks.COAL_ORE.getDefaultState(), MAP_STONES, 5, 15, 3, 7);
    public static final CoreSpheroidType IRON     = new CoreSpheroidType(SpheroidAdvancementIdentifier.iron, Blocks.IRON_ORE.getDefaultState(), MAP_STONES, 5, 12, 3, 5);
    public static final CoreSpheroidType GOLD     = new CoreSpheroidType(SpheroidAdvancementIdentifier.gold, Blocks.GOLD_ORE.getDefaultState(), MAP_STONES, 5, 10, 2, 4);
    public static final CoreSpheroidType DIAMOND  = new CoreSpheroidType(SpheroidAdvancementIdentifier.diamond, Blocks.DIAMOND_ORE.getDefaultState(), MAP_STONES, 3, 7, 1, 3);
    public static final CoreSpheroidType REDSTONE = new CoreSpheroidType(SpheroidAdvancementIdentifier.redstone, Blocks.REDSTONE_ORE.getDefaultState(), MAP_STONES, 5, 15, 4, 8);
    public static final CoreSpheroidType LAPIS    = new CoreSpheroidType(SpheroidAdvancementIdentifier.lapis, Blocks.LAPIS_ORE.getDefaultState(), MAP_STONES, 5, 8, 2, 4);
    public static final CoreSpheroidType EMERALD  = new CoreSpheroidType(SpheroidAdvancementIdentifier.emerald, Blocks.EMERALD_ORE.getDefaultState(), MAP_STONES, 5, 6, 1, 3);

    // "ORES"
    public static final CoreSpheroidType BONE       = new CoreSpheroidType(SpheroidAdvancementIdentifier.bone, Blocks.BONE_BLOCK.getDefaultState(), MAP_STONES, 4, 8, 2, 4);
    public static final CoreSpheroidType HAY        = new CoreSpheroidType(SpheroidAdvancementIdentifier.hay, Blocks.HAY_BLOCK.getDefaultState(), MAP_STONES, 4, 8, 2, 4);
    public static final CoreSpheroidType PRISMARINE = new CoreSpheroidType(SpheroidAdvancementIdentifier.prismarine, Blocks.PRISMARINE.getDefaultState(), MAP_STONES, 4, 8, 2, 4);
    public static final CoreSpheroidType SLIME      = new CoreSpheroidType(SpheroidAdvancementIdentifier.slime, Blocks.SLIME_BLOCK.getDefaultState(), MAP_STONES, 4, 8, 2, 4);
    public static final CoreSpheroidType TNT      = new CoreSpheroidType(SpheroidAdvancementIdentifier.tnt, Blocks.TNT.getDefaultState(), MAP_STONES, 4, 8, 2, 4);

    //WOOD
    public static final ShellSpheroidType OAK_WOOD      = new ShellSpheroidType(SpheroidAdvancementIdentifier.oak_wood, Blocks.OAK_WOOD.getDefaultState(),      Blocks.OAK_LEAVES.getDefaultState().with(Properties.DISTANCE_1_7, 1),      5, 20, 2, 4);
    public static final ShellSpheroidType SPRUCE_WOOD   = new ShellSpheroidType(SpheroidAdvancementIdentifier.spruce_wood, Blocks.SPRUCE_WOOD.getDefaultState(),   Blocks.SPRUCE_LEAVES.getDefaultState().with(Properties.DISTANCE_1_7, 1),   5, 20, 2, 4);
    public static final ShellSpheroidType JUNGLE_WOOD   = new ShellSpheroidType(SpheroidAdvancementIdentifier.jungle_wood, Blocks.JUNGLE_WOOD.getDefaultState(),   Blocks.JUNGLE_LEAVES.getDefaultState().with(Properties.DISTANCE_1_7, 1),   5, 20, 2, 4);
    public static final ShellSpheroidType DARK_OAK_WOOD = new ShellSpheroidType(SpheroidAdvancementIdentifier.dark_oak_wood, Blocks.DARK_OAK_WOOD.getDefaultState(), Blocks.DARK_OAK_LEAVES.getDefaultState().with(Properties.DISTANCE_1_7, 1), 5, 20, 2, 4);
    public static final ShellSpheroidType BIRCH_WOOD    = new ShellSpheroidType(SpheroidAdvancementIdentifier.birch_wood, Blocks.BIRCH_WOOD.getDefaultState(),    Blocks.BIRCH_LEAVES.getDefaultState().with(Properties.DISTANCE_1_7, 1),    5, 20, 2, 4);
    public static final ShellSpheroidType ACACIA_WOOD   = new ShellSpheroidType(SpheroidAdvancementIdentifier.acacia_wood, Blocks.ACACIA_WOOD.getDefaultState(),   Blocks.ACACIA_LEAVES.getDefaultState().with(Properties.DISTANCE_1_7, 1),   5, 20, 2, 4);

    //FLUIDS
    public static final LiquidSpheroidType WATER_GLASS      = new LiquidSpheroidType(SpheroidAdvancementIdentifier.water, Blocks.WATER.getDefaultState(), MAP_GLASS, 5, 15, 1, 2, 20, 100, 75);
    public static final LiquidSpheroidType WATER_CLAY       = new LiquidSpheroidType(SpheroidAdvancementIdentifier.clay, Blocks.WATER.getDefaultState(), MAP_GLASS, 5, 10, 1, 2, 70, 100, 75).setCoreBlock(Blocks.CLAY.getDefaultState(), 3, 6);
    public static final LiquidSpheroidType WATER_SPONGE     = new LiquidSpheroidType(SpheroidAdvancementIdentifier.sponge, Blocks.WATER.getDefaultState(), MAP_GLASS, 5, 10, 1, 2, 70, 100, 75).setCoreBlock( Blocks.WET_SPONGE.getDefaultState(), 1, 3);
    public static final LiquidSpheroidType WATER_SLIME      = new LiquidSpheroidType(SpheroidAdvancementIdentifier.slime, Blocks.WATER.getDefaultState(), MAP_GLASS, 5, 7, 1, 1, 70, 100, 50).setCoreBlock(Blocks.SLIME_BLOCK.getDefaultState(), 1, 3);
    public static final LiquidSpheroidType WATER_ICE        = new LiquidSpheroidType(SpheroidAdvancementIdentifier.ice, Blocks.WATER.getDefaultState(), MAP_GLASS, 8, 20, 1, 2, 70, 100, 75).setCoreBlock(Blocks.ICE.getDefaultState(), 2, 4);
    public static final LiquidSpheroidType WATER_PACKED_ICE = new LiquidSpheroidType(SpheroidAdvancementIdentifier.packed_ice, Blocks.WATER.getDefaultState(), Blocks.ICE.getDefaultState(), 8, 20, 1, 3, 70, 100, 75).setCoreBlock(Blocks.PACKED_ICE.getDefaultState(), 2, 4);
    public static final LiquidSpheroidType LAVA_GLASS       = new LiquidSpheroidType(SpheroidAdvancementIdentifier.lava, Blocks.LAVA.getDefaultState(), MAP_GLASS, 5, 20, 1, 3, 25, 90,  25);
    public static final LiquidSpheroidType LAVA_STONE       = new LiquidSpheroidType(SpheroidAdvancementIdentifier.lava, Blocks.LAVA.getDefaultState(), MAP_STONES,5, 20, 2, 6, 10, 100, 25);
    public static final LiquidSpheroidType LAVA_MAGMA       = new LiquidSpheroidType(SpheroidAdvancementIdentifier.magma, Blocks.LAVA.getDefaultState(), MAP_STONES,5, 20, 3, 6, 70, 100, 25).setCoreBlock(Blocks.MAGMA_BLOCK.getDefaultState(), 2, 5);
    public static final LiquidSpheroidType LAVA_OBSIDIAN    = new LiquidSpheroidType(SpheroidAdvancementIdentifier.obsidian, Blocks.LAVA.getDefaultState(), MAP_STONES,10,20, 3, 6, 50, 100, 10).setCoreBlock(Blocks.OBSIDIAN.getDefaultState(), 2, 5);

    //COLD
    public static final CoreSpheroidType       ICE           = new CoreSpheroidType(SpheroidAdvancementIdentifier.ice, Blocks.ICE.getDefaultState(), Blocks.SNOW_BLOCK.getDefaultState(), 5, 15, 3, 6);
    public static final DoubleCoreSpheroidType GLASS_ICE     = new DoubleCoreSpheroidType(SpheroidAdvancementIdentifier.ice, Blocks.PACKED_ICE.getDefaultState(), Blocks.ICE.getDefaultState(), Blocks.GLASS.getDefaultState(), 5, 12, 2, 4, 2, 4);
    public static final DoubleCoreSpheroidType SNOW_ICE      = new DoubleCoreSpheroidType(SpheroidAdvancementIdentifier.ice, Blocks.PACKED_ICE.getDefaultState(), Blocks.ICE.getDefaultState(), Blocks.SNOW_BLOCK.getDefaultState(), 5, 12, 2, 4, 2, 4);
    public static final DoubleCoreSpheroidType SNOW_BLUE_ICE = new DoubleCoreSpheroidType(SpheroidAdvancementIdentifier.blue_ice, Blocks.BLUE_ICE.getDefaultState(), Blocks.PACKED_ICE.getDefaultState(), Blocks.SNOW_BLOCK.getDefaultState(), 5, 8, 2, 3, 1, 3);
    public static final DoubleCoreSpheroidType ICE_BLUE_ICE  = new DoubleCoreSpheroidType(SpheroidAdvancementIdentifier.blue_ice, Blocks.BLUE_ICE.getDefaultState(), Blocks.PACKED_ICE.getDefaultState(), Blocks.ICE.getDefaultState(), 5, 8, 2, 3, 1, 3);

    //RAINBOW
    public static final RainbowSpheroidType RAINBOW_WOOL       = new RainbowSpheroidType(SpheroidAdvancementIdentifier.rainbow_wool, LIST_WOOL, 5, 16);
    public static final RainbowSpheroidType RAINBOW_GLASS      = new RainbowSpheroidType(SpheroidAdvancementIdentifier.rainbow_glass, LIST_STAINED_GLASS, 5, 16);
    public static final RainbowSpheroidType RAINBOW_CONCRETE   = new RainbowSpheroidType(SpheroidAdvancementIdentifier.rainbow_concrete, LIST_CONCRETE, 5, 16);
    public static final RainbowSpheroidType RAINBOW_TERRACOTTA = new RainbowSpheroidType(SpheroidAdvancementIdentifier.rainbow_terracotta, LIST_TERRACOTTA, 5, 16);

    //SPECIAL
    public static final DungeonSpheroidType DUNGEON_ZOMBIE = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, EntityType.ZOMBIE, MAP_DUNGEON_STONES, 6, 12, 2, 4);
    public static final DungeonSpheroidType DUNGEON_SKELETON = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, EntityType.SKELETON, MAP_DUNGEON_STONES, 6, 12, 2, 4);
    public static final DungeonSpheroidType DUNGEON_SPIDER = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, EntityType.SPIDER, MAP_DUNGEON_STONES, 6, 12, 2, 4);
    public static final DungeonSpheroidType DUNGEON_CREEPER = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, EntityType.CREEPER, MAP_DUNGEON_STONES, 6, 12, 2, 4);
    public static final DungeonSpheroidType DUNGEON_CAVE_SPIDER = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, EntityType.CAVE_SPIDER, MAP_DUNGEON_STONES, 6, 12, 2, 4);
    public static final DungeonSpheroidType DUNGEON_SLIME = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, EntityType.SLIME, MAP_DUNGEON_STONES, 6, 12, 2, 4);
    public static final DungeonSpheroidType DUNGEON_DROWNED = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, EntityType.DROWNED, MAP_DUNGEON_STONES, 6, 12, 2, 4);
    public static final DungeonSpheroidType DUNGEON_HUSK = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, EntityType.HUSK, MAP_DUNGEON_STONES, 6, 12, 2, 4);
    public static final DungeonSpheroidType DUNGEON_STRAY = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, EntityType.STRAY, MAP_DUNGEON_STONES, 6, 12, 2, 4);
    public static final DungeonSpheroidType DUNGEON_WITCH = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, EntityType.WITCH, MAP_DUNGEON_STONES, 6, 12, 2, 4);
    public static final DungeonSpheroidType DUNGEON_SILVERFISH = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, EntityType.SILVERFISH, MAP_DUNGEON_STONES, 6, 12, 2, 4);

    // BEES
    public static final BeeHiveSpheroidType BEE_HIVE = new BeeHiveSpheroidType(SpheroidAdvancementIdentifier.bee_hive,10, 16, 2, 4, 1, 2, 1, 2);

}
