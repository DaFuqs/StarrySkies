package de.dafuqs.starryskies.client.sky;

import net.fabricmc.api.*;
import net.minecraft.client.render.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

@Environment(EnvType.CLIENT)
public class StarrySkyProperties extends DimensionEffects {

	public StarrySkyProperties() {
		super(SkyType.NORMAL, false, false);
	}
	
	@Override
	public Vec3d adjustFogColor(@NotNull Vec3d color, float sunHeight) {
		return color.multiply((sunHeight), (sunHeight), (sunHeight));
	}

	@Override
	public boolean useThickFog(int camX, int camY) {
		return false;
	}

}
