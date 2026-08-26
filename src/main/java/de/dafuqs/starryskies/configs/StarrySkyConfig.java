package de.dafuqs.starryskies.configs;

import me.shedaniel.autoconfig.*;
import me.shedaniel.autoconfig.annotation.*;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

@Config(name = "StarrySky")
public class StarrySkyConfig implements ConfigData {

	@ConfigEntry.Category("GENERAL")
	@Comment(value = """
			Should Starry generate a Portal for Overworld <=> Starry Skies travel""")
	public boolean enableStarryPortal = true;

	@ConfigEntry.Gui.PrefixText()
	@ConfigEntry.Category("GENERAL")
	@Comment(value = """
			The block the portal to the Starry Sky dimension needs to be built with.
			Build it like a nether portal & has to be activated with flint & steel
			Default: PACKED_ICE""")
	public String starrySkyPortalFrameBlock = "PACKED_ICE";

	@ConfigEntry.Category("GENERAL")
	@Comment(value = """
			The amount of chunks each sphere system spans.
			Higher values make spheres spread out farther, having more air in between
			Default: 50""")
	public int systemSizeChunks = 50;

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
			The '/starryskies_locate' command lists all the data of the closest sphere (position, blocks, ...)
			Default: 2""")
	public int locateSphereCommandRequiredPermissionLevel = 2;

	@ConfigEntry.Gui.Tooltip()
	@ConfigEntry.Category("GENERAL")
	@Comment(value = """
			The '/starryskies_system_statistics' command lists the amount of generated spheres listed by id)
			Default: 2""")
	public int systemStatisticsCommandRequiredPermissionLevel = 2;

	@ConfigEntry.Gui.Tooltip()
	@ConfigEntry.Category("GENERAL")
	@Comment(value = """
			The '/starryskies_generate' command lets users generate new spheres
			Default: 4""")
	public int generateSphereCommandRequiredPermissionLevel = 4;

	private boolean isValidBlock(String blockName) {
		// validate floorBlock
		try {
			Identifier identifier = Identifier.tryParse(blockName.toLowerCase());
			if (identifier == null || BuiltInRegistries.BLOCK.getOptional(identifier).isEmpty())
				return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public void validatePostLoad() {
		// portal frame blocks
		if (!isValidBlock(starrySkyPortalFrameBlock)) {
			starrySkyPortalFrameBlock = "PACKED_ICE";
		}
	}

	public Block getPortalFrameBlock() {
		Identifier portalFrameBlockIdentifier = Identifier.tryParse(starrySkyPortalFrameBlock.toLowerCase());
		return BuiltInRegistries.BLOCK.getValue(portalFrameBlockIdentifier);
	}

	public BlockBehaviour.StatePredicate getPortalFrame() {
		return (state, level, pos) -> state.is(getPortalFrameBlock());
	}

}
