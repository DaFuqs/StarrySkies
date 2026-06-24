package de.dafuqs.starryskies.worldgen.spheres;

import com.mojang.datafixers.util.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.state_providers.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.chunk.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.*;
import org.jspecify.annotations.*;

import java.util.*;

public class CoralsSphere extends Sphere<CoralsSphere.Config> {
	
	public static final ArrayList<BlockState> LIST_FULL_CORAL_BLOCKS = new ArrayList<>() {{
		add(Blocks.BRAIN_CORAL_BLOCK.defaultBlockState());
		add(Blocks.TUBE_CORAL_BLOCK.defaultBlockState());
		add(Blocks.BUBBLE_CORAL_BLOCK.defaultBlockState());
		add(Blocks.FIRE_CORAL_BLOCK.defaultBlockState());
		add(Blocks.HORN_CORAL_BLOCK.defaultBlockState());
	}};
	public static final ArrayList<BlockState> LIST_WATERLOGGABLE_CORAL_BLOCKS = new ArrayList<>() {{
		add(Blocks.BRAIN_CORAL.defaultBlockState().setValue(BaseCoralPlantTypeBlock.WATERLOGGED, true));
		add(Blocks.TUBE_CORAL.defaultBlockState().setValue(BaseCoralPlantTypeBlock.WATERLOGGED, true));
		add(Blocks.BUBBLE_CORAL.defaultBlockState().setValue(BaseCoralPlantTypeBlock.WATERLOGGED, true));
		add(Blocks.FIRE_CORAL.defaultBlockState().setValue(BaseCoralPlantTypeBlock.WATERLOGGED, true));
		add(Blocks.HORN_CORAL.defaultBlockState().setValue(BaseCoralPlantTypeBlock.WATERLOGGED, true));
		
		add(Blocks.BRAIN_CORAL_FAN.defaultBlockState().setValue(BaseCoralPlantTypeBlock.WATERLOGGED, true));
		add(Blocks.TUBE_CORAL_FAN.defaultBlockState().setValue(BaseCoralPlantTypeBlock.WATERLOGGED, true));
		add(Blocks.BUBBLE_CORAL_FAN.defaultBlockState().setValue(BaseCoralPlantTypeBlock.WATERLOGGED, true));
		add(Blocks.FIRE_CORAL_FAN.defaultBlockState().setValue(BaseCoralPlantTypeBlock.WATERLOGGED, true));
		add(Blocks.HORN_CORAL_FAN.defaultBlockState().setValue(BaseCoralPlantTypeBlock.WATERLOGGED, true));
		
		add(Blocks.SEA_PICKLE.defaultBlockState().setValue(SeaPickleBlock.WATERLOGGED, true).setValue(SeaPickleBlock.PICKLES, 1));
		add(Blocks.SEA_PICKLE.defaultBlockState().setValue(SeaPickleBlock.WATERLOGGED, true).setValue(SeaPickleBlock.PICKLES, 2));
		add(Blocks.SEA_PICKLE.defaultBlockState().setValue(SeaPickleBlock.WATERLOGGED, true).setValue(SeaPickleBlock.PICKLES, 3));
		add(Blocks.SEA_PICKLE.defaultBlockState().setValue(SeaPickleBlock.WATERLOGGED, true).setValue(SeaPickleBlock.PICKLES, 4));
	}};
	protected static BlockState WATER = Blocks.WATER.defaultBlockState();
	
	public CoralsSphere(Codec<CoralsSphere.Config> codec) {
		super(codec);
	}
	
	@Override
	public PlacedSphere<?> generate(ConfiguredSphere<? extends Sphere<CoralsSphere.Config>, Config> configuredSphere, Config config, WorldgenRandom random, WorldGenLevel level, BlockPos pos, float radius) {
		return new CoralsSphere.Placed(configuredSphere, radius, configuredSphere.getDecorators(random), configuredSphere.getSpawns(random), random, config.shellBlock.getForSphere(level, random, pos), config.shellThickness.sample(random));
	}
	
	public static class Config extends SphereConfig {
		
		public static final Codec<CoralsSphere.Config> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
				SphereConfig.CONFIG_CODEC.forGetter((config) -> config),
				SphereStateProvider.CODEC.fieldOf("shell_block").forGetter((config) -> config.shellBlock),
				IntProviders.POSITIVE_CODEC.fieldOf("shell_thickness").forGetter((config) -> config.shellThickness),
				ExtraCodecs.NON_NEGATIVE_FLOAT.fieldOf("hole_in_bottom_chance").forGetter((config) -> config.holeInBottomChance)
		).apply(instance, (sphereConfig, shellBlock, shellThickness, holeInBottomChance) -> new Config(sphereConfig.size, sphereConfig.decorators, sphereConfig.spawns, sphereConfig.generation, shellBlock, shellThickness, holeInBottomChance)));
		
		protected final SphereStateProvider shellBlock;
		protected final IntProvider shellThickness;
		protected final float holeInBottomChance;
		
		public Config(FloatProvider size, Map<Holder<ConfiguredSphereDecorator<?, ?>>, Float> decorators, List<SphereEntitySpawnDefinition> spawns, @Nullable Generation generation, SphereStateProvider shellBlock, IntProvider shellThickness, float holeInBottomChance) {
			super(size, decorators, spawns, generation);
			this.shellBlock = shellBlock;
			this.shellThickness = shellThickness;
			this.holeInBottomChance = holeInBottomChance;
		}
		
	}
	
	public static class Placed extends PlacedSphere<CoralsSphere.Config> {
		
		private final BlockStateProvider shellBlock;
		private final float shellRadius;

        public Placed(ConfiguredSphere<? extends Sphere<CoralsSphere.Config>, CoralsSphere.Config> configuredSphere, float radius, List<Holder<ConfiguredSphereDecorator<?, ?>>> decorators, List<Pair<EntityType<?>, Integer>> spawns, WorldgenRandom random,
                      BlockStateProvider shellBlock, float shellRadius) {
			super(configuredSphere, radius, decorators, spawns, random);
			this.shellBlock = shellBlock;
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
			
			BlockPos.MutableBlockPos currBlockPos = new BlockPos.MutableBlockPos();
			for (int x2 = Math.max(chunkX * 16, x - ceiledRadius); x2 <= maxX; x2++) {
				for (int y2 = y - ceiledRadius; y2 <= y + ceiledRadius; y2++) {
					for (int z2 = Math.max(chunkZ * 16, z - ceiledRadius); z2 <= maxZ; z2++) {
						long d = Math.round(Support.getDistance(x, y, z, x2, y2, z2));
						if (d > this.radius) {
							continue;
						}
						currBlockPos.set(x2, y2, z2);
						
						if (d <= (this.radius - this.shellRadius - 1)) {
							int rand = random.nextInt(7);
							if (rand < 2) {
								BlockState coral = getRandomCoralBlock(random);
								if (rand == 0 && chunk.getBlockState(currBlockPos.below()).getBlock() == Blocks.WATER) {
									chunk.setBlockState(currBlockPos.below(), coral);
									chunk.setBlockState(currBlockPos, getRandomWaterLoggableBlock(random));
								} else {
									chunk.setBlockState(currBlockPos, coral);
								}
							} else {
								chunk.setBlockState(currBlockPos, WATER);
							}
						} else if (d <= (this.radius - this.shellRadius)) {
							chunk.setBlockState(currBlockPos, WATER);
						} else {
							chunk.setBlockState(currBlockPos, this.shellBlock.getState(level, random, currBlockPos));
						}
					}
				}
			}
		}
		
		public BlockState getRandomCoralBlock(WorldgenRandom random) {
			return LIST_FULL_CORAL_BLOCKS.get(random.nextInt(LIST_FULL_CORAL_BLOCKS.size()));
		}
		
		public BlockState getRandomWaterLoggableBlock(WorldgenRandom random) {
			return LIST_WATERLOGGABLE_CORAL_BLOCKS.get(random.nextInt(LIST_WATERLOGGABLE_CORAL_BLOCKS.size()));
		}
		
		@Override
		public String getDescription(RegistryAccess registryManager) {
			return "+++ CoralSphere +++" +
					"\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
					"\nTemplateID: " + this.getID(registryManager) +
					"\nRadius: " + this.radius +
					"\nShell: " + this.shellBlock.toString() + " (Radius: " + this.shellRadius + ")";
		}
	}
	
}
	
