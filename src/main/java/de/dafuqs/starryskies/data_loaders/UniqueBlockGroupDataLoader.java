package de.dafuqs.starryskies.data_loaders;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.*;
import it.unimi.dsi.fastutil.objects.*;
import net.fabricmc.fabric.api.resource.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.*;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class UniqueBlockGroupDataLoader extends SimpleJsonResourceReloadListener<UniqueBlockGroupDataLoader.Entry> implements IdentifiableResourceReloadListener {
	
	public static final String LOCATION = "starry_skies/unique_block_group";
	public static final ResourceLocation ID = StarrySkies.id(LOCATION);
	public static final UniqueBlockGroupDataLoader INSTANCE = new UniqueBlockGroupDataLoader();
	
	protected static final Map<String, Block> GROUPS = new Object2ObjectArrayMap<>();
	
	public record Entry(String group, List<ResourceLocation> blockIDs) {
		public static final Codec<Entry> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
				Codec.STRING.fieldOf("group").forGetter(Entry::group),
				ResourceLocation.CODEC.listOf().fieldOf("blocks").forGetter(Entry::blockIDs)
		).apply(instance, Entry::new));
	}
	
	private UniqueBlockGroupDataLoader() {
		super(Entry.CODEC, FileToIdConverter.json(LOCATION));
	}
	
	@Override
	protected void apply(Map<ResourceLocation, Entry> prepared, ResourceManager manager, ProfilerFiller profiler) {
		for (Map.Entry<ResourceLocation, Entry> entry : prepared.entrySet()) {
			String groupName = entry.getValue().group;
			if (GROUPS.containsKey(groupName)) {
				return;
			}
			
			for (ResourceLocation blockId : entry.getValue().blockIDs) {
				Optional<Block> optionalBlock = BuiltInRegistries.BLOCK.getOptional(blockId);
				optionalBlock.ifPresent(block -> GROUPS.put(groupName, block));
				return;
			}
		}
	}
	
	@Override
	public ResourceLocation getFabricId() {
		return ID;
	}
	
	public Block get(String id) {
		return GROUPS.get(id);
	}
	
	public BlockState getEntry(String group, RandomSource random) {
		Block block = UniqueBlockGroupDataLoader.INSTANCE.get(group);
		if (block == null) {
			StarrySkies.LOGGER.warn("Trying to query a nonexistent UniqueBlockGroup: {}", group);
			StarrySkies.LOGGER.error(Arrays.toString(Thread.currentThread().getStackTrace()));
			return Blocks.AIR.defaultBlockState();
		}
		return block.defaultBlockState();
	}
	
}