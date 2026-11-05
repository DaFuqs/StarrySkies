package de.dafuqs.starryskies.state_providers;

import com.mojang.serialization.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.*;
import org.jspecify.annotations.*;

import java.util.*;

public class AlternatingBockStateProvider implements BlockStateProvider {

	public static final MapCodec<AlternatingBockStateProvider> CODEC;
	private final List<BlockState> states;
	
	protected AlternatingBockStateProvider(List<BlockState> states) {
		this.states = states;
	}

	public @NonNull MapCodec<? extends BlockStateProvider> codec() {
		return StarryStateProviders.ALTERNATING_STATE_PROVIDER.get();
	}

	public @NonNull BlockState getState(@NonNull LevelAccessor level, @NonNull RandomSource random, BlockPos pos) {
		int sum = Math.abs(pos.getX()) + Math.abs(pos.getY()) + Math.abs(pos.getZ());
		int mod = sum % this.states.size();
		return states.get(mod);
	}

	static {
		CODEC = BlockState.CODEC.listOf().fieldOf("states").xmap(AlternatingBockStateProvider::new, (provider) -> provider.states);
	}
}
