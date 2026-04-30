package de.dafuqs.starryskies.advancements;

import de.dafuqs.starryskies.*;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class StarryAdvancementCriteria {

    private static final DeferredRegister<CriterionTrigger<?>> REGISTRAR = DeferredRegister.create(BuiltInRegistries.TRIGGER_TYPES, StarrySkies.MOD_ID);

    public static DeferredHolder<CriterionTrigger<?>, SphereDiscoveredCriterion> SPHERE_DISCOVERED = REGISTRAR.register("sphere_discovered", () -> new SphereDiscoveredCriterion());

    public static void register(IEventBus modBus) {
        REGISTRAR.register(modBus);
	}


}