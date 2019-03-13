package io;

import decorator.BlockCreator;
import collidables.Block;

import java.util.Map;
import java.util.TreeMap;

/**
 * The type Blocks from symbols factory.
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * Instantiates a new Blocks from symbols factory.
     *
     * @param spacerWidths  the spacer widths
     * @param blockCreators the block creators
     */
    public BlocksFromSymbolsFactory(Map<String, Integer> spacerWidths, Map<String, BlockCreator> blockCreators) {
        this.spacerWidths = spacerWidths;
        this.blockCreators = blockCreators;
    }

    /**
     * Instantiates a new Blocks from symbols factory.
     */
    public BlocksFromSymbolsFactory() {
        this.spacerWidths = new TreeMap<>();
        this.blockCreators = new TreeMap<>();
    }

    /**
     * Sets block creators.
     *
     * @param blockCreator the block creator
     */
    public void setBlockCreators(Map<String, BlockCreator> blockCreator) {
        this.blockCreators = blockCreator;
    }

    /**
     * Sets spacer widths.
     *
     * @param spacerWidth the spacer width
     */
    public void setSpacerWidths(Map<String, Integer> spacerWidth) {
        this.spacerWidths = spacerWidth;
    }

    /**
     * Add to block creators.
     *
     * @param s            the s
     * @param blockCreator the block creator
     */
    public void addToBlockCreators(String s, BlockCreator blockCreator) {
        this.blockCreators.put(s, blockCreator);
    }

    /**
     * Add to spacer widths.
     *
     * @param s     the s
     * @param space the space
     */
    public void addToSpacerWidths(String s, int space) {
        this.spacerWidths.put(s, space);
    }

    /**
     * Is space symbol boolean.
     *
     * @param s the s
     * @return the boolean
     */
// returns true if 's' is a valid space symbol.
    public boolean isSpaceSymbol(String s) {
        return spacerWidths.containsKey(s);
    }

    /**
     * Is block symbol boolean.
     *
     * @param s the s
     * @return the boolean
     */
// returns true if 's' is a valid block symbol.
    public boolean isBlockSymbol(String s) {
        return blockCreators.containsKey(s);
    }

    /**
     * Gets block.
     *
     * @param s the s
     * @param x the x
     * @param y the y
     * @return the block
     */
// Return a block according to the definitions associated
    // with symbol s. The block will be located at position (xpos, ypos).
    public Block getBlock(String s, int x, int y) {
        return this.blockCreators.get(s).create(x, y);
    }

    /**
     * Gets space width.
     *
     * @param s the s
     * @return the space width
     */
// Returns the width in pixels associated with the given spacer-symbol.
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }


}
