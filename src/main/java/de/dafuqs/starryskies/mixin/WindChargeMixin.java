package de.dafuqs.starryskies.mixin;

import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.portal.*;
import de.dafuqs.starryskies.registries.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.projectile.hurtingprojectile.windcharge.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import java.util.*;

@Mixin(WindCharge.class)
public class WindChargeMixin {

    @Inject(at = @At("TAIL"), method = "explode(Lnet/minecraft/world/phys/Vec3;)V")
    public void starryskies$spawnPortal(Vec3 position, CallbackInfo ci) {
        if (StarrySkies.CONFIG.enableStarryPortal) {
            WindCharge windCharge = (WindCharge) (Object) this;
            Level level = windCharge.level();
            BlockPos pos = BlockPos.containing(position);
            if (starryskies$inPortalDimension(level)) {
                Optional<StarryPortalShape> optionalShape = StarryPortalShape.findEmptyPortalShape(level, pos, Direction.Axis.X);
                optionalShape.ifPresent(starryPortalShape -> starryPortalShape.createPortalBlocks(level));
            }
        }
    }

    @Unique
    private static boolean starryskies$inPortalDimension(Level level) {
        return level.dimension() == Level.OVERWORLD || level.dimension() == StarryDimensionKeys.OVERWORLD_KEY;
    }

}
