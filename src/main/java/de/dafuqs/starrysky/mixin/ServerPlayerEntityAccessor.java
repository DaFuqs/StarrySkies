package de.dafuqs.starrysky.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;


@Mixin(ServerPlayerEntity.class)
public interface ServerPlayerEntityAccessor {

    @Accessor("seenCredits")
    public boolean getSeenCredits();

    @Accessor("seenCredits")
    public void setSeenCredits(boolean seenCredits);

}