package de.dafuqs.starryskies.spheroids;

import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.spheroids.decorators.*;
import net.minecraft.registry.Registry;

public class SpheroidDecoratorTypes {
	
	private static Class<? extends SpheroidDecorator> register(String id, Class<? extends SpheroidDecorator> spheroidDecorator) {
		return Registry.register(StarryRegistries.SPHEROID_DECORATOR_TYPE, StarrySkies.locate(id), spheroidDecorator);
	}
	
	// Quite configurable ones
	public static Class<? extends SpheroidDecorator> SINGLE_BLOCK = register("single_block", SingleBlock.class);
	public static Class<? extends SpheroidDecorator> DOUBLE_BLOCK = register("double_block", DoubleBlockDecorator.class);
	public static Class<? extends SpheroidDecorator> STACKED_BLOCK = register("stacked_block", StackedBlockDecorator.class);
	public static Class<? extends SpheroidDecorator> GROUND_BLOCK = register("ground_block", GroundDecorator.class);
	public static Class<? extends SpheroidDecorator> CAVE_BOTTOM_BLOCK = register("cave_bottom_block", CaveBottomDecorator.class);
	public static Class<? extends SpheroidDecorator> PLANT_AROUND_POND = register("plant_around_pond", PlantAroundPondDecorator.class);
	public static Class<? extends SpheroidDecorator> CENTER_POND = register("center_pond", CenterPondDecorator.class);
	public static Class<? extends SpheroidDecorator> MULTIFACE_GROWTH = register("multiface_growth", MultifaceGrowthDecorator.class);
	public static Class<? extends SpheroidDecorator> HANGING_BLOCK = register("hanging_block", HangingBlockDecorator.class);
	public static Class<? extends SpheroidDecorator> HANGING_CAVE_BLOCK = register("hanging_cave_block", HangingCaveBlockDecorator.class);
	public static Class<? extends SpheroidDecorator> X_SPOT = register("x_spot", XMarksTheSpotDecorator.class);
	public static Class<? extends SpheroidDecorator> HUGE_PLANT = register("huge_plant", HugePlantDecorator.class);
	public static Class<? extends SpheroidDecorator> HUGE_HANGING_PLANT = register("huge_hanging_plant", HugeHangingPlantDecorator.class);
	
	// Pretty static ones (does not make too much sense to be configurable)
	public static Class<? extends SpheroidDecorator> DRIPLEAF = register("dripleaf", DripleafDecorator.class);
	public static Class<? extends SpheroidDecorator> COCOA = register("cocoa", CocoaDecorator.class);
	public static Class<? extends SpheroidDecorator> BAMBOO = register("bamboo", BambooDecorator.class);
	public static Class<? extends SpheroidDecorator> SEA_GREENS = register("sea_greens", SeaGreensDecorator.class);
	public static Class<? extends SpheroidDecorator> RUINED_PORTAL = register("ruined_portal", RuinedPortalDecorator.class);
	public static Class<? extends SpheroidDecorator> END_PORTAL = register("end_portal", EndPortalDecorator.class);
	public static Class<? extends SpheroidDecorator> END_GATEWAY = register("end_gateway", EndGatewayDecorator.class);
	public static Class<? extends SpheroidDecorator> CHORUS_FRUIT = register("chorus_fruit", ChorusFruitDecorator.class);
	
	public static void initialize() {
	
	}
	
}
