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

import java.util.Optional;

import static org.apache.logging.log4j.Level.DEBUG;

public class ProximityAdvancementCheckEvent implements ServerTickEvents.EndTick {

    // Advancements
    private int tickCounter;
    private final int advancementsEveryXTicks = 100;
    private final SpheroidAdvancementIdentifierGroups spheroidAdvancementIdentifierGroups;

    public ProximityAdvancementCheckEvent() {
        this.spheroidAdvancementIdentifierGroups = new SpheroidAdvancementIdentifierGroups();
    }

    @Override
    public void onEndTick(MinecraftServer minecraftServer) {
        tickCounter++;
        if(tickCounter % advancementsEveryXTicks == 0) {
            tickCounter = 0;
            StarrySkyCommon.log(DEBUG, "Advancement check start. Players: " + minecraftServer.getPlayerManager().getCurrentPlayerCount());
            for (ServerPlayerEntity serverPlayerEntity : minecraftServer.getPlayerManager().getPlayerList()) {
                StarrySkyCommon.log(DEBUG, "Checking player " +serverPlayerEntity.getEntityName());
                if(StarrySkyCommon.inStarryWorld(serverPlayerEntity)) {
                    StarrySkyCommon.log(DEBUG, "In starry world");
                    Optional<Support.SpheroidDistance> spheroidDistance = Support.getClosestSpheroidToPlayer(serverPlayerEntity);
                    if(spheroidDistance.isPresent() && (Math.sqrt(spheroidDistance.get().squaredDistance)) < spheroidDistance.get().spheroid.getRadius() + 2) {
                        SpheroidAdvancementIdentifier spheroidAdvancementIdentifier = spheroidDistance.get().spheroid.getSpheroidAdvancementIdentifier();

                        if(spheroidAdvancementIdentifier != null) {
                            StarrySkyCommon.log(DEBUG, "AdvancementIdentifier: " + spheroidAdvancementIdentifier.name());
                            SpheroidAdvancementGroup spheroidAdvancementGroup = spheroidAdvancementIdentifierGroups.spheroidAdvancementIdentifierGroups.get(spheroidAdvancementIdentifier);

                            if(spheroidAdvancementGroup != null) {
                                String groupAdvancementString = "sphere_group_" + spheroidAdvancementGroup.name().toLowerCase();
                                String identifierAdvancementString = "sphere_" + spheroidAdvancementIdentifier.name().toLowerCase();
    
                                ServerAdvancementLoader sal = minecraftServer.getAdvancementLoader();
                                PlayerAdvancementTracker tracker = serverPlayerEntity.getAdvancementTracker();
    
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
                            }
                        } else {
                            StarrySkyCommon.log(DEBUG, "No advancementIdentifier :(...");
                        }
                    }
                }
            }
        }
    }

}
