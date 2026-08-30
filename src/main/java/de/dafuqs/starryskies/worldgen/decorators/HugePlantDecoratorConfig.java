package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.*;

import java.util.*;

public record HugePlantDecoratorConfig(BlockStateProvider block, Optional<BlockStateProvider> baseBlock,
                                       Optional<BlockStateProvider> tipBlock, IntProvider height,
                                       float chance) implements SphereDecoratorConfig {

    public static final Codec<HugePlantDecoratorConfig> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    BlockStateProvider.CODEC.fieldOf("block").forGetter(decorator -> decorator.block),
                    BlockStateProvider.CODEC.optionalFieldOf("base_block").forGetter(decorator -> decorator.baseBlock),
                    BlockStateProvider.CODEC.optionalFieldOf("tip_block").forGetter(decorator -> decorator.tipBlock),
                    IntProviders.CODEC.fieldOf("height").forGetter(decorator -> decorator.height),
                    Codec.FLOAT.fieldOf("chance").forGetter(decorator -> decorator.chance)
            ).apply(instance, HugePlantDecoratorConfig::new));

    public BlockStateProvider getBlockFor(int currHeight, int maxHeight) {
        if (currHeight == maxHeight - 1) {
            return this.tipBlock.orElseGet(() -> block);
        }
        if (currHeight == 0) {
            return this.baseBlock.orElseGet(() -> block);
        }
        return block;
    }

}
