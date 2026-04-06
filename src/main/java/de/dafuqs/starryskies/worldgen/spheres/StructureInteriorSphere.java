package de.dafuqs.starryskies.worldgen.spheres;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.state_providers.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.*;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class StructureInteriorSphere extends ShellSphere<StructureInteriorSphere.Config> {
	
	private final static int STRUCTURE_SIZE = 9;
	
	public StructureInteriorSphere(Codec<StructureInteriorSphere.Config> codec) {
		super(codec);
	}
	
	@Override
	public PlacedSphere<?> generate(ConfiguredSphere<? extends Sphere<Config>, Config> configuredSphere, Config config, WorldgenRandom random, RegistryAccess registryManager, BlockPos pos, float radius) {
		return new Placed(configuredSphere, radius, configuredSphere.getDecorators(random), configuredSphere.getSpawns(random), random,
				config.innerBlock.getForSphere(random, pos), config.shellBlock.getForSphere(random, pos), config.shellThickness.sample(random), config.shellThickness.sample(random), config.centerStructures, config.outerStructures);
	}
	
	public static class Config extends ShellSphere.Config {
		
		public static final Codec<StructureInteriorSphere.Config> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
				SphereConfig.CONFIG_CODEC.forGetter((config) -> config),
				SphereStateProvider.CODEC.fieldOf("main_block").forGetter((config) -> config.innerBlock),
				SphereStateProvider.CODEC.fieldOf("shell_block").forGetter((config) -> config.shellBlock),
				IntProvider.POSITIVE_CODEC.fieldOf("shell_thickness").forGetter((config) -> config.shellThickness),
				WeightedList.codec(ResourceLocation.CODEC).fieldOf("center_structures").forGetter((config) -> config.centerStructures),
				WeightedList.codec(ResourceLocation.CODEC).fieldOf("structures").forGetter((config) -> config.outerStructures)
		).apply(instance, (sphereConfig, innerBlock, shellBlock, shellThickness, centerStructures, outerStructures)
				-> new Config(sphereConfig.size, sphereConfig.decorators, sphereConfig.spawns, sphereConfig.generation, innerBlock, shellBlock, shellThickness, centerStructures, outerStructures)));
		
		protected final IntProvider shellThickness;
		protected final WeightedList<ResourceLocation> centerStructures;
		protected final WeightedList<ResourceLocation> outerStructures;
		
		public Config(FloatProvider size, Map<Holder<ConfiguredSphereDecorator<?, ?>>, Float> decorators, List<SphereEntitySpawnDefinition> spawns, @Nullable Generation generation,
                      SphereStateProvider innerBlock, SphereStateProvider shellBlock, IntProvider shellThickness, WeightedList<ResourceLocation> centerStructures, WeightedList<ResourceLocation> outerStructures) {
			super(size, decorators, spawns, generation, innerBlock, shellBlock, shellThickness);
			this.shellThickness = shellThickness;
			this.centerStructures = centerStructures;
			this.outerStructures = outerStructures;
		}
	}
	
	public static class Placed extends ShellSphere.Placed<StructureInteriorSphere.Config> {
		
		protected final float shellRadius;
		// These should all be 9x9x9 in size
		protected final WeightedList<ResourceLocation> centerStructures;
		protected final WeightedList<ResourceLocation> outerStructures;
		
		public Placed(ConfiguredSphere<? extends Sphere<Config>, Config> configuredSphere, float radius, List<Holder<ConfiguredSphereDecorator<?, ?>>> decorators, List<Tuple<EntityType<?>, Integer>> spawns, WorldgenRandom random,
                      BlockStateProvider innerBlock, BlockStateProvider shellBlock, int shellRadius, float shellRadius1, WeightedList<ResourceLocation> centerStructures, WeightedList<ResourceLocation> outerStructures) {
			super(configuredSphere, radius, decorators, spawns, random, innerBlock, shellBlock, shellRadius);
			this.shellRadius = shellRadius1;
			this.centerStructures = centerStructures;
			this.outerStructures = outerStructures;
		}
		
		@Override
		public String getDescription(RegistryAccess registryManager) {
			return "+++ StructureInteriorSphere +++" +
					"\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
					"\nTemplateID: " + this.getID(registryManager) +
					"\nRadius: " + this.radius +
					"\nShell: " + this.shellBlock.toString() + " (Radius: " + this.shellRadius + ")" +
					"\nCore: " + this.innerBlock.toString();
		}
		
		@Override
		public void decorate(WorldGenLevel world, BlockPos origin, RandomSource random) {
			super.decorate(world, origin, random);
			
			StructureTemplateManager templateManager = world.getServer().getStructureManager();
			
			ChunkPos chunkPos = new ChunkPos(origin);
			BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
			
			int pivot = (STRUCTURE_SIZE - 1) / 2;
			int iMaxRadius = (int) (radius - shellRadius) - pivot - 1;
			int step = STRUCTURE_SIZE + 1;
			int start = ((int) (radius - shellRadius) / step) * step;
			
			for (int x2 = position.getX() - start; x2 <= position.getX() + start; x2 += step) {
				for (int y2 = position.getY() - start; y2 <= position.getY() + start; y2 += step) {
					for (int z2 = position.getZ() - start; z2 <= position.getZ() + start; z2 += step) {
						long d = Math.round(Support.getDistance(position.getX(), position.getY(), position.getZ(), x2, y2, z2));
						if (d < iMaxRadius) {
							mutable.set(x2, y2, z2);
							if (Support.isBlockPosInChunkPos(chunkPos, mutable)) {
								WeightedList<ResourceLocation> structurePool = d == 0 ? centerStructures : outerStructures;
								ResourceLocation structureId = structurePool.getRandomOrThrow(random);
								StructureTemplate template = templateManager.get(structureId).orElse(null);
								if (template != null) {
									BlockPos set = mutable.set(x2 - pivot, y2, z2 - pivot).immutable();
									// TODO: how about giving them a random rotation via BlockRotation.random(random)? (need to adjust the pos, though)
									StructurePlaceSettings structurePlacementData = new StructurePlaceSettings().setRotation(Rotation.NONE).setIgnoreEntities(false);
									template.placeInWorld(world, set, set, structurePlacementData, StructureBlockEntity.createRandom(this.position.asLong()), 2);
								} else {
									StarrySkies.LOGGER.error("Trying to place a nonexistent structure template: {}", structureId);
								}
							}
						}
					}
				}
			}
		}
	}
	
}