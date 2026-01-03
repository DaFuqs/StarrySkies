package de.dafuqs.starryskies.commands;

import com.mojang.brigadier.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.command.*;
import net.minecraft.command.argument.*;
import net.minecraft.registry.entry.*;
import net.minecraft.server.command.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.*;
import org.jetbrains.annotations.*;

public class GenerateSphereCommand {
	
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
		dispatcher.register(CommandManager.literal("starryskies_generate")
				.requires(CommandManager.requirePermissionLevel(CommandManager.GAMEMASTERS_CHECK))
				.then(CommandManager.argument("sphere", new ConfiguredSphereArgumentType(registryAccess))
						.executes(context -> execute(context.getSource(), null, context.getArgument("sphere", RegistryEntry.class)))
						.then(CommandManager.argument("pos", BlockPosArgumentType.blockPos())
								.executes(context -> execute(context.getSource(), BlockPosArgumentType.getLoadedBlockPos(context, "pos"), context.getArgument("sphere", RegistryEntry.class))))));
	}
	
	private static int execute(ServerCommandSource source, @Nullable BlockPos pos, RegistryEntry<ConfiguredSphere<?, ?>> entry) {
		if (pos == null) {
			pos = BlockPos.ofFloored(source.getPosition());
		}
		
		ConfiguredSphere<?, ?> sphere = entry.value();
		Random random = source.getWorld().random;
		ChunkRandom chunkRandom = new ChunkRandom(random);
		PlacedSphere<?> placed = sphere.generate(chunkRandom, source.getWorld().getRegistryManager(), pos, sphere.getSize(chunkRandom));
		placed.setPosition(new BlockPos(pos.getX(), pos.getY(), pos.getZ()));
		
		placed.streamChunksWithSphere().forEach(chunkPos -> placed.generate(source.getWorld().getChunk(chunkPos.getStartPos()), source.getRegistryManager()));
		placed.streamChunksWithSphere().forEach(chunkPos -> placed.decorate(source.getWorld(), chunkPos.getStartPos(), random));
		placed.populateEntities(new ChunkPos(pos), source.getWorld(), chunkRandom);
		placed.streamBlockPosesOfSpheres().forEach(blockPos -> source.getWorld().getChunkManager().markForUpdate(blockPos));
		
		return 0;
	}
	
	
}