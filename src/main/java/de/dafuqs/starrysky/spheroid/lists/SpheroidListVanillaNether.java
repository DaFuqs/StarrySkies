package de.dafuqs.starrysky.spheroid.lists;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.dimension.SpheroidDistributionType;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import de.dafuqs.starrysky.dimension.decorators.HugePlantDecorator;
import de.dafuqs.starrysky.dimension.decorators.HugeUnderPlantDecorator;
import de.dafuqs.starrysky.dimension.decorators.PlantDecorator;
import de.dafuqs.starrysky.spheroid.SpheroidEntitySpawnDefinitions;
import de.dafuqs.starrysky.spheroid.types.*;
import de.dafuqs.starrysky.spheroid.types.unique.NetherFortressSpheroidType;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootTables;

import static de.dafuqs.starrysky.dimension.SpheroidLoader.SpheroidDimensionType.NETHER;
import static org.apache.logging.log4j.Level.INFO;

public class SpheroidListVanillaNether extends SpheroidList {
	
	public static class SpheroidDecorators {
        public static SpheroidDecorator NETHER_WART = new PlantDecorator(Blocks.NETHER_WART.getDefaultState(), 0.02F);
        public static SpheroidDecorator FIRE = new PlantDecorator(Blocks.FIRE.getDefaultState(), 0.1F);
        public static SpheroidDecorator SOUL_FIRE = new PlantDecorator(Blocks.SOUL_FIRE.getDefaultState(), 0.1F);
        public static SpheroidDecorator CRIMSON_ROOTS = new PlantDecorator(Blocks.CRIMSON_ROOTS.getDefaultState(), 0.1F);
        public static SpheroidDecorator WARPED_ROOTS = new PlantDecorator(Blocks.WARPED_ROOTS.getDefaultState(), 0.1F);
        public static SpheroidDecorator NETHER_SPROUTS = new PlantDecorator(Blocks.NETHER_SPROUTS.getDefaultState(), 0.1F);
        public static SpheroidDecorator CRIMSON_FUNGUS = new PlantDecorator(Blocks.CRIMSON_FUNGUS.getDefaultState(), 0.05F);
        public static SpheroidDecorator WARPED_FUNGUS = new PlantDecorator(Blocks.WARPED_FUNGUS.getDefaultState(), 0.05F);
        public static SpheroidDecorator TWISTING_VINES = new HugePlantDecorator(Blocks.TWISTING_VINES_PLANT.getDefaultState(), 0.05F, 1, 6).setLastBlockState(Blocks.TWISTING_VINES.getDefaultState()); // warped, grow upward
        public static SpheroidDecorator WEEPING_VINES = new HugeUnderPlantDecorator(Blocks.WEEPING_VINES_PLANT.getDefaultState(), 0.1F, 1, 6).setLastBlockState(Blocks.WEEPING_VINES.getDefaultState()); // crimson, growing downwards
	}
	
    // SPHEROID TYPES
    // BASIC
    public static final SpheroidType NETHERRACK = new ModularSpheroidType(SpheroidAdvancementIdentifier.netherrack, 7, 16,  Blocks.NETHERRACK.getDefaultState())
			.addDecorator(SpheroidListVanilla.SpheroidDecorators.RUINED_PORTAL_DECORATOR, 0.03F)
			.addDecorator(SpheroidDecorators.FIRE, 0.3F)
			.addDecorator(SpheroidListVanilla.SpheroidDecorators.MUSHROOMS, 0.3F)
			.addDecorator(SpheroidListVanilla.SpheroidDecorators.CENTER_POND_LAVA, 0.03F)
			.addSpawn(SpheroidEntitySpawnDefinitions.SKELETON_HORSE, 0.01F)
			.addSpawn(SpheroidEntitySpawnDefinitions.ZOMBIE_HORSE, 0.01F);
    public static final SpheroidType MAGMA_SPRINKLED_NETHERRACK = new ShellSpheroidType(SpheroidAdvancementIdentifier.netherrack, 7, 12,  Blocks.NETHERRACK.getDefaultState(), Blocks.MAGMA_BLOCK.getDefaultState(), 2, 4)
			.addShellSpeckles(Blocks.MAGMA_BLOCK.getDefaultState(), 0.15F)
			.addDecorator(SpheroidListVanilla.SpheroidDecorators.CENTER_POND_LAVA, 0.1F);
    public static final SpheroidType SOUL_SAND = new ModularSpheroidType(SpheroidAdvancementIdentifier.soul_sand, 6, 12,  Blocks.SOUL_SAND.getDefaultState())
			.addDecorator(SpheroidDecorators.SOUL_FIRE, 0.25F)
			.addDecorator(SpheroidDecorators.NETHER_WART, 0.1F)
			.addDecorator(SpheroidListVanilla.SpheroidDecorators.CENTER_POND_LAVA, 0.05F);
    public static final SpheroidType SOUL_SOIL = new ModularSpheroidType(SpheroidAdvancementIdentifier.soul_soil, 6, 10,  Blocks.SOUL_SOIL.getDefaultState())
			.addDecorator(SpheroidDecorators.SOUL_FIRE, 0.25F)
			.addDecorator(SpheroidDecorators.NETHER_WART, 0.1F);
    public static final SpheroidType MAGMA_BLOCK = new ModularSpheroidType(SpheroidAdvancementIdentifier.magma, 5, 8,  Blocks.MAGMA_BLOCK.getDefaultState())
			.addDecorator(SpheroidDecorators.FIRE, 0.5F)
			.addDecorator(SpheroidListVanilla.SpheroidDecorators.CENTER_POND_LAVA, 0.25F);
	public static final SpheroidType GRAVEL = new ModularSpheroidType(SpheroidAdvancementIdentifier.gravel, 5, 7,  Blocks.GRAVEL.getDefaultState())
            .setBottomBlockState(Blocks.COBBLESTONE.getDefaultState());
	public static final SpheroidType CRIMSON_NYLIUM = new ModularSpheroidType(SpheroidAdvancementIdentifier.crimson_nylium, 9, 15,  Blocks.NETHERRACK.getDefaultState())
			.setTopBlockState(Blocks.CRIMSON_NYLIUM.getDefaultState())
			.addDecorator(SpheroidDecorators.CRIMSON_ROOTS, 0.8F)
			.addDecorator(SpheroidDecorators.WEEPING_VINES, 0.8F)
			.addDecorator(SpheroidDecorators.CRIMSON_FUNGUS, 0.8F)
			.addSpawn(SpheroidEntitySpawnDefinitions.HOGLIN, 0.4F);
	public static final SpheroidType WARPED_NYLIUM = new ModularSpheroidType(SpheroidAdvancementIdentifier.warped_nylium, 9, 15,  Blocks.NETHERRACK.getDefaultState()).setTopBlockState(Blocks.WARPED_NYLIUM.getDefaultState())
			.addDecorator(SpheroidDecorators.WARPED_ROOTS, 0.8F)
			.addDecorator(SpheroidDecorators.TWISTING_VINES, 0.8F)
			.addDecorator(SpheroidDecorators.NETHER_SPROUTS, 0.8F)
			.addDecorator(SpheroidDecorators.WARPED_FUNGUS, 0.3F);
	public static final SpheroidType BASALT = new ModularSpheroidType(SpheroidAdvancementIdentifier.basalt, 10, 25,  Blocks.BASALT.getDefaultState())
			.addDecorator(SpheroidListVanilla.SpheroidDecorators.CENTER_POND_LAVA, 0.1F);
	public static final SpheroidType BLACKSTONE = new ShellSpheroidType(SpheroidAdvancementIdentifier.blackstone, 5, 10,  Blocks.GILDED_BLACKSTONE.getDefaultState(), Blocks.BLACKSTONE.getDefaultState(), 10, 10)
			.addShellSpeckles(Blocks.GILDED_BLACKSTONE.getDefaultState(), 0.2F);

    // GIANT SPHERES
	public static final SpheroidType HUGE_MONSTER_CAVE = new CaveSpheroidType(SpheroidAdvancementIdentifier.cave, 20, 30, Blocks.NETHERRACK.getDefaultState(), Blocks.CRIMSON_NYLIUM.getDefaultState(), 2, 5)
			.addChestWithLootTable(LootTables.RUINED_PORTAL_CHEST, 0.5F)
			.addDecorator(SpheroidListVanilla.SpheroidDecorators.MUSHROOMS, 0.3F);
	public static final SpheroidType MONSTER_CAVE = new CaveSpheroidType(SpheroidAdvancementIdentifier.cave, 10, 15, Blocks.NETHERRACK.getDefaultState(), Blocks.WARPED_NYLIUM.getDefaultState(), 1, 3)
			.addChestWithLootTable(LootTables.RUINED_PORTAL_CHEST, 0.5F)
			.addDecorator(SpheroidListVanilla.SpheroidDecorators.MUSHROOMS, 0.3F);

	// HUGE FUNGI
	public static final SpheroidType CRIMSON_WOOD = new ShellSpheroidType(SpheroidAdvancementIdentifier.crimson_wood, 8, 15, Blocks.CRIMSON_STEM.getDefaultState(), Blocks.NETHER_WART_BLOCK.getDefaultState(), 2, 3)
			.addShellSpeckles(Blocks.SHROOMLIGHT.getDefaultState(), 0.1F);
	public static final SpheroidType WARPED_WOOD = new ShellSpheroidType(SpheroidAdvancementIdentifier.warped_wood, 8, 15, Blocks.WARPED_STEM.getDefaultState(), Blocks.WARPED_WART_BLOCK.getDefaultState(), 2, 3)
			.addShellSpeckles(Blocks.SHROOMLIGHT.getDefaultState(), 0.1F);
		
    // GLASS
    public static final SpheroidType BLACK_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 8,  Blocks.BLACK_STAINED_GLASS.getDefaultState());
    public static final SpheroidType ORANGE_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 8,  Blocks.ORANGE_STAINED_GLASS.getDefaultState());
    public static final SpheroidType RED_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 8,  Blocks.RED_STAINED_GLASS.getDefaultState());
    public static final SpheroidType YELLOW_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 8,  Blocks.YELLOW_STAINED_GLASS.getDefaultState());

    // RARE
    public static final SpheroidType OBSIDIAN = new ModularSpheroidType(SpheroidAdvancementIdentifier.obsidian, 5, 8, Blocks.OBSIDIAN.getDefaultState());
    public static final SpheroidType CRYING_OBSIDIAN = new ShellSpheroidType(SpheroidAdvancementIdentifier.crying_obsidian, 4, 6, Blocks.CRYING_OBSIDIAN.getDefaultState(), Blocks.OBSIDIAN.getDefaultState(), 2, 4)
			.addShellSpeckles(Blocks.CRYING_OBSIDIAN.getDefaultState(), 0.1F);
    public static final SpheroidType GLOWSTONE = new ModularSpheroidType(SpheroidAdvancementIdentifier.glowstone, 5, 12, Blocks.GLOWSTONE.getDefaultState());
    public static final SpheroidType BEDROCK = new ModularSpheroidType(SpheroidAdvancementIdentifier.bedrock, 4, 7, Blocks.BEDROCK.getDefaultState());
	
    // ORES
    public static final SpheroidType NETHER_QUARTZ = new CoreSpheroidType(SpheroidAdvancementIdentifier.nether_quartz, 5, 15, Blocks.NETHER_QUARTZ_ORE.getDefaultState(), MAP_NETHER_STONES, 4, 8);
    public static final SpheroidType NETHER_GOLD_ORE = new CoreSpheroidType(SpheroidAdvancementIdentifier.nether_gold_ore, 5, 15, Blocks.NETHER_GOLD_ORE.getDefaultState(), MAP_NETHER_STONES, 2, 5);
    public static final SpheroidType ANCIENT_DEBRIS = new CoreSpheroidType(SpheroidAdvancementIdentifier.ancient_debris, 5, 15, Blocks.ANCIENT_DEBRIS.getDefaultState(), MAP_NETHER_STONES, 1, 2);

    // LAVA
    public static final SpheroidType LAVA_GLASS = new LiquidSpheroidType(SpheroidAdvancementIdentifier.lava, 5, 14, Blocks.LAVA.getDefaultState(), MAP_NETHER_GLASS, 1, 3, 25, 90,  50);
    public static final SpheroidType LAVA_GLASS_MAGMA = new LiquidSpheroidType(SpheroidAdvancementIdentifier.magma, 5, 20, Blocks.LAVA.getDefaultState(), MAP_NETHER_GLASS, 3, 6, 70, 100, 50)
            .setCoreBlock(Blocks.MAGMA_BLOCK.getDefaultState(), 2, 5);
    public static final SpheroidType LAVA_STONE = new LiquidSpheroidType(SpheroidAdvancementIdentifier.lava, 5, 14, Blocks.LAVA.getDefaultState(), MAP_NETHER_STONES, 2, 6, 10, 100, 50);
    public static final SpheroidType LAVA_STONE_MAGMA = new LiquidSpheroidType(SpheroidAdvancementIdentifier.magma, 5, 20, Blocks.LAVA.getDefaultState(), MAP_NETHER_STONES, 3, 6, 70, 100, 50)
            .setCoreBlock(Blocks.MAGMA_BLOCK.getDefaultState(), 2, 5);
    public static final SpheroidType LAVA_OBSIDIAN = new LiquidSpheroidType(SpheroidAdvancementIdentifier.obsidian, 10, 20, Blocks.LAVA.getDefaultState(), MAP_NETHER_STONES, 3, 6, 50, 100, 25)
            .setCoreBlock(Blocks.OBSIDIAN.getDefaultState(), 2, 5);
    public static final SpheroidType LAVA_BEDROCK = new LiquidSpheroidType(SpheroidAdvancementIdentifier.bedrock, 10, 20, Blocks.LAVA.getDefaultState(), MAP_NETHER_STONES, 3, 6, 50, 100, 0)
            .setCoreBlock(Blocks.BEDROCK.getDefaultState(), 2, 5);

    // MUSHROOMS
    public static final SpheroidType BROWN_MUSHROOM = new MushroomSpheroidType(SpheroidAdvancementIdentifier.brown_mushroom, 4, 10, Blocks.MUSHROOM_STEM.getDefaultState(), Blocks.BROWN_MUSHROOM_BLOCK.getDefaultState(), 2, 3);
    public static final SpheroidType RED_MUSHROOM = new MushroomSpheroidType(SpheroidAdvancementIdentifier.red_mushroom, 4, 10, Blocks.MUSHROOM_STEM.getDefaultState(), Blocks.RED_MUSHROOM_BLOCK.getDefaultState(), 2, 3);

    // DUNGEON
	public static final SpheroidType DUNGEON_MAGMA_CUBE = new DungeonSpheroidType(SpheroidAdvancementIdentifier.nether_dungeon, 6, 12, EntityType.MAGMA_CUBE, MAP_NETHER_DUNGEON_STONES, 2, 4);
	public static final SpheroidType DUNGEON_WITHER_SKELETON = new DungeonSpheroidType(SpheroidAdvancementIdentifier.nether_dungeon, 6, 12, EntityType.WITHER_SKELETON,  MAP_NETHER_DUNGEON_STONES, 2, 4);
	public static final SpheroidType DUNGEON_BLAZE = new DungeonSpheroidType(SpheroidAdvancementIdentifier.nether_dungeon, 6, 12, EntityType.BLAZE, MAP_NETHER_DUNGEON_STONES, 2, 4);
	public static final SpheroidType DUNGEON_STRIDER = new DungeonSpheroidType(SpheroidAdvancementIdentifier.nether_dungeon, 6, 12, EntityType.STRIDER, MAP_NETHER_DUNGEON_STONES, 2, 4);
	public static final SpheroidType DUNGEON_ENDERMAN = new DungeonSpheroidType(SpheroidAdvancementIdentifier.nether_dungeon, 6, 12, EntityType.STRIDER, MAP_NETHER_DUNGEON_STONES, 2, 4);
	public static final SpheroidType DUNGEON_HOGLIN = new DungeonSpheroidType(SpheroidAdvancementIdentifier.nether_dungeon, 6, 12, EntityType.HOGLIN, MAP_NETHER_DUNGEON_STONES, 2, 4);

	// UNIQUE
	public static final SpheroidType NETHER_FORTRESS = new NetherFortressSpheroidType(SpheroidAdvancementIdentifier.nether_fortress, 25, 35);


	public static void setup(SpheroidLoader spheroidLoader) {
		StarrySkyCommon.log(INFO, "Loading Vanilla Nether Spheroids...");

		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.ESSENTIAL, 10.0F, NETHERRACK);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.ESSENTIAL, 1.0F, MAGMA_SPRINKLED_NETHERRACK);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.ESSENTIAL, 4.0F, SOUL_SAND);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.ESSENTIAL, 3.0F, SOUL_SOIL);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.ESSENTIAL, 2.0F, MAGMA_BLOCK);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.ESSENTIAL, 2.0F, GRAVEL);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.ESSENTIAL, 4.0F, CRIMSON_NYLIUM);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.ESSENTIAL, 4.0F, WARPED_NYLIUM);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.ESSENTIAL, 3.0F, BASALT);

		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.DECORATIVE, 10.0F, GLOWSTONE);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.DECORATIVE, 2.0F, BLACKSTONE);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.DECORATIVE, 0.5F, BLACK_STAINED_GLASS);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.DECORATIVE, 0.5F, ORANGE_STAINED_GLASS);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.DECORATIVE, 0.5F, RED_STAINED_GLASS);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.DECORATIVE, 0.5F, YELLOW_STAINED_GLASS);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.DECORATIVE, 1.5F, OBSIDIAN);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.DECORATIVE, 1.5F, CRYING_OBSIDIAN);

		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.WOOD, 10.0F, CRIMSON_WOOD);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.WOOD, 10.0F, WARPED_WOOD);

		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.ORE, 10.0F, NETHER_QUARTZ);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.ORE, 4.0F, NETHER_GOLD_ORE);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.ORE, 0.5F, ANCIENT_DEBRIS);

		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.FLUID, 10.0F, LAVA_GLASS);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.FLUID, 5.0F, LAVA_STONE);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.FLUID, 4.0F, LAVA_STONE_MAGMA);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.FLUID, 4.0F, LAVA_GLASS_MAGMA);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.FLUID, 2.0F, LAVA_OBSIDIAN);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.FLUID, 0.5F, LAVA_BEDROCK);

		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.TREASURE, 5.0F, BROWN_MUSHROOM);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.TREASURE, 5.0F, RED_MUSHROOM);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.TREASURE, 5.0F, MONSTER_CAVE);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.TREASURE, 1.0F, HUGE_MONSTER_CAVE);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.TREASURE, 1.0F, BEDROCK);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.TREASURE, 5.0F, NETHER_FORTRESS);

		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.DUNGEON, 10.0F, DUNGEON_BLAZE);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.DUNGEON, 5.0F, DUNGEON_WITHER_SKELETON);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.DUNGEON, 5.0F, DUNGEON_MAGMA_CUBE);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.DUNGEON, 2.0F, DUNGEON_ENDERMAN);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.DUNGEON, 0.3F, DUNGEON_STRIDER);
		spheroidLoader.registerSpheroidType(NETHER, SpheroidDistributionType.DUNGEON, 0.3F, DUNGEON_HOGLIN);
	}

}