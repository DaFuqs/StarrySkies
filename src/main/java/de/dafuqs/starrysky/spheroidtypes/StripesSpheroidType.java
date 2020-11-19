package de.dafuqs.starrysky.spheroidtypes;

import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import net.minecraft.block.BlockState;

import java.util.ArrayList;

public class StripesSpheroidType extends SpheroidType {

    private ArrayList<BlockState> stripesBlockStates;

    public StripesSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, ArrayList<BlockState> stripesBlockStates, int minSize, int maxSize) {
        this.spheroidAdvancementIdentifier = spheroidAdvancementIdentifier;
        this.minRadius = minSize;
        this.maxRadius = maxSize;
        this.stripesBlockStates = stripesBlockStates;
    }

    public ArrayList<BlockState> getStripesBlockStates() {
        return stripesBlockStates;
    }

    @Override
    public String getDescription() {
        return "StripesSpheroid";
    }


}
