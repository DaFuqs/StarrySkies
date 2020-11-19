package de.dafuqs.starrysky.spheroidlists;

import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.spheroidtypes.SpheroidType;
import de.dafuqs.starrysky.spheroidtypes.StripesSpheroidType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SpheroidListLGBT extends SpheroidList {

    public static final ArrayList<BlockState> LIST_GAY = new ArrayList<BlockState>() {{
        add(Blocks.RED_WOOL.getDefaultState());
        add(Blocks.ORANGE_WOOL.getDefaultState());
        add(Blocks.YELLOW_WOOL.getDefaultState());
        add(Blocks.GREEN_WOOL.getDefaultState());
        add(Blocks.CYAN_WOOL.getDefaultState());
        add(Blocks.PURPLE_WOOL.getDefaultState());
    }};

    public static final ArrayList<BlockState> LIST_BI = new ArrayList<BlockState>() {{
        add(Blocks.MAGENTA_WOOL.getDefaultState());
        add(Blocks.PURPLE_WOOL.getDefaultState());
        add(Blocks.BLUE_WOOL.getDefaultState());
    }};

    public static final ArrayList<BlockState> LIST_PAN = new ArrayList<BlockState>() {{
        add(Blocks.MAGENTA_WOOL.getDefaultState());
        add(Blocks.YELLOW_WOOL.getDefaultState());
        add(Blocks.LIGHT_BLUE_WOOL.getDefaultState());
    }};

    public static final ArrayList<BlockState> LIST_ASEXUAL = new ArrayList<BlockState>() {{
        add(Blocks.BLACK_WOOL.getDefaultState());
        add(Blocks.GRAY_WOOL.getDefaultState());
        add(Blocks.WHITE_WOOL.getDefaultState());
        add(Blocks.PURPLE_WOOL.getDefaultState());
    }};

    public static final ArrayList<BlockState> LIST_GENDERQUEER = new ArrayList<BlockState>() {{
        add(Blocks.PURPLE_WOOL.getDefaultState());
        add(Blocks.WHITE_WOOL.getDefaultState());
        add(Blocks.GREEN_WOOL.getDefaultState());
    }};

    public static final ArrayList<BlockState> LIST_TRANSGENDER = new ArrayList<BlockState>() {{
        add(Blocks.LIGHT_BLUE_WOOL.getDefaultState());
        add(Blocks.PINK_WOOL.getDefaultState());
        add(Blocks.WHITE_WOOL.getDefaultState());
        add(Blocks.PINK_WOOL.getDefaultState());
        add(Blocks.LIGHT_BLUE_WOOL.getDefaultState());
    }};

    public static final ArrayList<BlockState> LIST_NONBINARY = new ArrayList<BlockState>() {{
        add(Blocks.YELLOW_WOOL.getDefaultState());
        add(Blocks.WHITE_WOOL.getDefaultState());
        add(Blocks.PURPLE_WOOL.getDefaultState());
        add(Blocks.BLACK_WOOL.getDefaultState());
    }};

    //SPHEROID TYPES
    //BASIC
    public static final StripesSpheroidType PRIDE_GAY = new StripesSpheroidType(SpheroidAdvancementIdentifier.grass, LIST_GAY, 5, 20);
    public static final StripesSpheroidType PRIDE_BI = new StripesSpheroidType(SpheroidAdvancementIdentifier.grass, LIST_BI, 5, 20);
    public static final StripesSpheroidType PRIDE_PAN = new StripesSpheroidType(SpheroidAdvancementIdentifier.grass, LIST_PAN, 5, 20);
    public static final StripesSpheroidType PRIDE_ASEXUAL = new StripesSpheroidType(SpheroidAdvancementIdentifier.grass, LIST_ASEXUAL, 5, 20);
    public static final StripesSpheroidType PRIDE_GENDERQUEER = new StripesSpheroidType(SpheroidAdvancementIdentifier.grass, LIST_GENDERQUEER, 5, 20);
    public static final StripesSpheroidType PRIDE_TRANSGENDER = new StripesSpheroidType(SpheroidAdvancementIdentifier.grass, LIST_TRANSGENDER, 5, 20);
    public static final StripesSpheroidType PRIDE_NONBINARY = new StripesSpheroidType(SpheroidAdvancementIdentifier.grass, LIST_NONBINARY, 5, 20);

    public static boolean shouldGenerate() {
        return true;
    }

    public static LinkedHashMap<SpheroidType, Float> getSpheroidTypesWithProbabilities() {
        LinkedHashMap<SpheroidType, Float> spheroidTypes = new LinkedHashMap<>();

        //BASIC
        spheroidTypes.put(PRIDE_GAY, 0.1F);
        spheroidTypes.put(PRIDE_BI, 0.1F);
        spheroidTypes.put(PRIDE_PAN, 0.1F);
        spheroidTypes.put(PRIDE_ASEXUAL, 0.1F);
        spheroidTypes.put(PRIDE_GENDERQUEER, 0.1F);
        spheroidTypes.put(PRIDE_TRANSGENDER, 0.1F);
        spheroidTypes.put(PRIDE_NONBINARY, 0.1F);

        return spheroidTypes;
    }

}
