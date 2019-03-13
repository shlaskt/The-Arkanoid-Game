package decorator;

import biuoop.DrawSurface;

import java.awt.Color;

import geometry.Rectangle;

/**
 * The type Block draw color.
 */
public class BlockDrawColor implements BlockBackgroundDrawer {
    private Color color;

    /**
     * Instantiates a new Block draw color.
     *
     * @param color the color
     */
    public BlockDrawColor(Color color) {
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
        rectangle.drawRect(d, this.color);
    }
}
