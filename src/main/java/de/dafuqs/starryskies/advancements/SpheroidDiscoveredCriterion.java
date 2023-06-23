package de.dafuqs.starryskies.advancements;

import com.google.gson.*;
import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.spheroids.spheroids.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

public class SpheroidDiscoveredCriterion extends AbstractCriterion<SpheroidDiscoveredCriterion.Conditions> {
	
	public static final Identifier ID = StarrySkies.locate("spheroid_discovered");
	
	public Identifier getId() {
		return ID;
	}
	
	public SpheroidDiscoveredCriterion.Conditions conditionsFromJson(JsonObject jsonObject, LootContextPredicate lootContextPredicate, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
		Identifier[] identifiers;
		if (jsonObject.has("ids")) {
			identifiers = deserializeAll(jsonObject.get("ids"));
		} else {
			identifiers = new Identifier[0];
		}
		return new SpheroidDiscoveredCriterion.Conditions(lootContextPredicate, identifiers);
	}
	
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
	
	public static class Conditions extends AbstractCriterionConditions {
		
		private final Identifier[] identifiers;
		
		public Conditions(LootContextPredicate player, @Nullable Identifier[] identifiers) {
			super(ID, player);
			this.identifiers = identifiers;
		}
		
		public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
			JsonObject jsonObject = super.toJson(predicateSerializer);
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
