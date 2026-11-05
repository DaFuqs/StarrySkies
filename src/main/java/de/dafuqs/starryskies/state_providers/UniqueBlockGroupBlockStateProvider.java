package de.dafuqs.starryskies.state_providers;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.data_loaders.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.*;
import org.jspecify.annotations.*;

public class UniqueBlockGroupBlockStateProvider implements BlockStateProvider {
	public static final MapCodec<UniqueBlockGroupBlockStateProvider> CODEC;
	private final String group;
	
	protected UniqueBlockGroupBlockStateProvider(String group) {
		this.group = group;
	}

	public @NonNull MapCodec<? extends BlockStateProvider> codec() {
		return StarryStateProviders.UNIQUE_BLOCK_GROUP_STATE_PROVIDER.get();
	}

	public @NonNull BlockState getState(@NonNull LevelAccessor level, @NonNull RandomSource random, @NonNull BlockPos pos) {
		return UniqueBlockGroupDataLoader.INSTANCE.getEntry(group, random);
	}

	static {
		CODEC = ExtraCodecs.NON_EMPTY_STRING.fieldOf("group").xmap(UniqueBlockGroupBlockStateProvider::new, (provider) -> provider.group);
	}
}
