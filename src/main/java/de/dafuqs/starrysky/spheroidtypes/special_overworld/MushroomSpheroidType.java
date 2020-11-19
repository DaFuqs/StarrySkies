package de.dafuqs.starrysky.spheroidtypes.special_overworld;

import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.spheroidtypes.ShellSpheroidType;
import net.minecraft.block.BlockState;


/**
 * Very similar to ShellSpheroid
 * but uses the mushroom blocks "sides" properties
 */
public class MushroomSpheroidType extends ShellSpheroidType {

    public MushroomSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, BlockState coreBlock, BlockState shellBlock, int minSize, int maxSize, int minShellRadius, int maxShellRadius) {
        super(spheroidAdvancementIdentifier, coreBlock, shellBlock, minSize, maxSize, minShellRadius, maxShellRadius);
    }

    public String getDescription() {
        return "PumpkinSpheroid";
    }

}