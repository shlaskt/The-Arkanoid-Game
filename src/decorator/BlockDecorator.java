package decorator;

import collidables.Block;

/**
 * The type Block decorator.
 */
public abstract class BlockDecorator implements BlockCreator {
    private BlockCreator decoratedBlock;

    /**
     * Instantiates a new Block decorator.
     *
     * @param decorated the decorated
     */
    public BlockDecorator(BlockCreator decorated) {
        this.decoratedBlock = decorated;
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
        return this.decoratedBlock.create(xpos, ypos);
    }
}
