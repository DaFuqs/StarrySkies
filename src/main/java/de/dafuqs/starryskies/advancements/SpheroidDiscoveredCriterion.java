package de.dafuqs.starryskies.advancements;

import com.google.gson.*;
import de.dafuqs.starryskies.spheroids.spheroids.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class SpheroidDiscoveredCriterion extends AbstractCriterion<SpheroidDiscoveredCriterion.Conditions> {
	
	private static Identifier[] deserializeAll(JsonElement json) {
		JsonArray array = json.getAsJsonArray();
		Identifier[] ids = new Identifier[array.size()];
		for (int i = 0; i < array.size(); i++) {
			ids[i] = Identifier.tryParse(array.get(i).getAsString());
		}
		return ids;
	}
	
	private static JsonElement serializeAll(Identifier[] identifiers) {
		JsonArray array = new JsonArray();
		for (Identifier id : identifiers) {
			array.add(id.toString());
		}
		return array;
	}
	
	public void trigger(ServerPlayerEntity player, Spheroid spheroid) {
		this.trigger(player, (conditions) -> conditions.matches(spheroid.getTemplate().getID()));
	}
	
	@Override
	protected Conditions conditionsFromJson(JsonObject json, Optional<LootContextPredicate> predicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
		Identifier[] identifiers;
		if (json.has("ids")) {
			identifiers = deserializeAll(json.get("ids"));
		} else {
			identifiers = new Identifier[0];
		}
		return new SpheroidDiscoveredCriterion.Conditions(predicate, identifiers);
	}
	
	public static class Conditions extends AbstractCriterionConditions {
		
		private final Identifier[] identifiers;
		
		public Conditions(Optional<LootContextPredicate> playerPredicate, @Nullable Identifier[] identifiers) {
			super(playerPredicate);
			this.identifiers = identifiers;
		}
		
		public JsonObject toJson() {
			JsonObject jsonObject = super.toJson();
			jsonObject.add("ids", serializeAll(identifiers));
			return jsonObject;
		}
		
		public boolean matches(Identifier spheroidIdentifier) {
			if (this.identifiers.length == 0) {
				return true;
			}
			if (spheroidIdentifier == null) {
				return true;
			}
			for (Identifier id : identifiers) {
				if (spheroidIdentifier.equals(id)) {
					return true;
				}
			}
			return false;
		}
	}
	
}
