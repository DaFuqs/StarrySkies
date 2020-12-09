package de.dafuqs.starrysky.mixin;

import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(DimensionType.class)
public abstract class DimensionTypeMixin {

    @Inject(at = @At("HEAD"), method = "hasEnderDragonFight", cancellable = true)
    void hasEnderDragonFight(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if(checkEnderDragonFight((DimensionType) (Object) this)) {
            callbackInfoReturnable.setReturnValue(true);
        }
    }

    private boolean checkEnderDragonFight(DimensionType dimensionType) {
        if(true) { // if(StarrySkyCommon.STARRY_SKY_CONFIG.fightEnderDragonInStarryEndInstead) {
            // search for the end
            if(    !dimensionType.hasCeiling()
                    && !dimensionType.isUltrawarm()
                    && !dimensionType.isNatural()
                    && dimensionType.getCoordinateScale() == 1.0
                    && !dimensionType.isBedWorking()
                    && !dimensionType.isRespawnAnchorWorking()
                    && dimensionType.getSkyProperties().equals(new Identifier("the_end"))) {
                // as good as it gets. The Starry End has sky light, the default end doesn't
                if(dimensionType.hasSkyLight()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}