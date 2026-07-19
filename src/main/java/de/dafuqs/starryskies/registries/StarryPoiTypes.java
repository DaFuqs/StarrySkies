package de.dafuqs.starryskies.registries;

import com.google.common.collect.*;
import de.dafuqs.starryskies.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.ai.village.poi.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

import java.util.*;

public class StarryPoiTypes {

    public static final ResourceKey<PoiType> STARRY_PORTAL = createKey("starry_portal");

    private static ResourceKey<PoiType> createKey(String name) {
        return ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, StarrySkies.id(name));
    }

    // has to be registered late, after the blocks have been initialized
    public static void register() {
        PoiTypes.register(BuiltInRegistries.POINT_OF_INTEREST_TYPE, STARRY_PORTAL, getBlockStates(StarryBlocks.STARRY_PORTAL), 0, 1);
    }

    private static Set<BlockState> getBlockStates(Block block) {
        return ImmutableSet.copyOf(block.getStateDefinition().getPossibleStates());
    }

}