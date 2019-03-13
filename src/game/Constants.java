package game;

import java.awt.Color;

/**
 * a constants class.
 * has all the constants that in use in the game
 */
public class Constants {
    /**
     * The constant X_START_FRAME.
     */
// game frame
    public static final int X_START_FRAME = 0;
    /**
     * The constant Y_START_FRAME.
     */
    public static final int Y_START_FRAME = 0;
    /**
     * The constant WIDTH.
     */
    public static final int WIDTH = 800;
    /**
     * The constant HEIGHT.
     */
    public static final int HEIGHT = 600;
    /**
     * The constant LIM_SIZE.
     */
    public static final int LIM_SIZE = 20;
    /**
     * The constant FRAMES_COLOR.
     */
    public static final Color FRAMES_COLOR = Color.BLACK;
    /**
     * The constant PAUSE_END_BACKGROUND_PATH.
     */
    public static final String PAUSE_END_BACKGROUND_PATH = "background_images/omer_adam.jpg";

    /**
     * The constant GAME_NAME.
     */
    public static final String GAME_NAME = "Arkanoid";

    /**
     * The constant NUM_OF_LIVES.
     */
// game
    public static final int NUM_OF_LIVES = 5;
    /**
     * The constant BONUS_FOR_KILLING_ALL_BLOCKS.
     */
    public static final int BONUS_FOR_KILLING_ALL_BLOCKS = 100;
    /**
     * The constant PAUSE_KEY.
     */
    public static final String PAUSE_KEY = "p";

    /**
     * The constant FILE_PATH.
     */
// file
    public static final String FILE_PATH = "src/game/highscores.ser";
    /**
     * The constant HIGH_SCORE_TABLE_MAX_SIZE.
     */
    public static final int HIGH_SCORE_TABLE_MAX_SIZE = 5;


    /**
     * The constant BACKGROUND_INDICATOR_COLOR.
     */
// indicator
    public static final Color BACKGROUND_INDICATOR_COLOR = Color.cyan.darker().darker();
    /**
     * The constant INDICATOR_TEXT_COLOR.
     */
    public static final Color INDICATOR_TEXT_COLOR = Color.orange;
    /**
     * The constant INDICATOR_TEXT_SIZE.
     */
    public static final int INDICATOR_TEXT_SIZE = 13;

    /**
     * The constant COUNT_FROM.
     */
// countdown
    public static final int COUNT_FROM = 3;
    /**
     * The constant COUNTDOWN_NUM_OF_SEC.
     */
    public static final int COUNTDOWN_NUM_OF_SEC = 2;
    /**
     * The constant DELTA_TEXT.
     */
    public static final int DELTA_TEXT = 30;
    /**
     * The constant TEXT_SIZE.
     */
    public static final int TEXT_SIZE = 100;

    /**
     * The constant DISTANCE_FROM_THE_PADDLE.
     */
// balls
    public static final int DISTANCE_FROM_THE_PADDLE = 8;
    /**
     * The constant BALL_RADIUS.
     */
    public static final int BALL_RADIUS = 5;


    /**
     * The constant PADDLE_HEIGHT.
     */
// paddle
    public static final int PADDLE_HEIGHT = 15;
    /**
     * The constant SEPARATE_PADDLE_TO_SEGMENTS.
     */
    public static final int SEPARATE_PADDLE_TO_SEGMENTS = 5;
    /**
     * The constant PADDLE_COLOR.
     */
    public static final Color PADDLE_COLOR = Color.ORANGE;
    /**
     * The constant FRAMES_PER_SECONDS.
     */
// run the game
    public static final int FRAMES_PER_SECONDS = 60;

    /**
     * The constant TABLE_TEXT_SIZE.
     */
//table animation
    public static final int TABLE_TEXT_SIZE = 30;
    /**
     * The constant TABLE_PAUSED_TEXT.
     */
    public static final int TABLE_PAUSED_TEXT = 45;

    /**
     * The constant TABLE_TITLE_X.
     */
    public static final int TABLE_TITLE_X = 230;
    /**
     * The constant TABLE_TITLE_Y.
     */
    public static final int TABLE_TITLE_Y = 80;
    /**
     * The constant TABLE_LINE_DY.
     */
    public static final int TABLE_LINE_DY = 5;
    /**
     * The constant TABLE_LINE_WIDTH.
     */
    public static final int TABLE_LINE_WIDTH = 480;

    /**
     * The constant TABLE_NAME_X.
     */
    public static final int TABLE_NAME_X = 150;
    /**
     * The constant TABLE_SCORE_X.
     */
    public static final int TABLE_SCORE_X = TABLE_NAME_X + 400;
    /**
     * The constant TABLE_DY.
     */
    public static final int TABLE_DY = 50;
    /**
     * The constant TABLE_Y.
     */
    public static final int TABLE_Y = TABLE_TITLE_Y + TABLE_DY;

    /**
     * The constant TABLE_PAUSED_Y.
     */
    public static final int TABLE_PAUSED_Y = 550;


}
