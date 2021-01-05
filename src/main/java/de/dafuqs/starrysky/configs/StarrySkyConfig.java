package de.dafuqs.starrysky.configs;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Config(name = "StarrySky")
public class StarrySkyConfig implements ConfigData {

    // wiki:
    // https://gitlab.com/sargunv-mc-mods/auto-config/-/wikis/home
    @ConfigEntry.Gui.PrefixText()
    @ConfigEntry.Category("GENERAL")
    @Comment(value = "\nThe block the portal to the Starry Sky dimension needs to be built with."
                   + "\nBuild it like a nether portal & has to be activated with flint & steel"
                   + "\nDefault: PACKED_ICE")
    public String starrySkyPortalFrameBlock = "PACKED_ICE";

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("GENERAL")
    @Comment(value = "\nThe height of clouds in the Starry Sky dimension."
                     + "\nDefault: 270")
    public float cloudHeight = 270F;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("GENERAL")
    @Comment(value = "\nUse a fancy rainbow skybox instead of a generic one."
                     + "\nDefault: true")
    public boolean rainbowSkybox = true;

    @ConfigEntry.Category("GENERAL")
    @Comment(value = "\nIf true nether portals in Starry Sky lead to Scary Sky, if false portals do not form."
            + "\nDefault: true")
    public boolean enableNetherPortalsToStarryNether = true;

    @ConfigEntry.Category("GENERAL")
    @Comment(value = "\nIf true end portals in Starry Sky lead to Scarcy Sky, if false to the vanilla end."
            + "\nDefault: true")
    public boolean enableEndPortalsToStarryEnd = true;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("GENERAL")
    @Comment(value = "\nThe '/sphere' command lists all the data of the clostest sphere (position, blocks, ...)\n"
            + "\nDefault: 0")
    public int sphereCommandRequiredPermissionLevel = 0;

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("SYSTEM GENERATION")
    @Comment(value = "\nSpheroids are generated in systems.\n"
            + "Each system consists out of x spheroids over y chunks.\n"
            + "How big each system should be in chunks²\n"
            + "Higher values make the very slight 'gaps' at the border between\n"
            + "systems less common, but since systems are generating all at once\n"
            + "high values can result in small lag spikes every time a new system is generated. (but less spikes in total)\n"
            + "Default: 50")
    public int systemSizeChunks = 50;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("SYSTEM GENERATION")
    @Comment(value = "\nHow many spheres a system can contain max.\n"
            + "Some will fail because of distance restrictions\n"
            + "so it's not an exact value\n"
            + "Default: 2000")
    public int sphereDensityOverworld = 2000;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("SYSTEM GENERATION")
    @Comment(value = "\nHow much empty blocks should be enforced between individual spheres.\n"
            + "If the distance is too low generation of that one sphere will be cancelled.\n"
            + "Default: 10\n")
    public int minDistanceBetweenSpheresOverworld = 10;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("SYSTEM GENERATION")
    @Comment(value = "\nHow many spheres a system can contain max.\n"
            + "Some will fail because of distance restrictions\n"
            + "so it's not an exact value\n"
            + "Default: 2500")
    public int sphereDensityNether = 2500;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("SYSTEM GENERATION")
    @Comment(value = "\nHow much empty blocks should be enforced between individual spheres.\n"
            + "If the distance is too low generation of that one sphere will be cancelled.\n"
            + "Default: 10\n")
    public int minDistanceBetweenSpheresNether = 7;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("SYSTEM GENERATION")
    @Comment(value = "\nHow many spheres a system can contain max.\n"
            + "Some will fail because of distance restrictions\n"
            + "so it's not an exact value\n"
            + "Default: 1500")
    public int sphereDensityEnd = 1500;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("SYSTEM GENERATION")
    @Comment(value = "\nHow much empty blocks should be enforced between individual spheres.\n"
            + "If the distance is too low generation of that one sphere will be cancelled.\n"
            + "Default: 10\n")
    public int minDistanceBetweenSpheresEnd = 8;

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Category("WORLD FLOOR GENERATION")
    @Comment(value = "\nIf (or how high) there should be a world floor.\n"
            + "When set to  1 there will the specified BottomBlock at y=1 and the floor block up to the floor height\n"
            + "When set to >1 there will the specified BottomBlock at y=1\n"
            + "When set to  0 there will be no ground, only void\n"
            + "Default: 3 (2 layers water, 1 layer bedrock)")
    @ConfigEntry.BoundedDiscrete(min = 0, max = 128)
    public int floorHeightOverworld = 3;

    @ConfigEntry.Category("WORLD FLOOR GENERATION")
    @Comment(value = "\nThe block generating at y>1 (if floorHeight > 1)\n"
            + "Default: WATER")
    public String floorBlockOverworld = "WATER";

    @ConfigEntry.Category("WORLD FLOOR GENERATION")
    @Comment(value = "\nThe block generating at y=0 (if floorHeight > 0)\n"
            + "Default: BEDROCK")
    public String bottomBlockOverworld = "BEDROCK";

    @ConfigEntry.Category("WORLD FLOOR GENERATION")
    @Comment(value = "\nIf (or how high) there should be a world floor.\n"
            + "When set to  1 there will the specified BottomBlock at y=1 and the floor block up to the floor height\n"
            + "When set to >1 there will the specified BottomBlock at y=1\n"
            + "When set to  0 there will be no ground, only void\n"
            + "Default: 3 (2 layers water, 1 layer bedrock)")
    @ConfigEntry.BoundedDiscrete(min = 0, max = 128)
    public int floorHeightNether = 3;

    @ConfigEntry.Category("WORLD FLOOR GENERATION")
    @Comment(value = "\nThe block generating at y>1 (if floorHeight > 1)\n"
            + "Default: LAVA")
    public String floorBlockNether = "LAVA";

    @ConfigEntry.Category("WORLD FLOOR GENERATION")
    @Comment(value = "\nThe block generating at y=0 (if floorHeight > 0)\n"
            + "Default: BEDROCK")
    public String bottomBlockNether = "BEDROCK";

    @ConfigEntry.Category("WORLD FLOOR GENERATION")
    @Comment(value = "\nIf (or how high) there should be a world floor.\n"
            + "When set to  1 there will the specified BottomBlock at y=1 and the floor block up to the floor height\n"
            + "When set to >1 there will the specified BottomBlock at y=1\n"
            + "When set to  0 there will be no ground, only void\n"
            + "Default: 0 (only void)")
    @ConfigEntry.BoundedDiscrete(min = 0, max = 128)
    public int floorHeightEnd = 0;

    @ConfigEntry.Category("WORLD FLOOR GENERATION")
    @Comment(value = "\nThe block generating at y>1 (if floorHeight > 1)\n"
            + "Default: WATER")
    public String floorBlockEnd = "WATER";

    @ConfigEntry.Category("WORLD FLOOR GENERATION")
    @Comment(value = "\nThe block generating at y=0 (if floorHeight > 0)\n"
            + "Default: BEDROCK")
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
