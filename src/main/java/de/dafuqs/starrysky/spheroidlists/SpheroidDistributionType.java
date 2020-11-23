package de.dafuqs.starrysky.spheroidlists;

/**
 * TODO:
 * Instead of having a long list of sheroids with probablilities
 * split it up in multiple lists that each have their own sublist.
 *
 * That way if a mod adds lots of ore / wood spheres that's not everything
 * the players can find anymore - making the world more diverse and intesting
 */
public enum SpheroidDistributionType {
    ESSENTIAL,
    DECORATIVE,
    FLUID,
    TREASURE,
    WOOD,
    ORE,
    DUNGEON
}
