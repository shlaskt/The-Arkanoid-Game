package decorator;

import collidables.Block;

/**
 * The type Block heigh creator.
 */
public class BlockHeighCreator extends BlockDecorator {
    private int height;


    /**
     * Instantiates a new Block heigh creator.
     *
     * @param decorated the decorated
     * @param he        the he
     */
    public BlockHeighCreator(BlockCreator decorated, int he) {
        super(decorated);
        this.height = he;
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
        block.setHeigh(this.height);
        return block;
    }
}
