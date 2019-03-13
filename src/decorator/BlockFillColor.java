package decorator;

import biuoop.DrawSurface;

import java.awt.Color;

import geometry.Rectangle;

/**
 * The type Block fill color.
 */
public class BlockFillColor implements BlockBackgroundDrawer {
    private Color color;

    /**
     * Instantiates a new Block fill color.
     *
     * @param color the color
     */
    public BlockFillColor(Color color) {
        this.color = color;
    }

    /**
     * draw block by interface.
     *
     * @param d         the d
     * @param rectangle the rectangle
     */
    @Override
    public void drawBlock(DrawSurface d, Rectangle rectangle) {
        rectangle.fillRect(d, this.color);
    }
}
