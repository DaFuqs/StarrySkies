package de.dafuqs.starryskies.mixin;

import de.dafuqs.starryskies.StarrySkies;
import de.dafuqs.starryskies.Support;
import de.dafuqs.starryskies.dimension.StarrySkyChunkGenerator;
import de.dafuqs.starryskies.dimension.StarrySkyDimension;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.concurrent.Executor;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {
	
	@Mutable
	@Shadow
	@Final
	@Nullable
	private EnderDragonFight enderDragonFight;
	
	@Inject(at = @At("TAIL"), method = "<init>")
	void starryskies$hasEnderDragonFight(MinecraftServer server, Executor workerExecutor, LevelStorage.Session session, ServerWorldProperties properties, RegistryKey worldKey, DimensionOptions dimensionOptions, WorldGenerationProgressListener worldGenerationProgressListener, boolean debugWorld, long seed, List spawners, boolean shouldTickTime, CallbackInfo ci) {
		ServerWorld thisWorld = (ServerWorld) (Object) this;
		if (thisWorld.getRegistryKey() == StarrySkyDimension.END_KEY) {
			this.enderDragonFight = new EnderDragonFight(thisWorld, server.getSaveProperties().getGeneratorOptions().getSeed(), server.getSaveProperties().getDragonFight());
		} else {
			this.enderDragonFight = null;
		}
	}
	
	@Inject(at = @At("HEAD"), method = "locateStructure(Lnet/minecraft/tag/TagKey;Lnet/minecraft/util/math/BlockPos;IZ)Lnet/minecraft/util/math/BlockPos;", cancellable = true)
	public void starryskies$locateStructure(TagKey<Structure> structureTag, BlockPos pos, int radius, boolean skipReferencedStructures, CallbackInfoReturnable<BlockPos> cir) {
		ServerWorld thisWorld = (ServerWorld) (Object) this;
		ChunkGenerator chunkGenerator = thisWorld.getChunkManager().getChunkGenerator();
		if (chunkGenerator instanceof StarrySkyChunkGenerator) {
			Support.SpheroidDistance spheroidDistance = Support.getClosestSpheroid3x3(thisWorld, pos, StarrySkies.locate("overworld/treasure/stronghold"));
			if(spheroidDistance == null) {
				cir.setReturnValue(null);
			} else {
				cir.setReturnValue(spheroidDistance.spheroid.getPosition());
			}
		}
	}
	
}