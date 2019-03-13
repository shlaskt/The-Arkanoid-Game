package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.Constants;
import game.Counter;

import java.awt.Color;


/**
 * The type End screen.
 */
public class EndScreen implements Animation {
    private KeyboardSensor keyboard;
    private Counter score;
    private String status;
    private Constants constants = new Constants();

    /**
     * Constructor.
     * if isWinner == true, set to a victory string
     * otherwise, set to a game over string
     *
     * @param k            the k
     * @param currentScore the current score
     * @param isWinner     the is winner
     */
    public EndScreen(KeyboardSensor k, Counter currentScore, boolean isWinner) {
        this.keyboard = k;
        this.score = currentScore;
        if (isWinner) {
            this.status = "You Win!";
        } else {
            this.status = "Game over";
        }
    }


    /**
     * Draw end text.
     *
     * @param d the d
     */
    public void drawEndText(DrawSurface d) {
        int textSize = 60;
        int x = 10;
        int y = d.getHeight() / 3;

        d.setColor(Color.orange);
        d.drawText(x, y, status, textSize);
        d.drawText(x, y + 2 * textSize, "Your score is: " + Integer.toString(score.getValue()), textSize);
        d.drawText(x, y + 4 * textSize, "Press space to exit", textSize);
    }

    /**
     * print the end screen.
     * until the player press the space key
     *
     * @param d  the DrawSurface
     * @param dt the td
     */
    public void doOneFrame(DrawSurface d, double dt) {
        // draw background
        d.setColor(Color.cyan.darker().darker());
        d.fillRectangle(constants.X_START_FRAME, constants.Y_START_FRAME, d.getWidth(), d.getHeight());


//        IoUtils utils = new IoUtils();
//        Image img = utils.setImageFromString(constants.PAUSE_END_BACKGROUND_PATH);
//        // Draw the image on a DrawSurface
//        d.drawImage(constants.X_START_FRAME, constants.Y_START_FRAME, img);

        drawEndText(d); // draw the text
    }

    /**
     * return if it should stop printing.
     *
     * @return boolean if should stop
     */
    public boolean shouldStop() {
        return false;
    }
}