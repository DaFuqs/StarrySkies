package de.dafuqs.starryskies.worldgen.spheres;

import com.mojang.datafixers.util.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.*;
import de.dafuqs.starryskies.state_providers.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.chunk.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.*;
import org.jspecify.annotations.*;

import java.util.*;

public class ShellSphere<SC extends ShellSphere.Config> extends Sphere<SC> {
	
	public ShellSphere(Codec<SC> configCodec) {
		super(configCodec);
	}
	
	@Override
	public PlacedSphere<?> generate(ConfiguredSphere<? extends Sphere<SC>, SC> configuredSphere, SC config, WorldgenRandom random, WorldGenLevel level, BlockPos pos, float radius) {
		return new ShellSphere.Placed<>(configuredSphere, radius, configuredSphere.getDecorators(random), configuredSphere.getSpawns(random), random, config.innerBlock.getForSphere(level, random, pos), config.shellBlock.getForSphere(level, random, pos), config.shellThickness.sample(random));
	}
	
	public static class Config extends SphereConfig {
		
		public static final Codec<ShellSphere.Config> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
				SphereConfig.CONFIG_CODEC.forGetter((config) -> config),
				SphereStateProvider.CODEC.fieldOf("main_block").forGetter((config) -> config.innerBlock),
				SphereStateProvider.CODEC.fieldOf("shell_block").forGetter((config) -> config.shellBlock),
				IntProviders.POSITIVE_CODEC.fieldOf("shell_thickness").forGetter((config) -> config.shellThickness)
		).apply(instance, (sphereConfig, innerBlock, shellBlock, shellThickness) -> new Config(sphereConfig.size, sphereConfig.decorators, sphereConfig.spawns, sphereConfig.generation, innerBlock, shellBlock, shellThickness)));
		
		protected final SphereStateProvider innerBlock;
		protected final SphereStateProvider shellBlock;
		protected final IntProvider shellThickness;
		
		public Config(FloatProvider size, Map<Holder<ConfiguredSphereDecorator<?, ?>>, Float> decorators, List<SphereEntitySpawnDefinition> spawns, @Nullable Generation generation, SphereStateProvider innerBlock, SphereStateProvider shellBlock, IntProvider shellThickness) {
			super(size, decorators, spawns, generation);
			this.innerBlock = innerBlock;
			this.shellBlock = shellBlock;
			this.shellThickness = shellThickness;
		}
		
	}
	
	public static class Placed<SC extends SphereConfig> extends PlacedSphere<SC> {
		
		protected final BlockStateProvider innerBlock;
		protected final BlockStateProvider shellBlock;
		protected final float shellRadius;

		public Placed(ConfiguredSphere<? extends Sphere<SC>, SC> configuredSphere, float radius, List<Holder<ConfiguredSphereDecorator<?, ?>>> decorators, List<Pair<EntityType<?>, Integer>> spawns, WorldgenRandom random, BlockStateProvider innerBlock, BlockStateProvider shellBlock, int shellRadius) {
			super(configuredSphere, radius, decorators, spawns, random);
			this.innerBlock = innerBlock;
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
						
						if (d <= (this.radius - this.shellRadius)) {
							chunk.setBlockState(currBlockPos, this.innerBlock.getState(level, random, currBlockPos));
						} else {
							chunk.setBlockState(currBlockPos, this.shellBlock.getState(level, random, currBlockPos));
						}
					}
				}
			}
		}
		
		@Override
		public String getDescription(RegistryAccess registryManager) {
			return "+++ ShellSphere +++" +
					"\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
					"\nTemplateID: " + this.getID(registryManager) +
					"\nRadius: " + this.radius +
					"\nShell: " + this.shellBlock.toString() + " (Radius: " + this.shellRadius + ")" +
					"\nCore: " + this.innerBlock.toString();
		}
	}
	
}
	
