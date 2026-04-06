package de.dafuqs.starryskies.advancements;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SphereDiscoveredCriterion extends SimpleCriterionTrigger<SphereDiscoveredCriterion.Conditions> {

	@Override
	public @NotNull Codec<Conditions> codec() {
		return Conditions.CODEC;
	}
	
	public void trigger(ServerPlayer player, PlacedSphere<?> sphere) {
		this.trigger(player, (conditions) -> conditions.matches(sphere.getID(player.registryAccess())));
	}

	public record Conditions(Optional<ContextAwarePredicate> player,
							 List<ResourceLocation> identifiers) implements SimpleCriterionTrigger.SimpleInstance {

		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(
				(instance) -> instance.group(
						EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(Conditions::player),
						ResourceLocation.CODEC.listOf().optionalFieldOf("ids", List.of()).forGetter(Conditions::identifiers)
				).apply(instance, Conditions::new)
		);

		public boolean matches(ResourceLocation id) {
			if (id == null) {
				return true;
			}
			if (this.identifiers.isEmpty()) {
				return true;
			}
			for (ResourceLocation identifier : identifiers) {
				if (identifier.equals(id)) {
					return true;
				}
			}
			return false;
		}

	}

}
