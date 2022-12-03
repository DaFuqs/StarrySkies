package de.dafuqs.starryskies.dimension.sky;

import de.dafuqs.starryskies.StarrySkies;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class StarrySkyProperties extends DimensionEffects {
	
	public StarrySkyProperties() {
		super(StarrySkies.CONFIG.cloudHeight, true, SkyType.NORMAL, true, false);
	}
	
	/**
	 * @param color
	 * @param sunHeight Cos 0-1
	 * @return
	 */
	@Override
	public Vec3d adjustFogColor(@NotNull Vec3d color, float sunHeight) {
		return color.multiply((sunHeight), (sunHeight), (sunHeight));
	}
	
	@Override
	public boolean useThickFog(int camX, int camY) {
		return false;
	}
	
	// NO sunrise / sunset
	@Override
	public float[] getFogColorOverride(float skyAngle, float tickDelta) {
		return null;
	}
	
	@Override
	public boolean shouldBrightenLighting() {
		return false;
	}
	
	
}
