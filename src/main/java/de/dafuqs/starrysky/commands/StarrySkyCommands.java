package de.dafuqs.starrysky.commands;

import de.dafuqs.starrysky.StarrySkyCommon;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;

public class StarrySkyCommands {

    public static void initialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("sphere")
                .requires((source)->source.hasPermissionLevel(StarrySkyCommon.STARRY_SKY_CONFIG.sphereCommandRequiredPermissionLevel))
                .executes(new ClosestSpheroidCommand())));
    }

}
