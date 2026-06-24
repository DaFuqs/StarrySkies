package de.dafuqs.starryskies.advancements;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.advancements.predicates.*;
import net.minecraft.advancements.predicates.entity.*;
import net.minecraft.advancements.triggers.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import org.jspecify.annotations.*;

import java.util.*;

public class SphereDiscoveredCriterion extends SimpleCriterionTrigger<SphereDiscoveredCriterion.Conditions> {

	@Override
	public @NonNull Codec<Conditions> codec() {
		return Conditions.CODEC;
	}
	
	public void trigger(ServerPlayer player, PlacedSphere<?> sphere) {
		this.trigger(player, (conditions) -> conditions.matches(sphere.getID(player.registryAccess())));
	}

	public record Conditions(Optional<ContextAwarePredicate> player,
	                         List<Identifier> identifiers) implements SimpleCriterionTrigger.SimpleInstance {

		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(
				(instance) -> instance.group(
						EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(Conditions::player),
						Identifier.CODEC.listOf().optionalFieldOf("ids", List.of()).forGetter(Conditions::identifiers)
				).apply(instance, Conditions::new)
		);

		public boolean matches(Identifier id) {
			if (id == null) {
				return true;
			}
			if (this.identifiers.isEmpty()) {
				return true;
			}
			for (Identifier identifier : identifiers) {
				if (identifier.equals(id)) {
					return true;
				}
			}
			return false;
		}

	}

}
