package de.dafuqs.starrysky.dimension;

/**
 * Instead of having a long list of spheroids with probabilities
 * this enum is used to generate a list of lists, so that each
 * SpheroidDistributionType has their own sublist of spheroids.
 *
 * That way if a mod adds lots of ore / wood spheres that's not everything
 * the players can find anymore - making the world more diverse and interesting
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
