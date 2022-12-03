package de.dafuqs.starryskies.spheroids.lists;

import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.data_loaders.SpheroidTemplateLoader;
import net.fabricmc.loader.api.FabricLoader;

import static org.apache.logging.log4j.Level.INFO;

public class SpheroidListIndustrialRevolution extends SpheroidList {
	
	private static final String MOD_ID = "indrev";
	
	
	public static boolean shouldGenerate() {
		return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkies.CONFIG.generateIndustrialRevolutionSpheroids;
	}
	
	public static void setup(SpheroidTemplateLoader spheroidLoader) {
		StarrySkies.log(INFO, "Loading Industrial Revolution integration...");

        /*BlockState industrialrevolution_nikolite = getDefaultBlockState(MOD_ID,"nikolite_ore");
        BlockState industrialrevolution_tin = getDefaultBlockState(MOD_ID,"copper_ore");

        spheroidLoader.registerDynamicOre(OVERWORLD, "nikolite", industrialrevolution_nikolite);
        spheroidLoader.registerDynamicOre(OVERWORLD, "tin", industrialrevolution_tin);*/
	}
	
}
