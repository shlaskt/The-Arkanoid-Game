package game;

import animation.Animation;
import animation.AnimationRunner;
import animation.CountdownAnimation;
import animation.KeyPressStoppableAnimation;
import animation.PauseScreen;
import collidables.Block;
import collidables.BlockRemover;
import collidables.Collidable;
import collidables.Paddle;
import decorator.BlockCreator;
import decorator.BlockDrawOrFillCreator;
import decorator.BlockHeighCreator;
import decorator.BlockPointsCreator;
import decorator.BlockWidthCreator;
import decorator.DefaultBlockCreator;
import geometry.Point;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import sprites.Ball;
import sprites.BallRemover;
import sprites.LivesIndicator;
import sprites.ScoreIndicator;
import sprites.Sprite;
import sprites.SpriteCollection;

import java.awt.Color;


/**
 * the game class.
 * initialize all the objects for the game (balls, blocks, paddle..)
 * run the game.
 */
public class GameLevel implements Animation {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private AnimationRunner runner;
    private KeyboardSensor keyboard;
    private game.Constants constants;
    private Counter numberOfLives;
    private Counter blocksCounter;
    private Counter ballCounter;
    private Counter score;
    private boolean running;
    private LevelInformation levelInformation;


    /**
     * Instantiates a new Game level.
     *
     * @param level        the level
     * @param ks           the KeyboardSensor
     * @param ar           the AnimationRunner
     * @param lives        the lives
     * @param currentScore the cuurent score
     */
    public GameLevel(LevelInformation level, KeyboardSensor ks, AnimationRunner ar,
                     Counter lives, Counter currentScore) {
        this.constants = new game.Constants();
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.blocksCounter = new Counter();
        this.ballCounter = new Counter();
        this.runner = ar;
        this.levelInformation = level;
        this.keyboard = ks;
        this.numberOfLives = lives;
        this.score = currentScore;
    }

    /**
     * add a collidable to the game.
     * from the collidable's class
     *
     * @param c collidable
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * add a sprite to the game.
     * from the sprite's class
     *
     * @param s sprite
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * set the blocks of the limits (game's frame).
     *
     * @param x1        x of the start point of the frame
     * @param y1        y of the start point of the frame
     * @param x2        x of the end point of the frame
     * @param y2        y of the end point of the frame
     * @param blockSize the size of the blocks
     */
    public void setLimitsBlocks(int x1, int y1, int x2, int y2, int blockSize) {
        Block upLim = createBlockByDecorator(new Point(x1, y1), x2 - x1, blockSize, 1);
        Block leftLim = createBlockByDecorator(new Point(x1, y1), blockSize, y2 - y1, 1);
        Block rightLim = createBlockByDecorator(new Point(x2 - blockSize, y1), blockSize, y2 - y1, 1);
        // create a special block that will sit at the bottom of the screen
        Block deathBlock = createBlockByDecorator(new Point(x1, y2), x2 - x1, blockSize, 1);

        BallRemover ballRemover = new BallRemover(this, this.ballCounter);
        deathBlock.addHitListener(ballRemover);

        // add the limits to the game
        upLim.addToGame(this);
        leftLim.addToGame(this);
        rightLim.addToGame(this);
        deathBlock.addToGame(this);

    }

    /**
     * Create one lim block.
     *
     * @param upLimPoint the up lim point
     * @param width      the width
     * @param height     the height
     * @param numOfHits  the num of hits
     * @return the block
     */
    public Block createBlockByDecorator(Point upLimPoint, int width, int height, int numOfHits) {
        BlockCreator blockCreator =
                new BlockDrawOrFillCreator(new BlockDrawOrFillCreator(
                        new BlockHeighCreator(
                                new BlockWidthCreator(
                                        new BlockPointsCreator(new DefaultBlockCreator(), numOfHits),
                                        width),
                                height),
                        Color.LIGHT_GRAY,
                        false, -1), Color.BLACK, true, -1);
        Block block = blockCreator.create((int) upLimPoint.getX(), (int) upLimPoint.getY());
        return block;
    }

    /**
     * Create balls on top of paddle.
     *
     * @param pad the paddle
     */
    public void createBallsOnTopOfPaddle(Paddle pad) {
        double x = pad.getRect().getUpperLeft().getX() + pad.getRect().getWidth() / 2;
        double y = pad.getRect().getUpperLeft().getY() - constants.DISTANCE_FROM_THE_PADDLE;
        int numOfBalls = levelInformation.numberOfBalls();
        int moveBallX = levelInformation.paddleWidth() / (numOfBalls + 1); // +1-> not be on the edge
        int dx = 0;
        int di = 1;

        if (numOfBalls % 2 == 0) { // even number of balls
            dx += moveBallX; // skip the first location of the ball (exactly in the middle)
            di = 2; // change the place of the balls
        }

        for (int i = 0; i < numOfBalls; i++) { // create the balls
            Point center = new Point(x + dx, y);
            Ball ball = new Ball(center, constants.BALL_RADIUS, Color.white,
                    this.levelInformation.initialBallVelocities().get(i));
            // add the ball to the game
            ball.setPaddle(pad);
            ball.addToGame(this);
            ball.setGameEnvironment(this.environment);
            this.ballCounter.increase(1); // update the counter
            this.environment.addBall(ball);
            // change the delta x
            if (i % 2 == 0) {
                dx -= moveBallX * (i + di); // to not put the balls in the same place
            } else {
                dx += moveBallX * (i + di); // to not put the balls in the same place
            }
        }
    }


    /**
     * initialize the paddle.
     *
     * @return the paddle (to send it to the balls after)
     */
    public Paddle initializePaddle() {
        Paddle paddle = new Paddle(this.levelInformation.paddleWidth(), constants.PADDLE_HEIGHT,
                constants.PADDLE_COLOR, this.levelInformation.paddleSpeed(), this.keyboard);
        paddle.addToGame(this); // add the paddle to the game
        return paddle;
    }

    /**
     * Initialize blocks.
     */
    public void initializeBlocks() {
        BlockRemover blockRemover = new BlockRemover(this, this.blocksCounter);
        ScoreTrackingListener scoreTrackingListener = new ScoreTrackingListener(this.score);
        // add all the blocks in the list to the game
        for (Block block : levelInformation.blocks()) {
            block.addHitListener(scoreTrackingListener);
            block.addHitListener(blockRemover);
            block.addToGame(this); // add the block to the game
            this.blocksCounter.increase(1); // update the blocks counter
        }

    }

    /**
     * Initialize the level.
     * get all the information it needed from the specific level.
     */
    public void initialize() {
        // initialize the background
        this.sprites.addSprite(levelInformation.getBackground());
        // initialize the limits and the "death block"
        setLimitsBlocks(constants.X_START_FRAME, constants.Y_START_FRAME + constants.LIM_SIZE,
                constants.WIDTH, constants.HEIGHT, constants.LIM_SIZE);
        // initialize the blocks
        initializeBlocks();
        // initialize the score and life indicators
        ScoreIndicator scoreIndicator = new ScoreIndicator(new Point(constants.X_START_FRAME, constants.Y_START_FRAME),
                constants.WIDTH, constants.LIM_SIZE, constants.BACKGROUND_INDICATOR_COLOR,
                this.score, this.levelInformation.levelName());
        LivesIndicator livesIndicator = new LivesIndicator(new Point(constants.X_START_FRAME, constants.Y_START_FRAME),
                constants.WIDTH, constants.LIM_SIZE, this.numberOfLives);
        this.sprites.addSprite(scoreIndicator);
        this.sprites.addSprite(livesIndicator);
    }

    /**
     * doing One Frame.
     * run the countdown game, then the level itself.
     * check all the time if the player want to pause
     *
     * @param d  the DrawSurface
     * @param dt the dt
     */
    public void doOneFrame(DrawSurface d, double dt) {
        if (this.keyboard.isPressed(constants.PAUSE_KEY)) { // if the player pressed the paused key
            Animation pauseablePaused = new KeyPressStoppableAnimation(keyboard,
                    keyboard.SPACE_KEY, new PauseScreen());
            this.runner.run(pauseablePaused); // pause the game
        }
        // if there is no more blocks
        if (this.blocksCounter.getValue() == 0) {
            this.score.increase(constants.BONUS_FOR_KILLING_ALL_BLOCKS); // increase the score with bonus
            this.running = false; // change to false - next level
        } else if (this.ballCounter.getValue() == 0) { // if there is no more balls
            this.running = false; // change to false - lose life
        }
        this.sprites.drawAllOn(d); // draw all others objects
        this.sprites.notifyAllTimePassed();
    }

    /**
     * return if should stop.
     *
     * @return not is running
     */
    public boolean shouldStop() {
        return !this.running;
    }


    /**
     * Play one turn.
     */
    public void playOneTurn() {
        // initialize new paddle and balls
        Paddle paddle = initializePaddle();
        this.createBallsOnTopOfPaddle(paddle);

        this.runner.run(new CountdownAnimation(constants.COUNTDOWN_NUM_OF_SEC, constants.COUNT_FROM, this.sprites));
        this.running = true;
        this.runner.run(this);

        if (this.ballCounter.getValue() == 0) { // if the player lose the turn
            this.numberOfLives.decrease(1); // decrease one life
        }
        paddle.removeFromGame(this); // remove paddle from game
    }


    /**
     * Remove collidable.
     *
     * @param c the c
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Remove sprite.
     *
     * @param s the s
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Remove ball.
     *
     * @param b the b
     */
    public void removeBall(Ball b) {
        this.environment.removeBall(b);
    }

    /**
     * Get num of blocks left int.
     *
     * @return the int
     */
    public int getNumOfBlocksLeft() {
        return this.blocksCounter.getValue();
    }

}
