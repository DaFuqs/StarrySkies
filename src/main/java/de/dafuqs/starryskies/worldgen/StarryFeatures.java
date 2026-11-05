package de.dafuqs.starryskies.worldgen;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.worldgen.dimension.*;
import net.minecraft.core.registries.*;
import net.minecraft.world.level.levelgen.feature.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;
import org.jspecify.annotations.*;

public class StarryFeatures {

	private static final DeferredRegister<MapCodec<? extends Feature>> REGISTRAR = DeferredRegister.create(BuiltInRegistries.FEATURE_TYPE, StarrySkies.MOD_ID);

	public static DeferredHolder<MapCodec<? extends Feature>, @NonNull MapCodec<SphereDecorationFeature>> SPHERE_DECORATION = REGISTRAR.register("sphere_decoration", SphereDecorationFeature.INSTANCE::codec);

	public static void register(IEventBus modBus) {
		REGISTRAR.register(modBus);

	}
}