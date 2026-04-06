package de.dafuqs.starryskies.state_providers;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.data_loaders.*;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import org.jetbrains.annotations.NotNull;

public class UniqueBlockGroupBlockStateProvider extends BlockStateProvider {
	public static final MapCodec<UniqueBlockGroupBlockStateProvider> CODEC;
	private final String group;
	
	protected UniqueBlockGroupBlockStateProvider(String group) {
		this.group = group;
	}
	
	public @NotNull BlockStateProviderType<?> type() {
		return StarryStateProviders.UNIQUE_BLOCK_GROUP_STATE_PROVIDER;
	}
	
	public @NotNull BlockState getState(RandomSource random, BlockPos pos) {
		return UniqueBlockGroupDataLoader.INSTANCE.getEntry(group, random);
	}
	
	static {
		CODEC = ExtraCodecs.NON_EMPTY_STRING.fieldOf("group").xmap(UniqueBlockGroupBlockStateProvider::new, (provider) -> provider.group);
	}
}
