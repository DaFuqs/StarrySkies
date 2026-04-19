package de.dafuqs.starryskies.state_providers;

import com.mojang.serialization.*;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.*;
import org.jspecify.annotations.NonNull;

import java.util.*;

public class AlternatingBockStateProvider extends BlockStateProvider {
	
	public static final MapCodec<AlternatingBockStateProvider> CODEC;
	private final List<BlockState> states;
	
	protected AlternatingBockStateProvider(List<BlockState> states) {
		this.states = states;
	}
	
	public @NonNull BlockStateProviderType<?> type() {
		return StarryStateProviders.ALTERNATING_STATE_PROVIDER;
	}

	public @NonNull BlockState getState(@NonNull WorldGenLevel level, @NonNull RandomSource random, BlockPos pos) {
		int sum = Math.abs(pos.getX()) + Math.abs(pos.getY()) + Math.abs(pos.getZ());
		int mod = sum % this.states.size();
		return states.get(mod);
	}
	
	static {
		CODEC = BlockState.CODEC.listOf().fieldOf("states").xmap(AlternatingBockStateProvider::new, (provider) -> provider.states);
	}
}
