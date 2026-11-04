package de.dafuqs.starryskies.worldgen.decorators;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.starryskies.worldgen.*;
import net.minecraft.world.level.block.state.*;

public record WallAttachedCenterDecoratorConfig(BlockState state, float chance,
                                                boolean facingOutwards) implements SphereDecoratorConfig {

    public static final Codec<WallAttachedCenterDecoratorConfig> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    BlockState.CODEC.fieldOf("block").forGetter(decorator -> decorator.state),
                    Codec.floatRange(0.0F, 1.0F).fieldOf("chance").forGetter(decorator -> decorator.chance),
                    Codec.BOOL.fieldOf("facing_outwards").forGetter(decorator -> decorator.facingOutwards)
            ).apply(instance, WallAttachedCenterDecoratorConfig::new));

}
