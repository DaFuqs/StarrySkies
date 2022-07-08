package de.dafuqs.starrysky.mixin;

import de.dafuqs.starrysky.dimension.StarrySkyDimension;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.dimension.DimensionOptions;
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

import java.util.List;
import java.util.concurrent.Executor;


@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {
    
    @Mutable
    @Shadow @Final @Nullable private EnderDragonFight enderDragonFight;
    
    @Inject(at = @At("TAIL"), method = "<init>")
    void hasEnderDragonFight(MinecraftServer server, Executor workerExecutor, LevelStorage.Session session, ServerWorldProperties properties, RegistryKey worldKey, DimensionOptions dimensionOptions, WorldGenerationProgressListener worldGenerationProgressListener, boolean debugWorld, long seed, List spawners, boolean shouldTickTime, CallbackInfo ci) {
        ServerWorld thisWorld = (ServerWorld) (Object) this;
        if (thisWorld.getRegistryKey() == StarrySkyDimension.END_KEY) {
            this.enderDragonFight = new EnderDragonFight(thisWorld, server.getSaveProperties().getGeneratorOptions().getSeed(), server.getSaveProperties().getDragonFight());
        } else {
            this.enderDragonFight = null;
        }
    }

}