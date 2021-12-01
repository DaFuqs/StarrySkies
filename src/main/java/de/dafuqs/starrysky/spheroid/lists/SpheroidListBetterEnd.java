package de.dafuqs.starrysky.spheroid.lists;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.dimension.SpheroidDistributionType;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import de.dafuqs.starrysky.spheroid.decorators.DoublePlantDecorator;
import de.dafuqs.starrysky.spheroid.decorators.PlantDecorator;
import de.dafuqs.starrysky.spheroid.decorators.UnderPlantDecorator;
import de.dafuqs.starrysky.spheroid.types.*;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;

import static de.dafuqs.starrysky.dimension.SpheroidLoader.SpheroidDimensionType.END;
import static org.apache.logging.log4j.Level.INFO;

public class SpheroidListBetterEnd extends SpheroidList {

    private static final String MOD_ID = "betterend";


    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateBetterEndSpheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        StarrySkyCommon.log(INFO, "Loading Better End integration...");

        BlockState end_stone = Blocks.END_STONE.getDefaultState();

        // CHORUS FOREST
        BlockState chorus_nylium = getDefaultBlockState(MOD_ID,"chorus_nylium"); // overgrown end stone
        BlockState chorus_grass = getDefaultBlockState(MOD_ID,"chorus_grass"); // plant
        PlantDecorator CHORUS_GRASS_DECORATOR = new PlantDecorator(chorus_grass, 0.25F);
        SpheroidType CHORUS_NYLIUM = new ModularSpheroidType(null, 7, 14, end_stone)
                .setTopBlockState(chorus_nylium)
                .addDecorator(CHORUS_GRASS_DECORATOR, 0.9F)
                .addDecorator(SpheroidListVanillaEnd.SpheroidDecorators.CHORUS_FRUIT, 0.7F);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.5F, CHORUS_NYLIUM);

        // CRYSTAL MOUNTAINS
        BlockState crystal_moss = getDefaultBlockState(MOD_ID,"crystal_moss"); // overgrown end stone
        BlockState crystal_grass = getDefaultBlockState(MOD_ID,"crystal_grass"); // plant
        PlantDecorator CRYSTAL_GRASS_DECORATOR = new PlantDecorator(crystal_grass, 0.25F);
        SpheroidType CRYSTAL_MOSS = new ModularSpheroidType(null, 7, 14, end_stone)
                .setTopBlockState(crystal_moss)
                .addDecorator(CRYSTAL_GRASS_DECORATOR, 0.9F);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.5F, CRYSTAL_MOSS);

        BlockState aurora_crystal = getDefaultBlockState(MOD_ID,"aurora_crystal");
        SpheroidType AURORA_CRYSTAL = new ModularSpheroidType(null, 7, 14, aurora_crystal);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.5F, AURORA_CRYSTAL);

        // DUST WASTELANDS
        BlockState endstone_dust = getDefaultBlockState(MOD_ID,"endstone_dust");
        SpheroidType ENDSTONE_DUST = new ModularSpheroidType(null, 9, 17, endstone_dust)
                .setBottomBlockState(end_stone);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.4F, ENDSTONE_DUST);

        // FOGGY MUSHROOMLAND
        BlockState end_moss = getDefaultBlockState(MOD_ID,"end_moss");
        BlockState end_mycelium = getDefaultBlockState(MOD_ID,"end_mycelium");

        BlockState creeping_moss = getDefaultBlockState(MOD_ID,"creeping_moss");
        BlockState umbrella_moss = getDefaultBlockState(MOD_ID,"umbrella_moss");
        BlockState umbrella_moss_tall = getDefaultBlockState(MOD_ID,"umbrella_moss_tall");
        BlockState blue_vine_seed = getDefaultBlockState(MOD_ID,"blue_vine_seed"); // single
        PlantDecorator BLUE_VINE_SEED_DECORATOR = new PlantDecorator(blue_vine_seed, 0.1F);
        PlantDecorator CREEPING_MOSS_DECORATOR = new PlantDecorator(creeping_moss, 0.1F);
        PlantDecorator UMBRELLA_MOSS_DECORATOR = new PlantDecorator(umbrella_moss, 0.1F);
        DoublePlantDecorator UMBRELLA_MOSS_TALL_DECORATOR = new DoublePlantDecorator(umbrella_moss_tall, 0.05F);
        //BlockState dense_vine = getDefaultBlockState(MOD_ID,"dense_vine"); // growing downwards on glowshrooms, Bottom, middle, top TODO

        SpheroidType END_MOSS = new ModularSpheroidType(null, 8, 14, end_stone)
                .setTopBlockState(end_moss)
                .addDecorator(CREEPING_MOSS_DECORATOR, 0.8F)
                .addDecorator(UMBRELLA_MOSS_DECORATOR, 0.8F)
                .addDecorator(UMBRELLA_MOSS_TALL_DECORATOR, 0.8F);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.4F, END_MOSS);
        SpheroidType UMBRELLA_MOSS = new ModularSpheroidType(null, 8, 14, end_stone)
                .setTopBlockState(end_mycelium)
                .addDecorator(CREEPING_MOSS_DECORATOR, 0.8F)
                .addDecorator(UMBRELLA_MOSS_DECORATOR, 0.8F)
                .addDecorator(UMBRELLA_MOSS_TALL_DECORATOR, 0.8F)
                .addDecorator(BLUE_VINE_SEED_DECORATOR, 0.3F);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.4F, UMBRELLA_MOSS);

        BlockState mossy_glowshroom_log = getDefaultBlockState(MOD_ID,"mossy_glowshroom_log");
        BlockState mossy_glowshroom_cap = getDefaultBlockState(MOD_ID,"mossy_glowshroom_cap");
        BlockState mossy_glowshroom_hymenophore = getDefaultBlockState(MOD_ID,"mossy_glowshroom_hymenophore");
        SpheroidType MOSSY_GLOWSHROOM = new DoubleCoreSpheroidType(null, 5, 12, mossy_glowshroom_log, mossy_glowshroom_hymenophore, mossy_glowshroom_cap, 2, 4, 2, 4);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.WOOD, 0.4F, MOSSY_GLOWSHROOM);

        // MEGA LAKE
        BlockState lacugrove_log = getDefaultBlockState(MOD_ID,"lacugrove_log");
        BlockState lacugrove_leaves = getDefaultBlockState(MOD_ID,"lacugrove_leaves").with(LeavesBlock.DISTANCE, 1);;
        SpheroidType LACUGROVE_WOOD = new ShellSpheroidType(null, 10, 15, lacugrove_log, lacugrove_leaves, 3, 4);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.WOOD, 0.4F, LACUGROVE_WOOD);

        /* TODO
        BlockState end_lotus_stem = getDefaultBlockState(MOD_ID,"end_lotus_stem"); // bottom, middle, top, waterloggable
        BlockState end_lotus_flower = getDefaultBlockState(MOD_ID,"end_lotus_flower"); // on top of end lotus stem

        BlockState lotus_leaf = getDefaultBlockState(MOD_ID,"lotus_leaf"); // facing + shape; top of water
        BlockState end_lily = getDefaultBlockState(MOD_ID,"end_lily"); // bottom, middle, top (top of water), waterloggable
        BlockState bubble_coral = getDefaultBlockState(MOD_ID,"bubble_coral"); // bottom of water
        */

        // PAINTED MOUNTAINS
        BlockState flavolite = getDefaultBlockState(MOD_ID,"flavolite");
        SpheroidType FLAVOLITE = new ModularSpheroidType(null, 6, 12, flavolite);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.ESSENTIAL, 0.4F, FLAVOLITE);
        SpheroidType SPECKLED_FLAVOLITE = new ShellSpheroidType(null, 6, 12, flavolite, end_stone, 3, 4).addShellSpeckles(flavolite, 0.15F);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.ESSENTIAL, 1.0F, SPECKLED_FLAVOLITE);

        BlockState violecite = getDefaultBlockState(MOD_ID,"violecite");
        SpheroidType VIOLECITE = new ModularSpheroidType(null, 6, 14, violecite);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.ESSENTIAL, 0.4F, VIOLECITE);

        // SHADOW FOREST
        BlockState dragon_tree_log = getDefaultBlockState(MOD_ID,"dragon_tree_log");
        BlockState dragon_tree_leaves = getDefaultBlockState(MOD_ID,"dragon_tree_leaves").with(LeavesBlock.DISTANCE, 1);
        //BlockState purple_polyphore = getDefaultBlockState(MOD_ID,"purple_polyphore"); // on side of dragon tree log
        SpheroidType DRAGON_TREE = new ShellSpheroidType(null, 10, 17, dragon_tree_log, dragon_tree_leaves, 2, 4);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.WOOD, 0.3F, DRAGON_TREE);

        BlockState shadow_grass = getDefaultBlockState(MOD_ID,"shadow_grass");
        BlockState shadow_plant = getDefaultBlockState(MOD_ID,"shadow_plant");
        BlockState needlegrass = getDefaultBlockState(MOD_ID,"needlegrass");
        BlockState murkweed = getDefaultBlockState(MOD_ID,"murkweed");
        BlockState shadow_berry = getDefaultBlockState(MOD_ID,"shadow_berry");

        PlantDecorator SHADOW_PLANT_DECORATOR = new PlantDecorator(shadow_plant, 0.2F);
        PlantDecorator NEEDLEGRASS_DECORATOR = new PlantDecorator(needlegrass, 0.1F);
        PlantDecorator SHADOW_BERRY_DECORATOR = new PlantDecorator(shadow_berry, 0.05F);
        PlantDecorator MURKWEED_DECORATOR = new PlantDecorator(murkweed, 0.05F);
        SpheroidType SHADOW_GRASS = new ModularSpheroidType(null, 6, 14, end_stone)
                .setTopBlockState(shadow_grass)
                .addDecorator(SHADOW_PLANT_DECORATOR, 0.9F)
                .addDecorator(NEEDLEGRASS_DECORATOR, 0.7F)
                .addDecorator(MURKWEED_DECORATOR, 0.5F)
                .addDecorator(SHADOW_BERRY_DECORATOR, 0.3F);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.4F, SHADOW_GRASS);

        // ENDER ORE
        BlockState ender_ore = getDefaultBlockState(MOD_ID,"ender_ore");
        SpheroidType ENDER_ORE = new CoreSpheroidType(null, 6, 14, ender_ore, end_stone, 2, 4);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.ORE, 0.5F, ENDER_ORE);

        // CAVE
        BlockState cave_moss = getDefaultBlockState(MOD_ID,"cave_moss");
        BlockState cave_grass = getDefaultBlockState(MOD_ID,"cave_grass");
        BlockState cave_bush = getDefaultBlockState(MOD_ID,"cave_bush");
        PlantDecorator CAVE_GRASS_DECORATOR = new PlantDecorator(cave_grass, 0.1F);
        PlantDecorator CAVE_BUSH_DECORATOR = new PlantDecorator(cave_bush, 0.3F);
        SpheroidType CAVE_MOSS = new CaveSpheroidType(null, 8, 14, end_stone, cave_moss, 2, 4)
                .addDecorator(CAVE_BUSH_DECORATOR, 0.9F)
                .addDecorator(CAVE_GRASS_DECORATOR, 0.9F);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.4F, CAVE_MOSS);

        // PYTADENDRON
        BlockState pythadendron_log = getDefaultBlockState(MOD_ID,"pythadendron_log");
        BlockState pythadendron_leaves = getDefaultBlockState(MOD_ID,"pythadendron_leaves").with(LeavesBlock.DISTANCE, 1);
        SpheroidType PYTHADENDRON_BUSH = new ShellSpheroidType(null, 4, 6, pythadendron_log, pythadendron_leaves, 3, 3);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.WOOD, 1.0F, PYTHADENDRON_BUSH);

        SpheroidType PYTHADENDRON_WOOD = new ShellSpheroidType(null, 8, 13, pythadendron_log, pythadendron_leaves, 2, 4);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.WOOD, 1.0F, PYTHADENDRON_WOOD);

        // BLOSSOMING SPIRES
        BlockState tenanea_log = getDefaultBlockState(MOD_ID,"tenanea_log");
        BlockState tenanea_leaves = getDefaultBlockState(MOD_ID,"tenanea_leaves").with(LeavesBlock.DISTANCE, 1);

        BlockState tenanea_flowers = getDefaultBlockState(MOD_ID,"tenanea_flowers");
        UnderPlantDecorator tenanea_flowers_decorator = new UnderPlantDecorator(tenanea_flowers, 0.1F);

        SpheroidType TENANEA_WOOD = new ShellSpheroidType(null, 4, 6, tenanea_log, tenanea_leaves, 3, 3)
                .addDecorator(tenanea_flowers_decorator, 0.9F);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.WOOD, 1.0F, TENANEA_WOOD);

        BlockState pink_moss = getDefaultBlockState(MOD_ID,"pink_moss");
        BlockState bushy_grass = getDefaultBlockState(MOD_ID,"bushy_grass");
        PlantDecorator bushy_grass_decorator = new PlantDecorator(bushy_grass, 0.6F);
        BlockState blossom_berry_seed = getDefaultBlockState(MOD_ID,"blossom_berry_seed");
        PlantDecorator blossom_berry_seed_decorator = new PlantDecorator(blossom_berry_seed, 0.05F);

        SpheroidType PINK_MOSS = new ModularSpheroidType(null, 4, 6, end_stone)
                .setTopBlockState(pink_moss)
                .addDecorator(bushy_grass_decorator, 0.9F)
                .addDecorator(blossom_berry_seed_decorator, 0.5F);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.3F, PINK_MOSS);

        // AMBER LAND
        BlockState amber_moss = getDefaultBlockState(MOD_ID,"amber_moss");
        BlockState amber_grass = getDefaultBlockState(MOD_ID,"amber_grass");
        PlantDecorator amber_grass_decorator = new PlantDecorator(amber_grass, 0.6F);
        BlockState lanceleaf_seed = getDefaultBlockState(MOD_ID,"lanceleaf_seed");
        PlantDecorator lanceleaf_seed_decorator = new PlantDecorator(lanceleaf_seed, 0.05F);
        BlockState glowing_pillar_seed = getDefaultBlockState(MOD_ID,"glowing_pillar_seed");
        PlantDecorator glowing_pillar_seed_decorator = new PlantDecorator(glowing_pillar_seed, 0.05F);

        SpheroidType AMBER_MOSS = new ModularSpheroidType(null, 6, 13, end_stone)
                .setTopBlockState(amber_moss)
                .addDecorator(amber_grass_decorator, 0.9F)
                .addDecorator(lanceleaf_seed_decorator, 0.5F)
                .addDecorator(glowing_pillar_seed_decorator, 0.5F);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.3F, AMBER_MOSS);

        BlockState amber_ore = getDefaultBlockState(MOD_ID,"amber_ore");
        SpheroidType AMBER_ORE = new ShellSpheroidType(null, 6, 13, amber_ore, end_stone, 2, 4);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.ORE, 0.4F, AMBER_ORE);

        BlockState helix_tree_log = getDefaultBlockState(MOD_ID,"helix_tree_log");
        BlockState helix_tree_leaves = getDefaultBlockState(MOD_ID,"helix_tree_leaves");

        SpheroidType HELIX_TREE_WOOD = new ShellSpheroidType(null, 6, 11, helix_tree_log, helix_tree_leaves, 2, 3);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.WOOD, 1.0F, HELIX_TREE_WOOD);

        // ICE STARFIELD
        BlockState dense_snow = getDefaultBlockState(MOD_ID,"dense_snow");
        BlockState emerald_ice = getDefaultBlockState(MOD_ID,"emerald_ice");
        BlockState dense_emerald_ice = getDefaultBlockState(MOD_ID,"dense_emerald_ice");
        BlockState ancient_emerald_ice = getDefaultBlockState(MOD_ID,"ancient_emerald_ice");

        SpheroidType EMERALD_ICE = new DoubleCoreSpheroidType(null, 5, 10, dense_emerald_ice, emerald_ice, dense_snow, 1, 3, 2, 4);
        SpheroidType DENSE_EMERALD_ICE = new CoreSpheroidType(null, 5, 8, dense_emerald_ice, emerald_ice, 2, 3);
        SpheroidType ANCIENT_EMERALD_ICE = new DoubleCoreSpheroidType(null, 6, 10, ancient_emerald_ice, dense_emerald_ice, emerald_ice, 1, 3, 2, 4);

        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.3F, EMERALD_ICE);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.2F, DENSE_EMERALD_ICE);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.1F, ANCIENT_EMERALD_ICE);

        // UMBRELLA JUNGLE
        BlockState jungle_vine = getDefaultBlockState(MOD_ID,"jungle_vine");
        UnderPlantDecorator jungle_vine_decorator = new UnderPlantDecorator(jungle_vine, 0.2F);

        BlockState umbrella_tree_membrane = getDefaultBlockState(MOD_ID,"umbrella_tree_membrane");
        BlockState umbrella_tree_cluster = getDefaultBlockState(MOD_ID,"umbrella_tree_cluster"); // speckles
        BlockState umbrella_tree_bark = getDefaultBlockState(MOD_ID,"umbrella_tree_bark");

        SpheroidType UMBRELLA_TREE = new ShellSpheroidType(null, 8, 14, umbrella_tree_bark, umbrella_tree_membrane, 3, 4)
                .addShellSpeckles(umbrella_tree_cluster, 0.1F)
                .addDecorator(jungle_vine_decorator, 0.7F);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.WOOD, 0.4F, UMBRELLA_TREE);

        BlockState jellyshroom_log = getDefaultBlockState(MOD_ID,"jellyshroom_log");
        BlockState jellyshroom_cap_purple = getDefaultBlockState(MOD_ID,"jellyshroom_cap_purple");

        SpheroidType PURPLE_JELLYSHROOM = new ShellSpheroidType(null, 6, 10, jellyshroom_log, jellyshroom_cap_purple, 3, 4)
                .addDecorator(jungle_vine_decorator, 0.7F);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.WOOD, 0.4F, PURPLE_JELLYSHROOM);

        BlockState jungle_moss = getDefaultBlockState(MOD_ID,"jungle_moss");
        BlockState jungle_grass = getDefaultBlockState(MOD_ID,"jungle_grass");
        BlockState small_jellyshroom = getDefaultBlockState(MOD_ID,"small_jellyshroom");
        BlockState twisted_umbrella_moss = getDefaultBlockState(MOD_ID,"twisted_umbrella_moss");
        BlockState twisted_umbrella_moss_tall = getDefaultBlockState(MOD_ID,"twisted_umbrella_moss_tall");
        PlantDecorator jungle_grass_decorator = new PlantDecorator(jungle_grass, 0.2F);
        PlantDecorator small_jellyshroom_decorator = new PlantDecorator(small_jellyshroom, 0.2F);
        PlantDecorator twisted_umbrella_moss_decorator = new PlantDecorator(twisted_umbrella_moss, 0.2F);
        DoublePlantDecorator twisted_umbrella_moss_tall_decorator = new DoublePlantDecorator(twisted_umbrella_moss_tall, 0.1F);

        SpheroidType JUNGLE_MOSS = new ModularSpheroidType(null, 6, 15, end_stone)
                .setTopBlockState(jungle_moss)
                .addDecorator(jungle_grass_decorator, 0.9F)
                .addDecorator(small_jellyshroom_decorator, 0.5F)
                .addDecorator(twisted_umbrella_moss_decorator, 0.5F)
                .addDecorator(twisted_umbrella_moss_tall_decorator, 0.5F);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.3F, JUNGLE_MOSS);

        // SULFUR SPRINGS
        BlockState charnia_red = getDefaultBlockState(MOD_ID,"charnia_red");
        BlockState charnia_purple = getDefaultBlockState(MOD_ID,"charnia_purple");
        BlockState charnia_orange = getDefaultBlockState(MOD_ID,"charnia_orange");
        BlockState charnia_light_blue = getDefaultBlockState(MOD_ID,"charnia_light_blue");
        BlockState charnia_cyan = getDefaultBlockState(MOD_ID,"charnia_cyan");
        BlockState charnia_green = getDefaultBlockState(MOD_ID,"charnia_green");

        PlantDecorator charnia_red_decorator = new PlantDecorator(charnia_red, 0.2F);
        PlantDecorator charnia_purple_decorator = new PlantDecorator(charnia_purple, 0.2F);
        PlantDecorator charnia_orange_decorator = new PlantDecorator(charnia_orange, 0.2F);
        PlantDecorator charnia_light_blue_decorator = new PlantDecorator(charnia_light_blue, 0.2F);
        PlantDecorator charnia_cyan_decorator = new PlantDecorator(charnia_cyan, 0.2F);
        PlantDecorator charnia_green_decorator = new PlantDecorator(charnia_green, 0.2F);

        BlockState brimstone = getDefaultBlockState(MOD_ID,"brimstone");
        BlockState sulphuric_rock = getDefaultBlockState(MOD_ID,"sulphuric_rock");

        /* TODO
        BlockState hydralux_sapling = getDefaultBlockState(MOD_ID,"hydralux_sapling");
        BlockState sulphur_crystal = getDefaultBlockState(MOD_ID,"sulphur_crystal");
        BlockState hydrothermal_vent = getDefaultBlockState(MOD_ID,"sulphur_crystal");
         */

        SpheroidType WATER_BRIMSTONE = new LiquidSpheroidType(null, 6, 9, Blocks.WATER.getDefaultState(), brimstone, 2, 3, 20, 80, 50)
                .addDecorator(charnia_red_decorator, 0.2F)
                .addDecorator(charnia_purple_decorator, 0.2F)
                .addDecorator(charnia_orange_decorator, 0.2F)
                .addDecorator(charnia_light_blue_decorator, 0.2F)
                .addDecorator(charnia_cyan_decorator, 0.2F)
                .addDecorator(charnia_green_decorator, 0.2F);
        SpheroidType WATER_SULPHURIC_ROCK = new LiquidSpheroidType(null, 6, 11, Blocks.WATER.getDefaultState(), brimstone, 2, 3, 10, 100, 50)
                .addDecorator(charnia_red_decorator, 0.2F)
                .addDecorator(charnia_purple_decorator, 0.2F)
                .addDecorator(charnia_orange_decorator, 0.2F)
                .addDecorator(charnia_light_blue_decorator, 0.2F)
                .addDecorator(charnia_cyan_decorator, 0.2F)
                .addDecorator(charnia_green_decorator, 0.2F);
        SpheroidType BRIMSTONE_SULPHURIC_ROCK = new ShellSpheroidType(null, 6, 12, sulphuric_rock, brimstone, 2, 3);

        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.FLUID, 0.2F, WATER_BRIMSTONE);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.FLUID, 0.2F, WATER_SULPHURIC_ROCK);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.2F, BRIMSTONE_SULPHURIC_ROCK);
    }

}
