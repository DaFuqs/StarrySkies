package de.dafuqs.starrysky.spheroid.lists;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.dimension.SpheroidDistributionType;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import de.dafuqs.starrysky.dimension.decorators.DoublePlantDecorator;
import de.dafuqs.starrysky.dimension.decorators.PlantDecorator;
import de.dafuqs.starrysky.spheroid.types.*;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static de.dafuqs.starrysky.dimension.SpheroidLoader.SpheroidDimensionType.END;

public class SpheroidListBetterEnd extends SpheroidList {

    private static final String MOD_ID = "betherend";


    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateBetterEndSpheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        StarrySkyCommon.LOGGER.info("[StarrySky] Loading Better End integration...");

        BlockState end_stone = Registry.BLOCK.get(new Identifier("end_stone")).getDefaultState();

        // CHORUS FOREST
        BlockState chorus_nylium = Registry.BLOCK.get(new Identifier(MOD_ID,"chorus_nylium")).getDefaultState(); // overgrown end stone
        BlockState chorus_grass = Registry.BLOCK.get(new Identifier(MOD_ID,"chorus_grass")).getDefaultState(); // plant
        PlantDecorator CHORUS_GRASS_DECORATOR = new PlantDecorator(chorus_grass, 0.25F);
        SpheroidType CHORUS_NYLIUM = new ModularSpheroidType(null, 7, 14, end_stone)
                .setTopBlockState(chorus_nylium)
                .addDecorator(CHORUS_GRASS_DECORATOR, 0.9F)
                .addDecorator(SpheroidListVanillaEnd.SpheroidDecorators.CHORUS_FRUIT, 0.7F);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.5F, CHORUS_NYLIUM);

        // CRYSTAL MOUNTAINS
        BlockState crystal_moss = Registry.BLOCK.get(new Identifier(MOD_ID,"crystal_moss")).getDefaultState(); // overgrown end stone
        BlockState crystal_grass = Registry.BLOCK.get(new Identifier(MOD_ID,"crystal_grass")).getDefaultState(); // plant
        PlantDecorator CRYSTAL_GRASS_DECORATOR = new PlantDecorator(crystal_grass, 0.25F);
        SpheroidType CRYSTAL_MOSS = new ModularSpheroidType(null, 7, 14, end_stone)
                .setTopBlockState(crystal_moss)
                .addDecorator(CRYSTAL_GRASS_DECORATOR, 0.9F);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.5F, CRYSTAL_MOSS);

        BlockState aurora_crystal = Registry.BLOCK.get(new Identifier(MOD_ID,"aurora_crystal")).getDefaultState();
        SpheroidType AURORA_CRYSTAL = new ModularSpheroidType(null, 7, 14, aurora_crystal);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.5F, AURORA_CRYSTAL);

        // DUST WASTELANDS
        BlockState endstone_dust = Registry.BLOCK.get(new Identifier(MOD_ID,"endstone_dust")).getDefaultState();
        SpheroidType ENDSTONE_DUST = new ModularSpheroidType(null, 9, 17, endstone_dust)
                .setBottomBlockState(end_stone);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.4F, ENDSTONE_DUST);

        // FOGGY MUSHROOMLAND
        BlockState end_moss = Registry.BLOCK.get(new Identifier(MOD_ID,"end_moss")).getDefaultState();
        BlockState end_mycelium = Registry.BLOCK.get(new Identifier(MOD_ID,"end_mycelium")).getDefaultState();

        BlockState creeping_moss = Registry.BLOCK.get(new Identifier(MOD_ID,"creeping_moss")).getDefaultState();
        BlockState umbrella_moss = Registry.BLOCK.get(new Identifier(MOD_ID,"umbrella_moss")).getDefaultState();
        BlockState umbrella_moss_tall = Registry.BLOCK.get(new Identifier(MOD_ID,"umbrella_moss_tall")).getDefaultState();
        PlantDecorator CREEPING_MOSS_DECORATOR = new PlantDecorator(creeping_moss, 0.1F);
        PlantDecorator UMBRELLA_MOSS_DECORATOR = new PlantDecorator(umbrella_moss, 0.1F);
        DoublePlantDecorator UMBRELLA_MOSS_TALL_DECORATOR = new DoublePlantDecorator(umbrella_moss_tall, 0.05F);
        //BlockState dense_vine = Registry.BLOCK.get(new Identifier(MOD_ID,"dense_vine")).getDefaultState(); // growing downwards on glowshrooms, Bottom, middle, top TODO

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
                .addDecorator(UMBRELLA_MOSS_TALL_DECORATOR, 0.8F);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.4F, UMBRELLA_MOSS);

        BlockState mossy_glowshroom_log = Registry.BLOCK.get(new Identifier(MOD_ID,"mossy_glowshroom_log")).getDefaultState();
        BlockState mossy_glowshroom_cap = Registry.BLOCK.get(new Identifier(MOD_ID,"mossy_glowshroom_cap")).getDefaultState();
        BlockState mossy_glowshroom_hymenophore = Registry.BLOCK.get(new Identifier(MOD_ID,"mossy_glowshroom_hymenophore")).getDefaultState();
        SpheroidType MOSSY_GLOWSHROOM = new DoubleCoreSpheroidType(null, 5, 12, mossy_glowshroom_log, mossy_glowshroom_hymenophore, mossy_glowshroom_cap, 2, 4, 2, 4);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.WOOD, 0.4F, MOSSY_GLOWSHROOM);

        // MEGA LAKE TODO
        BlockState lacugrove_log = Registry.BLOCK.get(new Identifier(MOD_ID,"lacugrove_log")).getDefaultState();
        BlockState lacugrove_leaves = Registry.BLOCK.get(new Identifier(MOD_ID,"lacugrove_leaves")).getDefaultState().with(LeavesBlock.DISTANCE, 1);;
        SpheroidType LACUGROVE_WOOD = new ShellSpheroidType(null, 10, 15, lacugrove_log, lacugrove_leaves, 3, 4);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.WOOD, 0.4F, LACUGROVE_WOOD);

        BlockState end_lotus_stem = Registry.BLOCK.get(new Identifier(MOD_ID,"end_lotus_stem")).getDefaultState(); // bottom, middle, top, waterloggable
        BlockState end_lotus_flower = Registry.BLOCK.get(new Identifier(MOD_ID,"end_lotus_flower")).getDefaultState(); // on top of end lotos stem

        BlockState lotus_leaf = Registry.BLOCK.get(new Identifier(MOD_ID,"lotus_leaf")).getDefaultState(); // facing + shape; top of water
        BlockState end_lily = Registry.BLOCK.get(new Identifier(MOD_ID,"end_lily")).getDefaultState(); // bottom, middle, top (top of water), waterloggable
        BlockState bubble_coral = Registry.BLOCK.get(new Identifier(MOD_ID,"bubble_coral")).getDefaultState(); // bottom of water

        BlockState blue_vine = Registry.BLOCK.get(new Identifier(MOD_ID,"creeping_moss")).getDefaultState(); // Bottom, middle, top
        BlockState blue_vine_fur = Registry.BLOCK.get(new Identifier(MOD_ID,"blue_vine_fur")).getDefaultState(); // on top of blue vine top


        // PAINTED MOUNTAINS
        BlockState flavolite = Registry.BLOCK.get(new Identifier(MOD_ID,"flavolite")).getDefaultState();
        SpheroidType FLAVOLITE = new ModularSpheroidType(null, 6, 12, flavolite);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.ESSENTIAL, 0.4F, FLAVOLITE);
        SpheroidType SPECKLED_FLAVOLITE = new ShellSpheroidType(null, 6, 12, flavolite, end_stone, 3, 4).addShellSpeckles(flavolite, 0.15F);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.ESSENTIAL, 1.0F, SPECKLED_FLAVOLITE);

        BlockState violecite = Registry.BLOCK.get(new Identifier(MOD_ID,"violecite")).getDefaultState();
        SpheroidType VIOLECITE = new ModularSpheroidType(null, 6, 14, violecite);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.ESSENTIAL, 0.4F, VIOLECITE);

        // SHADOW FOREST
        BlockState dragon_tree_log = Registry.BLOCK.get(new Identifier(MOD_ID,"dragon_tree_log")).getDefaultState();
        BlockState dragon_tree_leaves = Registry.BLOCK.get(new Identifier(MOD_ID,"dragon_tree_leaves")).getDefaultState().with(LeavesBlock.DISTANCE, 1);;
        //BlockState purple_polyphore = Registry.BLOCK.get(new Identifier(MOD_ID,"purple_polyphore")).getDefaultState(); // on side of dragon tree log
        SpheroidType DRAGON_TREE = new ShellSpheroidType(null, 10, 17, dragon_tree_log, dragon_tree_leaves, 2, 4);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.WOOD, 0.3F, DRAGON_TREE);

        BlockState shadow_grass = Registry.BLOCK.get(new Identifier(MOD_ID,"shadow_grass")).getDefaultState();
        BlockState shadow_plant = Registry.BLOCK.get(new Identifier(MOD_ID,"shadow_plant")).getDefaultState();
        BlockState needlegrass = Registry.BLOCK.get(new Identifier(MOD_ID,"needlegrass")).getDefaultState();
        BlockState shadow_berry = Registry.BLOCK.get(new Identifier(MOD_ID,"shadow_berry")).getDefaultState();

        PlantDecorator SHADOW_PLANT_DECORATOR = new PlantDecorator(shadow_plant, 0.2F);
        PlantDecorator NEEDLEGRASS_DECORATOR = new PlantDecorator(needlegrass, 0.1F);
        PlantDecorator SHADOW_BERRY_DECORATOR = new PlantDecorator(shadow_berry, 0.05F);
        SpheroidType SHADOW_GRASS = new ModularSpheroidType(null, 6, 14, end_stone)
                .setTopBlockState(shadow_grass)
                .addDecorator(SHADOW_PLANT_DECORATOR, 0.9F)
                .addDecorator(NEEDLEGRASS_DECORATOR, 0.7F)
                .addDecorator(SHADOW_BERRY_DECORATOR, 0.3F);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.4F, SHADOW_GRASS);

        // ENDER ORE
        BlockState ender_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"ender_ore")).getDefaultState();
        SpheroidType ENDER_ORE = new CoreSpheroidType(null, 6, 14, ender_ore, end_stone, 2, 4);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.ORE, 0.5F, ENDER_ORE);

        // ?? TODO
        BlockState cave_moss = Registry.BLOCK.get(new Identifier(MOD_ID,"cave_moss")).getDefaultState();
        BlockState cave_grass = Registry.BLOCK.get(new Identifier(MOD_ID,"cave_grass")).getDefaultState();
        BlockState cave_bush = Registry.BLOCK.get(new Identifier(MOD_ID,"cave_bush")).getDefaultState();
        PlantDecorator CAVE_GRASS_DECORATOR = new PlantDecorator(cave_grass, 0.1F);
        PlantDecorator CAVE_BUSH_DECORATOR = new PlantDecorator(cave_bush, 0.3F);
        SpheroidType CAVE_MOSS = new CaveSpheroidType(null, 8, 14, end_stone, cave_moss, 2, 4)
                .addDecorator(CAVE_BUSH_DECORATOR, 0.9F)
                .addDecorator(CAVE_GRASS_DECORATOR, 0.9F);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 0.4F, CAVE_MOSS);

        // PYTADENDRON
        BlockState pythadendron_log = Registry.BLOCK.get(new Identifier(MOD_ID,"pythadendron_log")).getDefaultState();
        BlockState pythadendron_leaves = Registry.BLOCK.get(new Identifier(MOD_ID,"pythadendron_leaves")).getDefaultState().with(LeavesBlock.DISTANCE, 1);
        SpheroidType PYTHADENDRON_WOOD = new ShellSpheroidType(null, 10, 17, pythadendron_log, pythadendron_leaves, 2, 4);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.WOOD, 0.3F, PYTHADENDRON_WOOD);




    }

}
