package de.dafuqs.starrysky.configs;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Config(name = "StarrySky")
public class StarrySkyConfig implements ConfigData {

    @ConfigEntry.Gui.PrefixText()
    @ConfigEntry.Category("GENERAL")
    @Comment(value = """
            
            Should there be a portal between overworld and Starry Sky?
            Build it like a nether portal & has to be activated with flint & steel
            Once in Starry Sky you can access Scary Sky and Scarcy Sky like their vanilla counterparts.
            Default: true""")
    public boolean portalToStarrySky = true;

    @Comment(value = """
            
            The block for building the portal to Starry Sky
            Default: PACKED_ICE""")
    public String starrySkyPortalFrameBlock = "PACKED_ICE";

    @ConfigEntry.Category("GENERAL")
    @Comment(value = """
            
            The height of clouds in the Starry Sky dimension.
            Default: 270""")
    public float cloudHeight = 270F;

    @ConfigEntry.Category("GENERAL")
    @Comment(value = """
            
            Use a fancy rainbow skybox instead of a generic one.
            Default: true""")
    public boolean rainbowSkybox = true;

    @ConfigEntry.Category("GENERAL")
    @Comment(value = """
            
            If true nether portals in Starry Sky lead to Scary Sky, if false portals do not form.
            Default: true""")
    public boolean enableNetherPortalsToStarryNether = true;

    @ConfigEntry.Category("GENERAL")
    @Comment(value = """
            
            If true end portals in Starry Sky lead to Scarcy Sky, if false to the vanilla end.
            Default: true""")
    public boolean enableEndPortalsToStarryEnd = true;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("GENERAL")
    @Comment(value = """
            
            The '/sphere' command lists all the data of the closest sphere (position, blocks, ...)
            Default: 0""")
    public int sphereCommandRequiredPermissionLevel = 0;

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("SYSTEM GENERATION")
    @Comment(value = """
            
            Spheroids are generated in systems.
            Each system consists out of x spheroids over y chunks.
            How big each system should be in chunks²
            Higher values make the very slight 'gaps' at the border between
            systems less common, but since systems are generating all at once
            high values can result in small lag spikes every time a new system is generated. (but less spikes in total)
            Default: 50""")
    public int systemSizeChunks = 50;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("SYSTEM GENERATION")
    @Comment(value = """
            
            How many spheres a system can contain max.
            Some will fail because of distance restrictions
            so it's not an exact value
            Default: 2000""")
    public int sphereDensityOverworld = 2000;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("SYSTEM GENERATION")
    @Comment(value = """
            
            How much empty blocks should be enforced between individual spheres.
            If the distance is too low generation of that one sphere will be cancelled.
            Default: 10
            """)
    public int minDistanceBetweenSpheresOverworld = 10;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("SYSTEM GENERATION")
    @Comment(value = """
            
            How many spheres a system can contain max.
            Some will fail because of distance restrictions
            so it's not an exact value
            Default: 2500""")
    public int sphereDensityNether = 2500;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("SYSTEM GENERATION")
    @Comment(value = """
            
            How much empty blocks should be enforced between individual spheres.
            If the distance is too low generation of that one sphere will be cancelled.
            Default: 10
            """)
    public int minDistanceBetweenSpheresNether = 7;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("SYSTEM GENERATION")
    @Comment(value = """
            
            How many spheres a system can contain max.
            Some will fail because of distance restrictions
            so it's not an exact value
            Default: 1500""")
    public int sphereDensityEnd = 1500;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("SYSTEM GENERATION")
    @Comment(value = """
            
            How much empty blocks should be enforced between individual spheres.
            If the distance is too low generation of that one sphere will be cancelled.
            Default: 10
            """)
    public int minDistanceBetweenSpheresEnd = 8;

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Category("WORLD FLOOR GENERATION")
    @Comment(value = """
            
            If (or how high) there should be a world floor.
            When set to  1 there will the specified BottomBlock at y=1 and the floor block up to the floor height
            When set to >1 there will the specified BottomBlock at y=1
            When set to  0 there will be no ground, only void
            Default: 3 (2 layers water, 1 layer bedrock)""")
    @ConfigEntry.BoundedDiscrete(min = 0, max = 128)
    public int floorHeightOverworld = 3;

    @ConfigEntry.Category("WORLD FLOOR GENERATION")
    @Comment(value = """
            
            The block generating at y>1 (if floorHeight > 1)
            Default: WATER""")
    public String floorBlockOverworld = "WATER";

    @ConfigEntry.Category("WORLD FLOOR GENERATION")
    @Comment(value = """
            
            The block generating at y=0 (if floorHeight > 0)
            Default: BEDROCK""")
    public String bottomBlockOverworld = "BEDROCK";

    @ConfigEntry.Category("WORLD FLOOR GENERATION")
    @Comment(value = """
            
            If (or how high) there should be a world floor.
            When set to  1 there will the specified BottomBlock at y=1 and the floor block up to the floor height
            When set to >1 there will the specified BottomBlock at y=1
            When set to  0 there will be no ground, only void
            Default: 3 (2 layers water, 1 layer bedrock)""")
    @ConfigEntry.BoundedDiscrete(min = 0, max = 128)
    public int floorHeightNether = 3;

    @ConfigEntry.Category("WORLD FLOOR GENERATION")
    @Comment(value = """
            
            The block generating at y>1 (if floorHeight > 1)
            Default: LAVA""")
    public String floorBlockNether = "LAVA";

    @ConfigEntry.Category("WORLD FLOOR GENERATION")
    @Comment(value = """
            
            The block generating at y=0 (if floorHeight > 0)
            Default: BEDROCK""")
    public String bottomBlockNether = "BEDROCK";

    @ConfigEntry.Category("WORLD FLOOR GENERATION")
    @Comment(value = """
            
            If (or how high) there should be a world floor.
            When set to  1 there will the specified BottomBlock at y=1 and the floor block up to the floor height
            When set to >1 there will the specified BottomBlock at y=1
            When set to  0 there will be no ground, only void
            Default: 0 (only void)""")
    @ConfigEntry.BoundedDiscrete(min = 0, max = 128)
    public int floorHeightEnd = 0;

    @ConfigEntry.Category("WORLD FLOOR GENERATION")
    @Comment(value = """
            
            The block generating at y>1 (if floorHeight > 1)
            Default: WATER""")
    public String floorBlockEnd = "WATER";

    @ConfigEntry.Category("WORLD FLOOR GENERATION")
    @Comment(value = """
            
            The block generating at y=0 (if floorHeight > 0)
            Default: BEDROCK""")
    public String bottomBlockEnd = "BEDROCK";

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Category("SPHEROIDS")
    @Comment(value = "\nGenerate Pride Spheroids")
    public boolean generatePrideSpheroids = false;

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Category("SPHEROIDS")
    @Comment(value = "\nEnable Astromine Integration")
    public boolean generateAstromineSpheroids = true;

    @ConfigEntry.Category("SPHEROIDS")
    @Comment(value = "\nEnable Industrial Revolution Integration")
    public boolean generateIndustrialRevolutionSpheroids = true;

    @ConfigEntry.Category("SPHEROIDS")
    @Comment(value = "\nEnable Applied Energistics 2 Integration")
    public boolean generateAppliedEnergistics2Spheroids = true;

    @ConfigEntry.Category("SPHEROIDS")
    @Comment(value = "\nEnable Modern Industrialization Integration")
    public boolean generateModernIndustrializationSpheroids = true;

    @ConfigEntry.Category("SPHEROIDS")
    @Comment(value = "\nEnable Tech Reborn Integration")
    public boolean generateTechRebornSpheroids = true;

    @ConfigEntry.Category("SPHEROIDS")
    @Comment(value = "\nEnable Sakura Rosea Integration")
    public boolean generateSakuraRoseaSpheroids = true;

    @ConfigEntry.Category("SPHEROIDS")
    @Comment(value = "\nEnable Terrestria Integration")
    public boolean generateTerrestriaSpheroids = true;

    @ConfigEntry.Category("SPHEROIDS")
    @Comment(value = "\nEnable Traverse Integration")
    public boolean generateTraverseSpheroids = true;

    @ConfigEntry.Category("SPHEROIDS")
    @Comment(value = "\nEnable Sandwichable Integration")
    public boolean generateSandwichableSpheroids = true;

    @ConfigEntry.Category("SPHEROIDS")
    @Comment(value = "\nEnable Biomes You Go Integration")
    public boolean generateBYGSpheroids = true;

    @ConfigEntry.Category("SPHEROIDS")
    @Comment(value = "\nEnable Unearthed Integration")
    public boolean generateUnearthedSpheroids = true;

    @ConfigEntry.Category("SPHEROIDS")
    @Comment(value = "\nEnable Blockus Integration")
    public boolean generateBlockusSpheroids = true;

    @ConfigEntry.Category("SPHEROIDS")
    @Comment(value = "\nEnable Mythic Metals Integration")
    public boolean generateMythicMetalsSpheroids = true;

    @ConfigEntry.Category("SPHEROIDS")
    @Comment(value = "\nEnable BetterNether Integration")
    public boolean generateBetterNetherSpheroids = true;

    @ConfigEntry.Category("SPHEROIDS")
    @Comment(value = "\nEnable BetterEnd Integration")
    public boolean generateBetterEndSpheroids = true;

    @ConfigEntry.Category("SPHEROIDS")
    @Comment(value = "\nEnable Cinderscapes Integration")
    public boolean generateCinderscapesSpheroids = true;

    @ConfigEntry.Category("SPHEROIDS")
    @Comment(value = "\nEnable Ecotones Integration")
    public boolean generateEcotonesSpheroids = true;

    @ConfigEntry.Category("SPHEROIDS")
    @Comment(value = "\nEnable Woods And Mires Integration")
    public boolean generateWoodsAndMiresSpheroids = true;

    @ConfigEntry.Category("SPHEROIDS")
    @Comment(value = "\nEnable Biome Makeover Integration")
    public boolean generateBiomeMakeoverSpheroids = true;

    private boolean isValidBlock(String blockName) {
        // validate floorBlock
        try {
            Identifier identifier = new Identifier(blockName.toLowerCase());
            BlockState bs = Registry.BLOCK.get(identifier).getDefaultState();
            if(bs == null) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void validatePostLoad() {

        // do all the blocks exist?
        // if not: use defaults

        // floor blocks
        if(!isValidBlock(floorBlockOverworld)) {
            floorBlockOverworld = "WATER";
        }
        if(!isValidBlock(floorBlockNether)) {
            floorBlockNether = "LAVA";
        }
        if(!isValidBlock(floorBlockEnd)) {
            floorBlockEnd = "WATER";
        }

        // bottom blocks
        if(!isValidBlock(bottomBlockOverworld)) {
            bottomBlockOverworld = "BEDROCK";
        }
        if(!isValidBlock(bottomBlockNether)) {
            bottomBlockNether = "BEDROCK";
        }
        if(!isValidBlock(bottomBlockEnd)) {
            bottomBlockEnd = "BEDROCK";
        }

        // portal frame blocks
        if(!isValidBlock(starrySkyPortalFrameBlock)) {
            starrySkyPortalFrameBlock = "PACKED_ICE";
        }

    }

}
