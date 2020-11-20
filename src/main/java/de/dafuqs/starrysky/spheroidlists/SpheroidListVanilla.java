package de.dafuqs.starrysky.spheroidlists;

import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.spheroiddecorators.SpheroidDecorators;
import de.dafuqs.starrysky.spheroidtypes.*;
import de.dafuqs.starrysky.spheroidtypes.special_overworld.BeeHiveSpheroidType;
import de.dafuqs.starrysky.spheroidtypes.special_overworld.CoralSpheroidType;
import de.dafuqs.starrysky.spheroidtypes.special_overworld.DungeonSpheroidType;
import de.dafuqs.starrysky.spheroidtypes.special_overworld.MushroomSpheroidType;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.state.property.Properties;

import java.util.LinkedHashMap;

public class SpheroidListVanilla extends SpheroidList {

    //SPHEROID TYPES
    //BASIC
    public static final ModularSpheroidType GRASS = (ModularSpheroidType) new ModularSpheroidType(SpheroidAdvancementIdentifier.grass, Blocks.DIRT.getDefaultState(), 5, 20).setTopBlockState(Blocks.GRASS_BLOCK.getDefaultState()).addDecorator(SpheroidDecorators.SUGAR_CANE_POND, 0.2F);
    public static final ModularSpheroidType MYCELIUM = new ModularSpheroidType(SpheroidAdvancementIdentifier.mycelium, Blocks.DIRT.getDefaultState(), 5, 8).setTopBlockState(Blocks.MYCELIUM.getDefaultState());
    public static final ModularSpheroidType PODZOL = (ModularSpheroidType) new ModularSpheroidType(SpheroidAdvancementIdentifier.podzol, Blocks.DIRT.getDefaultState(), 5, 12).setTopBlockState(Blocks.PODZOL.getDefaultState()).addDecorator(SpheroidDecorators.BAMBOO, 0.5F);
    public static final ModularSpheroidType STONE = new ModularSpheroidType(SpheroidAdvancementIdentifier.stone, Blocks.STONE.getDefaultState(), 5, 18);
    public static final ModularSpheroidType GRANITE = new ModularSpheroidType(SpheroidAdvancementIdentifier.granite, Blocks.GRANITE.getDefaultState(), 6, 13);
    public static final ModularSpheroidType DIORITE = new ModularSpheroidType(SpheroidAdvancementIdentifier.diorite, Blocks.DIORITE.getDefaultState(), 6, 13);
    public static final ModularSpheroidType ANDESITE = new ModularSpheroidType(SpheroidAdvancementIdentifier.andesite, Blocks.ANDESITE.getDefaultState(), 6, 13);
    public static final ModularSpheroidType SAND = (ModularSpheroidType) new ModularSpheroidType(SpheroidAdvancementIdentifier.sand, Blocks.SAND.getDefaultState(), 5, 15).setBottomBlockState(Blocks.SANDSTONE.getDefaultState()).addDecorator(SpheroidDecorators.CACTUS, 0.3F);
    public static final ModularSpheroidType RED_SAND = (ModularSpheroidType) new ModularSpheroidType(SpheroidAdvancementIdentifier.red_sand, Blocks.RED_SAND.getDefaultState(), 5, 12).setBottomBlockState(Blocks.RED_SANDSTONE.getDefaultState()).addDecorator(SpheroidDecorators.CACTUS, 0.3F);
    public static final ModularSpheroidType GRAVEL = new ModularSpheroidType(SpheroidAdvancementIdentifier.gravel, Blocks.GRAVEL.getDefaultState(), 6, 10).setBottomBlockState(Blocks.COBBLESTONE.getDefaultState());
    public static final ModularSpheroidType COBBLESTONE = new ModularSpheroidType(SpheroidAdvancementIdentifier.cobblestone, Blocks.COBBLESTONE.getDefaultState(), 5, 15);
    public static final ModularSpheroidType MOSSY_COBBLESTONE = new ModularSpheroidType(SpheroidAdvancementIdentifier.mossy_cobblestone, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 5, 6);
    public static final ModularSpheroidType COARSE_DIRT = new ModularSpheroidType(SpheroidAdvancementIdentifier.coarse_dirt, Blocks.COARSE_DIRT.getDefaultState(), 5, 6);

    // GIANT SPHERES
    public static final CaveSpheroidType HUGE_MONSTER_CAVE = (CaveSpheroidType) new CaveSpheroidType(SpheroidAdvancementIdentifier.cave, Blocks.DIRT.getDefaultState(), Blocks.COARSE_DIRT.getDefaultState(), 20, 30, 2, 5).setTopBlockState(Blocks.GRASS_BLOCK.getDefaultState()).addDecorator(SpheroidDecorators.MUSHROOMS, 0.5F);
    public static final DoubleCoreSpheroidType THE_SUN = new DoubleCoreSpheroidType(SpheroidAdvancementIdentifier.the_sun, Blocks.NETHERITE_BLOCK.getDefaultState(), Blocks.GOLD_BLOCK.getDefaultState(), Blocks.GLOWSTONE.getDefaultState(), 50, 50, 2, 3, 45, 45);

    // CORALS
    public static final CoralSpheroidType CORALS_GLASS = new CoralSpheroidType(SpheroidAdvancementIdentifier.coral, MAP_GLASS, SpheroidList.LIST_FULL_CORAL_BLOCKS, SpheroidList.LIST_WATERLOGGABLE_CORAL_BLOCKS, 10, 20, 1, 2);
    public static final CoralSpheroidType CORALS_STONE = new CoralSpheroidType(SpheroidAdvancementIdentifier.coral, MAP_STONES, SpheroidList.LIST_FULL_CORAL_BLOCKS, SpheroidList.LIST_WATERLOGGABLE_CORAL_BLOCKS, 10, 20, 1, 2);

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
    public static final ShellSpheroidType STONE_HOLLOW = (ShellSpheroidType) new ShellSpheroidType(SpheroidAdvancementIdentifier.cave, Blocks.CAVE_AIR.getDefaultState(), MAP_STONES, 5, 20, 3, 8).addDecorator(SpheroidDecorators.MUSHROOMS, 0.5F);

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
    public static final ShellSpheroidType OAK_WOOD      = new ShellSpheroidType(SpheroidAdvancementIdentifier.oak_wood, Blocks.OAK_WOOD.getDefaultState(),      Blocks.OAK_LEAVES.getDefaultState().with(Properties.DISTANCE_1_7, 1),      8, 15, 2, 4);
    public static final ShellSpheroidType SPRUCE_WOOD   = new ShellSpheroidType(SpheroidAdvancementIdentifier.spruce_wood, Blocks.SPRUCE_WOOD.getDefaultState(),   Blocks.SPRUCE_LEAVES.getDefaultState().with(Properties.DISTANCE_1_7, 1),   5, 15, 2, 4);
    public static final ShellSpheroidType JUNGLE_WOOD   = (ShellSpheroidType) new ShellSpheroidType(SpheroidAdvancementIdentifier.jungle_wood, Blocks.JUNGLE_WOOD.getDefaultState(),   Blocks.JUNGLE_LEAVES.getDefaultState().with(Properties.DISTANCE_1_7, 1),   10, 20, 2, 4).addDecorator(SpheroidDecorators.COCOA, 0.5F);
    public static final ShellSpheroidType DARK_OAK_WOOD = new ShellSpheroidType(SpheroidAdvancementIdentifier.dark_oak_wood, Blocks.DARK_OAK_WOOD.getDefaultState(), Blocks.DARK_OAK_LEAVES.getDefaultState().with(Properties.DISTANCE_1_7, 1), 5, 15, 2, 4);
    public static final ShellSpheroidType BIRCH_WOOD    = new ShellSpheroidType(SpheroidAdvancementIdentifier.birch_wood, Blocks.BIRCH_WOOD.getDefaultState(),    Blocks.BIRCH_LEAVES.getDefaultState().with(Properties.DISTANCE_1_7, 1),    5, 15, 2, 4);
    public static final ShellSpheroidType ACACIA_WOOD   = new ShellSpheroidType(SpheroidAdvancementIdentifier.acacia_wood, Blocks.ACACIA_WOOD.getDefaultState(),   Blocks.ACACIA_LEAVES.getDefaultState().with(Properties.DISTANCE_1_7, 1),   5, 12, 2, 4);

    //FLUIDS
    public static final LiquidSpheroidType WATER_GLASS      = (LiquidSpheroidType) new LiquidSpheroidType(SpheroidAdvancementIdentifier.water, Blocks.WATER.getDefaultState(), MAP_GLASS, 5, 15, 1, 2, 20, 100, 75).addDecorator(SpheroidDecorators.SEA_GREENS, 0.5F);
    public static final LiquidSpheroidType WATER_CLAY       = (LiquidSpheroidType) new LiquidSpheroidType(SpheroidAdvancementIdentifier.clay, Blocks.WATER.getDefaultState(), MAP_GLASS, 5, 10, 1, 2, 70, 100, 75).setCoreBlock(Blocks.CLAY.getDefaultState(), 3, 6).addDecorator(SpheroidDecorators.SEA_GREENS, 0.5F).addDecorator(SpheroidDecorators.SEA_GREENS, 0.75F);
    public static final LiquidSpheroidType WATER_SPONGE     = (LiquidSpheroidType) new LiquidSpheroidType(SpheroidAdvancementIdentifier.sponge, Blocks.WATER.getDefaultState(), MAP_GLASS, 5, 10, 1, 2, 70, 100, 75).setCoreBlock( Blocks.WET_SPONGE.getDefaultState(), 1, 3).addDecorator(SpheroidDecorators.SEA_GREENS, 0.25F);
    public static final LiquidSpheroidType WATER_SLIME      = (LiquidSpheroidType) new LiquidSpheroidType(SpheroidAdvancementIdentifier.slime, Blocks.WATER.getDefaultState(), MAP_GLASS, 5, 7, 1, 1, 70, 100, 50).setCoreBlock(Blocks.SLIME_BLOCK.getDefaultState(), 1, 3).addDecorator(SpheroidDecorators.SEA_GREENS, 0.25F);
    public static final LiquidSpheroidType WATER_ICE        = (LiquidSpheroidType) new LiquidSpheroidType(SpheroidAdvancementIdentifier.ice, Blocks.WATER.getDefaultState(), MAP_GLASS, 8, 20, 1, 2, 70, 100, 75).setCoreBlock(Blocks.ICE.getDefaultState(), 2, 4).addDecorator(SpheroidDecorators.SEA_GREENS, 0.25F);
    public static final LiquidSpheroidType WATER_PACKED_ICE = (LiquidSpheroidType) new LiquidSpheroidType(SpheroidAdvancementIdentifier.packed_ice, Blocks.WATER.getDefaultState(), Blocks.ICE.getDefaultState(), 8, 20, 1, 3, 70, 100, 75).setCoreBlock(Blocks.PACKED_ICE.getDefaultState(), 2, 4).addDecorator(SpheroidDecorators.SEA_GREENS, 0.15F);
    public static final LiquidSpheroidType LAVA_GLASS       = new LiquidSpheroidType(SpheroidAdvancementIdentifier.lava, Blocks.LAVA.getDefaultState(), MAP_GLASS, 5, 20, 1, 3, 25, 90,  25);
    public static final LiquidSpheroidType LAVA_STONE       = new LiquidSpheroidType(SpheroidAdvancementIdentifier.lava, Blocks.LAVA.getDefaultState(), MAP_STONES,5, 20, 2, 6, 10, 100, 25);
    public static final LiquidSpheroidType LAVA_MAGMA       = new LiquidSpheroidType(SpheroidAdvancementIdentifier.magma, Blocks.LAVA.getDefaultState(), MAP_STONES,5, 20, 3, 6, 70, 100, 25).setCoreBlock(Blocks.MAGMA_BLOCK.getDefaultState(), 2, 5);
    public static final LiquidSpheroidType LAVA_OBSIDIAN    = new LiquidSpheroidType(SpheroidAdvancementIdentifier.obsidian, Blocks.LAVA.getDefaultState(), MAP_STONES,10,20, 3, 6, 50, 100, 10).setCoreBlock(Blocks.OBSIDIAN.getDefaultState(), 2, 5);

    // MUSHROOMS
    public static final ShellSpheroidType BROWN_MUSHROOM    = new MushroomSpheroidType(SpheroidAdvancementIdentifier.brown_mushroom, Blocks.MUSHROOM_STEM.getDefaultState(), Blocks.BROWN_MUSHROOM_BLOCK.getDefaultState(), 4, 8, 2, 3);
    public static final ShellSpheroidType RED_MUSHROOM      = new MushroomSpheroidType(SpheroidAdvancementIdentifier.red_mushroom, Blocks.MUSHROOM_STEM.getDefaultState(), Blocks.RED_MUSHROOM_BLOCK.getDefaultState(), 4, 8, 2, 3);

    //COLD
    public static final CoreSpheroidType       ICE           = new CoreSpheroidType(SpheroidAdvancementIdentifier.ice, Blocks.ICE.getDefaultState(), Blocks.SNOW_BLOCK.getDefaultState(), 5, 15, 3, 6);
    public static final DoubleCoreSpheroidType GLASS_ICE     = new DoubleCoreSpheroidType(SpheroidAdvancementIdentifier.ice, Blocks.PACKED_ICE.getDefaultState(), Blocks.ICE.getDefaultState(), Blocks.GLASS.getDefaultState(), 5, 12, 2, 4, 2, 4);
    public static final DoubleCoreSpheroidType SNOW_ICE      = (DoubleCoreSpheroidType) new DoubleCoreSpheroidType(SpheroidAdvancementIdentifier.ice, Blocks.PACKED_ICE.getDefaultState(), Blocks.ICE.getDefaultState(), Blocks.SNOW_BLOCK.getDefaultState(), 5, 12, 2, 4, 2, 4).addDecorator(SpheroidDecorators.SWEET_BERRIES, 0.5F);
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
    public static final DungeonSpheroidType DUNGEON_ENDERMAN = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, EntityType.ENDERMAN, MAP_DUNGEON_STONES, 6, 12, 2, 4);

    // BEES
    public static final BeeHiveSpheroidType BEE_HIVE = new BeeHiveSpheroidType(SpheroidAdvancementIdentifier.bee_hive,10, 16, 2, 4, 1, 2, 1, 2);

    public static boolean shouldGenerate() {
        return true;
    }

    public static LinkedHashMap<SpheroidType, Float> getSpheroidTypesWithProbabilities() {
        LinkedHashMap<SpheroidType, Float> spheroidTypes = new LinkedHashMap<>();

        //BASIC
        spheroidTypes.put(GRASS, 3.5F);
        spheroidTypes.put(MYCELIUM, 0.01F);
        spheroidTypes.put(PODZOL, 0.4F);
        spheroidTypes.put(STONE, 0.2F);
        spheroidTypes.put(GRANITE, 0.2F);
        spheroidTypes.put(DIORITE, 0.2F);
        spheroidTypes.put(ANDESITE, 1.0F);
        spheroidTypes.put(SAND, 2.0F);
        spheroidTypes.put(RED_SAND, 0.2F);
        spheroidTypes.put(GRAVEL, 0.3F);
        spheroidTypes.put(COBBLESTONE, 0.2F);
        spheroidTypes.put(MOSSY_COBBLESTONE, 0.05F);
        spheroidTypes.put(COARSE_DIRT, 0.05F);

        //GLASS
        spheroidTypes.put(GLASS, 0.1F);
        spheroidTypes.put(BLACK_STAINED_GLASS, 0.01F);
        spheroidTypes.put(BLUE_STAINED_GLASS, 0.01F);
        spheroidTypes.put(BROWN_STAINED_GLASS, 0.01F);
        spheroidTypes.put(CYAN_STAINED_GLASS, 0.01F);
        spheroidTypes.put(GRAY_STAINED_GLASS, 0.01F);
        spheroidTypes.put(GREEN_STAINED_GLASS, 0.01F);
        spheroidTypes.put(LIGHT_BLUE_STAINED_GLASS, 0.01F);
        spheroidTypes.put(LIGHT_GRAY_STAINED_GLASS, 0.01F);
        spheroidTypes.put(LIME_STAINED_GLASS, 0.01F);
        spheroidTypes.put(MAGENTA_STAINED_GLASS, 0.01F);
        spheroidTypes.put(ORANGE_STAINED_GLASS, 0.01F);
        spheroidTypes.put(PINK_STAINED_GLASS, 0.01F);
        spheroidTypes.put(PURPLE_STAINED_GLASS, 0.01F);
        spheroidTypes.put(RED_STAINED_GLASS, 0.01F);
        spheroidTypes.put(WHITE_STAINED_GLASS, 0.01F);
        spheroidTypes.put(YELLOW_STAINED_GLASS, 0.01F);

        // CORALS
        spheroidTypes.put(CORALS_GLASS, 0.1F);
        spheroidTypes.put(CORALS_STONE, 0.1F);

        //RARE
        spheroidTypes.put(OBSIDIAN, 0.1F);
        spheroidTypes.put(GLOWSTONE, 2.0F);
        spheroidTypes.put(BEDROCK, 0.01F);
        spheroidTypes.put(STONE_HOLLOW, 0.2F);

        //ORES
        spheroidTypes.put(COAL, 4.0F);
        spheroidTypes.put(IRON, 2.0F);
        spheroidTypes.put(GOLD, 0.5F);
        spheroidTypes.put(DIAMOND, 0.2F);
        spheroidTypes.put(REDSTONE, 1.0F);
        spheroidTypes.put(LAPIS, 0.3F);
        spheroidTypes.put(EMERALD, 0.05F);

        // "ORES"
        spheroidTypes.put(BONE, 0.02F);
        spheroidTypes.put(HAY, 0.02F);
        spheroidTypes.put(PRISMARINE, 0.02F);
        spheroidTypes.put(SLIME, 0.02F);
        spheroidTypes.put(TNT, 0.02F);

        //WOOD
        spheroidTypes.put(OAK_WOOD, 1.0F);
        spheroidTypes.put(SPRUCE_WOOD, 0.5F);
        spheroidTypes.put(JUNGLE_WOOD, 0.3F);
        spheroidTypes.put(DARK_OAK_WOOD, 0.3F);
        spheroidTypes.put(BIRCH_WOOD, 0.5F);
        spheroidTypes.put(ACACIA_WOOD, 0.3F);

        // MUSHROOMS
        spheroidTypes.put(BROWN_MUSHROOM, 0.1F);
        spheroidTypes.put(RED_MUSHROOM, 0.1F);

        //FLUIDS
        spheroidTypes.put(WATER_GLASS, 1.5F);
        spheroidTypes.put(WATER_CLAY, 0.5F);
        spheroidTypes.put(WATER_SPONGE, 0.1F);
        spheroidTypes.put(WATER_SLIME, 0.1F);
        spheroidTypes.put(WATER_ICE, 0.3F);
        spheroidTypes.put(WATER_PACKED_ICE, 0.1F);
        spheroidTypes.put(LAVA_STONE, 2.0F);
        spheroidTypes.put(LAVA_MAGMA, 0.4F);
        spheroidTypes.put(LAVA_OBSIDIAN, 1.0F);
        spheroidTypes.put(LAVA_GLASS, 0.5F);

        //COLD
        spheroidTypes.put(ICE, 0.4F);
        spheroidTypes.put(GLASS_ICE, 0.4F);
        spheroidTypes.put(SNOW_ICE, 0.2F);
        spheroidTypes.put(SNOW_BLUE_ICE, 0.2F);
        spheroidTypes.put(ICE_BLUE_ICE, 0.2F);

        //RAINBOW
        spheroidTypes.put(RAINBOW_WOOL, 0.1F);
        spheroidTypes.put(RAINBOW_GLASS, 0.1F);
        spheroidTypes.put(RAINBOW_CONCRETE, 0.1F);
        spheroidTypes.put(RAINBOW_TERRACOTTA, 0.1F);

        // SPAWNERS
        spheroidTypes.put(DUNGEON_ZOMBIE, 0.02F);
        spheroidTypes.put(DUNGEON_SKELETON, 0.02F);
        spheroidTypes.put(DUNGEON_SPIDER, 0.02F);
        spheroidTypes.put(DUNGEON_CREEPER, 0.08F);
        spheroidTypes.put(DUNGEON_CAVE_SPIDER, 0.01F);
        spheroidTypes.put(DUNGEON_SLIME, 0.01F);
        spheroidTypes.put(DUNGEON_DROWNED, 0.01F);
        spheroidTypes.put(DUNGEON_HUSK, 0.01F);
        spheroidTypes.put(DUNGEON_STRAY, 0.01F);
        spheroidTypes.put(DUNGEON_WITCH, 0.01F);
        spheroidTypes.put(DUNGEON_SILVERFISH, 0.01F);
        spheroidTypes.put(DUNGEON_ENDERMAN, 0.003F);

        // SPECIAL
        spheroidTypes.put(BEE_HIVE, 0.2F);
        spheroidTypes.put(HUGE_MONSTER_CAVE, 0.1F);
        spheroidTypes.put(THE_SUN, 0.005F);

        return spheroidTypes;
    }

}
