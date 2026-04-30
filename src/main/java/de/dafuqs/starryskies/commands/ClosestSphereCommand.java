package de.dafuqs.starryskies.commands;

import com.google.common.base.Stopwatch;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.datafixers.util.Pair;
import de.dafuqs.starryskies.Support;
import de.dafuqs.starryskies.configs.StarrySkyConfig;
import de.dafuqs.starryskies.registries.StarryRegistryKeys;
import de.dafuqs.starryskies.worldgen.ConfiguredSphere;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceOrTagArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.LocateCommand;
import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.PermissionLevel;
import net.minecraft.util.Util;

import java.util.Optional;

public class ClosestSphereCommand {
	
	private static final DynamicCommandExceptionType SPHERE_NOT_FOUND_EXCEPTION = new DynamicCommandExceptionType((id) ->
			Component.translatableEscape("commands.starry_skies.locate.sphere.not_found", id));
	
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess) {
		dispatcher.register(Commands.literal("starryskies_locate")
				.requires((source) -> source.permissions().hasPermission(new Permission.HasCommandLevel(PermissionLevel.byId(StarrySkyConfig.CONFIG.locateSphereCommandRequiredPermissionLevel.get()))))
				.executes((context -> execute(context.getSource())))
				.then(Commands.argument("sphere", ResourceOrTagArgument.resourceOrTag(registryAccess, StarryRegistryKeys.CONFIGURED_SPHERE))
						.executes(context -> execute(context.getSource(), ResourceOrTagArgument.getResourceOrTag(context, "sphere", StarryRegistryKeys.CONFIGURED_SPHERE)))));
	}
	
	private static int execute(CommandSourceStack source) {
		BlockPos pos = BlockPos.containing(source.getPosition());
		Optional<Support.SphereDistance> result;
		
		result = Support.getClosestSphere(source.getLevel(), pos);
		
		if (result.isPresent()) {
			source.sendSuccess(() -> Component.translatable(result.get().sphere.getDescription(source.registryAccess())), false);
			return 0;
		}
		
		source.sendSuccess(() -> Component.translatable("commands.starry_skies.locate.sphere.noop"), false);
		return 1;
	}
	
	private static int execute(CommandSourceStack source, ResourceOrTagArgument.Result<ConfiguredSphere<?, ?>> predicate) throws CommandSyntaxException {
		BlockPos pos = BlockPos.containing(source.getPosition());
		Optional<Pair<BlockPos, Holder<ConfiguredSphere<?, ?>>>> result;
		
		Stopwatch stopwatch = Stopwatch.createStarted(Util.TICKER);
		result = Support.getClosestSphere3x3(source.getLevel(), pos, predicate, source.registryAccess());
		stopwatch.stop();
		
		if (result.isPresent()) {
			return LocateCommand.showLocateResult(source, predicate, pos, result.get(), "commands.starry_skies.locate.sphere.success", true, stopwatch.elapsed());
		}
		
		throw SPHERE_NOT_FOUND_EXCEPTION.create(predicate.asPrintable());
	}
	
	
}