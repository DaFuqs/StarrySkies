package de.dafuqs.starryskies.registries;

import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.portal.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

public class StarryBlocks {

    private static final DeferredRegister.Blocks REGISTRAR = DeferredRegister.createBlocks(StarrySkies.MOD_ID);

    public static DeferredBlock<StarryPortalBlock> STARRY_PORTAL = REGISTRAR.register("portal", () -> new StarryPortalBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_PORTAL).setId(ResourceKey.create(Registries.BLOCK, StarrySkies.id("portal")))));

    public static void register(IEventBus modBus) {
        REGISTRAR.register(modBus);
    }
}