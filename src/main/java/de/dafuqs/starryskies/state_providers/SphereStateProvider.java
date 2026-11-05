package de.dafuqs.starryskies.state_providers;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.*;

public class SphereStateProvider {

	public static final MapCodec<BlockStateProvider> BLOCK_STATE_PROVIDER_MAP_CODEC = BuiltInRegistries.BLOCK_STATE_PROVIDER_TYPE.byNameCodec().dispatchMap(BlockStateProvider::codec, c -> c);
	
	public static final Codec<SphereStateProvider> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			BLOCK_STATE_PROVIDER_MAP_CODEC.forGetter((provider) -> provider.provider),
			Codec.BOOL.optionalFieldOf("reroll_for_every_pos", false).forGetter((provider) -> provider.rerollForEveryPos)
	).apply(instance, SphereStateProvider::new));
	
	private final BlockStateProvider provider;
	private final boolean rerollForEveryPos;
	
	public SphereStateProvider(BlockStateProvider provider, boolean rerollForEveryPos) {
		this.provider = provider;
		this.rerollForEveryPos = rerollForEveryPos;
	}
	
	public BlockStateProvider getForSphere(WorldGenLevel level, RandomSource random, BlockPos spherePos) {
		if (rerollForEveryPos) {
			return provider;
		} else {
			return new SimpleStateProvider(provider.getState(level, random, spherePos));
		}
	}
	
}
