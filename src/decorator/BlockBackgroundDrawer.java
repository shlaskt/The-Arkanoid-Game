package decorator;

import biuoop.DrawSurface;
import geometry.Rectangle;

/**
 * The interface Block background drawer.
 */
public interface BlockBackgroundDrawer {

    /**
     * Draw block.
     *
     * @param d         the d
     * @param rectangle the rectangle
     */
    void drawBlock(DrawSurface d, Rectangle rectangle);
}
