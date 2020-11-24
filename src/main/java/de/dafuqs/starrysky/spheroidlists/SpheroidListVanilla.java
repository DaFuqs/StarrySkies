package de.dafuqs.starrysky.spheroidlists;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.dimension.SpheroidDistributionType;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import de.dafuqs.starrysky.spheroidtypes.*;
import de.dafuqs.starrysky.spheroidtypes.special_overworld.*;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.state.property.Properties;

public class SpheroidListVanilla extends SpheroidList {

    //SPHEROID TYPES
    //BASIC
    public static final ModularSpheroidType GRASS = (ModularSpheroidType) new ModularSpheroidType(SpheroidAdvancementIdentifier.grass, 5, 20,  Blocks.DIRT.getDefaultState()).setTopBlockState(Blocks.GRASS_BLOCK.getDefaultState()).addDecorator(SpheroidDecorators.SUGAR_CANE_POND, 0.25F);
    public static final ModularSpheroidType MYCELIUM = new ModularSpheroidType(SpheroidAdvancementIdentifier.mycelium, 5, 8,  Blocks.DIRT.getDefaultState()).setTopBlockState(Blocks.MYCELIUM.getDefaultState());
    public static final ModularSpheroidType PODZOL = (ModularSpheroidType) new ModularSpheroidType(SpheroidAdvancementIdentifier.podzol, 5, 12,  Blocks.DIRT.getDefaultState()).setTopBlockState(Blocks.PODZOL.getDefaultState()).addDecorator(SpheroidDecorators.BAMBOO, 0.3F);
    public static final ModularSpheroidType STONE = new ModularSpheroidType(SpheroidAdvancementIdentifier.stone, 5, 13,  Blocks.STONE.getDefaultState());
    public static final ModularSpheroidType GRANITE = new ModularSpheroidType(SpheroidAdvancementIdentifier.granite, 6, 11,  Blocks.GRANITE.getDefaultState());
    public static final ModularSpheroidType DIORITE = new ModularSpheroidType(SpheroidAdvancementIdentifier.diorite, 6, 11,  Blocks.DIORITE.getDefaultState());
    public static final ModularSpheroidType ANDESITE = new ModularSpheroidType(SpheroidAdvancementIdentifier.andesite, 6, 11,  Blocks.ANDESITE.getDefaultState());
    public static final ModularSpheroidType SAND = (ModularSpheroidType) new ModularSpheroidType(SpheroidAdvancementIdentifier.sand, 5, 13,  Blocks.SAND.getDefaultState()).setBottomBlockState(Blocks.SANDSTONE.getDefaultState()).addDecorator(SpheroidDecorators.CACTUS, 0.3F).addDecorator(SpheroidDecorators.DEAD_GRASS, 0.3F);
    public static final ModularSpheroidType RED_SAND = (ModularSpheroidType) new ModularSpheroidType(SpheroidAdvancementIdentifier.red_sand, 5, 11,  Blocks.RED_SAND.getDefaultState()).setBottomBlockState(Blocks.RED_SANDSTONE.getDefaultState()).addDecorator(SpheroidDecorators.CACTUS, 0.3F).addDecorator(SpheroidDecorators.DEAD_GRASS, 0.3F);
    public static final ModularSpheroidType GRAVEL = new ModularSpheroidType(SpheroidAdvancementIdentifier.gravel, 6, 10,  Blocks.GRAVEL.getDefaultState()).setBottomBlockState(Blocks.COBBLESTONE.getDefaultState());
    public static final ModularSpheroidType COBBLESTONE = new ModularSpheroidType(SpheroidAdvancementIdentifier.cobblestone, 5, 9,  Blocks.COBBLESTONE.getDefaultState());
    public static final ModularSpheroidType MOSSY_COBBLESTONE = new ModularSpheroidType(SpheroidAdvancementIdentifier.mossy_cobblestone, 5, 6,  Blocks.MOSSY_COBBLESTONE.getDefaultState());
    public static final ModularSpheroidType COARSE_DIRT = new ModularSpheroidType(SpheroidAdvancementIdentifier.coarse_dirt, 5, 6,  Blocks.COARSE_DIRT.getDefaultState());

    // GIANT SPHERES
    public static final CaveSpheroidType HUGE_MONSTER_CAVE = (CaveSpheroidType) new CaveSpheroidType(SpheroidAdvancementIdentifier.cave, 20, 30, Blocks.DIRT.getDefaultState(), Blocks.COARSE_DIRT.getDefaultState(), 2, 5).setTopBlockState(Blocks.GRASS_BLOCK.getDefaultState()).addDecorator(SpheroidDecorators.MUSHROOMS, 0.3F);
    public static final DoubleCoreSpheroidType THE_SUN = new DoubleCoreSpheroidType(SpheroidAdvancementIdentifier.the_sun, 50, 50, Blocks.NETHERITE_BLOCK.getDefaultState(), Blocks.GOLD_BLOCK.getDefaultState(), Blocks.GLOWSTONE.getDefaultState(), 2, 2, 45, 45);

    // CORALS
    public static final CoralSpheroidType CORALS_GLASS = new CoralSpheroidType(SpheroidAdvancementIdentifier.coral, 10, 20, MAP_GLASS, SpheroidList.LIST_FULL_CORAL_BLOCKS, SpheroidList.LIST_WATERLOGGABLE_CORAL_BLOCKS, 1, 2);
    public static final CoralSpheroidType CORALS_STONE = new CoralSpheroidType(SpheroidAdvancementIdentifier.coral, 10, 20, MAP_STONES, SpheroidList.LIST_FULL_CORAL_BLOCKS, SpheroidList.LIST_WATERLOGGABLE_CORAL_BLOCKS, 1, 2);

    //GLASS
    public static final ModularSpheroidType GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.glass, 5, 8,  Blocks.GLASS.getDefaultState());
    public static final ModularSpheroidType BLACK_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.BLACK_STAINED_GLASS.getDefaultState());
    public static final ModularSpheroidType BLUE_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.BLUE_STAINED_GLASS.getDefaultState());
    public static final ModularSpheroidType BROWN_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.BROWN_STAINED_GLASS.getDefaultState());
    public static final ModularSpheroidType CYAN_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.CYAN_STAINED_GLASS.getDefaultState());
    public static final ModularSpheroidType GRAY_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.GRAY_STAINED_GLASS.getDefaultState());
    public static final ModularSpheroidType GREEN_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.GREEN_STAINED_GLASS.getDefaultState());
    public static final ModularSpheroidType LIGHT_BLUE_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.LIGHT_BLUE_STAINED_GLASS.getDefaultState());
    public static final ModularSpheroidType LIGHT_GRAY_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.LIGHT_GRAY_STAINED_GLASS.getDefaultState());
    public static final ModularSpheroidType LIME_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.LIME_STAINED_GLASS.getDefaultState());
    public static final ModularSpheroidType MAGENTA_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.MAGENTA_STAINED_GLASS.getDefaultState());
    public static final ModularSpheroidType ORANGE_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.ORANGE_STAINED_GLASS.getDefaultState());
    public static final ModularSpheroidType PINK_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.PINK_STAINED_GLASS.getDefaultState());
    public static final ModularSpheroidType PURPLE_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.PURPLE_STAINED_GLASS.getDefaultState());
    public static final ModularSpheroidType RED_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.RED_STAINED_GLASS.getDefaultState());
    public static final ModularSpheroidType WHITE_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.WHITE_STAINED_GLASS.getDefaultState());
    public static final ModularSpheroidType YELLOW_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.YELLOW_STAINED_GLASS.getDefaultState());

    //RARE
    public static final ModularSpheroidType OBSIDIAN = new ModularSpheroidType(SpheroidAdvancementIdentifier.obsidian, 3, 6,  Blocks.OBSIDIAN.getDefaultState());
    public static final ModularSpheroidType GLOWSTONE = new ModularSpheroidType(SpheroidAdvancementIdentifier.glowstone, 5, 10,  Blocks.GLOWSTONE.getDefaultState());
    public static final ModularSpheroidType BEDROCK = new ModularSpheroidType(SpheroidAdvancementIdentifier.bedrock, 3, 5,  Blocks.BEDROCK.getDefaultState());
    public static final ShellSpheroidType STONE_HOLLOW = (ShellSpheroidType) new ShellSpheroidType(SpheroidAdvancementIdentifier.cave, 5, 20, Blocks.CAVE_AIR.getDefaultState(), MAP_STONES, 3, 8).addDecorator(SpheroidDecorators.MUSHROOMS, 0.3F);
    public static final OceanMonumentSpheroidType OCEAN_MONUMENT = new OceanMonumentSpheroidType(SpheroidAdvancementIdentifier.ocean_monument, 25, 35, 3, 5, 2, 3);

    // ORES
    public static final CoreSpheroidType COAL     = new CoreSpheroidType(SpheroidAdvancementIdentifier.coal, 5, 15, Blocks.COAL_ORE.getDefaultState(), MAP_STONES, 4, 8);
    public static final CoreSpheroidType IRON     = new CoreSpheroidType(SpheroidAdvancementIdentifier.iron, 5, 12, Blocks.IRON_ORE.getDefaultState(), MAP_STONES, 3, 5);
    public static final CoreSpheroidType GOLD     = new CoreSpheroidType(SpheroidAdvancementIdentifier.gold, 5, 10, Blocks.GOLD_ORE.getDefaultState(), MAP_STONES, 2, 4);
    public static final CoreSpheroidType DIAMOND  = new CoreSpheroidType(SpheroidAdvancementIdentifier.diamond, 3, 7, Blocks.DIAMOND_ORE.getDefaultState(), MAP_STONES, 1, 3);
    public static final CoreSpheroidType REDSTONE = new CoreSpheroidType(SpheroidAdvancementIdentifier.redstone, 5, 15, Blocks.REDSTONE_ORE.getDefaultState(), MAP_STONES, 4, 8);
    public static final CoreSpheroidType LAPIS    = new CoreSpheroidType(SpheroidAdvancementIdentifier.lapis, 5, 8, Blocks.LAPIS_ORE.getDefaultState(), MAP_STONES, 2, 4);
    public static final CoreSpheroidType EMERALD  = new CoreSpheroidType(SpheroidAdvancementIdentifier.emerald, 5, 6, Blocks.EMERALD_ORE.getDefaultState(), MAP_STONES, 1, 3);

    // "ORES"
    public static final CoreSpheroidType BONE       = new CoreSpheroidType(SpheroidAdvancementIdentifier.bone, 4, 8, Blocks.BONE_BLOCK.getDefaultState(), MAP_STONES, 2, 4);
    public static final CoreSpheroidType HAY        = new CoreSpheroidType(SpheroidAdvancementIdentifier.hay, 4, 8, Blocks.HAY_BLOCK.getDefaultState(), MAP_STONES, 2, 4);
    public static final CoreSpheroidType PRISMARINE = new CoreSpheroidType(SpheroidAdvancementIdentifier.prismarine, 4, 8, Blocks.PRISMARINE.getDefaultState(), MAP_STONES, 2, 4);
    public static final CoreSpheroidType SLIME      = new CoreSpheroidType(SpheroidAdvancementIdentifier.slime, 4, 8, Blocks.SLIME_BLOCK.getDefaultState(), MAP_STONES, 2, 4);
    public static final CoreSpheroidType TNT      = new CoreSpheroidType(SpheroidAdvancementIdentifier.tnt, 4, 8, Blocks.TNT.getDefaultState(), MAP_STONES, 2, 4);

    //WOOD
    public static final ShellSpheroidType OAK_WOOD      = new ShellSpheroidType(SpheroidAdvancementIdentifier.oak_wood, 8, 15, Blocks.OAK_WOOD.getDefaultState(),      Blocks.OAK_LEAVES.getDefaultState().with(Properties.DISTANCE_1_7, 1),      2, 3);
    public static final ShellSpheroidType SPRUCE_WOOD   = new ShellSpheroidType(SpheroidAdvancementIdentifier.spruce_wood, 5, 15, Blocks.SPRUCE_WOOD.getDefaultState(),   Blocks.SPRUCE_LEAVES.getDefaultState().with(Properties.DISTANCE_1_7, 1),   2, 3);
    public static final ShellSpheroidType JUNGLE_WOOD   = (ShellSpheroidType) new ShellSpheroidType(SpheroidAdvancementIdentifier.jungle_wood, 10, 20, Blocks.JUNGLE_WOOD.getDefaultState(),   Blocks.JUNGLE_LEAVES.getDefaultState().with(Properties.DISTANCE_1_7, 1),   2, 3).addDecorator(SpheroidDecorators.COCOA, 0.3F);
    public static final ShellSpheroidType DARK_OAK_WOOD = new ShellSpheroidType(SpheroidAdvancementIdentifier.dark_oak_wood, 5, 15, Blocks.DARK_OAK_WOOD.getDefaultState(), Blocks.DARK_OAK_LEAVES.getDefaultState().with(Properties.DISTANCE_1_7, 1), 2, 2);
    public static final ShellSpheroidType BIRCH_WOOD    = new ShellSpheroidType(SpheroidAdvancementIdentifier.birch_wood, 5, 15, Blocks.BIRCH_WOOD.getDefaultState(),    Blocks.BIRCH_LEAVES.getDefaultState().with(Properties.DISTANCE_1_7, 1), 2, 3);
    public static final ShellSpheroidType ACACIA_WOOD   = new ShellSpheroidType(SpheroidAdvancementIdentifier.acacia_wood, 5, 12, Blocks.ACACIA_WOOD.getDefaultState(),   Blocks.ACACIA_LEAVES.getDefaultState().with(Properties.DISTANCE_1_7, 1),    2, 2);

    //FLUIDS
    public static final LiquidSpheroidType WATER_GLASS      = (LiquidSpheroidType) new LiquidSpheroidType(SpheroidAdvancementIdentifier.water, 5, 15, Blocks.WATER.getDefaultState(), MAP_GLASS, 1, 2, 20, 100, 75).addDecorator(SpheroidDecorators.SEA_GREENS, 0.5F);
    public static final LiquidSpheroidType WATER_CLAY       = (LiquidSpheroidType) new LiquidSpheroidType(SpheroidAdvancementIdentifier.clay, 5, 10, Blocks.WATER.getDefaultState(), MAP_GLASS, 1, 2, 70, 100, 75).setCoreBlock(Blocks.CLAY.getDefaultState(), 3, 6).addDecorator(SpheroidDecorators.SEA_GREENS, 0.5F).addDecorator(SpheroidDecorators.SEA_GREENS, 0.75F);
    public static final LiquidSpheroidType WATER_SPONGE     = (LiquidSpheroidType) new LiquidSpheroidType(SpheroidAdvancementIdentifier.sponge, 5, 10, Blocks.WATER.getDefaultState(), MAP_GLASS, 1, 2, 70, 100, 75).setCoreBlock( Blocks.WET_SPONGE.getDefaultState(), 1, 3).addDecorator(SpheroidDecorators.SEA_GREENS, 0.25F);
    public static final LiquidSpheroidType WATER_SLIME      = (LiquidSpheroidType) new LiquidSpheroidType(SpheroidAdvancementIdentifier.slime, 5, 7, Blocks.WATER.getDefaultState(), MAP_GLASS, 1, 1, 70, 100, 50).setCoreBlock(Blocks.SLIME_BLOCK.getDefaultState(), 1, 3).addDecorator(SpheroidDecorators.SEA_GREENS, 0.25F);
    public static final LiquidSpheroidType WATER_ICE        = (LiquidSpheroidType) new LiquidSpheroidType(SpheroidAdvancementIdentifier.ice, 8, 20, Blocks.WATER.getDefaultState(), MAP_GLASS, 1, 2, 70, 100, 75).setCoreBlock(Blocks.ICE.getDefaultState(), 2, 4).addDecorator(SpheroidDecorators.SEA_GREENS, 0.25F);
    public static final LiquidSpheroidType WATER_PACKED_ICE = (LiquidSpheroidType) new LiquidSpheroidType(SpheroidAdvancementIdentifier.packed_ice, 8, 20, Blocks.WATER.getDefaultState(), Blocks.ICE.getDefaultState(), 1, 3, 70, 100, 75).setCoreBlock(Blocks.PACKED_ICE.getDefaultState(), 2, 4).addDecorator(SpheroidDecorators.SEA_GREENS, 0.15F);
    public static final LiquidSpheroidType LAVA_GLASS       = new LiquidSpheroidType(SpheroidAdvancementIdentifier.lava, 5, 20, Blocks.LAVA.getDefaultState(), MAP_GLASS, 1, 3, 25, 90,  25);
    public static final LiquidSpheroidType LAVA_STONE       = new LiquidSpheroidType(SpheroidAdvancementIdentifier.lava, 5, 20, Blocks.LAVA.getDefaultState(), MAP_STONES, 2, 6, 10, 100, 25);
    public static final LiquidSpheroidType LAVA_MAGMA       = new LiquidSpheroidType(SpheroidAdvancementIdentifier.magma, 5, 20, Blocks.LAVA.getDefaultState(), MAP_STONES, 3, 6, 70, 100, 25).setCoreBlock(Blocks.MAGMA_BLOCK.getDefaultState(), 2, 5);
    public static final LiquidSpheroidType LAVA_OBSIDIAN    = new LiquidSpheroidType(SpheroidAdvancementIdentifier.obsidian, 10, 20, Blocks.LAVA.getDefaultState(), MAP_STONES, 3, 6, 50, 100, 10).setCoreBlock(Blocks.OBSIDIAN.getDefaultState(), 2, 5);

    // MUSHROOMS
    public static final MushroomSpheroidType BROWN_MUSHROOM    = new MushroomSpheroidType(SpheroidAdvancementIdentifier.brown_mushroom, 4, 8, Blocks.MUSHROOM_STEM.getDefaultState(), Blocks.BROWN_MUSHROOM_BLOCK.getDefaultState(), 2, 3);
    public static final MushroomSpheroidType RED_MUSHROOM      = new MushroomSpheroidType(SpheroidAdvancementIdentifier.red_mushroom, 4, 8, Blocks.MUSHROOM_STEM.getDefaultState(), Blocks.RED_MUSHROOM_BLOCK.getDefaultState(), 2, 3);

    //COLD
    public static final CoreSpheroidType       SNOW_ICE        = (CoreSpheroidType) new CoreSpheroidType(SpheroidAdvancementIdentifier.ice, 5, 15, Blocks.ICE.getDefaultState(), Blocks.SNOW_BLOCK.getDefaultState(), 3, 6).addDecorator(SpheroidDecorators.SWEET_BERRIES, 0.4F);
    public static final CoreSpheroidType       ICE_PACKED_ICE  = new CoreSpheroidType(SpheroidAdvancementIdentifier.packed_ice, 5, 15, Blocks.PACKED_ICE.getDefaultState(), Blocks.ICE.getDefaultState(), 3, 6);
    public static final DoubleCoreSpheroidType SNOW_PACKED_ICE = (DoubleCoreSpheroidType) new DoubleCoreSpheroidType(SpheroidAdvancementIdentifier.ice, 5, 12, Blocks.PACKED_ICE.getDefaultState(), Blocks.ICE.getDefaultState(), Blocks.SNOW_BLOCK.getDefaultState(), 2, 4, 2, 4).addDecorator(SpheroidDecorators.SWEET_BERRIES, 0.4F);
    public static final DoubleCoreSpheroidType SNOW_BLUE_ICE   = (DoubleCoreSpheroidType) new DoubleCoreSpheroidType(SpheroidAdvancementIdentifier.blue_ice, 5, 8, Blocks.BLUE_ICE.getDefaultState(), Blocks.PACKED_ICE.getDefaultState(), Blocks.SNOW_BLOCK.getDefaultState(), 2, 3, 1, 3).addDecorator(SpheroidDecorators.SWEET_BERRIES, 0.1F);;
    public static final DoubleCoreSpheroidType ICE_BLUE_ICE    = new DoubleCoreSpheroidType(SpheroidAdvancementIdentifier.blue_ice, 5, 8, Blocks.BLUE_ICE.getDefaultState(), Blocks.PACKED_ICE.getDefaultState(), Blocks.ICE.getDefaultState(), 2, 3, 1, 3);

    //RAINBOW
    public static final RainbowSpheroidType RAINBOW_WOOL       = new RainbowSpheroidType(SpheroidAdvancementIdentifier.rainbow_wool, 5, 16, LIST_WOOL);
    public static final RainbowSpheroidType RAINBOW_GLASS      = new RainbowSpheroidType(SpheroidAdvancementIdentifier.rainbow_glass, 5, 16, LIST_STAINED_GLASS);
    public static final RainbowSpheroidType RAINBOW_CONCRETE   = new RainbowSpheroidType(SpheroidAdvancementIdentifier.rainbow_concrete, 5, 16, LIST_CONCRETE);
    public static final RainbowSpheroidType RAINBOW_TERRACOTTA = new RainbowSpheroidType(SpheroidAdvancementIdentifier.rainbow_terracotta, 5, 16, LIST_TERRACOTTA);

    //SPECIAL
    public static final DungeonSpheroidType DUNGEON_ZOMBIE = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, 6, 12,  EntityType.ZOMBIE,  MAP_DUNGEON_STONES, 2,  4);
    public static final DungeonSpheroidType DUNGEON_SKELETON = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, 6, 12,  EntityType.SKELETON,  MAP_DUNGEON_STONES, 2,  4);
    public static final DungeonSpheroidType DUNGEON_SPIDER = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, 6, 12,  EntityType.SPIDER,  MAP_DUNGEON_STONES, 2,  4);
    public static final DungeonSpheroidType DUNGEON_CREEPER = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, 6, 12,  EntityType.CREEPER,  MAP_DUNGEON_STONES, 2,  4);
    public static final DungeonSpheroidType DUNGEON_CAVE_SPIDER = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, 6, 12,  EntityType.CAVE_SPIDER,  MAP_DUNGEON_STONES, 2,  4);
    public static final DungeonSpheroidType DUNGEON_SLIME = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, 6, 12,  EntityType.SLIME,  MAP_DUNGEON_STONES, 2,  4);
    public static final DungeonSpheroidType DUNGEON_DROWNED = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, 6, 12,  EntityType.DROWNED,  MAP_DUNGEON_STONES, 2,  4);
    public static final DungeonSpheroidType DUNGEON_HUSK = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, 6, 12,  EntityType.HUSK,  MAP_DUNGEON_STONES, 2,  4);
    public static final DungeonSpheroidType DUNGEON_STRAY = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, 6, 12,  EntityType.STRAY,  MAP_DUNGEON_STONES, 2,  4);
    public static final DungeonSpheroidType DUNGEON_WITCH = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, 6, 12,  EntityType.WITCH,  MAP_DUNGEON_STONES, 2,  4);
    public static final DungeonSpheroidType DUNGEON_SILVERFISH = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, 6, 12,  EntityType.SILVERFISH,  MAP_DUNGEON_STONES, 2,  4);
    public static final DungeonSpheroidType DUNGEON_ENDERMAN = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, 6, 12,  EntityType.ENDERMAN,  MAP_DUNGEON_STONES, 2,  4);

    // BEES
    public static final BeeHiveSpheroidType BEE_HIVE = new BeeHiveSpheroidType(SpheroidAdvancementIdentifier.bee_hive,10, 16, 2, 4, 1, 2, 2, 3);

    public static boolean shouldGenerate() {
        return true;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        StarrySkyCommon.LOGGER.info("Loading Vanilla Spheroids...");

        // COMMON
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.ESSENTIAL, 10.0F, GRASS);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.ESSENTIAL,  4.0F, SAND);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.ESSENTIAL,  1.0F, RED_SAND);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.ESSENTIAL,  1.0F, GRAVEL);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.ESSENTIAL,  8.0F, GLOWSTONE);

        // STONES
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 5.0F, STONE);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 2.2F, GRANITE);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 2.2F, DIORITE);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 2.0F, ANDESITE);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 0.5F, COBBLESTONE);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 0.1F, MOSSY_COBBLESTONE);

        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 2.0F, PODZOL);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 1.0F, STONE_HOLLOW);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 0.5F, COARSE_DIRT);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 0.5F, OBSIDIAN);

        //COLD
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 1.5F, SNOW_ICE);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 1.5F, SNOW_PACKED_ICE);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 0.4F, ICE_PACKED_ICE);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 0.5F, SNOW_BLUE_ICE);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 0.4F, ICE_BLUE_ICE);

        // TRANSLUCENT - GLASS
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 0.1F, GLASS);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 0.02F, BLACK_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 0.02F, BLUE_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 0.02F, BROWN_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 0.02F, CYAN_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 0.02F, GRAY_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 0.02F, GREEN_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 0.02F, LIGHT_BLUE_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 0.02F, LIGHT_GRAY_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 0.02F, LIME_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 0.02F, MAGENTA_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 0.02F, ORANGE_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 0.02F, PINK_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 0.02F, PURPLE_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 0.02F, RED_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 0.02F, WHITE_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 0.02F, YELLOW_STAINED_GLASS);

        spheroidLoader.registerSpheroidType(SpheroidDistributionType.TREASURE, 5.0F, BEE_HIVE);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.TREASURE, 1.0F, CORALS_GLASS);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.TREASURE, 1.0F, CORALS_STONE);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.TREASURE, 1.0F, BROWN_MUSHROOM);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.TREASURE, 0.7F, RED_MUSHROOM);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.TREASURE, 1.0F, HUGE_MONSTER_CAVE);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.TREASURE, 1.0F, MYCELIUM);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.TREASURE, 0.1F, BEDROCK);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.TREASURE, 0.05F, THE_SUN);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.TREASURE, 0.5F, OCEAN_MONUMENT); // TODO: Lower

        //RAINBOW
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.TREASURE, 1.0F, RAINBOW_WOOL);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.TREASURE, 1.0F, RAINBOW_GLASS);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.TREASURE, 1.0F, RAINBOW_CONCRETE);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.TREASURE, 1.0F, RAINBOW_TERRACOTTA);

        // FLUID - WATER
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.FLUID, 5.0F, WATER_GLASS);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.FLUID, 2.0F, WATER_CLAY);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.FLUID, 0.2F, WATER_SPONGE);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.FLUID, 0.2F, WATER_SLIME);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.FLUID, 1.0F, WATER_ICE);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.FLUID, 0.8F, WATER_PACKED_ICE);

        // FLUID - LAVA
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.FLUID, 3.0F, LAVA_GLASS);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.FLUID, 3.0F, LAVA_STONE);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.FLUID, 2.0F, LAVA_OBSIDIAN);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.FLUID, 1.0F, LAVA_MAGMA);

        //ORES
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.ORE, 9.0F, COAL);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.ORE, 7.0F, IRON);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.ORE, 1.0F, GOLD);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.ORE, 0.5F, DIAMOND);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.ORE, 4.0F, REDSTONE);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.ORE, 0.8F, LAPIS);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.ORE, 0.1F, EMERALD);

        // "ORES"
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.ORE, 0.05F, BONE);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.ORE, 0.05F, HAY);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.ORE, 0.05F, PRISMARINE);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.ORE, 0.05F, SLIME);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.ORE, 0.05F, TNT);

        //WOOD
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.WOOD, 5.0F, OAK_WOOD);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.WOOD, 2.5F, SPRUCE_WOOD);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.WOOD, 2.5F, BIRCH_WOOD);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.WOOD, 1.5F, JUNGLE_WOOD);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.WOOD, 1.5F, DARK_OAK_WOOD);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.WOOD, 1.5F, ACACIA_WOOD);

        // DUNGEON
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DUNGEON,  3.0F, DUNGEON_ZOMBIE);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DUNGEON,  2.0F, DUNGEON_SKELETON);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DUNGEON,  2.0F, DUNGEON_SPIDER);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DUNGEON,  1.0F, DUNGEON_WITCH);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DUNGEON,  0.8F, DUNGEON_CREEPER);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DUNGEON,  0.3F, DUNGEON_CAVE_SPIDER);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DUNGEON,  0.1F, DUNGEON_SLIME);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DUNGEON,  0.1F, DUNGEON_DROWNED);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DUNGEON,  0.1F, DUNGEON_HUSK);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DUNGEON,  0.1F, DUNGEON_STRAY);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DUNGEON,  0.1F, DUNGEON_SILVERFISH);
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DUNGEON,  0.05F, DUNGEON_ENDERMAN);
    }
}
