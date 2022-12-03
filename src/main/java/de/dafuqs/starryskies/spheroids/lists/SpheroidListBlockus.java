package de.dafuqs.starryskies.spheroids.lists;

import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.data_loaders.SpheroidTemplateLoader;
import net.fabricmc.loader.api.FabricLoader;

import static org.apache.logging.log4j.Level.INFO;

public class SpheroidListBlockus extends SpheroidList {
	
	private static final String MOD_ID = "blockus";
	
	
	public static boolean shouldGenerate() {
		return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkies.CONFIG.generateBlockusSpheroids;
	}
	
	public static void setup(SpheroidTemplateLoader spheroidLoader) {
		StarrySkies.log(INFO, "Loading Blockus integration...");

        /*BlockState blockus_limestone = getDefaultBlockState(MOD_ID,"limestone");
        BlockState blockus_marble = getDefaultBlockState(MOD_ID,"marble");
        BlockState blockus_bluestone = getDefaultBlockState(MOD_ID,"bluestone");
        BlockState blockus_white_oak_leaves = getDefaultBlockState(MOD_ID,"white_oak_leaves");
        BlockState blockus_white_oak_log = getDefaultBlockState(MOD_ID,"white_oak_log");

        ArrayList<BlockState> rainbowBeveled = new ArrayList<>() {{
            add(getDefaultBlockState(MOD_ID,"black_beveled_glass"));
            add(getDefaultBlockState(MOD_ID,"blue_beveled_glass"));
            add(getDefaultBlockState(MOD_ID,"brown_beveled_glass"));
            add(getDefaultBlockState(MOD_ID,"cyan_beveled_glass"));
            add(getDefaultBlockState(MOD_ID,"gray_beveled_glass"));
            add(getDefaultBlockState(MOD_ID,"green_beveled_glass"));
            add(getDefaultBlockState(MOD_ID,"light_blue_beveled_glass"));
            add(getDefaultBlockState(MOD_ID,"light_gray_beveled_glass"));
            add(getDefaultBlockState(MOD_ID,"lime_beveled_glass"));
            add(getDefaultBlockState(MOD_ID,"magenta_beveled_glass"));
            add(getDefaultBlockState(MOD_ID,"orange_beveled_glass"));
            add(getDefaultBlockState(MOD_ID,"pink_beveled_glass"));
            add(getDefaultBlockState(MOD_ID,"purple_beveled_glass"));
            add(getDefaultBlockState(MOD_ID,"red_beveled_glass"));
            add(getDefaultBlockState(MOD_ID,"white_beveled_glass"));
            add(getDefaultBlockState(MOD_ID,"yellow_beveled_glass"));
        }};

        ArrayList<BlockState> rainbowAsphalt = new ArrayList<>() {{
            add(getDefaultBlockState(MOD_ID,"asphalt"));
            add(getDefaultBlockState(MOD_ID,"blue_asphalt"));
            add(getDefaultBlockState(MOD_ID,"brown_asphalt"));
            add(getDefaultBlockState(MOD_ID,"cyan_asphalt"));
            add(getDefaultBlockState(MOD_ID,"gray_asphalt"));
            add(getDefaultBlockState(MOD_ID,"green_asphalt"));
            add(getDefaultBlockState(MOD_ID,"light_blue_asphalt"));
            add(getDefaultBlockState(MOD_ID,"light_gray_asphalt"));
            add(getDefaultBlockState(MOD_ID,"lime_asphalt"));
            add(getDefaultBlockState(MOD_ID,"magenta_asphalt"));
            add(getDefaultBlockState(MOD_ID,"orange_asphalt"));
            add(getDefaultBlockState(MOD_ID,"pink_asphalt"));
            add(getDefaultBlockState(MOD_ID,"purple_asphalt"));
            add(getDefaultBlockState(MOD_ID,"red_asphalt"));
            add(getDefaultBlockState(MOD_ID,"white_asphalt"));
            add(getDefaultBlockState(MOD_ID,"yellow_asphalt"));
        }};

        ArrayList<BlockState> rainbowFuturneo = new ArrayList<>() {{
            add(getDefaultBlockState(MOD_ID,"black_futurneo_block"));
            add(getDefaultBlockState(MOD_ID,"blue_futurneo_block"));
            add(getDefaultBlockState(MOD_ID,"brown_futurneo_block"));
            add(getDefaultBlockState(MOD_ID,"cyan_futurneo_block"));
            add(getDefaultBlockState(MOD_ID,"gray_futurneo_block"));
            add(getDefaultBlockState(MOD_ID,"green_futurneo_block"));
            add(getDefaultBlockState(MOD_ID,"light_blue_futurneo_block"));
            add(getDefaultBlockState(MOD_ID,"light_gray_futurneo_block"));
            add(getDefaultBlockState(MOD_ID,"lime_futurneo_block"));
            add(getDefaultBlockState(MOD_ID,"magenta_futurneo_block"));
            add(getDefaultBlockState(MOD_ID,"orange_futurneo_block"));
            add(getDefaultBlockState(MOD_ID,"pink_futurneo_block"));
            add(getDefaultBlockState(MOD_ID,"purple_futurneo_block"));
            add(getDefaultBlockState(MOD_ID,"red_futurneo_block"));
            add(getDefaultBlockState(MOD_ID,"white_futurneo_block"));
            add(getDefaultBlockState(MOD_ID,"yellow_futurneo_block"));
        }};

        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.1F, new RainbowSpheroidType(null, 7, 12, rainbowFuturneo));
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.1F, new RainbowSpheroidType(null, 7, 12, rainbowBeveled));
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.1F, new RainbowSpheroidType(null, 7, 12, rainbowAsphalt));
        spheroidLoader.registerSpheroidType(OVERWORLD, SpheroidDistributionType.DECORATIVE, 0.3F, new ShellSpheroidType(null, 7, 14, blockus_white_oak_log, blockus_white_oak_leaves.with(Properties.DISTANCE_1_7, 1),2, 4));

        // Add blocks to default lists
        SpheroidList.MAP_STONES.put(blockus_limestone, 0.5F);
        SpheroidList.MAP_STONES.put(blockus_marble, 0.5F);
        SpheroidList.MAP_STONES.put(blockus_bluestone, 0.5F);*/
	}
	
}
