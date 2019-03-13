package decorator;

import collidables.Block;

/**
 * The type Block points creator.
 */
public class BlockPointsCreator extends BlockDecorator {
    private int numOfHits;

    /**
     * Instantiates a new Block points creator.
     *
     * @param decorated the decorated
     * @param hp        the hp
     */
    public BlockPointsCreator(BlockCreator decorated, int hp) {
        super(decorated);
        this.numOfHits = hp;
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
        block.setNumOfHits(this.numOfHits);
        return block;
    }
}
