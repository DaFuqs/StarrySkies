package de.dafuqs.starrysky.spheroid.lists;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.dimension.SpheroidDistributionType;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import de.dafuqs.starrysky.dimension.decorators.DoublePlantDecorator;
import de.dafuqs.starrysky.dimension.decorators.PlantDecorator;
import de.dafuqs.starrysky.dimension.decorators.UnderPlantDecorator;
import de.dafuqs.starrysky.spheroid.types.*;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

public class SpheroidListCinderscapes extends SpheroidList {

    private static final String MOD_ID = "cinderscapes";

    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateCinderscapesSpheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        StarrySkyCommon.LOGGER.info("[StarrySky] Loading Cinderscapes integration...");

        // crystalline quartz blocks; as single and rainbow
        BlockState crystalline_quartz = Registry.BLOCK.get(new Identifier(MOD_ID,"crystalline_quartz")).getDefaultState();
        BlockState crystalline_sulfur_quartz = Registry.BLOCK.get(new Identifier(MOD_ID,"crystalline_sulfur_quartz")).getDefaultState();
        BlockState crystalline_rose_quartz = Registry.BLOCK.get(new Identifier(MOD_ID,"crystalline_rose_quartz")).getDefaultState();
        BlockState crystalline_smoky_quartz = Registry.BLOCK.get(new Identifier(MOD_ID,"crystalline_smoky_quartz")).getDefaultState();

        // quartz shards
        /*BlockState polypite_nether_quartz = Registry.BLOCK.get(new Identifier(MOD_ID,"polypite_nether_quartz")).getDefaultState();
        BlockState polypite_rose_quartz = Registry.BLOCK.get(new Identifier(MOD_ID,"polypite_rose_quartz")).getDefaultState();
        BlockState polypite_smoky_quartz = Registry.BLOCK.get(new Identifier(MOD_ID,"polypite_smoky_quartz")).getDefaultState();
        BlockState polypite_sulfur_quartz = Registry.BLOCK.get(new Identifier(MOD_ID,"polypite_sulfur_quartz")).getDefaultState();*/

        // quartz ore
        BlockState sulfur_quartz_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"sulfur_quartz_ore")).getDefaultState();
        BlockState rose_quartz_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"rose_quartz_ore")).getDefaultState();
        BlockState smoky_quartz_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"smoky_quartz_ore")).getDefaultState();

        // scorched wood
        BlockState scorched_stem = Registry.BLOCK.get(new Identifier(MOD_ID,"scorched_stem")).getDefaultState();
        BlockState scorched_hyphae = Registry.BLOCK.get(new Identifier(MOD_ID,"scorched_hyphae")).getDefaultState();

        // Luminous grove
        // from vanilla: nether sprouts, warped fungus, crimson fungus, crimson roots, warped roots,
        BlockState umbral_nylium = Registry.BLOCK.get(new Identifier(MOD_ID,"umbral_nylium")).getDefaultState(); // overgrown netherrack
        BlockState twilight_fescues = Registry.BLOCK.get(new Identifier(MOD_ID,"twilight_fescues")).getDefaultState();
        BlockState tall_fotofern = Registry.BLOCK.get(new Identifier(MOD_ID,"tall_fotofern")).getDefaultState(); // upper, lower
        BlockState fotofern = Registry.BLOCK.get(new Identifier(MOD_ID,"fotofern")).getDefaultState();
        BlockState twilight_tendrils = Registry.BLOCK.get(new Identifier(MOD_ID,"twilight_tendrils")).getDefaultState();
        BlockState luminous_pod = Registry.BLOCK.get(new Identifier(MOD_ID,"luminous_pod")).getDefaultState(); // upper, lower

        // Twilight vines
        BlockState twilight_vine_block = Registry.BLOCK.get(new Identifier(MOD_ID,"twilight_vine_block")).getDefaultState();
        BlockState ghastly_ectoplasm = Registry.BLOCK.get(new Identifier(MOD_ID,"ghastly_ectoplasm")).getDefaultState(); // top, middle, bottom

        // Umbral mushroom with shroomlight speckles
        BlockState umbral_stem = Registry.BLOCK.get(new Identifier(MOD_ID,"umbral_stem")).getDefaultState(); // "log";
        BlockState umbral_flesh_block = Registry.BLOCK.get(new Identifier(MOD_ID,"umbral_flesh_block")).getDefaultState(); // "branches"
        BlockState umbral_wart_block = Registry.BLOCK.get(new Identifier(MOD_ID,"umbral_wart_block")).getDefaultState(); // "leaves"
        // BlockState umbral_hyphae = Registry.BLOCK.get(new Identifier(MOD_ID,"umbral_hyphae")).getDefaultState() // where does this generate?

        // ash
        BlockState ash_block = Registry.BLOCK.get(new Identifier(MOD_ID,"ash_block")).getDefaultState();
        BlockState scorched_shrub = Registry.BLOCK.get(new Identifier(MOD_ID,"scorched_shrub")).getDefaultState();
        BlockState scorched_tendrils = Registry.BLOCK.get(new Identifier(MOD_ID,"scorched_tendrils")).getDefaultState();
        BlockState scorched_sprouts = Registry.BLOCK.get(new Identifier(MOD_ID,"scorched_sprouts")).getDefaultState();
        BlockState pyracinth = Registry.BLOCK.get(new Identifier(MOD_ID,"pyracinth")).getDefaultState();
        BlockState bramble_berry_bush = Registry.BLOCK.get(new Identifier(MOD_ID,"bramble_berry_bush")).getDefaultState();

        // Where does it generate?
        BlockState crystinium = Registry.BLOCK.get(new Identifier(MOD_ID,"crystinium")).getDefaultState();


        SpheroidType CRYSTALLINE_QUARTZ = new ModularSpheroidType(null, 5, 10,  crystalline_quartz);
        SpheroidType SULFUR_QUARTZ = new ModularSpheroidType(null, 5, 10,  crystalline_sulfur_quartz);
        SpheroidType ROSE_QUARTZ = new ModularSpheroidType(null, 5, 10,  crystalline_rose_quartz);
        SpheroidType SMOKY_QUARTZ = new ModularSpheroidType(null, 5, 10,  crystalline_smoky_quartz);

        ArrayList QUARTZS = new ArrayList<BlockState>() {{
            add(crystalline_quartz);
            add(crystalline_sulfur_quartz);
            add(crystalline_rose_quartz);
            add(crystalline_smoky_quartz);
        }};
        SpheroidType RAINBOW_QUARTZ = new RainbowSpheroidType(null, 5, 10, QUARTZS);

        PlantDecorator CRYSTINIUM_DECORATOR = new PlantDecorator(crystinium, 0.03F);
        ArrayList QUARTZ_ORES = new ArrayList<BlockState>() {{
            add(Blocks.NETHER_QUARTZ_ORE.getDefaultState());
            add(sulfur_quartz_ore);
            add(rose_quartz_ore);
            add(smoky_quartz_ore);
        }};
        SpheroidListVanillaNether.NETHER_QUARTZ.addDecorator(CRYSTINIUM_DECORATOR, 0.25F);
        SpheroidType SULFUR_QUARTZ_ORE = new CoreSpheroidType(null, 5, 15, Blocks.NETHER_QUARTZ_ORE.getDefaultState(), MAP_NETHER_STONES, 4, 8)
                .addDecorator(CRYSTINIUM_DECORATOR, 0.25F);
        SpheroidType ROSE_QUARTZ_ORE = new CoreSpheroidType(null, 5, 15, Blocks.NETHER_QUARTZ_ORE.getDefaultState(), MAP_NETHER_STONES, 4, 8)
                .addDecorator(CRYSTINIUM_DECORATOR, 0.25F);
        SpheroidType SMOKY_QUARTZ_ORE = new CoreSpheroidType(null, 5, 15, Blocks.NETHER_QUARTZ_ORE.getDefaultState(), MAP_NETHER_STONES, 4, 8)
                .addDecorator(CRYSTINIUM_DECORATOR, 0.25F);
        SpheroidType RAINBOW_QUARTZ_ORE = new RainbowSpheroidType(null, 5, 15, QUARTZ_ORES)
                .addDecorator(CRYSTINIUM_DECORATOR, 0.25F);

        SpheroidType SCORCHED_WOOD      = new ShellSpheroidType(null, 6, 12, scorched_stem, scorched_hyphae, 2, 3);

        PlantDecorator TWILIGHT_FESCUES_DECORATOR = new PlantDecorator(twilight_fescues, 0.1F);
        PlantDecorator FOTOFERN_DECORATOR = new PlantDecorator(fotofern, 0.1F);
        PlantDecorator TWILIGHT_TENDRILS_DECORATOR = new PlantDecorator(twilight_tendrils, 0.1F);
        DoublePlantDecorator TALL_FOTOFERN_DECORATOR = new DoublePlantDecorator(tall_fotofern, 0.1F);
        DoublePlantDecorator LUMINOUS_POD_DECORATOR = new DoublePlantDecorator(luminous_pod, 0.1F);
        SpheroidType UMBRAL = new ModularSpheroidType(null, 10, 20,  Blocks.NETHERRACK.getDefaultState())
                .setTopBlockState(umbral_nylium)
                .addDecorator(TWILIGHT_FESCUES_DECORATOR, 0.8F)
                .addDecorator(FOTOFERN_DECORATOR, 0.8F)
                .addDecorator(TWILIGHT_TENDRILS_DECORATOR, 0.8F)
                .addDecorator(TALL_FOTOFERN_DECORATOR, 0.8F)
                .addDecorator(LUMINOUS_POD_DECORATOR, 0.8F);

        UnderPlantDecorator GHASTLY_ECTOPLASM_DECORATOR = new UnderPlantDecorator(ghastly_ectoplasm, 0.1F);
        SpheroidType TWILIGHT_VINES = new ModularSpheroidType(null, 5, 8,  twilight_vine_block)
                .addDecorator(GHASTLY_ECTOPLASM_DECORATOR, 0.9F);

        SpheroidType UMBRAL_MUSHROOM = new DoubleCoreSpheroidType(null, 5, 12, umbral_stem, umbral_flesh_block, umbral_wart_block, 2, 4, 2, 4);

        PlantDecorator SCORCHED_SHRUB_DECORATOR = new PlantDecorator(scorched_shrub, 0.08F);
        PlantDecorator SCORCHED_TENDRILS_DECORATOR = new PlantDecorator(scorched_tendrils, 0.12F);
        PlantDecorator SCORCHED_SPROUTS_DECORATOR = new PlantDecorator(scorched_sprouts, 0.15F);
        PlantDecorator PYRACINTH_DECORATOR = new PlantDecorator(pyracinth, 0.03F);
        PlantDecorator BRAMBLE_BERRY_BUSH_DECORATOR = new PlantDecorator(bramble_berry_bush, 0.25F);
        SpheroidType ASH = new ModularSpheroidType(null, 10, 20,  ash_block)
                .addDecorator(SCORCHED_SHRUB_DECORATOR, 0.8F)
                .addDecorator(SCORCHED_TENDRILS_DECORATOR, 0.8F)
                .addDecorator(SCORCHED_SPROUTS_DECORATOR, 0.8F)
                .addDecorator(PYRACINTH_DECORATOR, 0.8F)
                .addDecorator(BRAMBLE_BERRY_BUSH_DECORATOR, 0.8F);


        // REGISTERING SPHEROID TYPES
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.DECORATIVE, 0.5F, CRYSTALLINE_QUARTZ);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.DECORATIVE, 0.5F, SULFUR_QUARTZ);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.DECORATIVE, 0.5F, ROSE_QUARTZ);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.DECORATIVE, 0.5F, SMOKY_QUARTZ);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.DECORATIVE, 0.2F, RAINBOW_QUARTZ);

        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.ORE, 0.2F, SULFUR_QUARTZ_ORE);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.ORE, 0.2F, ROSE_QUARTZ_ORE);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.ORE, 0.2F, SMOKY_QUARTZ_ORE);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.ORE, 0.1F, RAINBOW_QUARTZ_ORE);

        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.WOOD, 0.1F, SCORCHED_WOOD);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.WOOD, 0.1F, UMBRAL_MUSHROOM);

        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.DECORATIVE, 0.5F, TWILIGHT_VINES);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.DECORATIVE, 0.5F, UMBRAL);
        spheroidLoader.registerSpheroidType(SpheroidLoader.SpheroidDimensionType.NETHER, SpheroidDistributionType.DECORATIVE, 0.5F, ASH);
    }

}
