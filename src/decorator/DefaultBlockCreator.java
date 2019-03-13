package decorator;

import collidables.Block;

/**
 * The type Default block creator.
 */
public class DefaultBlockCreator implements BlockCreator {
    /**
     * create.
     *
     * @param xpos the xpos
     * @param ypos the ypos
     * @return decorated
     */
    @Override
    public Block create(int xpos, int ypos) {
        return new Block(xpos, ypos);
    }
}
