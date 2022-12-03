package de.dafuqs.starryskies.commands;

import com.mojang.brigadier.CommandDispatcher;
import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.Support;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class ClosestSpheroidCommand {
	
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(CommandManager.literal("starryskies_sphere")
				.requires((source) -> source.hasPermissionLevel(StarrySkies.CONFIG.sphereCommandRequiredPermissionLevel))
				.executes((context -> execute(context.getSource(), null)))
				.then(CommandManager.argument("identifier", IdentifierArgumentType.identifier())
						.executes((context -> execute(context.getSource(), IdentifierArgumentType.getIdentifier(context, "identifier"))))));
	}
	
	private static int execute(ServerCommandSource source, Identifier identifier) {
		ServerPlayerEntity caller = source.getPlayer();
		
		Optional<Support.SpheroidDistance> spheroidDistance;
		if (identifier == null) {
			spheroidDistance = Support.getClosestSpheroidToPlayer(caller);
		} else {
			spheroidDistance = Optional.ofNullable(Support.getClosestSpheroid3x3(source.getWorld(), new BlockPos(source.getPosition()), identifier));
		}
		
		if (spheroidDistance.isPresent()) {
			source.sendFeedback(Text.translatable(spheroidDistance.get().spheroid.getDescription()), false);
		} else {
			source.sendFeedback(Text.translatable("Could not determine closest spheroid. :("), false);
		}
		
		return 1;
	}
	
	
}