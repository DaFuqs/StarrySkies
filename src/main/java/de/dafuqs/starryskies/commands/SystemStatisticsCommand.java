package de.dafuqs.starryskies.commands;

import com.mojang.brigadier.*;
import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.configs.*;
import de.dafuqs.starryskies.registries.*;
import de.dafuqs.starryskies.worldgen.*;
import de.dafuqs.starryskies.worldgen.dimension.*;
import net.minecraft.commands.*;
import net.minecraft.commands.arguments.coordinates.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.server.permissions.*;
import org.jspecify.annotations.*;

import java.util.*;

public class SystemStatisticsCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess) {
        dispatcher.register(Commands.literal("starryskies_system_statistics")
                .requires((source) -> source.permissions().hasPermission(new Permission.HasCommandLevel(PermissionLevel.byId(StarrySkyConfig.CONFIG.systemStatisticsCommandRequiredPermissionLevel.get()))))
                .executes(context -> execute(context.getSource(), null))
                .then(Commands.argument("pos", BlockPosArgument.blockPos())
                        .executes(context -> execute(context.getSource(), BlockPosArgument.getLoadedBlockPos(context, "pos")))));
    }

    private static int execute(CommandSourceStack source, @Nullable BlockPos pos) {
        if (pos == null) {
            pos = BlockPos.containing(source.getPosition());
        }

        ServerLevel level = source.getLevel();
        if (!(level.getChunkSource().getGenerator() instanceof StarrySkyChunkGenerator starrySkyChunkGenerator)) {
            return -1;
        }

        SystemGenerator systemGenerator = starrySkyChunkGenerator.getSystemGenerator();
        if (systemGenerator == null) {
            return -1;
        }

        RegistryAccess registryAccess = level.registryAccess();
        Map<Identifier, Integer> spheres = new HashMap<>();
        int total = 0;

        for (Identifier i : registryAccess.lookupOrThrow(StarryRegistryKeys.CONFIGURED_SPHERE).keySet()) {
            spheres.put(i, 0);
        }

        for (PlacedSphere<?> p : systemGenerator.getSystem(level, pos)) {
            total++;
            Identifier sphereId = p.getID(registryAccess);
            spheres.put(sphereId, spheres.getOrDefault(sphereId, 0) + 1);
        }

        StarrySkies.LOGGER.info("System Statistics:");
        for (Map.Entry<Identifier, Integer> entry : spheres.entrySet()) {
            StarrySkies.LOGGER.info(entry.getKey() + "," + entry.getValue());
        }

        return total;
    }


}