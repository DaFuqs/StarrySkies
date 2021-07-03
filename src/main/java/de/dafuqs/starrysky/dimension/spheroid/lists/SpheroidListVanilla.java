package de.dafuqs.starrysky.dimension.spheroid.lists;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.dimension.SpheroidDistributionType;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import de.dafuqs.starrysky.dimension.decorators.*;
import de.dafuqs.starrysky.dimension.decorators.overworld.*;
import de.dafuqs.starrysky.dimension.spheroid.SpheroidEntitySpawnDefinitions;
import de.dafuqs.starrysky.dimension.spheroid.types.*;
import de.dafuqs.starrysky.dimension.spheroid.types.unique.BeeHiveSpheroidType;
import de.dafuqs.starrysky.dimension.spheroid.types.unique.GeodeSpheroidType;
import de.dafuqs.starrysky.dimension.spheroid.types.unique.OceanMonumentSpheroidType;
import de.dafuqs.starrysky.dimension.spheroid.types.unique.StrongholdSpheroidType;
import net.minecraft.block.BambooBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootTables;
import net.minecraft.state.property.Properties;

import static de.dafuqs.starrysky.dimension.SpheroidLoader.SpheroidDimensionType.OVERWORLD;
import static org.apache.logging.log4j.Level.INFO;

public class SpheroidListVanilla extends SpheroidList {

    // DECORATORS
    public static class SpheroidDecorators {
        public static SpheroidDecorator CACTUS = new BlockStackDecorator(Blocks.CACTUS.getDefaultState(), 0.1F, 1, 3).requiresAirAroundOfIt();
        public static SpheroidDecorator SEA_GREENS = new SeaGreensDecorator();
        public static SpheroidDecorator COCOA = new CocoaDecorator();
        public static SpheroidDecorator BAMBOO = new BambooDecorator(Blocks.BAMBOO.getDefaultState().with(BambooBlock.AGE, 0).with(BambooBlock.STAGE, 0), Blocks.BAMBOO_SAPLING.getDefaultState());
        public static SpheroidDecorator SUGAR_CANE_POND = new SugarCanePondDecorator();
        public static SpheroidDecorator CENTER_POND_UNDERWATER_RUIN_SMALL_CHEST = new CenterPondDecorator(Blocks.SAND.getDefaultState(), Blocks.WATER.getDefaultState(), LootTables.UNDERWATER_RUIN_SMALL_CHEST, 0.5F);
        public static SpheroidDecorator CENTER_POND_UNDERWATER_RUIN_BIG_CHEST = new CenterPondDecorator(Blocks.GRAVEL.getDefaultState(), Blocks.WATER.getDefaultState(), LootTables.UNDERWATER_RUIN_BIG_CHEST, 0.5F);
        public static SpheroidDecorator CENTER_POND_SHIPWRECK_CHEST = new CenterPondDecorator(Blocks.SAND.getDefaultState(), Blocks.WATER.getDefaultState(), LootTables.SHIPWRECK_TREASURE_CHEST, 0.5F);
        public static SpheroidDecorator CENTER_POND_LAVA = new CenterPondDecorator(Blocks.OBSIDIAN.getDefaultState(), Blocks.LAVA.getDefaultState(), LootTables.RUINED_PORTAL_CHEST, 0.25F);
        public static SpheroidDecorator MUSHROOMS_BROWN = new SingleBlockDecorator(Blocks.BROWN_MUSHROOM.getDefaultState(), 0.05F);
        public static SpheroidDecorator MUSHROOMS_RED = new SingleBlockDecorator(Blocks.RED_MUSHROOM.getDefaultState(), 0.03F);
        public static SpheroidDecorator DEAD_GRASS = new SingleBlockDecorator(Blocks.DEAD_BUSH.getDefaultState(), 0.05F);
        public static SpheroidDecorator SWEET_BERRIES = new SingleBlockDecorator(Blocks.SWEET_BERRY_BUSH.getDefaultState(), 0.03F);
        public static SingleBlockDecorator FERNS_DECORATOR = new SingleBlockDecorator(Blocks.FERN.getDefaultState(), 0.15F);
        public static TallFlowerBlockDecorator LARGE_FERNS_DECORATOR = new TallFlowerBlockDecorator(Blocks.LARGE_FERN.getDefaultState(), 0.15F);
        public static RuinedPortalDecorator RUINED_PORTAL_DECORATOR = new RuinedPortalDecorator(LootTables.RUINED_PORTAL_CHEST);

        public static SpheroidDecorator POINTED_DRIPSTONE_UP = new PointedDripstoneDecorator(0.2F, PointedDripstoneDecorator.PointedDripstoneDecoratorMode.UP);
        public static SpheroidDecorator POINTED_DRIPSTONE_CAVE = new PointedDripstoneDecorator(0.2F, PointedDripstoneDecorator.PointedDripstoneDecoratorMode.CAVE);
        public static SpheroidDecorator GLOW_LICHEN = new GlowLichenDecorator(0.1F);
        public static SpheroidDecorator HANGING_ROOTS = new BottomBlockDecorator(Blocks.HANGING_ROOTS.getDefaultState(), 0.2F);

        public static SpheroidDecorator AZALEA = new SingleBlockDecorator(Blocks.AZALEA.getDefaultState(), 0.02F);
        public static SpheroidDecorator FLOWERING_AZALEA = new SingleBlockDecorator(Blocks.FLOWERING_AZALEA.getDefaultState(), 0.02F);

        public static XMarksTheSpotDecorator X_SPOT_DESERT_PYRAMID = new XMarksTheSpotDecorator(LootTables.DESERT_PYRAMID_CHEST, Blocks.ORANGE_TERRACOTTA.getDefaultState());
        public static XMarksTheSpotDecorator X_SPOT_JUNGLE_TEMPLE = new XMarksTheSpotDecorator(LootTables.JUNGLE_TEMPLE_CHEST, Blocks.MOSSY_COBBLESTONE.getDefaultState());
        public static XMarksTheSpotDecorator X_SPOT_WOODLAND_MANSION = new XMarksTheSpotDecorator(LootTables.WOODLAND_MANSION_CHEST, Blocks.STRIPPED_BIRCH_WOOD.getDefaultState());
        public static XMarksTheSpotDecorator X_SPOT_BURIED_TREASURE = new XMarksTheSpotDecorator(LootTables.BURIED_TREASURE_CHEST, Blocks.PRISMARINE.getDefaultState());
        public static XMarksTheSpotDecorator X_SPOT_PILLAGER_OUTPOST = new XMarksTheSpotDecorator(LootTables.PILLAGER_OUTPOST_CHEST, Blocks.STRIPPED_DARK_OAK_WOOD.getDefaultState());
        public static XMarksTheSpotDecorator X_SPOT_RUINED_PORTAL = new XMarksTheSpotDecorator(LootTables.RUINED_PORTAL_CHEST, Blocks.OBSIDIAN.getDefaultState());
    }

    // SPHEROID TYPES
    // BASIC
    public static final SpheroidType GRASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.grass, 5, 20,  Blocks.DIRT.getDefaultState())
            .setTopBlockState(Blocks.GRASS_BLOCK.getDefaultState())
            .addDecorator(SpheroidDecorators.RUINED_PORTAL_DECORATOR, 0.03F)
            .addDecorator(SpheroidDecorators.SUGAR_CANE_POND, 0.15F)
            .addSpawn(SpheroidEntitySpawnDefinitions.COW, 0.25F)
            .addSpawn(SpheroidEntitySpawnDefinitions.SHEEP, 0.25F)
            .addSpawn(SpheroidEntitySpawnDefinitions.CHICKEN, 0.25F)
            .addSpawn(SpheroidEntitySpawnDefinitions.PIG, 0.25F)
            .addSpawn(SpheroidEntitySpawnDefinitions.WOLF, 0.08F)
            .addSpawn(SpheroidEntitySpawnDefinitions.HORSE, 0.08F)
            .addSpawn(SpheroidEntitySpawnDefinitions.DONKEY, 0.05F)
            .addSpawn(SpheroidEntitySpawnDefinitions.MULE, 0.05F)
            .addSpawn(SpheroidEntitySpawnDefinitions.GOAT, 0.03F);
    public static final SpheroidType BEACH_GRASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.beach, 10, 18,  Blocks.DIRT.getDefaultState())
            .setTopBlockState(Blocks.GRASS_BLOCK.getDefaultState())
            .addDecorator(SpheroidDecorators.CENTER_POND_UNDERWATER_RUIN_BIG_CHEST, 1.0F)
            .addSpawn(SpheroidEntitySpawnDefinitions.TURTLE, 0.7F);
    public static final SpheroidType BEACH_SAND = new ModularSpheroidType(SpheroidAdvancementIdentifier.beach, 10, 16,  Blocks.SAND.getDefaultState())
            .setBottomBlockState(Blocks.SANDSTONE.getDefaultState())
            .addDecorator(SpheroidDecorators.CENTER_POND_UNDERWATER_RUIN_SMALL_CHEST, 1.0F)
            .addSpawn(SpheroidEntitySpawnDefinitions.TURTLE, 0.7F);
    public static final SpheroidType MYCELIUM = new ModularSpheroidType(SpheroidAdvancementIdentifier.mycelium, 5, 8,  Blocks.DIRT.getDefaultState())
            .setTopBlockState(Blocks.MYCELIUM.getDefaultState())
            .addSpawn(SpheroidEntitySpawnDefinitions.MOOSHROOM, 1.0F);
    public static final SpheroidType PODZOL = new ModularSpheroidType(SpheroidAdvancementIdentifier.podzol, 5, 12,  Blocks.DIRT.getDefaultState())
            .setTopBlockState(Blocks.PODZOL.getDefaultState())
            .addDecorator(SpheroidDecorators.FERNS_DECORATOR, 0.8F)
            .addDecorator(SpheroidDecorators.LARGE_FERNS_DECORATOR, 0.8F);
    public static final SpheroidType JUNGLE = new ModularSpheroidType(SpheroidAdvancementIdentifier.podzol, 5, 12,  Blocks.DIRT.getDefaultState())
            .setTopBlockState(Blocks.PODZOL.getDefaultState())
            .addDecorator(SpheroidDecorators.BAMBOO, 1.0F)
            .addDecorator(SpheroidDecorators.X_SPOT_JUNGLE_TEMPLE, 0.1F)
            .addSpawn(SpheroidEntitySpawnDefinitions.PANDA, 0.4F)
            .addSpawn(SpheroidEntitySpawnDefinitions.OCELOT, 0.4F)
            .addSpawn(SpheroidEntitySpawnDefinitions.PARROT, 0.4F);
    public static final SpheroidType JUNGLE_POND = new ModularSpheroidType(SpheroidAdvancementIdentifier.beach, 10, 15,  Blocks.DIRT.getDefaultState())
            .setTopBlockState(Blocks.PODZOL.getDefaultState())
            .addDecorator(SpheroidDecorators.CENTER_POND_SHIPWRECK_CHEST, 1.0F)
            .addDecorator(SpheroidDecorators.BAMBOO, 0.5F)
            .addSpawn(SpheroidEntitySpawnDefinitions.PARROT, 0.4F);
    public static final SpheroidType STONE = new ModularSpheroidType(SpheroidAdvancementIdentifier.stone, 5, 13,  Blocks.STONE.getDefaultState())
            .addDecorator(SpheroidDecorators.CENTER_POND_LAVA, 0.1F)
            .addSpawn(SpheroidEntitySpawnDefinitions.LLAMA, 0.08F)
            .addSpawn(SpheroidEntitySpawnDefinitions.GOAT, 0.1F);
    public static final SpheroidType TUFF = new ModularSpheroidType(SpheroidAdvancementIdentifier.tuff, 6, 8,  Blocks.TUFF.getDefaultState());
    public static final SpheroidType DEEPSLATE = new ModularSpheroidType(SpheroidAdvancementIdentifier.deepslate, 5, 11,  Blocks.DEEPSLATE.getDefaultState())
            .addDecorator(SpheroidDecorators.CENTER_POND_LAVA, 0.2F);
    public static final SpheroidType COBBLED_DEEPSLATE = new ModularSpheroidType(SpheroidAdvancementIdentifier.cobbled_deepslate, 4, 10,  Blocks.COBBLED_DEEPSLATE.getDefaultState());
    public static final SpheroidType GRANITE = new ModularSpheroidType(SpheroidAdvancementIdentifier.granite, 6, 11,  Blocks.GRANITE.getDefaultState());
    public static final SpheroidType DIORITE = new ModularSpheroidType(SpheroidAdvancementIdentifier.diorite, 6, 11,  Blocks.DIORITE.getDefaultState());
    public static final SpheroidType ANDESITE = new ModularSpheroidType(SpheroidAdvancementIdentifier.andesite, 6, 11,  Blocks.ANDESITE.getDefaultState());
    public static final SpheroidType SAND = new ModularSpheroidType(SpheroidAdvancementIdentifier.sand, 5, 13,  Blocks.SAND.getDefaultState())
            .setBottomBlockState(Blocks.SANDSTONE.getDefaultState())
            .addDecorator(SpheroidDecorators.CACTUS, 0.3F)
            .addDecorator(SpheroidDecorators.DEAD_GRASS, 0.3F)
            .addDecorator(SpheroidDecorators.X_SPOT_DESERT_PYRAMID, 0.1F)
            .addSpawn(SpheroidEntitySpawnDefinitions.RABBIT, 0.5F);
    public static final SpheroidType RED_SAND = new ModularSpheroidType(SpheroidAdvancementIdentifier.red_sand, 5, 11,  Blocks.RED_SAND.getDefaultState())
            .setBottomBlockState(Blocks.RED_SANDSTONE.getDefaultState())
            .addDecorator(SpheroidDecorators.CACTUS, 0.3F)
            .addDecorator(SpheroidDecorators.DEAD_GRASS, 0.3F)
            .addSpawn(SpheroidEntitySpawnDefinitions.RABBIT, 0.5F);
    public static final SpheroidType GRAVEL = new ModularSpheroidType(SpheroidAdvancementIdentifier.gravel, 6, 10,  Blocks.GRAVEL.getDefaultState())
            .setBottomBlockState(Blocks.COBBLESTONE.getDefaultState());
    public static final SpheroidType COBBLESTONE = new ModularSpheroidType(SpheroidAdvancementIdentifier.cobblestone, 5, 9,  Blocks.COBBLESTONE.getDefaultState());
    public static final SpheroidType MOSSY_COBBLESTONE = new ModularSpheroidType(SpheroidAdvancementIdentifier.mossy_cobblestone, 5, 6,  Blocks.MOSSY_COBBLESTONE.getDefaultState());
    public static final SpheroidType COARSE_DIRT = new ModularSpheroidType(SpheroidAdvancementIdentifier.coarse_dirt, 5, 6,  Blocks.COARSE_DIRT.getDefaultState());

    // GIANT SPHERES
    public static final SpheroidType HUGE_MONSTER_CAVE = ((CaveSpheroidType) new CaveSpheroidType(SpheroidAdvancementIdentifier.cave, 20, 30, Blocks.DIRT.getDefaultState(), Blocks.COARSE_DIRT.getDefaultState(), 2, 5)
            .setTopBlockState(Blocks.GRASS_BLOCK.getDefaultState())
            .addDecorator(SpheroidDecorators.MUSHROOMS_RED, 0.3F)
            .addDecorator(SpheroidDecorators.MUSHROOMS_BROWN, 0.3F)
            .addDecorator(SpheroidDecorators.GLOW_LICHEN, 0.5F))
            .addChestWithLootTable(LootTables.ABANDONED_MINESHAFT_CHEST, 0.5F);
    public static final SpheroidType THE_SUN = new DoubleCoreSpheroidType(SpheroidAdvancementIdentifier.the_sun, 50, 50, Blocks.NETHERITE_BLOCK.getDefaultState(), Blocks.GOLD_BLOCK.getDefaultState(), Blocks.GLOWSTONE.getDefaultState(), 2, 2, 45, 45);

    // CORALS
    public static final SpheroidType CORALS_GLASS = new CoralSpheroidType(SpheroidAdvancementIdentifier.coral, 10, 20, MAP_GLASS, 1, 2)
            .addChestWithLootTable(LootTables.BURIED_TREASURE_CHEST, 0.5F);
    public static final SpheroidType CORALS_STONE = new CoralSpheroidType(SpheroidAdvancementIdentifier.coral, 10, 20, MAP_STONES, 1, 2)
            .addChestWithLootTable(LootTables.BURIED_TREASURE_CHEST, 0.5F);

    // GLASS
    public static final SpheroidType GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.glass, 5, 8,  Blocks.GLASS.getDefaultState());
    public static final SpheroidType BLACK_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.BLACK_STAINED_GLASS.getDefaultState());
    public static final SpheroidType BLUE_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.BLUE_STAINED_GLASS.getDefaultState());
    public static final SpheroidType BROWN_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.BROWN_STAINED_GLASS.getDefaultState());
    public static final SpheroidType CYAN_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.CYAN_STAINED_GLASS.getDefaultState());
    public static final SpheroidType GRAY_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.GRAY_STAINED_GLASS.getDefaultState());
    public static final SpheroidType GREEN_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.GREEN_STAINED_GLASS.getDefaultState());
    public static final SpheroidType LIGHT_BLUE_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.LIGHT_BLUE_STAINED_GLASS.getDefaultState());
    public static final SpheroidType LIGHT_GRAY_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.LIGHT_GRAY_STAINED_GLASS.getDefaultState());
    public static final SpheroidType LIME_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.LIME_STAINED_GLASS.getDefaultState());
    public static final SpheroidType MAGENTA_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.MAGENTA_STAINED_GLASS.getDefaultState());
    public static final SpheroidType ORANGE_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.ORANGE_STAINED_GLASS.getDefaultState());
    public static final SpheroidType PINK_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.PINK_STAINED_GLASS.getDefaultState());
    public static final SpheroidType PURPLE_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.PURPLE_STAINED_GLASS.getDefaultState());
    public static final SpheroidType RED_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.RED_STAINED_GLASS.getDefaultState());
    public static final SpheroidType WHITE_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.WHITE_STAINED_GLASS.getDefaultState());
    public static final SpheroidType YELLOW_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 10,  Blocks.YELLOW_STAINED_GLASS.getDefaultState());
    public static final SpheroidType TINTED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.tinted_glass, 3, 5,  Blocks.TINTED_GLASS.getDefaultState());

    // RARE
    public static final SpheroidType OBSIDIAN = new ModularSpheroidType(SpheroidAdvancementIdentifier.obsidian, 3, 6,  Blocks.OBSIDIAN.getDefaultState());
    public static final SpheroidType GLOWSTONE = new ModularSpheroidType(SpheroidAdvancementIdentifier.glowstone, 5, 10,  Blocks.GLOWSTONE.getDefaultState());
    public static final SpheroidType BEDROCK = new ModularSpheroidType(SpheroidAdvancementIdentifier.bedrock, 3, 5,  Blocks.BEDROCK.getDefaultState());
    public static final SpheroidType STONE_HOLLOW = new ShellSpheroidType(SpheroidAdvancementIdentifier.cave, 5, 20, Blocks.CAVE_AIR.getDefaultState(), MAP_STONES, 3, 8)
            .addDecorator(SpheroidDecorators.MUSHROOMS_RED, 0.3F)
            .addDecorator(SpheroidDecorators.MUSHROOMS_BROWN, 0.3F);

    // ORES
    public static final SpheroidType COAL     = new CoreSpheroidType(SpheroidAdvancementIdentifier.coal, 5, 15, Blocks.COAL_ORE.getDefaultState(), MAP_STONES, 4, 8);
    public static final SpheroidType IRON     = new CoreSpheroidType(SpheroidAdvancementIdentifier.iron, 5, 12, Blocks.IRON_ORE.getDefaultState(), MAP_STONES, 3, 5);
    public static final SpheroidType GOLD     = new CoreSpheroidType(SpheroidAdvancementIdentifier.gold, 5, 10, Blocks.GOLD_ORE.getDefaultState(), MAP_STONES, 2, 4);
    public static final SpheroidType DIAMOND  = new CoreSpheroidType(SpheroidAdvancementIdentifier.diamond, 3, 7, Blocks.DIAMOND_ORE.getDefaultState(), MAP_STONES, 1, 3);
    public static final SpheroidType REDSTONE = new CoreSpheroidType(SpheroidAdvancementIdentifier.redstone, 5, 15, Blocks.REDSTONE_ORE.getDefaultState(), MAP_STONES, 4, 8);
    public static final SpheroidType LAPIS    = new CoreSpheroidType(SpheroidAdvancementIdentifier.lapis, 5, 8, Blocks.LAPIS_ORE.getDefaultState(), MAP_STONES, 2, 4);
    public static final SpheroidType EMERALD  = new CoreSpheroidType(SpheroidAdvancementIdentifier.emerald, 5, 6, Blocks.EMERALD_ORE.getDefaultState(), MAP_MOUNTAIN_STONES, 1, 3);
    public static final SpheroidType COPPER     = new CoreSpheroidType(SpheroidAdvancementIdentifier.copper, 5, 12, Blocks.COPPER_ORE.getDefaultState(), MAP_STONES, 4, 6);

    // DEEPSLATE ORES
    public static final SpheroidType DEEPSLATE_COAL     = new CoreSpheroidType(SpheroidAdvancementIdentifier.coal, 5, 15, Blocks.DEEPSLATE_COAL_ORE.getDefaultState(), Blocks.DEEPSLATE.getDefaultState(), 4, 8);
    public static final SpheroidType DEEPSLATE_IRON     = new CoreSpheroidType(SpheroidAdvancementIdentifier.iron, 5, 12, Blocks.DEEPSLATE_IRON_ORE.getDefaultState(), Blocks.DEEPSLATE.getDefaultState(), 3, 5);
    public static final SpheroidType DEEPSLATE_GOLD     = new CoreSpheroidType(SpheroidAdvancementIdentifier.gold, 5, 10, Blocks.DEEPSLATE_GOLD_ORE.getDefaultState(), MAP_DEEPSLATE, 2, 4);
    public static final SpheroidType DEEPSLATE_DIAMOND  = new CoreSpheroidType(SpheroidAdvancementIdentifier.diamond, 3, 7, Blocks.DEEPSLATE_DIAMOND_ORE.getDefaultState(), MAP_DEEPSLATE, 1, 3);
    public static final SpheroidType DEEPSLATE_REDSTONE = new CoreSpheroidType(SpheroidAdvancementIdentifier.redstone, 5, 15, Blocks.DEEPSLATE_REDSTONE_ORE.getDefaultState(), MAP_DEEPSLATE, 4, 8);
    public static final SpheroidType DEEPSLATE_LAPIS    = new CoreSpheroidType(SpheroidAdvancementIdentifier.lapis, 5, 8, Blocks.DEEPSLATE_LAPIS_ORE.getDefaultState(), MAP_DEEPSLATE, 2, 4);
    public static final SpheroidType DEEPSLATE_EMERALD  = new CoreSpheroidType(SpheroidAdvancementIdentifier.emerald, 5, 6, Blocks.DEEPSLATE_EMERALD_ORE.getDefaultState(), MAP_DEEPSLATE, 1, 3);
    public static final SpheroidType DEEPSLATE_COPPER   = new CoreSpheroidType(SpheroidAdvancementIdentifier.copper, 5, 12, Blocks.DEEPSLATE_COPPER_ORE.getDefaultState(), Blocks.DEEPSLATE.getDefaultState(), 4, 6);

    // "ORES"
    public static final SpheroidType BONE       = new CoreSpheroidType(SpheroidAdvancementIdentifier.bone, 4, 8, Blocks.BONE_BLOCK.getDefaultState(), MAP_STONES, 2, 4);
    public static final SpheroidType HAY        = new CoreSpheroidType(SpheroidAdvancementIdentifier.hay, 4, 8, Blocks.HAY_BLOCK.getDefaultState(), MAP_STONES, 2, 4);
    public static final SpheroidType PRISMARINE = new CoreSpheroidType(SpheroidAdvancementIdentifier.prismarine, 4, 8, Blocks.PRISMARINE.getDefaultState(), MAP_STONES, 2, 4);
    public static final SpheroidType SLIME      = new CoreSpheroidType(SpheroidAdvancementIdentifier.slime, 4, 8, Blocks.SLIME_BLOCK.getDefaultState(), MAP_STONES, 2, 4);
    public static final SpheroidType TNT      = new CoreSpheroidType(SpheroidAdvancementIdentifier.tnt, 4, 8, Blocks.TNT.getDefaultState(), MAP_STONES, 2, 4);

    // DRIPSTONE
    private static final BlockState DRIPSTONE_BLOCK_STATE = Blocks.DRIPSTONE_BLOCK.getDefaultState();
    public static final SpheroidType DRIPSTONE = new CoreSpheroidType(SpheroidAdvancementIdentifier.dripstone, 5, 12, DRIPSTONE_BLOCK_STATE, DRIPSTONE_BLOCK_STATE, 3, 5)
            .addDecorator(SpheroidDecorators.POINTED_DRIPSTONE_UP, 0.1F);
    public static final SpheroidType DRIPSTONE_COAL = new CoreSpheroidType(SpheroidAdvancementIdentifier.dripstone, 5, 12, DRIPSTONE_BLOCK_STATE, DRIPSTONE_BLOCK_STATE, 3, 5)
            .addDecorator(SpheroidDecorators.POINTED_DRIPSTONE_UP, 0.1F);
    public static final SpheroidType DRIPSTONE_IRON = new CoreSpheroidType(SpheroidAdvancementIdentifier.dripstone, 5, 12, DRIPSTONE_BLOCK_STATE, DRIPSTONE_BLOCK_STATE, 3, 5)
            .addDecorator(SpheroidDecorators.POINTED_DRIPSTONE_UP, 0.1F);
    public static final SpheroidType DRIPSTONE_CAVE = new CaveSpheroidType(SpheroidAdvancementIdentifier.cave, 5, 12, DRIPSTONE_BLOCK_STATE, DRIPSTONE_BLOCK_STATE, 2, 4)
            .addDecorator(SpheroidDecorators.POINTED_DRIPSTONE_CAVE, 1.0F)
            .addDecorator(SpheroidDecorators.GLOW_LICHEN, 0.3F);

    //WOOD
    public static final SpheroidType OAK_WOOD      = new ShellSpheroidType(SpheroidAdvancementIdentifier.oak_wood, 8, 15, Blocks.OAK_WOOD.getDefaultState(),      Blocks.OAK_LEAVES.getDefaultState().with(Properties.DISTANCE_1_7, 1),      2, 3);
    public static final SpheroidType SPRUCE_WOOD   = new ShellSpheroidType(SpheroidAdvancementIdentifier.spruce_wood, 5, 15, Blocks.SPRUCE_WOOD.getDefaultState(),   Blocks.SPRUCE_LEAVES.getDefaultState().with(Properties.DISTANCE_1_7, 1),   2, 3);
    public static final SpheroidType JUNGLE_WOOD   = new ShellSpheroidType(SpheroidAdvancementIdentifier.jungle_wood, 10, 20, Blocks.JUNGLE_WOOD.getDefaultState(),   Blocks.JUNGLE_LEAVES.getDefaultState().with(Properties.DISTANCE_1_7, 1),   2, 3)
            .addDecorator(SpheroidDecorators.COCOA, 0.3F);
    public static final SpheroidType DARK_OAK_WOOD = new ShellSpheroidType(SpheroidAdvancementIdentifier.dark_oak_wood, 5, 15, Blocks.DARK_OAK_WOOD.getDefaultState(), Blocks.DARK_OAK_LEAVES.getDefaultState().with(Properties.DISTANCE_1_7, 1), 2, 2)
            .addDecorator(SpheroidDecorators.X_SPOT_WOODLAND_MANSION, 0.1F);
    public static final SpheroidType BIRCH_WOOD    = new ShellSpheroidType(SpheroidAdvancementIdentifier.birch_wood, 5, 15, Blocks.BIRCH_WOOD.getDefaultState(),    Blocks.BIRCH_LEAVES.getDefaultState().with(Properties.DISTANCE_1_7, 1), 2, 3);
    public static final SpheroidType ACACIA_WOOD   = new ShellSpheroidType(SpheroidAdvancementIdentifier.acacia_wood, 5, 12, Blocks.ACACIA_WOOD.getDefaultState(),   Blocks.ACACIA_LEAVES.getDefaultState().with(Properties.DISTANCE_1_7, 1),    2, 2)
            .addDecorator(SpheroidDecorators.X_SPOT_PILLAGER_OUTPOST, 0.1F);
    public static final SpheroidType AZALEA_WOOD = new ShellSpheroidType(SpheroidAdvancementIdentifier.azalea_wood, 4, 7,  Blocks.OAK_LOG.getDefaultState(), Blocks.AZALEA_LEAVES.getDefaultState().with(Properties.DISTANCE_1_7, 1), 2, 3)
            .addShellSpeckles(Blocks.FLOWERING_AZALEA_LEAVES.getDefaultState().with(Properties.DISTANCE_1_7, 1), 0.15F);

    // WATER
    public static final SpheroidType WATER_GLASS = new LiquidSpheroidType(SpheroidAdvancementIdentifier.water, 5, 15, Blocks.WATER.getDefaultState(), MAP_GLASS, 1, 2, 20, 100, 75)
            .addDecorator(SpheroidDecorators.SEA_GREENS, 0.5F);
    public static final SpheroidType WATER_CLAY = new LiquidSpheroidType(SpheroidAdvancementIdentifier.clay, 5, 10, Blocks.WATER.getDefaultState(), MAP_GLASS, 1, 2, 70, 100, 75)
            .setCoreBlock(Blocks.CLAY.getDefaultState(), 3, 6)
            .addDecorator(SpheroidDecorators.SEA_GREENS, 0.75F)
            .addDecorator(SpheroidDecorators.X_SPOT_BURIED_TREASURE, 0.1F);
    public static final SpheroidType WATER_SPONGE = new LiquidSpheroidType(SpheroidAdvancementIdentifier.sponge, 5, 10, Blocks.WATER.getDefaultState(), MAP_GLASS, 1, 2, 70, 100, 75)
            .setCoreBlock( Blocks.WET_SPONGE.getDefaultState(), 1, 3).addDecorator(SpheroidDecorators.SEA_GREENS, 0.25F);
    public static final SpheroidType WATER_SLIME = new LiquidSpheroidType(SpheroidAdvancementIdentifier.slime, 5, 7, Blocks.WATER.getDefaultState(), MAP_GLASS, 1, 1, 70, 100, 50)
            .setCoreBlock(Blocks.SLIME_BLOCK.getDefaultState(), 1, 3).addDecorator(SpheroidDecorators.SEA_GREENS, 0.25F);
    public static final SpheroidType WATER_ICE = new LiquidSpheroidType(SpheroidAdvancementIdentifier.ice, 8, 20, Blocks.WATER.getDefaultState(), MAP_GLASS, 1, 2, 70, 100, 75)
            .setCoreBlock(Blocks.ICE.getDefaultState(), 2, 4)
            .addDecorator(SpheroidDecorators.SEA_GREENS, 0.25F)
            .addDecorator(SpheroidDecorators.X_SPOT_BURIED_TREASURE, 0.1F);
    public static final SpheroidType WATER_PACKED_ICE = new LiquidSpheroidType(SpheroidAdvancementIdentifier.packed_ice, 8, 20, Blocks.WATER.getDefaultState(), Blocks.ICE.getDefaultState(), 1, 3, 70, 100, 75)
            .setCoreBlock(Blocks.PACKED_ICE.getDefaultState(), 2, 4)
            .addDecorator(SpheroidDecorators.SEA_GREENS, 0.15F);

    // LAVA
    public static final SpheroidType LAVA_GLASS = new LiquidSpheroidType(SpheroidAdvancementIdentifier.lava, 5, 20, Blocks.LAVA.getDefaultState(), MAP_GLASS, 1, 3, 25, 90,  25);
    public static final SpheroidType LAVA_STONE = new LiquidSpheroidType(SpheroidAdvancementIdentifier.lava, 5, 20, Blocks.LAVA.getDefaultState(), MAP_STONES, 2, 6, 10, 100, 25);
    public static final SpheroidType LAVA_MAGMA = new LiquidSpheroidType(SpheroidAdvancementIdentifier.magma, 5, 20, Blocks.LAVA.getDefaultState(), MAP_STONES, 3, 6, 70, 100, 25)
            .setCoreBlock(Blocks.MAGMA_BLOCK.getDefaultState(), 2, 5)
            .addDecorator(SpheroidDecorators.X_SPOT_RUINED_PORTAL, 0.1F);
    public static final SpheroidType LAVA_OBSIDIAN = new LiquidSpheroidType(SpheroidAdvancementIdentifier.obsidian, 10, 20, Blocks.LAVA.getDefaultState(), MAP_STONES, 3, 6, 50, 100, 10)
            .setCoreBlock(Blocks.OBSIDIAN.getDefaultState(), 2, 5)
            .addDecorator(SpheroidDecorators.X_SPOT_RUINED_PORTAL, 0.1F);

    // MUSHROOMS
    public static final SpheroidType BROWN_MUSHROOM = new MushroomSpheroidType(SpheroidAdvancementIdentifier.brown_mushroom, 4, 8, Blocks.MUSHROOM_STEM.getDefaultState(), Blocks.BROWN_MUSHROOM_BLOCK.getDefaultState(), 2, 3);
    public static final SpheroidType RED_MUSHROOM = new MushroomSpheroidType(SpheroidAdvancementIdentifier.red_mushroom, 4, 8, Blocks.MUSHROOM_STEM.getDefaultState(), Blocks.RED_MUSHROOM_BLOCK.getDefaultState(), 2, 3)
            .addSpawn(SpheroidEntitySpawnDefinitions.MOOSHROOM, 0.1F);

    // COLD
    public static final SpheroidType SNOW_CAVE = new CaveSpheroidType(SpheroidAdvancementIdentifier.cave, 6, 10, Blocks.ICE.getDefaultState(), Blocks.SNOW_BLOCK.getDefaultState(), 2, 4)
            .addChestWithLootTable(LootTables.IGLOO_CHEST_CHEST, 0.5F)
            .addSpawn(SpheroidEntitySpawnDefinitions.POLAR_BEAR, 0.3F)
            .addSpawn(SpheroidEntitySpawnDefinitions.GOAT, 0.25F)
            .addDecorator(SpheroidDecorators.GLOW_LICHEN, 0.1F);
    public static final SpheroidType SNOW_ICE = new CoreSpheroidType(SpheroidAdvancementIdentifier.ice, 5, 15, Blocks.ICE.getDefaultState(), Blocks.SNOW_BLOCK.getDefaultState(), 3, 6)
            .addDecorator(SpheroidDecorators.SWEET_BERRIES, 0.4F)
            .addSpawn(SpheroidEntitySpawnDefinitions.FOX, 0.4F);
    public static final SpheroidType ICE_PACKED_ICE = new CoreSpheroidType(SpheroidAdvancementIdentifier.packed_ice, 5, 15, Blocks.PACKED_ICE.getDefaultState(), Blocks.ICE.getDefaultState(), 3, 6);
    public static final SpheroidType SNOW_PACKED_ICE = new DoubleCoreSpheroidType(SpheroidAdvancementIdentifier.ice, 5, 12, Blocks.PACKED_ICE.getDefaultState(), Blocks.ICE.getDefaultState(), Blocks.SNOW_BLOCK.getDefaultState(), 2, 4, 2, 4)
            .addDecorator(SpheroidDecorators.SWEET_BERRIES, 0.4F)
            .addSpawn(SpheroidEntitySpawnDefinitions.FOX, 0.3F);
    public static final SpheroidType SNOW_BLUE_ICE = new DoubleCoreSpheroidType(SpheroidAdvancementIdentifier.blue_ice, 5, 8, Blocks.BLUE_ICE.getDefaultState(), Blocks.PACKED_ICE.getDefaultState(), Blocks.SNOW_BLOCK.getDefaultState(), 2, 3, 1, 3)
            .addDecorator(SpheroidDecorators.SWEET_BERRIES, 0.2F);
    public static final SpheroidType ICE_BLUE_ICE = new DoubleCoreSpheroidType(SpheroidAdvancementIdentifier.blue_ice, 5, 8, Blocks.BLUE_ICE.getDefaultState(), Blocks.PACKED_ICE.getDefaultState(), Blocks.ICE.getDefaultState(), 2, 3, 1, 3)
            .addSpawn(SpheroidEntitySpawnDefinitions.GOAT, 0.25F);
    public static final SpheroidType POWDER_SNOW_ICE = new ShellSpheroidType(SpheroidAdvancementIdentifier.powder_snow, 5, 15, Blocks.ICE.getDefaultState(), Blocks.POWDER_SNOW.getDefaultState(), 2, 4)
            .addDecorator(SpheroidDecorators.SWEET_BERRIES, 0.4F)
            .addSpawn(SpheroidEntitySpawnDefinitions.FOX, 0.3F)
            .addSpawn(SpheroidEntitySpawnDefinitions.GOAT, 0.25F);
    public static final SpheroidType POWDER_SNOW_ICE_PACKED_ICE = new DoubleCoreSpheroidType(SpheroidAdvancementIdentifier.powder_snow, 5, 12, Blocks.PACKED_ICE.getDefaultState(), Blocks.ICE.getDefaultState(), Blocks.POWDER_SNOW.getDefaultState(), 2, 4, 1, 3)
            .addSpawn(SpheroidEntitySpawnDefinitions.POLAR_BEAR, 0.2F);

    // RAINBOW
    public static final SpheroidType RAINBOW_WOOL       = new RainbowSpheroidType(SpheroidAdvancementIdentifier.rainbow_wool, 5, 16, LIST_WOOL);
    public static final SpheroidType RAINBOW_GLASS      = new RainbowSpheroidType(SpheroidAdvancementIdentifier.rainbow_glass, 5, 16, LIST_STAINED_GLASS);
    public static final SpheroidType RAINBOW_CONCRETE   = new RainbowSpheroidType(SpheroidAdvancementIdentifier.rainbow_concrete, 5, 16, LIST_CONCRETE);
    public static final SpheroidType RAINBOW_TERRACOTTA = new RainbowSpheroidType(SpheroidAdvancementIdentifier.rainbow_terracotta, 5, 16, LIST_TERRACOTTA);

    // SPECIAL
    public static final SpheroidType DUNGEON_ZOMBIE = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, 6, 11,  EntityType.ZOMBIE,  MAP_DUNGEON_STONES, 2,  4);
    public static final SpheroidType DUNGEON_SKELETON = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, 6, 11,  EntityType.SKELETON,  MAP_DUNGEON_STONES, 2,  4);
    public static final SpheroidType DUNGEON_SPIDER = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, 6, 11,  EntityType.SPIDER,  MAP_DUNGEON_STONES, 2,  4);
    public static final SpheroidType DUNGEON_CREEPER = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, 6, 11,  EntityType.CREEPER,  MAP_DUNGEON_STONES, 2,  4);
    public static final SpheroidType DUNGEON_CAVE_SPIDER = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, 6, 11,  EntityType.CAVE_SPIDER,  MAP_DUNGEON_STONES, 2,  4);
    public static final SpheroidType DUNGEON_SLIME = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, 6, 11,  EntityType.SLIME,  MAP_DUNGEON_STONES, 2,  4);
    public static final SpheroidType DUNGEON_DROWNED = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, 6, 11,  EntityType.DROWNED,  MAP_DUNGEON_STONES, 2,  4);
    public static final SpheroidType DUNGEON_HUSK = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, 6, 11,  EntityType.HUSK,  MAP_DUNGEON_STONES, 2,  4);
    public static final SpheroidType DUNGEON_STRAY = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, 6, 11,  EntityType.STRAY,  MAP_DUNGEON_STONES, 2,  4);
    public static final SpheroidType DUNGEON_WITCH = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, 6, 11,  EntityType.WITCH,  MAP_DUNGEON_STONES, 2,  4);
    public static final SpheroidType DUNGEON_SILVERFISH = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, 6, 11,  EntityType.SILVERFISH,  MAP_DUNGEON_STONES, 2,  4);
    public static final SpheroidType DUNGEON_ENDERMAN = new DungeonSpheroidType(SpheroidAdvancementIdentifier.dungeon, 6, 11,  EntityType.ENDERMAN,  MAP_DUNGEON_STONES, 2,  4);

    // UNIQUE
    public static final SpheroidType BEE_HIVE = new BeeHiveSpheroidType(SpheroidAdvancementIdentifier.bee_hive,10, 16, 2, 4, 1, 2, 2, 3);
    public static final SpheroidType OCEAN_MONUMENT = new OceanMonumentSpheroidType(SpheroidAdvancementIdentifier.ocean_monument, 25, 35, 3, 5, 2, 3);
    public static final SpheroidType STRONGHOLD = new StrongholdSpheroidType(SpheroidAdvancementIdentifier.stronghold, 25, 30);
    public static final SpheroidType AMETHYST_GEODE = new GeodeSpheroidType(SpheroidAdvancementIdentifier.amethyst, 6, 10, Blocks.AMETHYST_BLOCK.getDefaultState(), Blocks.BUDDING_AMETHYST.getDefaultState(), 0.08F, Blocks.CALCITE.getDefaultState(), Blocks.SMOOTH_BASALT.getDefaultState());

    public static final SpheroidType ROOTED_DIRT = new ModularSpheroidType(SpheroidAdvancementIdentifier.rooted_dirt, 7, 12, Blocks.ROOTED_DIRT.getDefaultState())
            .addDecorator(SpheroidDecorators.AZALEA, 0.7F)
            .addDecorator(SpheroidDecorators.FLOWERING_AZALEA, 0.75F)
            .addDecorator(SpheroidDecorators.HANGING_ROOTS, 0.75F);


    public static void setup(SpheroidLoader spheroidLoader) {
        StarrySkyCommon.log(INFO, "Loading Vanilla Spheroids...");

        // COMMON
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ESSENTIAL, 10.0F, GRASS);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ESSENTIAL,  4.0F, SAND);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ESSENTIAL,  1.0F, RED_SAND);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ESSENTIAL,  1.0F, GRAVEL);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ESSENTIAL,  8.0F, GLOWSTONE);

        // STONES
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 2.0F, STONE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 1.0F, DEEPSLATE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 1.0F, GRANITE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 1.0F, DIORITE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 1.0F, ANDESITE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 1.0F, TUFF);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.3F, COBBLESTONE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.2F, COBBLED_DEEPSLATE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.1F, MOSSY_COBBLESTONE);

        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.5F, BEACH_GRASS);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.5F, BEACH_SAND);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.5F, JUNGLE_POND);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 1.0F, PODZOL);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 1.0F, ROOTED_DIRT);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.7F, JUNGLE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.5F, SNOW_CAVE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 1.0F, STONE_HOLLOW);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.5F, COARSE_DIRT);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.5F, OBSIDIAN);

        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.2F, DRIPSTONE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.1F, DRIPSTONE_COAL);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.05F, DRIPSTONE_IRON);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 1.0F, DRIPSTONE_CAVE);

        //COLD
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 1.3F, SNOW_ICE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 1.3F, SNOW_PACKED_ICE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.3F, ICE_PACKED_ICE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.4F, SNOW_BLUE_ICE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.3F, ICE_BLUE_ICE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.6F, POWDER_SNOW_ICE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.4F, POWDER_SNOW_ICE_PACKED_ICE);

        // TRANSLUCENT - GLASS
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.1F, GLASS);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.02F, BLACK_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.02F, BLUE_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.02F, BROWN_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.02F, CYAN_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.02F, GRAY_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.02F, GREEN_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.02F, LIGHT_BLUE_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.02F, LIGHT_GRAY_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.02F, LIME_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.02F, MAGENTA_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.02F, ORANGE_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.02F, PINK_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.02F, PURPLE_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.02F, RED_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.02F, WHITE_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.02F, YELLOW_STAINED_GLASS);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.005F, TINTED_GLASS);

        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.TREASURE, 1.0F, CORALS_GLASS);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.TREASURE, 1.0F, CORALS_STONE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.TREASURE, 1.0F, BROWN_MUSHROOM);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.TREASURE, 0.7F, RED_MUSHROOM);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.TREASURE, 1.0F, HUGE_MONSTER_CAVE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.TREASURE, 1.0F, MYCELIUM);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.TREASURE, 0.1F, BEDROCK);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.TREASURE, 0.05F, THE_SUN);

        //RAINBOW
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.TREASURE, 1.0F, RAINBOW_WOOL);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.TREASURE, 1.0F, RAINBOW_GLASS);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.TREASURE, 1.0F, RAINBOW_CONCRETE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.TREASURE, 1.0F, RAINBOW_TERRACOTTA);

        // FLUID - WATER
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.FLUID, 5.0F, WATER_GLASS);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.FLUID, 2.0F, WATER_CLAY);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.FLUID, 0.2F, WATER_SPONGE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.FLUID, 0.2F, WATER_SLIME);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.FLUID, 1.0F, WATER_ICE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.FLUID, 0.8F, WATER_PACKED_ICE);

        // FLUID - LAVA
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.FLUID, 3.0F, LAVA_GLASS);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.FLUID, 3.0F, LAVA_STONE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.FLUID, 2.0F, LAVA_OBSIDIAN);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.FLUID, 1.0F, LAVA_MAGMA);

        //ORES
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 7.0F, COAL);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 7.0F, IRON);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 0.5F, GOLD);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 0.2F, DIAMOND);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 2.0F, REDSTONE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 0.3F, LAPIS);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 0.05F, EMERALD);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 2.0F, COPPER);

        // DEEPSLATE ORES
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 1.0F, DEEPSLATE_COAL);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 1.0F, DEEPSLATE_IRON);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 0.5F, DEEPSLATE_GOLD);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 0.2F, DEEPSLATE_DIAMOND);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 2.0F, DEEPSLATE_REDSTONE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 0.3F, DEEPSLATE_LAPIS);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 0.03F, DEEPSLATE_EMERALD);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 0.2F, DEEPSLATE_COPPER);

        // "ORES"
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 0.05F, BONE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 0.05F, HAY);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 0.05F, PRISMARINE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 0.05F, SLIME);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.ORE, 0.05F, TNT);

        //WOOD
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 5.0F, OAK_WOOD);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 2.5F, SPRUCE_WOOD);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 2.5F, BIRCH_WOOD);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 1.5F, JUNGLE_WOOD);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 1.5F, DARK_OAK_WOOD);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 1.5F, ACACIA_WOOD);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 1.0F, AZALEA_WOOD);

        // DUNGEON
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DUNGEON,  3.0F, DUNGEON_ZOMBIE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DUNGEON,  2.0F, DUNGEON_SKELETON);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DUNGEON,  2.0F, DUNGEON_SPIDER);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DUNGEON,  1.0F, DUNGEON_WITCH);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DUNGEON,  0.8F, DUNGEON_CREEPER);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DUNGEON,  0.3F, DUNGEON_CAVE_SPIDER);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DUNGEON,  0.1F, DUNGEON_SLIME);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DUNGEON,  0.1F, DUNGEON_DROWNED);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DUNGEON,  0.1F, DUNGEON_HUSK);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DUNGEON,  0.1F, DUNGEON_STRAY);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DUNGEON,  0.1F, DUNGEON_SILVERFISH);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DUNGEON,  0.05F, DUNGEON_ENDERMAN);

        // UNIQUE
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.TREASURE, 4.0F, BEE_HIVE);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.TREASURE, 0.5F, OCEAN_MONUMENT);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.TREASURE, 1.0F, STRONGHOLD);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.TREASURE, 1.0F, AMETHYST_GEODE); // TODO: Check and recuce weight to 1.5
    }
}
