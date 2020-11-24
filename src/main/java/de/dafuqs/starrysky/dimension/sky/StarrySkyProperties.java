package de.dafuqs.starrysky.dimension.sky;

import de.dafuqs.starrysky.StarrySkyCommon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class StarrySkyProperties extends SkyProperties {

    public StarrySkyProperties() {
        super(StarrySkyCommon.STARRY_SKY_CONFIG.cloudHeight, true, SkyType.NORMAL, false, false);
    }

    public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
        return color.multiply((sunHeight * 0.94F + 0.06F), (sunHeight * 0.94F + 0.06F), (sunHeight * 0.91F + 0.09F));
    }

    public boolean useThickFog(int camX, int camY) {
        return false;
    }

}
