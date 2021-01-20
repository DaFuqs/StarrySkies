package de.dafuqs.starrysky.spheroid.lists;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.dimension.SpheroidDistributionType;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import de.dafuqs.starrysky.dimension.decorators.DoublePlantDecorator;
import de.dafuqs.starrysky.dimension.decorators.PlantDecorator;
import de.dafuqs.starrysky.spheroid.types.*;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;

import static de.dafuqs.starrysky.dimension.SpheroidLoader.SpheroidDimensionType.END;

public class SpheroidListBetterEnd extends SpheroidList {

    private static final String MOD_ID = "betherend";


    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateBetterEndSpheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        StarrySkyCommon.LOGGER.info("[StarrySky] Loading Better End integration...");

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
                .setBottomBlockState(end_moss)
                .addDecorator(CREEPING_MOSS_DECORATOR, 0.8F)
                .addDecorator(UMBRELLA_MOSS_DECORATOR, 0.8F)
                .addDecorator(UMBRELLA_MOSS_TALL_DECORATOR, 0.8F);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.4F, END_MOSS);
        SpheroidType UMBRELLA_MOSS = new ModularSpheroidType(null, 8, 14, end_stone)
                .setBottomBlockState(end_mycelium)
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

        // MEGA LAKE TODO
        BlockState lacugrove_log = getDefaultBlockState(MOD_ID,"lacugrove_log");
        BlockState lacugrove_leaves = getDefaultBlockState(MOD_ID,"lacugrove_leaves").with(LeavesBlock.DISTANCE, 1);;
        SpheroidType LACUGROVE_WOOD = new ShellSpheroidType(null, 10, 15, lacugrove_log, lacugrove_leaves, 3, 4);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.WOOD, 0.4F, LACUGROVE_WOOD);

        BlockState end_lotus_stem = getDefaultBlockState(MOD_ID,"end_lotus_stem"); // bottom, middle, top, waterloggable
        BlockState end_lotus_flower = getDefaultBlockState(MOD_ID,"end_lotus_flower"); // on top of end lotos stem

        BlockState lotus_leaf = getDefaultBlockState(MOD_ID,"lotus_leaf"); // facing + shape; top of water
        BlockState end_lily = getDefaultBlockState(MOD_ID,"end_lily"); // bottom, middle, top (top of water), waterloggable
        BlockState bubble_coral = getDefaultBlockState(MOD_ID,"bubble_coral"); // bottom of water


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



    }

}
