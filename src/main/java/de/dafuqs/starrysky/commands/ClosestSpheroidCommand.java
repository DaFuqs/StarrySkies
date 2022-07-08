package de.dafuqs.starrysky.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import de.dafuqs.starrysky.Support;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Optional;

public class ClosestSpheroidCommand implements Command<ServerCommandSource> {

    @Override
    public int run(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity caller = context.getSource().getPlayer();

        Optional<Support.SpheroidDistance> spheroidDistance = Support.getClosestSpheroidToPlayer(caller);

        if(spheroidDistance.isPresent()) {
            context.getSource().sendFeedback(Text.translatable("Closest Sphere:"), false);
            context.getSource().sendFeedback(Text.translatable(spheroidDistance.get().spheroid.getDescription()), false);
        } else {
            context.getSource().sendFeedback(Text.translatable("Could not determine closest sphere. :("), false);
        }

        return 1;
    }


}