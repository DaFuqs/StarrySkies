package de.dafuqs.starrysky.spheroidlists;

import de.dafuqs.starrysky.SpheroidLoader;
import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.spheroiddecorators.SpheroidDecorator;
import de.dafuqs.starrysky.spheroids.Spheroid;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;

public class SpheroidListSandwichable extends SpheroidList {

    private static final String MOD_ID = "sandwichable";

    private static final SpheroidDecorator SANDWICHABLE_SALTY_SAND_DECORATOR = new SandwichableSaltDecorator();
    private static final SpheroidDecorator SANDWICHABLE_SHRUB_DECORATOR = new SandwichableShrubDecorator();

    private static class SandwichableSaltDecorator extends SpheroidDecorator {

        private static final BlockState sandwichable_salty_sand = Registry.BLOCK.get(new Identifier(MOD_ID,"salty_sand")).getDefaultState();
        private static final int SALT_CHANCE = 8;

        @Override
        public void decorateSpheroid(WorldView worldView, Chunk chunk, Spheroid spheroid, ArrayList<BlockPos> blockPos, ChunkRandom random) {
            blockPos = getDecorationBlockPosInChunk(chunk, blockPos);

            for(BlockPos bp : blockPos) {
                int r = random.nextInt(SALT_CHANCE);

                if(r == 0) {
                    chunk.setBlockState(bp, sandwichable_salty_sand, false);
                }
            }
        }
    }

    private static class SandwichableShrubDecorator extends SpheroidDecorator {

        private static final BlockState sandwichable_shrub = Registry.BLOCK.get(new Identifier(MOD_ID,"shrub")).getDefaultState();
        private static final int SHRUB_CHANCE = 24;

        @Override
        public void decorateSpheroid(WorldView worldView, Chunk chunk, Spheroid spheroid, ArrayList<BlockPos> blockPos, ChunkRandom random) {
            blockPos = getDecorationBlockPosInChunk(chunk, blockPos);

            for(BlockPos bp : blockPos) {
                int r = random.nextInt(SHRUB_CHANCE);

                if(r == 0) {
                    if(chunk.getBlockState(bp).isAir()) {
                        chunk.setBlockState(bp.up(), sandwichable_shrub, false);
                    }
                }
            }
        }
    }


    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateSandwichableSpheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        SpheroidListVanilla.SAND.addDecorator(SANDWICHABLE_SALTY_SAND_DECORATOR, 0.2F);
        SpheroidListVanilla.GRASS.addDecorator(SANDWICHABLE_SHRUB_DECORATOR, 0.3F);
    }

}
