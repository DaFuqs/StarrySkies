package de.dafuqs.starrysky.advancements;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.Support;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import static de.dafuqs.starrysky.StarrySkyCommon.starryWorld;

public class ProximityAchivementCheckEvent implements ServerTickEvents.EndTick {

    // Advancements
    private int tickCounter;
    private final int advancementsEveryXTicks = 100;
    private final SpheroidAdvancementIdentifierGroups spheroidAdvancementIdentifierGroups;

    public ProximityAchivementCheckEvent() {
        this.spheroidAdvancementIdentifierGroups = new SpheroidAdvancementIdentifierGroups();
    }

    @Override
    public void onEndTick(MinecraftServer minecraftServer) {
        tickCounter++;
        if(tickCounter % advancementsEveryXTicks == 0) {
            tickCounter = 0;
            StarrySkyCommon.LOGGER.debug("Advancement check start. Players: " + minecraftServer.getPlayerManager().getCurrentPlayerCount());
            for (ServerPlayerEntity serverPlayerEntity : minecraftServer.getPlayerManager().getPlayerList()) {
                StarrySkyCommon.LOGGER.debug("checking player " +serverPlayerEntity.getEntityName());
                if(serverPlayerEntity.getEntityWorld().equals(starryWorld)) {
                    StarrySkyCommon.LOGGER.debug( "In starry world");
                    Support.SpheroidDistance spheroidDistance = Support.getClosestSpheroidToPlayer(serverPlayerEntity);
                    if(spheroidDistance.spheroid != null && (Math.sqrt(spheroidDistance.distance)) < spheroidDistance.spheroid.getRadius() + 2) {
                        SpheroidAdvancementIdentifier spheroidAdvancementIdentifier = spheroidDistance.spheroid.getSpheroidType().getSpheroidTypeIdentifier();

                        if(spheroidAdvancementIdentifier != null) {
                            StarrySkyCommon.LOGGER.debug("AdvancementIdentifier: " + spheroidAdvancementIdentifier.name());
                            SpheroidAdvancementGroup spheroidAdvancementGroup = spheroidAdvancementIdentifierGroups.spheroidAdvancementIdentifierGroups.get(spheroidAdvancementIdentifier);

                            String groupAdvancementString = "sphere_group_" + spheroidAdvancementGroup.name().toLowerCase();
                            String identifierAdvancementString = "sphere_" + spheroidAdvancementIdentifier.name().toLowerCase();

                            ServerAdvancementLoader sal = minecraftServer.getAdvancementLoader();
                            PlayerAdvancementTracker tracker = serverPlayerEntity .getAdvancementTracker();

                            // grant group advancement
                            Identifier advancementIdentifier = new Identifier(StarrySkyCommon.MOD_ID, groupAdvancementString);
                            Advancement advancement = sal.get(advancementIdentifier);
                            if (advancement != null) {
                                tracker.grantCriterion(advancement, "seen");
                            }

                            // grant identifier advancement
                            advancementIdentifier = new Identifier(StarrySkyCommon.MOD_ID, identifierAdvancementString);
                            advancement = sal.get(advancementIdentifier);
                            if (advancement != null) {
                                tracker.grantCriterion(advancement, "seen");
                            }
                        } else {
                            StarrySkyCommon.LOGGER.debug("No advancementIdentifier :(...");
                        }
                    }
                }
            }
        }
    }

}
