package game;

import collidables.Block;
import sprites.Sprite;
import sprites.Velocity;

import java.util.LinkedList;
import java.util.List;

/**
 * The type Generic level.
 */
public class GenericLevel implements LevelInformation, Cloneable {
    private int numOfBalls;
    private List<Velocity> velocities;
    private int paddleSpe;
    private int paddleWid;
    private String nameLevel;
    private Sprite backgroundSprite;
    private List<Block> blocksList;
    private int numbOfBlocksToRemove;

    /**
     * Sets name level.
     *
     * @param name the name
     */
    public void setNameLevel(String name) {
        this.nameLevel = name;
    }

    /**
     * Sets background sprite.
     *
     * @param background the background
     */
    public void setBackgroundSprite(Sprite background) {
        this.backgroundSprite = background;
    }

    /**
     * Sets blocks list.
     *
     * @param blockList the block list
     */
    public void setBlocksList(List<Block> blockList) {
        this.blocksList = blockList;
    }

    /**
     * Sets numb of blocks to remove.
     *
     * @param numbOfBlocks the numb of blocks
     */
    public void setNumbOfBlocksToRemove(int numbOfBlocks) {
        this.numbOfBlocksToRemove = numbOfBlocks;
    }

    /**
     * Sets num of balls.
     *
     * @param ballsNum the balls num
     */
    public void setNumOfBalls(int ballsNum) {
        this.numOfBalls = ballsNum;
    }

    /**
     * Sets paddle spe.
     *
     * @param paddleSpeed the paddle speed
     */
    public void setPaddleSpe(int paddleSpeed) {
        this.paddleSpe = paddleSpeed;
    }

    /**
     * Sets paddle wid.
     *
     * @param paddleWidth the paddle width
     */
    public void setPaddleWid(int paddleWidth) {
        this.paddleWid = paddleWidth;
    }

    /**
     * Sets velocities.
     *
     * @param velocitiesList the velocities list
     */
    public void setVelocities(List<Velocity> velocitiesList) {
        this.velocities = velocitiesList;
    }

    /**
     * get the number of balls in the level.
     *
     * @return number of balls
     */

    @Override
    public int numberOfBalls() {
        return this.numOfBalls;
    }

    /**
     * The initial velocity of each ball.
     * make the balls get a fan shape
     *
     * @return list of velocities
     */
    @Override
    public List<Velocity> initialBallVelocities() {
        return this.velocities;
    }

    /**
     * get the paddle speed.
     *
     * @return paddle speed.
     */
    @Override
    public int paddleSpeed() {
        return this.paddleSpe;
    }

    /**
     * get the paddle width.
     *
     * @return paddle width.
     */
    @Override
    public int paddleWidth() {
        return this.paddleWid;
    }

    /**
     * the level name will be displayed at the top of the screen.
     *
     * @return level name
     */
    @Override
    public String levelName() {
        return this.nameLevel;
    }

    /**
     * Returns a sprite with the background of the level.
     *
     * @return background of the level
     */
    @Override
    public Sprite getBackground() {
        return this.backgroundSprite;
    }

    /**
     * The Blocks that make up this level.
     * each block contains its size, color, number of hits and and location.
     *
     * @return list of blocks
     */
    @Override
    public List<Block> blocks() {
        return this.blocksList;
    }

    /**
     * Number of blocks that should be removed before the level is considered to be "cleared".
     *
     * @return Number of blocks
     */
    @Override
    public int numberOfBlocksToRemove() {
        return this.numbOfBlocksToRemove;
    }

    /**
     * clone level information.
     * @return level info
     */
    @Override
    public LevelInformation clone() {
        try {
            GenericLevel cloned = (GenericLevel) super.clone();
            List<Block> newList = new LinkedList<>();
            for (Block b : cloned.blocksList) {
                newList.add(b.clone());
            }
            cloned.setBlocksList(newList);
            return cloned;
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }
}
