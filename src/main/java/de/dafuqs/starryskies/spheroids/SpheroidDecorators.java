package de.dafuqs.starryskies.spheroids;

import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.spheroids.decorators.*;
import net.minecraft.block.BambooBlock;
import net.minecraft.block.Blocks;
import net.minecraft.loot.LootTables;
import net.minecraft.util.registry.Registry;

public class SpheroidDecorators {
	
	private static SpheroidDecorator register(String id, SpheroidDecorator spheroidDecorator) {
		return Registry.register(StarryRegistries.SPHEROID_DECORATOR, StarrySkies.locate(id), spheroidDecorator);
	}
	
	// OVERWORLD
	public static SpheroidDecorator CACTUS = register("cactus", new CactusDecorator(Blocks.CACTUS.getDefaultState()));
	public static SpheroidDecorator SEA_GREENS = register("sea_greens", new SeaGreensDecorator());
	public static SpheroidDecorator COCOA = register("cocoa", new CocoaDecorator());
	public static SpheroidDecorator BAMBOO = register("bamboo", new BambooDecorator(Blocks.BAMBOO.getDefaultState().with(BambooBlock.AGE, 0).with(BambooBlock.STAGE, 0), Blocks.BAMBOO_SAPLING.getDefaultState()));
	public static SpheroidDecorator SUGAR_CANE_POND = register("sugar_cane_pond", new SugarCanePondDecorator());
	public static SpheroidDecorator CENTER_POND_UNDERWATER_RUIN_SMALL_CHEST = register("center_pond_underwater_ruin_small_chest", new CenterPondDecorator(Blocks.SAND.getDefaultState(), Blocks.WATER.getDefaultState(), LootTables.UNDERWATER_RUIN_SMALL_CHEST, 0.5F));
	public static SpheroidDecorator CENTER_POND_UNDERWATER_RUIN_BIG_CHEST = register("center_pond_underwater_ruin_big_chest", new CenterPondDecorator(Blocks.GRAVEL.getDefaultState(), Blocks.WATER.getDefaultState(), LootTables.UNDERWATER_RUIN_BIG_CHEST, 0.5F));
	public static SpheroidDecorator CENTER_POND_SHIPWRECK_CHEST = register("center_pond_shipwreck_chest", new CenterPondDecorator(Blocks.SAND.getDefaultState(), Blocks.WATER.getDefaultState(), LootTables.SHIPWRECK_TREASURE_CHEST, 0.5F));
	public static SpheroidDecorator CENTER_POND_LAVA = register("center_pond_lava", new CenterPondDecorator(Blocks.OBSIDIAN.getDefaultState(), Blocks.LAVA.getDefaultState(), LootTables.RUINED_PORTAL_CHEST, 0.25F));
	public static SpheroidDecorator MUSHROOMS = register("mushrooms", new MushroomDecorator());
	public static SpheroidDecorator DEAD_GRASS = register("dead_grass", new PlantDecorator(Blocks.DEAD_BUSH.getDefaultState(), 0.05F));
	public static SpheroidDecorator SWEET_BERRIES = register("sweet_berries", new PlantDecorator(Blocks.SWEET_BERRY_BUSH.getDefaultState(), 0.03F));
	public static SpheroidDecorator FERNS = register("ferns", new PlantDecorator(Blocks.FERN.getDefaultState(), 0.1F));
	public static SpheroidDecorator LARGE_FERNS = register("large_ferns", new DoublePlantDecorator(Blocks.LARGE_FERN.getDefaultState(), 0.1F));
	public static SpheroidDecorator RUINED_PORTAL = register("ruined_portal", new RuinedPortalDecorator(LootTables.RUINED_PORTAL_CHEST));
	public static SpheroidDecorator MUSHROOMS_BROWN = register("mushrooms_brown", new PlantDecorator(Blocks.BROWN_MUSHROOM.getDefaultState(), 0.05F));
	public static SpheroidDecorator MUSHROOMS_RED = register("mushrooms_red", new PlantDecorator(Blocks.RED_MUSHROOM.getDefaultState(), 0.03F));
	
	public static SpheroidDecorator POINTED_DRIPSTONE_UP = register("pointed_dripstone_up", new PointedDripstoneDecorator(0.2F, PointedDripstoneDecorator.PointedDripstoneDecoratorMode.UP));
	public static SpheroidDecorator POINTED_DRIPSTONE_CAVE = register("pointed_dripstone_cave", new PointedDripstoneDecorator(0.2F, PointedDripstoneDecorator.PointedDripstoneDecoratorMode.CAVE));
	public static SpheroidDecorator GLOW_LICHEN = register("glow_lichen", new GlowLichenDecorator(0.1F));
	public static SpheroidDecorator HANGING_ROOTS = register("hanging_roots", new UnderPlantDecorator(Blocks.HANGING_ROOTS.getDefaultState(), 0.2F));
	
	public static SpheroidDecorator AZALEA = register("azalea", new PlantDecorator(Blocks.AZALEA.getDefaultState(), 0.03F));
	public static SpheroidDecorator FLOWERING_AZALEA = register("flowering_azalea", new PlantDecorator(Blocks.FLOWERING_AZALEA.getDefaultState(), 0.03F));
	public static SpheroidDecorator SPORE_BLOSSOM = register("spore_blossom", new HangingCaveBlockDecorator(Blocks.SPORE_BLOSSOM.getDefaultState(), 0.05F));
	public static SpheroidDecorator DRIPLEAF = register("dripleaf", new DripleafDecorator(8));
	
	public static SpheroidDecorator X_SPOT_DESERT_PYRAMID = register("x_spot_desert_pyramid", new XMarksTheSpotDecorator(LootTables.DESERT_PYRAMID_CHEST, Blocks.ORANGE_TERRACOTTA.getDefaultState()));
	public static SpheroidDecorator X_SPOT_JUNGLE_TEMPLE = register("x_spot_jungle_temple", new XMarksTheSpotDecorator(LootTables.JUNGLE_TEMPLE_CHEST, Blocks.MOSSY_COBBLESTONE.getDefaultState()));
	public static SpheroidDecorator X_SPOT_WOODLAND_MANSION = register("x_spot_woodland_mansion", new XMarksTheSpotDecorator(LootTables.WOODLAND_MANSION_CHEST, Blocks.STRIPPED_BIRCH_WOOD.getDefaultState()));
	public static SpheroidDecorator X_SPOT_BURIED_TREASURE = register("x_spot_buried_treasure", new XMarksTheSpotDecorator(LootTables.BURIED_TREASURE_CHEST, Blocks.PRISMARINE.getDefaultState()));
	public static SpheroidDecorator X_SPOT_PILLAGER_OUTPOST = register("x_spot_pillager_outpost", new XMarksTheSpotDecorator(LootTables.PILLAGER_OUTPOST_CHEST, Blocks.STRIPPED_DARK_OAK_WOOD.getDefaultState()));
	public static SpheroidDecorator X_SPOT_RUINED_PORTAL = register("x_spot_ruined_portal", new XMarksTheSpotDecorator(LootTables.RUINED_PORTAL_CHEST, Blocks.OBSIDIAN.getDefaultState()));
	
	// NETHER
	public static SpheroidDecorator NETHER_WART = register("nether_wart", new PlantDecorator(Blocks.NETHER_WART.getDefaultState(), 0.02F));
	public static SpheroidDecorator FIRE = register("fire", new PlantDecorator(Blocks.FIRE.getDefaultState(), 0.1F));
	public static SpheroidDecorator SOUL_FIRE = register("soul_fire", new PlantDecorator(Blocks.SOUL_FIRE.getDefaultState(), 0.1F));
	public static SpheroidDecorator CRIMSON_ROOTS = register("crimson_roots", new PlantDecorator(Blocks.CRIMSON_ROOTS.getDefaultState(), 0.1F));
	public static SpheroidDecorator WARPED_ROOTS = register("warped_roots", new PlantDecorator(Blocks.WARPED_ROOTS.getDefaultState(), 0.1F));
	public static SpheroidDecorator NETHER_SPROUTS = register("nether_sprouts", new PlantDecorator(Blocks.NETHER_SPROUTS.getDefaultState(), 0.1F));
	public static SpheroidDecorator CRIMSON_FUNGUS = register("crimson_fungus", new PlantDecorator(Blocks.CRIMSON_FUNGUS.getDefaultState(), 0.05F));
	public static SpheroidDecorator WARPED_FUNGUS = register("warped_fungus", new PlantDecorator(Blocks.WARPED_FUNGUS.getDefaultState(), 0.05F));
	public static SpheroidDecorator TWISTING_VINES = register("twisting_vines", new HugePlantDecorator(Blocks.TWISTING_VINES_PLANT.getDefaultState(), 0.05F, 1, 6).setLastBlockState(Blocks.TWISTING_VINES.getDefaultState())); // warped, grow upward
	public static SpheroidDecorator WEEPING_VINES = register("weeping_vines", new HugeUnderPlantDecorator(Blocks.WEEPING_VINES_PLANT.getDefaultState(), 0.1F, 1, 6).setLastBlockState(Blocks.WEEPING_VINES.getDefaultState())); // crimson, growing downwards
	
	// END
	public static SpheroidDecorator END_PORTAL = register("end_portal", new EndPortalDecorator());
	public static SpheroidDecorator END_GATEWAY = register("end_gateway", new EndGatewayDecorator());
	public static SpheroidDecorator CHORUS_FRUIT = register("chorus_fruit", new ChorusFruitDecorator());
	
	public static void initialize() {
	
	}
	
}
