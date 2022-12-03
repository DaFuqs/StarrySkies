package de.dafuqs.starryskies.advancements;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.spheroids.types.Spheroid;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.jetbrains.annotations.Nullable;

public class SpheroidDiscoveredCriterion extends AbstractCriterion<SpheroidDiscoveredCriterion.Conditions> {
	
	public static final Identifier ID = StarrySkies.locate("spheroid_discovered");
	
	public Identifier getId() {
		return ID;
	}
	
	public SpheroidDiscoveredCriterion.Conditions conditionsFromJson(JsonObject jsonObject, EntityPredicate.Extended extended, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
		Identifier spheroidIdentifier = Identifier.tryParse(JsonHelper.getString(jsonObject, "id"));
		return new SpheroidDiscoveredCriterion.Conditions(extended, spheroidIdentifier);
	}
	
	public void trigger(ServerPlayerEntity player, Spheroid spheroid) {
		this.trigger(player, (conditions) -> conditions.matches(spheroid.getTemplate().getID()));
	}
	
	public static class Conditions extends AbstractCriterionConditions {
		
		private final Identifier spheroidIdentifier;
		
		public Conditions(EntityPredicate.Extended player, @Nullable Identifier spheroidIdentifier) {
			super(ID, player);
			this.spheroidIdentifier = spheroidIdentifier;
		}
		
		public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
			JsonObject jsonObject = super.toJson(predicateSerializer);
			jsonObject.add("id", new JsonPrimitive(String.valueOf(this.spheroidIdentifier)));
			return jsonObject;
		}
		
		public boolean matches(Identifier spheroidIdentifier) {
			return spheroidIdentifier != null && (this.spheroidIdentifier == null || this.spheroidIdentifier.equals(spheroidIdentifier));
		}
	}
	
}
