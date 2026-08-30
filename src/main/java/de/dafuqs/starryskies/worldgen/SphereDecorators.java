package de.dafuqs.starryskies.worldgen;

import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.registries.*;
import de.dafuqs.starryskies.worldgen.decorators.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

import java.util.function.*;

public class SphereDecorators {

	private static final DeferredRegister<SphereDecorator<?>> REGISTRAR = DeferredRegister.create(StarryRegistries.SPHERE_DECORATOR, StarrySkies.MOD_ID);

	public static DeferredHolder<SphereDecorator<?>, BambooDecorator> BAMBOO = register("bamboo", () -> new BambooDecorator(BambooDecoratorConfig.CODEC));
	public static DeferredHolder<SphereDecorator<?>, SingleBlockDecorator> SINGLE_BLOCK = register("single_block", () -> new SingleBlockDecorator(SingleBlockDecoratorConfig.CODEC));
	public static DeferredHolder<SphereDecorator<?>, SingleBlockProviderDecorator> SINGLE_BLOCK_PROVIDER = register("single_block_provider", () -> new SingleBlockProviderDecorator(SingleBlockProviderDecoratorConfig.CODEC));
	public static DeferredHolder<SphereDecorator<?>, DoubleBlockDecorator> DOUBLE_BLOCK = register("double_block", () -> new DoubleBlockDecorator(DoubleBlockDecoratorConfig.CODEC));
	public static DeferredHolder<SphereDecorator<?>, StackedBlockDecorator> STACKED_BLOCK = register("stacked_block", () -> new StackedBlockDecorator(StackedBlockDecoratorConfig.CODEC));
	public static DeferredHolder<SphereDecorator<?>, GroundBlockDecorator> GROUND_BLOCK = register("ground_block", () -> new GroundBlockDecorator(GroundBlockDecoratorConfig.CODEC));
	public static DeferredHolder<SphereDecorator<?>, CaveBottomDecorator> CAVE_BOTTOM_BLOCK = register("cave_bottom_block", () -> new CaveBottomDecorator(CaveBottomDecoratorConfig.CODEC));
	public static DeferredHolder<SphereDecorator<?>, PlantAroundPondDecorator> PLANT_AROUND_POND = register("plant_around_pond", () -> new PlantAroundPondDecorator(PlantAroundPondDecoratorConfig.CODEC));
	public static DeferredHolder<SphereDecorator<?>, CenterPondDecorator> CENTER_POND = register("center_pond", () -> new CenterPondDecorator(CenterPondDecoratorConfig.CODEC));
	public static DeferredHolder<SphereDecorator<?>, MultifaceGrowthDecorator> MULTIFACE_GROWTH = register("multiface_growth", () -> new MultifaceGrowthDecorator(MultifaceGrowthDecoratorConfig.CODEC));
	public static DeferredHolder<SphereDecorator<?>, HangingBlockDecorator> HANGING_BLOCK = register("hanging_block", () -> new HangingBlockDecorator(HangingBlockDecoratorConfig.CODEC));
	public static DeferredHolder<SphereDecorator<?>, HangingCavePlantDecorator> HANGING_CAVE_PLANT = register("hanging_cave_plant", () -> new HangingCavePlantDecorator(HugePlantDecoratorConfig.CODEC));
	public static DeferredHolder<SphereDecorator<?>, XMarksTheSpotDecorator> X_SPOT = register("x_spot", () -> new XMarksTheSpotDecorator(XMarksTheSpotDecoratorConfig.CODEC));
	public static DeferredHolder<SphereDecorator<?>, HugePlantDecorator> HUGE_PLANT = register("huge_plant", () -> new HugePlantDecorator(HugePlantDecoratorConfig.CODEC));
	public static DeferredHolder<SphereDecorator<?>, HugeHangingPlantDecorator> HUGE_HANGING_PLANT = register("huge_hanging_plant", () -> new HugeHangingPlantDecorator(HugePlantDecoratorConfig.CODEC));
	public static DeferredHolder<SphereDecorator<?>, DripleafDecorator> DRIPLEAF = register("dripleaf", () -> new DripleafDecorator(DripleafDecoratorConfig.CODEC));
	public static DeferredHolder<SphereDecorator<?>, CocoaDecorator> COCOA = register("cocoa", () -> new CocoaDecorator(SphereDecoratorConfig.DefaultSphereDecoratorConfig.CODEC));
	public static DeferredHolder<SphereDecorator<?>, SeaGreensDecorator> SEA_GREENS = register("sea_greens", () -> new SeaGreensDecorator(SphereDecoratorConfig.DefaultSphereDecoratorConfig.CODEC));
	public static DeferredHolder<SphereDecorator<?>, RuinedPortalDecorator> RUINED_PORTAL = register("ruined_portal", () -> new RuinedPortalDecorator(RuinedPortalDecoratorConfig.CODEC));
	public static DeferredHolder<SphereDecorator<?>, EndGatewayDecorator> END_GATEWAY = register("end_gateway", () -> new EndGatewayDecorator(SphereDecoratorConfig.DefaultSphereDecoratorConfig.CODEC));
	public static DeferredHolder<SphereDecorator<?>, ChorusFruitDecorator> CHORUS_FRUIT = register("chorus_fruit", () -> new ChorusFruitDecorator(ChorusFruitDecoratorConfig.CODEC));
	public static DeferredHolder<SphereDecorator<?>, BrushableBlockDecorator> BRUSHABLE_BLOCK = register("brushable_block", () -> new BrushableBlockDecorator(BrushableBlockDecoratorConfig.CODEC));
	public static DeferredHolder<SphereDecorator<?>, CaveColumnDecorator> CAVE_COLUMN = register("cave_column", () -> new CaveColumnDecorator(CaveColumnDecoratorConfig.CODEC));
	public static DeferredHolder<SphereDecorator<?>, LootChestDecorator> LOOT_CHEST = register("loot_chest", () -> new LootChestDecorator(LootChestDecoratorConfig.CODEC));
	public static DeferredHolder<SphereDecorator<?>, ScatteredLootChestsDecorator> SCATTERED_LOOT_CHESTS = register("scattered_loot_chests", () -> new ScatteredLootChestsDecorator(ScatteredLootChestsDecoratorConfig.CODEC));
	public static DeferredHolder<SphereDecorator<?>, MobSpawnerDecorator> MOB_SPAWNER = register("mob_spawner", () -> new MobSpawnerDecorator(MobSpawnerDecoratorConfig.CODEC));
	public static DeferredHolder<SphereDecorator<?>, BottomBlocksDecorator> BOTTOM_BLOCKS = register("bottom_blocks", () -> new BottomBlocksDecorator(BottomBlocksDecoratorConfig.CODEC));
	public static DeferredHolder<SphereDecorator<?>, SpeleothemDecorator> SPELEOTHEMS = register("speleothems", () -> new SpeleothemDecorator(SpeleothemDecoratorConfig.CODEC));
	public static DeferredHolder<SphereDecorator<?>, ReplaceBlocksDecorator> REPLACE_BLOCKS = register("replace_blocks", () -> new ReplaceBlocksDecorator(ReplaceBlocksDecoratorConfig.CODEC));
	public static DeferredHolder<SphereDecorator<?>, VinesDecorator> VINES = register("vines", () -> new VinesDecorator(VinesDecoratorConfig.CODEC));

	public static void register(IEventBus modBus) {
		REGISTRAR.register(modBus);
	}

	private static <C extends SphereDecoratorConfig, F extends SphereDecorator<C>> DeferredHolder<SphereDecorator<?>, F> register(String name, Supplier<F> feature) {
		return REGISTRAR.register(name, feature);
	}

}
