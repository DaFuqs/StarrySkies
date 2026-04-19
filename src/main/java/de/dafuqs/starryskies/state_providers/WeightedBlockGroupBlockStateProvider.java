package de.dafuqs.starryskies.state_providers;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.data_loaders.*;
import net.minecraft.core.BlockPos;
import net.minecraft.util.*;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.*;
import org.jspecify.annotations.NonNull;

public class WeightedBlockGroupBlockStateProvider extends BlockStateProvider {
	public static final MapCodec<WeightedBlockGroupBlockStateProvider> CODEC;
	private final String group;
	
	protected WeightedBlockGroupBlockStateProvider(String group) {
		this.group = group;
	}
	
	public @NonNull BlockStateProviderType<?> type() {
		return StarryStateProviders.WEIGHTED_BLOCK_GROUP_STATE_PROVIDER;
	}
	
	public @NonNull BlockState getState(@NonNull WorldGenLevel level, @NonNull RandomSource random, @NonNull BlockPos pos) {
		return WeightedBlockGroupDataLoader.INSTANCE.getEntry(group, random);
	}
	
	static {
		CODEC = ExtraCodecs.NON_EMPTY_STRING.fieldOf("group").xmap(WeightedBlockGroupBlockStateProvider::new, (provider) -> provider.group);
	}
}
