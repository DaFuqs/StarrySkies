package de.dafuqs.starryskies.worldgen;

import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.registries.*;
import de.dafuqs.starryskies.worldgen.spheres.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class Spheres<C extends SphereConfig, F extends Sphere<C>> {

	private static final DeferredRegister<Sphere<?>> REGISTRAR = DeferredRegister.create(StarryRegistries.SPHERE, StarrySkies.MOD_ID);
	
	// Highly configurable
	public static final DeferredHolder<Sphere<?>, SimpleSphere> SIMPLE = register("simple", () -> new SimpleSphere(SimpleSphere.Config.CODEC));
	public static final DeferredHolder<Sphere<?>, ShellSphere<ShellSphere.Config>> SHELL = register("shell", () -> new ShellSphere<>(ShellSphere.Config.CODEC));
	public static final DeferredHolder<Sphere<?>, CoreSphere> CORE = register("core", () -> new CoreSphere(CoreSphere.Config.CODEC));
	public static final DeferredHolder<Sphere<?>, ModularSphere> MODULAR = register("modular", () -> new ModularSphere(ModularSphere.Config.CODEC));
	public static final DeferredHolder<Sphere<?>, CaveSphere> CAVE = register("cave", () -> new CaveSphere(CaveSphere.Config.CODEC));
	public static final DeferredHolder<Sphere<?>, FluidSphere> FLUID = register("fluid", () -> new FluidSphere(FluidSphere.Config.CODEC));
	public static final DeferredHolder<Sphere<?>, FluidCoreSphere> FLUID_CORE = register("fluid_core", () -> new FluidCoreSphere(FluidCoreSphere.Config.CODEC));
	public static final DeferredHolder<Sphere<?>, ShellCoreSphere> SHELL_CORE = register("shell_core", () -> new ShellCoreSphere(ShellCoreSphere.Config.CODEC));
	public static final DeferredHolder<Sphere<?>, HorizontalStackedSphere> HORIZONTAL_STACKED = register("horizontal_stacked", () -> new HorizontalStackedSphere(HorizontalStackedSphere.Config.CODEC));
	public static final DeferredHolder<Sphere<?>, StructureInteriorSphere> STRUCTURE_INTERIOR = register("structure_interior", () -> new StructureInteriorSphere(StructureInteriorSphere.Config.CODEC));
	
	// Kind of specialized ones
	public static final DeferredHolder<Sphere<?>, MushroomSphere> MUSHROOM = register("mushroom", () -> new MushroomSphere(MushroomSphere.Config.CODEC));
	public static final DeferredHolder<Sphere<?>, GeodeSphere> GEODE = register("geode", () -> new GeodeSphere(GeodeSphere.Config.CODEC));
	public static final DeferredHolder<Sphere<?>, CoralsSphere> CORALS = register("corals", () -> new CoralsSphere(CoralsSphere.Config.CODEC));
	public static final DeferredHolder<Sphere<?>, BeeHiveSphere> BEE_HIVE = register("bee_hive", () -> new BeeHiveSphere(BeeHiveSphere.Config.CODEC));
	public static final DeferredHolder<Sphere<?>, OceanMonumentSphere> OCEAN_MONUMENT = register("ocean_monument", () -> new OceanMonumentSphere(OceanMonumentSphere.Config.CODEC));

	private static <C extends SphereConfig, F extends Sphere<C>> DeferredHolder<Sphere<?>, F> register(String name, Supplier<F> feature) {
		return REGISTRAR.register(name, feature);
	}

	public static void register(IEventBus modBus) {
		REGISTRAR.register(modBus);
	}

}
