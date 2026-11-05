package de.dafuqs.starryskies.worldgen;

import com.mojang.serialization.MapCodec;
import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.worldgen.dimension.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

public class StarryFeatures {

	public static Identifier SPHERE_DECORATOR_FEATURE_ID = StarrySkies.id("sphere_decoration");
	public static MapCodec<SphereDecorationFeature> SPHERE_DECORATION;

	public static void initialize() {
		SPHERE_DECORATION = Registry.register(BuiltInRegistries.FEATURE_TYPE, SPHERE_DECORATOR_FEATURE_ID, SphereDecorationFeature.INSTANCE.codec());
	}
}