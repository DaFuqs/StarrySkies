package de.dafuqs.starrysky.spheroid.lists;

import com.terraformersmc.terraform.leaves.block.ExtendedLeavesBlock;
import com.terraformersmc.terraform.wood.block.SmallLogBlock;
import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.dimension.SpheroidDistributionType;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import de.dafuqs.starrysky.dimension.decorators.PlantDecorator;
import de.dafuqs.starrysky.spheroid.types.LiquidSpheroidType;
import de.dafuqs.starrysky.spheroid.types.ModularSpheroidType;
import de.dafuqs.starrysky.spheroid.types.ShellSpheroidType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static de.dafuqs.starrysky.dimension.SpheroidLoader.SpheroidDimensionType.OVERWORLD;
import static de.dafuqs.starrysky.spheroid.lists.SpheroidListVanilla.SpheroidDecorators.FERNS_DECORATOR;

public class SpheroidListTerrestria extends SpheroidList {

    private static final String MOD_ID = "terrestria";

    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateTerrestriaSpheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        StarrySkyCommon.LOGGER.info("Loading Terrestria integration...");

        // TREES
        BlockState terrestria_redwood_leaves = Registry.BLOCK.get(new Identifier(MOD_ID,"redwood_leaves")).getDefaultState().with(ExtendedLeavesBlock.DISTANCE, 1);
        BlockState terrestria_redwood_log = Registry.BLOCK.get(new Identifier(MOD_ID,"redwood_log")).getDefaultState();
        BlockState terrestria_hemlock_leaves = Registry.BLOCK.get(new Identifier(MOD_ID,"hemlock_leaves")).getDefaultState().with(ExtendedLeavesBlock.DISTANCE, 1);
        BlockState terrestria_hemlock_log = Registry.BLOCK.get(new Identifier(MOD_ID,"hemlock_log")).getDefaultState();
        BlockState terrestria_rubber_leaves = Registry.BLOCK.get(new Identifier(MOD_ID,"rubber_leaves")).getDefaultState().with(LeavesBlock.DISTANCE, 1);
        BlockState terrestria_rubber_log = Registry.BLOCK.get(new Identifier(MOD_ID,"rubber_log")).getDefaultState();
        BlockState terrestria_cypress_leaves = Registry.BLOCK.get(new Identifier(MOD_ID,"cypress_leaves")).getDefaultState().with(LeavesBlock.DISTANCE, 1);
        BlockState terrestria_cypress_log = Registry.BLOCK.get(new Identifier(MOD_ID,"cypress_log")).getDefaultState();
        BlockState terrestria_willow_leaves = Registry.BLOCK.get(new Identifier(MOD_ID,"willow_leaves")).getDefaultState().with(LeavesBlock.DISTANCE, 1);
        BlockState terrestria_willow_log = Registry.BLOCK.get(new Identifier(MOD_ID,"willow_log")).getDefaultState();
        BlockState terrestria_dark_japanese_maple_leaves = Registry.BLOCK.get(new Identifier(MOD_ID,"dark_japanese_maple_leaves")).getDefaultState().with(LeavesBlock.DISTANCE, 1);
        BlockState terrestria_japanese_maple_leaves = Registry.BLOCK.get(new Identifier(MOD_ID,"japanese_maple_leaves")).getDefaultState().with(LeavesBlock.DISTANCE, 1);
        BlockState terrestria_japanese_maple_shrub_leaves = Registry.BLOCK.get(new Identifier(MOD_ID,"japanese_maple_shrub_leaves")).getDefaultState().with(LeavesBlock.DISTANCE, 1);
        BlockState terrestria_japanese_maple_log = Registry.BLOCK.get(new Identifier(MOD_ID,"japanese_maple_log")).getDefaultState();
        BlockState terrestria_rainbow_eucalyptus_leaves = Registry.BLOCK.get(new Identifier(MOD_ID,"rainbow_eucalyptus_leaves")).getDefaultState().with(LeavesBlock.DISTANCE, 1);
        BlockState terrestria_rainbow_eucalyptus_log = Registry.BLOCK.get(new Identifier(MOD_ID,"rainbow_eucalyptus_log")).getDefaultState();
        BlockState terrestria_sakura_leaves = Registry.BLOCK.get(new Identifier(MOD_ID,"sakura_leaves")).getDefaultState().with(LeavesBlock.DISTANCE, 1);
        BlockState terrestria_sakura_log = Registry.BLOCK.get(new Identifier(MOD_ID,"sakura_log")).getDefaultState().with(SmallLogBlock.HAS_LEAVES, true).with(SmallLogBlock.DOWN, true).with(SmallLogBlock.UP, true).with(SmallLogBlock.SOUTH, true).with(SmallLogBlock.WEST, true).with(SmallLogBlock.EAST, true).with(SmallLogBlock.NORTH, true);
        BlockState terrestria_yucca_palm_leaves = Registry.BLOCK.get(new Identifier(MOD_ID,"yucca_palm_leaves")).getDefaultState().with(LeavesBlock.DISTANCE, 1);
        BlockState terrestria_yucca_palm_log = Registry.BLOCK.get(new Identifier(MOD_ID,"yucca_palm_log")).getDefaultState().with(SmallLogBlock.HAS_LEAVES, true).with(SmallLogBlock.DOWN, true).with(SmallLogBlock.UP, true).with(SmallLogBlock.SOUTH, true).with(SmallLogBlock.WEST, true).with(SmallLogBlock.EAST, true).with(SmallLogBlock.NORTH, true);
        BlockState terrestria_jungle_palm_leaves = Registry.BLOCK.get(new Identifier(MOD_ID,"jungle_palm_leaves")).getDefaultState().with(LeavesBlock.DISTANCE, 1);

        // VOLCANIC STONES
        BlockState terrestria_basalt = Registry.BLOCK.get(new Identifier(MOD_ID,"basalt")).getDefaultState();
        BlockState terrestria_basalt_cobblestone = Registry.BLOCK.get(new Identifier(MOD_ID,"basalt_cobblestone")).getDefaultState();
        BlockState terrestria_mossy_basalt_cobblestone = Registry.BLOCK.get(new Identifier(MOD_ID,"mossy_basalt_cobblestone")).getDefaultState();
        BlockState terrestria_basalt_sand = Registry.BLOCK.get(new Identifier(MOD_ID,"basalt_sand")).getDefaultState(); // "black sand"
        BlockState terrestria_basalt_dirt = Registry.BLOCK.get(new Identifier(MOD_ID,"basalt_dirt")).getDefaultState(); // "andisoil"
        BlockState terrestria_basalt_grass_block = Registry.BLOCK.get(new Identifier(MOD_ID,"basalt_grass_block")).getDefaultState(); // on top of basalt dirt
        BlockState terrestria_basalt_podzol = Registry.BLOCK.get(new Identifier(MOD_ID,"basalt_podzol")).getDefaultState(); // on top of basalt dirt

        // SHRUBS - purely decorative
        BlockState terrestria_saguaro_cactus = Registry.BLOCK.get(new Identifier(MOD_ID,"saguaro_cactus")).getDefaultState(); // very dynamic cactus
        BlockState terrestria_cattail = Registry.BLOCK.get(new Identifier(MOD_ID,"cattail")).getDefaultState(); // in water
        BlockState terrestria_indian_paintbrush = Registry.BLOCK.get(new Identifier(MOD_ID,"indian_paintbrush")).getDefaultState(); // on basalt_grass
        BlockState terrestria_monsteras = Registry.BLOCK.get(new Identifier(MOD_ID,"monsteras")).getDefaultState();
        BlockState terrestria_tiny_cactus = Registry.BLOCK.get(new Identifier(MOD_ID,"tiny_cactus")).getDefaultState();
        BlockState terrestria_agave = Registry.BLOCK.get(new Identifier(MOD_ID,"agave")).getDefaultState();
        BlockState terrestria_aloe_vera = Registry.BLOCK.get(new Identifier(MOD_ID,"aloe_vera")).getDefaultState();
        BlockState terrestria_dead_grass = Registry.BLOCK.get(new Identifier(MOD_ID,"dead_grass")).getDefaultState();

        PlantDecorator terrestria_saguaro_cactus_decorator = new PlantDecorator(terrestria_saguaro_cactus, 0.1F); // TODO on top of sand
        PlantDecorator terrestria_cattail_decorator = new PlantDecorator(terrestria_cattail, 0.1F); // TODO single block, in water
        PlantDecorator terrestria_indian_paintbrush_decorator = new PlantDecorator(terrestria_indian_paintbrush, 0.1F);
        PlantDecorator terrestria_monsteras_decorator = new PlantDecorator(terrestria_monsteras, 0.1F);
        PlantDecorator terrestria_tiny_cactus_decorator = new PlantDecorator(terrestria_tiny_cactus, 0.05F);
        PlantDecorator terrestria_agave_decorator = new PlantDecorator(terrestria_agave, 0.05F);
        PlantDecorator terrestria_aloe_vera_decorator = new PlantDecorator(terrestria_aloe_vera, 0.05F);
        PlantDecorator terrestria_dead_grass_decorator = new PlantDecorator(terrestria_dead_grass, 0.1F);

        // WOOD
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 1.0F, new ShellSpheroidType(null, 7, 16, terrestria_redwood_log, terrestria_redwood_leaves, 2, 3));
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 1.0F, new ShellSpheroidType(null, 10, 17, terrestria_hemlock_log, terrestria_hemlock_leaves, 2, 4));
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 1.0F, new ShellSpheroidType(null, 6, 10, terrestria_rubber_log, terrestria_rubber_leaves, 1, 2));
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 1.0F, new ShellSpheroidType(null, 5, 9, terrestria_cypress_log, terrestria_cypress_leaves, 1, 2));
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 1.0F, new ShellSpheroidType(null, 7, 11, terrestria_willow_log, terrestria_willow_leaves, 2, 3));
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 1.0F, new ShellSpheroidType(null, 6, 10, terrestria_japanese_maple_log, terrestria_dark_japanese_maple_leaves, 3, 4));
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 1.0F, new ShellSpheroidType(null, 6, 10, terrestria_japanese_maple_log, terrestria_japanese_maple_leaves, 3, 4));
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 1.0F, new ShellSpheroidType(null, 4, 6, terrestria_japanese_maple_log, terrestria_japanese_maple_shrub_leaves, 1, 2));
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 1.0F, new ShellSpheroidType(null, 7, 14, terrestria_rainbow_eucalyptus_log, terrestria_rainbow_eucalyptus_leaves, 2, 3));
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 1.0F, new ShellSpheroidType(null, 6, 10, Blocks.JUNGLE_WOOD.getDefaultState(), terrestria_jungle_palm_leaves, 2, 2));
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 1.0F, new ShellSpheroidType(null, 7, 12, terrestria_sakura_log, terrestria_sakura_leaves, 2, 3));
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 1.0F, new ShellSpheroidType(null, 5, 8, terrestria_yucca_palm_log, terrestria_yucca_palm_leaves, 1, 2));

        // VOLCANIC
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.3F, new ModularSpheroidType(null, 5, 15, terrestria_basalt_sand).setBottomBlockState(terrestria_basalt_cobblestone));
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.5F, new ModularSpheroidType(null, 5, 20, terrestria_basalt_dirt).setTopBlockState(terrestria_basalt_grass_block)
                .addDecorator(terrestria_indian_paintbrush_decorator, 0.75F)
                .addDecorator(terrestria_monsteras_decorator, 0.75F)
                .addDecorator(FERNS_DECORATOR, 0.75F));
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.3F, new ModularSpheroidType(null, 5, 12, terrestria_basalt_dirt).setTopBlockState(terrestria_basalt_podzol));
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.FLUID, 0.5F, new LiquidSpheroidType(null, 5, 20, Blocks.LAVA.getDefaultState(), terrestria_basalt, 2, 4, 60, 80, 50));

        SpheroidListVanilla.RED_SAND.addDecorator(terrestria_agave_decorator, 0.5F);
        SpheroidListVanilla.RED_SAND.addDecorator(terrestria_dead_grass_decorator, 0.7F);
        SpheroidListVanilla.SAND.addDecorator(terrestria_tiny_cactus_decorator, 0.2F);
        SpheroidListVanilla.GRASS.addDecorator(terrestria_aloe_vera_decorator, 0.1F);
        SpheroidListVanilla.GRASS.addDecorator(terrestria_agave_decorator, 0.1F);

                // Add common blocks to maps
        SpheroidList.MAP_STONES.put(terrestria_basalt, 0.4F);
        SpheroidList.MAP_DUNGEON_STONES.put(terrestria_mossy_basalt_cobblestone, 1.0F);
        SpheroidList.MAP_DUNGEON_STONES.put(terrestria_basalt_cobblestone, 0.5F);
    }

}
