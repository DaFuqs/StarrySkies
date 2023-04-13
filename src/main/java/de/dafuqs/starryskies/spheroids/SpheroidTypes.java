package de.dafuqs.starryskies.spheroids;

import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.spheroids.spheroids.*;
import net.minecraft.util.registry.*;

public class SpheroidTypes {
	
	private static Class<? extends Spheroid.Template> register(String id, Class<? extends Spheroid.Template> spheroidType) {
		return Registry.register(StarryRegistries.SPHEROID_TYPE, StarrySkies.locate(id), spheroidType);
	}
	
	public static final Class<? extends Spheroid.Template> SIMPLE = register("simple", SimpleSpheroid.Template.class);
	public static final Class<? extends Spheroid.Template> DUNGEON = register("dungeon", DungeonSpheroid.Template.class);
	public static final Class<? extends Spheroid.Template> CAVE = register("cave", CaveSpheroid.Template.class);
	public static final Class<? extends Spheroid.Template> MODULAR = register("modular", ModularSpheroid.Template.class);
	public static final Class<? extends Spheroid.Template> CORE = register("core", CoreSpheroid.Template.class);
	public static final Class<? extends Spheroid.Template> NETHER_FORTRESS = register("nether_fortress", NetherFortressSpheroid.Template.class);
	public static final Class<? extends Spheroid.Template> GEODE = register("geode", GeodeSpheroid.Template.class);
	public static final Class<? extends Spheroid.Template> CORALS = register("corals", CoralsSpheroid.Template.class);
	public static final Class<? extends Spheroid.Template> STRONGHOLD = register("stronghold", StrongholdSpheroid.Template.class);
	public static final Class<? extends Spheroid.Template> STACKED_HORIZONTAL = register("stacked_horizontal", StackedHorizontalSpheroid.Template.class);
	public static final Class<? extends Spheroid.Template> END_CITY = register("end_city", EndCitySpheroid.Template.class);
	public static final Class<? extends Spheroid.Template> SHELL = register("shell", ShellSpheroid.Template.class);
	public static final Class<? extends Spheroid.Template> FLUID = register("fluid", FluidSpheroid.Template.class);
	public static final Class<? extends Spheroid.Template> CORE_FLUID = register("fluid_core", FluidCoreSpheroid.Template.class);
	public static final Class<? extends Spheroid.Template> MUSHROOM = register("mushroom", MushroomSpheroid.Template.class);
	public static final Class<? extends Spheroid.Template> SHELL_CORE = register("shell_core", ShellCoreSpheroid.Template.class);
	public static final Class<? extends Spheroid.Template> BEE_HIVE = register("bee_hive", BeeHiveSpheroid.Template.class);
	public static final Class<? extends Spheroid.Template> OCEAN_MONUMENT = register("ocean_monument", OceanMonumentSpheroid.Template.class);
	public static final Class<? extends Spheroid.Template> RAINBOW = register("rainbow", RainbowSpheroid.Template.class);
	public static final Class<? extends Spheroid.Template> MODULAR_RAINBOW = register("modular_rainbow", ModularRainbowSpheroid.Template.class);
	
	public static void initialize() {
	
	}
	
}
