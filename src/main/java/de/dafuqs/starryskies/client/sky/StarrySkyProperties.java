package de.dafuqs.starryskies.client.sky;

import net.fabricmc.api.*;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.*;

@Environment(EnvType.CLIENT)
public class StarrySkyProperties extends DimensionSpecialEffects {

	public StarrySkyProperties() {
		super(SkyType.OVERWORLD, false, false);
	}
	
	@Override
	public @NotNull Vec3 getBrightnessDependentFogColor(@NotNull Vec3 color, float sunHeight) {
		return color.multiply((sunHeight), (sunHeight), (sunHeight));
	}

	@Override
	public boolean isFoggyAt(int camX, int camY) {
		return false;
	}

}
