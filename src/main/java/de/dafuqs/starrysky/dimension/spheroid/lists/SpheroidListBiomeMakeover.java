package de.dafuqs.starrysky.dimension.spheroid.lists;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.dimension.SpheroidDistributionType;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import de.dafuqs.starrysky.dimension.decorators.*;
import de.dafuqs.starrysky.dimension.spheroid.types.ModularSpheroidType;
import de.dafuqs.starrysky.dimension.spheroid.types.MushroomSpheroidType;
import de.dafuqs.starrysky.dimension.spheroid.types.ShellSpheroidType;
import de.dafuqs.starrysky.dimension.spheroid.types.SpheroidType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.loot.LootTables;

import static de.dafuqs.starrysky.dimension.SpheroidLoader.SpheroidDimensionType.OVERWORLD;
import static org.apache.logging.log4j.Level.INFO;

public class SpheroidListBiomeMakeover extends SpheroidList {

    private static final String MOD_ID = "biomemakeover";


    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateBiomeMakeoverSpheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        StarrySkyCommon.log(INFO, "Loading Biome Makeover integration...");

        // WOOD
        BlockState blighted_balsa_leaves = getDefaultBlockState(MOD_ID,"blighted_balsa_leaves");
        BlockState blighted_balsa_log = getDefaultBlockState(MOD_ID,"blighted_balsa_log");
        BlockState willow_leaves = getDefaultBlockState(MOD_ID,"willow_leaves");
        BlockState willow_log = getDefaultBlockState(MOD_ID,"willow_log");
        BlockState swamp_cypress_leaves = getDefaultBlockState(MOD_ID,"swamp_cypress_leaves");
        BlockState swamp_cypress_log = getDefaultBlockState(MOD_ID,"swamp_cypress_log");

        BlockState willowing_branches = getDefaultBlockState(MOD_ID,"willowing_branches");
        BottomBlockStackDecorator willowing_branches_decorator = new BottomBlockStackDecorator(willowing_branches, 0.25F, 1, 4);

        SpheroidType blighted_balsa_wood = new ShellSpheroidType(null, 6, 9, blighted_balsa_log, blighted_balsa_leaves, 1, 3);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 0.5F, blighted_balsa_wood);

        SpheroidType willow_wood = new ShellSpheroidType(null, 7, 10, willow_log, willow_leaves, 2, 4)
                .addDecorator(willowing_branches_decorator, 0.9F);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 0.8F, willow_wood);

        SpheroidType swamp_cypress_wood = new ShellSpheroidType(null, 4, 7, swamp_cypress_log, swamp_cypress_leaves, 1, 2);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.WOOD, 0.7F, swamp_cypress_wood); // should have vines

        // MUSHROOM FIELDS
        BlockState mycelium_sprouts = getDefaultBlockState(MOD_ID,"mycelium_sprouts");
        BlockState mycelium_roots = getDefaultBlockState(MOD_ID,"mycelium_roots");
        BlockState tall_brown_mushroom = getDefaultBlockState(MOD_ID,"tall_brown_mushroom");
        BlockState tall_red_mushroom = getDefaultBlockState(MOD_ID,"tall_red_mushroom");

        SingleBlockDecorator mycelium_sprouts_decorator = new SingleBlockDecorator(mycelium_sprouts, 0.03F);
        SingleBlockDecorator mycelium_roots_decorator = new SingleBlockDecorator(mycelium_roots, 0.03F);
        TallFlowerBlockDecorator tall_brown_mushroom_decorator = new TallFlowerBlockDecorator(tall_brown_mushroom, 0.03F);
        TallFlowerBlockDecorator tall_red_mushroom_decorator = new TallFlowerBlockDecorator(tall_red_mushroom, 0.03F);

        SpheroidListVanilla.MYCELIUM.addDecorator(mycelium_sprouts_decorator, 0.8F);
        SpheroidListVanilla.MYCELIUM.addDecorator(mycelium_roots_decorator, 0.8F);
        SpheroidListVanilla.MYCELIUM.addDecorator(tall_brown_mushroom_decorator, 0.8F);
        SpheroidListVanilla.MYCELIUM.addDecorator(tall_red_mushroom_decorator, 0.8F);

        // SWAMP TODO
        // BlockState cattail = getDefaultBlockState(MOD_ID,"cattail"); // double plant. Bottom is waterlogged
        // BlockState reeds = getDefaultBlockState(MOD_ID,"reeds"); // double plant. Bottom is waterlogged
        // BlockState small_lily_pad = getDefaultBlockState(MOD_ID,"small_lily_pad"); // like lily pad
        // BlockState water_lily = getDefaultBlockState(MOD_ID,"water_lily"); // like lily pad
        BlockState peat = getDefaultBlockState(MOD_ID,"peat");
        BlockState mossy_peat = getDefaultBlockState(MOD_ID,"mossy_peat");

        SpheroidDecorator peat_pond_decorator = new CenterPondDecorator(mossy_peat, Blocks.WATER.getDefaultState(), LootTables.UNDERWATER_RUIN_SMALL_CHEST, 0.25F);
        SpheroidType peat_sphere = new ModularSpheroidType(null, 6, 12, peat)
                .setTopBlockState(mossy_peat)
                .addDecorator(peat_pond_decorator, 0.5F);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.2F, peat_sphere);


        // DESERT
        BlockState barrel_cactus = getDefaultBlockState(MOD_ID,"barrel_cactus");
        BlockState barrel_cactus_flowered = getDefaultBlockState(MOD_ID,"barrel_cactus_flowered");
        BlockState saguaro_cactus = getDefaultBlockState(MOD_ID,"saguaro_cactus");

        BlockStackDecorator saguaro_cactus_decorator = new BlockStackDecorator(saguaro_cactus, 0.05F, 1, 3).requiresAirAroundOfIt();
        SingleBlockDecorator barrel_cactus_decorator = new SingleBlockDecorator(barrel_cactus, 0.03F);
        SingleBlockDecorator barrel_cactus_flowered_decorator = new SingleBlockDecorator(barrel_cactus_flowered, 0.02F);

        SpheroidListVanilla.SAND.addDecorator(saguaro_cactus_decorator, 0.3F);
        SpheroidListVanilla.SAND.addDecorator(barrel_cactus_decorator, 0.2F);
        SpheroidListVanilla.SAND.addDecorator(barrel_cactus_flowered_decorator, 0.2F);
        SpheroidListVanilla.RED_SAND.addDecorator(saguaro_cactus_decorator, 0.1F);
        SpheroidListVanilla.RED_SAND.addDecorator(barrel_cactus_decorator, 0.1F);
        SpheroidListVanilla.RED_SAND.addDecorator(barrel_cactus_flowered_decorator, 0.1F);

        // GLOWSHROOMS (underground of mushroom fields)
        BlockState green_glowshroom_block = getDefaultBlockState(MOD_ID,"green_glowshroom_block");
        BlockState purple_glowshroom_block = getDefaultBlockState(MOD_ID,"purple_glowshroom_block");
        BlockState orange_glowshroom_block = getDefaultBlockState(MOD_ID,"orange_glowshroom_block");
        BlockState glowshroom_stem = getDefaultBlockState(MOD_ID,"glowshroom_stem");

        SpheroidType green_glowshroom = new MushroomSpheroidType(null, 4, 7, glowshroom_stem, green_glowshroom_block, 2, 3);
        SpheroidType purple_glowshroom = new MushroomSpheroidType(null, 4, 7, glowshroom_stem, purple_glowshroom_block, 2, 3);
        SpheroidType orange_glowshroom = new MushroomSpheroidType(null, 4, 7, glowshroom_stem, orange_glowshroom_block, 2, 3);

        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.TREASURE, 0.2F, green_glowshroom);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.TREASURE, 0.2F, purple_glowshroom);
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.TREASURE, 0.2F, orange_glowshroom);

        // PLANTS
        BlockState swamp_azalea = getDefaultBlockState(MOD_ID,"swamp_azalea");
        BlockState marigold = getDefaultBlockState(MOD_ID,"marigold");
        LIST_TALL_FLOWERS.add(swamp_azalea);
        LIST_TALL_FLOWERS.add(marigold);

    }

}
