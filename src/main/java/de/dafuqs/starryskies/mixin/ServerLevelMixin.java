package de.dafuqs.starryskies.mixin;

import com.mojang.datafixers.util.*;
import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.worldgen.*;
import de.dafuqs.starryskies.worldgen.dimension.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import java.util.*;
import java.util.function.*;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {
	
	/**
	 * When we are in a Starry world the structure location logic auto-remaps structure tags to sphere tags,
	 * for example, "eye_of_ender_located" structure tag (Eye of Ender) now locates spheres in the sphere tag "starry_skies:eye_of_ender_located"
	 */
	@Inject(at = @At("HEAD"), method = "findNearestMapStructure(Lnet/minecraft/tags/TagKey;Lnet/minecraft/core/BlockPos;IZ)Lnet/minecraft/core/BlockPos;", cancellable = true)
	public void starryskies$locateStructure(TagKey<Structure> structureTag, BlockPos pos, int radius, boolean skipReferencedStructures, CallbackInfoReturnable<BlockPos> cir) {
		ServerLevel thisWorld = (ServerLevel) (Object) this;
		ChunkGenerator chunkGenerator = thisWorld.getChunkSource().getGenerator();
		if (chunkGenerator instanceof StarrySkyChunkGenerator) {
			TagKey<ConfiguredSphere<?, ?>> targetSphereTag = SphereTags.getForVanillaStructure(structureTag);
			Predicate<Holder<ConfiguredSphere<?, ?>>> predicate = configuredSphereRegistryEntry -> configuredSphereRegistryEntry.is(targetSphereTag);
			Optional<Pair<BlockPos, Holder<ConfiguredSphere<?, ?>>>> distance = Support.getClosestSphere3x3(thisWorld, pos, predicate, thisWorld.registryAccess());
			if (distance.isEmpty()) {
				cir.setReturnValue(null);
			} else {
				cir.setReturnValue(distance.get().getFirst());
			}
		}
	}


}