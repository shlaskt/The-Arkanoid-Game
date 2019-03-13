package decorator;

import collidables.Block;

/**
 * The type Block heigh creator.
 */
public class BlockWidthCreator extends BlockDecorator {
    private int width;


    /**
     * Instantiates a new Block heigh creator.
     *
     * @param decorated the decorated
     * @param wi        the width
     */
    public BlockWidthCreator(BlockCreator decorated, int wi) {
        super(decorated);
        this.width = wi;
    }

    /**
     * set height.
     *
     * @param xpos x
     * @param ypos y
     * @return block
     */
    @Override
    public Block create(int xpos, int ypos) {
        Block block = super.create(xpos, ypos);
        block.setWidth(this.width);
        return block;
    }
}

