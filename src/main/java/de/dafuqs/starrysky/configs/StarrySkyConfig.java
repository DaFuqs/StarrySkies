package de.dafuqs.starrysky.configs;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

@Config(name = "StarrySky")
public class StarrySkyConfig implements ConfigData {

    // wiki:
    // https://gitlab.com/sargunv-mc-mods/auto-config/-/wikis/home

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

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("SYSTEM GENERATION")
    @Comment(value = "\nHow many spheres a system can contain max.\n"
            + "Some will fail because of distance restrictions\n"
            + "so it's not an exact value\n"
            + "Default: 1250")
    public int sphereDensity = 1250;

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("SYSTEM GENERATION")
    @Comment(value = "\nHow much empty blocks should be enforced between individual spheres.\n"
            + "If the distance is too low generation of that one sphere will be cancelled.\n"
            + "Default: 10\n")
    public int minDistanceBetweenSpheres = 10;

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("WORLD FLOOR GENERATION")
    @Comment(value = "\nIf (or how high) there should be a world floor.\n"
            + "When set to  1 there will the specified BottomBlock at y=1 and the floor block up to the floor height\n"
            + "When set to >1 there will the specified BottomBlock at y=1\n"
            + "When set to  0 there will be no ground, only void\n"
            + "Default: 3 (2 layers water, 1 layer bedrock)")
    @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
    public int floorHeight = 3;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("WORLD FLOOR GENERATION")
    @Comment(value = "\nThe block generating at y>1 (if floorHeight > 1)\n"
            + "Default: WATER")
    public String floorBlock = "WATER";

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("WORLD FLOOR GENERATION")
    @Comment(value = "\nThe block generating at y=0 (if floorHeight > 0)\n"
            + "Default: BEDROCK")
    public String bottomBlock = "BEDROCK";

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("COMMANDS")
    @Comment(value = "\nThe '/sphere' command lists all the data of the clostest sphere (position, blocks, ...)\n"
                   + "Default: 0 (OP)")
    public int sphereCommandRequiredPermissionLevel = 0;

}
