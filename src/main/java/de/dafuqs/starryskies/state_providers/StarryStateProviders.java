package de.dafuqs.starryskies.state_providers;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.*;
import net.minecraft.core.registries.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

public class StarryStateProviders {

    private static final DeferredRegister<MapCodec<? extends BlockStateProvider>> REGISTRAR = DeferredRegister.create(BuiltInRegistries.BLOCK_STATE_PROVIDER_TYPE, StarrySkies.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends BlockStateProvider>, MapCodec<? extends BlockStateProvider>> WEIGHTED_BLOCK_GROUP_STATE_PROVIDER = register("weighted_block_group_state_provider", WeightedBlockGroupBlockStateProvider.CODEC);
    public static final DeferredHolder<MapCodec<? extends BlockStateProvider>, MapCodec<? extends BlockStateProvider>> UNIQUE_BLOCK_GROUP_STATE_PROVIDER = register("unique_block_group_state_provider", UniqueBlockGroupBlockStateProvider.CODEC);
    public static final DeferredHolder<MapCodec<? extends BlockStateProvider>, MapCodec<? extends BlockStateProvider>> ALTERNATING_STATE_PROVIDER = register("alternating_state_provider", AlternatingBockStateProvider.CODEC);

    private static <P extends BlockStateProvider> DeferredHolder<MapCodec<? extends BlockStateProvider>, MapCodec<? extends BlockStateProvider>> register(String name, MapCodec<P> codec) {
        return REGISTRAR.register(name, _ -> codec);
    }

    public static void register(IEventBus modBus) {
        REGISTRAR.register(modBus);
	}
	
}
