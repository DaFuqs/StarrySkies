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
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

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
        BlockState cincinnasite_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"cincinnasite_ore")).getDefaultState();
        BlockState nether_lapis_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"nether_lapis_ore")).getDefaultState();
        BlockState nether_ruby_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"nether_ruby_ore")).getDefaultState();

        SpheroidType CINCINNASITE_ORE = new CoreSpheroidType(null, 6, 12, cincinnasite_ore, netherrack, 3, 5);
        SpheroidType NETHER_LAPIS_ORE = new CoreSpheroidType(null, 6, 10, nether_lapis_ore, netherrack, 3, 5);
        SpheroidType NETHER_RUBY_ORE = new CoreSpheroidType(null, 5, 8, nether_ruby_ore, netherrack, 2, 4);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.ORE, 1.5F, CINCINNASITE_ORE);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.ORE, 0.8F, NETHER_LAPIS_ORE);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.ORE, 0.4F, NETHER_RUBY_ORE);


        // STALACTITES
        BlockState netherrack_stalactite = Registry.BLOCK.get(new Identifier(MOD_ID,"netherrack_stalactite")).getDefaultState(); // size: 0, 1, ...
        BlockState glowstone_stalactite = Registry.BLOCK.get(new Identifier(MOD_ID,"glowstone_stalactite")).getDefaultState(); // size: 0, 1, ...
        BlockState basalt_stalactite = Registry.BLOCK.get(new Identifier(MOD_ID,"basalt_stalactite")).getDefaultState(); // size: 0, 1, ...

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
        BlockState geyser = Registry.BLOCK.get(new Identifier(MOD_ID,"geyser")).getDefaultState(); // on netherrack, close to magma
        PlantDecorator GEYSER_DECORATOR = new PlantDecorator(geyser, 0.08F);
        SpheroidListVanillaNether.NETHERRACK.addDecorator(GEYSER_DECORATOR, 0.05F);
        SpheroidListVanillaNether.NETHER_GOLD_ORE.addDecorator(GEYSER_DECORATOR, 0.05F);
        SpheroidListVanillaNether.NETHER_QUARTZ.addDecorator(GEYSER_DECORATOR, 0.05F);
        SpheroidListVanillaNether.MAGMA_BLOCK.addDecorator(GEYSER_DECORATOR, 0.2F);
        SpheroidListVanillaNether.LAVA_STONE.addDecorator(GEYSER_DECORATOR, 0.2F);
        SpheroidListVanillaNether.LAVA_STONE_MAGMA.addDecorator(GEYSER_DECORATOR, 0.25F);

        // RAINBOW QUARTZ GLASS
        ArrayList<BlockState> QUARTZ_GLASSES = new ArrayList<BlockState>() {{
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_white")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_light_gray")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_gray")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_black")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_brown")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_red")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_orange")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_yellow")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_lime")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_green")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_cyan")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_light_blue")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_blue")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_purple")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_magenta")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_pink")).getDefaultState());
        }};
        SpheroidType RAINBOW_QUARTZ_GLASS = new RainbowSpheroidType(null, 6, 10, QUARTZ_GLASSES);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.DECORATIVE, 0.05F, RAINBOW_QUARTZ_GLASS);

        ArrayList<BlockState> FRAMED_QUARTZ_GLASSES = new ArrayList<BlockState>() {{
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_framed_white")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_framed_light_gray")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_framed_gray")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_framed_black")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_framed_brown")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_framed_red")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_framed_orange")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_framed_yellow")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_framed_lime")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_framed_green")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_framed_cyan")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_framed_light_blue")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_framed_blue")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_framed_purple")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_framed_magenta")).getDefaultState());
            add(Registry.BLOCK.get(new Identifier(MOD_ID, "quartz_glass_framed_pink")).getDefaultState());
        }};
        SpheroidType RAINBOW_FRAMED_QUARTZ_GLASS = new RainbowSpheroidType(null, 6, 10, FRAMED_QUARTZ_GLASSES);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.DECORATIVE, 0.05F, RAINBOW_FRAMED_QUARTZ_GLASS);

        // BONE REEF
        BlockState mushroom_grass = Registry.BLOCK.get(new Identifier(MOD_ID,"mushroom_grass")).getDefaultState();
        BlockState bone_grass = Registry.BLOCK.get(new Identifier(MOD_ID,"bone_grass")).getDefaultState();
        PlantDecorator BONE_GRASS_DECORATOR = new PlantDecorator(bone_grass, 0.2F);
        BlockState feather_fern = Registry.BLOCK.get(new Identifier(MOD_ID,"feather_fern")).getDefaultState(); // age 1, ...
        PlantDecorator FEATHER_FERN_DECORATOR = new PlantDecorator(feather_fern, 0.1F);
        BlockState jellyfish_mushroom = Registry.BLOCK.get(new Identifier(MOD_ID,"jellyfish_mushroom")).getDefaultState(); // shape: bottom (small), or middle + top
        PlantDecorator JELLYFISH_MUSHROOM_DECORATOR = new PlantDecorator(jellyfish_mushroom, 0.03F);


        BlockState lumabus_vine = Registry.BLOCK.get(new Identifier(MOD_ID,"lumabus_vine")).getDefaultState(); // top, middle, bottom
        UnderPlantDecorator LUMABUS_VINE_DECORATOR = new UnderPlantDecorator(lumabus_vine, 0.1F);

        SpheroidType BONE_REEF = new ModularSpheroidType(null, 8, 14, netherrack)
                .setTopBlockState(mushroom_grass)
                .addDecorator(BONE_GRASS_DECORATOR, 0.9F)
                .addDecorator(FEATHER_FERN_DECORATOR, 0.5F)
                .addDecorator(LUMABUS_VINE_DECORATOR, 0.5F)
                .addDecorator(JELLYFISH_MUSHROOM_DECORATOR, 0.5F);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.DECORATIVE, 0.8F, BONE_REEF);

        BlockState bone_block = Registry.BLOCK.get(new Identifier(MOD_ID,"bone_block")).getDefaultState();
        BlockState bone_mushroom = Registry.BLOCK.get(new Identifier(MOD_ID,"bone_mushroom")).getDefaultState();
        PlantDecorator BONE_MUSHROOM_DECORATOR = new PlantDecorator(bone_mushroom, 0.2F);
        SpheroidType BONE_BLOCK = new ModularSpheroidType(null, 8, 14, bone_block)
                .setTopBlockState(mushroom_grass)
                .addDecorator(BONE_MUSHROOM_DECORATOR, 1.0F);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.DECORATIVE, 0.4F, BONE_BLOCK);


        // CRIMSON GLOWING WOODS
        BlockState golden_vine = Registry.BLOCK.get(new Identifier(MOD_ID,"golden_vine")).getDefaultState();
        UnderPlantDecorator GOLDEN_VINE_DECORATOR = new UnderPlantDecorator(golden_vine, 0.2F);
        SpheroidListVanillaNether.CRIMSON_NYLIUM.addDecorator(GOLDEN_VINE_DECORATOR, 0.2F);

        // GRAVEL DESERT
        BlockState agave = Registry.BLOCK.get(new Identifier(MOD_ID,"agave")).getDefaultState();
        PlantDecorator AGAVE_DECORATOR = new PlantDecorator(agave, 0.09F);
        SpheroidListVanillaNether.GRAVEL.addDecorator(AGAVE_DECORATOR, 0.3F);

        BlockState barrel_cactus = Registry.BLOCK.get(new Identifier(MOD_ID,"barrel_cactus")).getDefaultState();
        PlantDecorator BARREL_CACTUS_DECORATOR = new PlantDecorator(barrel_cactus, 0.03F);
        SpheroidListVanillaNether.GRAVEL.addDecorator(BARREL_CACTUS_DECORATOR, 0.15F);

        BlockState nether_cactus = Registry.BLOCK.get(new Identifier(MOD_ID,"nether_cactus")).getDefaultState(); // TODO: top: true / false
        PlantDecorator NETHER_CACTUS_DECORATOR = new PlantDecorator(nether_cactus, 0.03F);
        SpheroidListVanillaNether.GRAVEL.addDecorator(NETHER_CACTUS_DECORATOR, 0.15F);

        // MAGMA LAND
        BlockState magma_flower = Registry.BLOCK.get(new Identifier(MOD_ID,"magma_flower")).getDefaultState();
        PlantDecorator MAGMA_FLOWER_DECORATOR = new PlantDecorator(magma_flower, 0.09F);
        SpheroidListVanillaNether.MAGMA_BLOCK.addDecorator(MAGMA_FLOWER_DECORATOR, 0.1F);

        // NETHER GRASSLANDS
        BlockState wart_seed = Registry.BLOCK.get(new Identifier(MOD_ID,"wart_seed")).getDefaultState();
        PlantDecorator WART_SEED_DECORATOR = new PlantDecorator(wart_seed, 0.1F);
        BlockState ink_bush = Registry.BLOCK.get(new Identifier(MOD_ID,"ink_bush")).getDefaultState();
        PlantDecorator INK_BUSH_DECORATOR = new PlantDecorator(ink_bush, 0.1F);
        BlockState black_apple = Registry.BLOCK.get(new Identifier(MOD_ID,"black_apple")).getDefaultState();
        PlantDecorator BLACK_APPLE_DECORATOR = new PlantDecorator(black_apple, 0.02F);
        BlockState nether_grass = Registry.BLOCK.get(new Identifier(MOD_ID,"nether_grass")).getDefaultState();
        PlantDecorator NETHER_GRASS_DECORATOR = new PlantDecorator(nether_grass, 0.1F);
        BlockState smoker = Registry.BLOCK.get(new Identifier(MOD_ID,"smoker")).getDefaultState(); // bottom, middle, top
        PlantDecorator SMOKER_DECORATOR = new PlantDecorator(smoker, 0.1F);

        SpheroidType NETHER_WART = new ModularSpheroidType(null, 8, 14, Blocks.NETHER_WART_BLOCK.getDefaultState())
                .addDecorator(WART_SEED_DECORATOR, 1.0F);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.DECORATIVE, 0.4F, NETHER_WART);

        BlockState netherrack_moss = Registry.BLOCK.get(new Identifier(MOD_ID,"netherrack_moss")).getDefaultState();
        SpheroidType NETHER_GRASSLANDS = new ModularSpheroidType(null, 8, 15, netherrack)
                .setTopBlockState(netherrack_moss)
                .addDecorator(WART_SEED_DECORATOR, 0.8F)
                .addDecorator(INK_BUSH_DECORATOR, 0.8F)
                .addDecorator(NETHER_GRASS_DECORATOR, 0.8F)
                .addDecorator(SMOKER_DECORATOR, 0.8F)
                .addDecorator(BLACK_APPLE_DECORATOR, 0.5F);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.DECORATIVE, 0.6F, NETHER_GRASSLANDS);

        // NETHER JUNGLE
        BlockState jungle_grass = Registry.BLOCK.get(new Identifier(MOD_ID,"jungle_grass")).getDefaultState();
        BlockState jungle_plant = Registry.BLOCK.get(new Identifier(MOD_ID,"jungle_plant")).getDefaultState();
        PlantDecorator JUNGLE_PLANT_DECORATOR = new PlantDecorator(jungle_plant, 0.3F);

        BlockState stalagnate = Registry.BLOCK.get(new Identifier(MOD_ID,"stalagnate")).getDefaultState(); // shape: bottom, middle, top
        PlantDecorator STALAGNATE_DECORATOR = new PlantDecorator(stalagnate, 0.15F);

        BlockState egg_plant = Registry.BLOCK.get(new Identifier(MOD_ID,"egg_plant")).getDefaultState(); // age: 0, 1, 2, 3
        PlantDecorator EGG_PLANT_DECORATOR = new PlantDecorator(egg_plant, 0.15F);


        BlockState black_vine = Registry.BLOCK.get(new Identifier(MOD_ID,"black_vine")).getDefaultState(); // bottom: true/false
        UnderPlantDecorator BLACK_VINE_DECORATOR = new UnderPlantDecorator(black_vine, 0.2F);
        BlockState blooming_vine = Registry.BLOCK.get(new Identifier(MOD_ID,"blooming_vine")).getDefaultState(); // bottom: true/false
        UnderPlantDecorator BLOOMING_VINE_DECORATOR = new UnderPlantDecorator(blooming_vine, 0.2F);

        BlockState eye_vine = Registry.BLOCK.get(new Identifier(MOD_ID,"eye_vine")).getDefaultState();
        BlockState eyeball_small = Registry.BLOCK.get(new Identifier(MOD_ID,"eye_vine")).getDefaultState();
        BlockState eyeball = Registry.BLOCK.get(new Identifier(MOD_ID,"eyeball")).getDefaultState();
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
        BlockState nether_mycelium = Registry.BLOCK.get(new Identifier(MOD_ID,"nether_mycelium")).getDefaultState();

        BlockState gray_mold = Registry.BLOCK.get(new Identifier(MOD_ID,"gray_mold")).getDefaultState(); // on nether mycelium
        PlantDecorator GRAY_MOLD_DECORATOR = new PlantDecorator(gray_mold, 0.1F);
        BlockState red_mold = Registry.BLOCK.get(new Identifier(MOD_ID,"red_mold")).getDefaultState(); // on nether mycelium
        PlantDecorator RED_MOLD_DECORATOR = new PlantDecorator(red_mold, 0.1F);
        BlockState orange_mushroom = Registry.BLOCK.get(new Identifier(MOD_ID,"orange_mushroom")).getDefaultState(); // on nether mycelium
        PlantDecorator ORANGE_MUSHROOM_DECORATOR = new PlantDecorator(orange_mushroom, 0.1F);

        BlockState mushroom_fir_sapling = Registry.BLOCK.get(new Identifier(MOD_ID,"mushroom_fir_sapling")).getDefaultState(); // on nether mycelium
        PlantDecorator MUSHROOM_FIR_SAPLING_DECORATOR = new PlantDecorator(mushroom_fir_sapling, 0.1F);
        BlockState giant_mold_sapling = Registry.BLOCK.get(new Identifier(MOD_ID,"giant_mold_sapling")).getDefaultState(); // on nether mycelium
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
        BlockState rubeus_leaves = Registry.BLOCK.get(new Identifier(MOD_ID,"rubeus_leaves")).getDefaultState();
        BlockState rubeus_log = Registry.BLOCK.get(new Identifier(MOD_ID,"rubeus_log")).getDefaultState(); // shape: middle, bottom
        BlockState rubeus_cone = Registry.BLOCK.get(new Identifier(MOD_ID,"rubeus_cone")).getDefaultState(); // hanging from leaves
        UnderPlantDecorator RUBEUS_CONE_DECORATOR = new UnderPlantDecorator(rubeus_cone, 0.2F);

        SpheroidType RUBEUS_WOOD = new ShellSpheroidType(null, 8, 20, rubeus_log, rubeus_leaves, 2, 3)
                .addDecorator(RUBEUS_CONE_DECORATOR, 1.0F);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.WOOD, 1.0F, RUBEUS_WOOD);

        // ANCHOR TREE
        BlockState anchor_tree_log = Registry.BLOCK.get(new Identifier(MOD_ID,"anchor_tree_log")).getDefaultState();
        BlockState anchor_tree_leaves = Registry.BLOCK.get(new Identifier(MOD_ID,"anchor_tree_leaves")).getDefaultState();
        SpheroidType ANCHOR_TREE = new ShellSpheroidType(null, 10, 20, anchor_tree_log, anchor_tree_leaves, 2, 3);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.WOOD, 0.6F, ANCHOR_TREE);

        // WILLOW TREE
        BlockState willow_tree_log = Registry.BLOCK.get(new Identifier(MOD_ID,"willow_tree_log")).getDefaultState();
        BlockState willow_tree_leaves = Registry.BLOCK.get(new Identifier(MOD_ID,"willow_tree_leaves")).getDefaultState();
        SpheroidType WILLOW_TREE = new ShellSpheroidType(null, 8, 14, willow_tree_log, willow_tree_leaves, 2, 3);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.WOOD, 1.0F, WILLOW_TREE);

        // NETHER SAKURA TREE
        BlockState nether_sakura_tree_log = Registry.BLOCK.get(new Identifier(MOD_ID,"nether_sakura_tree_log")).getDefaultState();
        BlockState nether_sakura_tree_leaves = Registry.BLOCK.get(new Identifier(MOD_ID,"nether_sakura_tree_leaves")).getDefaultState();
        SpheroidType NETHER_SAKURA_TREE = new ShellSpheroidType(null, 7, 12, nether_sakura_tree_log, nether_sakura_tree_leaves, 2, 3);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.WOOD, 1.0F, NETHER_SAKURA_TREE);


        // OBSIDIAN GLASS
        BlockState obsidian = Blocks.OBSIDIAN.getDefaultState();
        BlockState obsidian_glass = Registry.BLOCK.get(new Identifier(MOD_ID,"obsidian_glass")).getDefaultState();
        BlockState blue_obsidian_glass = Registry.BLOCK.get(new Identifier(MOD_ID,"blue_obsidian_glass")).getDefaultState();
        BlockState blue_obsidian = Registry.BLOCK.get(new Identifier(MOD_ID,"blue_obsidian")).getDefaultState();

        SpheroidType OBSIDIAN_GLASS = new ShellSpheroidType(null, 8, 20, obsidian, obsidian_glass, 2, 4)
            .addShellSpeckles(obsidian, 0.1F);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.DECORATIVE, 0.5F, OBSIDIAN_GLASS);
        SpheroidType BLUE_OBSIDIAN_GLASS = new ShellSpheroidType(null, 8, 20, blue_obsidian, blue_obsidian_glass, 2, 4)
                .addShellSpeckles(blue_obsidian, 0.1F);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.DECORATIVE, 0.5F, BLUE_OBSIDIAN_GLASS);

        // TODO
        BlockState nether_reed = Registry.BLOCK.get(new Identifier(MOD_ID,"nether_reed")).getDefaultState(); // top: true / false; next to lava
        BlockState moss_cover = Registry.BLOCK.get(new Identifier(MOD_ID,"moss_cover")).getDefaultState(); // on anchor tree tops

        // WALLS (jungle)
        BlockState lucis_mushroom = Registry.BLOCK.get(new Identifier(MOD_ID,"lucis_mushroom")).getDefaultState(); // several
        BlockState jungle_moss = Registry.BLOCK.get(new Identifier(MOD_ID,"jungle_moss")).getDefaultState(); // facing <direction>
        BlockState wall_mushroom_brown = Registry.BLOCK.get(new Identifier(MOD_ID,"wall_mushroom_brown")).getDefaultState(); // facing <direction>
        BlockState wall_mushroom_red = Registry.BLOCK.get(new Identifier(MOD_ID,"wall_mushroom_red")).getDefaultState(); // facing <direction>
    }

}
