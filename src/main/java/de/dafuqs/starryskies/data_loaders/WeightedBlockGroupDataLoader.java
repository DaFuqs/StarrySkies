package de.dafuqs.starryskies.data_loaders;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.*;
import net.minecraft.server.packs.resources.*;
import net.minecraft.util.*;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

import java.util.*;

public class WeightedBlockGroupDataLoader extends SimpleJsonResourceReloadListener<WeightedBlockGroupDataLoader.Entry> implements PreparableReloadListener {
	
	public static final String LOCATION = "starry_skies/weighted_block_group";
	public static final Identifier ID = StarrySkies.id(LOCATION);
	public static final WeightedBlockGroupDataLoader INSTANCE = new WeightedBlockGroupDataLoader();
	
	protected static final Map<String, Map<Block, Float>> GROUPS = new Object2ObjectArrayMap<>();
	
	public record Entry(String group, Map<Identifier, Float> weightedBlockIDs) {
		public static final Codec<WeightedBlockGroupDataLoader.Entry> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
				Codec.STRING.fieldOf("group").forGetter(WeightedBlockGroupDataLoader.Entry::group),
				Codec.unboundedMap(Identifier.CODEC, Codec.FLOAT).fieldOf("blocks").forGetter(WeightedBlockGroupDataLoader.Entry::weightedBlockIDs)
		).apply(instance, WeightedBlockGroupDataLoader.Entry::new));
	}
	
	private WeightedBlockGroupDataLoader() {
		super(Entry.CODEC, FileToIdConverter.json(LOCATION));
	}
	
	@Override
	protected void apply(Map<Identifier, WeightedBlockGroupDataLoader.Entry> prepared, @NonNull ResourceManager manager, @NonNull ProfilerFiller profiler) {
		for (Map.Entry<Identifier, WeightedBlockGroupDataLoader.Entry> entry : prepared.entrySet()) {
			String group = entry.getValue().group;
			
			for (Map.Entry<Identifier, Float> e : entry.getValue().weightedBlockIDs.entrySet()) {
				Optional<Block> optionalBlock = BuiltInRegistries.BLOCK.getOptional(e.getKey());
				if (optionalBlock.isPresent()) {
					Block block = optionalBlock.get();
					float weight = e.getValue();
					GROUPS.computeIfAbsent(group, k -> new Object2FloatArrayMap<>()).put(block, weight);
				}
			}
		}
	}
	
	public Map<Block, Float> get(String blockGroup) {
		return GROUPS.get(blockGroup);
	}
	
	public BlockState getEntry(String group, RandomSource random) {
		Map<Block, Float> weightedBlocks = get(group);
		if (weightedBlocks == null) {
			StarrySkies.LOGGER.warn("Trying to query a nonexistent WeightedBlockGroup: {}", group);
			StarrySkies.LOGGER.error(Arrays.toString(Thread.currentThread().getStackTrace()));
			return Blocks.AIR.defaultBlockState();
		} else if (weightedBlocks.isEmpty()) {
			StarrySkies.LOGGER.warn("Trying to query an empty WeightedBlockGroup: {}", group);
			StarrySkies.LOGGER.error(Arrays.toString(Thread.currentThread().getStackTrace()));
			return Blocks.AIR.defaultBlockState();
		}
		return Support.getWeightedRandom(weightedBlocks, random).defaultBlockState();
	}
	
}