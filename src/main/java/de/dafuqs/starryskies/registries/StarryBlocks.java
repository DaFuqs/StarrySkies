package de.dafuqs.starryskies.registries;

import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.portal.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

public class StarryBlocks {

    public static Identifier STARRY_PORTAL_ID = StarrySkies.id("portal");
    public static ResourceKey<Block> STARRY_PORTAL_KEY = ResourceKey.create(Registries.BLOCK, STARRY_PORTAL_ID);
    public static Block STARRY_PORTAL = Registry.register(BuiltInRegistries.BLOCK, STARRY_PORTAL_ID, new StarryPortalBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_PORTAL).setId(STARRY_PORTAL_KEY)));

    public static void register() {

    }
}