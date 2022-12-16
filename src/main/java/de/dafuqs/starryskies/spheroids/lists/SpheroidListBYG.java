package de.dafuqs.starryskies.spheroids.lists;

public class SpheroidListBYG {
	
	private static final String MOD_ID = "byg";
	
	// TODO: migrate those to json
	/*private static void setupEnd(SpheroidTypeLoader spheroidLoader) {
        BlockState end_stone = Blocks.END_STONE.getDefaultState();

        BlockState purpur_stone = getDefaultBlockState(MOD_ID,"purpur_stone");
        SpheroidType PURPUR_STONE_SMALL = new ModularSpheroidType(null, 4, 8, purpur_stone);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.ESSENTIAL, 0.5F, PURPUR_STONE_SMALL);

        SpheroidType PURPUR_STONE_BIG = new ModularSpheroidType(null, 9, 17, purpur_stone);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.ESSENTIAL, 0.5F, PURPUR_STONE_BIG);

        SpheroidType SPECKLED_END_STONE = new ShellSpheroidType(null, 5, 12, purpur_stone, end_stone, 3, 4)
                .addShellSpeckles(purpur_stone, 0.2F);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.ESSENTIAL, 0.5F, SPECKLED_END_STONE);

        // SHATTERED DESERT
        BlockState white_sand = getDefaultBlockState(MOD_ID,"white_sand");
        BlockState oddity_cactus = getDefaultBlockState(MOD_ID,"oddity_cactus"); // stacked like cactus
        CactusDecorator ODDITY_CACTUS_DECORATOR = new CactusDecorator(oddity_cactus);
        BlockState oddity_bush = getDefaultBlockState(MOD_ID,"oddity_bush"); // plant
        PlantDecorator ODDITY_BUSH_DECORATOR = new PlantDecorator(oddity_bush, 0.02F);

        SpheroidType SHATTERED_DESERT = new ModularSpheroidType(null, 8, 13, white_sand)
            .setBottomBlockState(end_stone)
            .addDecorator(ODDITY_BUSH_DECORATOR, 0.9F)
            .addDecorator(ODDITY_CACTUS_DECORATOR, 0.9F);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.5F, SHATTERED_DESERT);

        BlockState black_sand = getDefaultBlockState(MOD_ID,"black_sand");
        BlockState black_sandstone = getDefaultBlockState(MOD_ID,"black_sandstone");
        SpheroidType BLACK_SAND = new ModularSpheroidType(null, 8, 13, black_sand)
                .setBottomBlockState(black_sandstone);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.4F, BLACK_SAND);

        // NIGHTSHADE FOREST
        BlockState nightshade_phylium = getDefaultBlockState(MOD_ID,"nightshade_phylium");
        BlockState nightshade_sprouts = getDefaultBlockState(MOD_ID,"nightshade_sprouts"); // plant
        PlantDecorator NIGHTSHADE_SPROUTS_DECORATOR = new PlantDecorator(nightshade_sprouts, 0.2F);
        BlockState nightshade_roots = getDefaultBlockState(MOD_ID,"nightshade_roots"); // double plant
        DoublePlantDecorator NIGHTSHADE_ROOTS_DECORATOR = new DoublePlantDecorator(nightshade_roots, 0.1F);
        SpheroidType NIGHTSHADE_FOREST = new ModularSpheroidType(null, 8, 13, end_stone)
                .setTopBlockState(nightshade_phylium)
                .addDecorator(NIGHTSHADE_SPROUTS_DECORATOR, 0.9F)
                .addDecorator(NIGHTSHADE_ROOTS_DECORATOR, 0.9F);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.5F, NIGHTSHADE_FOREST);

        BlockState nightshade_log = getDefaultBlockState(MOD_ID,"nightshade_log");
        BlockState nightshade_leaves = getDefaultBlockState(MOD_ID,"nightshade_leaves").with(LeavesBlock.DISTANCE, 1);
        SpheroidType NIGHTSHADE_WOOD = new ShellSpheroidType(null, 6, 9, nightshade_log, nightshade_leaves, 2, 4);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.WOOD, 0.3F, NIGHTSHADE_WOOD);

        // IVIS FIELDS + ETHEREAL ISLANDS
        BlockState ivis_phylium = getDefaultBlockState(MOD_ID,"ivis_phylium");
        BlockState ivis_roots = getDefaultBlockState(MOD_ID,"ivis_roots");
        PlantDecorator IVIS_ROOTS_DECORATOR = new PlantDecorator(ivis_roots, 0.15F);
        BlockState ether_bush = getDefaultBlockState(MOD_ID,"ether_bush");
        PlantDecorator ETHER_BUSH_DECORATOR = new PlantDecorator(ether_bush, 0.04F);

        SpheroidType IVIS_FIELDS = new ModularSpheroidType(null, 6, 14, end_stone)
                .setTopBlockState(ivis_phylium)
                .addDecorator(IVIS_ROOTS_DECORATOR, 0.9F)
                .addDecorator(ETHER_BUSH_DECORATOR, 0.4F);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.5F, IVIS_FIELDS);

        BlockState bulbis_stem = getDefaultBlockState(MOD_ID,"bulbis_stem");
        BlockState bulbis_shell = getDefaultBlockState(MOD_ID,"bulbis_shell");
        SpheroidType BULBIS = new MushroomSpheroidType (null, 6, 14, bulbis_stem, bulbis_shell, 2, 2);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.WOOD, 0.3F, BULBIS);

        BlockState purple_bulbis_shell = getDefaultBlockState(MOD_ID,"purple_bulbis_shell");
        SpheroidType PURPLE_BULBIS = new MushroomSpheroidType (null, 6, 14, bulbis_stem, purple_bulbis_shell, 2, 2);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.WOOD, 0.3F, PURPLE_BULBIS);

        // CRYPTIC WASTES
        BlockState cryptic_stone = getDefaultBlockState(MOD_ID,"cryptic_stone");
        BlockState cryptic_magma_block = getDefaultBlockState(MOD_ID,"cryptic_magma_block");
        BlockState cryptic_fire = getDefaultBlockState(MOD_ID,"cryptic_fire");
        PlantDecorator CRYPTIC_FIRE_DECORATOR = new PlantDecorator(cryptic_fire, 0.15F);
        BlockState scorched_bush = getDefaultBlockState(MOD_ID,"scorched_bush");
        PlantDecorator SCORCHED_BUSH_DECORATOR = new PlantDecorator(scorched_bush, 0.1F);
        BlockState scorched_grass = getDefaultBlockState(MOD_ID,"scorched_grass");
        PlantDecorator SCORCHED_GRASS_DECORATOR = new PlantDecorator(scorched_grass, 0.05F);

        SpheroidType CRYPTIC_WASTES = new ShellSpheroidType(null, 8, 15, cryptic_stone, cryptic_stone, 1, 1)
                .addShellSpeckles(cryptic_magma_block, 0.2F)
                .addDecorator(CRYPTIC_FIRE_DECORATOR, 0.9F)
                .addDecorator(SCORCHED_BUSH_DECORATOR, 0.9F)
                .addDecorator(SCORCHED_GRASS_DECORATOR, 0.9F);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.5F, CRYPTIC_WASTES);

        // ETHEREAL ISLANDS
        BlockState ether_stone = getDefaultBlockState(MOD_ID,"ether_stone");
        BlockState lignite_ore = getDefaultBlockState(MOD_ID,"lignite_ore");
        SpheroidType LIGNITE_ORE = new CoreSpheroidType(null, 8, 15, lignite_ore, ether_stone, 4, 6);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.ORE, 0.5F, LIGNITE_ORE);
        SpheroidType ETHER_STONE = new CoreSpheroidType(null, 8, 15, ether_stone, ether_stone, 1, 1);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.5F, ETHER_STONE);

        BlockState ether_soil = getDefaultBlockState(MOD_ID,"ether_soil");
        BlockState ether_phylium = getDefaultBlockState(MOD_ID,"ether_phylium");
        BlockState thereal_bellflower = getDefaultBlockState(MOD_ID,"thereal_bellflower");
        PlantDecorator THEREAL_BELLFOWER_DECORATOR = new PlantDecorator(thereal_bellflower, 0.03F);
        BlockState ether_grass = getDefaultBlockState(MOD_ID,"ether_grass");
        PlantDecorator ETHER_GRASS_DECORATOR = new PlantDecorator(ether_grass, 0.15F);

        SpheroidType ETHER_SOIL = new ModularSpheroidType(null, 6, 10, ether_soil)
                .setTopBlockState(ether_phylium)
                .addDecorator(THEREAL_BELLFOWER_DECORATOR, 0.9F)
                .addDecorator(ETHER_GRASS_DECORATOR, 0.9F);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.5F, ETHER_SOIL);

        BlockState ether_log = getDefaultBlockState(MOD_ID,"ether_log");
        BlockState ether_leaves = getDefaultBlockState(MOD_ID,"ether_leaves").with(LeavesBlock.DISTANCE, 1);
        SpheroidType ETHER_WOOD = new ShellSpheroidType(null, 6, 8, ether_log, ether_leaves, 3, 4);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.WOOD, 0.4F, ETHER_WOOD);

        // VISCAL ISLES
        BlockState vermilion_sculk = getDefaultBlockState(MOD_ID,"vermilion_sculk");
        BlockState vermilion_sculk_growth = getDefaultBlockState(MOD_ID,"vermilion_sculk_growth");
        PlantDecorator VERMILION_SCULK_GROWTH_DECORATOR = new PlantDecorator(vermilion_sculk_growth, 0.15F);
        BlockState therium_crystal = getDefaultBlockState(MOD_ID,"therium_crystal");
        PlantDecorator THERIUM_CRYSTAL_DECORATOR = new PlantDecorator(therium_crystal, 0.1F);

        SpheroidType VISCAL_ISLES = new ModularSpheroidType(null, 6, 10, ether_stone)
                .setTopBlockState(vermilion_sculk)
                .addDecorator(VERMILION_SCULK_GROWTH_DECORATOR, 0.9F)
                .addDecorator(THERIUM_CRYSTAL_DECORATOR, 0.9F);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.5F, VISCAL_ISLES);

        // SHULKREN FOREST
        BlockState shulkren_phylium = getDefaultBlockState(MOD_ID,"shulkren_phylium");
        BlockState shulkren_fungus = getDefaultBlockState(MOD_ID,"shulkren_fungus");
        PlantDecorator SHULKREN_FUNGUS_DECORATOR = new PlantDecorator(shulkren_fungus, 0.05F);
        SpheroidType SHULKREN_FOREST = new ModularSpheroidType(null, 6, 10, end_stone)
                .setTopBlockState(shulkren_phylium)
                .addDecorator(SHULKREN_FUNGUS_DECORATOR, 0.9F);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.5F, SHULKREN_FOREST);

        BlockState white_mushroom_stem = getDefaultBlockState(MOD_ID,"white_mushroom_stem");
        BlockState shulkren_wart_block = getDefaultBlockState(MOD_ID,"shulkren_wart_block");
        BlockState purple_shroomlight = getDefaultBlockState(MOD_ID,"purple_shroomlight");

        BlockState shulkren_vine_plant = getDefaultBlockState(MOD_ID,"shulkren_vine_plant");
        BlockState shulkren_vine = getDefaultBlockState(MOD_ID,"shulkren_vine");
        HugeUnderPlantDecorator SHULKREN_VINE_DECORATOR = new HugeUnderPlantDecorator(shulkren_vine_plant, 0.1F, 2, 6).setLastBlockState(shulkren_vine);

        SpheroidType SHULKREN_WART = new ShellSpheroidType(null, 7, 10, white_mushroom_stem, shulkren_wart_block, 1, 3)
                .addShellSpeckles(purple_shroomlight, 0.1F)
                .addDecorator(SHULKREN_VINE_DECORATOR, 0.9F);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.WOOD, 0.4F, SHULKREN_WART);
	}*/
	
}
