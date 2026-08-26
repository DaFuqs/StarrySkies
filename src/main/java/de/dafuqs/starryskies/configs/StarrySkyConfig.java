package de.dafuqs.starryskies.configs;

import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.neoforged.neoforge.common.*;
import org.apache.commons.lang3.tuple.*;

public class StarrySkyConfig {

	public static final StarrySkyConfig CONFIG;
	public static final ModConfigSpec CONFIG_SPEC;

	static {
		Pair<StarrySkyConfig, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(StarrySkyConfig::new);
		CONFIG = pair.getLeft();
		CONFIG_SPEC = pair.getRight();
	}

	public ModConfigSpec.BooleanValue enableStarryPortal;
	public ModConfigSpec.ConfigValue<String> starrySkiesPortalFrameBlock;
	public ModConfigSpec.ConfigValue<Integer> systemSizeChunks;
	public ModConfigSpec.BooleanValue rainbowSkybox;
	public ModConfigSpec.BooleanValue enableNetherPortalsToStarryNether;
	public ModConfigSpec.BooleanValue enableEndPortalsToStarryEnd;
	public ModConfigSpec.ConfigValue<Integer> locateSphereCommandRequiredPermissionLevel;
	public ModConfigSpec.ConfigValue<Integer> generateSphereCommandRequiredPermissionLevel;
	public ModConfigSpec.ConfigValue<Integer> systemStatisticsCommandRequiredPermissionLevel;

	private StarrySkyConfig(ModConfigSpec.Builder builder) {
		enableStarryPortal = builder
				.comment("Should Starry Skies allow a Portal for Overworld <=> Starry Skies travel")
				.define("enable_starry_portal", true);
		starrySkiesPortalFrameBlock = builder
				.comment("The block the portal to the Starry Sky dimension needs to be built with.\n" +
						"Build it like a nether portal & has to be activated with flint & steel")
                .define("portal_frame_block", "minecraft:packed_ice", name -> name instanceof String string && isValidBlock(string));
		systemSizeChunks = builder
				.comment("The amount of chunks each sphere system spans.\n" +
						"Higher values make spheres spread out farther, having more air in between")
				.define("system_size_chunks", 50);
		rainbowSkybox = builder
				.comment("Use a fancy rainbow skybox instead of a generic one.")
				.define("rainbow_skybox", true);
		enableNetherPortalsToStarryNether = builder
				.comment("If true nether portals in Starry Sky lead to Scary Sky, if false portals do not form.")
				.define("enable_nether_portals_to_starry_nether", true);
		enableEndPortalsToStarryEnd = builder
				.comment("If true end portals in Starry Sky lead to Scarcy Sky, if false to the vanilla end.")
				.define("enable_end_portals_to_starry_end", true);
		locateSphereCommandRequiredPermissionLevel = builder
				.comment("The '/starryskies_locate' command lists all the data of the closest sphere (position, blocks, ...)")
				.define("locate_sphere_command_permission_level", 2);
		generateSphereCommandRequiredPermissionLevel = builder
				.comment("The '/starryskies_generate' command lets users generate new spheres")
				.define("generate_sphere_command_permission_level", 3);
		systemStatisticsCommandRequiredPermissionLevel = builder
				.comment("The '/starryskies_system_statistics' command lists the amount of generated spheres listed by id)")
				.define("system_statistics_command_permission_level", 2);
	}

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

	public Block getPortalFrameBlock() {
		var b = BuiltInRegistries.BLOCK.get(Identifier.parse(starrySkiesPortalFrameBlock.get()));
		if (b.isEmpty())
			return Blocks.PACKED_ICE;
		return b.get().value();
	}

	public BlockBehaviour.StatePredicate getPortalFrame() {
		return (state, level, pos) -> state.is(getPortalFrameBlock());
	}
}
