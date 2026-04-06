package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import org.jetbrains.annotations.*;

import java.util.*;

/**
 * Places a mob spawner
 */
public class MobSpawnerDecorator extends SphereDecorator<MobSpawnerDecoratorConfig> {
	
	public MobSpawnerDecorator(Codec<MobSpawnerDecoratorConfig> codec) {
		super(codec);
	}
	
	@Override
	public boolean generate(SphereFeatureContext<MobSpawnerDecoratorConfig> context) {
		WorldGenLevel world = context.world();
		PlacedSphere<?> sphere = context.sphere();
		ChunkPos origin = context.chunkPos();
		RandomSource random = context.random();
		MobSpawnerDecoratorConfig config = context.config();
		
		if (!sphere.isCenterInChunk(origin)) {
			return false;
		}
		
		MobSpawnerDecoratorConfig.Position position = config.position();
		
		@Nullable BlockPos spawnerPos = switch (position) {
			case TOP_CENTER -> sphere.getPosition().above(sphere.getRadius() + 1);
			case CENTER -> sphere.getPosition();
			case CAVE_FLOOR -> getCaveBottomBlock(world, sphere.getPosition().above(), sphere);
		};
		
		if (spawnerPos != null) {
			world.setBlock(spawnerPos, Blocks.SPAWNER.defaultBlockState(), 3);
			if (world.getBlockEntity(spawnerPos) instanceof SpawnerBlockEntity spawnerBlock) {
				Optional<Holder.Reference<EntityType<?>>> entry = world.registryAccess().get(config.entityType());
				entry.ifPresent(entityTypeReference -> spawnerBlock.getSpawner().setEntityId(entityTypeReference.value(), null, random, spawnerPos));
			}
			return true;
		}
		
		return false;
	}
	
}
