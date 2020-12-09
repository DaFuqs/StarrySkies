package de.dafuqs.starrysky.spheroid.lists;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.dimension.SpheroidDistributionType;
import de.dafuqs.starrysky.dimension.SpheroidLoader;
import de.dafuqs.starrysky.dimension.decorators.end.EndCrystalDecorator;
import de.dafuqs.starrysky.dimension.decorators.end.EndPortalDecorator;
import de.dafuqs.starrysky.spheroid.SpheroidEntitySpawnDefinitions;
import de.dafuqs.starrysky.spheroid.types.*;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootTables;

import static de.dafuqs.starrysky.dimension.SpheroidLoader.SpheroidDimensionType.END;

public class SpheroidListVanillaEnd extends SpheroidList {

    public static class SpheroidDecorators {
        public static SpheroidDecorator END_CRYSTAL = new EndCrystalDecorator();
        public static SpheroidDecorator END_PORTAL = new EndPortalDecorator();
    }

    // DRAGON FIGHT
    // Not in list but only generated directly
    public static final SpheroidType END_SPAWN_WITH_PORTAL_AND_DRAGON = new ModularSpheroidType(SpheroidAdvancementIdentifier.end_stone, 34, 34,  Blocks.END_STONE.getDefaultState())
            .addDecorator(SpheroidDecorators.END_PORTAL, 1.0F)
            .addSpawn(SpheroidEntitySpawnDefinitions.ENDER_DRAGON, 1.0F);
    public static final SpheroidType END_SPAWN_WITH_PORTAL = new ModularSpheroidType(SpheroidAdvancementIdentifier.end_stone, 34, 34,  Blocks.END_STONE.getDefaultState())
            .addDecorator(SpheroidDecorators.END_PORTAL, 1.0F);
    public static final SpheroidType END_CRYSTAL = new ModularSpheroidType(SpheroidAdvancementIdentifier.end_stone, 7, 14,  Blocks.END_STONE.getDefaultState())
            .addDecorator(SpheroidDecorators.END_CRYSTAL, 1.0F);

    // SIMPLE
    public static final SpheroidType END_STONE_SMALL = new ModularSpheroidType(SpheroidAdvancementIdentifier.end_stone, 4, 7,  Blocks.END_STONE.getDefaultState());
    public static final SpheroidType END_STONE_LARGE = new ModularSpheroidType(SpheroidAdvancementIdentifier.end_stone, 10, 20,  Blocks.END_STONE.getDefaultState());
    public static final SpheroidType PURPLE_STAINED_GLASS = new ModularSpheroidType(SpheroidAdvancementIdentifier.stained_glass, 5, 8, Blocks.PURPLE_STAINED_GLASS.getDefaultState());

    // TREASURE
    public static final SpheroidType END_GATEWAY = new EndGatewaySpheroidType(SpheroidAdvancementIdentifier.end_gateway, 7, 10);
    public static final SpheroidType HUGE_MONSTER_CAVE = new CaveSpheroidType(SpheroidAdvancementIdentifier.cave, 20, 30, Blocks.END_STONE.getDefaultState(), Blocks.END_STONE_BRICKS.getDefaultState(), 2, 5)
            .addChestWithLootTable(LootTables.END_CITY_TREASURE_CHEST, 0.3F);
    public static final SpheroidType MONSTER_CAVE = new CaveSpheroidType(SpheroidAdvancementIdentifier.cave, 10, 15, Blocks.END_STONE.getDefaultState(), Blocks.END_STONE_BRICKS.getDefaultState(), 1, 3)
            .addChestWithLootTable(LootTables.END_CITY_TREASURE_CHEST, 0.1F);

    // DUNGEON
    public static final SpheroidType DUNGEON_ENDERMAN = new DungeonSpheroidType(SpheroidAdvancementIdentifier.end_dungeon, 6, 12,  EntityType.ENDERMAN, MAP_END_DUNGEON_STONES, 2,  4);
    public static final SpheroidType DUNGEON_ENDERMITE = new DungeonSpheroidType(SpheroidAdvancementIdentifier.end_dungeon, 6, 12,  EntityType.ENDERMITE,  MAP_END_DUNGEON_STONES, 2,  4);
    public static final SpheroidType DUNGEON_SHULKER = new DungeonSpheroidType(SpheroidAdvancementIdentifier.end_dungeon, 6, 12,  EntityType.SHULKER,  MAP_END_DUNGEON_STONES, 2,  4);
    public static final SpheroidType DUNGEON_PHANTOM = new DungeonSpheroidType(SpheroidAdvancementIdentifier.end_dungeon, 6, 12,  EntityType.PHANTOM,  MAP_END_DUNGEON_STONES, 2,  4);

    public static void setup(SpheroidLoader spheroidLoader) {
        StarrySkyCommon.LOGGER.info("Loading Vanilla End Spheroids...");

        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.ESSENTIAL, 10.0F, END_STONE_SMALL);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.ESSENTIAL, 10.0F, END_STONE_LARGE);

        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DECORATIVE, 10.0F, PURPLE_STAINED_GLASS);

        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.TREASURE, 8.0F, MONSTER_CAVE);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.TREASURE, 1.0F, HUGE_MONSTER_CAVE);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.TREASURE, 1.0F, END_GATEWAY);

        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DUNGEON, 10.0F, DUNGEON_ENDERMAN);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DUNGEON, 1.0F, DUNGEON_ENDERMITE);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DUNGEON, 0.5F, DUNGEON_SHULKER);
        spheroidLoader.registerSpheroidType(END, SpheroidDistributionType.DUNGEON, 0.5F, DUNGEON_PHANTOM);
    }

}