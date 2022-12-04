package de.dafuqs.starryskies.advancements;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.spheroids.spheroids.Spheroid;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class SpheroidDiscoveredCriterion extends AbstractCriterion<SpheroidDiscoveredCriterion.Conditions> {
	
	public static final Identifier ID = StarrySkies.locate("spheroid_discovered");
	
	public Identifier getId() {
		return ID;
	}
	
	public SpheroidDiscoveredCriterion.Conditions conditionsFromJson(JsonObject jsonObject, EntityPredicate.Extended extended, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
		Identifier[] identifiers;
		if(jsonObject.has("ids")) {
			identifiers = deserializeAll(jsonObject.get("ids"));
		} else {
			identifiers = new Identifier[0];
		}
		return new SpheroidDiscoveredCriterion.Conditions(extended, identifiers);
	}
	
	private static Identifier[] deserializeAll(JsonElement json) {
		JsonArray array = json.getAsJsonArray();
		Identifier[] ids = new Identifier[array.size()];
		for(int i = 0; i < array.size(); i++) {
			ids[i] = Identifier.tryParse(array.get(i).getAsString());
		}
		return ids;
	}
	
	private static JsonElement serializeAll(Identifier[] identifiers) {
		JsonArray array = new JsonArray();
		for(Identifier id : identifiers) {
			array.add(id.toString());
		}
		return array;
	}
	
	public void trigger(ServerPlayerEntity player, Spheroid spheroid) {
		this.trigger(player, (conditions) -> conditions.matches(spheroid.getTemplate().getID()));
	}
	
	public static class Conditions extends AbstractCriterionConditions {
		
		private final Identifier[] identifiers;
		
		public Conditions(EntityPredicate.Extended player, @Nullable Identifier[] identifiers) {
			super(ID, player);
			this.identifiers = identifiers;
		}
		
		public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
			JsonObject jsonObject = super.toJson(predicateSerializer);
			jsonObject.add("ids", serializeAll(identifiers));
			return jsonObject;
		}
		
		public boolean matches(Identifier spheroidIdentifier) {
			if(this.identifiers.length == 0) {
				return true;
			}
			if(spheroidIdentifier == null) {
				return true;
			}
			for(Identifier id : identifiers) {
				if(spheroidIdentifier.equals(id)) {
					return true;
				}
			}
			return false;
		}
	}
	
}
