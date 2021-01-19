package de.dafuqs.starrysky.dimension.sky;

import de.dafuqs.starrysky.StarrySkyCommon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class StarrySkyProperties extends SkyProperties {

    public StarrySkyProperties() {
        super(StarrySkyCommon.STARRY_SKY_CONFIG.cloudHeight, true, SkyType.NORMAL, true, false);
    }

    /**
     *
     * @param color
     * @param sunHeight Cos 0-1
     * @return
     */
    @Override
    public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
        return color.multiply((sunHeight), (sunHeight), (sunHeight));
    }

    @Override
    public boolean useThickFog(int camX, int camY) {
        return false;
    }

    // NO sunrise / sunset
    @Override
    @Nullable
    public float[] getFogColorOverride(float skyAngle, float tickDelta) {
        return null;
    }

    @Override
    public boolean shouldBrightenLighting() {
        return false;
    }


}
