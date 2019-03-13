package animation;

import biuoop.DrawSurface;
import game.Constants;
import table.HighScoresTable;
import table.ScoreInfo;

import java.awt.Color;


/**
 * The type High scores animation.
 */
public class HighScoresAnimation implements Animation {
    private Constants constants;
    private HighScoresTable table;

    /**
     * Instantiates a new High scores animation.
     *
     * @param scores the scores
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.constants = new Constants();
        this.table = scores;
    }

    /**
     * draw a high score animation.
     *
     * @param d  the DrawSurface
     * @param dt the dt
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        // draw background
        d.setColor(Color.cyan.darker().darker());
        d.fillRectangle(constants.X_START_FRAME, constants.Y_START_FRAME, d.getWidth(), d.getHeight());
        // draw High Scores title
        d.setColor(Color.orange);
        d.drawText(constants.TABLE_TITLE_X, constants.TABLE_TITLE_Y, "High Scores",
                constants.TABLE_TEXT_SIZE * 2);
        // draw High Score sub-title
        d.setColor(Color.white);
        int yLine = constants.TABLE_Y;
        d.drawText(constants.TABLE_NAME_X, yLine, "Name", constants.TABLE_TEXT_SIZE);
        d.drawText(constants.TABLE_SCORE_X, yLine, "Score", constants.TABLE_TEXT_SIZE);
        // draw separate line
        d.fillRectangle(constants.TABLE_NAME_X, yLine + constants.TABLE_LINE_DY,
                constants.TABLE_LINE_WIDTH, constants.TABLE_LINE_DY);
        // draw table
        d.setColor(Color.green);
        for (ScoreInfo currScore : this.table.getHighScores()) {
            yLine += constants.TABLE_DY;
            d.drawText(constants.TABLE_NAME_X, yLine, currScore.getName(), constants.TABLE_TEXT_SIZE);
            d.drawText(constants.TABLE_SCORE_X, yLine,
                    Integer.toString(currScore.getScore()), constants.TABLE_TEXT_SIZE);
        }
        //draw Press space to continue text
        d.setColor(Color.lightGray);
        d.drawText(constants.TABLE_NAME_X, constants.TABLE_PAUSED_Y, "Press space to continue",
                constants.TABLE_PAUSED_TEXT);
    }

    /**
     * check if should stop.
     * return false (it should have decorator that override it)
     *
     * @return false
     */
    @Override
    public boolean shouldStop() {
        return false;
    }
}
