package de.dafuqs.starryskies.state_providers;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class StarryStateProviders {

    private static final DeferredRegister<BlockStateProviderType<?>> REGISTRAR = DeferredRegister.create(BuiltInRegistries.BLOCKSTATE_PROVIDER_TYPE, StarrySkies.MOD_ID);

    public static final DeferredHolder<BlockStateProviderType<?>, BlockStateProviderType<WeightedBlockGroupBlockStateProvider>> WEIGHTED_BLOCK_GROUP_STATE_PROVIDER = register("weighted_block_group_state_provider", WeightedBlockGroupBlockStateProvider.CODEC);
    public static final DeferredHolder<BlockStateProviderType<?>, BlockStateProviderType<UniqueBlockGroupBlockStateProvider>> UNIQUE_BLOCK_GROUP_STATE_PROVIDER = register("unique_block_group_state_provider", UniqueBlockGroupBlockStateProvider.CODEC);
    public static final DeferredHolder<BlockStateProviderType<?>, BlockStateProviderType<AlternatingBockStateProvider>> ALTERNATING_STATE_PROVIDER = register("alternating_state_provider", AlternatingBockStateProvider.CODEC);

    private static <P extends BlockStateProvider> DeferredHolder<BlockStateProviderType<?>, BlockStateProviderType<P>> register(String name, MapCodec<P> codec) {
        return REGISTRAR.register(name, () -> new BlockStateProviderType<>(codec));
    }

    public static void register(IEventBus modBus) {
        REGISTRAR.register(modBus);
	}
	
}
