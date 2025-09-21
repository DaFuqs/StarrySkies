package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.*;
import net.minecraft.registry.entry.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
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
		StructureWorldAccess world = context.getWorld();
		PlacedSphere<?> sphere = context.getSphere();
		ChunkPos origin = context.getChunkPos();
		Random random = context.getRandom();
		MobSpawnerDecoratorConfig config = context.getConfig();
		
		if (!sphere.isCenterInChunk(origin)) {
			return false;
		}
		
		MobSpawnerDecoratorConfig.Position position = config.position();
		
		@Nullable BlockPos spawnerPos = switch (position) {
			case TOP_CENTER -> sphere.getPosition().up(sphere.getRadius() + 1);
			case CENTER -> sphere.getPosition();
			case CAVE_FLOOR -> getCaveBottomBlock(world, sphere.getPosition().up(), sphere);
		};
		
		if (spawnerPos != null) {
			world.setBlockState(spawnerPos, Blocks.SPAWNER.getDefaultState(), 3);
			if (world.getBlockEntity(spawnerPos) instanceof MobSpawnerBlockEntity spawnerBlock) {
				Optional<RegistryEntry.Reference<EntityType<?>>> entry = world.getRegistryManager().getOptionalEntry(config.entityType());
				entry.ifPresent(entityTypeReference -> spawnerBlock.getLogic().setEntityId(entityTypeReference.value(), null, random, spawnerPos));
			}
			return true;
		}
		
		return false;
	}
	
}
