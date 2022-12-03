package de.dafuqs.starryskies.mixin;

import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.dimension.StarrySkyDimension;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(AbstractFireBlock.class)
public abstract class AbstractFireBlockMixin {
	
	@Inject(method = {"isOverworldOrNether"}, at = {@At("HEAD")}, cancellable = true)
	private static void isOverworldOrNether(World world, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
		if (StarrySkies.CONFIG.enableNetherPortalsToStarryNether) {
			if (world.getRegistryKey().equals(StarrySkyDimension.OVERWORLD_KEY) || world.getRegistryKey().equals(StarrySkyDimension.NETHER_KEY)) {
				callbackInfoReturnable.setReturnValue(true);
			}
		}
	}
	
}