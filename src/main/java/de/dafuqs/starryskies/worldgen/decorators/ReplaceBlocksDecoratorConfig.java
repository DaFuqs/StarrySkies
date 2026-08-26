package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.*;

public record ReplaceBlocksDecoratorConfig(Direction towardsDirection, IntProvider depth,
                                           BlockStateProvider state) implements SphereDecoratorConfig {

    public static final Codec<ReplaceBlocksDecoratorConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Direction.CODEC.fieldOf("towards_direction").forGetter(decorator -> decorator.towardsDirection),
                    IntProviders.CODEC.fieldOf("depth").forGetter(decorator -> decorator.depth),
                    BlockStateProvider.CODEC.fieldOf("block").forGetter(decorator -> decorator.state)
            ).apply(instance, ReplaceBlocksDecoratorConfig::new)
    );

}