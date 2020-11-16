package de.dafuqs.starrysky.spheroids.special_overworld;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.spheroids.Spheroid;
import de.dafuqs.starrysky.spheroidtypes.special_overworld.BeeHiveSpheroidType;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

public class BeeHiveSpheroid extends Spheroid {

    private int shellRadius;
    private int flowerRingRadius;
    private int flowerRingSpacing;

    public BeeHiveSpheroid(BeeHiveSpheroidType beeHiveSpheroidType, ChunkRandom random) {
        super(beeHiveSpheroidType, random);

        this.shellRadius = beeHiveSpheroidType.getRandomShellRadius(random);
        this.radius = beeHiveSpheroidType.getRandomRadius(random);
        this.flowerRingRadius = beeHiveSpheroidType.getRandomFlowerRingRadius(random);
        this.flowerRingSpacing = beeHiveSpheroidType.getRandomFlowerRingSpacing(random);
    }

    @Override
    public void generate(Chunk chunk) {
        int chunkX = chunk.getPos().x;
        int chunkZ = chunk.getPos().z;

        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        int z = this.getPosition().getZ();

        int endRingDistance = this.radius;
        int startRingDistance = this.radius - this.flowerRingRadius;
        int shellDistance = startRingDistance - this.flowerRingSpacing;
        int coreDistance = shellDistance - shellRadius;

        random.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
        for (int x2 = Math.max(chunkX * 16, x - this.radius); x2 <= Math.min(chunkX * 16 + 15, x + this.radius); x2++) {
            for (int y2 = y - this.radius; y2 <= y + this.radius; y2++) {
                for (int z2 = Math.max(chunkZ * 16, z - this.radius); z2 <= Math.min(chunkZ * 16 + 15, z + this.radius); z2++) {
                    BlockPos currBlockPos = new BlockPos(x2, y2, z2);
                    float d = Math.round(Support.distance(x, y, z, x2, y2, z2));
                    if(d == 0) {
                        //TODO: Create bee hive and add queen + others in hive
                        /*BeeEntity queen =((BeeHiveSpheroidType) this.getSpheroidType()).getRandomQueen(random, world);
                        chunk.setBlockState(currBlockPos, Blocks.BEE_NEST.getDefaultState().rotate(BlockRotation.random(random)), false);
                        chunk.setBlockEntity(currBlockPos, new BeehiveBlockEntity());
                        BlockEntity blockEntity_1 = chunk.getBlockEntity(currBlockPos);
                        if (blockEntity_1 instanceof BeehiveBlockEntity) {
                            ((BeehiveBlockEntity) blockEntity_1).tryEnterHive(queen, false);
                        }

                        int countBees = random.nextInt(this.radius / 3);
                        /*for(int i = 0; i < countBees; i++) {
                            BeeEntity bee = new BeeEntity(EntityType.BEE, world);
                            bee.setPos(x, y-1, z);
                            world.spawnEntity(bee);
                        }*/
                    } else if (d < coreDistance) {
                        int r = random.nextInt((int) Math.ceil(coreDistance / 3F)); // TODO: more or less than 2?
                        if (coreDistance - r <= d) {
                            chunk.setBlockState(currBlockPos, Blocks.HONEY_BLOCK.getDefaultState(), false);
                        } else {
                            chunk.setBlockState(currBlockPos, Blocks.AIR.getDefaultState(), false);
                        }
                    } else if (d < shellDistance) {
                        if(random.nextInt(10) == 0) {
                            chunk.setBlockState(currBlockPos, Blocks.HONEY_BLOCK.getDefaultState(), false);
                        } else {
                            chunk.setBlockState(currBlockPos, Blocks.HONEYCOMB_BLOCK.getDefaultState(), false);
                        }
                    } else if (y-y2 == 0 && d > startRingDistance && d <= endRingDistance) {
                        chunk.setBlockState(currBlockPos, Blocks.GRASS_BLOCK.getDefaultState(), false);
                    } else if (y-y2 == -1 && d > startRingDistance && d <= endRingDistance && random.nextInt(3) == 0) {
                        chunk.setBlockState(currBlockPos, ((BeeHiveSpheroidType) getSpheroidType()).getRandomFlower(random), false);
                    }
                }
            }
        }

        this.setChunkFinished(chunk.getPos());
    }


    @Override
    public String getDescription() {
        return this.getSpheroidType().getDescription() +
                "\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
                "\nRadius: " + this.radius +
                "\nShellRadius: " + this.shellRadius +
                "\nFlowerRingRadius: " + this.flowerRingRadius +
                "\nFlowerRingSpacing: " + this.flowerRingSpacing;
    }
}
