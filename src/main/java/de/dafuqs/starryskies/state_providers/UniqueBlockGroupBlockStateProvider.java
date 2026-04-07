package de.dafuqs.starryskies.state_providers;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.data_loaders.*;
import net.minecraft.core.BlockPos;
import net.minecraft.util.*;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.*;
import org.jspecify.annotations.NonNull;

public class UniqueBlockGroupBlockStateProvider extends BlockStateProvider {
	public static final MapCodec<UniqueBlockGroupBlockStateProvider> CODEC;
	private final String group;
	
	protected UniqueBlockGroupBlockStateProvider(String group) {
		this.group = group;
	}
	
	public @NonNull BlockStateProviderType<?> type() {
		return StarryStateProviders.UNIQUE_BLOCK_GROUP_STATE_PROVIDER;
	}
	
	public @NonNull BlockState getState(@NonNull WorldGenLevel level, @NonNull RandomSource random, @NonNull BlockPos pos) {
		return UniqueBlockGroupDataLoader.INSTANCE.getEntry(group, random);
	}
	
	static {
		CODEC = ExtraCodecs.NON_EMPTY_STRING.fieldOf("group").xmap(UniqueBlockGroupBlockStateProvider::new, (provider) -> provider.group);
	}
}
