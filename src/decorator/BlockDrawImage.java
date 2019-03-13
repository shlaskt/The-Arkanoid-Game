package decorator;

import biuoop.DrawSurface;
import geometry.Rectangle;

import java.awt.Image;

/**
 * The type Block draw image.
 */
public class BlockDrawImage implements BlockBackgroundDrawer {
    private Image image;

    /**
     * Instantiates a new Block draw image.
     *
     * @param image the image
     */
    public BlockDrawImage(Image image) {
        this.image = image;
    }

    /**
     * draw block by interface.
     *
     * @param d         the d
     * @param rectangle the rectangle
     */
    @Override
    public void drawBlock(DrawSurface d, Rectangle rectangle) {
        rectangle.drawImageRect(d, image);
    }
}
