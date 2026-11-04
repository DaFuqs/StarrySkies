package de.dafuqs.starryskies.state_providers;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class StarryStateProviders {
	
	public static final MapCodec<WeightedBlockGroupBlockStateProvider> WEIGHTED_BLOCK_GROUP_STATE_PROVIDER = register("weighted_block_group_state_provider", WeightedBlockGroupBlockStateProvider.CODEC);
	public static final MapCodec<UniqueBlockGroupBlockStateProvider> UNIQUE_BLOCK_GROUP_STATE_PROVIDER = register("unique_block_group_state_provider", UniqueBlockGroupBlockStateProvider.CODEC);
	public static final MapCodec<AlternatingBockStateProvider> ALTERNATING_STATE_PROVIDER = register("alternating_state_provider", AlternatingBockStateProvider.CODEC);
	
	private static <P extends BlockStateProvider> MapCodec<P> register(String name, MapCodec<P> codec) {
		return Registry.register(BuiltInRegistries.BLOCK_STATE_PROVIDER_TYPE, StarrySkies.id(name), codec);
	}
	
	public static void register() {
	
	}
	
}
