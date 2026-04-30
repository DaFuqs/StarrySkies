package de.dafuqs.starryskies.mixin;

import de.dafuqs.starryskies.configs.StarrySkyConfig;
import de.dafuqs.starryskies.registries.StarryDimensionKeys;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(BaseFireBlock.class)
public abstract class BaseFireBlockMixin {

	@Inject(method = {"inPortalDimension"}, at = {@At("HEAD")}, cancellable = true)
	private static void starryskies$isOverworldOrNether(Level world, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
		if (StarrySkyConfig.CONFIG.enableNetherPortalsToStarryNether.get()) {
			if (world.dimension().equals(StarryDimensionKeys.OVERWORLD_KEY) || world.dimension().equals(StarryDimensionKeys.NETHER_KEY)) {
				callbackInfoReturnable.setReturnValue(true);
			}
		}
	}

}