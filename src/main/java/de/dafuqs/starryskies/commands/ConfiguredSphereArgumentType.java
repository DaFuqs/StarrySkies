package de.dafuqs.starryskies.commands;

import de.dafuqs.starryskies.registries.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.command.*;
import net.minecraft.command.argument.*;

public class ConfiguredSphereArgumentType extends RegistryEntryArgumentType<ConfiguredSphere<?, ?>> {
	
	public ConfiguredSphereArgumentType(CommandRegistryAccess registryAccess) {
		super(registryAccess, StarryRegistryKeys.CONFIGURED_SPHERE, ConfiguredSphere.CODEC);
	}
	
	public static ConfiguredSphereArgumentType configuredSphere(CommandRegistryAccess registryAccess) {
		return new ConfiguredSphereArgumentType(registryAccess);
	}
}
