package de.dafuqs.starryskies.worldgen;

import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.worldgen.dimension.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class StarryFeatures {

    private static final DeferredRegister<Feature<?>> REGISTRAR = DeferredRegister.create(Registries.FEATURE, StarrySkies.MOD_ID);

    public static DeferredHolder<Feature<?>, SphereDecorationFeature> SPHERE_DECORATION = REGISTRAR.register("sphere_decoration", () -> new SphereDecorationFeature(NoneFeatureConfiguration.CODEC));

    public static void register(IEventBus modBus) {
        REGISTRAR.register(modBus);
	}
}