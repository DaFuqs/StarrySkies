package de.dafuqs.starryskies.registries;

import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.portal.StarryPortalBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class StarryBlocks {

    private static final DeferredRegister.Blocks REGISTRAR = DeferredRegister.createBlocks(StarrySkies.MOD_ID);

    public static DeferredBlock<StarryPortalBlock> STARRY_PORTAL = REGISTRAR.register("portal", () -> new StarryPortalBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_PORTAL).setId(ResourceKey.create(Registries.BLOCK, StarrySkies.id("portal")))));

    public static void register(IEventBus modBus) {
        REGISTRAR.register(modBus);
    }
}