package de.dafuqs.starrysky.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.spheroids.Spheroid;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.List;

import static de.dafuqs.starrysky.generation.StarrySkyChunkGenerator.systemGenerator;

public class ClosestSpheroidCommand implements Command<ServerCommandSource> {

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity caller = context.getSource().getPlayer();

        Support.SpheroidDistance spheroidDistance = Support.getClosestSpheroidToPlayer(caller);

        if(spheroidDistance.spheroid != null) {
            context.getSource().sendFeedback(new LiteralText("Closest Sphere:"), true);
            context.getSource().sendFeedback(new LiteralText(spheroidDistance.spheroid.getDescription()), true);
        } else {
            context.getSource().sendFeedback(new LiteralText("Could not determine closest sphere. Ouch :("), true);
        }

        return 1;
    }


}