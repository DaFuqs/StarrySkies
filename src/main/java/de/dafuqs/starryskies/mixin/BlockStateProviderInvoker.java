package de.dafuqs.starryskies.mixin;

import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockStateProvider.class)
public interface BlockStateProviderInvoker {

    @Invoker("type")
    BlockStateProviderType<BlockStateProvider> invokeType();

}