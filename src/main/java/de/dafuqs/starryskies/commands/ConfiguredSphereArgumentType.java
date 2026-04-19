package de.dafuqs.starryskies.commands;

import de.dafuqs.starryskies.registries.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.arguments.ResourceOrIdArgument;

public class ConfiguredSphereArgumentType extends ResourceOrIdArgument<ConfiguredSphere<?, ?>> {
	
	public ConfiguredSphereArgumentType(CommandBuildContext registryAccess) {
		super(registryAccess, StarryRegistryKeys.CONFIGURED_SPHERE, ConfiguredSphere.CODEC);
	}
	
	public static ConfiguredSphereArgumentType configuredSphere(CommandBuildContext registryAccess) {
		return new ConfiguredSphereArgumentType(registryAccess);
	}
}
