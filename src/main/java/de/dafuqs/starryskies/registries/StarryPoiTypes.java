package de.dafuqs.starryskies.registries;

import com.google.common.collect.ImmutableSet;
import de.dafuqs.starryskies.StarrySkies;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;

public class StarryPoiTypes {

    private static final DeferredRegister<PoiType> REGISTRAR = DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, StarrySkies.MOD_ID);

    public static final ResourceKey<PoiType> STARRY_PORTAL = createKey("starry_portal");

    private static ResourceKey<PoiType> createKey(String name) {
        return ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, Identifier.withDefaultNamespace(name));
    }

    public static void register(IEventBus modBus) {
        REGISTRAR.register(modBus);
    }

    public static void bootstrap(Registry<PoiType> registry) {
        //register(registry, STARRY_PORTAL, getBlockStates(StarryBlocks.STARRY_PORTAL.get()), 0, 1);
    }

    private static Set<BlockState> getBlockStates(Block block) {
        return ImmutableSet.copyOf(block.getStateDefinition().getPossibleStates());
    }

    /*private static void register(Registry<PoiType> registry, ResourceKey<PoiType> id, Set<BlockState> matchingStates, int maxTickets, int validRange) {
        PoiType value = new PoiType(matchingStates, maxTickets, validRange);
        //registerBlockStates(registry.getOrThrow(id), matchingStates);
    }*/

}