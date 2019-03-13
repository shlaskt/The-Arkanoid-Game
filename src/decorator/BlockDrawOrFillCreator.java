package decorator;

import collidables.Block;

import java.awt.Color;
import java.awt.Image;

/**
 * The type Block draw or fill creator.
 */
public class BlockDrawOrFillCreator extends BlockDecorator {
    private BlockBackgroundDrawer blockDrawer;
    private int numOfHits;

    /**
     * Instantiates a new Block draw or fill creator.
     *
     * @param decorated the decorated
     * @param color     the color
     * @param isStorke  the is storke
     * @param hitPoints the hit points
     */
    public BlockDrawOrFillCreator(BlockCreator decorated, Color color, boolean isStorke, int hitPoints) {
        super(decorated);
        this.numOfHits = hitPoints;
        if (isStorke) {
            this.blockDrawer = new BlockDrawColor(color);
        } else {
            this.blockDrawer = new BlockFillColor(color);
        }
    }

    /**
     * Instantiates a new Block draw or fill creator.
     *
     * @param decorated the decorated
     * @param image     the image
     * @param hitPoints the hit points
     */
    public BlockDrawOrFillCreator(BlockCreator decorated, Image image, int hitPoints) {
        super(decorated);
        this.numOfHits = hitPoints;
        this.blockDrawer = new BlockDrawImage(image);
    }

    /**
     * create.
     *
     * @param xpos the xpos
     * @param ypos the ypos
     * @return decorated
     */
    @Override
    public Block create(int xpos, int ypos) {
        Block block = super.create(xpos, ypos);
        block.addNewDrawer(this.numOfHits, this.blockDrawer);
        return block;
    }
}
