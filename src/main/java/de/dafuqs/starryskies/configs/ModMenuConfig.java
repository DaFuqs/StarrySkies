package de.dafuqs.starryskies.configs;

import com.terraformersmc.modmenu.api.*;
import me.shedaniel.autoconfig.*;
import net.fabricmc.api.*;

@Environment(EnvType.CLIENT)
public class ModMenuConfig implements ModMenuApi {
	
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> AutoConfig.getConfigScreen(StarrySkyConfig.class, parent).get();
	}
	
}