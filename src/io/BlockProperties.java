package io;

/**
 * The type Block properties.
 */
public class BlockProperties {
    private double blocksStartX;
    private double blocksStartY;
    private double rowHeight;

    /**
     * Instantiates a new Block properties.
     *
     * @param blocksStartX the blocks start x
     * @param blocksStartY the blocks start y
     * @param rowHeight    the row height
     */
    public BlockProperties(double blocksStartX, double blocksStartY, double rowHeight) {
        this.blocksStartX = blocksStartX;
        this.blocksStartY = blocksStartY;
        this.rowHeight = rowHeight;
    }

    /**
     * Gets blocks start y.
     *
     * @return the blocks start y
     */
    public double getBlocksStartY() {
        return blocksStartY;
    }

    /**
     * Sets blocks start y.
     *
     * @param bsy the bsy
     */
    public void setBlocksStartY(double bsy) {
        this.blocksStartY = bsy;
    }

    /**
     * Gets blocks start x.
     *
     * @return the blocks start x
     */
    public double getBlocksStartX() {
        return blocksStartX;
    }

    /**
     * Sets blocks start x.
     *
     * @param bsx the bsx
     */
    public void setBlocksStartX(double bsx) {
        this.blocksStartX = bsx;
    }

    /**
     * Gets row height.
     *
     * @return the row height
     */
    public double getRowHeight() {
        return rowHeight;
    }

    /**
     * Sets row height.
     *
     * @param rh the rh
     */
    public void setRowHeight(double rh) {
        this.rowHeight = rh;
    }
}
