package de.dafuqs.starryskies.commands;

import com.mojang.brigadier.*;
import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import org.jetbrains.annotations.*;

public class GenerateSphereCommand {
	
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess) {
		dispatcher.register(Commands.literal("starryskies_generate")
				.requires((source) -> source.hasPermission(StarrySkies.CONFIG.generateSphereCommandRequiredPermissionLevel))
				.then(Commands.argument("sphere", new ConfiguredSphereArgumentType(registryAccess))
						.executes(context -> execute(context.getSource(), null, context.getArgument("sphere", Holder.class)))
						.then(Commands.argument("pos", BlockPosArgument.blockPos())
								.executes(context -> execute(context.getSource(), BlockPosArgument.getLoadedBlockPos(context, "pos"), context.getArgument("sphere", Holder.class))))));
	}
	
	private static int execute(CommandSourceStack source, @Nullable BlockPos pos, Holder<ConfiguredSphere<?, ?>> entry) {
		if (pos == null) {
			pos = BlockPos.containing(source.getPosition());
		}
		
		ConfiguredSphere<?, ?> sphere = entry.value();
		RandomSource random = source.getLevel().random;
		WorldgenRandom chunkRandom = new WorldgenRandom(random);
		PlacedSphere<?> placed = sphere.generate(chunkRandom, source.getLevel().registryAccess(), pos, sphere.getSize(chunkRandom));
		placed.setPosition(new BlockPos(pos.getX(), pos.getY(), pos.getZ()));
		
		placed.streamChunksWithSphere().forEach(chunkPos -> placed.generate(source.getLevel().getChunk(chunkPos.getWorldPosition()), source.registryAccess()));
		placed.streamChunksWithSphere().forEach(chunkPos -> placed.decorate(source.getLevel(), chunkPos.getWorldPosition(), random));
		placed.populateEntities(new ChunkPos(pos), source.getLevel(), chunkRandom);
		placed.streamBlockPosesOfSpheres().forEach(blockPos -> source.getLevel().getChunkSource().blockChanged(blockPos));
		
		return 0;
	}
	
	
}