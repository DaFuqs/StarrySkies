package de.dafuqs.starryskies.mixin;

import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.Support;
import de.dafuqs.starryskies.dimension.StarrySkyChunkGenerator;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.StructureTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {
	@Unique
	private final Map<TagKey<Structure>, Identifier> locatableStarrySpheres = new HashMap<>() {{
		put(StructureTags.EYE_OF_ENDER_LOCATED, StarrySkies.locate("overworld/treasure/stronghold"));
		put(StructureTags.ON_OCEAN_EXPLORER_MAPS, StarrySkies.locate("overworld/treasure/ocean_monument"));
	}};
	
	@Inject(at = @At("HEAD"), method = "locateStructure(Lnet/minecraft/tag/TagKey;Lnet/minecraft/util/math/BlockPos;IZ)Lnet/minecraft/util/math/BlockPos;", cancellable = true)
	public void starryskies$locateStructure(TagKey<Structure> structureTag, BlockPos pos, int radius, boolean skipReferencedStructures, CallbackInfoReturnable<BlockPos> cir) {
		ServerWorld thisWorld = (ServerWorld) (Object) this;
		ChunkGenerator chunkGenerator = thisWorld.getChunkManager().getChunkGenerator();
		if (chunkGenerator instanceof StarrySkyChunkGenerator && locatableStarrySpheres.containsKey(structureTag)) {
			Identifier sphereIdentifier = locatableStarrySpheres.get(structureTag);
			Support.SpheroidDistance spheroidDistance = Support.getClosestSpheroid3x3(thisWorld, pos, sphereIdentifier);
			if (spheroidDistance == null) {
				cir.setReturnValue(null);
			} else {
				cir.setReturnValue(spheroidDistance.spheroid.getPosition());
			}
		}
	}
	
	
	
}