package de.dafuqs.starryskies.spheroids.lists;

public class SpheroidListBYG {
	
	private static final String MOD_ID = "byg";
	
	// TODO: migrate those to json
	/*private static void setupNether(SpheroidTypeLoader spheroidLoader) {

        // BRIMSTONE CAVERNS
        BlockState brimstone = getDefaultBlockState(MOD_ID,"brimstone");
        BlockState anthracite_ore = getDefaultBlockState(MOD_ID,"anthracite_ore"); // like coal
        BlockState boric_fire = getDefaultBlockState(MOD_ID,"boric_fire"); // like coal
        PlantDecorator BORIC_FIRE_DECORATOR = new PlantDecorator(boric_fire, 0.05F);

        SpheroidType BRIMSTONE = new ModularSpheroidType(null, 5, 12, brimstone)
                .addDecorator(BORIC_FIRE_DECORATOR, 0.75F);

        SpheroidType ANTHRACITE_ORE = new CoreSpheroidType(null, 7, 12, anthracite_ore, brimstone, 3, 6)
                .addDecorator(BORIC_FIRE_DECORATOR, 0.5F);

        spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.DECORATIVE, 0.5F, BRIMSTONE);
        spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.ORE, 0.5F, ANTHRACITE_ORE);

        // CRIMSON GARDENS
        BlockState overgrown_crimson_blackstone = getDefaultBlockState(MOD_ID,"overgrown_crimson_blackstone");
        BlockState tall_embur_roots = getDefaultBlockState(MOD_ID,"tall_embur_roots");
        DoublePlantDecorator TALL_EMBUR_ROOTS_DECORATOR = new DoublePlantDecorator(tall_embur_roots, 0.05F);
        SpheroidType CRIMSON_GARDENS = new ModularSpheroidType(null, 7, 12, Blocks.BLACKSTONE.getDefaultState())
                .setTopBlockState(overgrown_crimson_blackstone)
                .addDecorator(SpheroidListVanillaNether.SpheroidDecorators.CRIMSON_ROOTS, 0.5F)
                .addDecorator(SpheroidListVanilla.SpheroidDecorators.MUSHROOMS, 0.5F)
                .addDecorator(SpheroidListVanillaNether.SpheroidDecorators.CRIMSON_ROOTS, 0.5F)
                .addDecorator(TALL_EMBUR_ROOTS_DECORATOR, 0.5F)
                .addDecorator(SpheroidListVanillaNether.SpheroidDecorators.WEEPING_VINES, 0.5F);
        spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.DECORATIVE, 0.5F, CRIMSON_GARDENS);

        // EMBUR BOG
        BlockState blue_netherrack = getDefaultBlockState(MOD_ID,"blue_netherrack");
        SpheroidType BLUE_NETHERRACK = new ModularSpheroidType(null, 5, 15, blue_netherrack);
        spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.DECORATIVE, 0.5F, BLUE_NETHERRACK);

        // GLOWSTONE GARDENS
        BlockState weeping_roots_plant = getDefaultBlockState(MOD_ID,"weeping_roots_plant");
        HugeUnderPlantDecorator WEEPING_ROOTS_PLANT_DECORATOR = new HugeUnderPlantDecorator(weeping_roots_plant, 0.09F, 1, 7);
        SpheroidListVanillaNether.GLOWSTONE.addDecorator(WEEPING_ROOTS_PLANT_DECORATOR, 0.05F);

        // QUARTZ DESERT
        BlockState quartzite_sand = getDefaultBlockState(MOD_ID,"quartzite_sand");
        BlockState raw_quartz_block = getDefaultBlockState(MOD_ID,"raw_quartz_block");

        BlockState quartz_crystal = getDefaultBlockState(MOD_ID,"quartz_crystal");
        PlantDecorator QUARTZ_CRYSTAL_DECORATOR = new PlantDecorator(quartz_crystal, 0.1F);

        BlockState hanging_bones = getDefaultBlockState(MOD_ID,"hanging_bones");
        HugeUnderPlantDecorator HANGING_BONES_DECORATOR = new HugeUnderPlantDecorator(hanging_bones, 0.05F, 2, 5);

        SpheroidType QUARTZ_DESERT = new ShellSpheroidType(null, 10, 16, raw_quartz_block, quartzite_sand, 2, 4)
                .addShellSpeckles(quartzite_sand, 0.2F)
                .addDecorator(QUARTZ_CRYSTAL_DECORATOR, 0.95F)
                .addDecorator(HANGING_BONES_DECORATOR, 0.8F)
                .addDecorator(SpheroidListVanilla.SpheroidDecorators.MUSHROOMS, 0.25F)
                .addDecorator(SpheroidListVanillaNether.SpheroidDecorators.FIRE, 0.3F);
        spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.DECORATIVE, 0.5F, QUARTZ_DESERT);

        // SUBZERO HYPOGEAL
        BlockState subzero_ash_block = getDefaultBlockState(MOD_ID,"subzero_ash_block");
        BlockState frost_magma = getDefaultBlockState(MOD_ID,"frost_magma");

        SpheroidType SUBZERO_ASH = new ShellSpheroidType(null, 10, 16, subzero_ash_block, subzero_ash_block, 2, 4)
                .addShellSpeckles(frost_magma, 0.2F);
        spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.DECORATIVE, 0.5F, SUBZERO_ASH);

        SpheroidType FROST_MAGMA = new ModularSpheroidType(null, 10, 16, frost_magma)
                .addDecorator(SpheroidListVanillaNether.SpheroidDecorators.SOUL_FIRE, 0.9F);
        spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.DECORATIVE, 0.5F, FROST_MAGMA);

        // SYTHIAN TORRIDS
        BlockState mossy_netherrack = getDefaultBlockState(MOD_ID,"mossy_netherrack");
        SpheroidType MOSSY_NETHERRACK = new ModularSpheroidType(null, 10, 16, mossy_netherrack);
        spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.DECORATIVE, 0.5F, MOSSY_NETHERRACK);

        BlockState sythian_hyphae = getDefaultBlockState(MOD_ID,"sythian_hyphae");
        BlockState hanging_sythian_roots_plant = getDefaultBlockState(MOD_ID,"hanging_sythian_roots_plant");
        BlockState hanging_sythian_roots = getDefaultBlockState(MOD_ID,"hanging_sythian_roots");
        HugeUnderPlantDecorator HANGING_SYTHIAN_ROOTS_DECORATOR = new HugeUnderPlantDecorator(hanging_sythian_roots_plant, 0.1F, 2, 6).setLastBlockState(hanging_sythian_roots);
        SpheroidType SYTHIAN_HYPHAE = new ModularSpheroidType(null, 10, 16, sythian_hyphae)
                .addDecorator(HANGING_SYTHIAN_ROOTS_DECORATOR, 1.0F);
        spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.DECORATIVE, 0.5F, SYTHIAN_HYPHAE);

        BlockState sythian_nylium = getDefaultBlockState(MOD_ID,"sythian_nylium"); // on top of netherrack
        BlockState sythian_sprout = getDefaultBlockState(MOD_ID,"sythian_nylium"); // plant
        PlantDecorator SYTHIAN_SPROUT_DECORATOR = new PlantDecorator(sythian_sprout, 0.1F);
        BlockState sythian_stalk_block = getDefaultBlockState(MOD_ID,"sythian_stalk_block"); // like bamboo
        BambooDecorator SYTHIAN_STALK_DECORATOR = new BambooDecorator(sythian_stalk_block.with(BambooBlock.AGE, 0).with(BambooBlock.STAGE, 0), sythian_stalk_block.with(BambooBlock.AGE, 0).with(BambooBlock.STAGE, 0));
        SpheroidType SYTHIAN_NYLIUM = new ModularSpheroidType(null, 10, 16, Blocks.NETHERRACK.getDefaultState())
                .setTopBlockState(sythian_nylium)
                .addDecorator(SYTHIAN_SPROUT_DECORATOR, 0.8F)
                .addDecorator(SYTHIAN_STALK_DECORATOR, 0.8F);
        spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.DECORATIVE, 0.5F, SYTHIAN_NYLIUM);


        BlockState sythian_wart_block = getDefaultBlockState(MOD_ID,"sythian_wart_block");
        BlockState sythian_stem = getDefaultBlockState(MOD_ID,"sythian_stem");
        SpheroidType SYTHIAN_WART = new ShellSpheroidType(null, 10, 16, sythian_stem, sythian_wart_block, 1, 2)
                .addShellSpeckles(Blocks.SHROOMLIGHT.getDefaultState(), 0.05F);
        spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.WOOD, 0.5F, SYTHIAN_WART);

        // WARPED DESERT
        BlockState soul_shroom_block = getDefaultBlockState(MOD_ID,"soul_shroom_block");
        BlockState soul_shroom_spore = getDefaultBlockState(MOD_ID,"soul_shroom_spore");
        BlockState soul_shroom_spore_end = getDefaultBlockState(MOD_ID,"soul_shroom_spore_end");
        HugeUnderPlantDecorator SOUL_SHROOM_SPORE_DECORATOR = new HugeUnderPlantDecorator(soul_shroom_spore, 0.1F, 2, 5).setLastBlockState(soul_shroom_spore_end);

        BlockState nylium_soul_sand = getDefaultBlockState(MOD_ID,"nylium_soul_sand");
        BlockState warped_cactus = getDefaultBlockState(MOD_ID,"warped_cactus");
        CactusDecorator WARPED_CACTUS_DECORATOR = new CactusDecorator(warped_cactus);
        BlockState warped_coral = getDefaultBlockState(MOD_ID,"warped_coral");
        PlantDecorator WARPED_CORAL_DECORATOR = new PlantDecorator(warped_coral, 0.05F);
        BlockState warped_coral_fan = getDefaultBlockState(MOD_ID,"warped_coral_fan");
        PlantDecorator WARPED_CORAL_FAN_DECORATOR = new PlantDecorator(warped_coral_fan, 0.02F);
        SpheroidType NYLIUM_SOUL_SAND = new ShellSpheroidType(null, 10, 16, nylium_soul_sand, nylium_soul_sand, 1, 1)
                .addShellSpeckles(soul_shroom_block, 0.05F)
                .addDecorator(WARPED_CACTUS_DECORATOR, 0.9F)
                .addDecorator(WARPED_CORAL_DECORATOR, 0.8F)
                .addDecorator(WARPED_CORAL_FAN_DECORATOR, 0.7F)
                .addDecorator(SOUL_SHROOM_SPORE_DECORATOR, 0.6F);
        spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.DECORATIVE, 0.5F, NYLIUM_SOUL_SAND);

        BlockState nylium_soul_soil = getDefaultBlockState(MOD_ID,"nylium_soul_soil");
        SpheroidType NYLIUM_SOUL_SOIL = new ShellSpheroidType(null, 10, 16, nylium_soul_soil, nylium_soul_soil, 1, 1)
                .addShellSpeckles(soul_shroom_block, 0.05F)
                .addDecorator(SpheroidListVanillaNether.SpheroidDecorators.SOUL_FIRE, 0.9F)
                .addDecorator(WARPED_CACTUS_DECORATOR, 0.8F)
                .addDecorator(WARPED_CORAL_DECORATOR, 0.7F)
                .addDecorator(WARPED_CORAL_FAN_DECORATOR, 0.6F)
                .addDecorator(SOUL_SHROOM_SPORE_DECORATOR, 0.5F);
        spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.DECORATIVE, 0.5F, NYLIUM_SOUL_SOIL);

        BlockState warped_coral_block = getDefaultBlockState(MOD_ID,"warped_coral_block");
        SpheroidType WARPED_CORAL_BLOCK = new ModularSpheroidType(null, 5, 7, warped_coral_block);
        spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.DECORATIVE, 0.4F, WARPED_CORAL_BLOCK);

        // WAILING GARTH
        BlockState overgrown_netherrack = getDefaultBlockState(MOD_ID,"overgrown_netherrack");
        BlockState whaling_grass = getDefaultBlockState(MOD_ID,"whaling_grass"); // on overgrown netherrack
        PlantDecorator WHALING_GRASS_DECORATOR = new PlantDecorator(whaling_grass, 0.1F);
        BlockState scorched_bush = getDefaultBlockState(MOD_ID,"scorched_bush");
        PlantDecorator SCORCHED_BUSH_DECORATOR = new PlantDecorator(scorched_bush, 0.05F);
        BlockState scorched_grass = getDefaultBlockState(MOD_ID,"scorched_grass");
        PlantDecorator SCORCHED_GRASS_DECORATOR = new PlantDecorator(scorched_grass, 0.1F);

        SpheroidType WAILING_GARTH = new ModularSpheroidType(null, 7, 14, Blocks.NETHERRACK.getDefaultState())
                .setTopBlockState(overgrown_netherrack)
                .addDecorator(WHALING_GRASS_DECORATOR, 0.9F)
                .addDecorator(SpheroidListVanillaNether.SpheroidDecorators.NETHER_SPROUTS, 0.7F)
                .addDecorator(SCORCHED_BUSH_DECORATOR, 0.5F)
                .addDecorator(SCORCHED_GRASS_DECORATOR, 0.9F);
        spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.DECORATIVE, 0.4F, WAILING_GARTH);

        BlockState black_puff_mushroom_block = getDefaultBlockState(MOD_ID,"black_puff_mushroom_block");
        BlockState brown_mushroom_stem = getDefaultBlockState(MOD_ID,"brown_mushroom_stem");
        SpheroidType BROWN_MUSHROOM = new MushroomSpheroidType (null, 6, 14, black_puff_mushroom_block, brown_mushroom_stem, 2, 2);
        spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.WOOD, 0.3F, BROWN_MUSHROOM);

        BlockState white_mushroom_stem = getDefaultBlockState(MOD_ID,"white_mushroom_stem");
        BlockState green_mushroom_block = getDefaultBlockState(MOD_ID,"green_mushroom_block");
        SpheroidType GREEN_MUSHROOM = new MushroomSpheroidType (null, 6, 14, white_mushroom_stem, green_mushroom_block, 2, 2);
        spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.WOOD, 0.3F, GREEN_MUSHROOM);

        BlockState mushroom_stem = Blocks.MUSHROOM_STEM.getDefaultState();
        BlockState weeping_milkcap_mushroom_block = getDefaultBlockState(MOD_ID,"weeping_milkcap_mushroom_block");
        SpheroidType WEEPING_MILKCAP = new MushroomSpheroidType (null, 6, 14, mushroom_stem, weeping_milkcap_mushroom_block, 2, 2);
        spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.WOOD, 0.3F, WEEPING_MILKCAP);
        
        BlockState wood_blewit_mushroom_block = getDefaultBlockState(MOD_ID,"wood_blewit_mushroom_block");
        SpheroidType BLEWIT_MUSHROOM = new MushroomSpheroidType (null, 6, 14, brown_mushroom_stem, wood_blewit_mushroom_block, 2, 2);
        spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.WOOD, 0.3F, BLEWIT_MUSHROOM);

        // WITHERING WOODS
        BlockState withering_oak_leaves = getDefaultBlockState(MOD_ID,"withering_oak_leaves").with(Properties.DISTANCE_1_7, 1);
        BlockState withering_oak_log = getDefaultBlockState(MOD_ID,"withering_oak_log");
        SpheroidType WITHERING_WOOD = new ShellSpheroidType(null, 7, 13, withering_oak_log, withering_oak_leaves, 2, 3);
        spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.WOOD, 0.4F, WITHERING_WOOD);

        BlockState magmatic_stone = getDefaultBlockState(MOD_ID,"magmatic_stone");
        SpheroidType MAGMATIC_STONE = new ModularSpheroidType(null, 7, 13, magmatic_stone);
        spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.DECORATIVE, 0.6F, MAGMATIC_STONE);

        // WEEPING MIRE
        BlockState lament_leaves = getDefaultBlockState(MOD_ID,"lament_leaves").with(Properties.DISTANCE_1_7, 1);
        BlockState lament_log = getDefaultBlockState(MOD_ID,"lament_log");
        SpheroidType LAMENT_WOOD = new ShellSpheroidType(null, 7, 10, lament_log, lament_leaves, 2, 3)
                .addShellSpeckles(Blocks.SHROOMLIGHT.getDefaultState(), 0.05F);
        spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.DECORATIVE, 0.5F, LAMENT_WOOD);

        BlockState lament_vine_plant = getDefaultBlockState(MOD_ID,"lament_vine_plant");
        BlockState lament_vine = getDefaultBlockState(MOD_ID,"lament_vine");
        HugeUnderPlantDecorator LAMENT_VINE_DECORATOR = new HugeUnderPlantDecorator(lament_vine_plant, 0.1F, 2, 5).setLastBlockState(lament_vine);
        WAILING_GARTH.addDecorator(LAMENT_VINE_DECORATOR, 0.15F);

        // ADDING TO VANILLA
        BlockState death_cap = getDefaultBlockState(MOD_ID,"death_cap"); // mushroom
        PlantDecorator DEATH_CAP_DECORATOR = new PlantDecorator(death_cap, 0.09F);
        SpheroidListVanillaNether.NETHERRACK.addDecorator(DEATH_CAP_DECORATOR, 0.1F);
    }

    private static void setupEnd(SpheroidTypeLoader spheroidLoader) {
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
