package de.dafuqs.starrysky.spheroid.lists;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SpheroidListBetterNether extends SpheroidList {

    private static final String MOD_ID = "betternether";


    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateBetterNetherSpheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        StarrySkyCommon.LOGGER.info("[StarrySky] Loading Better Nether integration...");

        // ores
        BlockState cincinnasite_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"cincinnasite_ore")).getDefaultState();
        BlockState nether_lapis_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"nether_lapis_ore")).getDefaultState();
        BlockState nether_ruby_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"nether_ruby_ore")).getDefaultState();

        // stalactites
        BlockState netherrack_stalactite = Registry.BLOCK.get(new Identifier(MOD_ID,"netherrack_stalactite")).getDefaultState(); // size: 0, 1, ...
        BlockState glowstone_stalactite = Registry.BLOCK.get(new Identifier(MOD_ID,"glowstone_stalactite")).getDefaultState(); // size: 0, 1, ...

        // JUNGLE:
        BlockState jungle_grass = Registry.BLOCK.get(new Identifier(MOD_ID,"jungle_grass")).getDefaultState();
        BlockState jungle_plant = Registry.BLOCK.get(new Identifier(MOD_ID,"jungle_plant")).getDefaultState();
        BlockState feather_fern = Registry.BLOCK.get(new Identifier(MOD_ID,"feather_fern")).getDefaultState(); // age 1, ...

        BlockState egg_plant = Registry.BLOCK.get(new Identifier(MOD_ID,"egg_plant")).getDefaultState(); // age: 0, 1, 2, 3

        BlockState jellyfish_mushroom = Registry.BLOCK.get(new Identifier(MOD_ID,"jellyfish_mushroom")).getDefaultState(); // shape: bottom (small), or middle + top

        BlockState eye_vine = Registry.BLOCK.get(new Identifier(MOD_ID,"eye_vine")).getDefaultState();
        BlockState eyeball_small = Registry.BLOCK.get(new Identifier(MOD_ID,"eye_vine")).getDefaultState();
        BlockState eyeball = Registry.BLOCK.get(new Identifier(MOD_ID,"eyeball")).getDefaultState();

        BlockState stalagnate = Registry.BLOCK.get(new Identifier(MOD_ID,"stalagnate")).getDefaultState(); // shape: bottom, middle, top

        BlockState golden_vine = Registry.BLOCK.get(new Identifier(MOD_ID,"golden_vine")).getDefaultState(); // bottom: true/false
        BlockState black_vine = Registry.BLOCK.get(new Identifier(MOD_ID,"black_vine")).getDefaultState(); // bottom: true/false
        BlockState blooming_vine = Registry.BLOCK.get(new Identifier(MOD_ID,"blooming_vine")).getDefaultState(); // bottom: true/false

        // WALLS (jungle)
        BlockState lucis_mushroom = Registry.BLOCK.get(new Identifier(MOD_ID,"lucis_mushroom")).getDefaultState(); // several
        BlockState jungle_moss = Registry.BLOCK.get(new Identifier(MOD_ID,"jungle_moss")).getDefaultState(); // facing <direction>
        BlockState wall_mushroom_brown = Registry.BLOCK.get(new Identifier(MOD_ID,"wall_mushroom_brown")).getDefaultState(); // facing <direction>
        BlockState wall_mushroom_red = Registry.BLOCK.get(new Identifier(MOD_ID,"wall_mushroom_red")).getDefaultState(); // facing <direction>

        BlockState rubeus_leaves = Registry.BLOCK.get(new Identifier(MOD_ID,"rubeus_leaves")).getDefaultState();
        BlockState rubeus_bark = Registry.BLOCK.get(new Identifier(MOD_ID,"rubeus_leaves")).getDefaultState();
        BlockState rubeus_log = Registry.BLOCK.get(new Identifier(MOD_ID,"rubeus_log")).getDefaultState(); // shape: middle, bottom
        BlockState rubeus_cone = Registry.BLOCK.get(new Identifier(MOD_ID,"rubeus_cone")).getDefaultState(); // hanging from leaves


        BlockState magma_flower = Registry.BLOCK.get(new Identifier(MOD_ID,"magma_flower")).getDefaultState(); // on magma. Age 1+
        BlockState geyser = Registry.BLOCK.get(new Identifier(MOD_ID,"geyser")).getDefaultState(); // on netherrack, close to magma

        // sprinkled
        BlockState obsidian_glass = Registry.BLOCK.get(new Identifier(MOD_ID,"obsidian_glass")).getDefaultState();
        BlockState blue_obsidian_glass = Registry.BLOCK.get(new Identifier(MOD_ID,"blue_obsidian_glass")).getDefaultState();
        BlockState blue_obsidian = Registry.BLOCK.get(new Identifier(MOD_ID,"blue_obsidian")).getDefaultState();

        BlockState anchor_tree_log = Registry.BLOCK.get(new Identifier(MOD_ID,"anchor_tree_log")).getDefaultState();
        BlockState anchor_tree_leaves = Registry.BLOCK.get(new Identifier(MOD_ID,"anchor_tree_leaves")).getDefaultState();
        BlockState moss_cover = Registry.BLOCK.get(new Identifier(MOD_ID,"moss_cover")).getDefaultState(); // on anchor tree tops

        // MYCELIUM
        BlockState nether_mycelium = Registry.BLOCK.get(new Identifier(MOD_ID,"nether_mycelium")).getDefaultState(); // on netherrack
        BlockState gray_mold = Registry.BLOCK.get(new Identifier(MOD_ID,"gray_mold")).getDefaultState(); // on nether mycelium
        BlockState red_mold = Registry.BLOCK.get(new Identifier(MOD_ID,"red_mold")).getDefaultState(); // on nether mycelium

        BlockState netherrack_moss = Registry.BLOCK.get(new Identifier(MOD_ID,"netherrack_moss")).getDefaultState(); // on netherrack

    }

}
