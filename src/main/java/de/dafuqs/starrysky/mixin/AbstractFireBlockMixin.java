package de.dafuqs.starrysky.mixin;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.dimension.StarrySkyDimension;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(AbstractFireBlock.class)
public abstract class AbstractFireBlockMixin {

    @Inject(method = {"method_30366"}, at = {@At("HEAD")}, cancellable = true)
    private static void method_30366(World world, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if(StarrySkyCommon.STARRY_SKY_CONFIG.enableNetherPortalsToStarryNether) {
            if (world.getRegistryKey().equals(StarrySkyDimension.STARRY_SKY_WORLD_KEY) || world.getRegistryKey().equals(StarrySkyDimension.STARRY_SKY_NETHER_WORLD_KEY)) {
                callbackInfoReturnable.setReturnValue(true);
            }
        }
    }

}