package de.dafuqs.starryskies.worldgen.spheres;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.state_providers.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import org.jspecify.annotations.Nullable;

import java.util.*;

public class FluidCoreSphere extends Sphere<FluidCoreSphere.Config> {
	
	public FluidCoreSphere(Codec<FluidCoreSphere.Config> codec) {
		super(codec);
	}
	
	@Override
	public PlacedSphere<?> generate(ConfiguredSphere<? extends Sphere<FluidCoreSphere.Config>, Config> configuredSphere, Config config, WorldgenRandom random, WorldGenLevel level, BlockPos pos, float radius) {
		return new FluidCoreSphere.Placed(configuredSphere, radius, configuredSphere.getDecorators(random), configuredSphere.getSpawns(random), random,
				config.shellBlock.getForSphere(level, random, pos), config.shellThickness.sample(random), config.fluidBlock, config.fillPercent.sample(random), config.holeInBottomChance > random.nextFloat(), config.coreBlock.getForSphere(level, random, pos), config.coreRadius.sample(random));
	}
	
	public static class Config extends SphereConfig {
		
		public static final Codec<FluidCoreSphere.Config> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
				SphereConfig.CONFIG_CODEC.forGetter((config) -> config),
				SphereStateProvider.CODEC.fieldOf("shell_block").forGetter((config) -> config.shellBlock),
				IntProviders.POSITIVE_CODEC.fieldOf("shell_thickness").forGetter((config) -> config.shellThickness),
				BlockState.CODEC.fieldOf("fluid_block").forGetter((config) -> config.fluidBlock),
				FloatProviders.codec(0.0F, 1.0F).fieldOf("fluid_fill_percent").forGetter((config) -> config.fillPercent),
				ExtraCodecs.NON_NEGATIVE_FLOAT.fieldOf("hole_in_bottom_chance").forGetter((config) -> config.holeInBottomChance),
				SphereStateProvider.CODEC.fieldOf("core_block").forGetter((config) -> config.coreBlock),
				FloatProviders.codec(1.0F, 32.0F).fieldOf("core_radius").forGetter((config) -> config.coreRadius)
		).apply(instance, (sphereConfig, shellBlock, shellThickness, fluidBlock, fillAmount, holeInBottom, coreBlock, coreRadius) -> new Config(sphereConfig.size, sphereConfig.decorators, sphereConfig.spawns, sphereConfig.generation, shellBlock, shellThickness, fluidBlock, fillAmount, holeInBottom, coreBlock, coreRadius)));
		
		protected final SphereStateProvider shellBlock;
		protected final IntProvider shellThickness;
		protected final BlockState fluidBlock;
		protected final FloatProvider fillPercent;
		protected final float holeInBottomChance;
		private final SphereStateProvider coreBlock;
		private final FloatProvider coreRadius;
		
		public Config(FloatProvider size, Map<Holder<ConfiguredSphereDecorator<?, ?>>, Float> decorators, List<SphereEntitySpawnDefinition> spawns, @Nullable Generation generation,
                      SphereStateProvider shellBlock, IntProvider shellThickness, BlockState fluidBlock, FloatProvider fillPercent, float holeInBottomChance, SphereStateProvider coreBlock, FloatProvider coreRadius) {
			super(size, decorators, spawns, generation);
			this.shellBlock = shellBlock;
			this.shellThickness = shellThickness;
			this.fluidBlock = fluidBlock;
			this.fillPercent = fillPercent;
			this.holeInBottomChance = holeInBottomChance;
			this.coreBlock = coreBlock;
			this.coreRadius = coreRadius;
		}
		
	}
	
	public static class Placed extends PlacedSphere<FluidCoreSphere.Config> {
		
		private final BlockState CAVE_AIR = Blocks.CAVE_AIR.defaultBlockState();
		
		private final BlockStateProvider shellBlock;
		private final float shellRadius;
		private final BlockState fluidBlock;
		private final float fillPercent;
		private final boolean holeInBottom;
		private final BlockStateProvider coreBlock;
		private final float coreRadius;
		
		public Placed(ConfiguredSphere<? extends Sphere<FluidCoreSphere.Config>, FluidCoreSphere.Config> configuredSphere, float radius, List<Holder<ConfiguredSphereDecorator<?, ?>>> decorators, List<Tuple<EntityType<?>, Integer>> spawns, WorldgenRandom random,
                      BlockStateProvider shellBlock, float shellRadius, BlockState fluidBlock, float fillPercent, boolean holeInBottom, BlockStateProvider coreBlock, float coreRadius) {
			super(configuredSphere, radius, decorators, spawns, random);
			this.shellBlock = shellBlock;
			this.shellRadius = shellRadius;
			this.fluidBlock = fluidBlock;
			this.fillPercent = fillPercent;
			this.holeInBottom = holeInBottom;
			this.coreBlock = coreBlock;
			this.coreRadius = coreRadius;
		}
		
		@Override
		public void generate(ChunkAccess chunk, WorldGenLevel level) {
			int chunkX = chunk.getPos().x();
			int chunkZ = chunk.getPos().z();
			random.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
			BlockPos spherePos = this.getPosition();
			int x = spherePos.getX();
			int y = spherePos.getY();
			int z = spherePos.getZ();
			
			int ceiledRadius = (int) Math.ceil(this.radius);
			int maxX = Math.min(chunkX * 16 + 15, x + ceiledRadius);
			int maxZ = Math.min(chunkZ * 16 + 15, z + ceiledRadius);
			
			float liquidRadius = this.radius - this.shellRadius;
			float maxLiquidY = y + (this.fillPercent * liquidRadius * 2 - liquidRadius);
			
			BlockPos.MutableBlockPos currBlockPos = new BlockPos.MutableBlockPos();
			for (int x2 = Math.max(chunkX * 16, x - ceiledRadius); x2 <= maxX; x2++) {
				for (int y2 = y - ceiledRadius; y2 <= y + ceiledRadius; y2++) {
					for (int z2 = Math.max(chunkZ * 16, z - ceiledRadius); z2 <= maxZ; z2++) {
						long d = Math.round(Support.getDistance(x, y, z, x2, y2, z2));
						if (d > this.radius) {
							continue;
						}
						currBlockPos.set(x2, y2, z2);
						
						if (this.holeInBottom && (x - x2) == 0 && (z - z2) == 0 && (y - y2 + 1) >= liquidRadius) {
							chunk.setBlockState(new BlockPos(currBlockPos), this.fluidBlock);
							chunk.markPosForPostprocessing(currBlockPos); // makes it drop down after generation is complete
						} else if (d <= this.coreRadius) {
							chunk.setBlockState(currBlockPos, this.coreBlock.getState(level, random, currBlockPos));
						} else if (d <= liquidRadius) {
							if (y2 <= maxLiquidY) {
								chunk.setBlockState(currBlockPos, this.fluidBlock);
							} else {
								chunk.setBlockState(currBlockPos, CAVE_AIR);
							}
						} else {
							chunk.setBlockState(currBlockPos, this.shellBlock.getState(level, random, currBlockPos));
						}
					}
				}
			}
		}
		
		@Override
		public String getDescription(RegistryAccess registryManager) {
			return "+++ FluidCoreSphere +++" +
					"\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
					"\nTemplateID: " + this.getID(registryManager) +
					"\nRadius: " + this.radius +
					"\nShell: " + this.shellBlock.toString() + "(Radius: " + this.shellRadius + ")" +
					"\nLiquid: " + this.fluidBlock.toString() +
					"\nCore: " + this.coreBlock + "(Radius: " + this.coreRadius + ")" +
					"\nFill Percent: " + this.fillPercent +
					"\nHole in bottom: " + this.holeInBottom;
		}
	}
	
}
	
