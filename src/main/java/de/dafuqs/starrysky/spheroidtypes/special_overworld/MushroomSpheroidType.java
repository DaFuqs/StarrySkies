package de.dafuqs.starrysky.spheroidtypes.special_overworld;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.spheroids.special_overworld.MushroomSpheroid;
import de.dafuqs.starrysky.spheroidtypes.ShellSpheroidType;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.ChunkRandom;


/**
 * Very similar to ShellSpheroid
 * but uses the mushroom blocks "sides" properties
 */
public class MushroomSpheroidType extends ShellSpheroidType {

    public MushroomSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, BlockState coreBlock, BlockState shellBlock, int minSize, int maxSize, int minShellRadius, int maxShellRadius) {
        super(spheroidAdvancementIdentifier, coreBlock, shellBlock, minSize, maxSize, minShellRadius, maxShellRadius);

        if(coreBlock == null) {
            StarrySkyCommon.LOGGER.error("MushroomSpheroidType: Registered a SpheroidType with null coreBlock!");
        }
        if(shellBlock == null) {
            StarrySkyCommon.LOGGER.error("MushroomSpheroidType: Registered a SpheroidType with null shellBlock!");
        }
    }

    public String getDescription() {
        return "MushroomSpheroid";
    }

    public MushroomSpheroid getRandomSphere(ChunkRandom chunkRandom) {
        return new MushroomSpheroid(this, chunkRandom);
    }

}