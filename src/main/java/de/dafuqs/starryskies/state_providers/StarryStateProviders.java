package de.dafuqs.starryskies.state_providers;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;

public class StarryStateProviders {
	
	public static final BlockStateProviderType<WeightedBlockGroupBlockStateProvider> WEIGHTED_BLOCK_GROUP_STATE_PROVIDER = register("weighted_block_group_state_provider", WeightedBlockGroupBlockStateProvider.CODEC);
	public static final BlockStateProviderType<UniqueBlockGroupBlockStateProvider> UNIQUE_BLOCK_GROUP_STATE_PROVIDER = register("unique_block_group_state_provider", UniqueBlockGroupBlockStateProvider.CODEC);
	public static final BlockStateProviderType<AlternatingBockStateProvider> ALTERNATING_STATE_PROVIDER = register("alternating_state_provider", AlternatingBockStateProvider.CODEC);
	
	private static <P extends BlockStateProvider> BlockStateProviderType<P> register(String name, MapCodec<P> codec) {
		return Registry.register(BuiltInRegistries.BLOCKSTATE_PROVIDER_TYPE, StarrySkies.id(name), new BlockStateProviderType<>(codec));
	}
	
	public static void register() {
	
	}
	
}
