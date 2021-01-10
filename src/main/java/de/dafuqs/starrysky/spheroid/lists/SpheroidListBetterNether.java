package de.dafuqs.starrysky.spheroid.lists;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.dimension.SpheroidDistributionType;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import de.dafuqs.starrysky.dimension.decorators.PlantDecorator;
import de.dafuqs.starrysky.dimension.decorators.UnderPlantDecorator;
import de.dafuqs.starrysky.spheroid.types.*;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

import java.util.ArrayList;

public class SpheroidListBetterNether extends SpheroidList {

    private static final String MOD_ID = "betternether";


    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateBetterNetherSpheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        StarrySkyCommon.LOGGER.info("[StarrySky] Loading Better Nether integration...");

        BlockState netherrack = Blocks.NETHERRACK.getDefaultState();

        // ORES
        BlockState cincinnasite_ore = getDefaultBlockState(MOD_ID,"cincinnasite_ore");
        BlockState nether_lapis_ore = getDefaultBlockState(MOD_ID,"nether_lapis_ore");
        BlockState nether_ruby_ore = getDefaultBlockState(MOD_ID,"nether_ruby_ore");

        SpheroidType CINCINNASITE_ORE = new CoreSpheroidType(null, 6, 12, cincinnasite_ore, netherrack, 3, 5);
        SpheroidType NETHER_LAPIS_ORE = new CoreSpheroidType(null, 6, 10, nether_lapis_ore, netherrack, 3, 5);
        SpheroidType NETHER_RUBY_ORE = new CoreSpheroidType(null, 5, 8, nether_ruby_ore, netherrack, 2, 4);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.ORE, 1.5F, CINCINNASITE_ORE);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.ORE, 0.8F, NETHER_LAPIS_ORE);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.ORE, 0.4F, NETHER_RUBY_ORE);


        // STALACTITES
        BlockState netherrack_stalactite = getDefaultBlockState(MOD_ID,"netherrack_stalactite"); // size: 0, 1, ...
        BlockState glowstone_stalactite = getDefaultBlockState(MOD_ID,"glowstone_stalactite"); // size: 0, 1, ...
        BlockState basalt_stalactite = getDefaultBlockState(MOD_ID,"basalt_stalactite"); // size: 0, 1, ...

        PlantDecorator NETHERRACK_STALACTITE_DECORATOR = new PlantDecorator(netherrack_stalactite, 0.1F);
        PlantDecorator GLOWSTONE_STALACTITE_DECORATOR = new PlantDecorator(glowstone_stalactite, 0.1F);
        UnderPlantDecorator BASALT_STALACTITE_DECORATOR = new UnderPlantDecorator(basalt_stalactite, 0.1F);

        SpheroidListVanillaNether.NETHERRACK.addDecorator(NETHERRACK_STALACTITE_DECORATOR, 0.2F);
        SpheroidListVanillaNether.NETHERRACK.addDecorator(GLOWSTONE_STALACTITE_DECORATOR, 0.2F);
        SpheroidListVanillaNether.GLOWSTONE.addDecorator(GLOWSTONE_STALACTITE_DECORATOR, 0.2F);
        SpheroidListVanillaNether.MAGMA_BLOCK.addDecorator(NETHERRACK_STALACTITE_DECORATOR, 0.2F);
        SpheroidListVanillaNether.MAGMA_BLOCK.addDecorator(GLOWSTONE_STALACTITE_DECORATOR, 0.2F);
        SpheroidListVanillaNether.NETHER_QUARTZ.addDecorator(NETHERRACK_STALACTITE_DECORATOR, 0.2F);
        SpheroidListVanillaNether.NETHER_QUARTZ.addDecorator(GLOWSTONE_STALACTITE_DECORATOR, 0.2F);
        SpheroidListVanillaNether.NETHER_GOLD_ORE.addDecorator(NETHERRACK_STALACTITE_DECORATOR, 0.2F);
        SpheroidListVanillaNether.NETHER_GOLD_ORE.addDecorator(GLOWSTONE_STALACTITE_DECORATOR, 0.2F);
        SpheroidListVanillaNether.BASALT.addDecorator(BASALT_STALACTITE_DECORATOR, 0.2F);

        // GEYSER
        BlockState geyser = getDefaultBlockState(MOD_ID,"geyser"); // on netherrack, close to magma
        PlantDecorator GEYSER_DECORATOR = new PlantDecorator(geyser, 0.08F);
        SpheroidListVanillaNether.NETHERRACK.addDecorator(GEYSER_DECORATOR, 0.05F);
        SpheroidListVanillaNether.NETHER_GOLD_ORE.addDecorator(GEYSER_DECORATOR, 0.05F);
        SpheroidListVanillaNether.NETHER_QUARTZ.addDecorator(GEYSER_DECORATOR, 0.05F);
        SpheroidListVanillaNether.MAGMA_BLOCK.addDecorator(GEYSER_DECORATOR, 0.2F);
        SpheroidListVanillaNether.LAVA_STONE.addDecorator(GEYSER_DECORATOR, 0.2F);
        SpheroidListVanillaNether.LAVA_STONE_MAGMA.addDecorator(GEYSER_DECORATOR, 0.25F);

        // RAINBOW QUARTZ GLASS
        ArrayList<BlockState> QUARTZ_GLASSES = new ArrayList<BlockState>() {{
            add(getDefaultBlockState(MOD_ID, "quartz_glass_white"));
            add(getDefaultBlockState(MOD_ID, "quartz_glass_light_gray"));
            add(getDefaultBlockState(MOD_ID, "quartz_glass_gray"));
            add(getDefaultBlockState(MOD_ID, "quartz_glass_black"));
            add(getDefaultBlockState(MOD_ID, "quartz_glass_brown"));
            add(getDefaultBlockState(MOD_ID, "quartz_glass_red"));
            add(getDefaultBlockState(MOD_ID, "quartz_glass_orange"));
            add(getDefaultBlockState(MOD_ID, "quartz_glass_yellow"));
            add(getDefaultBlockState(MOD_ID, "quartz_glass_lime"));
            add(getDefaultBlockState(MOD_ID, "quartz_glass_green"));
            add(getDefaultBlockState(MOD_ID, "quartz_glass_cyan"));
            add(getDefaultBlockState(MOD_ID, "quartz_glass_light_blue"));
            add(getDefaultBlockState(MOD_ID, "quartz_glass_blue"));
            add(getDefaultBlockState(MOD_ID, "quartz_glass_purple"));
            add(getDefaultBlockState(MOD_ID, "quartz_glass_magenta"));
            add(getDefaultBlockState(MOD_ID, "quartz_glass_pink"));
        }};
        SpheroidType RAINBOW_QUARTZ_GLASS = new RainbowSpheroidType(null, 6, 10, QUARTZ_GLASSES);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.DECORATIVE, 0.05F, RAINBOW_QUARTZ_GLASS);

        ArrayList<BlockState> FRAMED_QUARTZ_GLASSES = new ArrayList<BlockState>() {{
            add(getDefaultBlockState(MOD_ID, "quartz_glass_framed_white"));
            add(getDefaultBlockState(MOD_ID, "quartz_glass_framed_light_gray"));
            add(getDefaultBlockState(MOD_ID, "quartz_glass_framed_gray"));
            add(getDefaultBlockState(MOD_ID, "quartz_glass_framed_black"));
            add(getDefaultBlockState(MOD_ID, "quartz_glass_framed_brown"));
            add(getDefaultBlockState(MOD_ID, "quartz_glass_framed_red"));
            add(getDefaultBlockState(MOD_ID, "quartz_glass_framed_orange"));
            add(getDefaultBlockState(MOD_ID, "quartz_glass_framed_yellow"));
            add(getDefaultBlockState(MOD_ID, "quartz_glass_framed_lime"));
            add(getDefaultBlockState(MOD_ID, "quartz_glass_framed_green"));
            add(getDefaultBlockState(MOD_ID, "quartz_glass_framed_cyan"));
            add(getDefaultBlockState(MOD_ID, "quartz_glass_framed_light_blue"));
            add(getDefaultBlockState(MOD_ID, "quartz_glass_framed_blue"));
            add(getDefaultBlockState(MOD_ID, "quartz_glass_framed_purple"));
            add(getDefaultBlockState(MOD_ID, "quartz_glass_framed_magenta"));
            add(getDefaultBlockState(MOD_ID, "quartz_glass_framed_pink"));
        }};
        SpheroidType RAINBOW_FRAMED_QUARTZ_GLASS = new RainbowSpheroidType(null, 6, 10, FRAMED_QUARTZ_GLASSES);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.DECORATIVE, 0.05F, RAINBOW_FRAMED_QUARTZ_GLASS);

        // BONE REEF
        BlockState mushroom_grass = getDefaultBlockState(MOD_ID,"mushroom_grass");
        BlockState bone_grass = getDefaultBlockState(MOD_ID,"bone_grass");
        PlantDecorator BONE_GRASS_DECORATOR = new PlantDecorator(bone_grass, 0.2F);
        BlockState feather_fern = getDefaultBlockState(MOD_ID,"feather_fern"); // age 1, ...
        PlantDecorator FEATHER_FERN_DECORATOR = new PlantDecorator(feather_fern, 0.1F);
        BlockState jellyfish_mushroom = getDefaultBlockState(MOD_ID,"jellyfish_mushroom"); // shape: bottom (small), or middle + top
        PlantDecorator JELLYFISH_MUSHROOM_DECORATOR = new PlantDecorator(jellyfish_mushroom, 0.03F);


        BlockState lumabus_vine = getDefaultBlockState(MOD_ID,"lumabus_vine"); // top, middle, bottom
        UnderPlantDecorator LUMABUS_VINE_DECORATOR = new UnderPlantDecorator(lumabus_vine, 0.1F);

        SpheroidType BONE_REEF = new ModularSpheroidType(null, 8, 14, netherrack)
                .setTopBlockState(mushroom_grass)
                .addDecorator(BONE_GRASS_DECORATOR, 0.9F)
                .addDecorator(FEATHER_FERN_DECORATOR, 0.5F)
                .addDecorator(LUMABUS_VINE_DECORATOR, 0.5F)
                .addDecorator(JELLYFISH_MUSHROOM_DECORATOR, 0.5F);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.DECORATIVE, 0.8F, BONE_REEF);

        BlockState bone_block = getDefaultBlockState(MOD_ID,"bone_block");
        BlockState bone_mushroom = getDefaultBlockState(MOD_ID,"bone_mushroom");
        PlantDecorator BONE_MUSHROOM_DECORATOR = new PlantDecorator(bone_mushroom, 0.2F);
        SpheroidType BONE_BLOCK = new ModularSpheroidType(null, 8, 14, bone_block)
                .setTopBlockState(mushroom_grass)
                .addDecorator(BONE_MUSHROOM_DECORATOR, 1.0F);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.DECORATIVE, 0.4F, BONE_BLOCK);


        // CRIMSON GLOWING WOODS
        BlockState golden_vine = getDefaultBlockState(MOD_ID,"golden_vine");
        UnderPlantDecorator GOLDEN_VINE_DECORATOR = new UnderPlantDecorator(golden_vine, 0.2F);
        SpheroidListVanillaNether.CRIMSON_NYLIUM.addDecorator(GOLDEN_VINE_DECORATOR, 0.2F);

        // GRAVEL DESERT
        BlockState agave = getDefaultBlockState(MOD_ID,"agave");
        PlantDecorator AGAVE_DECORATOR = new PlantDecorator(agave, 0.09F);
        SpheroidListVanillaNether.GRAVEL.addDecorator(AGAVE_DECORATOR, 0.3F);

        BlockState barrel_cactus = getDefaultBlockState(MOD_ID,"barrel_cactus");
        PlantDecorator BARREL_CACTUS_DECORATOR = new PlantDecorator(barrel_cactus, 0.03F);
        SpheroidListVanillaNether.GRAVEL.addDecorator(BARREL_CACTUS_DECORATOR, 0.15F);

        BlockState nether_cactus = getDefaultBlockState(MOD_ID,"nether_cactus"); // TODO: top: true / false
        PlantDecorator NETHER_CACTUS_DECORATOR = new PlantDecorator(nether_cactus, 0.03F);
        SpheroidListVanillaNether.GRAVEL.addDecorator(NETHER_CACTUS_DECORATOR, 0.15F);

        // MAGMA LAND
        BlockState magma_flower = getDefaultBlockState(MOD_ID,"magma_flower");
        PlantDecorator MAGMA_FLOWER_DECORATOR = new PlantDecorator(magma_flower, 0.09F);
        SpheroidListVanillaNether.MAGMA_BLOCK.addDecorator(MAGMA_FLOWER_DECORATOR, 0.1F);

        // NETHER GRASSLANDS
        BlockState wart_seed = getDefaultBlockState(MOD_ID,"wart_seed");
        PlantDecorator WART_SEED_DECORATOR = new PlantDecorator(wart_seed, 0.1F);
        BlockState ink_bush = getDefaultBlockState(MOD_ID,"ink_bush");
        PlantDecorator INK_BUSH_DECORATOR = new PlantDecorator(ink_bush, 0.1F);
        BlockState black_apple = getDefaultBlockState(MOD_ID,"black_apple");
        PlantDecorator BLACK_APPLE_DECORATOR = new PlantDecorator(black_apple, 0.02F);
        BlockState nether_grass = getDefaultBlockState(MOD_ID,"nether_grass");
        PlantDecorator NETHER_GRASS_DECORATOR = new PlantDecorator(nether_grass, 0.1F);
        BlockState smoker = getDefaultBlockState(MOD_ID,"smoker"); // bottom, middle, top
        PlantDecorator SMOKER_DECORATOR = new PlantDecorator(smoker, 0.1F);

        SpheroidType NETHER_WART = new ModularSpheroidType(null, 8, 14, Blocks.NETHER_WART_BLOCK.getDefaultState())
                .addDecorator(WART_SEED_DECORATOR, 1.0F);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.DECORATIVE, 0.4F, NETHER_WART);

        BlockState netherrack_moss = getDefaultBlockState(MOD_ID,"netherrack_moss");
        SpheroidType NETHER_GRASSLANDS = new ModularSpheroidType(null, 8, 15, netherrack)
                .setTopBlockState(netherrack_moss)
                .addDecorator(WART_SEED_DECORATOR, 0.8F)
                .addDecorator(INK_BUSH_DECORATOR, 0.8F)
                .addDecorator(NETHER_GRASS_DECORATOR, 0.8F)
                .addDecorator(SMOKER_DECORATOR, 0.8F)
                .addDecorator(BLACK_APPLE_DECORATOR, 0.5F);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.DECORATIVE, 0.6F, NETHER_GRASSLANDS);

        // NETHER JUNGLE
        BlockState jungle_grass = getDefaultBlockState(MOD_ID,"jungle_grass");
        BlockState jungle_plant = getDefaultBlockState(MOD_ID,"jungle_plant");
        PlantDecorator JUNGLE_PLANT_DECORATOR = new PlantDecorator(jungle_plant, 0.3F);

        BlockState stalagnate = getDefaultBlockState(MOD_ID,"stalagnate"); // shape: bottom, middle, top
        PlantDecorator STALAGNATE_DECORATOR = new PlantDecorator(stalagnate, 0.15F);

        BlockState egg_plant = getDefaultBlockState(MOD_ID,"egg_plant"); // age: 0, 1, 2, 3
        PlantDecorator EGG_PLANT_DECORATOR = new PlantDecorator(egg_plant, 0.15F);


        BlockState black_vine = getDefaultBlockState(MOD_ID,"black_vine"); // bottom: true/false
        UnderPlantDecorator BLACK_VINE_DECORATOR = new UnderPlantDecorator(black_vine, 0.2F);
        BlockState blooming_vine = getDefaultBlockState(MOD_ID,"blooming_vine"); // bottom: true/false
        UnderPlantDecorator BLOOMING_VINE_DECORATOR = new UnderPlantDecorator(blooming_vine, 0.2F);

        BlockState eye_vine = getDefaultBlockState(MOD_ID,"eye_vine");
        BlockState eyeball_small = getDefaultBlockState(MOD_ID,"eye_vine");
        BlockState eyeball = getDefaultBlockState(MOD_ID,"eyeball");
        UnderPlantDecorator EYEBALL_DECORATOR = new UnderPlantDecorator(eyeball, 0.1F); // That's not nice to look at, but as least there are drops

        SpheroidType NETHER_JUNGLE = new ModularSpheroidType(null, 8, 20, netherrack)
                .setTopBlockState(jungle_grass)
                .addDecorator(JUNGLE_PLANT_DECORATOR, 0.95F)
                .addDecorator(JELLYFISH_MUSHROOM_DECORATOR, 0.7F)
                .addDecorator(FEATHER_FERN_DECORATOR, 0.5F)
                .addDecorator(BLACK_VINE_DECORATOR, 0.8F)
                .addDecorator(BLOOMING_VINE_DECORATOR, 0.8F)
                .addDecorator(EYEBALL_DECORATOR, 0.5F)
                .addDecorator(EGG_PLANT_DECORATOR, 0.8F)
                .addDecorator(STALAGNATE_DECORATOR, 0.8F);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.DECORATIVE, 1.3F, NETHER_JUNGLE);

        // NETHER MUSHROOM FOREST
        BlockState nether_mycelium = getDefaultBlockState(MOD_ID,"nether_mycelium");

        BlockState gray_mold = getDefaultBlockState(MOD_ID,"gray_mold"); // on nether mycelium
        PlantDecorator GRAY_MOLD_DECORATOR = new PlantDecorator(gray_mold, 0.1F);
        BlockState red_mold = getDefaultBlockState(MOD_ID,"red_mold"); // on nether mycelium
        PlantDecorator RED_MOLD_DECORATOR = new PlantDecorator(red_mold, 0.1F);
        BlockState orange_mushroom = getDefaultBlockState(MOD_ID,"orange_mushroom"); // on nether mycelium
        PlantDecorator ORANGE_MUSHROOM_DECORATOR = new PlantDecorator(orange_mushroom, 0.1F);

        BlockState mushroom_fir_sapling = getDefaultBlockState(MOD_ID,"mushroom_fir_sapling"); // on nether mycelium
        PlantDecorator MUSHROOM_FIR_SAPLING_DECORATOR = new PlantDecorator(mushroom_fir_sapling, 0.1F);
        BlockState giant_mold_sapling = getDefaultBlockState(MOD_ID,"giant_mold_sapling"); // on nether mycelium
        PlantDecorator GIANT_MOLD_SAPLING_DECORATOR = new PlantDecorator(giant_mold_sapling, 0.1F);

        SpheroidType NETHER_MYCELIUM = new ModularSpheroidType(null, 8, 20, netherrack)
                .setTopBlockState(nether_mycelium)
                .addDecorator(GRAY_MOLD_DECORATOR, 0.9F)
                .addDecorator(RED_MOLD_DECORATOR, 0.9F)
                .addDecorator(ORANGE_MUSHROOM_DECORATOR, 0.9F)
                .addDecorator(MUSHROOM_FIR_SAPLING_DECORATOR, 0.9F)
                .addDecorator(GIANT_MOLD_SAPLING_DECORATOR, 0.9F);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.DECORATIVE, 1.0F, NETHER_MYCELIUM);

        // RUBEUS TREE
        BlockState rubeus_leaves = getDefaultBlockState(MOD_ID,"rubeus_leaves");
        BlockState rubeus_log = getDefaultBlockState(MOD_ID,"rubeus_log"); // shape: middle, bottom
        BlockState rubeus_cone = getDefaultBlockState(MOD_ID,"rubeus_cone"); // hanging from leaves
        UnderPlantDecorator RUBEUS_CONE_DECORATOR = new UnderPlantDecorator(rubeus_cone, 0.2F);

        SpheroidType RUBEUS_WOOD = new ShellSpheroidType(null, 8, 20, rubeus_log, rubeus_leaves, 2, 3)
                .addDecorator(RUBEUS_CONE_DECORATOR, 1.0F);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.WOOD, 1.0F, RUBEUS_WOOD);

        // ANCHOR TREE
        BlockState anchor_tree_log = getDefaultBlockState(MOD_ID,"anchor_tree_log");
        BlockState anchor_tree_leaves = getDefaultBlockState(MOD_ID,"anchor_tree_leaves");
        SpheroidType ANCHOR_TREE = new ShellSpheroidType(null, 10, 20, anchor_tree_log, anchor_tree_leaves, 2, 3);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.WOOD, 0.6F, ANCHOR_TREE);

        // WILLOW TREE
        BlockState willow_tree_log = getDefaultBlockState(MOD_ID,"willow_tree_log");
        BlockState willow_tree_leaves = getDefaultBlockState(MOD_ID,"willow_tree_leaves");
        SpheroidType WILLOW_TREE = new ShellSpheroidType(null, 8, 14, willow_tree_log, willow_tree_leaves, 2, 3);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.WOOD, 1.0F, WILLOW_TREE);

        // NETHER SAKURA TREE
        BlockState nether_sakura_tree_log = getDefaultBlockState(MOD_ID,"nether_sakura_tree_log");
        BlockState nether_sakura_tree_leaves = getDefaultBlockState(MOD_ID,"nether_sakura_tree_leaves");
        SpheroidType NETHER_SAKURA_TREE = new ShellSpheroidType(null, 7, 12, nether_sakura_tree_log, nether_sakura_tree_leaves, 2, 3);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.WOOD, 1.0F, NETHER_SAKURA_TREE);


        // OBSIDIAN GLASS
        BlockState obsidian = Blocks.OBSIDIAN.getDefaultState();
        BlockState obsidian_glass = getDefaultBlockState(MOD_ID,"obsidian_glass");
        BlockState blue_obsidian_glass = getDefaultBlockState(MOD_ID,"blue_obsidian_glass");
        BlockState blue_obsidian = getDefaultBlockState(MOD_ID,"blue_obsidian");

        SpheroidType OBSIDIAN_GLASS = new ShellSpheroidType(null, 8, 20, obsidian, obsidian_glass, 2, 4)
            .addShellSpeckles(obsidian, 0.1F);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.DECORATIVE, 0.5F, OBSIDIAN_GLASS);
        SpheroidType BLUE_OBSIDIAN_GLASS = new ShellSpheroidType(null, 8, 20, blue_obsidian, blue_obsidian_glass, 2, 4)
                .addShellSpeckles(blue_obsidian, 0.1F);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.DECORATIVE, 0.5F, BLUE_OBSIDIAN_GLASS);

        // TODO
        BlockState nether_reed = getDefaultBlockState(MOD_ID,"nether_reed"); // top: true / false; next to lava
        BlockState moss_cover = getDefaultBlockState(MOD_ID,"moss_cover"); // on anchor tree tops

        // WALLS (jungle)
        BlockState lucis_mushroom = getDefaultBlockState(MOD_ID,"lucis_mushroom"); // several
        BlockState jungle_moss = getDefaultBlockState(MOD_ID,"jungle_moss"); // facing <direction>
        BlockState wall_mushroom_brown = getDefaultBlockState(MOD_ID,"wall_mushroom_brown"); // facing <direction>
        BlockState wall_mushroom_red = getDefaultBlockState(MOD_ID,"wall_mushroom_red"); // facing <direction>
    }

}
