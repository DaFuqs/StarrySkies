package de.dafuqs.starrysky.spheroidlists;

import com.terraformersmc.terraform.leaves.block.ExtendedLeavesBlock;
import com.terraformersmc.terraform.wood.block.SmallLogBlock;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.spheroidtypes.ModularSpheroidType;
import de.dafuqs.starrysky.spheroidtypes.ShellSpheroidType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SpheroidListTerrestria extends SpheroidList {

    private static final String MOD_ID = "terrestria";

    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateTerrestriaSpheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
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

        // SHRUBS - purely decorative TODO
        BlockState terrestria_cattail = Registry.BLOCK.get(new Identifier(MOD_ID,"cattail")).getDefaultState(); // in water
        BlockState terrestria_indian_paintbrush = Registry.BLOCK.get(new Identifier(MOD_ID,"indian_paintbrush")).getDefaultState(); // on basalt_grass
        BlockState terrestria_monsteras = Registry.BLOCK.get(new Identifier(MOD_ID,"monsteras")).getDefaultState();
        BlockState terrestria_tiny_cactus = Registry.BLOCK.get(new Identifier(MOD_ID,"tiny_cactus")).getDefaultState();
        BlockState terrestria_agave = Registry.BLOCK.get(new Identifier(MOD_ID,"agave")).getDefaultState();
        BlockState terrestria_aloe_vera = Registry.BLOCK.get(new Identifier(MOD_ID,"aloe_vera")).getDefaultState();

        // WOOD
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.WOOD, 1.0F, new ShellSpheroidType(null, terrestria_redwood_log, terrestria_redwood_leaves,7, 16, 2, 3));
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.WOOD, 1.0F, new ShellSpheroidType(null, terrestria_hemlock_log, terrestria_hemlock_leaves,10, 17, 2, 4));
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.WOOD, 1.0F, new ShellSpheroidType(null, terrestria_rubber_log, terrestria_rubber_leaves,6, 10, 1, 2));
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.WOOD, 1.0F, new ShellSpheroidType(null, terrestria_cypress_log, terrestria_cypress_leaves,5, 9, 1, 2));
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.WOOD, 1.0F, new ShellSpheroidType(null, terrestria_willow_log, terrestria_willow_leaves,7, 11, 2, 3));
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.WOOD, 1.0F, new ShellSpheroidType(null, terrestria_japanese_maple_log, terrestria_dark_japanese_maple_leaves,6, 10, 3, 4));
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.WOOD, 1.0F, new ShellSpheroidType(null, terrestria_japanese_maple_log, terrestria_japanese_maple_leaves,6, 10, 3, 4));
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.WOOD, 1.0F, new ShellSpheroidType(null, terrestria_japanese_maple_log, terrestria_japanese_maple_shrub_leaves,4, 6, 1, 2));
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.WOOD, 1.0F, new ShellSpheroidType(null, terrestria_rainbow_eucalyptus_log, terrestria_rainbow_eucalyptus_leaves,7, 14, 2, 3));
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.WOOD, 1.0F, new ShellSpheroidType(null, Blocks.JUNGLE_WOOD.getDefaultState(), terrestria_jungle_palm_leaves,6, 10, 2, 2));
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.WOOD, 1.0F, new ShellSpheroidType(null, terrestria_sakura_log, terrestria_sakura_leaves,7, 12, 2, 3));
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.WOOD, 1.0F, new ShellSpheroidType(null, terrestria_yucca_palm_log, terrestria_yucca_palm_leaves,5, 8, 1, 2));

        // VOLCANIC
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 1.0F, new ModularSpheroidType(null, terrestria_basalt_sand, 5, 15).setBottomBlockState(terrestria_basalt_cobblestone));
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 1.0F, new ModularSpheroidType(null, terrestria_basalt_dirt, 5, 20).setTopBlockState(terrestria_basalt_grass_block));
        spheroidLoader.registerSpheroidType(SpheroidDistributionType.DECORATIVE, 0.5F, new ModularSpheroidType(null, terrestria_basalt_dirt, 5, 12).setTopBlockState(terrestria_basalt_podzol));

        // Add common blocks to maps
        SpheroidList.MAP_STONES.put(terrestria_basalt, 0.4F);
        SpheroidList.MAP_DUNGEON_STONES.put(terrestria_mossy_basalt_cobblestone, 1.0F);
        SpheroidList.MAP_DUNGEON_STONES.put(terrestria_basalt_cobblestone, 0.5F);
    }

}
