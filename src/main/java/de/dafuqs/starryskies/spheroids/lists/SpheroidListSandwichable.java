package de.dafuqs.starryskies.spheroids.lists;

import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.data_loaders.SpheroidTemplateLoader;
import net.fabricmc.loader.api.FabricLoader;

import static org.apache.logging.log4j.Level.INFO;

public class SpheroidListSandwichable {
	
	private static final String MOD_ID = "sandwichable";
	
	public static boolean shouldGenerate() {
		return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkies.CONFIG.generateSandwichableSpheroids;
	}
	
	public static void setup(SpheroidTemplateLoader spheroidLoader) {
		StarrySkies.log(INFO, "Loading Sandwichable integration...");

        /*SpheroidDecorator SANDWICHABLE_SALTY_SAND_DECORATOR = new GroundDecorator(getDefaultBlockState(MOD_ID, "salty_sand"), 0.08F);
        SpheroidDecorator SANDWICHABLE_SHRUB_DECORATOR = new PlantDecorator(getDefaultBlockState(MOD_ID, "shrub"), 0.04F);

        SpheroidListVanilla.SAND.addDecorator(SANDWICHABLE_SALTY_SAND_DECORATOR, 0.2F);
        SpheroidListVanilla.GRASS.addDecorator(SANDWICHABLE_SHRUB_DECORATOR, 0.3F);*/
	}
	
}
