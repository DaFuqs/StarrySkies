package de.dafuqs.starryskies.commands;

import com.mojang.brigadier.CommandDispatcher;
import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.configs.StarrySkyConfig;
import de.dafuqs.starryskies.worldgen.ConfiguredSphere;
import de.dafuqs.starryskies.worldgen.PlacedSphere;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceOrIdArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.PermissionLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import org.jspecify.annotations.Nullable;

public class GenerateSphereCommand {
	
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess) {
		dispatcher.register(Commands.literal("starryskies_generate")
				.requires((source) -> source.permissions().hasPermission(new Permission.HasCommandLevel(PermissionLevel.byId(StarrySkyConfig.CONFIG.generateSphereCommandRequiredPermissionLevel.get()))))
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
		ServerLevel level = source.getLevel();
		RandomSource random = level.getRandom();
		WorldgenRandom chunkRandom = new WorldgenRandom(random);
		PlacedSphere<?> placed = sphere.generate(chunkRandom, level, pos, sphere.getSize(chunkRandom));
		placed.setPosition(new BlockPos(pos.getX(), pos.getY(), pos.getZ()));
		
		placed.streamChunksWithSphere().forEach(chunkPos -> placed.generate(level.getChunk(chunkPos.getWorldPosition()), level));
		placed.streamChunksWithSphere().forEach(chunkPos -> placed.decorate(level, chunkPos.getWorldPosition(), random));
		placed.populateEntities(ChunkPos.containing(pos), level, chunkRandom);
		placed.streamBlockPosesOfSpheres().forEach(blockPos -> level.getChunkSource().blockChanged(blockPos));
		
		return 0;
	}
	
	
}