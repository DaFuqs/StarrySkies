package de.dafuqs.starryskies.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)

public interface EntityAccessor {
	
	@Accessor("lastNetherPortalPosition")
	BlockPos getLastNetherPortalPosition();
	
}