package de.dafuqs.starryskies.mixin;

import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.registries.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;


@Mixin(BaseFireBlock.class)
public abstract class BaseFireBlockMixin {

	@Inject(method = {"inPortalDimension"}, at = {@At("HEAD")}, cancellable = true)
	private static void starryskies$isOverworldOrNether(Level world, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
		if (StarrySkies.CONFIG.enableNetherPortalsToStarryNether) {
			if (world.dimension().equals(StarryDimensionKeys.OVERWORLD_KEY) || world.dimension().equals(StarryDimensionKeys.NETHER_KEY)) {
				callbackInfoReturnable.setReturnValue(true);
			}
		}
	}

}