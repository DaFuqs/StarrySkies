package de.dafuqs.starryskies.worldgen.spheres;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import org.jspecify.annotations.Nullable;

import java.util.*;

public class MushroomSphere extends Sphere<MushroomSphere.Config> {
	
	public MushroomSphere(Codec<MushroomSphere.Config> codec) {
		super(codec);
	}
	
	@Override
	public PlacedSphere<?> generate(ConfiguredSphere<? extends Sphere<MushroomSphere.Config>, Config> configuredSphere, Config config, WorldgenRandom random, WorldGenLevel level, BlockPos pos, float radius) {
		return new MushroomSphere.Placed(configuredSphere, radius, configuredSphere.getDecorators(random), configuredSphere.getSpawns(random), random, config.stemBlock, config.mushroomBlock, config.shellThickness.sample(random));
	}
	
	public static class Config extends SphereConfig {
		
		public static final Codec<MushroomSphere.Config> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
				SphereConfig.CONFIG_CODEC.forGetter((config) -> config),
				BlockState.CODEC.fieldOf("stem_block").forGetter((config) -> config.stemBlock),
				BlockState.CODEC.fieldOf("mushroom_block").forGetter((config) -> config.mushroomBlock),
				IntProviders.POSITIVE_CODEC.fieldOf("shell_thickness").forGetter((config) -> config.shellThickness)
		).apply(instance, (sphereConfig, stemBlock, mushroomBlock, shellThickness) -> new Config(sphereConfig.size, sphereConfig.decorators, sphereConfig.spawns, sphereConfig.generation, stemBlock, mushroomBlock, shellThickness)));
		
		protected final BlockState stemBlock;
		protected final BlockState mushroomBlock;
		protected final IntProvider shellThickness;
		
		public Config(FloatProvider size, Map<Holder<ConfiguredSphereDecorator<?, ?>>, Float> decorators, List<SphereEntitySpawnDefinition> spawns, @Nullable Generation generation, BlockState stemBlock, BlockState mushroomBlock, IntProvider shellThickness) {
			super(size, decorators, spawns, generation);
			this.stemBlock = stemBlock;
			this.mushroomBlock = mushroomBlock;
			this.shellThickness = shellThickness;
		}
		
	}
	
	public static class Placed extends PlacedSphere<MushroomSphere.Config> {
		
		private final BlockState stemBlock;
		private final BlockState mushroomBlock;
		private final float shellRadius;
		
		public Placed(ConfiguredSphere<? extends Sphere<MushroomSphere.Config>, MushroomSphere.Config> configuredSphere, float radius, List<Holder<ConfiguredSphereDecorator<?, ?>>> decorators, List<Tuple<EntityType<?>, Integer>> spawns, WorldgenRandom random,
                      BlockState stemBlock, BlockState mushroomBlock, float shellRadius) {
			super(configuredSphere, radius, decorators, spawns, random);
			this.stemBlock = stemBlock;
			this.mushroomBlock = mushroomBlock;
			this.shellRadius = shellRadius;
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
			
			// see: HugeRedMushroomFeature
			BlockState placementBlockstateInner = this.mushroomBlock
					.setValue(BlockStateProperties.UP, false)
					.setValue(BlockStateProperties.NORTH, false)
					.setValue(BlockStateProperties.EAST, false)
					.setValue(BlockStateProperties.SOUTH, false)
					.setValue(BlockStateProperties.WEST, false)
					.setValue(BlockStateProperties.DOWN, false);
			
			// not perfectly correct, but eh
			BlockState placementBlockstateOuter = this.mushroomBlock
					.setValue(BlockStateProperties.UP, true)
					.setValue(BlockStateProperties.NORTH, true)
					.setValue(BlockStateProperties.EAST, true)
					.setValue(BlockStateProperties.SOUTH, true)
					.setValue(BlockStateProperties.WEST, true)
					.setValue(BlockStateProperties.DOWN, true);
			
			BlockPos.MutableBlockPos currBlockPos = new BlockPos.MutableBlockPos();
			for (int x2 = Math.max(chunkX * 16, x - ceiledRadius); x2 <= maxX; x2++) {
				for (int y2 = y - ceiledRadius; y2 <= y + ceiledRadius; y2++) {
					for (int z2 = Math.max(chunkZ * 16, z - ceiledRadius); z2 <= maxZ; z2++) {
						long d = Math.round(Support.getDistance(x, y, z, x2, y2, z2));
						if (d > this.radius) {
							continue;
						}
						currBlockPos.set(x2, y2, z2);
						
						long rounded = Math.round(d);
						if (rounded < (this.radius - this.shellRadius)) {
							chunk.setBlockState(currBlockPos, this.stemBlock);
						} else if (d < this.radius - 1) {
							chunk.setBlockState(currBlockPos, placementBlockstateInner);
						} else {
							chunk.setBlockState(currBlockPos, placementBlockstateOuter);
						}
					}
				}
			}
		}
		
		@Override
		public String getDescription(RegistryAccess registryManager) {
			return "+++ MushroomSphere +++" +
					"\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
					"\nTemplateID: " + this.getID(registryManager) +
					"\nRadius: " + this.radius +
					"\nMushroom: " + this.mushroomBlock.toString() + " (Radius: " + this.shellRadius + ")" +
					"\nStem: " + this.stemBlock.toString();
		}
	}
	
}
	
