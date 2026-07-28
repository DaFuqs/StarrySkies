package de.dafuqs.starryskies.advancements;

import de.dafuqs.starryskies.*;
import net.minecraft.advancements.triggers.*;
import net.minecraft.core.registries.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

public class StarryAdvancementCriteria {

    private static final DeferredRegister<CriterionTrigger<?>> REGISTRAR = DeferredRegister.create(BuiltInRegistries.TRIGGER_TYPES, StarrySkies.MOD_ID);

    public static DeferredHolder<CriterionTrigger<?>, SphereDiscoveredCriterion> SPHERE_DISCOVERED = REGISTRAR.register("sphere_discovered", () -> new SphereDiscoveredCriterion());

    public static void register(IEventBus modBus) {
        REGISTRAR.register(modBus);
	}


}